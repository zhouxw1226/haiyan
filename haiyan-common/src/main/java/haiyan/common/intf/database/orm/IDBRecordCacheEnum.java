package haiyan.common.intf.database.orm;

public enum IDBRecordCacheEnum {
	CONTEXT_SESSION, // 默认缓存（Context.DBM缓存单例）
	THREAD_SESSION, // 一级缓存（ThreadLocal缓存单例）可以用ThreadLocal实现请参考UnitOfWork的实现
	APP_SESSION, // 二级缓存(App全局缓存单例)
	PERSIST_SESSION, // 持久化缓存（Cache、DB缓存实例）
	USERDEFINE_SESSION // 自定义缓存
}
