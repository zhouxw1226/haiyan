/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.orm.database.sql.page.MySqlPageFactory;
import haiyan.orm.database.sql.page.SQLDBPageFactory;
import haiyan.orm.database.sql.page.SQLWrapPageFactory;
import haiyan.orm.database.sql.query.Query;
import haiyan.orm.database.sql.query.QueryListener;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
class MySqlSQLRender extends SQLRender {

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