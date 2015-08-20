package haiyan.orm.database;

import haiyan.common.StringUtil;
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
	@Deprecated
	public static ITableDBContext createDBContext() {
		String DSN = ConfigUtil.getDefaultDSN();
		return createDBContext(DSN);
	}
	// ----------------------------------------------------------------------------------- //
	public static ITableDBContext createDBContext(IUser user, ITableDBManager dbm) {
		ITableDBContext context = new TableDBContext();
		context.setUser(user);
		String DSN = null;
		if(user != null){
			String DSN2 = user.getDSN();
			DSN = StringUtil.isEmpty(DSN2)?ConfigUtil.getDefaultDSN():DSN2;
		}else{
			DSN = ConfigUtil.getDefaultDSN();
		}
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
	public static ITableDBContext createDBContext(IContext parent, String DSN) {
		ITableDBContext context = new TableDBContext(parent);
		if (StringUtil.isEmpty(DSN))
			if (parent!=null){
				String DSN2 = parent.getDSN();
				DSN = StringUtil.isEmpty(DSN2)?ConfigUtil.getDefaultDSN():DSN2;
			}else{
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
		if (parent!=null){
			String DSN2 = parent.getDSN();
			DSN = StringUtil.isEmpty(DSN2)?ConfigUtil.getDefaultDSN():DSN2;
		}else{
			DSN = ConfigUtil.getDefaultDSN();
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
