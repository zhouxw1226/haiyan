package haiyan.orm.database.sql;

import haiyan.common.DebugUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.database.sql.ISQLDBClear;
import haiyan.common.intf.database.sql.ISQLDBManager;
import haiyan.common.intf.database.sql.ISQLDatabase;

import java.sql.Connection;
import java.sql.Savepoint;

public abstract class AbstractSQLDBManager implements ISQLDBManager {
	protected transient volatile boolean autoCommit = true;
	protected transient volatile boolean commited = false;
	protected transient Connection connection; 
	protected transient ISQLDatabase database;
	protected transient ISQLDBClear dbClear = new SQLDBClear();
	protected transient Savepoint savePoint = null;
	/**
	 * @param conn
	 * @param notSameConn
	 */
	public AbstractSQLDBManager(ISQLDatabase db) {
		this.database = db;
	}
	@Override
	public IDatabase getDatabase() {
		return this.database;
	}
	@Override
	public String getDSN() {
		return database.getDSN();
	}
	@Override
	public Savepoint getSavepoint() {
		return this.savePoint;
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
	public abstract void commit() throws Throwable;
	public abstract void rollback() throws Throwable;

}
