package haiyan.cache;

import haiyan.common.DebugUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IUser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;


/**
 * @author shangjie
 *
 */
public class RedisStringDataCache extends AbstractRedisDataCache {

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
	
}
