package haiyan.orm.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public class IsNotNullCriticalItem extends SingleOprCriticalItem {

	/**
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public IsNotNullCriticalItem(String fieldName, Object value, Class<?> type) {
		super(fieldName, value, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.Item#isIgnored()
	 */
	public boolean isIgnored() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.SingleOprCriticalItem#getOperator()
	 */
	protected String getOperator() {
		return "is not null";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#getPreparedStr()
	 */
	public String getPreparedStr() {
		return this.fieldName + " " + getOperator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.CriticalItem#setValue(java.sql.PreparedStatement,
	 *      int)
	 */
	public int setValue(PreparedStatement ps, int number) throws SQLException {
		return number;
	}

}