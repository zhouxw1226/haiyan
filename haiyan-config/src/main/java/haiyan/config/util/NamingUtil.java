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
	public final static String _DELIMITER = "__";
	/**
	 * AutoNaming太长了直接去掉
	 */
	public final static String _PREFIX = "__";
	/**
	 * 
	 */
	public final static String _START = "start";
	/**
	 * 
	 */
	public final static String _END = "end";
	/**
	 * 
	 */
	public final static String CONSTANT_POSTFIX = "Conatant";
	/**
	 * 
	 */
	public final static String ORDER_BY_FIELD_NAME = _PREFIX  + "OrderByField" + _DELIMITER + CONSTANT_POSTFIX;
	/**
	 * 
	 */
	public final static String ORDER_BY_METHOD_NAME = _PREFIX  + "OrderByMethod" + _DELIMITER + CONSTANT_POSTFIX;
	/**
	 * 
	 */
	public final static String QUICKCONDITION_FIELD_NAME = _PREFIX  + "QuickConditionByField" + _DELIMITER + CONSTANT_POSTFIX;
	/**
	 * 
	 */
	public final static String QUICKCONDITION_METHOD_NAME = _PREFIX  + "QuickConditionByMethod" + _DELIMITER + CONSTANT_POSTFIX;
	/**
	 * 
	 */
	public final static String QUICKCONDITION_VALUE_NAME = _PREFIX  + "QuickConditionByValue" + _DELIMITER + CONSTANT_POSTFIX;
	/**
	 * @param fld
	 * @return String
	 */
	public static String getBlobFieldAlias(AbstractField fld) {
		String result = _PREFIX  + fld.getName();
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
		String result = _PREFIX  + fld.getName() + _DELIMITER + refFlds[0];
		return result;
	}
	/**
	 * @param fkFieldName
	 * @param fkDisplayName
	 * @return String
	 */
	public static String getDisplayFieldAlias(String fkFieldName, String fkDisplayName) {
		String result = _PREFIX  + fkFieldName + _DELIMITER + fkDisplayName;
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
	 * @param key
	 * @return
	 */
	public static String getRealFieldName(String key) {
		if (key.startsWith(_PREFIX)) {
			if (key.endsWith(_START)) {
				return key.substring(_PREFIX.length(), key.length()-_START.length()-_DELIMITER.length());
			} else if (key.endsWith(_END)) {
				return key.substring(_PREFIX.length(), key.length()-_END.length()-_DELIMITER.length());
			} else {
				return key.substring(_PREFIX.length(), key.length()-key.lastIndexOf(_DELIMITER));
			}
		}
		return key;
	}
	/**
	 * @param fieldName
	 * @param regionType
	 * @return String
	 */
	public static String getRegionFieldName(String fieldName, int regionType) {
		String result = _PREFIX; // __CDATE__start ~ __CDATE__end
		switch (regionType) {
		case REGION_START:
			result += fieldName + _DELIMITER + _START;
			break;
		case REGION_END:
			result += fieldName + _DELIMITER + _END;
			break;
		default:
			break;
		}
		return result;
	}
}