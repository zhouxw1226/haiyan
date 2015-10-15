/*
 * Created on 2005-4-28
 *
 */
package haiyan.database;

import haiyan.common.DebugUtil;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author zhouxw
 * 
 */
public class LocalAccessDatabase extends SQLDatabase {

	private final static String preURL = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};";
	private final static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	private String address;
	private String user;
	private String pass;
	/**
	 * @param address
	 * @param user
	 * @param pass
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public LocalAccessDatabase(String address, String user, String pass)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		super();
		this.address = address;
		this.user = user;
		this.pass = pass;
		Class.forName(driver).newInstance();
	}
	/**
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public LocalAccessDatabase() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		super();
		Class.forName(driver).newInstance();
	}
	@Override
	public Connection getDBConnection() throws Throwable {
		String url = preURL + "DBQ=" + address;// db1.mdb
		Connection conn = DriverManager.getConnection(url, user, pass);
		DebugUtil.debug(">----< dbm.open.connHash." + conn.hashCode() + ", thin=LocalAccessDatabase, Driver=" + driver
				+ ", Address=" + address + ", UserName=" + user + ", Password=" + pass);
		return conn;
	}
	@Override
	public String getLabel() {
		return null;
	}
	@Override
	public String getURL() {
		return preURL;
	}
}