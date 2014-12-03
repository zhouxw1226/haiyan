package haiyan.database;

import haiyan.common.DebugUtil;
import haiyan.common.intf.database.sql.ISQLDatabase;

import java.sql.Connection;

/**
 * SQLDatabase abstraction
 * 
 * @author zhouxw
 * 
 * @created 22. mars 2002
 * @todo Make use of registry prefs optional
 */
public abstract class SQLDatabase implements ISQLDatabase {

	public volatile static int connCount = 0; // 4 debug
	@Override
	public final Connection getConnection() throws Throwable {
		Connection conn = getDBConnection();
		connCount++;
		DebugUtil.debug(">----< db.open.connHash:"+conn.hashCode()+"\tdb.connCount:"+connCount);
		return conn;
	}
	protected abstract Connection getDBConnection() throws Throwable;
	@Override
	public abstract String getLabel();
	@Override
	public abstract String getURL() throws Throwable;
	private String dbType;
	
	public String getDBType() {
		return dbType;
	}
	public void setDBType(String dbType) {
		this.dbType = dbType;
	}
	private String DSN;
	@Override
	public String getDSN() {
		return DSN;
	}
	@Override
	public void setDSN(String dsn) {
		DSN = dsn;
	}
	
}