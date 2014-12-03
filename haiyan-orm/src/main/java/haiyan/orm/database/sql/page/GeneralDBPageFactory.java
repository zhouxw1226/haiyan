/*
 * Created on 2004-10-13
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
import java.util.ArrayList;

/**
 * @author zhouxw
 */
public class GeneralDBPageFactory extends SQLDBPageFactory {

	private static final long serialVersionUID = 1L;
	/**
	 * @param countPS
	 * @param selectPS
	 * @param factory
	 */
	public GeneralDBPageFactory(PreparedStatement selectPS, ISQLRecordFactory factory) {
		this.selectPS = selectPS;
		this.factory = factory;
	}
	/**
	 * @param countPS
	 * @param selectPS
	 * @param factory
	 */
	public GeneralDBPageFactory(PreparedStatement countPS,
			PreparedStatement selectPS, ISQLRecordFactory factory) {
		this.countPS = countPS;
		this.selectPS = selectPS;
		this.factory = factory;
	}
	/**
	 * @param countSQL
	 * @param selectSQL
	 * @param con
	 * @param factory
	 * @throws Throwable
	 */
	public GeneralDBPageFactory(String countSQL, String selectSQL,
			Connection conn, ISQLRecordFactory factory) throws Throwable {
		this.countPS = conn.prepareStatement(countSQL);
		this.selectPS = conn.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		this.factory = factory;
	}
	@Override
	public void dealPage(int maxPageRecordCount, int currPageNO,
			IDBRecordCallBack callback) throws Throwable {
		ResultSet selectRS = null;
		try {
			selectRS = selectPS.executeQuery();
			if (currPageNO > 1)
				selectRS.absolute(((currPageNO - 1) * maxPageRecordCount));
			for (int i = 0; i < maxPageRecordCount; i++) {
				if (selectRS.next()) {
					IDBRecord form = (IDBRecord) factory.getRecord(selectRS);
					callback.call(form);
				} else {
					break;
				}
			}
		}
		finally {
			CloseUtil.close(selectRS);
			CloseUtil.close(selectPS);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public DBPage getPage(int maxPageRecordCount, int currPageNO)
			throws Throwable {
		int recordCount = 0;
		ResultSet countRS = null;
		try {
			if (countPS!=null) {
				countRS = countPS.executeQuery();
				if (countRS != null && countRS.next()) {
					recordCount = countRS.getInt(1);
				}
			}
		}
		finally {
			CloseUtil.close(countRS);
			CloseUtil.close(countPS);
		}
		ArrayList<IDBRecord> pageData = createList(maxPageRecordCount, currPageNO);
		ResultSet selectRS = null;
		try {
			selectRS = selectPS.executeQuery();
			if (currPageNO > 1) // 分页游标处理
				selectRS.absolute(((currPageNO - 1) * maxPageRecordCount));
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