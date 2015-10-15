package haiyan.cache;

import haiyan.common.DebugUtil;
import haiyan.common.FileUtil;
import haiyan.common.StringUtil;
import haiyan.common.config.PathUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author zhouxw
 *
 */
public class EHDataCache extends AbstractDataCache {

    // // <!--缓存可以存储的总记录量-->
    // private final int maxElementsInMemory = 10000;
    // // // <!--磁盘可以存储的总记录量-->
    // // private final int maxElementsOnDisk = 0;
    // // // <!--磁盘存储有效时间-->
    // // 磁盘失效线程运行时间间隔，默认是120秒
    // // private final int diskExpiryThreadIntervalSeconds = 600000;
    // // <!--缓存创建之后，到达该时间时则缓存自动销毁-->
    // // 最大时间介于创建时间和失效时间之间。仅当element不是永久有效时使用，默认是0，也就是element存活时间无穷大
    // private final int timeToLiveSeconds = 0;
    // // <!--当缓存闲置时间超过该值，则缓存自动销毁-->
    // // 仅当element不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大
    // private final int timeToIdleSeconds = 0;
    // // <!--缓存是否永远不销毁, 永久有效-->
    // private final boolean eternal = true;
    // // <!--当缓存中的数据达到最大值时，是否把缓存数据写入磁盘-->
    // private final boolean overflowToDisk = true;
    // // // <!--存储是否持久化在磁盘-->
    // // private final boolean diskPersistent = true;
    // // // <!--存储策略-->
    // memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。
    // // 默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
    // // 这里比较遗憾，Ehcache并没有提供一个用户定制策略的接口，仅仅支持三种指定策略，感觉做的不够理想
    // // private final String memoryStoreEvictionPolicy = "LRU";
    // xsi:noNamespaceSchemaLocation=\"../config/ehcache.xsd\"
    private static String normalXML;
    // = "<ehcache xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" updateCheck=\"false\">"
    // + "<diskStore path=\"java.io.tmpdir\" />"
    // + "<defaultCache" + " maxElementsInMemory=\"" + maxElementsInMemory + "\""
    // + " maxElementsOnDisk=\"" + maxElementsOnDisk + "\""
    // + " timeToIdleSeconds=\"" + timeToIdleSeconds + "\""
    // + " timeToLiveSeconds=\"" + timeToLiveSeconds + "\""
    // + " overflowToDisk=\"" + overflowToDisk + "\""
    // + " eternal=\"" + eternal + "\""
    // + " diskPersistent=\"" + diskPersistent + "\""
    // + " diskExpiryThreadIntervalSeconds=\"" + diskExpiryThreadIntervalSeconds + "\""
    // + " memoryStoreEvictionPolicy=\"" + memoryStoreEvictionPolicy + "\" />" //
    // + "</ehcache>";
    private CacheManager manager;
    // private Ehcache cache; // 用静态变量根本就是不对的
    public EHDataCache() {
//        try {
//            String path = System.getProperty("java.io.tmpdir");
//            System.out.println("#cache.DIR:" + path);
//            File[] fs = new File(path).listFiles(new FilenameFilter() {
//            	@Override
//                public boolean accept(File pFile, String fileName) {
//            		if (!fileName.endsWith(".data"))
//            			return false;
//                    if (fileName.startsWith("Haiyan.DATAS."))
//                        return true;
//                    return false;
//                }
//
//            });
//            for (File f : fs) {
//                try {
//                    if (f.exists())
//                        f.delete();
//                } catch (Throwable ignore) {
//                	ignore.printStackTrace();
//                }
//            }
//        } catch (Throwable e) {
//        	throw Warning.getWarning(e);
//        }
    }
    @Override
    public void setServers(String[] servers) {
    }
    @Override
    public void initialize() {
    	getManager();
    }
    private static synchronized void loadConfigXML() {
        try {
            if (normalXML == null) {
            	//String userDir = System.getProperty("user.dir");
                String fileName = PathUtil.getWebInfoHome()+File.separator+"ehcache.xml";
                DebugUtil.debug("#cache.configXML:" + fileName);
                normalXML = FileUtil.toString(fileName, null);
                if (StringUtil.isEmpty(normalXML)) {
                	throw new Warning("Ehcache配置错误:"+fileName);
                }
            }
        } catch (Throwable ex) {
            throw Warning.getWarning(ex);
        }
    }
    private CacheManager getManager() {
        if (manager == null)
            synchronized (EHDataCache.class) {
                if (manager == null) {
                    loadConfigXML();
                    manager = CacheManager.create(new ByteArrayInputStream(normalXML.getBytes()));
                }
            }
        return manager;
    }
    // private CacheManager getConfigManager() {
    // if (manager == null)
    // manager = CacheManager.create(new ByteArrayInputStream(configXML.getBytes()));
    // return manager;
    // }
    private int getSize(Ehcache cache) {
        return cache.getDiskStoreSize();
    }
//    private Object getObjectValue(Ehcache cache, Object key) {
//        Element ele = cache.get(key);
//        if (ele != null)
//            return ele.getObjectValue();
//        return null;
//    }
    private Object getValue(Ehcache cache, Object key) {
        try {
            Element ele = cache.get(key);
            if (ele != null)
                return ele.getValue();
        } catch (Throwable e) {
            DebugUtil.error(e);
            cache.remove(key);
        }
        return null;
    }
    public void removeCache(String cacheID) {
        CacheManager manager = getManager();
        manager.removeCache(cacheID);
        // try {
        // String path = System.getProperty("java.io.tmpdir");
        // File f = new File(path + File.separator + "Haiyan.DATAS." + ID
        // + ".data");
        // if (f.exists())
        // f.delete();
        // // Haiyan.DATAS.3c853aca-1623-4bcf-9fe3-a06f78e24698.data
        // } catch (Throwable ignore) {
        //			ignore.printStackTrace();
        //		}
    }
    // --------------------- tableconfig cache --------------------- //
    private Ehcache getTableCache() {
        String key = "Haiyan.TABLES";
        CacheManager manager = getManager();
    	if (!manager.cacheExists(key))
	        synchronized (manager) {
	        	if (!manager.cacheExists(key))
	        		manager.addCache(key);
	        }
        Cache cache = manager.getCache(key);
        return cache;
    }
    // for local cache
    @Override
    public ITableConfig setTable(String sKey, ITableConfig table) {
        Ehcache cache = getTableCache();
        cache.put(new Element(sKey, table));
        // manager.shutdown();
        cache.flush();
        return table;
    }
    // for local cache
    @Override
    public ITableConfig getTable(String sKey) {
        Ehcache cache = getTableCache();
        Element ele = cache.get(sKey);
        if (ele == null)
            return null;
        ITableConfig table = (ITableConfig) ele.getValue();
        return table;
    }
    // for local cache
    @Override
    public boolean removeTable(String sKey) {
        Ehcache cache = getTableCache();
        boolean b = cache.remove(sKey);
        return b;
    }
    // for local cache
    @Override
    public String[] getAllTableKeys() {
        ArrayList<String> list = new ArrayList<String>();
        Ehcache cache = getTableCache();
        for (Object sID : cache.getKeys()) {
            Object obj = getValue(cache, sID);
            if (obj == null)
                continue;
            ITableConfig table = (ITableConfig) obj;
            list.add(table.getName());
            // Element ele = cache.get(sID);
            // // Serializable
            // ITable table = (ITable) ele.getValue();
            // list.add(table.getName());
        }
        return list.toArray(new String[0]);
    }
    // --------------------- billconfig cache --------------------- //
    private Ehcache getBillCache() {
        String key = "Haiyan.BILLS";
        CacheManager manager = getManager();
    	if (!manager.cacheExists(key))
	        synchronized (manager) {
	        	if (!manager.cacheExists(key))
	        		manager.addCache(key);
	        }
        Cache cache = manager.getCache(key);
        return cache;
    }
    // for local cache
    @Override
    public IBillConfig setBill(String sKey, IBillConfig bill) {
        Ehcache cache = getBillCache();
        cache.put(new Element(sKey, bill));
        // manager.shutdown();
        cache.flush();
        return bill;
    }
    // for local cache
    @Override
    public IBillConfig getBill(String sKey) {
        Ehcache cache = getBillCache();
        Element ele = cache.get(sKey);
        if (ele == null)
            return null;
        // Serializable
        IBillConfig bill = (IBillConfig) ele.getValue();
        return bill;
    }
    // for local cache
    @Override
    public boolean removeBill(String sKey) {
        Ehcache cache = getBillCache();
        boolean b = cache.remove(sKey);
        return b;
    }
    // for local cache
    @Override
    public String[] getAllBillKeys() {
        ArrayList<String> list = new ArrayList<String>();
        Ehcache cache = getBillCache();
        for (Object sID : cache.getKeys()) {
            Object obj = getValue(cache, sID);
            if (obj == null)
                continue;
            ITableConfig bill = (ITableConfig) obj;
            list.add(bill.getName());
        }
        return list.toArray(new String[0]);
    }
    // --------------------- user cache --------------------- //
    private Ehcache getUserCache() {
        String key = "Haiyan.USERS";
        CacheManager manager = getManager();
    	if (!manager.cacheExists(key))
	        synchronized (manager) {
	        	if (!manager.cacheExists(key))
	        		manager.addCache(key);
	        }
        Cache cache = manager.getCache(key);
        return cache;
    }
    @Override
    public IUser setUser(String sID, IUser user) {
        Ehcache cache = getUserCache();
        cache.put(new Element(sID, user));
        cache.flush();
        return user;
    }
    @Override
    public IUser getUser(String sID) {
        Ehcache cache = getUserCache();
        Object obj = getValue(cache, sID);
        if (obj == null)
            return null;
        IUser user = (IUser) obj;
        return user;
    }
    @Override
    public boolean removeUser(String sID) {
        Ehcache cache = getUserCache();
        boolean b = cache.remove(sID);
        cache.flush();
        return b;
    }
    @Override
	public boolean containsUser(String sessionId) {
		return getUser(sessionId)!=null;
	}
    /** 
     * 
     * 遍历这么大量数据 失去了Cache的意义
     */
    @Override
    @Deprecated
    public IUser getUserByID(String userID) {
        Ehcache cache = getUserCache();
        for (Object sID : cache.getKeys()) {
            Object obj = getValue(cache, sID);
            if (obj == null)
                return null;
            IUser user = (IUser) obj;
            if (userID.equals(user.getId()))
                return user;
        }
        return null;
    }
    /** 
     * 遍历这么大量数据 失去了Cache的意义
     */
    @Override
    @Deprecated
    public IUser getUserByCode(String userCode) {
        Ehcache cache = getUserCache();
        for (Object sID : cache.getKeys()) {
            Object obj = getValue(cache, sID);
            if (obj == null)
                return null;
            IUser user = (IUser) obj;
            if (userCode.equals(user.getCode()))
                return user;
        }
        return null;
    }
    /**
     * 返回IUser[]数组的失去了Cache的意义
     * 
     * @return IUser[]
     */
    @Deprecated 
    public IUser[] getAllUsers() {
        ArrayList<IUser> list = new ArrayList<IUser>();
        Ehcache cache = getUserCache();
        for (Object sID : cache.getKeys()) {
            Object obj = getValue(cache, sID);
            if (obj == null)
                return null;
            IUser user = (IUser) obj;
            list.add(user);
            if (list.size() > 2000) // NOTICE 只允许显示开头2000个用户
                break;
        }
        return list.toArray(new IUser[0]);
    }
    public int sizeOfUser() {
        Ehcache cache = getUserCache();
        return getSize(cache);
    }
    // --------------------- data cache --------------------- //
    private Ehcache getDataCache() {
        String key = "Haiyan.DATAS";
        CacheManager manager = getManager();
    	if (!manager.cacheExists(key))
	        synchronized (manager) {
	        	if (!manager.cacheExists(key))
	        		manager.addCache(key);
	        }
        Cache cache = manager.getCache(key);
        return cache;
    }
    @Override
    public Object setLocalData(String cacheID, Object key, Object ele) {
        Ehcache cache = getDataCache();
        cache.put(new Element(key+"@"+cacheID, ele));
        cache.flush();
        return ele;
    }
    @Override
    public Object setData(String cacheID, Object key, Object ele) {
        return this.setLocalData(cacheID, key, ele);
    }
    @Override
    public Object updateData(String cacheID, Object key, Object ele) {
        return this.setLocalData(cacheID, key, ele);
    }
    @Override
    public Object getLocalData(String cacheID, Object key) {
    	Ehcache cache = getDataCache();
        Element ele = cache.get(key+"@"+cacheID);
        if (ele != null)
            return ele.getObjectValue();
        return null;
    }
    @Override
    public Object getData(String cacheID, Object key) {
    	return this.getLocalData(cacheID, key);
    }
    private Object removeLocalData(Ehcache cache, Object key) {
        Object obj = null;
        Element ele = cache.get(key);
        if (ele != null) {
            obj = ele.getObjectValue();
            cache.remove(key);
        }
        cache.flush();
        return obj;
    }
    @Override
	public Object removeLocalData(String cacheID, Object key) {
        Ehcache cache = getDataCache();
        return removeLocalData(cache, key+"@"+cacheID);
    }
	@Override
	public Object deleteData(String cacheID, Object key) {
        return removeLocalData(cacheID, key);
    }
    @Override
    @Deprecated
    public void clearData(String cacheID) {
//        Ehcache cache = getDataCache();
//        cache.removeAll();
//        cache.flush();
    }
//    // -------------------------------------------------------------------------- //
//    private Ehcache getListCache() {
//        String key = "Haiyan.LIST";
//        CacheManager manager = getManager();
//    	if (!manager.cacheExists(key))
//	        synchronized (manager) {
//	        	if (!manager.cacheExists(key))
//	        		manager.addCache(key);
//	        }
//        Cache cache = manager.getCache(key);
////        if (cache == null) {
////            cache = manager.getCache(key);
//            // // (String name, int maxElementsInMemory, boolean overflowToDisk,
//            // // boolean eternal, long timeToLiveSeconds, long
//            // timeToIdleSeconds)
//            // cache = new Cache("Haiyan.DATAS." + ID, maxElementsInMemory,
//            // overflowToDisk, !eternal, timeToLiveSeconds,
//            // timeToIdleSeconds);
//            // manager.addCache(cache);
////        }
//        return cache;
//    }
//	// for temp-local list 
//	private boolean addListData(String cacheID, Collection<?> c) {
//		Ehcache cache = getListCache();
//		int size = getSize(cache);
//		Iterator<?> iter = c.iterator();
//		while (iter.hasNext()) {
//			int i = size++;
//			Object ele = iter.next();
//			cache.put(new Element(i, ele));
//		}
//		cache.flush();
//		return true;
//	}
//    // for temp-local list
//	@Override
//    public boolean addListData(String cacheID, Object ele) {
//    	if (ele instanceof Collection)
//    		return addListData(cacheID, (Collection<?>)ele);
//        Ehcache cache = getListCache(cacheID);
//        int i = getSize(cache);
//        cache.put(new Element(i, ele));
//        cache.flush();
//        return true;
//    }
//    // for temp-local list
//    @Override
//    public int getDataSize(String cacheID) {
//        Ehcache cache = getListCache(cacheID);
//        return getSize(cache);
//    }
//    // for temp-local list
//    @Override
//    public int getIndexOf(String cacheID, Object o) {
//        Ehcache cache = getListCache(cacheID);
//        int size = getSize(cache);
//        if (o == null) {
//            for (int i = 0; i < size; i++) {
//                Object obj = getObjectValue(cache, i);
//                if (obj == null)
//                    return i;
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                Object obj = getObjectValue(cache, i);
//                if (o.equals(obj))
//                    return i;
//            }
//        }
//        return -1;
//    }
//    // for temp-local list
//    @Override
//    public int getLastIndexOf(String cacheID, Object o) {
//        Ehcache cache = getListCache(cacheID);
//        int size = getSize(cache);
//        if (o == null) {
//            for (int i = size - 1; i >= 0; i--) {
//                Object obj = getObjectValue(cache, i);
//                if (obj == null)
//                    return i;
//            }
//        } else {
//            for (int i = size - 1; i >= 0; i--) {
//                Object obj = getObjectValue(cache, i);
//                if (o.equals(obj))
//                    return i;
//            }
//        }
//        return -1;
//    }
//	@Override
//    public boolean removeListData(String cacheID, Object o) {
//        Ehcache cache = getListCache(cacheID);
//        int size = getSize(cache);
//        if (o == null) {
//            for (int i = 0; i < size; i++) {
//                // Element ele = cache.get(i);
//                // if (ele != null && ele.getObjectValue() == null) {
//                // // cache.remove(i);
//                // removeData(cache, i);
//                // return true;
//                // }
//                Object obj = getObjectValue(cache, i);
//                if (obj == null) {
//                	removeLocalData(cache, i);
//                    return true;
//                }
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                // Element ele = cache.get(i);
//                // if (ele != null && o.equals(ele.getObjectValue())) {
//                // // cache.remove(i);
//                // removeData(cache, i);
//                // return true;
//                // }
//                Object obj = getObjectValue(cache, i);
//                if (o.equals(obj)) {
//                	removeLocalData(cache, i);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
 
}
