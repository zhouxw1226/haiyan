package test.config;

import haiyan.common.cache.AppDataCache;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;

import java.io.File;

import org.junit.Test;

public class TestConfigUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestConfigUtil t = new TestConfigUtil();
		t.test1();
	}
	@Test
	public void test1() {
		ConfigUtil.setDataCache(new AppDataCache()); 
		
		File file = new File(TestTableConfig.class.getResource("SYS.xml").getPath());
		ConfigUtil.loadTableConfig(file, true);
		Table table = ConfigUtil.getTable("SYS");
        System.out.println(table.getName());
        System.out.println(table.getDescription());
        System.out.println(table.getId());
	}

}
