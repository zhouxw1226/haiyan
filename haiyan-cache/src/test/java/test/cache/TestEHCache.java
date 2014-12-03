package test.cache;

import static java.lang.Integer.valueOf;
import haiyan.cache.EHDataCache;
import haiyan.common.intf.cache.IDataCache;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestEHCache {
	public static void main(String[] args) {
		try {
			TestEHCache t = new TestEHCache();
			t.test1();
		} finally {
			System.exit(0);
		}
	}
	@Test
	public void test1() {
		IDataCache dc = new EHDataCache();
		Integer i = new Integer(9999);
		dc.setData("TESTCACHE", "10000", i);
		Object o = dc.getData("TESTCACHE", "10000");
		System.out.println(o);
		assert (o != valueOf(9999));
	}
}
