package haiyan.common.jms;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;

public abstract class JMSConsumer extends JMSManager {
	private final List<JMSConsumerListener> listeners = new ArrayList<JMSConsumerListener>();
	public JMSConsumer() {
		super();
	}
	public JMSConsumer(String DSN) {
		super(DSN);
	}
	@Deprecated
	public void addJMSListener(JMSConsumerListener listener) {
		this.listeners.add(listener);
	}
	public void addListener(JMSConsumerListener listener) {
		this.listeners.add(listener);
	}
	public int getListenerCount(){
		return this.listeners.size();
	}
	public void clear() {
		this.listeners.clear();
	}
	@SuppressWarnings("rawtypes")
	private Map<String,Object> getMap(MapMessage message) throws JMSException { 
		Map<String,Object> map = new HashMap<String,Object>();
		Enumeration enume = message.getMapNames();
		while(enume.hasMoreElements()) {
			String key = (String)enume.nextElement();
			Object val = message.getObject(key);
			map.put(key, val);
		}
		return map; 
    }
	protected MessageListener createJMSListener() {
		return new MessageListener() {
			public void onMessage(Message m) {
				for (JMSConsumerListener listener : listeners) {
					try {
						if ((m instanceof TextMessage)) {
							listener.execute(m, ((TextMessage) m).getText());
						} else if ((m instanceof MapMessage)) {
							listener.execute(m, getMap((MapMessage)m));
						} else if ((m instanceof ObjectMessage)) {
							listener.execute(m, ((ObjectMessage) m).getObject());
						} else if ((m instanceof BytesMessage)) {
							byte[] buff = new byte[1024];
							int n = -1;
							ByteArrayOutputStream os = null;
							try {
								os = new ByteArrayOutputStream();
								BytesMessage bm = (BytesMessage) m;
								while ((n = bm.readBytes(buff)) > 0) {
									os.write(buff, 0, n);
								}
								listener.execute(m, os.toByteArray());
							} finally {
								CloseUtil.close(os);
							}
						}
					} catch (Throwable e) {
						DebugUtil.error(e);
					}
				}
			}
		};
	}
	public void execute() throws Throwable {
		MessageListener jmsLisenter = createJMSListener();
		this.createConnection();
		for (JMSConsumerListener listener : listeners) {
			String subject = listener.getSubject().toString();
			Destination destination;
			if (this.topic) {
				destination = new ActiveMQTopic(subject);
//				// 为每个listener创建独立的ConsumerSession
//				Session session = connection.createSession(listener.isTransacted(), listener.getSessionType());
//				TopicSubscriber topicScriber = session.createDurableSubscriber((ActiveMQTopic)destination, subject);
//				topicScriber.setMessageListener(jmsLisenter);
			} else {
				destination = new ActiveMQQueue(subject);
//				// 为每个listener创建独立的ConsumerSession
//				Session session = connection.createSession(listener.isTransacted(), listener.getSessionType());
//				MessageConsumer consumer = session.createConsumer(destination);
//				consumer.setMessageListener(jmsLisenter);
			}
			// 为每个listener创建独立的ConsumerSession
			Session session = connection.createSession(listener.isTransacted(), listener.getSessionType());
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(jmsLisenter);
		}
	}
	@Override
	public void close() {
		this.clear();
		super.close();
	}
}