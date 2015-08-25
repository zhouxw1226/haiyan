package haiyan.common.intf.database;

public interface IDatabase {
	String getURL() throws Throwable;
	String getLabel();
	void setDSN(String DSN);
	String getDSN();
	void setDBType(String dbType);
	String getDBType();
}
