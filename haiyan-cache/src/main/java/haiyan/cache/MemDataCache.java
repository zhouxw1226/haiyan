//package haiyan.cache;
//
//import com.danga.MemCached.MemCachedClient;
//import com.danga.MemCached.SockIOPool;
//
//
///**
// * @author zhouxw
// *
// */
//public class MemDataCache extends EHDataCache {
//
//	private MemCachedClient mcc = new MemCachedClient();
//	
//    private String[] servers = null;
//    
//    public MemDataCache() {
//    	super();
//    }
//    
//    public void setServers(String[] servers) {
//    	this.servers = servers;
//    }
//    
//    public void initialize() {
//    	super.initialize();
//    	// 设置服务器权重
//		Integer[] weights = new Integer[this.servers.length];
//		for (int i=0;i<weights.length;i++) {
//			weights[i]=i+1;
//		}
//		// 创建一个Socked连接池实例
//		SockIOPool pool = SockIOPool.getInstance();
//		// 向连接池设置服务器和权重
//		pool.setServers(servers);
//		pool.setWeights(weights);
//		// set some TCP settings
//		// disable nagle
//		// set the read timeout to 3 secs
//		// and don't set a connect timeout
//		pool.setNagle(false);
//		pool.setSocketTO(3000);
//		pool.setSocketConnectTO(0);
//		// initialize the connection pool
//		pool.initialize();
//    }
//
////	private Object getRemoteCache(String k) {
////		try {
////			return mcc.get(k);
////		} catch(Throwable e) {
////			mcc.delete(k);
////			throw new RuntimeException(e);
////		}
////	}
//    
//    // --------------------- user cache --------------------- //
//
////	private String getUserIndexKey() {
////    	return "Haiyan.USERS.__index";
////    }
//    
//	private String getUserKey(String cacheID) {
//    	return "Haiyan.USERS."+cacheID;
//    }
//
////		mcc.flushAll();
//		// NOTICE 用index不能保证多服务器之间的index数据同步
////		k = getUserIndexKey();
////		HashSet<String> h = (HashSet)mcc.get(k);
////		if (h==null)
////			synchronized(mcc) {
////				if (h==null) {
////					h = new HashSet<String>();
////				}
////			}
////		h.add(sessionId);
////		mcc.set(k, h);
//
//	public IUser setUser(String sessionId, IUser user) {
//		super.setUser(sessionId, user);
//		String k = getUserKey(sessionId);
//		mcc.set(k, user);
//		mcc.set(k+"._status", 0); // login
//		return user;
//	}
//
//	public IUser getUser(String sessionId) {
//		IUser user = super.getUser(sessionId);
//		String k = getUserKey(sessionId);
//		Integer status = (Integer)mcc.get(k+"._status");
//		if (status == -1) { // is logout
//			// mcc.delete(k+"._status"); NOTICE 不能加，有些服务器还没通知完
//			super.removeUser(sessionId);
//			return null;
//		}
//		if (user==null) {
//			user = (IUser)mcc.get(k);
//			if (user!=null)
//				super.setUser(sessionId, user);
//		}
//		return user;
//	}
//
//	public boolean removeUser(String sessionId) {
//		super.removeUser(sessionId);
//		String k = getUserKey(sessionId);
//		boolean b = mcc.delete(k);
//		mcc.set(k+"._status", -1); // logout
//		return b;
//	}
//
////	public IUser[] getAllUsers() {
////		throw new Warning("Not Support");
////	}
////
////	public IUser getUserByCode(String userCode) {
////		throw new Warning("Not Support");
////	}
////
////	public IUser getUserByID(String userID) {
////		throw new Warning("Not Support");
////	}
////	
////	public int sizeOfUser() {
////		throw new Warning("Not Support");
////    }
//
//    // --------------------- data cache --------------------- //
//	
//	private String getDataKey(String cacheID, Object key) {
//		return "Haiyan.DATAS." + cacheID+":"+key;
//	}
//
//	public Object setData(String cacheID, Object key, Object ele) {
//		String mk = getDataKey(cacheID, key);
//		super.setLocalData(cacheID, key, ele);
//		mcc.set(mk, ele);
//		return ele;
//	}
//	  
//	public Object getData(String cacheID, Object key) {
//		String mk = getDataKey(cacheID, key);
//		Integer v = (Integer)super.getLocalData(cacheID, key+"._version");
//		Integer mv = (Integer)mcc.get(mk+"._version");
//		if (v==null || (mv!=null && v.intValue()<mv.intValue())) {
//			Object mo = mcc.get(mk);
//			if (mo!=null) {
//				super.setLocalData(cacheID, key+"._version", mv);
//				return super.setLocalData(cacheID, key, mo);
//			}
//			return null;
//		}
//		Object o = super.getLocalData(cacheID, key);
//		if (o!=null) {
//			return o;
//		}
//		Object mo = mcc.get(mk);
//		if (mo!=null) {
//			if (mv==null)
//				mv = 0;
//			super.setLocalData(cacheID, key+"._version", mv);
//			return super.setLocalData(cacheID, key, mo);
//		}
//		return null;
//	}
//	
//	public Object updateData(String cacheID, Object key, Object ele) {
//		String mk = getDataKey(cacheID, key);
//		Integer v = (Integer)super.getLocalData(cacheID, key+"._version");
//		Integer mv = (Integer)mcc.get(mk+"._version");
//		if (v!=null && mv!=null && v.intValue()!=mv.intValue()) {
//			throw new Warning(VERSION_WARNING);
//		}
//		mcc.set(mk, ele); // update cluster
//		super.setLocalData(cacheID, key, ele); // update local
//		{
//			if (mv==null)
//				mv = 0;
//			mv++;
//			mcc.set(mk+"._version", mv);
//			super.setLocalData(cacheID, key+"._version", mv);
//		}
//		return ele;
//	}
//	
//	public Object deleteData(String cacheID, Object key) {
//		String mk = getDataKey(cacheID, key);
//		Integer v = (Integer)super.getLocalData(cacheID, key+"._version");
//		Integer mv = (Integer)mcc.get(mk+"._version");
//		if (v!=null && mv!=null && v.intValue()!=mv.intValue()) {
//			throw new Warning(VERSION_WARNING);
//		}
//	    mcc.delete(mk); // update cluster
//	    Object o = super.removeLocalData(cacheID, key); // update local
//		{
//			if (mv==null)
//				mv = 0;
//			mv++;
//			mcc.set(mk+"._version", mv);
//			super.removeLocalData(cacheID, key+"._version");
//		}
//	    return o;
//    }
//	
//	public final static String VERSION_WARNING = "当前单据数据已被修改,请重新打开当前单据后继续操作.";
//	
//}
