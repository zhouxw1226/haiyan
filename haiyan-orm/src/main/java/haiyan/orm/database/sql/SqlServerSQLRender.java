/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.orm.database.sql.query.CriticalItem;
import haiyan.orm.database.sql.query.EqualCriticalItem;

/**
 * @author zhouxw
 */
class SqlServerSQLRender extends SQLRender {
    @Override
    protected CriticalItem getEqualCriticalItem(IDBRecord record,
            Table table, Field field, String fullFieldName) throws Throwable {
        if (StringUtil.isBlankOrNull(record.get(field.getName())))
            return null;
        CriticalItem item = new SqlServerEqualCriticalItem(fullFieldName,
                CriticalItem.getItemValue(record, field.getName(), table,
                        field), SQLDBTypeConvert.getJavaType(field));
        return item;
    }
    /**
     * @author zhouxw
     * 
     */
    private static class SqlServerEqualCriticalItem extends EqualCriticalItem {
        public SqlServerEqualCriticalItem(String fieldName, Object value,
                Class<?> type) {
            super(fieldName, value, type);
        }
        @Override
        public String getPreparedStr() {
            if (this.isValueValid(this.value)) {
                if (type.equals(String.class))
                    return this.fieldName + " " + getOperator() + " '" + this.value + "' ";
                else
                    return this.fieldName + " " + getOperator() + " " + this.value + " ";
            } else {
                return "";
            }
        }
        @Override
        public int setValue(PreparedStatement ps, int number)
                throws SQLException {
            return number;
        }
    }
    @Override
    protected String getInsertSQL(Table table, Field[] validFields)
            throws ParseException {
        StringBuffer buf = new StringBuffer().append("insert into ").append(
                getDBName(table)).append(" (").append(
                table.getId().getName());
        StringBuffer bufValue = new StringBuffer().append(" values (" + trim(null));
        for (int i = 0; i < validFields.length; i++) {
            buf.append(",");
            buf.append(validFields[i].getName());
            bufValue.append(",");
            bufValue.append(trim(validFields[i]));
            // bufValue.append("rtrim(?)");
        }
        buf.append(")");
        bufValue.append(")");
        buf.append(bufValue.toString());
        // String reault = buf.toString();
        mainSQL = buf.toString();
        DebugUtil.info(">InsertSQL=" + mainSQL);
        return mainSQL;
    }
    /**
     * @param field
     * @return String
     */
    private String trim(Field field) {
        // if (field == null)
        // return "rtrim(?)";
        // else {
        // if (field.getJavaType() == AbstractCommonFieldJavaTypeType.STRING)
        // return "rtrim(?)";
        // }
        return "?";
    }
}