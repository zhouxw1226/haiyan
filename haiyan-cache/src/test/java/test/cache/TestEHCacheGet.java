package test.cache;

import haiyan.cache.EHDataCache;
import haiyan.common.intf.cache.IDataCache;

import org.junit.Test;
 
/**
 * Unit test for simple App.
 */
public class TestEHCacheGet  {
	public static void main(String[] args) throws InterruptedException {
		TestEHCacheGet t = new TestEHCacheGet();
		t.testGet();
	}
	@Test
	public void testGet() throws InterruptedException {
		IDataCache dc = new EHDataCache();
		Object o;
//		Integer i = new Integer(9999);
//		dc.setData("TESTCACHE", "10000", i);
		o = dc.getData("TESTCACHE", "10000");
		System.out.println(o);
		assert(o!=Integer.valueOf(9999));
		while(true) {
			o = dc.getData("TESTCACHE", "shangjie+10000");
			if (o!=null) {
				System.out.println("remote:"+o);
				assert(o!=Integer.valueOf(9999));
			}
			o = dc.getData("TESTCACHE", "10000");
			if (o!=null) {
				System.out.println("local:"+o);
				assert(o!=Integer.valueOf(9999));
			}
			Thread.sleep(3000);
		}
	} 
}
