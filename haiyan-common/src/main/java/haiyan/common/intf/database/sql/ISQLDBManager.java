package haiyan.common.intf.database.sql;

import java.sql.Connection;

import haiyan.common.intf.database.IDBManager;

/**
 * 基于SQL数据库DB管理器
 * 
 * @author ZhouXW
 *
 */
public interface ISQLDBManager extends IDBManager {

	void setConnection(Connection conn) throws Throwable;
	Connection getConnection() throws Throwable;
	Connection getConnection(boolean openTrans) throws Throwable;
}
