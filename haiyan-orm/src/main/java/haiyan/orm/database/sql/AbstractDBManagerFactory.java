package haiyan.orm.database.sql;

import java.util.HashMap;
import java.util.Map;

import haiyan.common.SysCode;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.factory.IFactory;
import haiyan.config.castorgen.root.DataSource;
import haiyan.config.castorgen.root.JdbcURL;
import haiyan.config.util.ConfigUtil;
import haiyan.database.DBCPDatabase;
import haiyan.database.JDBCDatabase;
import haiyan.database.JNDIDatabase;
import haiyan.database.MongoDatabase;

public abstract class AbstractDBManagerFactory implements IFactory {
	// DataBase必须是单例的，否则类似DBCP的DataSource不能起到Connection复用的效果。
	protected static final Map<String, IDatabase> DATABASES = new HashMap<String, IDatabase>(); // ConcurrentHashMap
	public static IDatabase createDatabase(String DSN) throws Throwable {
//		if (DATABASES.containsKey(DSN))
//		return DATABASES.get(DSN);
		if (!DATABASES.containsKey(DSN)) 
			synchronized(DATABASES) {
				if (!DATABASES.containsKey(DSN)) {
					IDatabase database = pCreateDatabase(DSN);
					DATABASES.put(DSN, database);
					return database;
				}
			}
		return DATABASES.get(DSN);
	}
	private static IDatabase pCreateDatabase(String DSN) throws Throwable {
		IDatabase database = null;
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
			dbType = dataSource.getDbType();
			if ("mongodb".equalsIgnoreCase(dbType)) {
				database = new MongoDatabase(dataSource.getJndi());
			} else {
				database = new JNDIDatabase(dataSource.getJndi());
			}
		} 
		if (database==null)
	        throw new Warning(SysCode.SysCodeNum.NO_MATCHEDDSN,SysCode.SysCodeMessage.NO_MATCHEDDSN+":"+DSN);
		database.setDBType(dbType);
		return database;
	}

}
