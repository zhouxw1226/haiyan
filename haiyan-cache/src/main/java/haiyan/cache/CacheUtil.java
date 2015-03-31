package haiyan.cache;

import haiyan.common.DebugUtil;
import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Cache Facade
 * 
 * @author zhouxw
 * 
 */
public class CacheUtil {
	private static IDataCache CACHE;
	public static final void setDataCache(IDataCache c) {
		CACHE = c;
	}
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
	public static Object setData(String tbl, Object id, Object o) {
		return getDataCache().setData(tbl, id, o);
	}
	public static Object getData(String tbl, Object id) {
		return getDataCache().getData(tbl, id);
	}
	public static Object setLocalData(String cacheID, Object id, Object o) {
		return getDataCache().setLocalData(cacheID, id, o);
	}
	public static Object updateData(String tbl, Object id, Object o) {
		return getDataCache().updateData(tbl, id, o);
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
//	// -------------------- list opr --------------------//
//	public static Object setListData(String cacheID, Object id, Object o) {
//		return getDataCache().setLocalData(cacheID, id, o);
//	}
//	public static Object getListData(String cacheID, Object id) {
//		return getDataCache().getLocalData(cacheID, id);
//	}
//	public static boolean addListData(String cacheID, Object o) {
//		return getDataCache().addListData(cacheID, o);
//	}
//	public static boolean removeListData(String cacheID, Object o) {
//		return getDataCache().removeListData(cacheID, o);
//	}
//	public static int getDataSize(String cacheID) {
//		return getDataCache().getDataSize(cacheID);
//	}
//	public static int getIndexOf(String cacheID, Object o) {
//		return getDataCache().getIndexOf(cacheID, o);
//	}
//	public static int getLastIndexOf(String cacheID, Object o) {
//		return getDataCache().getLastIndexOf(cacheID, o);
//	}
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
	private static Map<String,IUser> USER_CACHE = new HashMap<String,IUser>();
	public static IUser setUser(String sessionId, IUser user) {
		USER_CACHE.put(sessionId, user);
		return getDataCache().setUser(sessionId, user);
	}
	public static IUser getUser(String sessionId) {
		IUser user = (IUser) USER_CACHE.get(sessionId);
		if (user != null) {
			return user;
		}
		user = getDataCache().getUser(sessionId);
		DebugUtil.debug("getDataCache().getUser(sessionId),sessionId="
				+ sessionId + ",user=" + user);
		if (user != null) {
			USER_CACHE.put(sessionId, user);
		}
		return user;
	}
	public static void removeUser(String sessionId) {
		USER_CACHE.remove(sessionId);
		getDataCache().removeUser(sessionId);
	}
	public static boolean containsUser(String sessionId) {
		if (USER_CACHE.containsKey(sessionId))
			return true;
		return getDataCache().containsUser(sessionId);
	}
	@Deprecated
	public static IUser[] getAllUsers() {
		List<IUser> users = new ArrayList<IUser>();
		Iterator<String> iter = USER_CACHE.keySet().iterator();
		while (iter.hasNext()) {
			String sessionId = (String) iter.next();
			users.add((IUser) USER_CACHE.get(sessionId));
		}
		return (IUser[]) users.toArray(new IUser[0]);
	}
	@Deprecated
	public static IUser getUserByCode(String userCode) {
		IUser[] users = getAllUsers();
		for (IUser user : users) {
			if (userCode.equals(user.getCode()))
				return user;
		}
		return getDataCache().getUserByCode(userCode);
	}
	@Deprecated
	public static IUser getUserByID(String userID) {
		IUser[] users = getAllUsers();
		for (IUser user : users) {
			if (userID.equals(user.getId()))
				return user;
		}
		return getDataCache().getUserByID(userID);
	}

}