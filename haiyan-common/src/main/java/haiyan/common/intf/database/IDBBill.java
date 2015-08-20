package haiyan.common.intf.database;

import java.io.Serializable;

import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;

public interface IDBBill extends Serializable {

//	String getDSN();
//	void setDSN(String DSN);
	IBillConfig getBillConfig();
	void setBillConfig(IBillConfig billConfig);
	
	IDBResultSet[] getResultSets();
	void setResultSets(IDBResultSet[] resultSets);
	IDBResultSet getResultSet(int tableIndex);
	void setResultSet(int tableIndex, IDBResultSet rst);
	
	IDBFilter[] getDBFilters();
	void setDBFilters(IDBFilter[] filters);
	IDBFilter getDBFilter(int tableIndex);
	void setDBFilter(int tableIndex, IDBFilter filter);
	
	void setActiveRecord(int tableIndex, int rowIndex);
	Object getValue(String name);
	void setValue(String name, Object value);
	Object getBillID();
	void setBillID(Object billID);
	
	IDBRecord find(int tableIndex, IPredicate predicate);
	void filter(int tableIndex, IPredicate predicate);

	IDBRecord insertRowBefore(int tableIndex, int rowIndex) throws Throwable;
	IDBRecord insertRowAfter(int tableIndex, int rowIndex) throws Throwable;
	IDBRecord appendRow(int tableIndex) throws Throwable;
	IDBRecord deleteRow(int tableIndex, int rowIndex) throws Throwable;
	
	void commit();
	void rollback();
	
}
