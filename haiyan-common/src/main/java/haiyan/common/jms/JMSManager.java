package haiyan.common.jms;

import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import haiyan.common.PropUtil;
import haiyan.common.intf.session.ISession;

public abstract class JMSManager implements ISession {
	protected Enum<?> subject; // "CHANNEL.DEFAULT";
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
	protected Connection connection;
	protected static PooledConnectionFactory PCF;
	private synchronized PooledConnectionFactory getPoolFactory() {
		if (PCF == null)
			synchronized (JMSManager.class) {
				if (PCF == null) {
					ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
							this.getUser(), this.getPassword(), this.getUrl());
					connectionFactory.setSendTimeout(this.soTimeout);
					connectionFactory.setCloseTimeout(this.soTimeout);
					connectionFactory.setUseAsyncSend(true);
					connectionFactory.setUseCompression(true);
					// connectionFactory.setMaxThreadPoolSize(maxThreadPool);
					PCF = new PooledConnectionFactory(connectionFactory);
				}
			}
		return PCF;
	}
	private String DSN;
	public JMSManager() {
	}
	public JMSManager(String DSN) {
		this.DSN = DSN;
	}
	public String getDSN() {
		return DSN;
	}
	protected String getName() {
		return getClass().getCanonicalName();
	}
	protected void createConnection() throws Throwable {
		this.connection = getPoolFactory().createConnection();
		//this.connection.setClientID(UUID.randomUUID().toString());
		this.connection.start();
	}
	@Override
	public Boolean isAlive() { 
		return this.connection!=null;
	}
	@Override
	public void close() {
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
		this.connection = null;
	}
	public abstract void execute() throws Throwable;
	// ========================================================================================= //
	protected String url = PropUtil.getProperty("server.soa.jmsurl", "tcp://0.0.0.0:61616");
	protected String user = PropUtil.getProperty("server.soa.jmsuser", ActiveMQConnection.DEFAULT_USER);
	protected String password = PropUtil.getProperty("server.soa.jmspass", ActiveMQConnection.DEFAULT_PASSWORD);
	public String getUrl() {
		return url;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	// ========================================================================================= //
	public Enum<?> getSubject() {
		return subject;
	}
	public void setSubject(Enum<?> subject) {
		this.subject = subject;
	}
	public boolean isTopic() {
		return topic;
	}
	public void setTopic(boolean topic) {
		this.topic = topic;
	}
	// ========================================================================================= //
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
//	public void setUrl(String url) {
//		this.url = url;
//	}
//	public void setUser(String user) {
//		this.user = user;
//	}
//	public void setPassword(String pass) {
//		this.password = pass;
//	}
//	public abstract void setUrl(String url);
//	public abstract void setUser(String user);
//	public abstract void setPassword(String pass);
}