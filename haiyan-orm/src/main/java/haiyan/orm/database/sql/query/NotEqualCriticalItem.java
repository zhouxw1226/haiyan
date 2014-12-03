package haiyan.orm.database.sql.query;

/**
 * @author zhouxw
 */
public class NotEqualCriticalItem extends SingleOprCriticalItem {

    public NotEqualCriticalItem(String fieldName, Object value, Class<?> type) {
        super(fieldName, value, type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.haiyan.genmis.util.queryengine.SingleOprCriticalItem#getOperator()
     */
    protected String getOperator() {
        return "<>";
    }

}