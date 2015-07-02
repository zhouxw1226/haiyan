package haiyan.bill;

import haiyan.bill.database.BillDBContextFactory;
import haiyan.bill.database.IBillDBManager;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.CloseUtil;
import haiyan.common.DateUtil;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.AppUser;
import haiyan.config.util.ConfigUtil;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestDBBillSave {
	public static void main(String[] args) throws Throwable {
		TestDBBillSave t = new TestDBBillSave();
		t.test1();
		System.exit(0);
	}
	@Test
	public void test1() throws Throwable {
		TestLoadConfig.loadConfig();
		IBillDBContext context = null;
		try {
			IUser user = new AppUser();
			user.setDSN("MYSQL");
			IBillConfig billCfg = ConfigUtil.getBill("TEST_BILL");
			context = BillDBContextFactory.createDBContext(user, billCfg);
			IBillDBManager bbm = context.getBBM();
			
			IDBBill billOld = bbm.createBill(context, billCfg, false);
			billOld.setBillID("1");
			bbm.loadBill(context, billOld);
			System.out.println("-----------------");
			System.out.println(billOld.getResultSet(0).getRecordCount());
			System.out.println(billOld.getResultSet(1).getRecordCount());
			System.out.println("ID1:"+billOld.getValue("ID1"));
			System.out.println("ID2:"+billOld.getValue("ID2"));
			System.out.println("CODE1:"+billOld.getValue("CODE1"));
			System.out.println("CODE2:"+billOld.getValue("CODE2"));
			System.out.println("NAME1:"+billOld.getValue("NAME1"));
			System.out.println("NAME2:"+billOld.getValue("NAME2"));
			System.out.println("-----------------");
			billOld.setValue("CODE1","code1");
			billOld.setValue("CODE2","code2");
			billOld.setValue("NAME1","name1");
			billOld.setValue("NAME2","name2");
			System.out.println("ID1:"+billOld.getValue("ID1"));
			System.out.println("ID2:"+billOld.getValue("ID2"));
			System.out.println("CODE1:"+billOld.getValue("CODE1"));
			System.out.println("CODE2:"+billOld.getValue("CODE2"));
			System.out.println("NAME1:"+billOld.getValue("NAME1"));
			System.out.println("NAME2:"+billOld.getValue("NAME2"));
			
			bbm.rollback();
			System.out.println("-----------------");
			billOld.setValue("CODE1","code1-"+DateUtil.getLastTime());
			billOld.setValue("CODE2","code2-"+DateUtil.getLastTime());
			System.out.println("ID1:"+billOld.getValue("ID1"));
			System.out.println("ID2:"+billOld.getValue("ID2"));
			System.out.println("CODE1:"+billOld.getValue("CODE1"));
			System.out.println("CODE2:"+billOld.getValue("CODE2"));
			System.out.println("NAME1:"+billOld.getValue("NAME1"));
			System.out.println("NAME2:"+billOld.getValue("NAME2"));
			
			IDBBill billNew = bbm.createBill(context, billCfg);
			//bbm.createBillID(billNew);
			IDBRecord record1 = billNew.insertRowAfter(1, 0);
			System.out.println("record1:"+record1);
//
			IDBRecord record2 = billNew.insertRowBefore(1, 0);
			System.out.println("record2:"+record2);

			IDBRecord record3 = billNew.appendRow(1);
			System.out.println("record3:"+record3);

			IDBRecord record6 = billNew.deleteRow(1, 0);
			System.out.println("record6:"+record6);
			IDBRecord record7 = billNew.deleteRow(1, 1);
			System.out.println("record7:"+record7);
//			
//			billMgr.deleteBill(bill.getBillConfig(), bill.getBillID());
//			billMgr.deleteBill(bill);
//			billMgr.rollback();
			
			bbm.openTransaction();
			bbm.saveBill(context,billOld);
			bbm.saveBill(context,billNew);
			bbm.commit();
			System.out.println("-----------------");
			
			billNew.setValue("CODE1","code1-"+DateUtil.getLastTime());
			billNew.setValue("CODE2","code2-"+DateUtil.getLastTime());
			billNew.setValue("NAME1","name1");
			billNew.setValue("NAME2","name2");
			System.out.println("ID1:"+billNew.getValue("ID1"));
			System.out.println("ID2:"+billNew.getValue("ID2"));
			System.out.println("CODE1:"+billNew.getValue("CODE1"));
			System.out.println("CODE2:"+billNew.getValue("CODE2"));
			System.out.println("NAME1:"+billNew.getValue("NAME1"));
			System.out.println("NAME2:"+billNew.getValue("NAME2"));
			bbm.saveBill(context,billNew);
			bbm.commit();
			
			bbm.closeTransaction();
			
			System.out.println("test end");
		}finally{
			CloseUtil.close(context);
		}
	}
}
