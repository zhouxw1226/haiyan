package haiyan.cache;

import haiyan.common.ByteUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
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
    private JedisPool masterJedisPool;//非切片连接池
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private BinaryJedisCommands jedisImplWriter;
    private BinaryJedisCommands jedisImplReader;
    private String[] servers = null;
	public RedisDataCache() {
		super();
	}
	/**
	 * 初始化非切片池
	 */
	private void initialMasterPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(200);
		config.setMaxIdle(50);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		{
			String[] arr = StringUtil.split(servers[0],":"); // 第一个为master
			masterJedisPool = new JedisPool(config, arr[0], VarUtil.toInt(arr[1]));//"127.0.0.1", 6379);
		}
	}
	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(200);
		config.setMaxIdle(50);
		config.setMaxWait(1000l);
		config.setTestOnBorrow(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		int length = servers.length;
		for (int i=1;i<length;i++) {
			String[] arr = StringUtil.split(servers[i],":");
			JedisShardInfo jsi = new JedisShardInfo(arr[0], VarUtil.toInt(arr[1]), "slave"); // "master"
			shards.add(jsi);
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
    		initialMasterPool();
    		jedisImplWriter = masterJedisPool.getResource();
    		initialShardedPool();
    		jedisImplReader = shardedJedisPool.getResource();
    	} else {
    		initialMasterPool();
    		jedisImplWriter = masterJedisPool.getResource();
    	}
    }
    protected BinaryJedisCommands getJedisWriter() {
    	try {
    		return jedisImplWriter;
    	}catch(Throwable e){
    		throw Warning.wrapException(e);
    	}
    }
    protected BinaryJedisCommands getJedisReader() {
    	try {
    		if(jedisImplReader == null)
    			return jedisImplWriter;
    		return jedisImplReader;
    	}catch(Throwable e){
    		throw Warning.wrapException(e);
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
		getJedisWriter().set(k.getBytes(), ByteUtil.toBytes(user));
		getJedisWriter().set((k+"._status").getBytes(), "0".getBytes()); // login
		return user;
	}
	@Override
	public IUser getUser(String sessionId) {
		IUser user = super.getUser(sessionId);
		String k = getUserKey(sessionId);
		{
			Integer status = -1;
			byte[] bytes = getJedisReader().get((k+"._status").getBytes());
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
			byte[] bytes = getJedisReader().get(k.getBytes());
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
		if (getJedisWriter() instanceof ShardedJedis)
			((ShardedJedis)getJedisWriter()).del(k);
		else if (getJedisWriter() instanceof Jedis)
			((Jedis)getJedisWriter()).del(k.getBytes());
		getJedisWriter().set((k+"._status").getBytes(), "-1".getBytes()); // logout
		return true;
	}
    // --------------------- data cache --------------------- //
	private String getDataKey(String cacheID, Object key) {
		return "Haiyan.DATAS." + cacheID+":"+key;
	}
	@Override
	public Object setData(String cacheID, Object key, Object ele) {
		String mk = getDataKey(cacheID, key);
		getJedisWriter().set(mk.getBytes(), ByteUtil.toBytes((Serializable)ele));
		return ele;
	}
	@Override
	public Object getData(String cacheID, Object key) {
		String mk = getDataKey(cacheID, key);
		byte[] bytes = getJedisReader().get(mk.getBytes());
		if (bytes!=null) {
			return ByteUtil.toObject(bytes);
		}
		return null;
	}
	@Override
	public Object updateData(String cacheID, Object key, Object ele) {
		return this.setData(cacheID, key, ele);
	}
	@Override
	public Object deleteData(String cacheID, Object key) {
		Object o = this.getData(cacheID, key);
		String mk = getDataKey(cacheID, key);
		if (getJedisWriter() instanceof ShardedJedis)
			((ShardedJedis)getJedisWriter()).del(mk);
		else if (getJedisWriter() instanceof Jedis)
			((Jedis)getJedisWriter()).del(mk.getBytes());
		return o;
    }
//	public final static String VERSION_WARNING = "当前单据数据已被修改,请重新打开当前单据后继续操作.";
	
}
