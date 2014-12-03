package haiyan.common.intf.database;

import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.session.IUser;

import java.io.Serializable;

public interface IDBBill extends Serializable {

	IUser getUser();
	void setUser(IUser user);
	IBillConfig getBillConfig();
	void setBillConfig(IBillConfig billConfig);
	
	IDBResultSet[] getResultSets();
	void setResultSets(IDBResultSet[] resultSets);
	IDBResultSet getResultSet(int index);
	void setResultSet(int index, IDBResultSet rst);
	
	IDBFilter[] getFilters();
	void setFilters(IDBFilter[] filters);
	IDBFilter getFilter(int index);
	void setFilter(int index, IDBFilter filter);
	
	Object getValue(String name);
	void setValue(String name, Object value);
	Object getBillID();
	void setBillID(Object billID);
	
}
