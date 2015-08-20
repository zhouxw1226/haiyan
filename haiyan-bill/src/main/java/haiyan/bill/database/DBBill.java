package haiyan.bill.database;

import static haiyan.common.SysCode.SysCodeNum.NOT_INIT_FILTERS;

import java.util.ArrayList;
import java.util.Iterator;

import haiyan.common.SysCode.SysCodeMessage;
import haiyan.common.SysCode.SysCodeNum;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.IBillField;
import haiyan.common.intf.config.IBillIDConfig;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.IPredicate;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.config.castorgen.Bill;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBPage;

/**
 * DB单据 Bean
 * 
 * @author ZhouXW
 *
 */
public class DBBill extends AbstractDBBill {

	private static final long serialVersionUID = 1L;
	private IBillConfig billConfig;
	private IDBResultSet[] resultSets;
	private IDBFilter[] dbFilters;
	private int tableCount;
	private Object billID;
	public DBBill(IBillConfig billConfig) {
		this.billConfig = billConfig;
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
			throw new Warning(SysCodeNum.NOT_INIT_RESULTSETS, SysCodeMessage.NOT_INIT_RESULTSETS);
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
			throw new Warning(NOT_INIT_FILTERS, SysCodeMessage.NOT_INIT_FILTERS);
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
		IBillIDConfig[] billIDs = billConfig.getBillID();
		for (IBillIDConfig billID:billIDs) {
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
		IBillField[] billFields = billConfig.getBillField();
		for (IBillField billField:billFields) {
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
		IBillIDConfig[] billIDs = billConfig.getBillID();
		for (IBillIDConfig billID:billIDs) {
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
					if (record.getStatus()!=IDBRecord.INSERT && record.getStatus()!=IDBRecord.DELETE)
						record.setStatus(IDBRecord.UPDATE);
				}
			}
		}
		IBillField[] billFields = billConfig.getBillField();
		for (IBillField billField:billFields) {
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
					if (record.getStatus()!=IDBRecord.INSERT && record.getStatus()!=IDBRecord.DELETE)
						record.setStatus(IDBRecord.UPDATE);
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
		IDBResultSet[] resultSets = this.getResultSets();
		IBillConfig billConfig = this.getBillConfig();
		for (int tableIndex=0;tableIndex<resultSets.length;tableIndex++) {
			IDBResultSet rst = resultSets[tableIndex];
			Iterator<IDBRecord> iter = rst.getRecords().iterator(); 
			while(iter.hasNext()) {
				IDBRecord record = iter.next();
				IBillIDConfig billIdConf = ConfigUtil.getBillIDConfig(billConfig, tableIndex);
//				Object recordbillIDVal = record.get(billIdConf.getDbName());
//				if (!StringUtil.isEmpty(recordbillIDVal) && !recordbillIDVal.equals(this.getBillID()))
//					throw new Warning("BillID exists when createBillByID:"+this.getBillID());
				record.set(billIdConf.getDbName(), billID); // 单据外键
			}
		}
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
	public IDBRecord insertRowBefore(int tableIndex, int rowIndex)
			throws Throwable {
		return this.resultSets[tableIndex].insertRowBefore(rowIndex);
	}
	@Override
	public IDBRecord insertRowAfter(int tableIndex, int rowIndex)
			throws Throwable {
		return this.resultSets[tableIndex].insertRowAfter(rowIndex);
	}
	@Override
	public IDBRecord appendRow(int tableIndex)
			throws Throwable {
		return this.resultSets[tableIndex].appendRow();
	}
	@Override
	public IDBRecord deleteRow(int tableIndex, int rowIndex)
			throws Throwable {
		return this.resultSets[tableIndex].deleteRow(rowIndex);
	}
	@Override
	public void commit() {
		if (this.resultSets==null)
			return;
		for (IDBResultSet rst:this.resultSets) {
			rst.commit();
		}
	}
	@Override
	public void rollback() {
		if (this.resultSets==null)
			return;
		for (IDBResultSet rst:this.resultSets) {
			rst.rollback();
		}
	}

}
