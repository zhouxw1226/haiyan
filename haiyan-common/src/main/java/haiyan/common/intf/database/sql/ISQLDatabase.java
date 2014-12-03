package haiyan.common.intf.database.sql;

import haiyan.common.intf.database.IDatabase;

import java.sql.Connection;

public interface ISQLDatabase extends IDatabase {
	Connection getConnection() throws Throwable;
	void setDSN(String DSN);
	String getDSN();
	void setDBType(String dbType);
	String getDBType();
}
