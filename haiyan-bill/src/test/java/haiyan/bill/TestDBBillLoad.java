package haiyan.bill;

import haiyan.bill.database.sql.DBBillManagerFactory;
import haiyan.common.CloseUtil;
import haiyan.common.cache.AppDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.IPredicate;
import haiyan.common.intf.database.bill.IDBBillManager;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBFilter;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.AppUser;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.DBContextFactory;
import haiyan.orm.database.sql.SQLDBFilter;

import java.io.File;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestDBBillLoad {
	public static void main(String[] args) throws Throwable {
		TestDBBillLoad t = new TestDBBillLoad();
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
			file = new File(TestDBBillLoad.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDBBillLoad.class.getResource("SYSCACHE.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			
			file = new File(TestDBBillLoad.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			
			file = new File(TestDBBillLoad.class.getResource("TEST_BILL.xml").getPath());
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
			bill.find(1, new IPredicate(){
				@Override
				public boolean evaluate(IDBRecord r) {
					if (r.getString("NAME").equalsIgnoreCase("clear T_WM_IN")) {
						System.out.println("find:"+r);
						return true;
					}
					return false;
				}
			});
//			System.out.println("find:"+record);
			System.out.println("-----------------");
			bill.filter(1, new IPredicate(){
				@Override
				public boolean evaluate(IDBRecord r) {
					if (r.getString("NAME").startsWith("clear")) {
						System.out.println("filter:"+r);
						return true;
					}
					return false;
				}
			});
			
			ISQLDBFilter dbFilter = new SQLDBFilter(" and LENGTH(t_1.ID)>5 "){ };
			IDBResultSet rst2 = bill.query(context, 1, dbFilter, 5, 1, true); // 1:firstPage
			System.out.println("-----------------");
			System.out.println("rst2:"+rst2.getRecordCount());
			System.out.println("rst2:"+rst2.getTotalRecordCount());
			System.out.println("rst2:"+rst2.getActiveRecord());
			
			IDBResultSet rst4 = bill.queryNext(context, 1, dbFilter, 5, true);
			System.out.println("-----------------");
			System.out.println("rst4:"+rst4.getRecordCount());
			System.out.println("rst4:"+rst4.getTotalRecordCount());
			System.out.println("rst4:"+rst4.getActiveRecord());
			
			IDBResultSet rst3 = bill.queryPrev(context, 1, dbFilter, 5, true);
			System.out.println("-----------------");
			System.out.println("rst3:"+rst3.getRecordCount());
			System.out.println("rst3:"+rst3.getTotalRecordCount());
			System.out.println("rst3:"+rst3.getActiveRecord());
//			IDBResultSet rst5 = bill.sort(1, new ISort());

			billMgr.commit();
			System.out.println("test end");
		}finally{
			CloseUtil.close(billMgr);
		}
	}
}
