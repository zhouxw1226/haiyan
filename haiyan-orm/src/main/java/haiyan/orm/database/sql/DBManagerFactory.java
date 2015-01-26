package haiyan.orm.database.sql;

import haiyan.common.SysCode;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDBManager;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.root.DataSource;
import haiyan.config.castorgen.root.JdbcURL;
import haiyan.config.intf.database.sql.ITableSQLRender;
import haiyan.config.util.ConfigUtil;
import haiyan.database.DBCPDatabase;
import haiyan.database.JDBCDatabase;
import haiyan.database.JNDIDatabase;


public class DBManagerFactory implements IFactory {

	private DBManagerFactory() {
	}
	public static IDBManager createDBManager(IUser user)  {
		return createDBManager(user.getDSN());
	}
	public static IDBManager createDBManager(String DSN)  {
		try {
			ISQLDatabase database = createDatabase(DSN);
			if (database==null)
				throw new Warning(SysCode.SysCodeNum.NO_MATCHEDSQLDATABASE,SysCode.SysCodeMessage.NO_MATCHEDSQLDATABASE);
			String dbType = database.getDBType();
			database.setDSN(DSN);
			if ("mysql".equalsIgnoreCase(dbType)) 
				return new MySqlDBManager(database);
			else if ("oracle".equalsIgnoreCase(dbType)) 
				return new OracleDBManager(database);
			else if ("sqlserver".equalsIgnoreCase(dbType)) 
				return new SqlServerDBManager(database);
			else if ("derby".equalsIgnoreCase(dbType)) 
				return new DerbyDBManager(database);
			else if ("hsql".equalsIgnoreCase(dbType)) 
				return new HSqldbDBManager(database);
			else if ("jndi".equalsIgnoreCase(dbType)) 
				return new SQLTableDBManager(database) {
					@Override
					public ITableSQLRender getSQLRender() {
						return new SQLRender();
					}
				};
//			else if ("soap".equalsIgnoreCase(dbType)) 
//				return new SOAPDBManager(database);
			throw new Warning(SysCode.SysCodeNum.NO_MATCHEDDBMANAGER,SysCode.SysCodeMessage.NO_MATCHEDDBMANAGER);
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	public static ISQLDatabase createDatabase(String DSN) throws Throwable {
		ISQLDatabase database = null;
		JdbcURL jdbcURL = ConfigUtil.getJdbcURL(DSN);
		String dbType = null;
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
		return database;
	}

}
