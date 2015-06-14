package haiyan.orm.database;

import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.sql.TableDBContext;
import haiyan.orm.database.sql.TableDBManagerFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

/**
 * AppSession
 * 
 * @author ZhouXW
 *
 */
public class TableDBContextFactory implements IFactory {

	private TableDBContextFactory() { 
	}
	public static ITableDBContext createDBContext(String DSN) {
		ITableDBContext context = new TableDBContext();
		ITableDBManager dbm = TableDBManagerFactory.createDBManager(DSN);
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext() {
		String DSN = PropUtil.getProperty("SERVER.DSN");
		return createDBContext(DSN);
	}
	// ----------------------------------------------------------------------------------- //
	public static ITableDBContext createDBContext(IUser user, ITableDBManager dbm) {
		ITableDBContext context = new TableDBContext();
		context.setUser(user);
		String DSN = StringUtil.isEmpty(user.getDSN())?PropUtil.getProperty("SERVER.DSN"):user.getDSN();
		dbm = dbm==null?TableDBManagerFactory.createDBManager(DSN):dbm;
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext(IUser user) {
		return createDBContext(user, null);
	}
	// ----------------------------------------------------------------------------------- //
	public static ITableDBContext createDBContext(IContext parent, ITableDBManager dbm) {
		TableDBContext context = new TableDBContext(parent);
		if (parent!=null)
			context.setUser(parent.getUser());
		String DSN = StringUtil.isEmpty(context.getDSN())?PropUtil.getProperty("SERVER.DSN"):context.getDSN();
		dbm = dbm==null?TableDBManagerFactory.createDBManager(DSN):dbm;
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext(IContext parent) {
		return createDBContext(parent, null);
	}
}
