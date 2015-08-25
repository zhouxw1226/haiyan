package haiyan.orm.database.sql;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;

import haiyan.common.DebugUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCacheManager;
import haiyan.common.intf.database.sql.ISQLDBClear;
import haiyan.common.intf.database.sql.ISQLDBManager;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.config.castorgen.Table;
import haiyan.orm.database.AbstractCacheDBManager;
import haiyan.orm.database.TableDBRecordCacheFactory;
import haiyan.orm.intf.database.orm.ITableDBRecordCacheManager;
import haiyan.orm.intf.session.ITableDBContext;

public abstract class AbstractSQLDBManager extends AbstractCacheDBManager implements ISQLDBManager {
	// ================================================= //
	protected transient ISQLDBClear dbClear = new SQLDBClear();
	protected transient ISQLDatabase database;
	protected transient Connection connection;
	protected transient Savepoint savePoint;
	// ================================================= //
	protected transient volatile boolean autoCommit = true;
	protected transient volatile boolean commited = false;
//	protected transient String masterDSNSuffix;
//	protected transient String slaveDSNSuffix;
	/**
	 * @param db
	 */
	public AbstractSQLDBManager(ISQLDatabase db) {
		this.database = db;
	}
	protected void beforeCommit() {
	}
	protected void afterCommit() {
		this.savePoint = null;
	}
	protected void beforeRollback() {
	}
	protected void afterRollback() {
		this.savePoint = null;
	}
//	@Override
//	public String getMasterDSNSuffix() {
//		return masterDSNSuffix;
//	}
//	@Override
//	public void setMasterDSNSuffix(String masterDSNSuffix) {
//		this.masterDSNSuffix = masterDSNSuffix;
//	}
//	@Override
//	public String getSlaveDSNSuffix() {
//		return slaveDSNSuffix;
//	}
//	@Override
//	public void setSlaveDSNSuffix(String slaveDSNSuffix) {
//		this.slaveDSNSuffix = slaveDSNSuffix;
//	}
	@Override
	public IDatabase getDatabase() {
		return this.database;
	}
	@Override
	public String getDSN() {
		return this.database.getDSN();
	}
	@Override
	public Savepoint getSavepoint() {
		return this.savePoint;
	}
	@Override
	public Connection getConnectionOnly() {
		return this.connection;
	}
	@Override
	public void setConnection(Connection conn) throws Throwable {
		if (this.connection!=null)
			throw new Warning("当前AppSession中已经存在Connection");
		this.connection = conn;
	}
	/**
	 * 普通的查询操作不要开启事务即使beginTransaction了，除非做过一次insert update delete 或者 executeUpdate
	 * 
	 * 这里只返回当前连接
	 * 
	 * @return Connection
	 * @throws Throwable
	 */
	@Override
	public Connection getConnection() throws Throwable {
		if (this.connection == null) {
			if (this.database == null)
				throw new Warning("未知数据库类型");
			this.connection = this.database.getConnection();
		}
		return this.connection;
	}
	/**
	 * @return Connection
	 * @throws Throwable
	 */
	@Override
	public Connection getConnection(boolean openTrans) throws Throwable {
		if (this.connection == null) {
			if (this.database == null)
				throw new Warning("未知数据库类型");
			this.connection = this.database.getConnection();
		}
		if (openTrans && this.autoCommit == false) // 指定要开启事务,例如执行insert update delete 或者 executeUpdate
			if (this.isAlive() && this.connection.getAutoCommit()) { // 连接允许开启事务
				this.connection.setAutoCommit(false);
			}
		return this.connection;
	}
	/**
	 * 设置事务开关
	 * 
	 * @throws Throwable
	 */
	@Override
	public void setAutoCommit(boolean b) throws Throwable {
		if (this.autoCommit==b)
			return;
		this.autoCommit = b; // 如果此时没有conn也可以作为后面创建conn的依据
		if (this.isAlive() && !this.connection.getAutoCommit()) { // 此时conn可能没创建
			this.connection.setAutoCommit(b);
//			this.connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED)
		}
		DebugUtil.debug(">----< dbm.setAutoCommit:" + this.autoCommit);
	}
	/**
	 * 强制开启事务
	 * 
	 * @throws Throwable
	 */
	@Override
	public void openTransaction() throws Throwable {
		this.setAutoCommit(false); // 如果此时没有conn也可以作为后面创建conn的依据
		if (this.savePoint!=null)
			throw new Warning("openTransaction savePoint exists");
		this.savePoint = this.getConnection(true).setSavepoint();
	}
	/**
	 * 强制关闭事务
	 * 
	 * @throws Throwable
	 */
	@Override
	public void closeTransaction() throws Throwable {
		this.setAutoCommit(true); // 如果此时没有conn也可以作为后面创建conn的依据
	}
	@Override
	public Boolean isAlive() {
		try {
			return this.connection != null && !this.connection.isClosed();
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	// ------------------------------------------------------ Cache ------------------------------------------------------ //
	protected Map<Short,ITableDBRecordCacheManager> cacheMgr = new HashMap<Short, ITableDBRecordCacheManager>(); // 事务缓存管理器
	protected ITableDBRecordCacheManager getCacheMgr(short type) {
		if (!cacheMgr.containsKey(type)) {
			synchronized(this) {
				if (!cacheMgr.containsKey(type)) {
					ITableDBRecordCacheManager m = TableDBRecordCacheFactory.getCacheManager(type);
					cacheMgr.put(type, m);
				}
			}
		}
		return cacheMgr.get(type);
	}
	public IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable {
		ITableDBRecordCacheManager cacheMgr = getCacheMgr(type);
		if (cacheMgr!=null) {
			return cacheMgr.getCache(context, table, id);
		}
		return null;
	}
	public void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable {
		ITableDBRecordCacheManager cacheMgr = getCacheMgr(type);
		if (cacheMgr!=null) {
			cacheMgr.removeCache(context, table, ids);
		}
	}
	// NOTICE 在getFormByRow|insertNoSyn|update执行后在会调用到
	public void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		ITableDBRecordCacheManager cacheMgr = getCacheMgr(type);
		if (cacheMgr!=null) {
			cacheMgr.updateCache(context, table, record);
		}
	}
	public void commit() throws Throwable {
		// --------------------------------------------------------- //
		final IDBRecordCacheManager cacheMgr = this.getCacheMgr(IDBRecordCacheManager.CONTEXT_SESSION);
		try { // 内存记录提交
			if (cacheMgr!=null) 
				cacheMgr.commit();
		}catch(Throwable ex){
			throw Warning.wrapException(ex);
		}finally {
			if (cacheMgr!=null)
				cacheMgr.clear();
		}
		// --------------------------------------------------------- //
		this.commited = true; // 事务是否结束
//		// 清理缓存
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				synchronized(EVENTS) {
//					for (String s:EVENTS) {
//						String[] regTables = SQLRegUtil.getEventTableFromSQL(s);
//						cacheMgr.clearCache(regTables);
//					}
//					EVENTS.clear();
//				}
//			}
//		});
//		this.commited = true;
//		//this.autoCommit = true;
	}
	public void rollback() throws Throwable {
		// --------------------------------------------------------- //
		final IDBRecordCacheManager cacheMgr = this.getCacheMgr(IDBRecordCacheManager.CONTEXT_SESSION);
		try { // 内存记录提交
			if (cacheMgr!=null)
				cacheMgr.rollback();
		}catch(Throwable ex){
			throw Warning.wrapException(ex);
		}finally {
			if (cacheMgr!=null)
				cacheMgr.clear();
		}
		// --------------------------------------------------------- //
		this.commited = true; // 事务是否结束
//		// 清理缓存 回滚不清理缓存
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				synchronized(EVENTS) {
//					for (String s:EVENTS) {
//						String[] regTables = RegExpUtil.getTableFromSQL(s);
//						clearCache(regTables);
//					}
//					EVENTS.clear();
//				}
//			}
//		});
//		//this.autoCommit = true;
	}
	public void clear() {
		//LOCAL.remove();
		try {
			if (this.cacheMgr!=null)
				this.cacheMgr.clear();
		} catch (Throwable ignore) {
			ignore.printStackTrace();
		}
	}
}