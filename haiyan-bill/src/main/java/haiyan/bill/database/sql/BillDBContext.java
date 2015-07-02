package haiyan.bill.database.sql;

import haiyan.bill.database.IBillDBManager;
import haiyan.common.CloseUtil;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.sql.DBBillAutoID;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

import java.util.ArrayList;
import java.util.List;

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
	public BillDBContext(IContext parent) { 
		super(parent);
	}
	private List<ITableDBContext> tableContexts = new ArrayList<ITableDBContext>();
	@Override
	public void setTableDBContext(int tableIndex, ITableDBContext context) {
		if(tableContexts.size()>tableIndex)
			tableContexts.set(tableIndex, context);
		else
			tableContexts.add(context);
	}
	@Override
	public ITableDBContext getTableDBContext(int tableIndex) {
		return tableContexts.get(tableIndex);
	}
	public Object getNextID(IBillConfig bill) throws Throwable {
		return DBBillAutoID.genShortID(this, ConfigUtil.getMainTable(bill), 100);
//		return UUID.randomUUID().toString();
	}
	private IBillDBManager bbm;
	@Override
	public void setBBM(IBillDBManager bbm) {
//		if (this.bbm!=null && this.bbm.isAlive())
//			CloseUtil.close(this.bbm);
		this.bbm = bbm;
	}
	@Override
	public IBillDBManager getBBM() {
		if (this.bbm!=null)
			return this.bbm;
		if (this.parent!=null && this.parent instanceof IBillDBContext)
			return ((IBillDBContext)this.parent).getBBM();
		return null;
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
		for (ITableDBContext context:this.tableContexts) {
			ITableDBManager dbm = context.getDBM();
			if(dbm != null)
				dbm.commit();
		}
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.commit();
	}
	@Override
	public void rollback() throws Throwable {
		for (ITableDBContext context:this.tableContexts) {
			ITableDBManager dbm = context.getDBM();
			if(dbm != null)
				dbm.rollback();
		}
		IBillDBManager bbm = this.getBBM();
		if (bbm==null)
			return;
		bbm.rollback();
	} 
	@Override
	public void close() {
		for (ITableDBContext context:this.tableContexts) {
			CloseUtil.close(context);
		}
		this.tableContexts.clear();

		CloseUtil.close(this.bbm);
		this.bbm = null;

		super.close();
	}
	@Override
	public void clear() {
		for (ITableDBContext context:this.tableContexts) {
			context.clear();
		}
//		this.tableContexts.clear();
		
		if (this.bbm!=null)
			this.bbm.clear();
		
		super.clear();
	}
	
}
