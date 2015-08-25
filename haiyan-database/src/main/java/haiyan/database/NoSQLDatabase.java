package haiyan.database;

import haiyan.common.intf.database.nosql.INoSQLDatabase;

public abstract class NoSQLDatabase implements INoSQLDatabase {

//	private INoSQLDataSource ds;
	private String url = null;
	public NoSQLDatabase(String url) {
		this.url = url;
//		this.ds = new INoSQLDataSource() {
//			@Override
//			public String getURL() throws NamingException {
//				return NoSQLDatabase.this.url;
//			}
//		};
	}
	@Override
	public String getURL() {
		return this.url;
	}
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
	@Override
	public String getLabel() {
		return "NoSQLDatabase";
	}

}
