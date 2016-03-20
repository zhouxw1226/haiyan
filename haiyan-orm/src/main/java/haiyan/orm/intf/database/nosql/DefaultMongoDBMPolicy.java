package haiyan.orm.intf.database.nosql;

import static haiyan.config.util.ConfigUtil.getDBName;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import haiyan.common.DebugUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDBClear;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCacheManager;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;
import haiyan.database.MongoDatabase;
import haiyan.orm.database.DBPage;
import haiyan.orm.database.DBRecord;
import haiyan.orm.intf.session.ITableDBContext;

public class DefaultMongoDBMPolicy implements IMongoDBMPolicy {

	protected transient MongoDatabase database;
	protected transient Mongo mongo;
	protected boolean transaction;
	/**
	 * @param db
	 */
	public DefaultMongoDBMPolicy(IDatabase db) {
		this.database = (MongoDatabase)db;
	}
	@Override
	public void beforeCommit() {
	}
	@Override
	public void afterCommit() {
	}
	@Override
	public void beforeRollback() {
	}
	@Override
	public void afterRollback() {
	}
	@Override
	public Mongo getMongoDB() {
		if (mongo==null) {
			synchronized(this) {
				if (mongo==null)
					mongo = database.getMongoDB();
			}
		}
		return mongo;
	}
	@Override
	public String getMongoDBName() {
		return database.getService();
	}
	@SuppressWarnings("deprecation")
	@Override
	public DB getDB() {
		return getMongoDB().getDB(getMongoDBName());
	}
	@Override
	public String getDSN() {
		return this.database.getDSN();
	}
	@Override
	public IDatabase getDatabase() {
		return this.database;
	}
	@Override
	public IDBClear getClear() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean isAlive() {
		return mongo!=null;
	}
	@Override
	public void commit(ITableDBContext context) throws Throwable {
		// TODO Auto-generated method stub
	}
	@Override
	public void rollback(ITableDBContext context) throws Throwable {
		// TODO Auto-generated method stub
	}
	@Override
	public void commit() throws Throwable {
		// TODO Auto-generated method stub
//		if (mongo!=null) 
//			mongo.unlock();
	}
	@Override
	public void rollback() throws Throwable {
		// TODO Auto-generated method stub
//		if (mongo!=null) 
//			mongo.unlock();
	}
	@Override
	public void close() {
		if (mongo!=null) {
//			mongo.unlock();
			mongo.close();
		}
		mongo = null;
		this.transaction = false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}
	@Override
	public void openTransaction() throws Throwable {
		this.setAutoCommit(false);
	}
	@Override
	public void closeTransaction() throws Throwable {
		this.setAutoCommit(true);
	}
	@Override
	public void setAutoCommit(boolean b) throws Throwable {
		if (b) { // 自动提交
//			Mongo mgo = this.getMongoDB();
//			mgo.unlock();
			this.transaction = false;
		} else {
//			Mongo mgo = this.getMongoDB();
//			mgo.fsyncAndLock();
			this.transaction = true;
		}
	}
	@Override
	public IDBRecord createRecord() {
		IDBRecord record = new DBRecord(); 
		return record;
	}
	@Override
	public IDBRecord createRecord(Class<?> clz) throws InstantiationException, IllegalAccessException {
		IDBRecord record = (IDBRecord)clz.newInstance();
		return record;
	}
	@Override
	public boolean delete(ITableDBContext context, Table table, String[] ids) throws Throwable {
		return doDelete(context, table, ids);
	}
	@Override
	public boolean doDelete(ITableDBContext context, Table table, String[] ids) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
		for (String id:ids) {
			coll.remove(new BasicDBObject("_id", new ObjectId(id))); 
//			coll.remove(new BasicDBObject(table.getId().getName(), new ObjectId(id))); 
		}
		return true;
	}
	@Override
	public IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table)); 
        DBObject bean = new BasicDBObject();
        bean.putAll(record.getDataMap());
        bean.put("_id", new ObjectId(record.getString(table.getId().getName())));
//      bean.put(table.getId().getName(), new ObjectId(record.getString(table.getId().getName())));
        coll.insert(bean);
		return record;
	}
	@Override
	public IDBRecord insert(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		return doInsert(context, table, record);
	}
	@Override
	public IDBRecord doInsert(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		String id = new ObjectId().toHexString();
		record.set(table.getId().getName(), id);
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table)); 
        DBObject bean = new BasicDBObject(); 
        bean.putAll(record.getDataMap());
        bean.put("_id", new ObjectId(record.getString(table.getId().getName())));
//        bean.put(table.getId().getName(), new ObjectId(record.getString(table.getId().getName())));
        coll.insert(bean);
		return record;
	}
	@Override
	public List<IDBRecord> insert(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
		List<DBObject> list = new ArrayList<DBObject>();
		for (IDBRecord record:records) {
	        DBObject bean = new BasicDBObject();
	        record.set(table.getId().getName(), new ObjectId());
	        bean.putAll(record.getDataMap());
	        bean.put("_id", record.get(table.getId().getName()));
	        list.add(bean);
		}
        coll.insert(list);
		return records;
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		return doUpdate(context, table, record);
	}
	@Override
	public IDBRecord doUpdate(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table)); 
        DBObject query = new BasicDBObject(); 
//      query.put(table.getId().getName(), record.get(table.getId().getName()));
        query.put("_id", new ObjectId(record.getString(table.getId().getName())));
        DBObject bean = new BasicDBObject(); 
        bean.putAll(record.getDataMap());
        coll.update(query, bean);
		return record;
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
		for (IDBRecord record:records) {
	        DBObject query = new BasicDBObject(); 
//	        query.put(table.getId().getName(), record.get(table.getId().getName()));
	        query.put("_id", new ObjectId(record.getString(table.getId().getName())));
	        DBObject bean = new BasicDBObject();
	        bean.putAll(record.getDataMap());
	        coll.update(query, bean);
		}
		return records;
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, IDBFilter filter)
			throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id) throws Throwable {
		return select(context, table, id, IDBRecordCacheManager.CONTEXT_SESSION);
	}
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id, short type, int... args) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
//		DBObject bean = coll.findOne(new BasicDBObject(table.getId().getName(), new ObjectId(id)));
		DBObject bean = coll.findOne(new BasicDBObject("_id", new ObjectId(id)));
		if (bean==null)
			return null;
		IDBRecord record = this.createRecord();
		record.putAll(bean.toMap());
		return record;
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids) throws Throwable {
		return select(context, table, ids, null);
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
		List<IDBRecord> list = new ArrayList<IDBRecord>();
		for (String id:ids) {
//			DBObject bean = coll.findOne(new BasicDBObject(table.getId().getName(), new ObjectId(id)));
			DBObject bean = coll.findOne(new BasicDBObject("_id", new ObjectId(id)));
			if (bean==null)
				continue;
			IDBRecord record = this.createRecord();
			record.putAll(bean.toMap());
			list.add(record);
		}
		IDBResultSet page = new DBPage(list);
		return page;
	}
	@SuppressWarnings("deprecation")
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord recordQ, long startRow, int count)
			throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
		List<IDBRecord> list = new ArrayList<IDBRecord>();
        DBObject query = new BasicDBObject(); 
        query.putAll(recordQ.getDataMap());
        DBObject protection = new BasicDBObject(); 
        DBCursor cursor = coll.find(query, protection, (int)startRow, count);
        while(cursor.hasNext()) {
        	DBObject bean = cursor.next();
			IDBRecord record = this.createRecord();
			record.putAll(bean.toMap());
			list.add(record);
        }
		IDBResultSet page = new DBPage(list);
		return page;
	}
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, long startRow, int count)
			throws Throwable {
		// TODO Auto-generated method stub
//		coll.find(query, projection, numToSkip, batchSize)
//		return null;
		throw new Warning("not impl");
	}
	@SuppressWarnings("deprecation")
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, IDBRecord recordQ, int pageSize, int pageIndex)
			throws Throwable {
		int startRow = pageSize*pageIndex;
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
		List<IDBRecord> list = new ArrayList<IDBRecord>();
        DBObject query = new BasicDBObject(); 
        query.putAll(recordQ.getDataMap());
        DBObject protection = new BasicDBObject(); 
        DBCursor cursor = coll.find(query, protection, (int)startRow, pageSize);
        while(cursor.hasNext()) {
        	DBObject bean = cursor.next();
			IDBRecord record = this.createRecord();
			record.putAll(bean.toMap());
			list.add(record);
        }
		IDBResultSet page = new DBPage(list);
		return page;
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, IDBFilter filter, int pageSize, int pageIndex)
			throws Throwable {
		// TODO Auto-generated method stub
//		int startRow = pageSize*pageIndex;
//		coll.find(query, projection, startRow, pageSize)
		throw new Warning("not impl");
	}
	@Override
	public void loopBy(ITableDBContext context, Table table, IDBRecord recordQ, IDBRecordCallBack callback)
			throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
        DBObject query = new BasicDBObject(); 
        query.putAll(recordQ.getDataMap());
        DBObject protection = new BasicDBObject(); 
        DBCursor cursor = coll.find(query, protection);
        while(cursor.hasNext()) {
        	DBObject bean = cursor.next();
			IDBRecord record = this.createRecord();
			record.putAll(bean.toMap());
			callback.call(record);
        }
	}
	@Override
	public void loopBy(ITableDBContext context, Table table, IDBFilter filter, IDBRecordCallBack callback)
			throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBRecord recordQ) throws Throwable {
		DB db = getDB();
		DBCollection coll = db.getCollection(getDBName(table));
        DBObject query = new BasicDBObject(); 
        query.putAll(recordQ.getDataMap());
        DBObject protection = new BasicDBObject(); 
        return coll.find(query, protection).count();
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBFilter filter) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public boolean isDBCorrect(Throwable ex) {
		return false;
	} 
	@Override
	public Object[][] getResultArray(String SQL, int colNum, Object[] paras) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public int executeUpdate(String SQL, Object[] paras) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	// ------------------------------------------------------ status ------------------------------------------------------ //
	@Override
	public void flushOreign(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		record.flushOreign();
	}
	// 乐观离线锁 TODO 用MVCC多版本并发控制来做
	public void checkVersion(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		if (!ConfigUtil.isORMUseCache() || !ConfigUtil.isORMSMCC() || table.getName().toUpperCase().startsWith("V_")) // 系统标记 
			return;
		if (ConfigUtil.getFieldByName(table, DataConstant.HYVERSION, true)==null) // 配置中取消检查的标记
			return;
		Boolean check = (Boolean)context.getAttribute(DataConstant.HYVERSIONCHECK); // 一个App生命周期中是否需要检查的标记
		if (check!=null && check==false)
			return;
		// 双缓冲：更新缓存后能提高DB->VO的转换效率
		// 要注意直接执行SQL语句的操作对缓冲数据的影响
		String IDName = table.getId().getName();
		String IDVal = (String)record.get(IDName);
		// NOTICE checkVersion这样会多一次网络和数据库访问，只用在非常严谨的企业应用里
		IDBRecord oldRecord = this.select(context, table, IDVal); // IDBRecordCacheManager.PERSIST_SESSION);
		if (oldRecord!=null && oldRecord!=record) { // 同一个dbsession中可能是一个
			int vOld = oldRecord.getVersion();
			int vNow = record.getVersion();
			if (vOld > vNow) {
				DebugUtil.debug(">vOld="+vOld+",vNow="+vNow);
				throw new Warning(VERSION_WARNING);
			}
		}
	}
	public final static String VERSION_WARNING = "当前数据已被修改,请重新加载数据后继续操作.";
	// ------------------------------------------------------ CRUD ------------------------------------------------------ //
}
