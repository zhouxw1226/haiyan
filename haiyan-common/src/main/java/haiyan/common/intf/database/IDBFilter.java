package haiyan.common.intf.database;

import haiyan.common.intf.database.orm.IDBRecord;

import java.util.List;

public interface IDBFilter {

	Object[] getParas();
	boolean filter(IDBRecord record);
	String getFilter();
	void setFilter(String sFilter);
}
