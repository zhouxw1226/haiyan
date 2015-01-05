package haiyan.cache;

import haiyan.common.ByteUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.session.IUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import bsh.StringUtil;


/**
 * @author zhouxw
 *
 */
public class RedisDataCache extends EHDataCache {

//	private Jedis defaultJedis;//非切片额客户端连接
    private JedisPool defaultJedisPool;//非切片连接池
//    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private BinaryJedisCommands jedis;
    private String[] servers = null;
	public RedisDataCache() {
		super();
	}
	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(20);
		config.setMaxIdle(5);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		String[] arr = StringUtil.split(servers[0],":");
		defaultJedisPool = new JedisPool(config, arr[0], VarUtil.toInt(arr[1]));//"127.0.0.1", 6379);
	}
	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(20);
		config.setMaxIdle(5);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String server:servers) {
			String[] arr = StringUtil.split(server,":");
			shards.add(new JedisShardInfo(arr[0], VarUtil.toInt(arr[1]), "master"));//"127.0.0.1", 6379, "master"));
		}
		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}
    @Override
    public void setServers(String[] servers) {
    	this.servers = servers;
    }
    @Override
    public void initialize() {
    	super.initialize();
    	if (servers.length>1) {
    		initialShardedPool();
    		jedis = shardedJedisPool.getResource();
    	} else {
    		initialPool();
    		jedis = defaultJedisPool.getResource();
    	}
    }
    // --------------------- user cache --------------------- //
	private String getUserKey(String cacheID) {
    	return "Haiyan.USERS."+cacheID;
    } 
	@Override
	public IUser setUser(String sessionId, IUser user) {
		super.setUser(sessionId, user);
		String k = getUserKey(sessionId);
		jedis.set(k.getBytes(), ByteUtil.toBytes(user));
		jedis.set((k+"._status").getBytes(), "0".getBytes()); // login
		return user;
	}
	@Override
	public IUser getUser(String sessionId) {
		IUser user = super.getUser(sessionId);
		String k = getUserKey(sessionId);
		{
			Integer status = -1;
			byte[] bytes = jedis.get((k+"._status").getBytes());
			if (bytes!=null) {
				status = VarUtil.toInt(bytes);
			}
			if (status == -1) { // is logout
				// mcc.delete(k+"._status"); NOTICE 不能加，有些服务器还没通知完
				super.removeUser(sessionId);
				return null;
			}
		}
		if (user==null) {
			byte[] bytes = jedis.get(k.getBytes());
			if (bytes!=null)
				user = (IUser)ByteUtil.toObject(bytes);
			if (user!=null)
				super.setUser(sessionId, user);
		}
		return user;
	}
	@Override
	public boolean removeUser(String sessionId) {
		super.removeUser(sessionId);
		String k = getUserKey(sessionId);
		if (jedis instanceof ShardedJedis)
			((ShardedJedis)jedis).del(k);
		else if (jedis instanceof Jedis)
			((Jedis)jedis).del(k.getBytes());
		jedis.set((k+"._status").getBytes(), "-1".getBytes()); // logout
		return true;
	}
    // --------------------- data cache --------------------- //
	private String getDataKey(String cacheID, Object key) {
		return "Haiyan.DATAS." + cacheID+":"+key;
	}
	@Override
	public Object setData(String cacheID, Object key, Object ele) {
		String mk = getDataKey(cacheID, key);
//		super.setLocalData(cacheID, mk, ele);
		jedis.set(mk.getBytes(), ByteUtil.toBytes((Serializable)ele));
		return ele;
	}
	@Override
	public Object getData(String cacheID, Object key) {
		String mk = getDataKey(cacheID, key);
		byte[] bytes = jedis.get(mk.getBytes());
		if (bytes!=null) {
			return ByteUtil.toObject(bytes);
		}
		return null;
//		Integer v = (Integer)super.getLocalData(cacheID, key+"._version");
//		Integer mv = VarUtil.toInt(jedis.get(mk+"._version"));
//		if (v==null || (mv!=null && v.intValue()<mv.intValue())) {
//			Object mo = jedis.get(mk);
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
//		Object mo = jedis.get(mk);
//		if (mo!=null) {
//			if (mv==null)
//				mv = 0;
//			super.setLocalData(cacheID, key+"._version", mv);
//			return super.setLocalData(cacheID, key, mo);
//		}
//		return null;
	}
	@Override
	public Object updateData(String cacheID, Object key, Object ele) {
		return this.setData(cacheID, key, ele);
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
	}
	@Override
	public Object deleteData(String cacheID, Object key) {
		Object o = this.getData(cacheID, key);
		String mk = getDataKey(cacheID, key);
		if (jedis instanceof ShardedJedis)
			((ShardedJedis)jedis).del(mk);
		else if (jedis instanceof Jedis)
			((Jedis)jedis).del(mk.getBytes());
		return o;
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
    }
//	public final static String VERSION_WARNING = "当前单据数据已被修改,请重新打开当前单据后继续操作.";
	
}
