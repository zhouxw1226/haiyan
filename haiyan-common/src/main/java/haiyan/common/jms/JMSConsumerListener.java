package haiyan.common.jms;

import javax.jms.Message;

public abstract interface JMSConsumerListener {
	public abstract void execute(Message message, Object content) throws Throwable;
	public abstract Enum<?> getSubject();
	public abstract int getSessionType();
	public abstract boolean isTransacted();
}