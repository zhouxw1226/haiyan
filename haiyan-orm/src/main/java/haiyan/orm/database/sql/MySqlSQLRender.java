/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import haiyan.common.DebugUtil;
import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.config.castorgen.Table;
import haiyan.orm.database.sql.page.MySqlPageFactory;
import haiyan.orm.database.sql.page.SQLDBPageFactory;
import haiyan.orm.database.sql.page.SQLWrapPageFactory;
import haiyan.orm.database.sql.query.PrimaryTable;
import haiyan.orm.database.sql.query.Query;
import haiyan.orm.database.sql.query.QueryListener;
import haiyan.orm.intf.session.ITableDBContext;

/**
 * @author zhouxw
 */
class MySqlSQLRender extends SQLRender {

	@Override
	public PreparedStatement getSelectPreparedStatement(ITableDBContext context, Table table, String id) throws Throwable {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// main
		mainSQL = pTable.getSQL();
		mainSQL += " where " + pTableAlias + "." + table.getId().getName() + "=? limit 1 ";
		DebugUtil.info(">selectByPK(Table=" + table.getName() + ",ID=" + id + "):" + mainSQL);
		// deal
		PreparedStatement ps = null;
		ps = getConnection(context).prepareStatement(mainSQL);
		//ps.setInt(1,id);
		//ps.setObject(1, id);
		ps.setString(1, id);
		return ps;
	}
	@Override
	protected Query dealWithSelectQueryByLimit(Query selectQuery, final long startNum, final int count) {
		// oracle OraclePage listener
		QueryListener ql = new QueryListener() {
			@Override
			public StringBuffer wrapSQL(StringBuffer sql) {
				StringBuffer b = new StringBuffer();
				b.append(((SQLWrapPageFactory) getPageFactory(null, null, null)).wrapSQL(sql.toString()));
				return b;
			}
			@Override
			public void setSelectPS(PreparedStatement selectPS, int lastIndex)
					throws SQLException {
				((SQLWrapPageFactory) getPageFactory(null, null, null)).setSelectPSByLimit(selectPS, lastIndex, startNum, count);
			}
		};
		selectQuery.addListener(ql);
		return selectQuery;
	}
	@Override
	protected Query dealWithSelectQuery(Query selectQuery, final int currPageNO, final int maxPageRecordCount) {
		// oracle OraclePage listener
		QueryListener ql = new QueryListener() {
			@Override
			public StringBuffer wrapSQL(StringBuffer sql) {
				StringBuffer b = new StringBuffer();
				b.append(((SQLWrapPageFactory) getPageFactory(null, null, null)).wrapSQL(sql.toString()));
				return b;
			}
			@Override
			public void setSelectPS(PreparedStatement selectPS, int lastIndex)
					throws SQLException {
				((SQLWrapPageFactory) getPageFactory(null, null, null)).setSelectPS(selectPS, lastIndex, currPageNO, maxPageRecordCount);
			}
		};
		selectQuery.addListener(ql);
		return selectQuery;
	}
	@Override
	protected SQLDBPageFactory getPageFactory(PreparedStatement countPS,
			PreparedStatement selectPS, ISQLRecordFactory factory) {
		return new MySqlPageFactory(countPS, selectPS, factory);
	}
}