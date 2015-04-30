package haiyan.cache;

import haiyan.common.ByteUtil;
import haiyan.common.DebugUtil;
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
import redis.clients.jedis.exceptions.JedisConnectionException;
import bsh.StringUtil;


/**
 * @author zhouxw
 *
 */
public class RedisDataCacheRemote extends AbstractDataCache {

//	private Jedis defaultJedis;//非切片额客户端连接
    private JedisPool masterJedisPool;//非切片连接池
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private BinaryJedisCommands jedisImplWriter;
    private BinaryJedisCommands jedisImplReader;
    private String[] servers = null;
	public RedisDataCacheRemote() {
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
			DebugUtil.debug("redis.master:"+servers[0]);
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
			DebugUtil.debug("redis.slave:"+servers[i]);
			String[] arr = StringUtil.split(servers[i],":");
			JedisShardInfo jsi = new JedisShardInfo(arr[0], VarUtil.toInt(arr[1]), "slave"); // "master"
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
	private String getUserKey(String schema) {
    	return "Haiyan.USERS."+schema;
    } 
	private int reconn = 0;
	@Override
	public IUser setUser(String sessionId, IUser user) {
		try {
			if (user==null)
				throw new Warning("user is null");
			String k = getUserKey(sessionId);
			getJedisWriter().set(k.getBytes(), ByteUtil.toBytes(user));
			getJedisWriter().set((k+"._status").getBytes(), "0".getBytes()); // login
			reconn = 0;
			return user;
		}catch (JedisConnectionException ex) {
			DebugUtil.error(ex);
			if (reconn>=3) {
				reconn = 0;
				return null;
			}
			reconn++;
			this.initialize();
			return setUser(sessionId, user);
		}catch(Throwable e){
			DebugUtil.error(e);
			return null;
		}
	}
	@Override
	public IUser getUser(String sessionId) {
		try {
			IUser user = null; // super.getUser();
			String key = getUserKey(sessionId);
			Integer status = -1;
			byte[] bytes = getJedisReader().get((key+"._status").getBytes());
			if (bytes!=null) {
				status = VarUtil.toInt(bytes);
			}
			if (status == -1) { // is deleted
//				mcc.delete(k+"._status"); NOTICE 不能加，有些服务器还没通知完
//				super.removeUser(sessionId);
				return null;
			}
			if (user==null) {
				bytes = getJedisReader().get(key.getBytes());
				if (bytes!=null)
					user = (IUser)ByteUtil.toObject(bytes);
//				if (user!=null)
//					super.setUser(sessionId, user);
			}
			reconn = 0;
			return user;
		}catch (JedisConnectionException ex) {
			DebugUtil.error(ex);
			if (reconn>=3) {
				reconn = 0;
				return null;
			}
			reconn++;
			this.initialize();
			return getUser(sessionId);
		}catch(Throwable e){
			DebugUtil.error(e);
			return null;
		}
	}
	@Override
	public boolean removeUser(String sessionId) {
		try {
			String key = getUserKey(sessionId);
			if (getJedisWriter() instanceof ShardedJedis)
				((ShardedJedis)getJedisWriter()).del(key);
			else if (getJedisWriter() instanceof Jedis)
				((Jedis)getJedisWriter()).del(key.getBytes());
			getJedisWriter().set((key+"._status").getBytes(), "-1".getBytes()); // logout
			reconn = 0;
			return true;
		}catch (JedisConnectionException ex) {
			DebugUtil.error(ex);
			if (reconn>=3) {
				reconn = 0;
				return false;
			}
			reconn++;
			this.initialize();
			return removeUser(sessionId);
		}catch(Throwable e){
			DebugUtil.error(e);
			return false;
		}
	}
    // --------------------- data cache --------------------- //
	private String getDataKey(String schema, Object key) {
		return "Haiyan.DATAS." + schema+":"+key;
	}
	@Override
	public Object setData(String schema, Object key, Object ele) {
		String mk = getDataKey(schema, key);
		try {
			BinaryJedisCommands cmds = getJedisWriter();
			byte[] kBytes = mk.getBytes();
			byte[] vBytes = ByteUtil.toBytes((Serializable)ele);
			cmds.set(kBytes, vBytes);
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
		}
	}
	@Override
	public Object getData(String schema, Object key) {
		try {
			String mk = getDataKey(schema, key);
			byte[] bytes = getJedisReader().get(mk.getBytes());
			if (bytes!=null) {
				return ByteUtil.toObject(bytes);
			}
			return null;
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
		}
	}
	@Override
	public Object updateData(String schema, Object key, Object ele) {
		return this.setData(schema, key, ele);
	}
	@Override
	public Object deleteData(String schema, Object key) {
		Object o = this.getData(schema, key);
		String mk = getDataKey(schema, key);
		try {
			if (getJedisWriter() instanceof ShardedJedis)
				((ShardedJedis)getJedisWriter()).del(mk);
			else if (getJedisWriter() instanceof Jedis)
				((Jedis)getJedisWriter()).del(mk.getBytes());
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
