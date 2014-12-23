package haiyan.orm.database;

import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.database.IDBManager;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.sql.DBManagerFactory;


/**
 * AppSession
 * 
 * @author ZhouXW
 *
 */
public class DBContextFactory implements IFactory {

	private DBContextFactory() { 
	}
	public static ITableDBContext createDBContext(String DSN) {
		ITableDBContext context = new TableDBContext();
		IDBManager dbm = DBManagerFactory.createDBManager(DSN);
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext() {
		String DSN = PropUtil.getProperty("SERVER.DSN");
		return createDBContext(DSN);
	}
	public static ITableDBContext createDBContext(IUser user, IDBManager dbm) {
		ITableDBContext context = new TableDBContext();
		context.setUser(user);
		String DSN = StringUtil.isEmpty(user.getDSN())?PropUtil.getProperty("SERVER.DSN"):user.getDSN();
		dbm = dbm==null?DBManagerFactory.createDBManager(DSN):dbm;
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext(IUser user) {
		return createDBContext(user, null);
	}
	public static ITableDBContext createDBContext(IContext parent, IDBManager dbm) {
		TableDBContext context = new TableDBContext(parent);
		String DSN = StringUtil.isEmpty(context.getDSN())?PropUtil.getProperty("SERVER.DSN"):context.getDSN();
		dbm = dbm==null?DBManagerFactory.createDBManager(DSN):dbm;
		context.setDBM(dbm);
		IExpUtil exp = new ExpUtil(context);
		context.setExpUtil(exp);
		return context;
	}
	public static ITableDBContext createDBContext(IContext parent) {
		return createDBContext(parent, null);
	}
}
