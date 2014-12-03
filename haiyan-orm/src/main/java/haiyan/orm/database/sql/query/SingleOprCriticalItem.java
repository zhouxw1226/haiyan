package haiyan.orm.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public abstract class SingleOprCriticalItem extends CriticalItem {

	/**
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public SingleOprCriticalItem(String fieldName, Object value, Class<?> type) {
		this.fieldName = fieldName;
		this.value = value;
		this.type = type;
	}

	/**
	 * 
	 */
	protected Object value;

	/**
	 * @return String
	 */
	protected abstract String getOperator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#getPreparedStr()
	 */
	public String getPreparedStr() {
		if (this.isValueValid(this.value)) {
			return this.fieldName + " " + getOperator() + " ? ";
		} else {
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.Item#isIgnored()
	 */
	public boolean isIgnored() {
		return !this.isValueValid(this.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#setValue(java.sql.PreparedStatement,
	 *      int)
	 */
	public int setValue(PreparedStatement ps, int number) throws SQLException {
		if (this.isValueValid(this.value)) {
			setDBValue(ps, number, this.value, this.type);
			number++;
		}
		return number;
	}

	/**
	 * @param fieldName
	 * @return String
	 */
	static String sqlLower(String fieldName) {
		return "lower(" + fieldName + ")";
	}

}