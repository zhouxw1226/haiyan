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
		config.setMaxActive(3000);
		config.setMaxIdle(1000);
		config.setMaxWait(1500);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
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
    protected BinaryJedisCommands getJedisWriter() {
    	try {
    		return masterJedisPool.getResource();
    	}catch(Throwable e){
    		throw Warning.wrapException(e);
    	}
    }
    protected BinaryJedisCommands getJedisReader() {
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
	public void recycleJedisWriter(BinaryJedisCommands jedis) {
		if (jedis==null)
			return;
		masterJedisPool.returnResource((Jedis)jedis);
	}
	public void recycleJedisReader(BinaryJedisCommands jedis) {
		if (jedis==null)
			return;
		if (servers.length==1) {
			masterJedisPool.returnResource((Jedis)jedis);
			return;
		}
		shardedJedisPool.returnResource((ShardedJedis)jedis);
	}
    // --------------------- user cache --------------------- //
	private String getUserKey(String schema) {
    	return "Haiyan.USERS."+schema;
    } 
	private int reconn = 0;
	@Override
	public IUser setUser(String sessionId, IUser user) {
		BinaryJedisCommands writer = null;
		try {
			writer = getJedisWriter();
			if (user==null)
				throw new Warning("user is null");
			String k = getUserKey(sessionId);
			writer.set(k.getBytes(), ByteUtil.toBytes(user));
			writer.set((k+"._status").getBytes(), "0".getBytes()); // login
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
		}finally{
			recycleJedisWriter(writer);
		}
	}
	@Override
	public IUser getUser(String sessionId) {
		BinaryJedisCommands reader = null;
		try {
			reader = getJedisReader();
			IUser user = null; // super.getUser();
			String key = getUserKey(sessionId);
			Integer status = -1;
			byte[] bytes = reader.get((key+"._status").getBytes());
			if (bytes!=null) {
				status = VarUtil.toInt(bytes);
			}
			if (status == -1) { // is deleted
//				mcc.delete(k+"._status"); NOTICE 不能加，有些服务器还没通知完
//				super.removeUser(sessionId);
				return null;
			}
			if (user==null) {
				bytes = reader.get(key.getBytes());
				if (bytes!=null){
					Object obj = ByteUtil.toObject(bytes);
					if(obj instanceof String){
						DebugUtil.error(obj);
					}else{
						user = (IUser)obj;
					}
				}
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
		}finally{
			recycleJedisWriter(reader);
		}
	}
	@Override
	public boolean removeUser(String sessionId) {
		BinaryJedisCommands writer = null;
		try {
			writer = getJedisWriter();
			String key = getUserKey(sessionId);
			if (writer instanceof ShardedJedis) {
				((ShardedJedis)writer).del(key);
			}
			else if (writer instanceof Jedis) {
				((Jedis)writer).del(key.getBytes());
			}
			writer.set((key+"._status").getBytes(), "-1".getBytes()); // logout
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
		}finally{
			recycleJedisWriter(writer);
		}
	}
    // --------------------- data cache --------------------- //
	private String getDataKey(String schema, Object key) {
		return "Haiyan.DATAS." + schema+":"+key;
	}
	@Override
	public Object setData(String schema, Object key, Object ele) {
		BinaryJedisCommands writer = null;
		try {
			writer = getJedisWriter();
			String mk = getDataKey(schema, key);
			byte[] kBytes = mk.getBytes();
			byte[] vBytes = ByteUtil.toBytes((Serializable)ele);
			writer.set(kBytes, vBytes);
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
			recycleJedisWriter(writer);
		}
	}
	@Override
	public Object getData(String schema, Object key) {
		BinaryJedisCommands reader = null;
		try {
			reader = getJedisReader();
			String mk = getDataKey(schema, key);
			byte[] bytes = reader.get(mk.getBytes());
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
		}finally{
			recycleJedisReader(reader);
		}
	}
	@Override
	public Object updateData(String schema, Object key, Object ele) {
		return this.setData(schema, key, ele);
	}
	@Override
	public Object deleteData(String schema, Object key) {
		BinaryJedisCommands writer = null;
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
			recycleJedisReader(writer);
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
