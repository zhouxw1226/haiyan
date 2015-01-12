package haiyan.orm.database.sql.query;

import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.orm.database.sql.SQLDBTypeConvert;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author zhouxw
 */
public abstract class CriticalItem extends Item {

	//
	CriticalItem() {
	}

	/**
	 * 
	 */
	protected String fieldName;

	/**
	 * 
	 */
	protected Class<?> type;

	/**
	 * 
	 */
	String relationOpr = "and";

	/**
	 * @param relationOpr
	 */
	public void setRelationOpr(String relationOpr) {
		this.relationOpr = relationOpr;
	}

	/**
	 * @return String
	 */
	String getRelationOpr() {
		return relationOpr;
	}

	/**
	 * @return String
	 */
	abstract String getPreparedStr();

	/**
	 * @param ps
	 * @param number
	 * @return int
	 */
	abstract int setValue(PreparedStatement ps, int number) throws SQLException;

	/**
	 * @param ps
	 * @param number
	 * @param value
	 * @param type
	 * @throws SQLException
	 */
	public static void setDBValue(PreparedStatement ps, int number, Object value, Class<?> type) throws SQLException {
		if (type == null)
			throw new RuntimeException("unknown data type: Null");
		if (type.equals(Object[].class)) {
			ps.setObject(number, (Object[]) value);
		} else if (type.equals(String.class)) {
			ps.setString(number, (String) value);
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			ps.setInt(number, ((Integer) value).intValue());
		} else if (type.equals(long.class) || type.equals(Long.class)) {
			ps.setLong(number, ((Long) value).longValue());
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			ps.setDouble(number, ((Double) value).doubleValue());
		} else if (type.equals(BigDecimal.class)) {
			ps.setBigDecimal(number, (BigDecimal) value);
		} else if (type.equals(Timestamp.class)) {
			ps.setTimestamp(number, (Timestamp) value);
		} else if (type.equals(java.sql.Date.class)) {
			ps.setDate(number, (java.sql.Date) value);
		}  else if (type.equals(Blob.class)) {
			throw new RuntimeException("unknown data type:" + type.getName());
		} else {
			throw new RuntimeException("unknown data type:" + type.getName());
		}
	}

	/**
	 * @param form
	 * @param key
	 * @param table
	 * @param field
	 * @return Object
	 * @throws Throwable
	 */
	public static Object getItemValue(IDBRecord form, String key, Table table, AbstractField field) throws Throwable {
		// TODO 此方法的api参数需要再议
		Object result = null;
		Object strValue = form.get(key);
		AbstractCommonFieldJavaTypeType type = field.getJavaType();
		if (type==AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
			result = SQLDBTypeConvert.toBigDecimal(strValue, field);
		} else if (type==AbstractCommonFieldJavaTypeType.STRING) {
			result = strValue;
		} else if (type==AbstractCommonFieldJavaTypeType.DATE) {
			result = SQLDBTypeConvert.toTimestamp(strValue, field);
		} else if (type==AbstractCommonFieldJavaTypeType.BLOB) {
			result = strValue;
		} else if (type==AbstractCommonFieldJavaTypeType.DBCLOB) {
			result = strValue;
		} else if (type==AbstractCommonFieldJavaTypeType.INTEGER) {
			result = VarUtil.toInt(strValue);
		} 
//		else if (type==AbstractCommonFieldJavaTypeType.DBBLOB) { // result = strValue;
//			throw new Warning("unkown data type:" + type);
//		} 
		else {
			throw new Warning("unkown data type:" + type);
		}
		return result;
	}

}