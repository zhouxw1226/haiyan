package haiyan.common.intf.database.orm;

public interface IDBRecordCacheManager {
	
	public static final short DEFAULTSESSION = 0;
	public static final short THREADSESSION = 1; // 一级缓存可以用ThreadLocal实现请参考UnitOfWork的实现
	public static final short DBSESSION = 2;
	public static final short USESESSION = 3;
	public static final short APPSESSION = 4;
	public static final short WEBSESSION = 5;
	void clear(); 
	void commit() throws Throwable;
	void rollback() throws Throwable;
	void clearCache(String[] regTables);

}
