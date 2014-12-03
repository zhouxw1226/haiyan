package haiyan.orm.database.sql.page;

import haiyan.common.intf.database.sql.ISQLRecordFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public class OraclePageFactory extends SQLWrapPageFactory {

	private static final long serialVersionUID = 1L;
	/**
	 * @param countPS
	 * @param selectPS
	 * @param factory
	 */
	public OraclePageFactory(PreparedStatement countPS,
			PreparedStatement selectPS, ISQLRecordFactory factory) {
		super(countPS, selectPS, factory);
	}
	/**
	 * @param countSQL䡣
	 * @param selectSQL䡣
	 * @param con
	 * @param factory
	 * @throws Throwable
	 */
	public OraclePageFactory(String countSQL, String selectSQL, Connection con,
			ISQLRecordFactory factory) throws Throwable {
		super(countSQL, selectSQL, con, factory);
	}
	@Override
	public String wrapSQL(String sql) {
		StringBuffer pagingSelect = new StringBuffer(100);
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
		return pagingSelect.toString();
	}
	@Override
	public void setSelectPS(PreparedStatement selectPS, int index,
			int currPageNO, int maxPageRecordCount) throws SQLException {
		int startRowNum = (currPageNO - 1) * maxPageRecordCount;
		selectPS.setLong(index, startRowNum + maxPageRecordCount);
		selectPS.setLong(index + 1, startRowNum);
		//DebugUtil.debug(">setSelectPS:" + (startRowNum + maxPageRecordCount) + "," + startRowNum);
	}
	@Override
	public void setSelectPSByLimit(PreparedStatement selectPS, int index,
			int startRowNum, int count) throws SQLException {
		selectPS.setLong(index, startRowNum + count);
		selectPS.setLong(index + 1, startRowNum);
	}

}