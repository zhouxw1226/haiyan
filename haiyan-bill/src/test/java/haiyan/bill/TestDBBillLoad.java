package haiyan.bill;

import haiyan.bill.database.sql.DBBillManagerFactory;
import haiyan.common.CloseUtil;
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
import haiyan.orm.database.DBContextFactory;
import haiyan.orm.database.sql.SQLDBFilter;

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
		TestLoadConfig.loadConfig();
		IDBBillManager bbm = null;
		try {
			IUser user = new AppUser();
			user.setDSN("MYSQL");
			IContext context = DBContextFactory.createDBContext(user);
			bbm = DBBillManagerFactory.createDBBillManager(context);
			IBillConfig billCfg = ConfigUtil.getBill("TEST_BILL");
			
			long time = System.currentTimeMillis();
			IDBBill bill = bbm.createBill(billCfg, false);
			bill.setBillID("1");
			bbm.loadBill(bill);
			System.out.println("-----------------");
			bill.find(1, new IPredicate(){
				@Override
				public boolean evaluate(IDBRecord r) {
					if (r.getString("NAME").startsWith("name")) {
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
					if (r.getString("NAME").startsWith("name")) {
						System.out.println("filter:"+r);
						return true;
					}
					return false;
				}
			});
			
			ISQLDBFilter dbFilter = new SQLDBFilter(" and LENGTH(t_1.ID)<7 "){ };
			IDBResultSet rst2 = bill.query(context, 1, dbFilter, 3, 1, true); // 3:rowPageCount, 1:firstPage
			System.out.println("-----------------");
			System.out.println("rst2:"+rst2.getRecordCount());
			System.out.println("rst2:"+rst2.getTotalRecordCount());
			System.out.println("rst2:"+rst2.getActiveRecord());
			
			IDBResultSet rst4 = bill.queryNext(context, 1, dbFilter, -1, true);
			System.out.println("-----------------");
			System.out.println("rst4:"+rst4.getRecordCount());
			System.out.println("rst4:"+rst4.getTotalRecordCount());
			System.out.println("rst4:"+rst4.getActiveRecord());
			
			IDBResultSet rst3 = bill.queryPrev(context, 1, dbFilter, -1, true);
			System.out.println("-----------------");
			System.out.println("rst3:"+rst3.getRecordCount());
			System.out.println("rst3:"+rst3.getTotalRecordCount());
			System.out.println("rst3:"+rst3.getActiveRecord());
//			IDBResultSet rst5 = bill.sort(1, new ISort());

//			bbm.commit();
			System.out.println("test end:"+(System.currentTimeMillis()-time)+"ms");
		}finally{
			CloseUtil.close(bbm);
		}
	}
}
