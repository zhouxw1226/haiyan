package haiyan.common.intf.database.orm;

import haiyan.common.intf.database.IPredicate;

import java.util.Collection;

import net.sf.json.JSONArray;

public interface IDBResultSet {

	void setTotalRecordCount(long c);
	long getTotalRecordCount();
	int getPageIndex();
	int getMaxPageCount();
	int getPageRowCount();
	int getRecordCount();
	void setActiveRecord(int rowIndex);
	IDBRecord getRecord(int i);
	Collection<IDBRecord> getRecords();
	void setTableName(String name);
	String getTableName();
	IDBRecord getActiveRecord();
	
	IDBRecord find(IPredicate predicate);
	void filter(IPredicate predicate);
	IDBRecord insertRowAfter(int rowIndex);
	IDBRecord insertRowBefore(int rowIndex);
	IDBRecord appendRow();
	IDBRecord deleteRow(int rowIndex);
	
	JSONArray toJSon();
	
	void commit();
	void rollback();

}
