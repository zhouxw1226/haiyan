package haiyan.orm.database.sql.query;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.database.IDBFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author zhouxw
 */
public class Query {

	// private boolean sqlMainHasWhereClause;

	private String sqlBody;

	private String sqlFilter;

	private ArrayList<CriticalItem> criticalItems = new ArrayList<CriticalItem>();

	private ArrayList<OrderByItem> orderByItems = new ArrayList<OrderByItem>();

	private ArrayList<QueryListener> listeners = new ArrayList<QueryListener>();
	
	private ArrayList<IDBFilter> filters = new ArrayList<IDBFilter>();

	/**
	 * @param sqlMain
	 * @param sqlFilter
	 */
	public Query(String sqlMain, String sqlFilter) {
		this.sqlBody = sqlMain;
		this.sqlFilter = sqlFilter;
	}
	/**
	 * @param sqlMain
	 * @param sqlFilter
	 * @param orderByItems
	 */
	public Query(String sqlMain, String sqlFilter, ArrayList<OrderByItem> orderByItems) {
		this.sqlBody = sqlMain;
		this.sqlFilter = sqlFilter;
		if (orderByItems != null) {
			this.orderByItems=orderByItems;
		}
	}
	/**
	 * @param sqlMain
	 * @param sqlFilter
	 * @param criticalItems
	 * @param orderByItems
	 */
	public Query(String sqlMain, String sqlFilter, ArrayList<CriticalItem> criticalItems, ArrayList<OrderByItem> orderByItems) {
		this.sqlBody = sqlMain;
		this.sqlFilter = sqlFilter;
		if (criticalItems != null) {
			this.criticalItems=criticalItems;
		}
		if (orderByItems != null) {
			this.orderByItems=orderByItems;
		}
	}
//	/**
//	 * @param sqlMain
//	 *            sql test1.a,test2.b from test1,test2 where test1.ID=test2.xxID
//	 * @param sqlFilter
//	 * @param criticalItems
//	 * @param orderByitems
//	 */
//	public Query(String sqlMain, String sqlFilter, CriticalItem[] criticalItems, OrderByItem[] orderByItems) {
//		this.sqlBody = sqlMain;
//		this.sqlFilter = sqlFilter;
//		if (criticalItems != null) {
//			for (int i = 0; i < criticalItems.length; i++) {
//				this.criticalItems.add(criticalItems[i]);
//			}
//		}
//		if (orderByItems != null) {
//			for (int i = 0; i < orderByItems.length; i++) {
//				this.orderByItems.add(orderByItems[i]);
//			}
//		}
//	}
	/**
	 * @param f
	 */
	public void addFilter(IDBFilter f) {
		this.filters.add(f);
	}
	/**
	 * @param items
	 * @return ArrayList
	 */
	private ArrayList<Item> getValidItems(ArrayList<?> items) {
		ArrayList<Item> validItems = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++) {
			Item item = (Item) items.get(i);
			if (item.isIgnored() == false)
				validItems.add(item);
		}
		return validItems;
	}
	/**
	 * 
	 * @param ps
	 */
	private void setValue(PreparedStatement ps) throws SQLException {
		ArrayList<Item> validItems = getValidItems(this.criticalItems);
		int number = 1; // PreparedStatement
		for (int i = 0; i < validItems.size(); i++) {
			CriticalItem item = (CriticalItem) validItems.get(i);
			number = item.setValue(ps, number);
		}
		for (IDBFilter filter:this.filters) {
			if (filter==null||filter.getParas()==null)
				continue;
			for (Object o:filter.getParas()) {
				DebugUtil.debug(">setFilterValue,"+number+":"+o);
				CriticalItem.setDBValue(ps, number, o, o==null?null:o.getClass());
				number++;
			}
		}
		// SQL中?域的游标 (wrapSQL数据库方言用)
		for (int i = 0; i < listeners.size(); i++) {
			((QueryListener) listeners.get(i)).setSelectPS(ps, number);
		}
	}
	/**
	 * @param sql
	 * @return String
	 */
	private String resetSql(String sql) {
		// TODO 重置SQL
		if (sql.indexOf("count(*)") != -1) {
			// String app = "";
			// int s = -1;
			// s = sql.lastIndexOf("LIMIT ?,?");
			// if (s >= 0)
			// app = sql.substring(s);
			// s = sql.lastIndexOf("order by ");
			// if (s >= 0)
			// sql = sql.substring(0, s);
			// sql += app;
			// //////////////////////////////
			// int e = s;
			// int i = s + 9;
			// while (i < sql.length() && !" ".equals(sql.substring(i, i++))) {
			// e += 1;
			// }
			// if ((e + 5) < sql.length()
			// && " desc".equals(sql.substring(e, e + 5)))
			// e += 5;
			// if ((e + 4) < sql.length()
			// && " asc".equals(sql.substring(e, e + 4)))
			// e += 4;
			// // int e = s;
			// sql = sql.substring(0, s - 1) + sql.substring(e);
		}
		return sql;
	}
	/**
	 * @param con
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement buildWithScrollCursor(Connection con)
			throws SQLException {
		return build(con, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	/**
	 * @param con
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement build(Connection con, int resultSetType, int resultSetConcurrency) throws SQLException {
		String sql = getSQL();
		// DebugUtil.asert(sql.indexOf('?') < 0);
		sql = resetSql(sql);
		// DebugUtil.asert(sql.indexOf('?') < 0);
		DebugUtil.debug(">Query sql:" + sql);
		PreparedStatement ps = con.prepareStatement(sql, resultSetType, resultSetConcurrency);
		setValue(ps);
		return ps;
	}
	/**
	 * for count(*)
	 * 
	 * @param con
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement build(Connection con) throws SQLException {
		String sql = getSQL();
		// DebugUtil.asert(sql.indexOf('?') < 0);
		sql = resetSql(sql);
		// DebugUtil.asert(sql.indexOf('?') < 0);
		DebugUtil.debug(">Query sql:" + sql);
		PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		setValue(ps);
		return ps;
	}
	/**
	 * @param ql
	 */
	public void addListener(QueryListener ql) {
		listeners.add(ql);
	}
	/**
	 * 
	 */
	public void clearListener() {
		listeners.clear();
	}
	/**
	 * @return String
	 */
	public String getSQL() {
		// item sql
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlCritical = new StringBuffer();
		StringBuffer sqlOrderBy = new StringBuffer();
		// validCriticalItems validOrderByItems
		ArrayList<Item> validCriticalItems = getValidItems(this.criticalItems);
		ArrayList<Item> validOrderByItems = getValidItems(this.orderByItems);
		for (int i = 0; i < validCriticalItems.size(); i++) {
			CriticalItem item = (CriticalItem) validCriticalItems.get(i);
			// DebugUtil.debug(item.fieldName + " " + item.getPreparedStr());
			if (i != 0)
				if (StringUtil.isBlankOrNull(item.getRelationOpr()))
					sqlCritical.append(" and ");
				else
					sqlCritical.append(" " + item.getRelationOpr() + " ");
			sqlCritical.append("(");
			sqlCritical.append(item.getPreparedStr());
			sqlCritical.append(")");
		}
		// validOrderByItems
		if (validOrderByItems.size() != 0)
			if (sqlCritical.toString().toLowerCase().indexOf("order by") == -1 && sqlFilter.toLowerCase().indexOf("order by") == -1)
				sqlOrderBy.append(" order by ");
			else
				sqlOrderBy.append(", ");
		for (int i = 0; i < validOrderByItems.size(); i++) {
			OrderByItem item = (OrderByItem) validOrderByItems.get(i);
			if (i != 0)
				sqlOrderBy.append(", ");
			sqlOrderBy.append(item.getSqlStr());
		}
		// sqlBody
		sql.append(this.sqlBody);
		sql.append(" where 1=1 ");
		// sqlCritical
		if (!StringUtil.isBlankOrNull(sqlCritical.toString()))
			sql.append(" and (" + sqlCritical + ") ");
		// extFilter
		if (!StringUtil.isBlankOrNull(this.sqlFilter))
			sql.append(this.sqlFilter); // 可能配有order by所以放最后
		// sqlOrderBy
		sql.append(sqlOrderBy);
		// wrapSQL(数据库方言)
		for (int i = 0; i < listeners.size(); i++) {
			sql = ((QueryListener) listeners.get(i)).wrapSQL(sql);
		}
		return sql.toString();
	}
	/**
	 * sqlBody
	 * 
	 * @return String
	 */
	public String getSqlBody() {
		return this.sqlBody;
	}
	/**
	 * @param sqlBody
	 *            The sqlBody to set
	 */
	public void setSqlBody(String sqlBody) {
		this.sqlBody = sqlBody;
	}
}