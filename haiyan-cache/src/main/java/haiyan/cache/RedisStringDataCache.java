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
	public Object setData(String schema, Object key, Object ele) {
		JedisCommands writer = null;
		try {
			writer = getJedisMaster();
			String mk = getDataKey(schema, key);
			writer.set(mk, ele==null?"":ele.toString());
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
			return setData(schema,key,ele==null?"":ele.toString());
		}finally{
			recycleJedisResource(writer);
		}
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
