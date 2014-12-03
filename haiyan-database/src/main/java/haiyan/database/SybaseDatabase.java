/*
 * Created on 2004-12-16
 *
 */
package haiyan.database;

import haiyan.common.DebugUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.sybase.jdbcx.SybDriver;

/**
 * @author zhouxw
 * 
 * 
 */
public class SybaseDatabase extends JDBCDatabase {
	/**
	 * Get static reference to Log4J Logger
	 */
	protected Properties props = new Properties();
	/**
	 * @param driver
	 * @param url
	 * @param userName
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public SybaseDatabase(String driver, String url, String userName,
			String password) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		super(driver, url, userName, password);
		SybDriver sybDriver = (SybDriver) Class.forName(_driver).newInstance();
		// sybDriver.setVersion(com.sybase.jdbcx.SybDriver.VERSION_5);
		DriverManager.registerDriver(sybDriver);
		// props.put(Context.PROVIDER_URL, super.get_url());
		props.put("user", _userName);
		props.put("password", _password);
		props.put("CHARSET_CONVERTER_CLASS", "com.sybase.jdbc2.utils.TruncationConverter");
	}
	@Override
	public Connection getDBConnection() throws Throwable {
		Connection conn = DriverManager.getConnection(_url, props);
		DebugUtil.debug(">----< dbm.open.connHash." + conn.hashCode()+", thin=SybaseDatabase Driver=" + _driver + ", URL="
				+ _url + ", UserName=" + _userName + ", Password=" + _password);
		return conn;
	}
	
}