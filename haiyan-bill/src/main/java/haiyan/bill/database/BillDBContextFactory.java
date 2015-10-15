package haiyan.bill.database;

import haiyan.bill.database.sql.BillDBContext;
import haiyan.bill.database.sql.BillDBManagerFactory;
import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.TableDBContextFactory;
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
//	public static IBillDBContext createDBContext(String DSN) {
//		ITableDBContext tableContext = TableDBContextFactory.createDBContext(DSN);
//		IBillDBContext context = new BillDBContext(tableContext);
//		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
//		context.setBBM(bbm);
//		return context;
//	}
//	// ----------------------------------------------------------------------------------- //
//	public static IBillDBContext createDBContext(IUser user) {
//		if (user==null)
//			throw new Warning("user lost");
//		String DSN = user.getDSN();
//		IBillDBContext context = createDBContext(DSN);
//		context.setUser(user);
//		return context;
//	}
	// ----------------------------------------------------------------------------------- //
	public static IBillDBContext createDBContext(IContext parent, String DSN) {
		if (parent instanceof ITableDBContext)
			throw new Warning("IllegalAccessException");
		ITableDBContext tableContext = TableDBContextFactory.createDBContext(parent, DSN);
		IBillDBContext context = new BillDBContext(tableContext);
		if (StringUtil.isEmpty(DSN)) {
			if (parent != null) {
				String DSNOfParent = parent.getDSN();
				DSN = StringUtil.isEmpty(DSNOfParent) ? ConfigUtil.getDefaultDSN() : DSNOfParent;
			} else {
				DSN = ConfigUtil.getDefaultDSN();
			}
		} else
			context.setDSN(DSN);
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		return context;
	}
	public static IBillDBContext createDBContext(ITableDBContext tableContext) {
//		ITableDBContext tableContext = TableDBContextFactory.createDBContext(parent);
//		tableContext.setDBM(dbm);
		IBillDBContext context = new BillDBContext(tableContext);
		String DSN = null;
		{
			if (tableContext != null) {
				String DSNOfParent = tableContext.getDSN();
				DSN = StringUtil.isEmpty(DSNOfParent) ? ConfigUtil.getDefaultDSN() : DSNOfParent;
			} else {
				DSN = ConfigUtil.getDefaultDSN();
			}
		}
		IBillDBManager bbm = BillDBManagerFactory.createDBManager(DSN);
		context.setBBM(bbm);
		return context;
	}
//	public static IBillDBContext createDBContext(ITableDBContext parent) {
//		return createDBContext(parent, (String)null);
//	}

}
