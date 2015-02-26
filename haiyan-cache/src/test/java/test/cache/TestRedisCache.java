package test.cache;

import static java.lang.Integer.valueOf;
import haiyan.cache.RedisDataCacheRemote;
import haiyan.common.PropUtil;
import haiyan.common.intf.cache.IDataCache;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestRedisCache {
	public static void main(String[] args) {
		try {
			TestRedisCache t = new TestRedisCache();
			t.test1();
		} catch(Throwable e){
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	@Test
	public void test1() {
		IDataCache cache = null;
//		new RedisDataCacheRemote();
//		//dc.setServers(new String[]{"127.0.0.1:6379"});
//		dc.setServers(new String[]{"121.40.62.116:6379","121.40.106.146:6380","121.41.85.81:6380"});
//		dc.initialize();

		cache = new RedisDataCacheRemote();
		cache.setServers(PropUtil.getProperty("REDISCACHE.SERVERS").split(";"));
		cache.initialize();
		
		Integer i = new Integer(999999);
		cache.setData("TESTCACHE", "10000", i);
		Object o = cache.getData("TESTCACHE", "10000");
		System.out.println(o);
		assert (o != valueOf(999999));
	}
}
