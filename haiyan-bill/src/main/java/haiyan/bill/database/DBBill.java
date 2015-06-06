package haiyan.bill.database;

import static haiyan.common.SysCode.SysCodeNum.NOT_INIT_FILTERS;
import haiyan.common.SysCode.SysCodeMessage;
import haiyan.common.SysCode.SysCodeNum;
import haiyan.common.exception.Warning;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.BillField;
import haiyan.config.castorgen.BillID;
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
	private IDBFilter[] filters;
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
		this.filters=new IDBFilter[tableCount];
		for (int i=0;i<tableCount;i++) {
			this.resultSets[i]=new DBPage(new ArrayList<IDBRecord>());
			this.filters[i]=null;
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
	public void setResultSet(int index, IDBResultSet rst){ 
		if (resultSets==null || resultSets.length<index)
			throw new Warning(this.user, SysCodeNum.NOT_INIT_RESULTSETS, SysCodeMessage.NOT_INIT_RESULTSETS);
		resultSets[index] = rst;
	}
	@Override
	public IDBResultSet getResultSet(int index){
		if (resultSets==null || resultSets.length<index)
			return null;
		return resultSets[index];
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
	public void setFilter(int index, IDBFilter filter){ 
		if (filters==null || filters.length<index)
			throw new Warning(this.user, NOT_INIT_FILTERS, SysCodeMessage.NOT_INIT_FILTERS);
		filters[index] = filter;
	}
	@Override
	public IDBFilter getFilter(int index){
		if (filters==null || filters.length<index)
			return null;
		return filters[index];
	}
	@Override
	public IDBFilter[] getFilters() {
		return this.filters;
	}
	@Override
	public void setFilters(IDBFilter[] filters) {
		this.filters = filters;
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

}
