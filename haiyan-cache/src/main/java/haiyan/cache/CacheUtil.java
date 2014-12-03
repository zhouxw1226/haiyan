package haiyan.cache;

import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache Facade
 * 
 * @author zhouxw
 * 
 */
public class CacheUtil {
	private static IDataCache CACHE;
	public static final IDataCache getDataCache() {
		if (CACHE == null)
			synchronized (CacheUtil.class) {
				if (CACHE == null) {
					try {
//						if ("memcache".equalsIgnoreCase(PropUtil.getProperty("CACHE_TYPE"))) {
//							cache = new MemDataCache();
//							String[] servers = PropUtil.getProperty("CACHE_SERVERS").split(",");
//							cache.setServers(servers);
//						} else 
						{
							CACHE = new EHDataCache();
						}
						CACHE.initialize();
					}catch(Throwable e){
						throw new RuntimeException(e);
					}
				}
			}
		return CACHE;
	}
	public static final void setDataCache(IDataCache c) {
		CACHE = c;
	}
	public static Object setData(String tbl, Object id, Object form) {
		return getDataCache().setData(tbl, id, form);
	}
	public static Object getData(String tbl, Object id) {
		return getDataCache().getData(tbl, id);
	}
	public static Object setLocalData(String cacheID, Object id, Object o) {
		return getDataCache().setLocalData(cacheID, id, o);
	}
	public static Object updateData(String tbl, Object id, Object form) {
		return getDataCache().updateData(tbl, id, form);
	}
	public static Object deleteData(String tbl, Object id) {
		return getDataCache().deleteData(tbl, id);
	}
	public static Object removeLocalData(String tbl, Object id) {
		return getDataCache().removeLocalData(tbl, id);
	}
	public static void clearData(String key) {
		getDataCache().clearData(key);
	}
	// -------------------- list opr --------------------//
	public static Object setListData(String cacheID, Object id, Object o) {
		return getDataCache().setLocalData(cacheID, id, o);
	}
	public static Object getListData(String cacheID, Object id) {
		return getDataCache().getLocalData(cacheID, id);
	}
	public static boolean addListData(String cacheID, Object o) {
		return getDataCache().addListData(cacheID, o);
	}
	public static boolean removeListData(String cacheID, Object o) {
		return getDataCache().removeListData(cacheID, o);
	}
	public static int getDataSize(String cacheID) {
		return getDataCache().getDataSize(cacheID);
	}
	public static int getIndexOf(String cacheID, Object o) {
		return getDataCache().getIndexOf(cacheID, o);
	}
	public static int getLastIndexOf(String cacheID, Object o) {
		return getDataCache().getLastIndexOf(cacheID, o);
	}
	// -------------------- table --------------------//
	private static Map<String,ITableConfig> TABLE_CACHE = new HashMap<String,ITableConfig>();
	public static void setTable(String tbl, ITableConfig table) {
		TABLE_CACHE.put(tbl, table);
		//getCache().setTable(tbl, table);
	}
	public static ITableConfig getTable(String tbl) {
		return TABLE_CACHE.get(tbl);
//		return getCache().getTable(tbl);
	}
	public static String[] getAllTableKeys() {
		return TABLE_CACHE.keySet().toArray(new String[0]);
//		return getCache().getAllTableKeys();
	}
	public static boolean removeTable(String tbl) {
		TABLE_CACHE.remove(tbl);
		return true;
//		return getCache().removeTable(tbl);
	}
	public static boolean removeTableFile(String tbl) {
		return getDataCache().removeTableFile(tbl);
	}
	public static void setTableFile(String tbl, File f) {
		getDataCache().setTableFile(tbl, f);
	}
	public static File getTableFile(String tbl) {
		return getDataCache().getTableFile(tbl);
	}
	// -------------------- user --------------------//
	public static IUser setUser(String userID, IUser user) {
		return getDataCache().setUser(userID, user);
	}
	public static IUser getUser(String sessionId) {
		return getDataCache().getUser(sessionId);
	}
	public static IUser[] getAllUsers() {
		return getDataCache().getAllUsers();
	}
	public static void removeUser(String sessionId) {
		getDataCache().removeUser(sessionId);
	}
	public static boolean containsUser(String sessionId) {
		return getDataCache().containsUser(sessionId);
	}
	public static IUser getUserByCode(String userCode) {
		return getDataCache().getUserByCode(userCode);
	}
	public static IUser getUserByID(String userID) {
		return getDataCache().getUserByID(userID);
	}

}
