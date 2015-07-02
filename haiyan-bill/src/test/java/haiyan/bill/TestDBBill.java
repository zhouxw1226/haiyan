package haiyan.bill;

import haiyan.bill.database.BillDBContextFactory;
import haiyan.bill.database.IBillDBManager;
import haiyan.bill.database.sql.BillDBManagerFactory;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.CloseUtil;
import haiyan.common.cache.AppDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.AppUser;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;

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
			ConfigUtil.setExpUtil(new ExpUtil()); // 鍏ㄥ眬鐢ㄥ叕寮忓紩鎿�			ConfigUtil.setORMUseCache(true); // 寮�惎ORM澶氱骇缂撳瓨
//			CacheUtil.setDataCache(new EHDataCache()); // 鍏ㄥ眬鐢ㄧ紦瀛樻鏋�//			ConfigUtil.setDataCache(new EHDataCache()); // 閰嶇疆鐢ㄧ紦瀛樻鏋�//			ConfigUtil.setExpUtil(new ExpUtil()); // 鍏ㄥ眬鐢ㄥ叕寮忓紩鎿�//			ConfigUtil.setORMUseCache(true); // 寮�惎ORM澶氱骇缂撳瓨
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
			
			file = new File(TestDBBill.class.getResource("SYSBILL.xml").getPath());
			ConfigUtil.loadBillConfig(file, true);
		}
		IUser user = new AppUser();
		user.setDSN("MYSQL");
		IBillDBContext context = null;
		try {
			IBillConfig billCfg = ConfigUtil.getBill("SYSBILL");
			context = BillDBContextFactory.createDBContext(user, billCfg);
			IBillDBManager billMgr = BillDBManagerFactory.createDBManager(user);
			IDBBill bill = billMgr.createBill(context, billCfg);
			
			billMgr.loadBill(context, bill);
			bill.setValue("CODE1","code1");
			bill.setValue("CODE2","code2");
			bill.setValue("NAME1","name1");
			bill.setValue("NAME2","name2");
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			
			billMgr.rollback();
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			
			billMgr.createBillID(context, bill);
			bill.setValue("CODE1","code1");
			bill.setValue("CODE2","code2");
			bill.setValue("NAME1","name1");
			bill.setValue("NAME2","name2");
			System.out.println("ID1:"+bill.getValue("ID1"));
			System.out.println("ID2:"+bill.getValue("ID2"));
			System.out.println("CODE1:"+bill.getValue("CODE1"));
			System.out.println("CODE2:"+bill.getValue("CODE2"));
			System.out.println("NAME1:"+bill.getValue("NAME1"));
			System.out.println("NAME2:"+bill.getValue("NAME2"));
			
//			billMgr.deleteBill(bill);
//			billMgr.rollback();
//			billMgr.saveBill(bill);
			billMgr.commit();
			System.out.println("test end");
		}finally{
			CloseUtil.close(context);
		}
	}
}
