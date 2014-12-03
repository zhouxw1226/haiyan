package haiyan.orm.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public class NotLikeCriticalItem extends SingleOprCriticalItem {

	/**
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public NotLikeCriticalItem(String fieldName, Object value, Class<?> type) {
		super(sqlLower(fieldName), value, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.SingleOprCriticalItem#getOperator()
	 */
	protected String getOperator() {
		return "not like";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#setValue(java.sql.PreparedStatement, int)
	 */
	public int setValue(PreparedStatement ps, int number) throws SQLException {
		if (this.isValueValid(this.value)) {
			if (this.type.equals(String.class)) {
				String v = this.value.toString();
				if (v.indexOf("%") >= 0)
					setDBValue(ps, number, v.toLowerCase(), this.type);
				else
					setDBValue(ps, number, ("%" + v + "%").toLowerCase(), this.type);
			} else {
				setDBValue(ps, number, this.value, this.type);
			}
			number++;
		}
		return number;
	}

}