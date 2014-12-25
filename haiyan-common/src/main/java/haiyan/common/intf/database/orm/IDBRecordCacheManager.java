package haiyan.common.intf.database.orm;

/**
 * 默认context程序上下文缓存
 * 一级thread线程安全缓存
 * 二级app应用容器全局缓存
 * 三persist持久层缓存
 * 四级userdefine自定义缓存
 * 
 * @author ZhouXW
 *
 */
public interface IDBRecordCacheManager {
	
	public static final short CONTEXT_SESSION = 0;
	public static final short THREAD_SESSION = 1; // 一级缓存可以用ThreadLocal实现请参考UnitOfWork的实现
	public static final short APP_SESSION = 2;
	public static final short PERSIST_SESSION = 3;
	public static final short USERDEFINE_SESSION = 4;
	void clear(); 
	void commit() throws Throwable;
	void rollback() throws Throwable;
	void clearCache(String[] regTables);

}
