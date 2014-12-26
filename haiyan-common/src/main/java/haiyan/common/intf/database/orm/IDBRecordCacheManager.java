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
	
	public static final short CONTEXT_SESSION = 0; // 默认缓存（Context.DBM缓存单例）
	public static final short THREAD_SESSION = 1; // 一级缓存（ThreadLocal缓存单例）可以用ThreadLocal实现请参考UnitOfWork的实现
	public static final short APP_SESSION = 2; // 二级缓存(App全局缓存单例)
	public static final short PERSIST_SESSION = 3; // 持久化缓存（Cache、DB缓存实例）
	public static final short USERDEFINE_SESSION = 4; // 自定义缓存
	void clear(); // 清理所用到的所有资源
	void commit() throws Throwable; // 提交缓存
	void rollback() throws Throwable; // 回滚缓存
	void clearCache(String[] regTables); // 清理指定表的缓存数据

}
