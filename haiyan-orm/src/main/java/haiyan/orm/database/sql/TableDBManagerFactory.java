package haiyan.orm.database.sql;

import haiyan.common.SysCode;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IUser;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.database.sql.ITableSQLRender;

public class TableDBManagerFactory extends AbstractDBManagerFactory implements IFactory {
	private TableDBManagerFactory() {
	}
	public static ITableDBManager createDBManager(IUser user)  {
		return createDBManager(user.getDSN());
	}
	public static ITableDBManager createDBManager(String DSN)  {
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

}
