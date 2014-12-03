package haiyan.orm.database.sql.query;

/**
 * @author zhouxw
 */
public class UpperThanCriticalItem extends SingleOprCriticalItem {

	/**
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public UpperThanCriticalItem(String fieldName, Object value, Class<?> type) {
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
	 * @see
	 * com.haiyan.genmis.util.queryengine.SingleOprCriticalItem#getOperator()
	 */
	protected String getOperator() {
		return ">";
	}

}