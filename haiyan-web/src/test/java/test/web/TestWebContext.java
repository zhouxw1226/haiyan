package test.web;

import haiyan.cache.CacheUtil;
import haiyan.cache.EHDataCache;
import haiyan.com.intf.web.IWebContext;
import haiyan.common.CloseUtil;
import haiyan.common.intf.database.IDBManager;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBContextFactory;
import haiyan.orm.database.sql.DBManagerFactory;
import haiyan.web.session.WebContextFactory;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestWebContext {
	public static void main(String[] args) throws IOException {
		TestWebContext t = new TestWebContext();
		t.test1();
	}
	@Test
	public void test1() throws IOException {
		ServletRequest req = new TestServletRequest();
		ServletResponse res = new TestServletResponse();
		CacheUtil.setDataCache(new EHDataCache()); // 全局用缓存框架
		ConfigUtil.setDataCache(new EHDataCache()); // 配置用缓存框架
		String path = TestWebContext.class.getClassLoader().getResource("haiyan-config.xml").getPath();
		File file = new File(path);
		try {
			ConfigUtil.loadRootConfig(file);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		IWebContext wc = WebContextFactory.createDBContext(req, res);
		wc.setDSN("MYSQL");
		ITableDBContext dc = DBContextFactory.createDBContext(wc);
		{
			wc.setAttribute("WEB_ATTR", 12345);
			System.out.println(dc.getAttribute("WEB_ATTR"));
		}
		{
			((ServletOutputStream)wc.getOutputStream()).println("abcdef");
			wc.getWriter().write("2222222");
		}
		{
			IDBManager dbm = DBManagerFactory.createDBManager("MYSQL");
			wc.setDBM(dbm);
			dc.setDBM(dbm);
		}
		CloseUtil.close(dc);
		CloseUtil.close(wc);
	}
}
