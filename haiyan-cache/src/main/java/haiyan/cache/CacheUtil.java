package haiyan.cache;

import haiyan.common.DebugUtil;
import haiyan.common.PropUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.SessionMap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Cache Facade
 * 
 * @author zhouxw
 * 
 */
public class CacheUtil {
	private static Map<String,IDataCache> CACHE_DSN = new HashMap<String,IDataCache>();
	public static final void setDataCache(String DSN, IDataCache c) { // IOC
		CACHE_DSN.put(DSN, c);
	}
	public static final IDataCache getDataCache(String DSN) { 
		return CACHE_DSN.get(DSN);
	}
	private static IDataCache CACHE;
	public static final void setDataCache(IDataCache c) { // IOC
		CACHE = c;
	}
	public static final IDataCache getDataCache() {
		if (CACHE == null)
			synchronized (CacheUtil.class) {
				if (CACHE == null) {
					try {
						CACHE = new EHDataCache(); // 默认的Cache引擎
						CACHE.initialize();
					}catch(Throwable e){
						throw new RuntimeException(e);
					}
				}
			}
		return CACHE;
	}
	private static class CacheBean {
		CacheBean(String schema, Object id, Object val) {
			this.schema = schema;
			this.id = id;
			this.val = val;
		}
		String schema;
		Object id;
		Object val;
	}
	private static Lock LOCK = new ReentrantLock();
	private static Condition CONDITION = LOCK.newCondition();
	private static Queue<CacheBean> QUEUE = new ArrayBlockingQueue<CacheBean>(1024*1024, false);
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
			20,
            100,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1000));
	static {
		pool.execute(new Runnable(){
			@Override
			public void run() {
				while(true) {
					if (QUEUE.size()==0) {
						LOCK.lock();
						try {
							CONDITION.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							LOCK.unlock();
						}
					}
					CacheBean bean = QUEUE.poll();
					if (bean == null) {
						LOCK.lock();
						try {
							CONDITION.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							LOCK.unlock();
						}
					} else {
						String schema = bean.schema;
						Object id = bean.id;
						Object val = bean.val;
						if (val!=null)
							getDataCache().setData(schema, id, val);
					}
				}
			}
		});
	}
//	private static String getKey(String schema, Object id) {
//		return schema+"_"+id;
//	}
	public static Object deleteData(String schema, Object id) {
//		String key = getKey(schema, id);
//		if (QUEUE.contains(key)) {
//			QUEUE.remove();
//		}
		return getDataCache().deleteData(schema, id);
	}
	public static Object updateData(String schema, Object id, Object o) {
		return getDataCache().updateData(schema, id, o);
	}
//	// 缓存对象只要一个在队列里即可
//	public static Object setData(String schema, Object id, Object o, boolean newAlways) {
//		String key = getKey(schema,id);
//		if (newAlways!=true) {
//			return o;
//		}
//		QUEUE.add(new CacheBean(schema, id, o));
//		return o;
//		//return getDataCache().setData(schema, id, o);
//	}
	// 缓存对象只要一个在队列里即可
	public static Object setData(String schema, Object id, Object o) {
//		String key = getKey(schema,id);
//		if (newAlways!=true) {
//			return o;
//		}
		QUEUE.add(new CacheBean(schema, id, o));
		LOCK.lock();
		try {
			CONDITION.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LOCK.unlock();
		}
		return o;
//		//return getDataCache().setData(schema, id, o);
	}
	public static Object getData(String schema, Object id) {
		return getDataCache().getData(schema, id);
	}
	public static Object setLocalData(String schema, Object id, Object o) {
		return getDataCache().setLocalData(schema, id, o);
	}
	public static Object getLocalData(String schema, Object id) {
		return getDataCache().getLocalData(schema, id);
	}
	public static Object removeLocalData(String schema, Object id) {
		return getDataCache().removeLocalData(schema, id);
	}
	public static void clearData(String schema) {
		getDataCache().clearData(schema);
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
//		getCache().setTable(tbl, table);
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
	private static int USER_CACHE_COUNT = 5000;
	static {
		int count = VarUtil.toInt(PropUtil.getProperty("cache.usercount"));
		if (count<=0)
			count = 5000;
		USER_CACHE_COUNT = count;
	}
	private static Map<String, Object> USER_CACHE = new SessionMap<String, IUser>() {
		private static final long serialVersionUID = 1L;
		@Override
		protected boolean isOverTime(String key) {
			if (this.size()<USER_CACHE_COUNT) // USER_CACHE_COUNT个以内不需要清理
				return false;
			return System.currentTimeMillis() - ((Long) this.sessions.get(key)).longValue() > overTime;
		}
		
		@Override
		protected void closeOvertime() {
			List<String> list = new ArrayList<String>();
			Iterator<String> iter = this.sessions.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (isOverTime(key)) {
					// iter.remove();
					// remove(key);
					list.add(key);
				}
			}
			synchronized (this) {
				for (String key : list) {
					this.remove(key);
					CacheUtil.removeUser(key);
				}
			}
		}
	};
	public static final String USER_REDISDSN = "USER"; 
	public static boolean USER_REDIS = false;
	private static boolean USE_LOCAL_CACHE = true; // 是否使用本地缓存
	public static boolean isUseLocalCache() {
		return USE_LOCAL_CACHE;
	}
	public static void setUseLocalCache(boolean useLocalCache) {
		USE_LOCAL_CACHE = useLocalCache;
	}
	private static IDataCache getUserDataCache() {
		IDataCache dc = null;
		if (USER_REDIS)
			dc = getDataCache(USER_REDISDSN);
		else
			dc = getDataCache();
		return dc;
	}
	public static IUser setUser(String sessionId, IUser user) {
		if (USE_LOCAL_CACHE) {
			USER_CACHE.put(sessionId, user);
		}
		IDataCache dc = getUserDataCache();
		return dc.setUser(sessionId, user);
	}
	public static IUser getUser(String sessionId) {
		IUser user = null;
		if (USE_LOCAL_CACHE) {
			user = (IUser) USER_CACHE.get(sessionId);
			if (user != null) {
				return user;
			}
		}
		IDataCache dc = getUserDataCache();
		user = dc.getUser(sessionId);
		DebugUtil.debug("CacheUtil.getUser(sessionId),sessionId=" + sessionId + ",user=" + user);
		if (USE_LOCAL_CACHE && user != null) {
			USER_CACHE.put(sessionId, user);
		}
		return user;
	}
	public static void removeUser(String sessionId) {
		if (USE_LOCAL_CACHE) {
			USER_CACHE.remove(sessionId);
		}
		IDataCache dc = getUserDataCache();
		dc.removeUser(sessionId);
	}
	public static boolean containsUser(String sessionId) {
		if (USE_LOCAL_CACHE && USER_CACHE.containsKey(sessionId)) {
			return true;
		}
		IDataCache dc = getUserDataCache();
		return dc.containsUser(sessionId);
	}
	@Deprecated
	public static IUser getUserByCode(String userCode) {
		IUser[] users = getAllUsers();
		for (IUser user : users) {
			if (userCode.equals(user.getCode()))
				return user;
		}
		IDataCache dc = getUserDataCache();
		return dc.getUserByCode(userCode);
	}
	@Deprecated
	public static IUser getUserByID(String userID) {
		IUser[] users = getAllUsers();
		for (IUser user : users) {
			if (userID.equals(user.getId()))
				return user;
		}
		IDataCache dc = getUserDataCache();
		return dc.getUserByID(userID);
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

}