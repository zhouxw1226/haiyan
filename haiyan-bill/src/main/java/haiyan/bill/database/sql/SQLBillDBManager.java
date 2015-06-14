package haiyan.bill.database.sql;

import static haiyan.common.intf.database.orm.IDBRecord.UPDATE;
import static haiyan.config.util.ConfigUtil.getTable;
import haiyan.bill.database.DBBill;
import haiyan.bill.database.IBillDBManager;
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
import haiyan.orm.intf.session.ITableDBContext;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SQLBillDBManager extends AbstractSQLDBManager implements IBillDBManager, ISQLDBManager {
	private transient List<IDBBill> billList = new ArrayList<IDBBill>();
	public SQLBillDBManager(ISQLDatabase db) {
		super(db);
	}
	@Override
	public void commit() throws Throwable {
		if (this.connection!=null)
			this.connection.commit();
		for (IDBBill bill:this.billList) {
			bill.commit(); // 内存单据提交
		}
	}
	@Override
	public void rollback() throws Throwable {
		if (this.savePoint!=null && this.connection!=null)
			this.connection.rollback(this.savePoint);
		for (IDBBill bill:this.billList) {
			bill.rollback(); // 内存单据回滚
		}
	}
	@Override
	public void clear() {
		this.billList.clear();
	}
	@Override
	public void close() {
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
		IDBBill bill = new DBBill(context.getUser(), billConfig);
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
	@Override
	public void loadBill(IBillDBContext bContext, IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when loadBill");
		Connection conn = this.getConnection();
		IDBFilter[] filters = bill.getDBFilters();
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		for (int i=0;i<billTables.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			IBillIDConfig billID = ConfigUtil.getBillIDConfig(billConfig, tableIndex);
			Table table = ConfigUtil.getTable(billTable.getDbName());
			IDBFilter filter = filters[tableIndex];
			if (filter==null&&!StringUtil.isEmpty(bill.getBillID()))
				filter = new SQLDBFilter(" and t_1."+billID.getDbName()+"=? ", new Object[]{bill.getBillID()});
			if(filter == null){//没有filter会查出所有的数据
				throw new Warning("loadBill filter is null,please set filter or billid");
			}
			ITableDBContext context = bContext.getTableDBContext(tableIndex);
			context.getDBM().setConnection(conn);
			resultSets[tableIndex] = context.getDBM().select(context, table, filter, DBPage.MAXCOUNTPERQUERY, 1);
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
		Connection conn = this.getConnection();
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		for (int i=0;i<resultSets.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			IDBResultSet rst = resultSets[tableIndex];
			IBillIDConfig billID = ConfigUtil.getBillIDConfig(billConfig, tableIndex);
			Table table = ConfigUtil.getTable(billTable.getDbName());
			List<String> ids = new ArrayList<String>();
			for (IDBRecord record:rst.getRecords()) {
				String id = record.getString(table.getId().getName());
				if (record.getStatus()==IDBRecord.DELETE && !StringUtil.isEmpty(id)) {
					ids.add(id);
				}
			}
			ITableDBContext context = bContext.getTableDBContext(tableIndex);
			context.getDBM().setConnection(conn);
			if (ids.size()>0) {
				context.getDBM().delete(context, table, ids.toArray(new String[0]));
			}
			for (IDBRecord record:rst.getRecords()) {
				record.set(billID.getDbName(), bill.getBillID()); // 单据外键
				if (record.getStatus()==IDBRecord.INSERT) {
					context.getDBM().insert(context, table, record);
				} else if (record.getStatus()==UPDATE) {
					context.getDBM().update(context, table, record);
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
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when deleteBill");
		for (int i=0;i<resultSets.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			IDBResultSet rst = resultSets[tableIndex];
			Table table = ConfigUtil.getTable(billTable.getDbName());
			List<String> ids = new ArrayList<String>();
			for (IDBRecord record:rst.getRecords()) {
				String id = record.getString(table.getId().getName());
				if (record.getStatus()==IDBRecord.DELETE && !StringUtil.isEmpty(id)) {
					ids.add(id);
				}
			}
			if (ids.size()>0) {
				Connection conn = this.getConnection();
				ITableDBContext context = bContext.getTableDBContext(tableIndex);
				context.getDBM().setConnection(conn);
				context.getDBM().delete(context, table, ids.toArray(new String[0]));
			}
		}
	}
	@Override
	public void deleteBill(IBillDBContext bContext, IBillConfig billConfig, String billID) throws Throwable {
		IBillTable[] billTables = billConfig.getBillTable();
		if (billTables==null)
			throw new Warning("BillTable lost when deleteBill");
		if (billID==null)
			throw new Warning("BillID lost when deleteBill");
		Connection conn = this.getConnection();
		for (int i=0;i<billTables.length;i++) {
			IBillTable billTable = billTables[i];
			int tableIndex = billTable.getTableIndex();
			Table table = ConfigUtil.getTable(billTable.getDbName());
			ITableDBContext context = bContext.getTableDBContext(tableIndex);
			context.getDBM().setConnection(conn);
			context.getDBM().delete(context, table, new String[]{billID});
		}
	}
	@Override
	public IDBResultSet query(IBillDBContext bContext, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, int pageRowCount, int pageIndex, boolean override) throws Throwable {
		Connection conn = this.getConnection();
		ITableDBContext context = bContext.getTableDBContext(tableIndex);
		context.getDBM().setConnection(conn);
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = getTable(billTable.getDbName());
		IDBResultSet rst = context.getDBM().select((ITableDBContext)context, table, dbFilter, pageRowCount, pageIndex);
		if (override)
			bill.setResultSet(tableIndex, rst);
		return rst;
	}
	@Override
	public IDBResultSet queryNext(IBillDBContext bContext, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, int pageRowCount, boolean override)
			throws Throwable {
		int pageIndex = bill.getResultSet(tableIndex).getPageIndex();
		int maxPageCount = bill.getResultSet(tableIndex).getMaxPageCount();
		if (pageIndex==maxPageCount)
			return null;
		Connection conn = this.getConnection();
		ITableDBContext context = bContext.getTableDBContext(tableIndex);
		context.getDBM().setConnection(conn);
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		int pageIndexNext = bill.getResultSet(tableIndex).getPageIndex()+1;
		if (pageRowCount<=0)
			pageRowCount = bill.getResultSet(tableIndex).getPageRowCount();
		IDBResultSet rst = context.getDBM().select((ITableDBContext)context, table, dbFilter, pageRowCount, pageIndexNext);
		if (override)
			bill.setResultSet(tableIndex, rst);
		return rst;
	}
	@Override
	public IDBResultSet queryPrev(IBillDBContext bContext, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, int pageRowCount, boolean override)
			throws Throwable {
		int pageIndex = bill.getResultSet(tableIndex).getPageIndex();
		if (pageIndex==1)
			return null;
		Connection conn = this.getConnection();
		ITableDBContext context = bContext.getTableDBContext(tableIndex);
		context.getDBM().setConnection(conn);
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable billTable = billConfig.getBillTable()[tableIndex];
		Table table = ConfigUtil.getTable(billTable.getDbName());
		int pageIndexPrev = bill.getResultSet(tableIndex).getPageIndex()-1;
		if (pageRowCount<=0)
			pageRowCount = bill.getResultSet(tableIndex).getPageRowCount();
		IDBResultSet rst = context.getDBM().select((ITableDBContext)context, table, dbFilter, pageRowCount, pageIndexPrev);
		if (override)
			bill.setResultSet(tableIndex, rst);
		return rst;
	}
}