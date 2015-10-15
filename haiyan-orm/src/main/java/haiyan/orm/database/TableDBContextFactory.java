package haiyan.orm.database;

import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.sql.TableDBContext;
import haiyan.orm.database.sql.TableDBManagerFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

/**
 * TableDBContext(AppSession)Factory
 * 
 * @author ZhouXW
 */
public class TableDBContextFactory implements IFactory {

	private TableDBContextFactory() {
	}
	// ----------------------------------------------------------------------------------- //
	public static ITableDBContext createDBContext(String DSN) {
		ITableDBContext context = new TableDBContext();
		ITableDBManager dbm = TableDBManagerFactory.createDBManager(DSN);
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	// ----------------------------------------------------------------------------------- //
	public static ITableDBContext createDBContext(IUser user) {
		if (user==null)
			throw new Warning("user lost");
		String DSN = user.getDSN();
		ITableDBContext context = createDBContext(DSN);
		context.setUser(user);
		return context;
	}
	// ----------------------------------------------------------------------------------- //
	public static ITableDBContext createDBContext(IContext parent, String DSN) {
		ITableDBContext context = new TableDBContext(parent);
		if (StringUtil.isEmpty(DSN))
			if (parent != null) {
				String DSNOfParent = parent.getDSN();
				DSN = StringUtil.isEmpty(DSNOfParent) ? ConfigUtil.getDefaultDSN() : DSNOfParent;
			} else {
				DSN = ConfigUtil.getDefaultDSN();
			}
		else
			context.setDSN(DSN);
		ITableDBManager dbm = TableDBManagerFactory.createDBManager(DSN);
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext(IContext parent, ITableDBManager dbm) {
		ITableDBContext context = new TableDBContext(parent);
		String DSN = null;
		{
			if (parent != null) {
				String DSNOfParent = parent.getDSN();
				DSN = StringUtil.isEmpty(DSNOfParent) ? ConfigUtil.getDefaultDSN() : DSNOfParent;
			} else {
				DSN = ConfigUtil.getDefaultDSN();
			}
		}
		dbm = dbm==null?TableDBManagerFactory.createDBManager(DSN):dbm;
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext(IContext parent) {
		return createDBContext(parent, (String)null);
	}
}
