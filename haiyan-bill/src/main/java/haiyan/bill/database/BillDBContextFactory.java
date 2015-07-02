package haiyan.bill.database;

import haiyan.bill.database.sql.BillDBContext;
import haiyan.bill.database.sql.BillDBManagerFactory;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.PropUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

public class BillDBContextFactory {

	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IUser user) {
		if (user==null)
			throw new Warning("user lost");
		IBillDBContext context = createDBContext(user.getDSN());
		context.setUser(user);
		return context;
	}
	public static IBillDBContext createDBContext(String DSN) {
		IBillDBContext context = new BillDBContext();
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		context.setDSN(DSN);
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(context);
		tableContext.setDSN(DSN);
		context.setTableDBContext(tableContext);
//		tableContext.setUser(context.getUser());
//		IBillTable[] billTables = billConfig.getBillTable();
//		for (int i=0;i<billTables.length;i++) {
//			IBillTable billTable = billTables[i];
//			int tableIndex = billTable.getTableIndex();
//			ITableDBContext tableContext;
//			tableContext = TableDBContextFactory.createDBContext(DSN);
//			//tableContext.getDBM().getConnection()
//			context.setTableDBContext(tableIndex, tableContext);
//		}
		return context;
	}
	public static IBillDBContext createDBContext() {
		String DSN = PropUtil.getProperty("SERVER.DSN");
		return createDBContext(DSN);
	}
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IContext parent, ITableDBManager dbm) {
		IBillDBContext context = new BillDBContext(parent);
		if(parent != null && parent.getUser() != null){
			context.setUser(parent.getUser());
		}else{
			return createDBContext();
		}
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(parent.getUser());
		context.setBBM(bbm);
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(context, dbm);
		tableContext.setUser(parent.getUser());
		context.setTableDBContext(tableContext);
//		IBillTable[] billTables = billConfig.getBillTable();
//		for (int i=0;i<billTables.length;i++) {
////			IBillTable billTable = billTables[i];
////			int tableIndex = billTable.getTableIndex();
////			ITableDBContext tableContext;
////			if (dbms==null)
////				tableContext = TableDBContextFactory.createDBContext(parent, null);
////			else
////				tableContext = TableDBContextFactory.createDBContext(parent, dbms[i]);
////			//tableContext.getDBM().getConnection()
////			context.setTableDBContext(tableIndex, tableContext);
//		}
		return context;
	}
	public static IBillDBContext createDBContext(IContext parent) {
		return createDBContext(parent, null);
	}

}
