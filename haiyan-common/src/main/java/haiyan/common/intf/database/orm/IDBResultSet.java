package haiyan.common.intf.database.orm;

import java.util.Collection;

public interface IDBResultSet {

	int getTotalRecordCount();
	IDBRecord getRecord(int i);
	Collection<IDBRecord> getRecords();
	void setTableName(String name);
	String getTableName();
	void setActiveRecord(int index);
	IDBRecord getActiveRecord();

}
