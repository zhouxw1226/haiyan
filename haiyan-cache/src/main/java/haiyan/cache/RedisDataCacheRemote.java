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
public class RedisDataCacheRemote extends AbstractDataCache {

//	private Jedis defaultJedis;//非切片额客户端连接
    private JedisPool defaultJedisPool;//非切片连接池
//  private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private BinaryJedisCommands jedis;
    private String[] servers = null;
	public RedisDataCacheRemote() {
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
    	if (servers.length>1) {
    		initialShardedPool();
    		jedis = shardedJedisPool.getResource();
    	} else {
    		initialPool();
    		jedis = defaultJedisPool.getResource();
    	}
    }
    // --------------------- user cache --------------------- //
	private String getUserKey(String schema) {
    	return "Haiyan.USERS."+schema;
    } 
	@Override
	public IUser setUser(String sessionId, IUser user) {
		String k = getUserKey(sessionId);
		jedis.set(k.getBytes(), ByteUtil.toBytes(user));
		jedis.set((k+"._status").getBytes(), "0".getBytes()); // login
		return user;
	}
	@Override
	public IUser getUser(String sessionId) {
		IUser user = null; // super.getUser();
		String k = getUserKey(sessionId);
		{
			Integer status = -1;
			byte[] bytes = jedis.get((k+"._status").getBytes());
			if (bytes!=null) {
				status = VarUtil.toInt(bytes);
			}
			if (status == -1) { // is logout
				// mcc.delete(k+"._status"); NOTICE 不能加，有些服务器还没通知完
				// super.removeUser(sessionId);
				return null;
			}
		}
		if (user==null) {
			byte[] bytes = jedis.get(k.getBytes());
			if (bytes!=null)
				user = (IUser)ByteUtil.toObject(bytes);
//			if (user!=null)
//				super.setUser(sessionId, user);
		}
		return user;
	}
	@Override
	public boolean removeUser(String sessionId) {
//		super.removeUser(sessionId);
		String k = getUserKey(sessionId);
		if (jedis instanceof ShardedJedis)
			((ShardedJedis)jedis).del(k);
		else if (jedis instanceof Jedis)
			((Jedis)jedis).del(k.getBytes());
		jedis.set((k+"._status").getBytes(), "-1".getBytes()); // logout
		return true;
	}
    // --------------------- data cache --------------------- //
	private String getDataKey(String schema, Object key) {
		return "Haiyan.DATAS." + schema+":"+key;
	}
	@Override
	public Object setData(String schema, Object key, Object ele) {
		String mk = getDataKey(schema, key);
//		super.setLocalData(schema, mk, ele);
		jedis.set(mk.getBytes(), ByteUtil.toBytes((Serializable)ele));
		return ele;
	}
	@Override
	public Object getData(String schema, Object key) {
		String mk = getDataKey(schema, key);
		byte[] bytes = jedis.get(mk.getBytes());
		if (bytes!=null) {
			return ByteUtil.toObject(bytes);
		}
		return null;
//		Integer v = (Integer)super.getLocalData(schema, key+"._version");
//		Integer mv = VarUtil.toInt(jedis.get(mk+"._version"));
//		if (v==null || (mv!=null && v.intValue()<mv.intValue())) {
//			Object mo = jedis.get(mk);
//			if (mo!=null) {
//				super.setLocalData(schema, key+"._version", mv);
//				return super.setLocalData(schema, key, mo);
//			}
//			return null;
//		}
//		Object o = super.getLocalData(schema, key);
//		if (o!=null) {
//			return o;
//		}
//		Object mo = jedis.get(mk);
//		if (mo!=null) {
//			if (mv==null)
//				mv = 0;
//			super.setLocalData(schema, key+"._version", mv);
//			return super.setLocalData(schema, key, mo);
//		}
//		return null;
	}
	@Override
	public Object updateData(String schema, Object key, Object ele) {
		return this.setData(schema, key, ele);
//		String mk = getDataKey(schema, key);
//		Integer v = (Integer)super.getLocalData(schema, key+"._version");
//		Integer mv = (Integer)mcc.get(mk+"._version");
//		if (v!=null && mv!=null && v.intValue()!=mv.intValue()) {
//			throw new Warning(VERSION_WARNING);
//		}
//		mcc.set(mk, ele); // update cluster
//		super.setLocalData(schema, key, ele); // update local
//		{
//			if (mv==null)
//				mv = 0;
//			mv++;
//			mcc.set(mk+"._version", mv);
//			super.setLocalData(schema, key+"._version", mv);
//		}
//		return ele;
	}
	@Override
	public Object deleteData(String schema, Object key) {
		Object o = this.getData(schema, key);
		String mk = getDataKey(schema, key);
		if (jedis instanceof ShardedJedis)
			((ShardedJedis)jedis).del(mk);
		else if (jedis instanceof Jedis)
			((Jedis)jedis).del(mk.getBytes());
		return o;
//		Integer v = (Integer)super.getLocalData(schema, key+"._version");
//		Integer mv = (Integer)mcc.get(mk+"._version");
//		if (v!=null && mv!=null && v.intValue()!=mv.intValue()) {
//			throw new Warning(VERSION_WARNING);
//		}
//	    mcc.delete(mk); // update cluster
//	    Object o = super.removeLocalData(schema, key); // update local
//		{
//			if (mv==null)
//				mv = 0;
//			mv++;
//			mcc.set(mk+"._version", mv);
//			super.removeLocalData(schema, key+"._version");
//		}
//	    return o;
    }
//	public final static String VERSION_WARNING = "当前单据数据已被修改,请重新打开当前单据后继续操作.";
    // --------------------- local data cache --------------------- //
	@Override
	public Object setLocalData(String cacheID, Object key, Object ele) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getLocalData(String cacheID, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object removeLocalData(String cacheID, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clearData(String cacheID) {
		// TODO Auto-generated method stub
	}
	
}
