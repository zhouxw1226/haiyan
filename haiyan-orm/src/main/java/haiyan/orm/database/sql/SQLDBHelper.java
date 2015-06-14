/*
 * Created on 2004-10-22
 */
package haiyan.orm.database.sql;

import static haiyan.common.DebugUtil.debug;
import haiyan.common.CloseUtil;
import haiyan.common.DBColumn;
import haiyan.common.DBColumnVO;
import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.sql.ISQLDBListener;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.sql.query.LeftOuterJoinedTable;
import haiyan.orm.database.sql.query.PrimaryTable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author zhouxw
 */
public class SQLDBHelper {
	public static boolean useDBPool = false;
//	/**
//	 * @return SQLDatabase
//	 * @throws Throwable
//	 */
//	static SQLDatabase getDefaultDatabase() throws Throwable {
//		Config config = ConfigUtil.getConfig();
//		if (config.getJdbcURL() != null) {
//			if (useDBPool) {
//				DebugUtil.debug(">init DsnPoolDatabase");
//				JdbcURL jdbcURL = config.getJdbcURL();
//				return new DbcpDatabase(jdbcURL.getDriver(), jdbcURL.getUrl(),
//						jdbcURL.getUsername(), jdbcURL.getPassword());
//			} else {
//				DebugUtil.debug(">init DefaultJdbcDatabase");
//				JdbcURL jdbcURL = config.getJdbcURL();
//				return new StandardDatabase(jdbcURL.getDriver(), jdbcURL
//						.getUrl(), jdbcURL.getUsername(), jdbcURL.getPassword());
//			}
//		} else if (config.getDataSource() != null
//				&& !isEmpty(config.getDataSource().getJndi())) {
//			DebugUtil.debug(">init DefaultJndiDatabase");
//			return new JNDIDatabase(config.getDataSource().getJndi());
//		}
//		throw new Warning("Unknown database.");
//	}
//	/**
//	 * @param dsn
//	 * @return SQLDatabase
//	 * @throws Throwable
//	 */
//	static SQLDatabase getDatabase(String dsn) throws Throwable {
//		Config config = ConfigUtil.getConfig();
//		if (!StringUtil.isEmpty(dsn)) {
//			DbSource dbSource = config.getDbSource();
//			for (JdbcURL jdbc : dbSource.getJdbcURL()) {
//				if (dsn.equalsIgnoreCase(jdbc.getName())) {
//					if (useDBPool) {
//						DebugUtil.debug(">init DsnPoolDatabase");
//						return new DbcpDatabase(jdbc.getDriver(),
//								jdbc.getUrl(), jdbc.getUsername(), jdbc.getPassword());
//					} else {
//						DebugUtil.debug(">init DsnJdbcDatabase");
//						return new StandardDatabase(jdbc.getDriver(), jdbc
//								.getUrl(), jdbc.getUsername(), jdbc.getPassword());
//					}
//				}
//			}
//			for (DataSource ds : dbSource.getDataSource()) {
//				if (dsn.equalsIgnoreCase(ds.getName())) {
//					DebugUtil.debug(">init DsnJndiDatabase");
//					return new JNDIDatabase(ds.getJndi());
//				}
//			}
//		}
//		return getDefaultDatabase();
//	}
	/**
	 * @param sql
	 * @param colNum
	 * @param paras
	 * @param conn
	 * @return
	 * @throws Throwable
	 */
	static Object[][] getResultArray(String sql, int colNum, Object[] paras,
			Connection conn) throws Throwable {
		return getResultArray(sql, colNum, paras, conn, null);
	}
	/**
	 * @param sql
	 * @param colNum
	 * @param paras
	 * @param conn
	 * @return
	 * @throws Throwable
	 */
	static Object[][] getResultArray(String sql, int colNum, Object[] paras,
			Connection conn, ISQLDBListener dbListener) throws Throwable {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			debug(">getResultArray:" + sql);
			st = conn.prepareStatement(sql);
			if (paras!=null) {
				int length = paras.length;
				for (int i=0;i<length;i++) {
					st.setObject(i+1, paras[i]);
				}
			}
			rs = st.executeQuery();
			if (rs==null)
				throw new SQLException(sql);
			while (rs.next()) {
				if (dbListener != null) {
					dbListener.listen(rs);
				} else {
					Object[] row = new Object[colNum];
					for (int i = 0; i < colNum; i++) {
						row[i] = rs.getObject(i + 1);
					}
					result.add(row);
				}
			}
			return result.toArray(new Object[0][0]);
		}
		finally {
			CloseUtil.close(rs);
			CloseUtil.close(st);
		}
	}
//	static JSONArray getJSonArray(String sql,
//			IDB2JSon db2json, Connection conn) throws Throwable {
//		if (db2json==null)
//			throw new Warning("db2json lost");
//		JSONArray result = new JSONArray();
//		Statement st = null;
//		ResultSet rs = null;
//		try {
//			DebugUtil.debug(">getResultStrArray:" + sql);
//			//
//			st = conn.createStatement();
//			rs = st.executeQuery(sql);
//			while (rs.next()) {
//				JSONObject obj = db2json.rs2json(rs);
//				result.add(obj);
//			}
//			return result;
//		}
//		// catch (Throwable ex) {
//		// throw ex;
//		// }
//		finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(st);
//		}
//	}
	/**
	 * @param sql
	 * @param colNum
	 * @param conn
	 * @return Object[][]
	 * @throws Throwable
	 */
	static Object[][] getResultArray(String sql, int colNum, Connection conn) throws Throwable { 
		return getResultArray(sql, colNum, conn, null);
	}
	/**
	 * @param sql
	 * @param colNum
	 * @param conn
	 * @param dbListener
	 * @return Object[][]
	 * @throws Throwable
	 */
	static Object[][] getResultArray(String sql, int colNum,
			Connection conn, ISQLDBListener dbListener) throws Throwable {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Statement st = null;
		ResultSet rs = null;
		try {
			DebugUtil.debug(">getResultArray:" + sql);
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				if (dbListener != null) {
					dbListener.listen(rs);
				} else {
					Object[] row = new Object[colNum];
					for (int i = 0; i < colNum; i++) {
						row[i] = rs.getString(i + 1);
					}
					result.add(row);
				}
			}
			return (Object[][]) result.toArray(new Object[0][0]);
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(rs);
			CloseUtil.close(st);
		}
	}
	/**
	 * @param sql
	 * @param paras
	 * @param conn
	 * @return
	 * @throws Throwable
	 */
	static int executeUpdate(String sql, Object[] paras, Connection conn) throws Throwable {
		int result = 0;
		PreparedStatement st = null;
		try {
			DebugUtil.debug(">executeUpdate:" + sql);
			st = conn.prepareStatement(sql);
			if (paras!=null) {
				int length = paras.length;
				for (int i=0;i<length;i++) {
					st.setObject(i+1, paras[i]);
				}
			}
			result = st.executeUpdate();
			return result;
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(st);
		}
	}
	/**
	 * @param sql
	 * @param conn
	 * @return int
	 * @throws Throwable
	 */
	static int executeUpdate(String sql, Connection conn) throws Throwable {
		int result = 0;
		Statement st = null;
		try {
			DebugUtil.debug(">executeUpdate:" + sql);
			st = conn.createStatement();
			result = st.executeUpdate(sql);
			return result;
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(st);
		}
	}
	/**
	 * @param rs
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<String>> getStrResults(ResultSet rs) throws Throwable {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		// try {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		// DebugUtil.debug(">>columnCount=" + columnCount);
		while (rs != null && rs.next()) {
			Vector<String> tempVector = new Vector<String>();
			for (int i = 1; i <= columnCount; i++) {
				switch (metaData.getColumnType(i)) {
				case 4:
				case 8:
				case 2:
					tempVector.add(getNnofStrValue(rs.getBigDecimal(i)));
					break;
				default:
					tempVector.add(getNnofStrValue(rs.getString(i)));
					break;
				}
			}
			// DebugUtil.debug();
			data.add(tempVector);
		}
		// } catch (Throwable ex) {
		// throw ex;
		// }
		return data;
	}
	/**
	 * @param conn
	 * @param catalog
	 * @param schemaPattern
	 * @param tableNamePattern
	 * @param columnNamePattern
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<String>> getTableColumnsVector(Connection conn,
			String catalog, String schemaPattern, String tableNamePattern,
			String columnNamePattern) throws Throwable {
		Vector<Vector<String>> vc = null;
		ResultSet rs = null;
		DatabaseMetaData metaData = null;
		try {
			metaData = conn.getMetaData();
			// ResultSet getColumns(String catalog, String schemaPattern,
			// String tableNamePattern, String columnNamePattern){}
			rs = metaData.getColumns(schemaPattern, schemaPattern, tableNamePattern, columnNamePattern);
			// DebugUtil.debug(metaData.getColumnLabel(1));
			vc = getStrResults(rs);
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Throwable ex) {
				DebugUtil.error(ex);
			}
		}
		return vc;
	}
	/**
	 * @param conn
	 * @param catalog
	 * @param schema
	 * @param table
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<String>> getImportedKeysVC(Connection conn,
			String catalog, String schema, String table) throws Throwable {
		Vector<Vector<String>> vc = null;
		ResultSet rs = null;
		DatabaseMetaData metaData = null;
		try {
			metaData = conn.getMetaData();
			// ResultSet getTables(String catalog, String schemaPattern,
			// String tableNamePattern, String types[]) throws
			// TornadoSQLException;
			rs = metaData.getImportedKeys(catalog, schema, table);
			vc = getStrResults(rs);
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(rs);
		}
		return vc;
	}
	/**
	 * @param conn
	 * @param catalog
	 * @param schema
	 * @param table
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<String>> getExportedKeysVC(Connection conn,
			String catalog, String schema, String table) throws Throwable {
		Vector<Vector<String>> vc = null;
		ResultSet rs = null;
		DatabaseMetaData metaData = null;
		try {
			metaData = conn.getMetaData();
			// ResultSet getTables(String catalog, String schemaPattern,
			// String tableNamePattern, String types[]) throws
			// TornadoSQLException;
			rs = metaData.getExportedKeys(catalog, schema, table);
			vc = getStrResults(rs);
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(rs);
		}
		return vc;
	}
	/**
	 * @param conn
	 * @param catalog
	 * @param schema
	 * @param table
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<String>> getPrimaryKeysVC(Connection conn,
			String catalog, String schema, String table) throws Throwable {
		Vector<Vector<String>> vc = null;
		ResultSet rs = null;
		DatabaseMetaData metaData = null;
		try {
			metaData = conn.getMetaData();
			// ResultSet getTables(String catalog, String schemaPattern,
			// String tableNamePattern, String types[]) throws
			// TornadoSQLException;
			rs = metaData.getPrimaryKeys(catalog, schema, table);
			vc = getStrResults(rs);
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(rs);
		}
		return vc;
	}
	/**
	 * @param conn
	 * @param catalog
	 * @param schemaPattern
	 * @param tableNamePattern
	 * @param types
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<String>> getTablesVector(Connection conn,
			String catalog, String schemaPattern, String tableNamePattern,
			String types[]) throws Throwable {
		Vector<Vector<String>> vc = null;
		ResultSet rs = null;
		DatabaseMetaData metaData = null;
		try {
			metaData = conn.getMetaData();
			// ResultSet getTables(String catalog, String schemaPattern,
			// String tableNamePattern, String types[]) throws
			// TornadoSQLException;
			rs = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			vc = getStrResults(rs);
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(rs);
		}
		return vc;
	}
	/**
	 * @param rs
	 * @return Vector
	 * @throws Throwable
	 */
	static Vector<Vector<DBColumnVO>> getDBVOVCByRS(ResultSet rs)
			throws Throwable {
		Vector<Vector<DBColumnVO>> data = new Vector<Vector<DBColumnVO>>();
		Vector<DBColumn> cls = new Vector<DBColumn>();
		// try {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			DBColumn DBColumn = new DBColumn();
			DBColumn.setSchema(metaData.getSchemaName(i));
			DBColumn.setTable(metaData.getTableName(i));
			DBColumn.setColumn(metaData.getColumnName(i));
			DBColumn.setType(metaData.getColumnTypeName(i));
			DBColumn.setLength(Integer.toString(metaData.getColumnDisplaySize(i)));
			DBColumn.setNullable(Integer.toString(metaData.isNullable(i)));
			cls.add(DBColumn);
		}
		while (rs != null && rs.next()) {
			Vector<DBColumnVO> tempVector = new Vector<DBColumnVO>();
			for (int i = 1; i <= columnCount; i++) {
				DebugUtil.debug(">typecode=" + metaData.getColumnType(i) + ",");
				switch (metaData.getColumnType(i)) {
				case 4:
				case 8:
				case 2: {
					DBColumnVO vo = new DBColumnVO();
					vo.setData(getNnofStrValue(rs.getBigDecimal(i)));
					vo.setCls((DBColumn) cls.elementAt(i - 1));//
					tempVector.add(vo);
					break;
				}
				default: {
					DBColumnVO vo = new DBColumnVO();
					vo.setData(getNnofStrValue(rs.getString(i)));
					vo.setCls((DBColumn) cls.elementAt(i - 1));//
					tempVector.add(vo);
					break;
				}
				}
			}
			data.add(tempVector);
		}
		DebugUtil.debug("\n");
		// } catch (Throwable ex) {
		// throw ex;
		// }
		return data;
	}
	/**
	 * @param conn
	 * @return boolean
	 * @throws Throwable
	 */
	static boolean isSqlServer(Connection conn) throws Throwable {
		String dbType = getDBType(conn);
		return "SQLSERVER".equalsIgnoreCase(dbType)
				|| dbType.toLowerCase().indexOf("Microsoft SQL Server".toLowerCase()) > -1;
	}
	/**
	 * @param conn
	 * @return boolean
	 * @throws Throwable
	 */
	static boolean isOracle(Connection conn) throws Throwable {
		String dbType = getDBType(conn);
		return dbType.toLowerCase().indexOf("Oracle".toLowerCase()) > -1;
	}
	/**
	 * @param conn
	 * @return boolean
	 * @throws Throwable
	 */
	static boolean isMySQL(Connection conn) throws Throwable {
		String dbType = getDBType(conn);
		return dbType.toLowerCase().indexOf("MySQL".toLowerCase()) > -1;
	}
	/**
	 * @param conn
	 * @return boolean
	 * @throws Throwable
	 */
	static boolean isHSqldb(Connection conn) throws Throwable {
		String dbType = getDBType(conn);
		return dbType.toLowerCase().indexOf("Hsql".toLowerCase()) > -1;
	}
	/**
	 * @param conn
	 * @return boolean
	 * @throws Throwable
	 */
	static boolean isDerby(Connection conn) throws Throwable {
		String dbType = getDBType(conn);
		return dbType.toLowerCase().indexOf("Derby".toLowerCase()) > -1;
	}
	/**
	 * @param conn
	 * @return String
	 * @throws Throwable
	 */
	static String getDBType(Connection conn) throws Throwable {
		DatabaseMetaData metaData = conn.getMetaData();
		return metaData.getDatabaseProductName();
	}
	/**
	 * @param conn
	 * @return String
	 * @throws Throwable
	 */
	public static String getUserName(Connection conn) throws Throwable {
		// Vector vc = null;
		DatabaseMetaData metaData = conn.getMetaData();
		// DebugUtil.debug(metaData.getCatalogTerm());
		// DebugUtil.debug(metaData.getDatabaseProductName());
		// DebugUtil.debug(metaData.getSchemaTerm());
		// DebugUtil.debug(metaData.getURL());
		// DebugUtil.debug(metaData.get);
		if (metaData.getDatabaseProductName().equalsIgnoreCase("MySQL")) {
			String url = metaData.getURL();
			int max = url.indexOf("?");
			if (max == -1)
				max = url.indexOf("@");
			if (max == -1)
				max = url.length();
			return url.substring(url.lastIndexOf("/") + 1, max);
		}
		return metaData.getUserName();
	}
	/**
	 * @param conn
	 * @param catalog
	 * @return HashMap
	 * @throws Throwable
	 */
	public static HashMap<String, Serializable> getTableMap(Connection conn,
			String catalog) throws Throwable {
		// StringTokenizer stk = null;
		HashMap<String, Serializable> tableMap = new HashMap<String, Serializable>();
		Vector<String> tableNames = new Vector<String>();
		// try {
		// String[] types = new String[] { "TABLES" };
		Vector<Vector<String>> vc = getTablesVector(conn, null, catalog, null, null);
		// DebugUtil.debug(vc.size());
		for (int i = 0; i < vc.size(); i++) {
			if (vc.elementAt(i) instanceof Vector) {
				Vector<String> v = vc.elementAt(i);
				// 0:class,1:schema,2:table,3:type,4:un
				HashMap<String, DBColumn> columnMap = new HashMap<String, DBColumn>();
				String schemaName = v.elementAt(1).toString();
				String tableName = v.elementAt(2).toString();
				// DebugUtil.debug("schemaName=" + schemaName +
				// ",wantschema=" + wantschema + ",tableName=" + tableName);
				if (tableName.indexOf("/") == -1
						&& (schemaName.equalsIgnoreCase(catalog) || StringUtil.isEmpty(schemaName))) {
					tableName = tableName.toUpperCase();
					tableNames.add(tableName);
					Vector<Vector<String>> columns = SQLDBHelper.getTableColumnsVector(conn, null, catalog, tableName, null);
					Vector<Vector<String>> fkVector = getImportedKeysVC(conn, null, catalog, tableName);
					Vector<Vector<String>> pkVector = getPrimaryKeysVC(conn, null, catalog, tableName);
					// for (int f = 0; f < fkVector.size(); f++) {
					// DebugUtil.debug(vc.elementAt(i));
					// }
					// un,schema,table,column,
					// dbtype,type,length,un,
					// un,un,nullable,un,
					// defaultvalue,un,un,defaultlength,
					// showvalue,nullable
					for (int k = 0; k < columns.size(); k++) {
						// columns
						Vector<String> columnProp = columns.elementAt(k);
						String schema = columnProp.elementAt(1).toString();
						String table = columnProp.elementAt(2).toString();
						String column = columnProp.elementAt(3).toString();
						String dbtype = columnProp.elementAt(4).toString();
						String type = columnProp.elementAt(5).toString();
						String length = columnProp.elementAt(6).toString();
						String nullable = columnProp.elementAt(10).toString();
						String defaultvalue = columnProp.elementAt(11).toString();
						String defaultlength = columnProp.elementAt(14).toString();
						String id = columnProp.elementAt(16).toString();
						String dbnullable = columnProp.elementAt(17).toString();
						DBColumn dbColumn = new DBColumn();
						// DebugUtil.debug(columnProp.size());
						for (int f = 0; f < fkVector.size(); f++) {
							Vector<String> fvc = fkVector.get(f);
							if (fvc.elementAt(7).toString().equalsIgnoreCase(
									column)) {
								dbColumn.setFK(true);
								dbColumn.setRefTable(fvc.elementAt(2).toString());
								dbColumn.setRefField(fvc.elementAt(3).toString());
							}
						}
						// DebugUtil.debug(columnProp.size());
						for (int p = 0; p < pkVector.size(); p++) {
							Vector<String> pvc = pkVector.get(p);
							if (pvc.elementAt(3).toString().equalsIgnoreCase(column)) {
								dbColumn.setPK(true);
							}
						}
						dbColumn.setSchema(schema);
						dbColumn.setTable(table);
						dbColumn.setColumn(column);
						dbColumn.setDbtype(dbtype);
						dbColumn.setType(type);
						dbColumn.setLength(length);
						dbColumn.setNullable(nullable);
						dbColumn.setDefaultvalue(defaultvalue);
						dbColumn.setDefaultlength(defaultlength);
						dbColumn.setId(id);
						dbColumn.setDbnullable(dbnullable);
						columnMap.put(column.toUpperCase(), dbColumn);
					}
				}
				tableMap.put(tableName.toUpperCase(), columnMap);
			}
		}
		tableMap.put("ALL_DATABASE_NAME_VECTOR", tableNames);
		// } catch (Exception ex) {
		// throw ex;
		// }
		return tableMap;
	}
	/**
	 * @param value
	 * @return String
	 */
	static String getNnofStrValue(Object value) {
		if (value instanceof BigDecimal)
			return StringUtil.isEmpty(value) ? "0" : value.toString();
		else if (value != null)
			return value.toString();
		return "";
	}
	/**
	 * 
	 */
	public static void shutdownService() {
//		long t = System.currentTimeMillis();
//		// 10000*10000*3=1~2s
//		// 30000*30000*3=4s~5s
//		TaskManager manager = new MultiTaskManager();
//		manager.setTimeOut(30000);// ms
//		manager.addTask(new AsyncroTask() {
//			public void setTaskList(ArrayList<Task> asynlist) {
//			}
//			public void setPTask(Task task) {
//			}
//			public String getTaskName() {
//				return null;
//			}
//			public Object getResult() {
//				return true;
//			}
//			public void prepare() {
//				// do nothing
//			}
//			public void start() {
//				try {
//					DBManager.staticExecute("SHUTDOWN;", null);
//				} catch (Throwable ignore) {
//					ignore.printStacktrace();
//				}
//			}
//			public void work() {
//				// do nothing
//			}
//		});
//		manager.workWait();
//		for (Task task : manager.getTaskList()) {
//			DebugUtil.debug(">>task:" + task.getTaskName() + "," + task.getResult());
//		}
//		DebugUtil.debug(">asyncro.shutdownService.cost:" + (System.currentTimeMillis() - t) + "ms");
	}
	/**
	 * 
	 */
	public static void startService() {
//		long t = System.currentTimeMillis();
//		// 10000*10000*3=1~3s
//		// 30000*30000*3=4s~5s
//		TaskManager manager = new MultiTaskManager();
//		manager.setTimeOut(30000);// ms
//		manager.addTask(new AsyncroTask() {
//			public void setTaskList(ArrayList<Task> asynlist) {
//			}
//			public void setPTask(Task task) {
//			}
//			public String getTaskName() {
//				return null;
//			}
//			public Object getResult() {
//				return true;
//			}
//			public void prepare() {
//				// do nothing
//			}
//			public void start() {
//				try {
//					Config config = ConfigUtil.getConfig();
//					if (config.getJdbcURL() != null
//							&& "hsqldb".equalsIgnoreCase(config.getJdbcURL().getDbType()))
//						SQLDBHelper.startHsqldbService(config.getJdbcURL().getUrl());
//					else if (config.getDataSource() != null
//							&& "hsqldb".equalsIgnoreCase(config.getDataSource().getDbType()))
//						SQLDBHelper.startHsqldbService(config.getDataSource().getJndi());
//					else if (config.getJdbcURL() != null
//							&& "derby".equalsIgnoreCase(config.getJdbcURL().getDbType()))
//						SQLDBHelper.startDerbyService();
//					else if (config.getDataSource() != null
//							&& "derby".equalsIgnoreCase(config.getDataSource().getDbType()))
//						SQLDBHelper.startDerbyService();
//				} catch (Throwable ignore) {
//					ignore.printStacktrace();
//				}
//			}
//			public void work() {
//				// do nothing
//			}
//		});
//		manager.workWait();
//		for (Task task : manager.getTaskList()) {
//			DebugUtil.debug(">>task:" + task.getTaskName() + "," + task.getResult());
//		}
//		DebugUtil.debug(">asyncro.startService.cost:" + (System.currentTimeMillis() - t) + "ms");
	}
	/**
	 * @param url
	 * @throws Throwable
	 */
	public static void startDerbyService() throws Throwable {
		// DebugUtil.debug(">startDerbyService");
		// NetworkServerControl server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
		// server.start(null);
	}
//	/**
//	 * @param url
//	 * 
//	 */
//	public static void startHsqldbService(String url) {
//		String dbname = url.substring(url.lastIndexOf("/") + 1, url.length());
//		dbname = dbname.indexOf("?") > 0 ? dbname.substring(0, dbname.indexOf("?")) : dbname;
//		dbname = dbname.endsWith(";") ? dbname.substring(0, dbname.length() - 1) : dbname;
//		DebugUtil.debug(">startHsqldbService:" + dbname);
//		String[] args = new String[] { "-database.0", "haiyan", "-dbname.0", dbname };
//		// String[] args = new String[] { "-dbname.0", dbname };
//		// org.hsqldb.WebServer.main(args);
//		org.hsqldb.Server.main(args);
//	}
	/**
	 * @param table
	 * @param field
	 * @param pTable
	 * @return String
	 */
	public static String getCriticalFieldName(Table table, Field field, PrimaryTable pTable) {
		String result = "";
		if (field.isVisual()) {
			if (!StringUtil.isBlankOrNull(field.getSubQuerySQL()))
				result = "(" + field.getSubQuerySQL() + ")";
			else if (!StringUtil.isBlankOrNull(field.getMappingTable()))
				result = pTable.getFirstTableAlias() + "." + table.getId().getName();
			else if (!StringUtil.isBlankOrNull(field.getOne2oneTable()))
				result = pTable.getFirstTableAlias() + "." + table.getId().getName();
		} else {
			result = pTable.getFirstTableAlias() + "." + field.getName();
		}
		// DebugUtil.debug(">getCriticalFieldName.pTable.getFirstTableAlias()="
		// + pTable.getFirstTableAlias());
		return result;
	}
	/**
	 * @param table
	 * @param field
	 * @return String
	 */
	public static String getOrderByFieldName(Table table, AbstractField field) {
		PrimaryTable pTable = new PrimaryTable("", "");
		String result = "";
		if (field.isVisual() == true && field instanceof Field
			&& !StringUtil.isBlankOrNull(((Field)field).getSubQuerySQL()))
			result = "(" + ((Field)field).getSubQuerySQL() + ")";
		else // TableNode.BEGIN_INDEX
			result = pTable.getTableAliasByIndex(1) + "." + field.getName();
		return result;
	}
    /**
     * @param mainTable
     * @param field
     * @return
     * @throws Throwable
     */
    public final static String getIndexName(Table mainTable, Field field)
            throws Throwable {
        String[][] flds = getIndexFields(mainTable, field);
        if (flds.length > 0 && flds[0].length > 0)
            return flds[0][0];
        return null;
    }
    /**
     * SQL字段名，注释（包含关联表字段）选项
     * 
     * @param table
     * @param field
     * @return String[][] examples:[["t_1.NAME","主表名称"],["t_2.CODE","机构编码"]...]
     * @throws Throwable
     */
    public final static String[][] getIndexFields(Table table, Field field)
            throws Throwable {
        // AbstractOperationsRender render =
        // OperationsRenderCreator.createRender(context, table);
        Field[] fields = table.getField();// render.getVisibleField();
        // RenderUtil.getQueryVisibleField(table, null);
        HashMap<String, String> visFlds = new HashMap<String, String>();
        for (Field fld : fields) {
            visFlds.put(fld.getName(), fld.getName());
        }
        ArrayList<String[]> list = new ArrayList<String[]>();
        PrimaryTable pTable = new PrimaryTable(ConfigUtil.getRealTableName(table), ""); // realTable,selectColumnSQL
        TableDBTemplate temp = getTableDBTemp(visFlds, field);
        temp.deal(table, new Object[] { list, pTable });
        return list.toArray(new String[0][0]);
    }
	/**
	 * @return DBTableTemplate
	 */
	public static TableDBTemplate getTableDBTemp(final HashMap<String, String> visFld, final Field fld) {
		// StringBuffer cols = new StringBuffer();
		// PrimaryTable pTable = new
		// PrimaryTable(ConfigUtil.getRealTableName(table), "");
		// dealer.deal(table, new Object[] { cols, pTable });
		TableDBTemplate template = new TableDBTemplate() {
			@Override
			public void dealWithIdField(Table table, int index,
					Object[] globalVars) {
			}
			@SuppressWarnings("unchecked")
			@Override
			public int dealWithDisplayField(Table table, int tableIndex,
					int index, Field field, Object[] globalVars) {
				if (fld != null && fld != field)
					return index;
				if (visFld.get(field.getName()) == null)
					return index;
				@SuppressWarnings("rawtypes")
				ArrayList cols = (ArrayList) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				String[] disFieldNames = ConfigUtil.getDisplayRefFields(field);
				// Table refTable = ConfigUtil.getTable(field.getReferenceTable());
				for (int i = 0; i < disFieldNames.length; i++) {
					// DebugUtil.debug(">disFieldNames[" + i + "]="
					// + pTable.getTableAliasByIndex(pTable.getIndex() +
					// tableIndex) + "." + disFieldNames[i]);
					// Field displayField = ConfigUtil.getFieldByName(refTable, disFieldNames[i]);
					String value = pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex)
							+ "." + disFieldNames[i];
					cols.add(new String[] { value, field.getDescription() });
				}
				// DebugUtil.debug(">cols=" + cols.toString() );
				return index;
			}
			@Override
			public void dealWithReferenceTable(Table table, int index,
					Field field, Object[] globalVars) {
				if (fld != null && fld != field)
					return;
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				String refTableName = field.getReferenceTable();
				pTable.addJoinedTable(new LeftOuterJoinedTable(ConfigUtil
						.getRealTableName(refTableName), ConfigUtil
						.getReferenceFieldName(field), pTable.getTableAlias(),
						field.getName(), pTable, pTable.getIndex() + index));
			}
			@SuppressWarnings("unchecked")
			@Override
			public void dealWithAssociatedField(Table table, int tableIndex,
					int index, Field mainField, Field associatedField,
					Object[] globalVars) {
				if (fld != null && fld != mainField)
					return;
				if (visFld.get(mainField.getName()) == null)
					return;
				@SuppressWarnings("rawtypes")
				ArrayList cols = (ArrayList) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				// DebugUtil.debug(">associatedField:"
				// + pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex) + "."
				// + associatedField.getDisplay ReferenceField() + " f_" + index);
				if (associatedField.getDisplayReferenceField() == null)
					throw new Warning(null, 100108,
							"disreferencefield_is_null",
							new String[] { table.getName(), associatedField.getName() });
				String value = pTable.getTableAliasByIndex(pTable.getIndex()
						+ tableIndex)
						+ "."
						+ ConfigUtil.getDisplayRefFields(associatedField)[0];
				cols.add(new String[] { value, mainField.getDescription() });
			}
			@SuppressWarnings("unchecked")
			@Override
			public void dealWithGeneralField(Table table, int index,
					Field field, Object[] globalVars) {
				if (fld != null && fld != field)
					return;
				if (visFld.get(field.getName()) == null)
					return;
				if (field.getReferenceTable() != null)
					return;
				@SuppressWarnings("rawtypes")
				ArrayList cols = (ArrayList) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				String value = pTable.getFirstTableAlias() + "." + field.getName();
				cols.add(new String[] { value, field.getDescription() });
			}
			@Override
			public void dealWithComputeField(Table table, int index,
					Field field, Object[] globalVars) {
				if (fld != null && fld != field)
					return;
				// if (field.isVisual())
				// return;
				// ArrayList cols = (ArrayList) globalVars[0];
				// PrimaryTable pTable = (PrimaryTable) globalVars[1];
				// String value = pTable.getFirstTableAlias() + "." +
				// field.getName();
				// cols.add(new String[] { value, field.getDescription() });
			}
			@Override
			public void dealWithLazyLoadField(Table table, Field field,
					Object[] globalVars) throws Throwable {
				if (fld != null && fld != field)
					return;
			}
			@Override
			public void dealWithMappingField(Table table, Field field,
					Object[] globalVars) throws Throwable {
				if (fld != null && fld != field)
					return;
			}
		};
		return template;
	}

}
 
