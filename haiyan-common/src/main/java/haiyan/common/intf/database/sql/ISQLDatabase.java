package haiyan.common.intf.database.sql;

import java.sql.Connection;

import haiyan.common.intf.database.IDatabase;

public interface ISQLDatabase extends IDatabase {
	Connection getConnection() throws Throwable;
}
