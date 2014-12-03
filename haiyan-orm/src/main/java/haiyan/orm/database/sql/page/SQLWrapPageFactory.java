/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql.page;

import haiyan.common.CloseUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.orm.database.DBPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author zhouxw
 */
public abstract class SQLWrapPageFactory extends SQLDBPageFactory {

	private static final long serialVersionUID = 1L;
	/**
	 * @param countPS
	 * @param selectPS
	 * @param factory
	 */
	public SQLWrapPageFactory(PreparedStatement countPS,
			PreparedStatement selectPS, ISQLRecordFactory factory) {
		this.countPS = countPS;
		this.selectPS = selectPS;
		this.factory = factory;
		// super(countPS, selectPS, factory);
	}
	/**
	 * @param countSQL
	 * @param selectSQL
	 * @param con
	 * @param factory
	 * @throws Throwable
	 */
	public SQLWrapPageFactory(String countSQL, String selectSQL,
			Connection con, ISQLRecordFactory factory) throws Throwable {
		this.countPS = con.prepareStatement(countSQL);
		this.selectPS = con.prepareStatement(selectSQL,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		this.factory = factory;
		// super(countSQL, selectSQL, con, factory);
	}
	/**
	 * for setSelectPS
	 * 
	 * @param sql
	 * @return String
	 */
	public abstract String wrapSQL(String sql);
	/**
	 * for wrapSQL
	 * 
	 * @param selectPS
	 * @param index
	 * @param currPageNO
	 * @param maxPageRecordCount
	 * @throws SQLException
	 */
	public abstract void setSelectPS(PreparedStatement selectPS, int index,
			int currPageNO, int maxPageRecordCount) throws SQLException;
	/**
	 * @param selectPS
	 * @param lastIndex
	 * @param startRowNum
	 * @param count
	 * @throws SQLException
	 */
	public abstract void setSelectPSByLimit(PreparedStatement selectPS, int lastIndex,
			int startRowNum, int count) throws SQLException;
	// DebugUtil.debug("startnum:"+startRowNum+"
	// endnum:"+startRowNum+maxPageRecordCount);
	@Override
	public void dealPage(int maxPageRecordCount, int currPageNO,
			IDBRecordCallBack callback) throws Throwable {
		ResultSet selectRS = null;
		try {
			selectRS = selectPS.executeQuery();
			selectRS.beforeFirst();
			// while (selectRS.next()) {
			for (int i = 0; i < maxPageRecordCount; i++) {
				if (selectRS.next()) {
					IDBRecord form = (IDBRecord) factory.getRecord(selectRS);
					callback.call(form);
				} else {
					break;
				}
			}
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(selectRS);
			CloseUtil.close(selectPS);
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public DBPage getPage(int maxPageRecordCount, int currPageNO)
			throws Throwable {
		int recordCount = 0;
		ResultSet countRS = null;
		try {
			countRS = countPS.executeQuery();
			if (countRS != null && countRS.next()) {
				recordCount = countRS.getInt(1);
			}
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(countRS);
			CloseUtil.close(countPS);
		}
		ArrayList<IDBRecord> pageData = createList(maxPageRecordCount, currPageNO);
		ResultSet selectRS = null;
		try { // NOTICE 和GeneralDBFactory不同，没有游标(WrapSQL处理了)，不可继承
			selectRS = selectPS.executeQuery();
			selectRS.beforeFirst();
			for (int i = 0; i < maxPageRecordCount; i++) {
				if (selectRS.next()) {
					// 如果cache里有 就不要花时间build BO 了
					IDBRecord form = (IDBRecord) factory.getRecord(selectRS);
					if (form != null)
						pageData.add(form); // 可能再次更新cache但必须否则像findByPK就不会更新了
					else
						recordCount--;
				} else {
					if (i == 0) {
						currPageNO = 0;
						recordCount = 0;
						maxPageRecordCount = 0;
					}
					break;
				}
			}
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(selectRS);
			CloseUtil.close(selectPS);
		}
		DBPage page = new DBPage();
		page.setRecords(pageData);
		page.setCurrPageNO(currPageNO);
		page.setTotalRecordCount(recordCount);
		page.setMaxPageRecordCount(maxPageRecordCount);
		return page;
	}

}