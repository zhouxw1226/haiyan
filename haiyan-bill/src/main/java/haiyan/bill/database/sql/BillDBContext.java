package haiyan.bill.database.sql;

import haiyan.bill.database.IBillDBManager;
import haiyan.common.CloseUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.session.AppContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.sql.DBBillAutoID;
import haiyan.orm.intf.session.ITableDBContext;

/**
 * AppSession
 * 
 * @author ZhouXW
 *
 */
public class BillDBContext extends AppContext implements IBillDBContext {

	public BillDBContext() { 
		super();
	}
	public BillDBContext(ITableDBContext parent) { 
		super(parent);
	}
	@Override
	public Object getNextID(IBillConfig bill) throws Throwable {
		return DBBillAutoID.genShortID(this, ConfigUtil.getMainTable(bill), 100);
	}
	@Override
	public ITableDBContext getTableDBContext() {
		if (this.parent instanceof ITableDBContext)
			return (ITableDBContext)this.parent;
		throw new Warning("unkown parent tableContext");
	}
	private IBillDBManager bbm;
	@Override
	public void setBBM(IBillDBManager bbm) {
		if (this.bbm!=null)
			throw new Warning("bbm exists");
		this.bbm = bbm;
	}
	@Override
	public IBillDBManager getBBM() {
		return this.bbm;
	}
	@Override
	public Boolean isAlive() {
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return false;
		return bbm.isAlive();
	}
	@Override
	public void openTransaction() throws Throwable {
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.openTransaction();
	}
	@Override
	public void closeTransaction() throws Throwable {
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.closeTransaction();
	}
	@Override
	public void setAutoCommit(boolean b) throws Throwable {
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.setAutoCommit(b);
	}
	@Override
	public void commit() throws Throwable {
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.commit();
	}
	@Override
	public void rollback() throws Throwable {
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.rollback();
	}
	@Override
	public void close() {
		CloseUtil.close(this.bbm);
		this.bbm = null;
		super.close();
	}
	@Override
	public void clear() {
		if (this.bbm!=null)
			this.bbm.clear();
		super.clear();
	}
	
}
