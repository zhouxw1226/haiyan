package test.cache;

import static java.lang.Integer.valueOf;
import haiyan.cache.RedisDataCacheRemote;
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
		IDataCache dc = new RedisDataCacheRemote();
		dc.setServers(new String[]{"127.0.0.1:6379"});
		dc.initialize();
		
		Integer i = new Integer(9999);
		dc.setData("TESTCACHE", "10000", i);
		Object o = dc.getData("TESTCACHE", "10000");
		System.out.println(o);
		assert (o != valueOf(9999));
	}
}
