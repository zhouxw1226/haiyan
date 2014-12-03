package haiyan.orm.database.sql.query;

import haiyan.common.StringUtil;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.orm.database.sql.SQLDBHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public class RefLikeCriticalItem extends SingleOprCriticalItem {

    /**
     * @param fieldName
     * @param value
     * @param type
     * @param mainTable
     * @param field
     * @throws Throwable
     */
    public RefLikeCriticalItem(String fieldName, Object value, Class<?> type,
            Table mainTable, Field field) throws Throwable {
        super(sqlLower(getRefName(fieldName,mainTable, field)), value, type);
    }
    /**
     * @param fieldName
     * @param mainTable
     * @param field
     * @return String
     * @throws Throwable
     */
    private static String getRefName(String fieldName, Table mainTable, Field field)
            throws Throwable {
        String indexName = SQLDBHelper.getIndexName(mainTable, field);
        if (!StringUtil.isBlankOrNull(indexName))
            return indexName;
        return fieldName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.haiyan.genmis.util.queryengine.SingleOprCriticalItem#getOperator()
     */
    protected String getOperator() {
        return "like";
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