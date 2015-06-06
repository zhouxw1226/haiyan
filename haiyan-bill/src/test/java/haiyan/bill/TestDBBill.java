package haiyan.bill;

import haiyan.bill.database.sql.DBBillManagerFactory;
import haiyan.common.CloseUtil;
import haiyan.common.DateUtil;
import haiyan.common.cache.AppDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.bill.IDBBillManager;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.AppUser;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.DBContextFactory;

import java.io.File;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestDBBill {
	public static void main(String[] args) throws Throwable {
		TestDBBill t = new TestDBBill();
		t.test1();
		System.exit(0);
	}
	@Test
	public void test1() throws Throwable {
		{
			ConfigUtil.setDataCache(new AppDataCache()); 
			ConfigUtil.setDataCache(new AppDataCache());
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
			file = new File(TestDBBill.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDBBill.class.getResource("SYSCACHE.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			
			file = new File(TestDBBill.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			
			file = new File(TestDBBill.class.getResource("TEST_BILL.xml").getPath());
			ConfigUtil.loadBillConfig(file, true);
		}
		IDBBillManager billMgr = null;
		try {
			IUser user = new AppUser();
			user.setDSN("MYSQL");
			IContext context = DBContextFactory.createDBContext(user);
			billMgr = DBBillManagerFactory.createBillManager(context);
			IBillConfig billCfg = ConfigUtil.getBill("TEST_BILL");
			IDBBill bill = billMgr.createBill(billCfg);
			
			billMgr.loadBill(bill);
			System.out.println("-----------------");
			System.out.println(bill.getResultSet(0).getRecordCount());
			System.out.println(bill.getResultSet(1).getRecordCount());
			System.out.println("ID1:"+bill.getValue("ID1"));
			System.out.println("ID2:"+bill.getValue("ID2"));
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			bill.setValue("CODE1","code1");
			bill.setValue("CODE2","code2");
			bill.setValue("NAME1","name1");
			bill.setValue("NAME2","name2");
			System.out.println("-----------------");
			System.out.println("ID1:"+bill.getValue("ID1"));
			System.out.println("ID2:"+bill.getValue("ID2"));
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			
			billMgr.rollback();
			System.out.println("-----------------");
			System.out.println("ID1:"+bill.getValue("ID1"));
			System.out.println("ID2:"+bill.getValue("ID2"));
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			
			billMgr.createBillID(bill);
			bill.setValue("CODE1","code1-"+DateUtil.getLastTime());
			bill.setValue("CODE2","code2-"+DateUtil.getLastTime());
			bill.setValue("NAME1","name1");
			bill.setValue("NAME2","name2");
			System.out.println("-----------------");
			System.out.println("ID1:"+bill.getValue("ID1"));
			System.out.println("ID2:"+bill.getValue("ID2"));
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			
//			billMgr.deleteBill(bill);
//			billMgr.rollback();
			billMgr.saveBill(bill);
			billMgr.commit();
			System.out.println("test end");
		}finally{
			CloseUtil.close(billMgr);
		}
	}
}
