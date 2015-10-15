package haiyan.orm.database.sql.query;

/**
 * @author zhouxw
 */
abstract class Item {
	/**
	 * @return boolean
	 */
	abstract boolean isIgnored();
	/**
	 * @param value
	 * @return boolean
	 */
	protected boolean isValueValid(Object value) {
		if (value == null
				|| (value instanceof String && ((String) value).length() == 0))
			return false;
		else
			return true;
	}
}