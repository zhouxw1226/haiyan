package haiyan.cache;

import haiyan.common.DebugUtil;
import haiyan.common.PropUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IUser;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import bsh.StringUtil;


/**
 * @author shangjie
 *
 */
public class RedisStringDataCacheRemote extends AbstractDataCache {

//	private Jedis defaultJedis;//非切片额客户端连接
    private JedisPool masterJedisPool;//非切片连接池
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private String[] servers = null;
    private String password = PropUtil.getProperty(null,"REDISCACHE.PASSWORD",null);
	public RedisStringDataCacheRemote() {
		super();
	}
	/**
	 * 初始化非切片池
	 */
	private void initialMasterPool() {
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
	private void initialShardedPool() {
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
    		initialShardedPool();
    	} else {
    		initialMasterPool();
    	}
    }
    protected JedisCommands getJedisWriter() {
    	try {
    		return masterJedisPool.getResource();
    	}catch(Throwable e){
    		throw Warning.wrapException(e);
    	}
    }
    protected JedisCommands getJedisReader() {
    	try {
    		if(servers.length==1) {
    			return masterJedisPool.getResource();
    		}
    		return shardedJedisPool.getResource();
    	}catch(Throwable e){
    		throw Warning.wrapException(e);
    	}
    }
	/** 归还jedis对象 */
	public void recycleJedisResource(JedisCommands jedis) {
		if (jedis==null)
			return;
		if (jedis instanceof ShardedJedis)
			shardedJedisPool.returnResource((ShardedJedis)jedis);
		else
			masterJedisPool.returnResource((Jedis)jedis);
	}
    // --------------------- user cache --------------------- //
	private int reconn = 0;
	@Override
	public IUser setUser(String sessionId, IUser user) {
		return null;
	}
	@Override
	public IUser getUser(String sessionId) {
		return null;
	}
	@Override
	public boolean removeUser(String sessionId) {
		return false;
	}
    // --------------------- data cache --------------------- //
	private String getDataKey(String schema, Object key) {
		return "Haiyan.DATAS." + schema+":"+key;
	}
	@Override
	public Object setData(String schema, Object key, Object ele) {
		JedisCommands writer = null;
		try {
			writer = getJedisWriter();
			String mk = getDataKey(schema, key);
			writer.set(mk, (String)ele);
			reconn = 0;
			return ele;
		} catch (JedisConnectionException e) {
			DebugUtil.error(e);
			if (reconn>=3) {
				reconn = 0;
				return null;
			}
			reconn++;
			this.initialize();
			return setData(schema,key,ele);
		}finally{
			recycleJedisResource(writer);
		}
	}
	@Override
	public Object getData(String schema, Object key) {
		JedisCommands reader = null;
		try {
			reader = getJedisReader();
			String mk = getDataKey(schema, key);
			return reader.get(mk);
		} catch (JedisConnectionException e) {
			DebugUtil.error(e);
			if (reconn>=3) {
				reconn = 0;
				return null;
			}
			reconn++;
			this.initialize();
			return getData(schema,key);
		} catch(Throwable e){
			DebugUtil.error(e);
			return null;
		}finally{
			recycleJedisResource(reader);
		}
	}
	@Override
	public Object updateData(String schema, Object key, Object ele) {
		return this.setData(schema, key, ele);
	}
	@Override
	public Object deleteData(String schema, Object key) {
		JedisCommands writer = null;
		try {
			writer = getJedisWriter();
			Object o = this.getData(schema, key);
			String mk = getDataKey(schema, key);
			if (writer instanceof ShardedJedis) {
				((ShardedJedis)writer).del(mk);
			}
			else if (writer instanceof Jedis) {
				((Jedis)writer).del(mk.getBytes());
			}
			reconn = 0;
			return o;
		} catch (JedisConnectionException e) {
			DebugUtil.error(e);
			if (reconn>=3) {
				reconn = 0;
				return null;
			}
			reconn++;
			this.initialize();
			return deleteData(schema,key);
		} catch(Throwable e){
			DebugUtil.error(e);
			return null;
		}finally{
			recycleJedisResource(writer);
		}
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
