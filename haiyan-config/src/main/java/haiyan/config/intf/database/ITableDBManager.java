package haiyan.config.intf.database;

import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.IDBManager;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.session.ITableDBContext;

import java.util.List;

/**
 * 基于表入口的DB管理器
 * 
 * @author ZhouXW
 *
 */
public interface ITableDBManager extends IDBManager {

	boolean delete(ITableDBContext context, Table table, String[] ids) throws Throwable;
	IDBRecord insert(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	IDBRecord update(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable;
	void commit(ITableDBContext context) throws Throwable;
	void rollback(ITableDBContext context) throws Throwable;
	
	IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable;
	void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable;
	void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable;

	IDBRecord select(ITableDBContext context, Table table, String id) throws Throwable;
	IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, long startRow, int count) throws Throwable;
	IDBResultSet select(ITableDBContext context, Table table, IDBRecord record, int maxPageCount, int page) throws Throwable;
	IDBResultSet select(ITableDBContext context, Table table, IDBFilter filter, int maxPageCount, int page) throws Throwable;
	void loopBy(ITableDBContext context, Table table, IDBRecord record, IDBRecordCallBack callback) throws Throwable;
	void loopBy(ITableDBContext context, Table table, IDBFilter filter, IDBRecordCallBack callback) throws Throwable;
	long countBy(ITableDBContext context, Table table, IDBFilter filter) throws Throwable;
	long countBy(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	
	Object[][] getResultArray(String SQL, int colNum, Object[] paras) throws Throwable;
	int executeUpdate(String SQL, Object[] paras) throws Throwable;

}
