package haiyan.common.jms;

import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;

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

public class JMSConsumer extends JMSManager {
	private final List<JMSConsumerListener> listeners = new ArrayList<JMSConsumerListener>();
	public void addJMSListener(JMSConsumerListener listener) {
		listeners.add(listener);
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
	public void execute() throws Throwable {
		MessageListener jmsLisenter = new MessageListener() {
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
							}finally{
								CloseUtil.close(os);
							}
						}
					} catch (Throwable e) {
						DebugUtil.error(e);
					}
				}
			}
		};
		this.createConnection();
		for (JMSConsumerListener listener : listeners) {
			Destination destination;
			if (this.topic) {
				destination = new ActiveMQTopic(listener.getChannel());
			} else {
				destination = new ActiveMQQueue(listener.getChannel());
			}
			Session session = connection.createSession(listener.isTransacted(), listener.getSessionType());
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(jmsLisenter);
		}
	}
}