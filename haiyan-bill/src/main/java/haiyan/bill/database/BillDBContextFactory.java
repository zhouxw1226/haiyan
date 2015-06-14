package haiyan.bill.database;

import haiyan.bill.database.sql.BillDBContext;
import haiyan.bill.database.sql.BillDBManagerFactory;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.PropUtil;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.IBillTable;
import haiyan.common.intf.session.IContext;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

public class BillDBContextFactory {

	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createBillDBContext(String DSN, IBillConfig billConfig) {
		IBillDBContext context = new BillDBContext();
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		IBillTable[] billTables = billConfig.getBillTable();
		for (int i=0;i<billTables.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			ITableDBContext tableContext;
			tableContext = TableDBContextFactory.createDBContext(DSN);
			//tableContext.getDBM().getConnection()
			context.setTableDBContext(tableIndex, tableContext);
		}
		return context;
	}
	public static IBillDBContext createBillDBContext(IBillConfig billConfig) {
		String DSN = PropUtil.getProperty("SERVER.DSN");
		return createBillDBContext(DSN, billConfig);
	}
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createBillDBContext(IContext parent, IBillConfig billConfig, ITableDBManager[] dbms) {
		IBillDBContext context = new BillDBContext(parent);
		if(parent != null){
			context.setUser(parent.getUser());
		}else{
			return createBillDBContext(billConfig);
		}
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(parent.getUser());
		context.setBBM(bbm);
		IBillTable[] billTables = billConfig.getBillTable();
		for (int i=0;i<billTables.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			ITableDBContext tableContext;
			if (dbms==null)
				tableContext = TableDBContextFactory.createDBContext(parent, null);
			else
				tableContext = TableDBContextFactory.createDBContext(parent, dbms[i]);
			//tableContext.getDBM().getConnection()
			context.setTableDBContext(tableIndex, tableContext);
		}
		return context;
	}
	public static IBillDBContext createBillDBContext(IContext parent, IBillConfig billConfig) {
		return createBillDBContext(parent, billConfig, null);
	}

}
