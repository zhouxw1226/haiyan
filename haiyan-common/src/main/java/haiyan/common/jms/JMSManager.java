package haiyan.common.jms;

import haiyan.common.PropUtil;
import haiyan.common.intf.session.ISession;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

public abstract class JMSManager implements ISession {

	protected String url = PropUtil.getProperty("server.soa.jmsurl","tcp://0.0.0.0:61616");
	protected String user = PropUtil.getProperty("server.soa.jmsuser",ActiveMQConnection.DEFAULT_USER);
	protected String password = PropUtil.getProperty("server.soa.jmspass",ActiveMQConnection.DEFAULT_PASSWORD);
	protected String subject = "CHANNEL.DEFAULT";
	protected long sleepTime;
	protected long timeToLive = 3600000L;
	protected int soTimeout = 5000;
//	protected int maxThreadPool = 1000;
//	protected static int parallelThreads = 1;
	protected boolean verbose = false;
	protected boolean topic = false;
	//在事务状态下进行发送操作，消息并未真正投递到中间件，而只有进行session.commit操作之后，消息才会发送到中间件
	protected boolean transacted = true;
	protected boolean persistent = true;
	protected Destination destination;
	protected Session session;
	protected Connection connection;
	protected static PooledConnectionFactory PCF;
	private synchronized PooledConnectionFactory getPoolFactory() {
		if (PCF==null)
			synchronized(JMSManager.class) {
				if (PCF == null) {
					ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
							this.user, this.password, this.url);
					connectionFactory.setSendTimeout(this.soTimeout);
					connectionFactory.setCloseTimeout(this.soTimeout);
					connectionFactory.setUseAsyncSend(true);
					connectionFactory.setUseCompression(true);
//					connectionFactory.setMaxThreadPoolSize(maxThreadPool);
					PCF = new PooledConnectionFactory(connectionFactory);
				}
			}
		return PCF;
	}
	protected String getName() {
		return getClass().getCanonicalName();
	}
	protected void createConnection() throws Throwable {
		this.connection = getPoolFactory().createConnection();
		this.connection.start();
	}
	private void createSession(int sessionType) throws Throwable {
		//Session.AUTO_ACKNOWLEDGE 处理完消息自动确认
		//Session.CLIENT_ACKNOWLEDGE 处理完消息手动确认
		//Session.DUPS_OK_ACKNOWLEDGE 批量确认
		//Session.SESSION_TRANSACTED 开启同步事务
		this.session = this.connection.createSession(this.transacted, sessionType<0?Session.CLIENT_ACKNOWLEDGE:sessionType);
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
		if (this.session != null)
			try {
				this.session.close();
			} catch (Throwable ignore) {
			}
		if (this.connection != null)
			try {
				this.connection.stop();
			} catch (Throwable ignore) {
			}
		if (this.connection != null)
			try {
				this.connection.close();
			} catch (Throwable ignore) {
			}
		this.destination = null;
		this.session = null;
		this.connection = null;
	}
	public void openTransaction(int sessionType) throws Throwable {
		this.transacted = true;
		this.createConnection();
		this.createSession(sessionType);
	}
	public void openConnection(int sessionType) throws Throwable {
		this.transacted = false;
		this.createConnection();
		this.createSession(sessionType);
	}
	@Override
	public Boolean isAlive() { 
		return this.connection!=null && this.session!=null;
	}
	public abstract void execute() throws Throwable;
	// ========================================================================================= //
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public long getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	public long getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}
	public int getSoTimeout() {
		return soTimeout;
	}
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	public boolean isVerbose() {
		return verbose;
	}
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	public boolean isTopic() {
		return topic;
	}
	public void setTopic(boolean topic) {
		this.topic = topic;
	}
	public boolean isTransacted() {
		return transacted;
	}
	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}
	public boolean isPersistent() {
		return persistent;
	}
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
	
}
