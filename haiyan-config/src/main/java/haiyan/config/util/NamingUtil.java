/*
 * Created on 2004-10-9
 */
package haiyan.config.util;

import haiyan.config.castorgen.AbstractField;


/**
 * 平台特殊命名法
 * 
 * @author zhouxw
 */
public class NamingUtil {

	/**
	 * 
	 */
	public final static int REGION_START = 1;

	/**
	 * 
	 */
	public final static int REGION_END = 2;

	/**
	 * 
	 */
	public final static String DELIMITER = "__";

	/**
	 * AutoNaming太长了直接去掉
	 */
	public final static String _PREFIX = "";

	/**
	 * 
	 */
	public final static String CONSTANT_POSTFIX = "ConatantName";

	/**
	 * 
	 */
	public final static String ORDER_BY_FIELD_NAME = _PREFIX + DELIMITER
			+ "OrderByField" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String ORDER_BY_METHOD_NAME = _PREFIX + DELIMITER
			+ "OrderByMethod" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String QUICKCONDITION_FIELD_NAME = _PREFIX + DELIMITER
			+ "QuickConditionByField" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String QUICKCONDITION_METHOD_NAME = _PREFIX + DELIMITER
			+ "QuickConditionByMethod" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String QUICKCONDITION_VALUE_NAME = _PREFIX + DELIMITER
			+ "QuickConditionByValue" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * @param fld
	 * @return String
	 */
	public static String getBlobFieldAlias(AbstractField fld) {
		String result = _PREFIX + DELIMITER + fld.getName();
		return result;
	}
	
	/**
	 * @param fld
	 * @return String
	 */
	public static String getDisplayFieldAlias(AbstractField fld) {
		String[] refFlds = ConfigUtil.getDisplayRefFields(fld);
		if (refFlds.length == 0)
			return null;
		String result = _PREFIX + DELIMITER + fld.getName() + DELIMITER + refFlds[0];
		return result;
	}
	/**
	 * @param fkFieldName
	 * @param fkDisplayName
	 * @return String
	 */
	public static String getDisplayFieldAlias(String fkFieldName, String fkDisplayName) {
		String result = _PREFIX + DELIMITER + fkFieldName + DELIMITER + fkDisplayName;
		return result;
	}

	/**
	 * @param fieldName
	 * @return String
	 */
	public static String getFileBoxName(String fieldName) {
		final String name = "fakeTableForFile";
		return getDisplayFieldAlias(fieldName, name);
	}

	/**
	 * @param tableName
	 * @return String
	 */
	public static String getValidationFunctionName(String tableName) {
		return "validate" + tableName.substring(0, 1).toUpperCase() + tableName.substring(1, tableName.length());
	}

	/**
	 * @param fieldName
	 * @param regionType
	 * @return String
	 */
	public static String getRegionFieldName(String fieldName, int regionType) {
		String result = _PREFIX + DELIMITER; // __CDATE__start __CDATE__end
		switch (regionType) {
		case REGION_START:
			result += fieldName + DELIMITER + "start";
			break;
		case REGION_END:
			result += fieldName + DELIMITER + "end";
			break;
		default:
			break;
		}
		return result;
	}


}