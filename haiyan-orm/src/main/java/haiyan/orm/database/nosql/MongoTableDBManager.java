/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.nosql;

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
import haiyan.database.MongoDatabase;
import haiyan.orm.database.AbstractCacheDBManager;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.database.nosql.DefaultMongoDBMPolicy;
import haiyan.orm.intf.database.nosql.IMongoDBMPolicy;
import haiyan.orm.intf.database.nosql.UnitOfWorkMongoDBMPolicy;
import haiyan.orm.intf.session.ITableDBContext;

/**
 * HSqldb
 * 
 * @author zhouxw
 */
public class MongoTableDBManager extends AbstractCacheDBManager implements ITableDBManager {
	private IMongoDBMPolicy policyUnitOfWork;
	private IMongoDBMPolicy policyDefault;
	private IMongoDBMPolicy policy;
	protected transient MongoDatabase database;
//	protected transient Mongo mongo;
//	protected boolean transaction;
	/**
	 * @param db
	 */
	public MongoTableDBManager(IDatabase db) {
		this.database = (MongoDatabase)db;
		this.policyUnitOfWork = new UnitOfWorkMongoDBMPolicy(this.database);
		this.policyDefault = new DefaultMongoDBMPolicy(this.database);
		this.policy = policyDefault;
	}
	protected void beforeCommit() {
		policy.beforeCommit();
	}
	protected void afterCommit() {
		policy.afterCommit();
	}
	protected void beforeRollback() {
		policy.beforeRollback();
	}
	protected void afterRollback() {
		policy.afterRollback();
	}
	protected Mongo getMongoDB() {
		return policy.getMongoDB();
//		if (mongo==null) {
//			synchronized(this) {
//				if (mongo==null)
//					mongo = database.getMongoDB();
//			}
//		}
//		return mongo;
	}
	protected String getMongoDBName() {
		return policy.getMongoDBName();
//		return database.getService();
	}
//	@SuppressWarnings("deprecation")
	protected DB getDB() {
		return policy.getDB();
//		return getMongoDB().getDB(getMongoDBName());
	}
	@Override
	public String getDSN() {
		return policy.getDSN();
	}
	@Override
	public IDatabase getDatabase() {
		return policy.getDatabase();
	}
	@Override
	public IDBClear getClear() {
		return policy.getClear();
	}
	@Override
	public Boolean isAlive() {
		return policy.isAlive();
	}
	@Override
	public void commit(ITableDBContext context) throws Throwable {
		policy.commit(context);
	}
	@Override
	public void rollback(ITableDBContext context) throws Throwable {
		policy.rollback(context);
	}
	@Override
	public void commit() throws Throwable {
		policy.commit();
	}
	@Override
	public void rollback() throws Throwable {
		policy.rollback();
	}
	@Override
	public void close() {
		policyDefault.close();
		policyUnitOfWork.close();
	}
	@Override
	public void clear() {
		policyDefault.clear();
		policyUnitOfWork.clear();
	}
	@Override
	public void openTransaction() throws Throwable {
		policy = policyUnitOfWork;
		policy.openTransaction();
	}
	@Override
	public void closeTransaction() throws Throwable {
		policy.closeTransaction();
		policy = policyDefault;
	}
	@Override
	public void setAutoCommit(boolean b) throws Throwable {
		if (b) {
			policy.closeTransaction();
			policy = policyDefault;
		} else {
			policy = policyUnitOfWork;
			policy.openTransaction();
		}
		policy.setAutoCommit(b);
	}
	protected void flushOreign(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		policy.flushOreign(context,table,record,type);
	}
	@Override
	public IDBRecord createRecord() {
		return policy.createRecord();
	}
	@Override
	public IDBRecord createRecord(Class<?> clz) throws Throwable {
		return policy.createRecord(clz);
	}
	@Override
	public boolean delete(ITableDBContext context, Table table, String[] ids) throws Throwable {
		return policy.delete(context, table, ids);
	}
	@Override
	public IDBRecord insert(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		return policy.insert(context, table, record);
	}
	@Override
	public IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		return policy.insertNoSyn(context, table, record);
	}
	@Override
	public List<IDBRecord> insert(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
		return policy.insert(context, table, records);
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		return policy.update(context, table, record);
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter) throws Throwable {
		return policy.update(context, table, record, filter);
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
		return policy.update(context, table, records);
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, IDBFilter filter)
			throws Throwable {
		return policy.update(context, table, records, filter);
	}
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id) throws Throwable {
		return policy.select(context, table, id);
	}
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id, short type, int... args) throws Throwable {
		return policy.select(context, table, id, type, args);
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids) throws Throwable {
		return policy.select(context, table, ids);
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable {
		return policy.select(context, table, ids, args);
	}
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord recordQ, long startRow, int count)
			throws Throwable {
		return policy.selectByLimit(context, table, recordQ, startRow, count);
	}
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, long startRow, int count)
			throws Throwable {
		return policy.selectByLimit(context, table, filter, startRow, count);
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, IDBRecord recordQ, int pageSize, int pageIndex)
			throws Throwable {
		return policy.select(context, table, recordQ, pageSize, pageIndex);
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, IDBFilter filter, int pageSize, int pageIndex)
			throws Throwable {
		return policy.select(context, table, filter, pageSize, pageIndex);
	}
	@Override
	public void loopBy(ITableDBContext context, Table table, IDBRecord recordQ, IDBRecordCallBack callback)
			throws Throwable {
		policy.loopBy(context, table, recordQ, callback);
	}
	@Override
	public void loopBy(ITableDBContext context, Table table, IDBFilter filter, IDBRecordCallBack callback)
			throws Throwable {
		policy.loopBy(context, table, filter, callback);
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBRecord recordQ) throws Throwable {
		return policy.countBy(context, table, recordQ);
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBFilter filter) throws Throwable {
		return policy.countBy(context, table, filter);
	}
	@Override
	public boolean isDBCorrect(Throwable ex) {
		return policy.isDBCorrect(ex);
	} 
	@Override
	public Object[][] getResultArray(String SQL, int colNum, Object[] paras) throws Throwable {
		return policy.getResultArray(SQL,colNum,paras);
	}
	@Override
	public int executeUpdate(String SQL, Object[] paras) throws Throwable {
		return policy.executeUpdate(SQL,paras);
	}
	@Override
	public IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable {
		return policy.getCache(context, table, id, type);
	}
	@Override
	public void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable {
		policy.removeCache(context, table, ids, type);
	}
	@Override
	public void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		policy.updateCache(context, table, record, type);
	}
	// ------------------------------------------------------ status ------------------------------------------------------ //
	public void checkVersion(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		policy.checkVersion(context, table, record);
	}
	// ------------------------------------------------------ CRUD ------------------------------------------------------ //

}