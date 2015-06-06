package haiyan.common.intf.database;

import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBFilter;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;

import java.io.Serializable;

public interface IDBBill extends Serializable {

	IUser getUser();
	void setUser(IUser user);
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
	IDBResultSet query(IContext context, int tableIndex, ISQLDBFilter dbFilter, 
			int pageRowCount, int pageIndex, boolean override) throws Throwable;
	IDBResultSet queryNext(IContext context, int tableIndex, ISQLDBFilter dbFilter,
			int pageRowCount, boolean override) throws Throwable;
	IDBResultSet queryPrev(IContext context, int tableIndex, ISQLDBFilter dbFilter,
			int pageRowCount, boolean override) throws Throwable;
	
}
