package haiyan.orm.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public class InCriticalItem extends CriticalItem {

	/**
	 * @param fieldName
	 * @param values
	 * @param types
	 */
	public InCriticalItem(String fieldName, Object[] values, Class<?> type) {
		this.fieldName = fieldName;
		this.values = values;
		this.type = type;
	}

	//
	private Object[] values;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		int count = 0;
		//
		String result = this.fieldName + " in (";
		for (int i = 0; i < values.length; i++) {
			if (isValueValid(this.values[i])) {
				if (count != 0)
					result += ",";
				result += "'" + this.values[i] + "'";
				count++;
			}
		}
		result += ")";
		//
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#getPreparedStr()
	 */
	public String getPreparedStr() {
		int count = 0;
		//
		String result = this.fieldName + " in (";
		for (int i = 0; i < values.length; i++) {
			if (isValueValid(this.values[i])) {
				if (count != 0)
					result += ",";
				result += "?";
				count++;
			}
		}
		result += ")";
		//
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.Item#isIgnored()
	 */
	public boolean isIgnored() {
		return values.length != 0 ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#setValue(java.sql.PreparedStatement,
	 *      int)
	 */
	public int setValue(PreparedStatement ps, int number) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			if (isValueValid(this.values[i])) {
				setDBValue(ps, number, this.values[i], this.type);
				number++;
			}
		}
		return number;
	}

}