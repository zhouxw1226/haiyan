package haiyan.cache;

import java.io.Serializable;

import haiyan.common.ByteUtil;
import haiyan.common.DebugUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IUser;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author zhouxw
 *
 */
public class RedisBinaryDataCache extends AbstractRedisDataCache {
	@Override
	public IUser setUser(String sessionId, IUser user) {
		BinaryJedisCommands writer = null;
		try {
			writer = getJedisMaster();
			if (user==null)
				throw new Warning("user is null");
			String key = getUserKey(sessionId);
			writer.set(key.getBytes(), ByteUtil.toBytes(user));
			writer.set((key+"._status").getBytes(), "0".getBytes()); // login
			reconn = 0;
			return user;
		}catch (JedisConnectionException ex) {
			if (reconn>=3) {
				reconn = 0;
				throw ex;
			}
			reconn++;
			DebugUtil.error(ex);
			this.initialize();
			return setUser(sessionId, user);
		}catch(Throwable e){
			DebugUtil.error(e);
			return null;
		}finally{
			recycleJedisResource((JedisCommands)writer);
		}
	}
	@Override
	public IUser getUser(String sessionId) {
		BinaryJedisCommands reader = null;
		try {
			reader = getJedisSharded();
			if (reader==null)
				reader = getJedisMaster();
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
			if (reconn>=3) {
				reconn = 0;
				throw ex;
			}
			reconn++;
			DebugUtil.error(ex);
			this.initialize();
			return getUser(sessionId);
		}catch(Throwable e){
			DebugUtil.error(e);
			return null;
		}finally{
			recycleJedisResource((JedisCommands)reader);
		}
	}
	@Override
	public boolean removeUser(String sessionId) {
		BinaryJedisCommands writer = null;
		try {
			writer = getJedisMaster();
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
			if (reconn>=3) {
				reconn = 0;
				throw ex;
			}
			reconn++;
			DebugUtil.error(ex);
			this.initialize();
			return removeUser(sessionId);
		}catch(Throwable e){
			DebugUtil.error(e);
			return false;
		}finally{
			recycleJedisResource((JedisCommands)writer);
		}
	}
    // --------------------- data cache --------------------- //
	@Override
	public Object setData(String schema, Object key, Object ele, int seconds) {
		BinaryJedisCommands writer = null;
		try {
			writer = getJedisMaster();
			String mk = getDataKey(schema, key);
			byte[] kBytes = mk.getBytes();
			byte[] vBytes = ByteUtil.toBytes((Serializable)ele);
			writer.set(kBytes, vBytes);
			if (seconds>0)
				writer.expire(kBytes, seconds); // seconds
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
			return setData(schema, key, ele, seconds);
		}finally{
			recycleJedisResource((JedisCommands)writer);
		}
	}
	@Override
	public Object setData(String schema, Object key, Object ele) {
		return setData(schema, key, ele, -1);
	}
	@Override
	public Object getData(String schema, Object key) {
		BinaryJedisCommands reader = null;
		try {
			reader = getJedisSharded();
			if (reader==null)
				reader = getJedisMaster();
			String mk = getDataKey(schema, key);
			byte[] bytes = reader.get(mk.getBytes());
			if (bytes!=null) {
				return ByteUtil.toObject(bytes);
			}
			return null;
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
			recycleJedisResource((JedisCommands)reader);
		}
	}
	@Override
	public Object deleteData(String schema, Object key) {
		BinaryJedisCommands writer = null;
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
			recycleJedisResource((JedisCommands)writer);
		}
    }
}