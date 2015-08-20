package haiyan.common.jms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class JMSProducer extends JMSManager {
	private List<JMSMessage> messages = new ArrayList<JMSMessage>();
	public void execute() throws Throwable {
		MessageProducer producer = null;
		try {
			if (this.topic)
				this.destination = session.createTopic(this.subject);
			else 
				this.destination = session.createQueue(this.subject);
			producer = session.createProducer(this.destination);
			if (this.persistent)
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			else {
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
	protected void sendLoop(Session session, MessageProducer producer)
			throws Exception {
		JMSMessage[] msgs = this.messages.toArray(new JMSMessage[0]);
		for (JMSMessage o :msgs) {
			Message message;
			if (o.type == JMSType.BYTES) {
				message = createMessage(session, (byte[]) o.data);
				message.setJMSType(JMSType.BYTES.name());
			} else if (o.type == JMSType.MAP) {
				message = createMessage(session, (Map<String, Object>)o.data);
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
	public void addMessage(JMSMessage s) {
		this.messages.add(s);
	}
	public int getMessageCount(){
		return this.messages.size();
	}
	public void clear() {
		this.messages.clear();
	}
	@Override
	public void close() {
		this.clear();
		super.close();
	}
}