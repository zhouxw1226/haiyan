package haiyan.bill.database.sql;

import static haiyan.common.intf.database.orm.IDBRecord.UPDATE;
import haiyan.bill.database.DBBill;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.IBillID;
import haiyan.common.intf.config.IBillTable;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.bill.IDBBillManager;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.BillID;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBPage;
import haiyan.orm.database.sql.DBBillAutoID;
import haiyan.orm.database.sql.SQLDBFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLDBBillManager implements IDBBillManager {
	private ITableDBContext context;
	private List<IDBBill> billList = new ArrayList<IDBBill>();
	protected SQLDBBillManager(IContext context) {
		this.context = (ITableDBContext)context;
	}
	@Override
	public void openTransaction() throws Throwable {
		this.context.openTransaction();
	}
	@Override
	public void closeTransaction() throws Throwable {
		this.context.closeTransaction();
	}
	@Override
	public void setAutoCommit(boolean b) throws Throwable {
		this.context.setAutoCommit(b);
	}
	@Override
	public void commit() throws Throwable {
		this.context.commit(); // 上下文的提交
		for (IDBBill bill:this.billList) {
			bill.commit(); // 内存单据提交
		}
	}
	@Override
	public void rollback() throws Throwable {
		this.context.rollback(); // 上下文的回滚
		for (IDBBill bill:this.billList) {
			bill.rollback(); // 内存单据回滚
		}
	}
	@Override
	public String getDSN() {
		return this.context.getDSN();
	}
	@Override
	public Boolean isAlive() {
		return context.isAlive();
	}
	@Override
	public void clear() {
		this.context.clear();
		this.billList.clear();
	}
	@Override
	public void close() {
		this.context.close();
		this.billList.clear();
	}
	@Override
	public Object createBillID(IDBBill bill) throws Throwable {
		if (!StringUtil.isEmpty(bill.getBillID()))
			return bill.getBillID();
		IDBResultSet[] resultSets = bill.getResultSets();
		Bill billConfig = (Bill)bill.getBillConfig();
		Table mainTable = ConfigUtil.getMainTable(billConfig);
		{
			if (mainTable.getId().getJavaType()==AbstractCommonFieldJavaTypeType.STRING) {
				String billID = DBBillAutoID.genShortID(this.context, mainTable, 1);
				bill.setBillID(billID);
			} else {
				BigDecimal billID = DBBillAutoID.genNewID(this.context, mainTable, 1);
				bill.setBillID(billID);
			}
		}
		for (int i=0;i<resultSets.length;i++) {
			IDBResultSet rst = resultSets[i];
			Iterator<IDBRecord> iter = rst.getRecords().iterator(); 
			while(iter.hasNext()) {
				IDBRecord record = iter.next();
				BillID billID = billConfig.getBillID(i);
				Object recordbillIDVal = record.get(billID.getDbName());
				if (!StringUtil.isEmpty(recordbillIDVal) && !recordbillIDVal.equals(bill.getBillID()))
					throw new Warning("BillID exists when createBillByID:"+bill.getBillID());
				record.set(billID.getDbName(), bill.getBillID()); // 单据外键
			}
		}
		return bill.getBillID();
	}
	@Override
	public IDBBill createBill(IBillConfig billConfig, boolean createBillID) throws Throwable {
		IDBBill bill = new DBBill(this.context.getUser(), billConfig);
		if (createBillID)
			this.createBillID(bill);
		if (!billList.contains(bill))
			billList.add(bill);
		return bill;
	} 
	@Override
	public IDBBill createBill(IBillConfig billConfig) throws Throwable {
		return createBill(billConfig, true);
	} 
	@Override
	public void loadBill(IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when loadBill");
		IDBFilter[] filters = bill.getDBFilters();
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		for (int tableIndex=0;tableIndex<billTables.length;tableIndex++) {
			IBillTable billTable = billTables[tableIndex];
			Table table = ConfigUtil.getTable(billTable.getDbName());
			IDBFilter filter = filters[tableIndex];
			if (filter==null&&!StringUtil.isEmpty(bill.getBillID()))
				filter = new SQLDBFilter(" and t_1."+billConfig.getBillID(tableIndex).getDbName()+"=? ", new Object[]{bill.getBillID()});
			resultSets[tableIndex] = context.getDBM().select(context, table, filter, DBPage.MAXCOUNTPERQUERY, 1);
		}
		int mainTableIndex = ConfigUtil.getMainTableIndex(billConfig);
		IBillID billID = billConfig.getBillID(mainTableIndex);
		IDBResultSet mainResult = resultSets[mainTableIndex];
		for (IDBRecord record:mainResult.getRecords()) {
			bill.setBillID(record.get(billID.getDbName()));// 只用第一条记录的billid
			break;
		}
		if (!billList.contains(bill))
			billList.add(bill);
	}
	@Override
	public void saveBill(IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when saveBill");
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		for (int tableIndex=0;tableIndex<resultSets.length;tableIndex++) {
			IDBResultSet rst = resultSets[tableIndex];
			IBillTable billTable = billTables[tableIndex];
			IBillID billID = billConfig.getBillID(tableIndex);
			Table table = ConfigUtil.getTable(billTable.getDbName());
			List<String> ids = new ArrayList<String>();
			for (IDBRecord record:rst.getRecords()) {
				String id = record.getString(table.getId().getName());
				if (record.getStatus()==IDBRecord.DELETE && !StringUtil.isEmpty(id)) {
					ids.add(id);
				}
			}
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
	@Override
	public void deleteBill(IDBBill bill) throws Throwable {
		IDBResultSet[] resultSets = bill.getResultSets();
		IBillConfig billConfig = bill.getBillConfig();
		IBillTable[] billTables = billConfig.getBillTable();
		if (resultSets==null)
			throw new Warning("IDBResultSet lost when deleteBill");
		for (int i=0;i<resultSets.length;i++) {
			IDBResultSet rst = resultSets[i];
			IBillTable billTable = billTables[i];
			Table table = ConfigUtil.getTable(billTable.getDbName());
			List<String> ids = new ArrayList<String>();
			for (IDBRecord record:rst.getRecords()) {
				String id = record.getString(table.getId().getName());
				if (record.getStatus()==IDBRecord.DELETE && !StringUtil.isEmpty(id)) {
					ids.add(id);
				}
			}
			if (ids.size()>0) {
				context.getDBM().delete(context, table, ids.toArray(new String[0]));
			}
		}
	}
	@Override
	public void deleteBill(IBillConfig billConfig, String billID) throws Throwable {
		IBillTable[] billTables = billConfig.getBillTable();
		if (billTables==null)
			throw new Warning("BillTable lost when deleteBill");
		if (billID==null)
			throw new Warning("BillID lost when deleteBill");
		for (int i=0;i<billTables.length;i++) {
			IBillTable billTable = billTables[i];
			Table table = ConfigUtil.getTable(billTable.getDbName());
			context.getDBM().delete(context, table, new String[]{billID});
		}
	}
}