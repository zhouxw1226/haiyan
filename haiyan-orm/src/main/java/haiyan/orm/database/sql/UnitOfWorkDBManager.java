package haiyan.orm.database.sql;

import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.database.sql.ITableSQLRender;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnitOfWorkDBManager extends SQLTableDBManager {
	public UnitOfWorkDBManager(ISQLDatabase db) {
		super(db);
	}
	@Override
	public ITableSQLRender getSQLRender() {
		return new SQLRender();
	}
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id,
			short type, int... args) throws Throwable {
		return super.select(context, table, id, type, args);
	}
	@Override
	protected IDBResultSet selectByLimit(final ITableDBContext context, final Table table, IDBFilter filter,
			long startNum, int count, int... args) throws Throwable {
		return super.selectByLimit(context, table, filter, startNum, count, args);
	}
	protected IDBResultSet selectBy(final ITableDBContext context, final Table table, IDBFilter filter,
			int maxPageRecordCount, int currPageNO,  int... args) throws Throwable {
		return super.selectBy(context, table, filter, maxPageRecordCount, currPageNO, args);
	}
	protected IDBResultSet selectBy(final ITableDBContext context, final Table table, IDBRecord queryRecord,
			int maxPageRecordCount, int currPageNO,  int... args) throws Throwable {
		return super.selectBy(context, table, queryRecord, maxPageRecordCount, currPageNO, args);
	}
	@Override
	protected List<IDBRecord> insertNoSyn(ITableDBContext context, Table table, List<IDBRecord> records, int... args) throws Throwable {
		String dbName = table.getName();
		if (!INSERTS.containsKey(dbName))
			INSERTS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = INSERTS.get(dbName);
		for (IDBRecord record:records) {
			record.updateVersion();
			map.put((String)record.get(table.getId().getName()), record);
		}
		return records;
	}
	@Override
	protected IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record, int... args) throws Throwable { 
		record.updateVersion();
		String dbName = table.getName();
		if (!INSERTS.containsKey(dbName))
			INSERTS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = INSERTS.get(dbName);
		map.put((String)record.get(table.getId().getName()), record);
		return record;
	}
	@Override
	protected List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, int... args) throws Throwable {
		String dbName = table.getName();
		if (!UPDATES.containsKey(dbName))
			UPDATES.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = UPDATES.get(dbName);
		for (IDBRecord record:records) {
			this.checkVersion(context, table, record);
			record.updateVersion();
			map.put((String)record.get(table.getId().getName()), record);
		}
		return records;
	}
	@Override
	protected IDBRecord update(ITableDBContext context, Table table, IDBRecord record, int... args) throws Throwable {
		this.checkVersion(context, table, record);
		record.updateVersion();
		String dbName = table.getName();
		if (!UPDATES.containsKey(dbName))
			UPDATES.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = UPDATES.get(dbName);
		map.put((String)record.get(table.getId().getName()), record);
		return record;
	}
	@Override
	public boolean delete(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable {
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
	@Deprecated
	public void commit() throws Throwable {
		throw new Warning("不支持此方法");
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
//			table = ConfigUtil.getTable(k);
			map = INSERTS.get(k);
			iter1 = map.keySet().iterator();
			while(iter1.hasNext()) {
				k1 = iter1.next();
				IDBRecord record = map.get(k1);
				doInsert(record);
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
				doUpdate(record);
			}
		}
		iter = DELETES.keySet().iterator();
		while(iter.hasNext()) {
			k = iter.next();
			table = ConfigUtil.getTable(k);
			list = DELETES.get(k);
			for (String id:list) {
				doDelete(id);
			}
			//super.delete(context, table, list.toArray());
		}
//		ITableRecordCacheManager cacheMgr = DBRecordCacheFactory.getCacheManager(type);
//		if (cacheMgr!=null) {
//			cacheMgr.updateCache(context, table, record, type);
//		}
	}
	protected void doInsert(IDBRecord record) {
		System.err.println("insertNoSyn:\t"+record);
		//super.insertNoSyn(context, table, record);
	}
	protected void doUpdate(IDBRecord record) {
		System.err.println("update:\t"+record);
		//super.update(context, table, record);
	}
	protected void doDelete(String id) {
		System.err.println("delete:\t"+id);
	}
	@Override
	@Deprecated
	public void rollback() throws Throwable {
		//throw new Warning("不支持此方法");
	}
	@Override
	public void rollback(ITableDBContext context) throws Throwable {
		OREIGNS.clear();
		INSERTS.clear();
		UPDATES.clear();
		DELETES.clear();
	}
	@Override
	public void close() {
		OREIGNS.clear();
		INSERTS.clear();
		UPDATES.clear();
		DELETES.clear();
		super.close();
	}
	@Override
	public void clear() {
		OREIGNS.clear();
		INSERTS.clear();
		UPDATES.clear();
		DELETES.clear();
		super.clear();
	}
	@Override
	protected void flushOreign(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		record.flushOreign();
		String dbName = table.getName();
		if (!OREIGNS.containsKey(dbName))
			OREIGNS.putIfAbsent(dbName, new HashMap<String,IDBRecord>());
		Map<String, IDBRecord> map = OREIGNS.get(dbName);
		map.put((String)record.get(table.getId().getName()), record);
	}
	// ------------------------------------------------------ 二级Cache ------------------------------------------------------ //
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
	}
	// NOTICE 只在getFormByRow后在会用到
	@Override
	public void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
//		ITableRecordCacheManager cacheMgr = DBRecordCacheFactory.getCacheManager(type);
//		if (cacheMgr!=null) {
//			cacheMgr.updateCache(context, table, record, type);
//		}
	}
	// ------------------------------------------------------ 一级Cache ------------------------------------------------------ //
	private ConcurrentHashMap<String,Map<String,IDBRecord>> OREIGNS = new ConcurrentHashMap<String,Map<String,IDBRecord>>();
	private ConcurrentHashMap<String,Map<String,IDBRecord>> INSERTS = new ConcurrentHashMap<String,Map<String,IDBRecord>>();
	private ConcurrentHashMap<String,Map<String,IDBRecord>> UPDATES = new ConcurrentHashMap<String,Map<String,IDBRecord>>();
	private ConcurrentHashMap<String,List<String>> DELETES = new ConcurrentHashMap<String,List<String>>();
}
