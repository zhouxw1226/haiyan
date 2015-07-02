package test.exp;

import haiyan.common.CloseUtil;
import haiyan.common.cache.AppDataCache;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;

import java.io.File;

import org.junit.Test;

public class TestExp {

	public static void main(String[] args) throws Throwable {
		TestExp t = new TestExp();
		t.test1();
	}
	@Test
	public void test1() throws Throwable {
//		TestBillConfigLoad.loadConfig();
		{
			ConfigUtil.setDataCache(new AppDataCache()); // 配置用缓存框架
			ConfigUtil.setExpUtil(new ExpUtil()); // 全局用公式引擎
			ConfigUtil.setORMUseCache(true); // 开启ORM多级缓存
		}
		{
			File file;
			file = new File(System.getProperty("user.dir")
					+File.separator+"WEB-INF"+File.separator+"haiyan-config.xml");
			ConfigUtil.loadRootConfig(file);
		}
		IContext context = new AppContext();
		try {
			IExpUtil exp = new ExpUtil(context);
			context.setExpUtil(exp);
			context.setAttribute("TTT", 123.45);
			System.out.println(exp.evalExp("GetPara(TTT)+654.32"));
		}finally{
			CloseUtil.close(context);
		}
	}

}
