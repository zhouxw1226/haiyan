package haiyan.bill.database;

import static haiyan.common.SysCode.SysCodeNum.NOT_INIT_FILTERS;
import haiyan.common.SysCode.SysCodeMessage;
import haiyan.common.SysCode.SysCodeNum;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.IBillTable;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.IPredicate;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBFilter;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.BillField;
import haiyan.config.castorgen.BillID;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.database.ITableDBManager;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBPage;

import java.util.ArrayList;

/**
 * DB单据
 * 
 * @author ZhouXW
 *
 */
public class DBBill extends AbstractDBBill {

	private static final long serialVersionUID = 1L;
	private Bill billConfig;
	private IUser user;
	private IDBResultSet[] resultSets;
	private IDBFilter[] dbFilters;
	private int tableCount;
	private Object billID;
	public DBBill(IUser user, IBillConfig billConfig) {
		this.user = user;
		this.billConfig = (Bill)billConfig;
		init();
	}
	private void init() {
		this.tableCount = billConfig.getBillTableCount();
		this.resultSets=new IDBResultSet[tableCount];
		this.dbFilters=new IDBFilter[tableCount];
		for (int i=0;i<tableCount;i++) {
			this.resultSets[i]=new DBPage(new ArrayList<IDBRecord>());
			this.dbFilters[i]=null;
		}
	}
	@Override
	public IUser getUser() {
		return this.user;
	}
	@Override
	public void setUser(IUser user) {
		this.user = user;
	}
	@Override
	public IBillConfig getBillConfig() {
		return billConfig;
	}
	@Override
	public void setBillConfig(IBillConfig billConfig) {
		this.billConfig = (Bill)billConfig;
	}
	// -------------------------------------------------------------------------------- //
	@Override
	public void setResultSet(int tableIndex, IDBResultSet rst){ 
		if (resultSets==null || resultSets.length<tableIndex)
			throw new Warning(this.user, SysCodeNum.NOT_INIT_RESULTSETS, SysCodeMessage.NOT_INIT_RESULTSETS);
		resultSets[tableIndex] = rst;
	}
	@Override
	public IDBResultSet getResultSet(int tableIndex){
		if (resultSets==null || resultSets.length<tableIndex)
			return null;
		return resultSets[tableIndex];
	}
	@Override
	public IDBResultSet[] getResultSets() {
		return this.resultSets;
	}
	@Override
	public void setResultSets(IDBResultSet[] resultSets) {
		this.resultSets = resultSets;
	}
	// -------------------------------------------------------------------------------- //
	@Override
	public void setDBFilter(int index, IDBFilter dbFilter){ 
		if (dbFilters==null || dbFilters.length<index)
			throw new Warning(this.user, NOT_INIT_FILTERS, SysCodeMessage.NOT_INIT_FILTERS);
		dbFilters[index] = dbFilter;
	}
	@Override
	public IDBFilter getDBFilter(int index){
		if (dbFilters==null || dbFilters.length<index)
			return null;
		return dbFilters[index];
	}
	@Override
	public IDBFilter[] getDBFilters() {
		return this.dbFilters;
	}
	@Override
	public void setDBFilters(IDBFilter[] dbFilters) {
		this.dbFilters = dbFilters;
	}
	// -------------------------------------------------------------------------------- //
	@Override
	public void setActiveRecord(int tableIndex, int rowIndex) {
		IDBResultSet rst = this.resultSets[tableIndex];
		rst.setActiveRecord(rowIndex);
	}
	@Override
	public Object getValue(String name) { // TODO 要做优化处理，将name和field做一个map索引
		BillID[] billIDs = billConfig.getBillID();
		for (BillID billID:billIDs) {
			if (name.equalsIgnoreCase(billID.getName())) {
				int tableIndex = billID.getTableIndex();
				if (this.resultSets==null)
					return null;
				if (this.resultSets[tableIndex]==null)
					return null;
				IDBResultSet rst = this.resultSets[tableIndex];
				IDBRecord record = rst.getActiveRecord();
				if (record!=null) {
					return record.get(billID.getDbName());
				}
			}
		}
		BillField[] billFields = billConfig.getBillField();
		for (BillField billField:billFields) {
			if (name.equalsIgnoreCase(billField.getName())) {
				int tableIndex = billField.getTableIndex();
				if (this.resultSets==null)
					return null;
				if (this.resultSets[tableIndex]==null)
					return null;
				IDBResultSet rst = this.resultSets[tableIndex];
				IDBRecord record = rst.getActiveRecord();
				if (record!=null) {
					return record.get(billField.getDbName());
				}
			}
		}
		return null;
	}
	@Override
	public void setValue(String name, Object value) { // TODO 要做优化处理，将name和field做一个map索引
		BillID[] billIDs = billConfig.getBillID();
		for (BillID billID:billIDs) {
			if (name.equalsIgnoreCase(billID.getName())) {
				int tableIndex = billID.getTableIndex();
				if (this.resultSets==null)
					return;
				if (this.resultSets[tableIndex]==null)
					return;
				IDBResultSet rst = this.resultSets[tableIndex];
				IDBRecord record = rst.getActiveRecord();
				if (record!=null) {
					record.set(billID.getDbName(), value);
				}
			}
		}
		BillField[] billFields = billConfig.getBillField();
		for (BillField billField:billFields) {
			if (name.equalsIgnoreCase(billField.getName())) {
				int tableIndex = billField.getTableIndex();
				if (this.resultSets==null)
					return;
				if (this.resultSets[tableIndex]==null)
					return;
				IDBResultSet rst = this.resultSets[tableIndex];
				IDBRecord record = rst.getActiveRecord();
				if (record!=null) {
					record.set(billField.getDbName(), value);
				}
			}
		}
	}
	@Override
	public Object getBillID() {
		return this.billID;
	}
	@Override
	public void setBillID(Object billID) {
		this.billID = billID;
	}
	@Override
	public IDBRecord find(int tableIndex, IPredicate predicate) {
		//if (tableIndex>=this.resultSets.length)
		IDBResultSet rst = this.resultSets[tableIndex];
		return rst.find(predicate);
//		return (IDBRecord)CollectionUtils.find(rst.getRecords(), predicate);
	}
	@Override
	public void filter(int tableIndex, IPredicate predicate) {
		//if (tableIndex>=this.resultSets.length)
		IDBResultSet rst = this.resultSets[tableIndex];
		rst.filter(predicate);
//		CollectionUtils.filter(rst.getRecords(), predicate);
	}
	@Override
	public IDBResultSet query(IContext context, int tableIndex, 
			ISQLDBFilter dbFilter, int pageRowCount, int pageIndex, boolean override) throws Throwable {
		IBillConfig billConfig = this.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		IDBResultSet rst = ((ITableDBManager)context.getDBM()).select((ITableDBContext)context, table, dbFilter, pageRowCount, pageIndex);
		if (override)
			this.resultSets[tableIndex]=rst;
		return rst;
	}
	@Override
	public IDBResultSet queryNext(IContext context, int tableIndex,
			ISQLDBFilter dbFilter, int pageRowCount, boolean override)
			throws Throwable {
		int maxPageCount = this.resultSets[tableIndex].getMaxPageCount();
		if (this.resultSets[tableIndex].getPageIndex()==maxPageCount)
			return null;
		IBillConfig billConfig = this.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		int pageIndexNext = this.resultSets[tableIndex].getPageIndex()+1;
		IDBResultSet rst = ((ITableDBManager)context.getDBM()).select((ITableDBContext)context, table, dbFilter, pageRowCount, pageIndexNext);
		if (override)
			this.resultSets[tableIndex]=rst;
		return rst;
	}
	@Override
	public IDBResultSet queryPrev(IContext context, int tableIndex,
			ISQLDBFilter dbFilter, int pageRowCount, boolean override)
			throws Throwable {
		if (this.resultSets[tableIndex].getPageIndex()==1)
			return null;
		IBillConfig billConfig = this.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		int pageIndexPrev = this.resultSets[tableIndex].getPageIndex()-1;
		IDBResultSet rst = ((ITableDBManager)context.getDBM()).select((ITableDBContext)context, table, dbFilter, pageRowCount, pageIndexPrev);
		if (override)
			this.resultSets[tableIndex]=rst;
		return rst;
	}

}
