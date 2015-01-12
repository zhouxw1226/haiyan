package haiyan.common.intf.session;

/**
 * DB会话(UnitOfWork)
 * 
 * @author ZhouXW
 *
 */
public interface IDBSession extends ISession {

	void openTransaction() throws Throwable;
	void closeTransaction() throws Throwable;
	void setAutoCommit(boolean b) throws Throwable;
	void commit() throws Throwable;
	void rollback() throws Throwable;
	//void close();
	void clear();
	String getDSN();
	
}
