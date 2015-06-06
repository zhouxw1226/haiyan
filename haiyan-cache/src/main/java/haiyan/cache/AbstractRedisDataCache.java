package haiyan.cache;

import haiyan.common.DebugUtil;
import haiyan.common.PropUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.session.IUser;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import bsh.StringUtil;


/**
 * @author zhouxw
 *
 */
public abstract class AbstractRedisDataCache extends AbstractDataCache {

//	protected Jedis defaultJedis;//非切片额客户端连接
    protected JedisPool masterJedisPool;//非切片连接池
    protected ShardedJedisPool shardedJedisPool;//切片连接池
    protected String[] servers = null;
    protected String password = PropUtil.getProperty(null,"REDISCACHE.PASSWORD",null);
	public AbstractRedisDataCache() {
		super();
	}
	/**
	 * 初始化非切片池
	 */
	protected void initialMasterPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(3000);
		config.setMaxIdle(1000);
		config.setMaxWait(1500);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		{
			DebugUtil.debug("redis.master:"+servers[0]);
			String[] arr = StringUtil.split(servers[0],":"); // 第一个为master
			if(password != null){
				masterJedisPool = new JedisPool(config, arr[0], VarUtil.toInt(arr[1]),2000,password);//"127.0.0.1", 6379);
			}else{
				masterJedisPool = new JedisPool(config, arr[0], VarUtil.toInt(arr[1]),2000);//"127.0.0.1", 6379);
			}
		}
	}
	/**
	 * 初始化切片池
	 */
	protected void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(3000);
		config.setMaxIdle(1000);
		config.setMaxWait(1500);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		int length = servers.length;
		for (int i=1;i<length;i++) {
			DebugUtil.debug("redis.slave:"+servers[i]);
			String[] arr = StringUtil.split(servers[i],":");
			JedisShardInfo jsi = new JedisShardInfo(arr[0], VarUtil.toInt(arr[1]), "slave"); // "master"
			if(password != null)
				jsi.setPassword(password);
//			jsi.setTimeout(0);
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
//    		jedisImplWriter = masterJedisPool.getResource();
    		initialShardedPool();
//    		jedisImplReader = shardedJedisPool.getResource();
    	} else {
    		initialMasterPool();
//    		jedisImplWriter = masterJedisPool.getResource();
    	}
    }
//    protected JedisCommands getJedisWriter() {
//    	try {
//    		return masterJedisPool.getResource();
//    	}catch(Throwable e){
//    		throw Warning.wrapException(e);
//    	}
//    }
//    protected JedisCommands getJedisReader() {
//    	try {
//    		if(servers.length==1) {
//    			return masterJedisPool.getResource();
//    		}
//    		return shardedJedisPool.getResource();
//    	}catch(Throwable e){
//    		throw Warning.wrapException(e);
//    	}
//    }
    // --------------------- user cache --------------------- //
	protected String getUserKey(String schema) {
    	return "Haiyan.USERS."+schema;
    } 
	protected int reconn = 0;
	@Override
	public abstract IUser setUser(String sessionId, IUser user);
	@Override
	public abstract IUser getUser(String sessionId);
	@Override
	public abstract boolean removeUser(String sessionId);
    // --------------------- data cache --------------------- //
	protected String getDataKey(String schema, Object key) {
		return "Haiyan.DATAS." + schema+":"+key;
	}
	@Override
	public abstract Object setData(String schema, Object key, Object ele);
	@Override
	public abstract Object getData(String schema, Object key);
	@Override
	public Object updateData(String schema, Object key, Object ele) {
		return this.setData(schema, key, ele);
	}
	@Override
	public abstract Object deleteData(String schema, Object key);
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
