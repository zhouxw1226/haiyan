package haiyan.common.intf.database.sql;

import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.session.IDBSession;

import java.sql.Connection;
import java.sql.Savepoint;

/**
 * 基于SQL数据库DB管理器
 * 
 * @author ZhouXW
 *
 */
public interface ISQLDBManager extends IDBSession {

	Connection getConnectionOnly();
	void setConnection(Connection conn) throws Throwable;
	Connection getConnection() throws Throwable;
	Connection getConnection(boolean openTrans) throws Throwable;
	IDatabase getDatabase();
	Savepoint getSavepoint() throws Throwable;
}
