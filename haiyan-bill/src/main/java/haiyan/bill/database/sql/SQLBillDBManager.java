package haiyan.bill.database.sql;

import static haiyan.common.intf.database.orm.IDBRecord.UPDATE;
import static haiyan.config.util.ConfigUtil.getTable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import haiyan.bill.database.DBBill;
import haiyan.bill.database.IBillDBManager;
import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.IBillIDConfig;
import haiyan.common.intf.config.IBillTable;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBFilter;
import haiyan.common.intf.database.sql.ISQLDBManager;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBPage;
import haiyan.orm.database.sql.AbstractSQLDBManager;
import haiyan.orm.database.sql.DBBillAutoID;
import haiyan.orm.database.sql.SQLDBFilter;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

class SQLBillDBManager extends AbstractSQLDBManager implements IBillDBManager, ISQLDBManager {
	private transient List<IDBBill> billList = new ArrayList<IDBBill>();
	SQLBillDBManager(ISQLDatabase db) {
		super(db);
	}
	@Override
	public void commit() throws Throwable {
		if (this.isAlive()) {
			if (!this.connection.getAutoCommit()) { // 事务不是自动提交
				//this.beforeCommit(); // for hsqldb
				this.connection.commit(); // 主动提交
				this.connection.setAutoCommit(true); // 变回自动提交
				this.autoCommit=true;
				this.afterCommit(); // for hsqldb
			}
			DebugUtil.debug(">----< bbm.commit.connHash:" + this.connection.hashCode()
					+ "\tbbm.isAutoCommit:" + this.autoCommit);
		} else {
			DebugUtil.debug(">----< bbm.commit.visualHash:" + this.hashCode()
					+ "\tbbm.isAutoCommit:" + this.autoCommit);
		}
		// --------------------------------------------------------- //
		for (IDBBill bill:this.billList) {
			bill.commit(); // 内存单据提交
//			bill.clear();
		}
		// --------------------------------------------------------- //
		this.commited = true; // 事务是否结束
	}
	@Override
	public void rollback() throws Throwable {
		if (this.isAlive()) {
			if (!this.connection.getAutoCommit()) {
				this.beforeRollback(); // for hsqldb
				if (this.getSavepoint()!=null)
					this.connection.rollback(this.getSavepoint());
				else
					this.connection.rollback();
				this.connection.setAutoCommit(true); // 变回自动提交
				this.autoCommit=true;
				this.afterRollback(); // for hsqldb
			}
			DebugUtil.debug(">----< bbm.rollback.connHash:" + this.connection.hashCode()
				+ "\tbbm.isAutoCommit:" + this.autoCommit);
		} else {
			DebugUtil.debug(">----< bbm.rollback.visualHash:" + this.hashCode()
				+ "\tbbm.isAutoCommit:" + this.autoCommit);
		}
		// --------------------------------------------------------- //
		for (IDBBill bill:this.billList) {
			bill.rollback(); // 内存单据回滚
//			bill.clear();
		}
		// --------------------------------------------------------- //
		this.commited = false;
	}
	@Override
	public void clear() {
		if (this.billList!=null)
			this.billList.clear();
	}
	@Override
	public Object createBillID(IBillDBContext context, IDBBill bill) throws Throwable {
		if (!StringUtil.isEmpty(bill.getBillID()))
			return bill.getBillID();
		Bill billConfig = (Bill)bill.getBillConfig();
		Table mainTable = (Table)ConfigUtil.getMainTable(billConfig);
		{
			if (mainTable.getId().getJavaType()==AbstractCommonFieldJavaTypeType.STRING) {
				String billID = DBBillAutoID.genShortID(context, mainTable, 1);
				bill.setBillID(billID);
			} else {
				BigDecimal billID = DBBillAutoID.genNewID(context, mainTable, 1);
				bill.setBillID(billID);
			}
		}
		return bill.getBillID();
	}
	@Override
	public IDBBill createBill(IBillDBContext context, IBillConfig billConfig, boolean createBillID) throws Throwable {
		IDBBill bill = new DBBill(billConfig);
		if (createBillID)
			this.createBillID(context, bill);
		if (!billList.contains(bill))
			billList.add(bill);
		return bill;
	} 
	@Override
	public IDBBill createBill(IBillDBContext context,IBillConfig billConfig) throws Throwable {
		return createBill(context, billConfig, true);
	} 
	private ITableDBManager getDBManager(ITableDBContext context) throws Throwable {
		ITableDBManager dbm = context.getDBM();
		if (dbm instanceof ISQLDBManager) {
			Connection conn = this.getConnection();
			Connection old = ((ISQLDBManager) dbm).getConnectionOnly();
			if (old != null) {
				if (old != conn)
					throw new Warning("当前DBM中已经存在Connection");
			} else // 如果dbm里connection为空
				((ISQLDBManager) dbm).setConnection(conn);
		}
		return dbm;
	}
	@Override
	public void loadBill(IBillDBContext bContext, IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when loadBill");
		IDBFilter[] filters = bill.getDBFilters();
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		for (int i=0;i<billTables.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			IBillIDConfig billID = ConfigUtil.getBillIDConfig(billConfig, tableIndex);
			Table table = ConfigUtil.getTable(billTable.getDbName());
			IDBFilter filter = filters[tableIndex];
			if (filter == null && !StringUtil.isEmpty(bill.getBillID()))
				filter = new SQLDBFilter(" and t_1." + billID.getDbName() + "=? ", new Object[] { bill.getBillID() });
			if (filter == null) { // 没有filter会查出所有的数据
				throw new Warning("loadBill filter is null,please set filter or billid");
			}
			ITableDBContext context = bContext.getTableDBContext(); // bContext.getTableDBContext(tableIndex);
			ITableDBManager dbm = this.getDBManager(context);
			resultSets[tableIndex] = dbm.select(context, table, filter, DBPage.MAXCOUNT_PERQUERY, 1);
		}
		int mainTableIndex = ConfigUtil.getMainTableIndex(billConfig);
		IBillIDConfig billID = ConfigUtil.getBillIDConfig(billConfig, mainTableIndex);
		IDBResultSet mainResult = resultSets[mainTableIndex];
		for (IDBRecord record:mainResult.getRecords()) {
			bill.setBillID(record.get(billID.getDbName()));// 只用第一条记录的billid
			break;
		}
		if (!billList.contains(bill))
			billList.add(bill);
	}
	@Override
	public void saveBill(IBillDBContext bContext, IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when saveBill");
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		for (int i=0;i<resultSets.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			IDBResultSet rst = resultSets[tableIndex];
//			IBillIDConfig billID = ConfigUtil.getBillIDConfig(billConfig, tableIndex);
			Table table = ConfigUtil.getTable(billTable.getDbName());
			List<String> ids = new ArrayList<String>();
			for (IDBRecord record : rst.getRecords()) {
				String id = record.getString(table.getId().getName());
				if (record.getStatus() == IDBRecord.DELETE && !StringUtil.isEmpty(id)) {
					ids.add(id);
				}
			}
			ITableDBContext context = bContext.getTableDBContext();//bContext.getTableDBContext(tableIndex);
			ITableDBManager dbm = this.getDBManager(context);
			if (ids.size() > 0) {
				dbm.delete(context, table, ids.toArray(new String[0]));
			}
			if (bill.getBillID() == null) {
				String newID = (String) context.getNextID(table);
				bill.setBillID(newID);
			}
			for (IDBRecord record : rst.getRecords()) {
				// record.set(billID.getDbName(), bill.getBillID()); // 单据外键
				if (record.getStatus() == IDBRecord.INSERT) {
					dbm.insert(context, table, record);
				} else if (record.getStatus() == UPDATE) {
					dbm.update(context, table, record);
				}
			}
		}
	}
	//TODO 现在只能根据record ID删除，不能根据billId删除
	//需要提供按条件删除的接口
	@Override
	public void deleteBill(IBillDBContext bContext, IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		if (resultSets == null)
			throw new Warning("IDBResultSet lost when deleteBill");
		for (int i = 0; i < resultSets.length; i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			IDBResultSet rst = resultSets[tableIndex];
			Table table = ConfigUtil.getTable(billTable.getDbName());
			List<String> ids = new ArrayList<String>();
			for (IDBRecord record : rst.getRecords()) {
				String id = record.getString(table.getId().getName());
				if (record.getStatus() == IDBRecord.DELETE && !StringUtil.isEmpty(id)) {
					ids.add(id);
				}
			}
			if (ids.size() > 0) {
				ITableDBContext context = bContext.getTableDBContext();// bContext.getTableDBContext(tableIndex);
				ITableDBManager dbm = this.getDBManager(context);
				dbm.delete(context, table, ids.toArray(new String[0]));
			}
		}
	}
	@Override
	public void deleteBill(IBillDBContext bContext, IBillConfig billConfig, String billID) throws Throwable {
		IBillTable[] billTables = billConfig.getBillTable();
		if (billTables == null)
			throw new Warning("BillTable lost when deleteBill");
		if (billID == null)
			throw new Warning("BillID lost when deleteBill");
		for (int i = 0; i < billTables.length; i++) {
			IBillTable billTable = billTables[i];
			// int tableIndex = billTable.getTableIndex();
			Table table = ConfigUtil.getTable(billTable.getDbName());
			ITableDBContext context = bContext.getTableDBContext();// bContext.getTableDBContext(tableIndex);
			ITableDBManager dbm = this.getDBManager(context);
			dbm.delete(context, table, new String[]{billID});
		}
	}
	@Override
	public IDBResultSet query(IBillDBContext bContext, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, 
			int pageRowCount, int pageIndex, boolean override) throws Throwable {
		ITableDBContext context = bContext.getTableDBContext();//bContext.getTableDBContext(tableIndex);
		ITableDBManager dbm = this.getDBManager(context);
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = getTable(billTable.getDbName());
		IDBResultSet rst = dbm.select((ITableDBContext) context, table, dbFilter, pageRowCount, pageIndex);
		if (override)
			bill.setResultSet(tableIndex, rst);
		return rst;
	}
	@Override
	public IDBResultSet queryNext(IBillDBContext bContext, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, int pageRowCount, boolean override)
			throws Throwable {
		int pageIndex = bill.getResultSet(tableIndex).getPageIndex();
		int maxPageCount = bill.getResultSet(tableIndex).getTotalPageCount();
		if (pageIndex == maxPageCount)
			return null;
		ITableDBContext context = bContext.getTableDBContext();// bContext.getTableDBContext(tableIndex);
		ITableDBManager dbm = this.getDBManager(context);
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		int pageIndexNext = bill.getResultSet(tableIndex).getPageIndex()+1;
		if (pageRowCount <= 0)
			pageRowCount = bill.getResultSet(tableIndex).getPageRowCount();
		IDBResultSet rst = dbm.select((ITableDBContext) context, table, dbFilter, pageRowCount, pageIndexNext);
		if (override)
			bill.setResultSet(tableIndex, rst);
		return rst;
	}
	@Override
	public IDBResultSet queryPrev(IBillDBContext bContext, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, int pageRowCount, boolean override)
			throws Throwable {
		int pageIndex = bill.getResultSet(tableIndex).getPageIndex();
		if (pageIndex == 1)
			return null;
		ITableDBContext context = bContext.getTableDBContext();// bContext.getTableDBContext(tableIndex);
		ITableDBManager dbm = this.getDBManager(context);
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		int pageIndexPrev = bill.getResultSet(tableIndex).getPageIndex() - 1;
		if (pageRowCount <= 0)
			pageRowCount = bill.getResultSet(tableIndex).getPageRowCount();
		IDBResultSet rst = dbm.select((ITableDBContext) context, table, dbFilter, pageRowCount, pageIndexPrev);
		if (override)
			bill.setResultSet(tableIndex, rst);
		return rst;
	}
	@Override
	public void createConnection(ITableDBManager dbm) throws Throwable {
		//((ISQLDBManager)bbm)
		this.setConnection(((ISQLDBManager)dbm).getConnection());
	}
}