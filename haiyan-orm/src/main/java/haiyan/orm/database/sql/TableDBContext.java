package haiyan.orm.database.sql;

import haiyan.common.CloseUtil;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

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
//	public Object getNextID(IBillConfig bill) throws Throwable {
//		return DBBillAutoID.genShortID(this, ConfigUtil.getMainTable(bill), 100);
////		return UUID.randomUUID().toString();
//	}
//	private IBillDBManager bbm;
//	@Override
//	public void setBBM(IBillDBManager bbm) {
////		if (this.bbm!=null && this.bbm.isAlive())
////			CloseUtil.close(this.bbm);
//		this.bbm = bbm;
//	}
//	@Override
//	public IBillDBManager getBBM() {
//		if (this.bbm!=null)
//			return this.bbm;
//		if (this.parent!=null && this.parent instanceof IBillDBContext)
//			return ((IBillDBContext)this.parent).getBBM();
//		return null;
//	}
	public Object getNextID(ITableConfig table) throws Throwable {
		return DBBillAutoID.genShortID(this, table, 100);
//		return UUID.randomUUID().toString();
	}
	private ITableDBManager dbm;
	@Override
	public void setDBM(ITableDBManager dbm) {
		if (this.dbm!=null && this.dbm.isAlive())
			CloseUtil.close(this.dbm);
		this.dbm = dbm;
	}
	@Override
	public ITableDBManager getDBM() {
		if (this.dbm!=null)
			return this.dbm;
		if (this.parent!=null && this.parent instanceof ITableDBContext)
			return ((ITableDBContext)this.parent).getDBM();
		return null;
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
	@Override
	public void close() {
		CloseUtil.close(this.dbm);
		this.dbm = null;
		super.close();
	}
	@Override
	public void clear() {
		if (this.dbm!=null)
			this.dbm.clear();
		super.clear();
	}
	
}
