package haiyan.orm.database.sql.query;

/**
 * @author zhouxw
 */
public class GreaterCriticalItem extends SingleOprCriticalItem {

	/**
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public GreaterCriticalItem(String fieldName, Object value, Class<?> type) {
		super(fieldName, value, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.SingleOprCriticalItem#getOperator()
	 */
	protected String getOperator() {
		return ">";
	}

}