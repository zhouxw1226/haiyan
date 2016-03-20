package haiyan.common.jms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public abstract class JMSProducer extends JMSManager {
	private final List<JMSMessage> messages = new ArrayList<JMSMessage>();
	public JMSProducer() {
		super();
	}
	public JMSProducer(String DSN) {
		super(DSN);
	}
	public void addMessage(JMSMessage s) {
		this.messages.add(s);
	}
	public int getMessageCount(){
		return this.messages.size();
	}
	public void clear() {
		this.messages.clear();
	}
	private Message createMessage(Session session, Map<String, Object> map) throws JMSException {  
        MapMessage message = session.createMapMessage();  
        if (map != null && !map.isEmpty()) {  
            Set<String> keys = map.keySet();  
            for (String key : keys) {  
                message.setObject(key, map.get(key));  
            }  
        }  
        return message;  
    }
	private Message createMessage(Session session, byte[] data) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(data);
		return message;
	}
	@SuppressWarnings("unchecked")
	protected void sendLoop(Session session, MessageProducer producer) throws Throwable {
		JMSMessage[] msgs = this.messages.toArray(new JMSMessage[0]);
		for (JMSMessage o :msgs) {
			Message message;
			if (o.type == JMSType.BYTES) {
				message = createMessage(session, (byte[]) o.data);
				message.setJMSType(JMSType.BYTES.name());
			} else if (o.type == JMSType.MAP) {
				message = createMessage(session, (Map<String, Object>) o.data);
				message.setJMSType(JMSType.MAP.name());
			}else if (o.type == JMSType.OBJECT) {
				message = session.createObjectMessage((Serializable) o.data);
				message.setJMSType(JMSType.OBJECT.name());
			} else {
				message = session.createTextMessage((String) o.data);
				message.setJMSType(JMSType.STRING.name());
			}
			message.setStringProperty(JMSMessage.ACTION, o.action);
			producer.send(message);
		}
	}
	public void execute() throws Throwable {
		MessageProducer producer = null;
		try {
			String subject = this.getSubject().toString();
			Destination destination;
			if (this.topic) {
				destination = session.createTopic(subject);
			} else { 
				destination = session.createQueue(subject);
			}
			producer = session.createProducer(destination);
			if (this.persistent) {
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			} else {
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			sendLoop(session, producer);
		} finally {
			if (producer != null)
				try {
					producer.close();
				} catch (Throwable ignore) {
				}
		}
	}
	protected Session session;
	private void createSession(int sessionType) throws Throwable {
		//Session.AUTO_ACKNOWLEDGE 处理完消息自动确认
		//Session.CLIENT_ACKNOWLEDGE 处理完消息手动确认
		//Session.DUPS_OK_ACKNOWLEDGE 批量确认
		//Session.SESSION_TRANSACTED 开启同步事务
		this.session = this.connection.createSession(this.transacted, sessionType < 0 ? Session.CLIENT_ACKNOWLEDGE : sessionType);
	}
	public void openConnection(int sessionType) throws Throwable {
		this.transacted = false;
		this.createConnection();
		this.createSession(sessionType);
	}
	public void openTransaction(int sessionType) throws Throwable {
		this.transacted = true;
		this.createConnection();
		this.createSession(sessionType);
	}
	public void commit() {
		if (this.session!=null)
			try {
				this.session.commit();
			} catch (JMSException ignore) {
				ignore.printStackTrace();
			}
	}
	public void rollback() {
		if (this.session!=null)
			try {
				this.session.rollback();
			} catch (JMSException ignore) {
				ignore.printStackTrace();
			}
	}
	public void close() {
		this.clear();
		if (this.session != null)
			try {
				this.session.close();
			} catch (Throwable ignore) {
			}
		this.session = null;
		super.close();
	}
}