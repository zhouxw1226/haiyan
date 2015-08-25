package haiyan.orm.database;

import haiyan.common.StringUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCacheManager;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.intf.database.orm.ITableDBRecordCacheManager;
import haiyan.orm.intf.session.ITableDBContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author ZhouXW
 *
 */
public class TableDBRecordCacheFactory {

	private static final String DSN_DEMI = "@";
	private static final String KEY_DEMI = ":";
	public static final short parse(String name) {
		if ("context".equalsIgnoreCase(name)) {
			return IDBRecordCacheManager.CONTEXT_SESSION;
		}
		else if ("thread".equalsIgnoreCase(name)) {
			return IDBRecordCacheManager.THREAD_SESSION;
		}
		else if ("app".equalsIgnoreCase(name)) {
			return IDBRecordCacheManager.APP_SESSION;
		}
		else if ("persist".equalsIgnoreCase(name)) {
			return IDBRecordCacheManager.PERSIST_SESSION;
		}
		else if ("userdefine".equalsIgnoreCase(name)) {
			return IDBRecordCacheManager.USERDEFINE_SESSION;
		}
		throw new RuntimeException("this cachetype:"+name+" not support");
	}
	// ITableRecordCacheManager(IDBRecordCacheManager)
	// Context RecordCacheManager
	private static ITableDBRecordCacheManager createRecordCacheManager() {
		return new ITableDBRecordCacheManager() { // 模拟了在内存中的ACID
			// 跟着DBSession走，DBSession既是IDBManager
			protected transient Map<String,IDBRecord> transaction = new HashMap<String,IDBRecord>(); // ConcurrentHashMap 
			@Override
			public void removeCache(ITableDBContext context, Table table, String[] ids) throws Throwable {
				if (!ConfigUtil.isORMUseCache() || table.getName().toUpperCase().startsWith("V_"))
					return;
				// 要注意直接执行SQL语句的操作对缓冲数据的影响
				String DSN = context.getDSN();
				String tableName = table.getName();
				if (!StringUtil.isBlankOrNull(DSN))
					tableName += DSN_DEMI + DSN;
				for (String id : ids) {
					//CacheUtil.removeData(tableName, IDVal);
					this.transaction.put(tableName+KEY_DEMI+id, null);
				}
				String[] linkedTables = ConfigUtil.getSameDBTableNames(table.getName());
				for (String linkedTable : linkedTables) {
					if (linkedTable.equalsIgnoreCase(table.getName()))
						continue;
					tableName = linkedTable;
					if (!StringUtil.isBlankOrNull(DSN))
						tableName += DSN_DEMI + DSN;
					for (String id : ids) {
						//CacheUtil.removeData(tableName, IDVal);
						this.transaction.put(tableName+KEY_DEMI+id, null);
					}
				}
			}
			@Override
			public void updateCache(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
				if (!ConfigUtil.isORMUseCache() || table.getName().toUpperCase().startsWith("V_"))
					return;
				// 双缓冲：更新缓存后能提高DB->VO的转换效率
				// 要注意直接执行SQL语句的操作对缓冲数据的影响
				String DSN = context.getDSN();
				String tableName = table.getName();
				if (!StringUtil.isBlankOrNull(DSN))
					tableName += DSN_DEMI + DSN;
				Object obj = record.get(table.getId().getName());
				String id = null;
				if(!StringUtil.isBlankOrNull(obj)){
					id = obj.toString();
				}
				//if (type==IDBRecordCacheManager.CONTEXT_SESSION) {
					// record.setDirty(); // 只要update或者insert过重取  
					// dirty后doEditOne才能取到最新的for executePlugin
					// NOTICE 但同一个事务中会把可能会回滚修改的记录reget放到trasaction里...
					this.transaction.put(tableName+KEY_DEMI+id, record);
				//} else 
//				{ // load from db
//					record.clearDirty();
//					record.commit();
////					if (type == IDBRecordCacheManager.PERSIST_SESSION)
////						CacheUtil.setData(tableName, id, record); // create
//					// record.syncVersion();
//					// 需要轮询不同配置拿到最新的版本号
//					// 这样处理也可以必须在同一个配置中使用一个版本号
//				}
				// CacheUtil.setData(tableName, id, record);
				// // 删掉吧,至少用businessobj能获取autonaming
				// CacheUtil.removeData(tableName, id);
				String[] linkedTables = ConfigUtil.getSameDBTableNames(table.getName());
				for (String linkedTableName : linkedTables) {
					if (linkedTableName.equalsIgnoreCase(table.getName()))
						continue;
					if (!StringUtil.isBlankOrNull(DSN))
						linkedTableName += DSN_DEMI + DSN;
					IDBRecord linkedRecord = null;
//					if (type == IDBRecordCacheManager.PERSIST_SESSION)
//						linkedRecord = (IDBRecord) CacheUtil.getData(linkedTableName, id);
					linkedRecord = this.transaction.get(linkedTableName+KEY_DEMI+id);
					if (linkedRecord != null) {
						{ // 更新关联表数据
//							Iterator<String> iter = linkedRecord.keySet().iterator(); // NOTICE 不同配置字段不同
//							if (iter != null) {
//								String key;
//								while (iter.hasNext()) {
//									key = (String) iter.next();
//									if (record.contains(key))
//										linkedRecord.set(key, record.get(key));
//								}
//							}
//							if (type==CONTEXT_SESSION) {
//								//linkedRecord.setDirty(); // 只要update或者insert过重取 
//								// dirty后doEditOne才能取到最新的for executePlugin 
//								// NOTICE 但同一个事务中是会把可能会回滚修改的记录reget放到trasaction里...
//								//transaction.put(tableName+CACHE_DEMI+id, linkedRecord);
//							    CacheUtil.deleteData(linkedTableName, id); // remove最合适因为version变了
//							    transaction.remove(linkedTableName+CACHE_DEMI+id); // 说明要更新 其他关联缓存
//							} else { // load from db
//								linkedRecord.clearDirty();
//								linkedRecord.commit();
//					            CacheUtil.setData(linkedTableName, id, linkedRecord); // create
//								// linkedRecord.syncVersion();
//								// 需要轮询不同配置拿到最新的版本号
//								// 这样处理也可以必须在同一个配置中使用一个版本号
//							}
//							// CacheUtil.setData(tableName, id, linkedRecord);
						}
						{ // NOTICE remove最合适因为version变了 并且linkedRecord的字段结构和当前表可能不同
//							if (type == IDBRecordCacheManager.PERSIST_SESSION)
//								CacheUtil.deleteData(linkedTableName, id); // remove最合适因为version变了
						    //if (type==CONTEXT_SESSION)
							this.transaction.remove(linkedTableName+KEY_DEMI+id); // 说明要更新 其他关联缓存
						}
					}
				}
			}
			@Override
			public IDBRecord getCache(ITableDBContext context, Table table, String id) throws Throwable {
				if (!ConfigUtil.isORMUseCache() || table.getName().toUpperCase().startsWith("V_"))
					return null;
				// 双缓冲：要注意直接执行SQL语句的操作对缓冲数据的影响
				String DSN = context.getDSN();
				String tableName = table.getName();
				if (!StringUtil.isBlankOrNull(DSN))
					tableName += DSN_DEMI + DSN;
				// String id = rs.getString(1); // 只做索引
				String k = tableName+KEY_DEMI+id;
				//if (type == IDBRecordCacheManager.CONTEXT_SESSION)
				if (this.transaction.containsKey(k))
					return this.transaction.get(k);
//				if (type == IDBRecordCacheManager.PERSIST_SESSION) {
//					IDBRecord record = (IDBRecord) CacheUtil.getData(tableName, id);
//					//if (type == IDBRecordCacheManager.CONTEXT_SESSION)
//					this.transaction.put(k, record);
//					return record;
//				}
				return null;
			}
			@Override
			public void clearCache(String[] regTables) {
				if (!ConfigUtil.isORMUseCache())
					return;
//				if (type == IDBRecordCacheManager.PERSIST_SESSION)
//					for (String realTable : regTables) {
//						String[] linkedTables = ConfigUtil.getSameDBTableNamesByReal(realTable);
//						for (String t : linkedTables) {
//							CacheUtil.clearData(t);
//						}
//					}
			}
			@Override
			public void clear() {
				this.transaction.clear();
			}
			@Override
			public void commit() throws Throwable {
				Iterator<String> iter = this.transaction.keySet().iterator();
				String key;
//				String[] arr;
				IDBRecord record;
				while(iter.hasNext()) {
					key = iter.next();
//					arr = k.split(CACHE_DEMI); // [0]:cacheStore [1]:cacheKey
					record = this.transaction.get(key);
					if (record==null) { // deleted
//						if (type == IDBRecordCacheManager.PERSIST_SESSION)
//							CacheUtil.deleteData(arr[0], arr[1]);
					} else {
						record.commit();
						record.setDirty(); // setDirty()+setCache()和removeCache()都會reget
						//update和insert设置setdirty后只用removeData
						record.remove(DataConstant.HYFORMKEY);
//						if (type == IDBRecordCacheManager.PERSIST_SESSION)
//							CacheUtil.updateData(arr[0], arr[1], r);
					}
//					if ("SYSOPERATOR".equalsIgnoreCase(arr[0]) || "SYSORGA".equalsIgnoreCase(arr[0]))
//						RightUtil.clearOrgasOfUser(arr[1]);
				}
				this.transaction.clear();
			}
			@Override
			public void rollback() throws Throwable {
				Iterator<String> iter = this.transaction.keySet().iterator();
				String key;
//				String[] arr;
				IDBRecord record;
				while(iter.hasNext()) {
					key = iter.next();
//					arr = k.split(CACHE_DEMI);
					record = this.transaction.get(key);
					if (record==null) { // deleted
//						if (type == IDBRecordCacheManager.PERSIST_SESSION)
//						CacheUtil.deleteData(arr[0], arr[1]);
					} else {
						//CacheUtil.setRemoteDirty(a[0], a[1]);
						record.setDirty(); // setDirty()+setCache()和removeCache()都會reget
						//update和insert设置setdirty后只用removeData
						if (!record.rollback()) {
//							if (type == IDBRecordCacheManager.PERSIST_SESSION)
//								CacheUtil.removeLocalData(arr[0], arr[1]);
						} else {
			                record.remove(DataConstant.HYFORMKEY);
//			                if (type == IDBRecordCacheManager.PERSIST_SESSION)
//			                	CacheUtil.setLocalData(arr[0], arr[1], r);
						}
					}
				}
				this.transaction.clear();
			}
		};
	}
	private static ITableDBRecordCacheManager APP_LOCAL = null;
	private static ThreadLocal<ITableDBRecordCacheManager> THREAD_LOCAL = new ThreadLocal<ITableDBRecordCacheManager>();
	// NOTICE 这些缓存可以都是集群可用的
	public static ITableDBRecordCacheManager getCacheManager(short type) { 
		switch(type) {
		case IDBRecordCacheManager.CONTEXT_SESSION: // 绑定到当前上下文的缓存管理器实例（一个dbm一个缓存管理器）
			return createRecordCacheManager();
		case IDBRecordCacheManager.APP_SESSION: { // 全局一个缓存管理器实例
			if (APP_LOCAL==null) {
				synchronized(IDBRecordCacheManager.class) {
					if (APP_LOCAL==null)
						APP_LOCAL = createRecordCacheManager();
				}
			}
			return APP_LOCAL;
		}
		case IDBRecordCacheManager.THREAD_SESSION: { // 绑定到当前线程的缓存管理器实例（使用不当会造成内存泄漏）
			if (THREAD_LOCAL.get()==null) {
				synchronized(IDBRecordCacheManager.class) {
					if (THREAD_LOCAL.get()==null)
						THREAD_LOCAL.set(createRecordCacheManager());
				}
			}
			return THREAD_LOCAL.get();
		}
		case IDBRecordCacheManager.PERSIST_SESSION: // 从持久层管理器获取数据
			return null;
		case IDBRecordCacheManager.USERDEFINE_SESSION: // TODO from invoke cachemanager-instance
			return null;
		}
		return null;
	}
	private TableDBRecordCacheFactory() {}

}
