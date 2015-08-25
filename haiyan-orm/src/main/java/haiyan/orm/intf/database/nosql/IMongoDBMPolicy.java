package haiyan.orm.intf.database.nosql;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.Mongo;

import haiyan.common.intf.database.IDBClear;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.config.castorgen.Table;
import haiyan.orm.intf.session.ITableDBContext;

public interface IMongoDBMPolicy {
	public void beforeCommit();
	public void afterCommit();
	public void beforeRollback();
	public void afterRollback();
	public Mongo getMongoDB();
	public String getMongoDBName();
	public DB getDB();
	public void setAutoCommit(boolean b) throws Throwable;
	public String getDSN();
	public IDatabase getDatabase();
	public IDBClear getClear();
	public Boolean isAlive();
	public void commit(ITableDBContext context) throws Throwable;
	public void rollback(ITableDBContext context) throws Throwable;
	public void commit() throws Throwable;
	public void rollback() throws Throwable;
	public void close();
	public void clear();
	public void openTransaction() throws Throwable;
	public void closeTransaction() throws Throwable;
	public void flushOreign(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable;
	public IDBRecord createRecord();
	public IDBRecord createRecord(Class<?> clz) throws Throwable;
	public boolean doDelete(ITableDBContext context, Table table, String[] ids) throws Throwable;
	public boolean delete(ITableDBContext context, Table table, String[] ids) throws Throwable;
	public IDBRecord doInsert(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	public IDBRecord insert(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	public IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	public List<IDBRecord> insert(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable;
	public IDBRecord doUpdate(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter) throws Throwable;
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable;
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, IDBFilter filter)
			throws Throwable;
	public IDBRecord select(ITableDBContext context, Table table, String id) throws Throwable;
	public IDBRecord select(ITableDBContext context, Table table, String id, short type, int... args) throws Throwable;
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids) throws Throwable;
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable;
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord recordQ, long startRow, int count)
			throws Throwable;
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, long startRow, int count)
			throws Throwable;
	public IDBResultSet select(ITableDBContext context, Table table, IDBRecord recordQ, int pageSize, int pageIndex)
			throws Throwable;
	public IDBResultSet select(ITableDBContext context, Table table, IDBFilter filter, int pageSize, int pageIndex)
			throws Throwable;
	public void loopBy(ITableDBContext context, Table table, IDBRecord recordQ, IDBRecordCallBack callback)
			throws Throwable;
	public void loopBy(ITableDBContext context, Table table, IDBFilter filter, IDBRecordCallBack callback)
			throws Throwable;
	public long countBy(ITableDBContext context, Table table, IDBRecord recordQ) throws Throwable;
	public long countBy(ITableDBContext context, Table table, IDBFilter filter) throws Throwable;
	public boolean isDBCorrect(Throwable ex);
	public Object[][] getResultArray(String SQL, int colNum, Object[] paras) throws Throwable;
	public int executeUpdate(String SQL, Object[] paras) throws Throwable;
	public IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable;
	public void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable;
	public void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable;
	// ------------------------------------------------------ status ------------------------------------------------------ //
	// 乐观离线锁 TODO 用MVCC多版本并发控制来做
	public void checkVersion(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
}
