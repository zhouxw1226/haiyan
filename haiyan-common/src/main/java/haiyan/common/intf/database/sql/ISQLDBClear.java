package haiyan.common.intf.database.sql;

import haiyan.common.intf.database.IDBClear;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ISQLDBClear extends IDBClear {

	void addReader(Reader reader);

	void addBlob(Blob blob);

	void addInputStream(InputStream ins);

	void addConn(Connection conn);

	void addStat(Statement stat);

	void addRest(ResultSet rest);

}
