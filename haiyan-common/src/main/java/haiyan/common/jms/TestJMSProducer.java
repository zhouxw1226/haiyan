package haiyan.common.jms;

import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;

import javax.jms.Session;

public class TestJMSProducer {

	public static void main(String[] args) {
		for (int i=0;i<500;i++) {
			test();
		}
	}
	public static void test() {
		//admin activemq
		JMSProducer producer = new JMSProducer();
		producer.setUrl("tcp://0.0.0.0:61616"); // "nio://127.0.0.1:61616"
		producer.setUser("admin");
		producer.setPassword("activemq");
		producer.setSubject("test");
		JMSMessage s = new JMSMessage("test",JMSType.STRING,"action1");
		producer.addMessage(s);
		for (int i=0;i<1;i++)
			try {
//				producer.openTransaction(Session.AUTO_ACKNOWLEDGE);
				producer.openTransaction(Session.CLIENT_ACKNOWLEDGE);
//				producer.openConnection(Session.AUTO_ACKNOWLEDGE);
				producer.execute();
				producer.commit();
				System.out.println(i+" "+s);
			}catch(Throwable e){
				producer.rollback();
				DebugUtil.error(e);
			}finally {
				CloseUtil.close(producer);
			} 
	}
}
