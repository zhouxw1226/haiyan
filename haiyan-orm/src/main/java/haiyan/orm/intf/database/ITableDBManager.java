package haiyan.orm.intf.database;

import haiyan.common.intf.database.IDBClear;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBManager;
import haiyan.config.castorgen.Table;
import haiyan.orm.intf.session.ITableDBContext;

import java.util.List;

/**
 * 基于表入口的DB管理器
 * 
 * 基于Table的DBManager
 * 
 * @author ZhouXW
 *
 */
public interface ITableDBManager extends ISQLDBManager {
	IDBRecord createRecord();
	IDBRecord createRecord(Class<?> clz) throws InstantiationException, IllegalAccessException;
	IDBClear getClear();

	boolean delete(ITableDBContext context, Table table, String[] ids) throws Throwable;
	IDBRecord insert(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	IDBRecord update(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter) throws Throwable;
	List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable;
	List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, IDBFilter filter) throws Throwable;
	void commit(ITableDBContext context) throws Throwable; // 4 UnitOfWork
	void rollback(ITableDBContext context) throws Throwable; // 4 UnitOfWork
	
	IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable;
	void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable;
	void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable;

	IDBRecord select(ITableDBContext context, Table table, String id) throws Throwable;
	IDBRecord select(ITableDBContext context, Table table, String id, short type, int... args) throws Throwable;
	IDBResultSet select(ITableDBContext context, Table table, String[] ids) throws Throwable;
	IDBResultSet select(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable;
	IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord record, long startRow, int count) throws Throwable;
	IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, long startRow, int count) throws Throwable;
	IDBResultSet select(ITableDBContext context, Table table, IDBRecord record, int pageRowCount, int pageIndex) throws Throwable;
	IDBResultSet select(ITableDBContext context, Table table, IDBFilter filter, int pageRowCount, int pageIndex) throws Throwable;
	void loopBy(ITableDBContext context, Table table, IDBRecord record, IDBRecordCallBack callback) throws Throwable;
	void loopBy(ITableDBContext context, Table table, IDBFilter filter, IDBRecordCallBack callback) throws Throwable;
	long countBy(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	long countBy(ITableDBContext context, Table table, IDBFilter filter) throws Throwable;
	
	Object[][] getResultArray(String SQL, int colNum, Object[] paras) throws Throwable;
	int executeUpdate(String SQL, Object[] paras) throws Throwable;
	IDatabase getDatabase();
	boolean isDBCorrect(Throwable ex);

}
