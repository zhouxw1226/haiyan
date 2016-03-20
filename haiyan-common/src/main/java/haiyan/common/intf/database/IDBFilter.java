package haiyan.common.intf.database;

import haiyan.common.intf.database.orm.IDBRecord;

public interface IDBFilter {
	Object[] getParas();
	boolean filter(IDBRecord record);
	String getFilter();
	void setFilter(String sFilter);
}
