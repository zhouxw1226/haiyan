package haiyan.common.intf.database.orm;

import java.util.Collection;

public interface IDBResultSet {

	void setTotalRecordCount(long c);
	long getTotalRecordCount();
	void setActiveRecord(int rowIndex);
	IDBRecord getRecord(int i);
	Collection<IDBRecord> getRecords();
	int getRecordCount();
	void setTableName(String name);
	String getTableName();
	IDBRecord getActiveRecord();

}
