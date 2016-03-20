package haiyan.orm.intf.database.nosql;

import static haiyan.config.util.ConfigUtil.getDBName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
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

public class UnitOfWorkMongoDBMPolicy implements IMongoDBMPolicy {

	//private 
	// ------------------------------------------------------ 一级Cache ------------------------------------------------------ //
	private ConcurrentHashMap<String,Map<String,IDBRecord>> OREIGNS = new ConcurrentHashMap<String,Map<String,IDBRecord>>();
	private ConcurrentHashMap<String,Map<String,IDBRecord>> INSERTS = new ConcurrentHashMap<String,Map<String,IDBRecord>>();
	private ConcurrentHashMap<String,Map<String,IDBRecord>> UPDATES = new ConcurrentHashMap<String,Map<String,IDBRecord>>();
	private ConcurrentHashMap<String,List<String>> DELETES = new ConcurrentHashMap<String,List<String>>();
	// ------------------------------------------------------ 一级Cache ------------------------------------------------------ //
	protected transient MongoDatabase database;
	protected transient Mongo mongo;
	protected boolean transaction;
	/**
	 * @param db
	 */
	public UnitOfWorkMongoDBMPolicy(IDatabase db) {
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
		Iterator<String> iter, iter1;
		String k, k1;
		Table table;
		Map<String, IDBRecord> map, map1;
		List<String> list;
		iter = OREIGNS.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			table = ConfigUtil.getTable(k);
			map = OREIGNS.get(k);
			iter1 = map.keySet().iterator();
			while(iter1.hasNext()) {
				k1 = iter1.next();
				IDBRecord record = map.get(k1);
				System.err.println("oreign:\t"+record);
				if (record.isDirty()) {
					String dbName = table.getName();
					if (!UPDATES.containsKey(dbName))
						UPDATES.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
					map1 = UPDATES.get(dbName);
					map1.put((String)record.get(table.getId().getName()), record);
				}
				//super.insertNoSyn(context, table, record);
			}
		}
		iter = INSERTS.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			table = ConfigUtil.getTable(k);
			map = INSERTS.get(k);
			iter1 = map.keySet().iterator();
			while(iter1.hasNext()) {
				k1 = iter1.next();
				IDBRecord record = map.get(k1);
				this.doInsert(context, table, record);
			}
		}
		iter = UPDATES.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			table = ConfigUtil.getTable(k);
			map = UPDATES.get(k);
			iter1 = map.keySet().iterator();
			while(iter1.hasNext()) {
				k1 = iter1.next();
				IDBRecord record = map.get(k1);
				this.doUpdate(context, table, record);
			}
		}
		iter = DELETES.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			table = ConfigUtil.getTable(k);
			list = DELETES.get(k);
			for (String id:list) {
				this.doDelete(context, table, new String[]{id});
			}
			//super.delete(context, table, list.toArray());
		}
//		ITableRecordCacheManager cacheMgr = DBRecordCacheFactory.getCacheManager(type);
//		if (cacheMgr!=null) {
//			cacheMgr.updateCache(context, table, record, type);
//		}
	}
	@Override
	public void rollback(ITableDBContext context) throws Throwable {
		OREIGNS.clear();
		INSERTS.clear();
		UPDATES.clear();
		DELETES.clear();
	}
	@Override
	public void commit() throws Throwable {
		// TODO Auto-generated method stub
//		if (mongo!=null) 
//			mongo.unlock();
		throw new Warning("please use commit(context)");
	}
	@Override
	public void rollback() throws Throwable {
		// TODO Auto-generated method stub
//		if (mongo!=null) 
//			mongo.unlock();
		throw new Warning("please use rollback(context)");
	}
	@Override
	public void close() {
		if (mongo!=null) {
//			mongo.unlock();
			mongo.close();
		}
		mongo = null;
		this.transaction = false;
		this.clear();
	}
	@Override
	public void clear() {
		OREIGNS.clear();
		INSERTS.clear();
		UPDATES.clear();
		DELETES.clear();
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
		record.set("_id", new ObjectId());
		return record;
	}
	@Override
	public IDBRecord createRecord(Class<?> clz) throws Throwable {
		IDBRecord record = (IDBRecord)clz.newInstance();
		record.set("_id", new ObjectId());
		return record;
	}
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
	public boolean delete(ITableDBContext context, Table table, String[] ids) throws Throwable {
//		DB db = getDB();
//		DBCollection coll = db.getCollection(ConfigUtil.getRealTableName(table));
//		for (String id:ids) {
//			coll.remove(new BasicDBObject("_id", new ObjectId(id))); 
////			coll.remove(new BasicDBObject(table.getId().getName(), new ObjectId(id))); 
//		}
//		return true;
		String dbName = table.getName();
		if (!DELETES.containsKey(dbName))
			DELETES.putIfAbsent(dbName, new ArrayList<String>());
		List<String> deletes = DELETES.get(dbName);
		Map<String, IDBRecord> map;
		if (!INSERTS.containsKey(dbName))
			INSERTS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		map = INSERTS.get(dbName);
		for (String id:ids) { 
			IDBRecord record = map.remove(id);
			if (record==null)
				deletes.add(id);
		}
		if (!UPDATES.containsKey(dbName))
			UPDATES.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		map = UPDATES.get(dbName);
		for (String id:ids) { 
			IDBRecord record = map.remove(id);
			if (record==null)
				deletes.add(id);
			else if (deletes.contains(id))
				deletes.remove(record.get(table.getId().getName()));
		}
		return true;
	}
	@Override
	public IDBRecord doInsert(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
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
	public IDBRecord insert(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		Object id = record.get("_id");
		if (id instanceof ObjectId)
			id = ((ObjectId)id).toHexString();
		else if (StringUtil.isEmpty(id))
			id = new ObjectId().toHexString();
		record.set(table.getId().getName(), id);
//		DB db = getDB();
//		DBCollection coll = db.getCollection(ConfigUtil.getRealTableName(table)); 
//        DBObject bean = new BasicDBObject(); 
//        bean.putAll(record.getDataMap());
//        bean.put("_id", new ObjectId(record.getString(table.getId().getName())));
////        bean.put(table.getId().getName(), new ObjectId(record.getString(table.getId().getName())));
//        coll.insert(bean);
//		return record;
		record.setTableName(table.getName());
  		record.updateVersion();
  		String dbName = table.getName();
  		if (!INSERTS.containsKey(dbName))
  			INSERTS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
  		Map<String, IDBRecord> map = INSERTS.get(dbName);
  		map.put(record.getString(table.getId().getName()), record);
		return record;
	}
	@Override
	public IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
//		DB db = getDB();
//		DBCollection coll = db.getCollection(ConfigUtil.getRealTableName(table)); 
//        DBObject bean = new BasicDBObject();
//        bean.putAll(record.getDataMap());
//        bean.put("_id", new ObjectId(record.getString(table.getId().getName())));
////      bean.put(table.getId().getName(), new ObjectId(record.getString(table.getId().getName())));
//        coll.insert(bean);
//		return record;
		record.setTableName(table.getName());
  		record.updateVersion();
  		String dbName = table.getName();
  		if (!INSERTS.containsKey(dbName))
  			INSERTS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
  		Map<String, IDBRecord> map = INSERTS.get(dbName);
  		map.put(record.getString(table.getId().getName()), record);
		return record;
	}
	@Override
	public List<IDBRecord> insert(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
//		DB db = getDB();
//		DBCollection coll = db.getCollection(ConfigUtil.getRealTableName(table));
//		List<DBObject> list = new ArrayList<DBObject>();
//		for (IDBRecord record:records) {
//	        DBObject bean = new BasicDBObject();
//	        record.set(table.getId().getName(), new ObjectId());
//	        bean.putAll(record.getDataMap());
//	        bean.put("_id", record.get(table.getId().getName()));
//	        list.add(bean);
//		}
//        coll.insert(list);
  		for (IDBRecord record:records) {
  			this.insert(context, table, record);
  		}
		return records;
	}
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
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		Object id = record.get("_id");
		if (id instanceof ObjectId) {
			record.set(table.getId().getName(), ((ObjectId)id).toHexString());
		}
//		DB db = getDB();
//		DBCollection coll = db.getCollection(ConfigUtil.getRealTableName(table)); 
//        DBObject query = new BasicDBObject(); 
////      query.put(table.getId().getName(), record.get(table.getId().getName()));
//        query.put("_id", new ObjectId(record.getString(table.getId().getName())));
//        DBObject bean = new BasicDBObject(); 
//        bean.putAll(record.getDataMap());
//        coll.update(query, bean);
//		return record;
//		this.checkVersion(context, table, record);
		record.updateVersion();
		String dbName = table.getName();
		if (!UPDATES.containsKey(dbName))
			UPDATES.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = UPDATES.get(dbName);
		map.put(record.getString(table.getId().getName()), record);
		return record;
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter) throws Throwable {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
//		DB db = getDB();
//		DBCollection coll = db.getCollection(ConfigUtil.getRealTableName(table));
//		for (IDBRecord record:records) {
//	        DBObject query = new BasicDBObject(); 
////	        query.put(table.getId().getName(), record.get(table.getId().getName()));
//	        query.put("_id", new ObjectId(record.getString(table.getId().getName())));
//	        DBObject bean = new BasicDBObject();
//	        bean.putAll(record.getDataMap());
//	        coll.update(query, bean);
//		}
  		for (IDBRecord record:records) {
  			this.update(context, table, record);
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
		{
	  		String dbName = table.getName();
	  		if (!OREIGNS.containsKey(dbName))
	  			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
	  		Map<String, IDBRecord> map = OREIGNS.get(dbName);
	  		map.put(record.getString(table.getId().getName()), record);
		}
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
			{
		  		String dbName = table.getName();
		  		if (!OREIGNS.containsKey(dbName))
		  			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		  		Map<String, IDBRecord> map = OREIGNS.get(dbName);
		  		map.put(record.getString(table.getId().getName()), record);
			}
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
			{
		  		String dbName = table.getName();
		  		if (!OREIGNS.containsKey(dbName))
		  			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		  		Map<String, IDBRecord> map = OREIGNS.get(dbName);
		  		map.put(record.getString(table.getId().getName()), record);
			}
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
			{
		  		String dbName = table.getName();
		  		if (!OREIGNS.containsKey(dbName))
		  			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		  		Map<String, IDBRecord> map = OREIGNS.get(dbName);
		  		map.put(record.getString(table.getId().getName()), record);
			}
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
		throw new Warning("not impl");
	}
	@Override
	public int executeUpdate(String SQL, Object[] paras) throws Throwable {
		throw new Warning("not impl");
	}
	@Override
	public IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable {
		String dbName = table.getName();
		if (!OREIGNS.containsKey(dbName))
			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = OREIGNS.get(dbName);
		if (map.containsKey(id))
			return map.get(id);
//		ITableRecordCacheManager cacheMgr = DBRecordCacheFactory.getCacheManager(type);
//		if (cacheMgr!=null) {
//			return cacheMgr.getCache(context, table, id, type);
//		}
		return null;
	}
	@Override
	public void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable {
//		ITableRecordCacheManager cacheMgr = DBRecordCacheFactory.getCacheManager(type);
//		if (cacheMgr!=null) {
//			cacheMgr.removeCache(context, table, ids, type);
//		}
		throw new Warning("not impl");
	}
	@Override
	public void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
//		ITableRecordCacheManager cacheMgr = DBRecordCacheFactory.getCacheManager(type);
//		if (cacheMgr!=null) {
//			cacheMgr.updateCache(context, table, record, type);
//		}
		throw new Warning("not impl");
	}
	// ------------------------------------------------------ status ------------------------------------------------------ //
	@Override
	public void flushOreign(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		record.flushOreign();
		String dbName = table.getName();
		if (!OREIGNS.containsKey(dbName))
			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = OREIGNS.get(dbName);
		map.put(record.getString(table.getId().getName()), record);
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
