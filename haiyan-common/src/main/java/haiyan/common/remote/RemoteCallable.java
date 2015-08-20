package haiyan.common.remote;

import java.io.Closeable;

public abstract interface RemoteCallable extends Closeable {
	public abstract void execute() throws Throwable;
	void commit() throws Throwable;
	void rollback() throws Throwable;
}