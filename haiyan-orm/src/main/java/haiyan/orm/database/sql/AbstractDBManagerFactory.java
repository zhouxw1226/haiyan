package haiyan.orm.database.sql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import haiyan.common.SysCode;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.common.intf.factory.IFactory;
import haiyan.config.castorgen.root.DataSource;
import haiyan.config.castorgen.root.JdbcURL;
import haiyan.config.util.ConfigUtil;
import haiyan.database.DBCPDatabase;
import haiyan.database.JDBCDatabase;
import haiyan.database.JNDIDatabase;

public abstract class AbstractDBManagerFactory implements IFactory {
	// DataBase必须是单例的，否则类似DBCP的DataSource不能起到Connection复用的效果。
	protected static final Map<String, ISQLDatabase> DATABASES = new ConcurrentHashMap<String, ISQLDatabase>();
	public static ISQLDatabase createDatabase(String DSN) throws Throwable {
		if (DATABASES.containsKey(DSN))
			return DATABASES.get(DSN);
		ISQLDatabase database = null;
		String dbType = null;
		JdbcURL jdbcURL = ConfigUtil.getJdbcURL(DSN);
		if (jdbcURL!=null) {
			if (jdbcURL.isDbcp())
				database = new DBCPDatabase(jdbcURL.getDriver(), jdbcURL.getUrl(), jdbcURL.getUsername(), jdbcURL.getPassword());
			else
				database = new JDBCDatabase(jdbcURL.getDriver(), jdbcURL.getUrl(), jdbcURL.getUsername(), jdbcURL.getPassword());
			dbType = jdbcURL.getDbType();
		}
		DataSource dataSource = ConfigUtil.getDataSource(DSN);
		if (dataSource!=null) {
			database = new JNDIDatabase(dataSource.getJndi());
			dbType = dataSource.getDbType();
		} 
		if (database==null)
	        throw new Warning(SysCode.SysCodeNum.NO_MATCHEDDSN,SysCode.SysCodeMessage.NO_MATCHEDDSN);
		database.setDBType(dbType);
		DATABASES.put(DSN, database);
		return database;
	}

}
