package haiyan.orm.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public class BetweenCriticalItem extends CriticalItem {

	private Object lowerValue;
	private Object upperValue;
	/**
	 * @param fieldName
	 * @param lowerValue
	 * @param upperValue
	 * @param type
	 */
	public BetweenCriticalItem(String fieldName, Object lowerValue,
			Object upperValue, Class<?> type) {
		this.fieldName = fieldName;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
		this.type = type;
	}
	@Override
	public String getPreparedStr() {
		String result = "";
		if (isValueValid(this.lowerValue) && isValueValid(this.upperValue)) {
			result = this.fieldName + " between ? and ? ";
		} else {
			if (isValueValid(this.lowerValue)) {
				result = this.fieldName + " >= ? ";
			}
			if (isValueValid(this.upperValue)) {
				result = this.fieldName + " <= ? ";
			}
		}
		return result;
	}
	@Override
	public boolean isIgnored() {
		return (isValueValid(this.lowerValue) || isValueValid(this.upperValue)) ? false : true;
	}
	@Override
	public int setValue(PreparedStatement ps, int number) throws SQLException {
		if (isValueValid(this.lowerValue)) {
			setDBValue(ps, number, this.lowerValue, this.type);
			number++;
		}
		if (isValueValid(this.upperValue)) {
			setDBValue(ps, number, this.upperValue, this.type);
			number++;
		}
		return number;
	}

}