package test.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import haiyan.cache.RedisBinaryDataCache;
import haiyan.cache.RedisLock;
import haiyan.common.PropUtil;
import haiyan.common.VarUtil;

/**
 * Unit test for simple App.
 */
@SuppressWarnings("rawtypes")
public class TestRedisLock {
	public static void main(String[] args) {
		try {
			TestRedisLock t = new TestRedisLock();
			t.test1();
		} catch(Throwable e){
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	@Test
	public void test1() throws Throwable {
		RedisBinaryDataCache cache = null;
//		new RedisDataCacheRemote();
//		//dc.setServers(new String[]{"127.0.0.1:6379"});
//		dc.setServers(new String[]{"121.40.62.116:6379","121.40.106.146:6380","121.41.85.81:6380"});
//		dc.initialize();
		cache = new RedisBinaryDataCache();
		cache.setServers(PropUtil.getProperty("REDISCACHE.SERVERS").split(";"));
		cache.initialize();
		redis.clients.util.Pool pool = cache.getPool();
		RedisLock lock = new RedisLock("test", pool);
		try {
			lock.lock(1000);
			int i = VarUtil.toInt(lock.getJedis().get("lock_value"));
			lock.getJedis().set("lock_value", ""+(i+1)); 
			System.out.println(i);
		}finally{
			lock.unlock();
		}
	}
}
