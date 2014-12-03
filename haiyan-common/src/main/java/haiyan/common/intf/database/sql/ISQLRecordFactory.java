package haiyan.common.intf.database.sql;

import java.sql.ResultSet;

/**
 * <p>
 * Copyright: Copyright (c) 2002-2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * 对象-关系元数据映射工厂
 * 
 * @author zhouxw
 * @version 1.0
 */
public interface ISQLRecordFactory extends java.io.Serializable {

	/**
	 * @param rs
	 * @return Object
	 * @throws Throwable
	 */
	public Object getRecord(ResultSet rs) throws Throwable;

	public String getTableName();

	public String getIDName();

	// public void addCacheID(String ID) throws Throwable;
}