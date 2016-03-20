package haiyan.cache;

import haiyan.common.DebugUtil;
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
	public Object setData(String schema, Object key, Object ele, int seconds) {
		JedisCommands writer = null;
		try {
			writer = getJedisMaster();
			String mk = getDataKey(schema, key);
			writer.set(mk, ele == null ? "" : ele.toString());
			if (seconds>0)
				writer.expire(mk, seconds); // seconds
//			writer.expireAt(arg0, arg1) // timestamp
			reconn = 0;
			return ele;
		} catch (JedisConnectionException ex) {
			if (reconn>=3) {
				reconn = 0;
				throw ex;
			}
			reconn++;
			DebugUtil.error(ex);
			this.initialize();
			return setData(schema, key, ele == null ? "" : ele.toString(), seconds);
		} finally {
			recycleJedisResource(writer);
		}
	}
	@Override
	public Object setData(String schema, Object key, Object ele) {
		return setData(schema, key, ele, -1);
	}
	@Override
	public Object getData(String schema, Object key) {
		JedisCommands reader = null;
		try {
			reader = getJedisSharded();
			if (reader==null)
				reader = getJedisMaster();
			String mk = getDataKey(schema, key);
			return reader.get(mk);
		} catch (JedisConnectionException ex) {
			if (reconn>=3) {
				reconn = 0;
				throw ex;
			}
			reconn++;
			DebugUtil.error(ex);
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
			writer = getJedisMaster();
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
		} catch (JedisConnectionException ex) {
			if (reconn>=3) {
				reconn = 0;
				throw ex;
			}
			reconn++;
			DebugUtil.error(ex);
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
