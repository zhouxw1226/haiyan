package haiyan.bill;

import haiyan.common.cache.AppDataCache;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;

import java.io.File;

public class TestBillConfigLoad {

	public static void loadConfig() throws Throwable {
		{
			ConfigUtil.setDataCache(new AppDataCache()); 
//			ConfigUtil.setDataCache(new AppDataCache());
			ConfigUtil.setExpUtil(new ExpUtil()); // 全局用公式引擎
			ConfigUtil.setORMUseCache(true); // 开启ORM多级缓存
//			CacheUtil.setDataCache(new EHDataCache()); // 全局用缓存框架
//			ConfigUtil.setDataCache(new EHDataCache()); // 配置用缓存框架
//			ConfigUtil.setExpUtil(new ExpUtil()); // 全局用公式引擎
//			ConfigUtil.setORMUseCache(true); // 开启ORM多级缓存
		}
		{
			File file;
			file = new File(System.getProperty("user.dir")
					+File.separator+"WEB-INF"+File.separator+"haiyan-config.xml");
			ConfigUtil.loadRootConfig(file);
			file = new File(TestBillConfigLoad.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestBillConfigLoad.class.getResource("SYSCACHE.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestBillConfigLoad.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestBillConfigLoad.class.getResource("TEST_BILL.xml").getPath());
			ConfigUtil.loadBillConfig(file, true);
		}
	}

}
