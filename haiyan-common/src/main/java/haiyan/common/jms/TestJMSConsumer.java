package haiyan.common.jms;

import haiyan.common.DebugUtil;

import javax.jms.Message;
import javax.jms.Session;

public class TestJMSConsumer {

	public static void main(String[] args) {
		for (int i=0;i<500;i++) {
			test();
		}
	}
	public static void test() {
		//admin activemq
		JMSConsumer consumer = new JMSConsumer();
		consumer.setUrl("tcp://0.0.0.0:61616"); // "nio://127.0.0.1:61616"
		consumer.setUser("admin");
		consumer.setPassword("activemq");
		JMSConsumerListener listener = new JMSConsumerListener() {
			private int i=0;
			@Override
			public void execute(Message message, Object content) throws Throwable {
				System.out.println((i++)+" "+content+" "+message.getStringProperty(JMSMessage.ACTION));
				message.acknowledge();
			}
			@Override
			public String getChannel() {
				return "test";
			}
			@Override
			public boolean isTransacted() {
				return false;
			}
			@Override
			public int getSessionType() {
				return Session.CLIENT_ACKNOWLEDGE;
			}
		};
		consumer.addJMSListener(listener);
//		consumer.addJMSListener(listener);
//		consumer.addJMSListener(listener);
		try {
			consumer.execute();
		} catch (Throwable e) {
			consumer.close();
			DebugUtil.error(e);
		}
	}
}