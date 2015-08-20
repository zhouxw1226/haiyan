package haiyan.bill.database;

import haiyan.bill.database.sql.BillDBContext;
import haiyan.bill.database.sql.BillDBManagerFactory;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

/**
 * BillDBContext(AppSession)Factory
 * 
 * @author ZhouXW
 */
public class BillDBContextFactory implements IFactory {

	private BillDBContextFactory() {
	}
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(String DSN) {
		IBillDBContext context = new BillDBContext(DSN);
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(context);
		context.setTableDBContext(tableContext);
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
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IUser user) {
		if (user==null)
			throw new Warning("user lost");
		String DSN = user.getDSN();
		IBillDBContext context = createDBContext(DSN);
		context.setUser(user);
		return context;
	}
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IContext parent, String DSN) {
		IBillDBContext context = new BillDBContext(parent);
		if (StringUtil.isEmpty(DSN))
			if (parent!=null){
				String DSN2 = parent.getDSN();
				DSN = StringUtil.isEmpty(DSN2)?ConfigUtil.getDefaultDSN():DSN2;
			}else{
				DSN = ConfigUtil.getDefaultDSN();
			}
		else 
			context.setDSN(DSN);
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(context, DSN);
		context.setTableDBContext(tableContext);
		return context;
	}
	public static IBillDBContext createDBContext(IContext parent, ITableDBManager dbm) {
		IBillDBContext context = new BillDBContext(parent);
		String DSN = null;
		if (parent!=null){
			String DSN2 = parent.getDSN();
			DSN = StringUtil.isEmpty(DSN2)?ConfigUtil.getDefaultDSN():DSN2;
		}else{
			DSN = ConfigUtil.getDefaultDSN();
		}
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(context, dbm);
		context.setTableDBContext(tableContext);
		return context;
	}
	public static IBillDBContext createDBContext(IContext parent) {
		return createDBContext(parent, (String)null);
	}

}
