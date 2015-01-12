package haiyan.database;

import haiyan.common.DebugUtil;
import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Properties;

/**
 * Describe what this class does
 * 
 * @author zhouxw
 * @created 21. april 2002
 * @todo-javadoc Write javadocs
 */
public class DBCPDatabase extends SQLDatabase {
	public static Properties DBCP_PROPS = new Properties();
	/**
	 * @todo-javadoc Describe the column
	 */
	private final String _url;
	/**
	 * @todo-javadoc Describe the column
	 */
	private final String _userName;
	/**
	 * @todo-javadoc Describe the column
	 */
	private final String _password;
	/**
	 * 
	 */
	private final String _driver;
	/**
	 * 
	 */
	private final org.apache.commons.dbcp.BasicDataSource ds;
	/**
	 * @param dataSourceJNDIName
	 */
	public DBCPDatabase(String driver, String url, String user, String pass) {
		if (url == null) {
			throw new IllegalArgumentException("dbcp.url can't be null");
		}
		this._driver = driver;
		this._url = url;
		this._userName = user;
		this._password = pass;
		// DebugUtil.debug(">jndi=" + dataSourceJNDIName);
		ds = new org.apache.commons.dbcp.BasicDataSource();
		ds.setDriverClassName(_driver);
		ds.setUrl(_url);
		ds.setUsername(_userName);
		ds.setPassword(_password);
		// 自动关闭无效链接
		ds.setRemoveAbandoned(true);
		// ds.setRemoveAbandonedTimeout(ConnectionParam.TIMEOUT_TRANS);
		ds.setLogAbandoned(true);
//		Connection.TRANSACTION_NONE; // 可脏读和重复读
//		Connection.TRANSACTION_READ_COMMITTED; // 读提交（有人在读可写）
//		Connection.TRANSACTION_READ_UNCOMMITTED; // 读不提交（有人在读不可写，锁行）
//		Connection.TRANSACTION_REPEATABLE_READ; // 不可重复读（锁多行）
//		Connection.TRANSACTION_SERIALIZABLE; // 串行化（锁表）
		String s = PropUtil.getProperty("dbcp.defaultTransactionIsolation");
		if (!StringUtil.isEmpty(s)) {
			int defaultTransactionIsolation = VarUtil.toInt(s);
			if (defaultTransactionIsolation>=0) {
				ds.setDefaultTransactionIsolation(defaultTransactionIsolation);
			}
		}
		int maxActive = VarUtil.toInt(PropUtil.getProperty("dbcp.maxActive"));
		if (maxActive>=ds.getMaxActive()) {
			ds.setMaxActive(maxActive);
		}
		// ds.setMaxActive(ConnectionParam.MAX_CONN);
		// ds.setMaxWait(ConnectionParam.WAIT_TIME);
		// ds.setMaxIdle(DataSourceImp.MAX_CONN);
		Iterator<Object> iter = DBCP_PROPS.keySet().iterator();
		while(iter.hasNext()) {
			String k = iter.next().toString();
			String v = DBCP_PROPS.getProperty(k);
			ds.addConnectionProperty(k, v);
		}
	}
	@Override
	public Connection getDBConnection() throws Throwable {
		Connection conn = ds.getConnection();
		DebugUtil.debug(">----< dbm.open.connHash." + conn.hashCode()+", thin=DBCPDatabase Driver=" + _driver + ", URL="
				+ _url + ", UserName=" + _userName + ", Password=" + _password);
		return conn;
	}
	@Override
	public String getLabel() {
		return _driver + _url + _userName + _password;
	}
	@Override
	public String getURL() {
		return _url;
	}

}