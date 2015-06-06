package haiyan.orm.database;

import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.database.ITableDBManager;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.orm.database.sql.DBBillAutoID;

/**
 * AppSession
 * 
 * @author ZhouXW
 *
 */
public class TableDBContext extends AppContext implements ITableDBContext {

	public TableDBContext() { 
		super();
	}
	public TableDBContext(IContext parent) { 
		super(parent);
	}
	@Override
	public void setDBM(ITableDBManager dbm) {
		super.setDBM(dbm);
	}
	@Override
	public ITableDBManager getDBM() {
		return (ITableDBManager)super.getDBM();
	}
//	@Override
//	public Object getNextID(String tableName) throws Throwable {
//		return DBBillAutoID.genNewID(this, ConfigUtil.getTable(tableName), 100);
////		return UUID.randomUUID().toString();
//	}
	@Override
	public Object getNextID(Table table) throws Throwable {
		return DBBillAutoID.genShortID(this, table, 100);
//		return UUID.randomUUID().toString();
	}
	@Override
	public Boolean isAlive() {
		ITableDBManager dbm = this.getDBM();
		if (dbm==null)
			return false;
		return dbm.isAlive();
	}
	@Override
	public void openTransaction() throws Throwable {
		ITableDBManager dbm = this.getDBM();
		if (dbm==null)
			return;
		dbm.openTransaction();
	}
	@Override
	public void closeTransaction() throws Throwable {
		ITableDBManager dbm = this.getDBM();
		if (dbm==null)
			return;
		dbm.closeTransaction();
	}
	@Override
	public void setAutoCommit(boolean b) throws Throwable {
		ITableDBManager dbm = this.getDBM();
		if (dbm==null)
			return;
		dbm.setAutoCommit(b);
	}
	@Override
	public void commit() throws Throwable {
		ITableDBManager dbm = this.getDBM();
		if (dbm==null)
			return;
		dbm.commit();
	}
	@Override
	public void rollback() throws Throwable {
		ITableDBManager dbm = this.getDBM();
		if (dbm==null)
			return;
		dbm.rollback();
	} 
	
}
