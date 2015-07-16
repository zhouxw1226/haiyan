package haiyan.bill.database;

import haiyan.bill.database.sql.BillDBContext;
import haiyan.bill.database.sql.BillDBManagerFactory;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

public class BillDBContextFactory {

//	void setMasterDSNSuffix(String suffix);
//	void setSlaveDSNSuffix(String suffix);
//	String getMasterDSNSuffix();
//	String getSlaveDSNSuffix();
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IUser user) {
		if (user==null)
			throw new Warning("user lost");
		String DSN = user.getDSN();
		IBillDBContext context = createDBContext(DSN);
		context.setUser(user);
		return context;
	}
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
	public static IBillDBContext createDBContext() {
		String DSN = ConfigUtil.getDefaultDSN();
		return createDBContext(DSN);
	}
	public static IBillDBContext createDBContextOfMaster() {
		String DSN = ConfigUtil.getDefaultDSNOfMaster();
		if (StringUtil.isEmpty(DSN))
			return createDBContext();
		return createDBContext(DSN);
	}
	public static IBillDBContext createDBContextOfSlave() {
		String sDSN = ConfigUtil.getDefaultDSNOfSlaves();
		if (StringUtil.isEmpty(sDSN))
			return createDBContext();
		String[] aDSN = StringUtil.split(sDSN, ",");
		String DSN = aDSN[(int)Math.round(Math.random()*(aDSN.length-1))];
		return createDBContext(DSN);
	}
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IContext parent, String DSN) {
		if (parent==null||parent.getUser()==null) {
			return createDBContext();
		}
		if (StringUtil.isEmpty(DSN)) {
			String DSN2 = parent.getDSN();
			DSN = StringUtil.isEmpty(DSN2)?ConfigUtil.getDefaultDSN():DSN2;
		}
		IBillDBContext context = new BillDBContext(parent);
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(context, DSN);
		context.setTableDBContext(tableContext);
		return context;
	}
	public static IBillDBContext createDBContext(IContext parent, ITableDBManager dbm) {
		if (parent==null||parent.getUser()==null) {
			return createDBContext();
		}
		String DSN = parent.getDSN();
		IBillDBContext context = new BillDBContext(parent);
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
