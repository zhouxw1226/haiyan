/*
 * Created on 2004-10-12
 */
package haiyan.orm.database.sql;

import static haiyan.config.util.ConfigUtil.getDisplayRefFields;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import haiyan.common.ByteUtil;
import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDBClear;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBClear;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Option;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.util.ConfigUtil;
import haiyan.config.util.NamingUtil;
import haiyan.orm.database.DBPage;
import haiyan.orm.database.sql.query.CriticalItem;
import haiyan.orm.database.sql.query.InCriticalItem;
import haiyan.orm.intf.session.ITableDBContext;
import oracle.sql.BLOB;

/**
 * @author zhouxw
 */
public class SQLDBTypeConvert {

    final static String DEFAULT_DATESTYLE = "yyyy-MM-dd";
    final static String DEFAULT_TIMESTAMPSTYLE = "yyyy-MM-dd HH:mm:ss";
    final static String DEFAULT_SYSDATESTYLE = "sysdate";
    /**
     * @param field
     * @return Class
     */
    static Class<?> getJavaType(AbstractField field) {
        AbstractCommonFieldJavaTypeType type = field.getJavaType();
        if (type==AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
            return BigDecimal.class;
        } else if (type==AbstractCommonFieldJavaTypeType.STRING) {
            return String.class;
        } else if (type==AbstractCommonFieldJavaTypeType.DATE) {
            return Timestamp.class;
        } else if (type==AbstractCommonFieldJavaTypeType.BLOB) {
            return String.class;
        } else if (type==AbstractCommonFieldJavaTypeType.DBBLOB) {
            return Blob.class;
        } else if (type==AbstractCommonFieldJavaTypeType.DBCLOB) {
            return Clob.class;
        }else if (type==AbstractCommonFieldJavaTypeType.INTEGER) {
            return Integer.class;
        }  else {
            throw new Warning("Unkown field type:" + type);
        }
    }
    /**
     * @param ps
     *            PreparedStatement
     * @param clear
     * @param index
     * @param field
     * @param value
     * @throws Throwable
     */
    @SuppressWarnings("deprecation")
	static void setValue(PreparedStatement ps, IDBClear clear, int index,
    		AbstractField field, Object value) throws Throwable {
        // HASIT:System.out.println("##setValue().fieldName=" + field.getName());
        AbstractCommonFieldJavaTypeType type = field.getJavaType();
        value = StringUtil.isBlankOrNull(value) ? null : value;
//        if (field.isSafeHtml() && value!=null) {
//        	value = HtmlUtil.getHtmlSafeStr(VarUtil.toString(value));
//        }
        if (AbstractCommonFieldJavaTypeType.BIGDECIMAL==type) {
            BigDecimal b = toBigDecimal(value, field);
            if (b == null)
                ps.setNull(index, Types.NUMERIC);
            else 
                ps.setBigDecimal(index, b);
        } else if (AbstractCommonFieldJavaTypeType.DATE==type) {
            // NOTICE 纠错功能 是否要判断是否多选
            if (!StringUtil.isBlankOrNull(value) && field instanceof Field && !((Field)field).isMultipleSelect()) {
                if (value instanceof String && value.toString().indexOf(",") >= 0) {
                    value = value.toString().substring(0, value.toString().indexOf(","));
                }
            } else {
            	// Nothing
            }
            java.sql.Timestamp t = toTimestamp(value, field);
            ps.setTimestamp(index, t);
        } else if (AbstractCommonFieldJavaTypeType.STRING==type) {
            ps.setString(index, VarUtil.toString(value));
        } else if (AbstractCommonFieldJavaTypeType.BLOB==type) {
            ps.setString(index, VarUtil.toString(value));
        } else if (AbstractCommonFieldJavaTypeType.DBCLOB==type) {
            Reader reader = null;
            try {
            	String v = value==null?"":VarUtil.toString(value);
            	reader = new StringReader(v);
            	ps.setCharacterStream(index, reader, v.length());
            } finally {
                if (reader != null) {
                	DebugUtil.debug(">addClear=" + clear);
                    ((ISQLDBClear)clear).addReader(reader); // 经测试必须先提交后关闭
                }
            }
        } else if (AbstractCommonFieldJavaTypeType.DBBLOB==type) {
            if (SQLDBHelper.isOracle(ps.getConnection())) {
                BLOB blob = null;
                try {
                    blob = BLOB.createTemporary(ps.getConnection(), true, BLOB.MODE_READWRITE);
                    if (value != null)  {
	                	String v = value==null?"":VarUtil.toString(value);
	                    blob.putBytes(1, v.getBytes());
                    } else {
                    	blob.putBytes(1, ByteUtil.EMPTY_BYTES);
                    }
                    ps.setBlob(index, blob);
                } finally {
                    if (blob != null) {
                        DebugUtil.debug(">addClear=" + clear);
                        ((ISQLDBClear)clear).addBlob((Blob)blob); // 经测试必须先提交后关闭
                    }
                }
            } else {
                InputStream ins = null;
                try {
                    if (value != null)  {
                    	String v = value==null?"":VarUtil.toString(value);
                        ins = new ByteArrayInputStream(v.getBytes());
                    } else {
                        ins = new ByteArrayInputStream(ByteUtil.EMPTY_BYTES);
                    }
                    ps.setBinaryStream(index, ins, ins.available());
                } finally { 
                	CloseUtil.close(ins); // 经测试可以直接关闭 
                }
            }
        } else if (AbstractCommonFieldJavaTypeType.INTEGER==type) {
            ps.setInt(index, VarUtil.toInt(value));
        } else {
            throw new Warning(null, 100028, "unsupport_javatype", new String[] { type.toString() });
        }
        // DebugUtil.debug("#DT.setValue(" + field.getName() + ")=" + value);
    }
    /**
     * @param context
     * @param rs
     * @param clear
     * @param index
     * @param table
     * @param field
     * @return
     * @throws Throwable
     */
    public static Object getValue(ITableDBContext context, ResultSet rs, IDBClear clear, int index,
            Table table, AbstractField field) throws Throwable {
        try {
            // System.out.println("##getValue().fieldName=" + field.getName());
            AbstractCommonFieldJavaTypeType type = field.getJavaType();
            Object value = null;
            if (type.equals(AbstractCommonFieldJavaTypeType.BIGDECIMAL)) {
                value = rs.getBigDecimal(index);
                if (value != null) {
                    NumberFormat nf = new DecimalFormat();
                    if (field.hasMaxFractionDigit())
                        nf.setMaximumFractionDigits((int) field.getMaxFractionDigit());
                    if (field.hasMinFractionDigit())
                        nf.setMinimumFractionDigits((int) field.getMinFractionDigit());
                    if (field instanceof Field)
                    nf.setGroupingUsed(((Field)field).getNumberGrouping());
                    value = nf.format(value);
                }
            } else if (type.equals(AbstractCommonFieldJavaTypeType.DATE)) {
                value = rs.getTimestamp(index);
                if (value != null) {
                    String dateStyle = null;
                    if (!StringUtil.isBlankOrNull(field.getDataStyle())) {
                        dateStyle = field.getDataStyle();
                    }
                    if (dateStyle != null && dateStyle.equals(DEFAULT_TIMESTAMPSTYLE)) {
                        value = value.toString().substring(0, 19);
                    } else {
                        dateStyle = DEFAULT_DATESTYLE;
                        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
                        value = sdf.format(value);
                    }
                }
            } else if (type.equals(AbstractCommonFieldJavaTypeType.STRING)) {
                value = rs.getString(index);
                value = (value == null) ? "" : value;
            } else if (type.equals(AbstractCommonFieldJavaTypeType.BLOB)) {
                value = rs.getString(index);
                String result = null;
                if (value != null && !StringUtil.isBlankOrNull(value.toString())) {
                	throw new Warning("大文本BLOB处理未实现"); // 大文本内容保存在磁盘里并非内存里
                    // DebugUtil.debug(">blob:" + NamingUtil.getUploadFilePath(table, field) + value);
//                    try {
//                        result = FileUtil.toString(
//                        	ConfigUtil.getUploadFilePath(context, table, field) + value, DataConstant.CHARSET);
//                    } catch (Throwable ex) {
//                        DebugUtil.error(ex);
//                        result = "ERROR[" + ConfigUtil.getUploadFilePath(context, table,field) + value + "]";
//                    }
                }
                value = (result == null) ? "" : result;
            } else if (type.equals(AbstractCommonFieldJavaTypeType.DBCLOB)) {
                String result = null;
                Reader reader = null;
                try {
                	reader = rs.getCharacterStream(index);
                    result = ByteUtil.getString(reader);
                } finally {
                	CloseUtil.close(reader);
                }
                value = (result == null) ? "" : result;
            } else if (type.equals(AbstractCommonFieldJavaTypeType.DBBLOB)) {
                String result = null;
                InputStream ins = null;
                try {
                    ins = rs.getBinaryStream(index);
                    if (ins!=null)
                    	result = new String(ByteUtil.getBytes(ins));
                } finally {
                	CloseUtil.close(ins);
                }
                value = (result == null) ? "" : result;
            } else if (type.equals(AbstractCommonFieldJavaTypeType.INTEGER)) {
            	value = rs.getInt(index);
                value = (value == null) ? 0 : value;
            } else {
                throw new Warning(100028, "unsupport_javatype", new String[] { type.toString() });
            }
            return  value;
        } catch (Throwable ex) {
            DebugUtil.error(ex);
            return ex.getMessage();
        }
    }
    /**
     * @param strTime
     * @param field
     * @return Timestamp
     * @throws Throwable
     */
    public static java.sql.Timestamp toTimestamp(Object date, AbstractField field) throws Throwable {
        if (date==null)
        	return null;
        if (date instanceof java.util.Date) {
            return new java.sql.Timestamp(((java.util.Date)date).getTime());
        }
        String time = null;
        if (!StringUtil.isBlankOrNull(date)) {
            time = date.toString();
        } else if (!StringUtil.isBlankOrNull(field.getDefaultValue())) {
            time = field.getDefaultValue().toString();
        }
        if (time == null) {
            return null;
        }
        java.sql.Timestamp result = null;
        // SQL Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
        if (!time.equals(DEFAULT_SYSDATESTYLE)) {
            if (time.trim().length() == 10) {
                time += " 00:00:00";
            }
            boolean isPhp = time.indexOf("T") > 0 && time.endsWith("Z");
            // for php time style 带T的要加8小时
            time = time.replaceAll("T", " ").replaceAll("Z", "");
            if (time.startsWith("\\"))
                time = time.substring(1);
            if (time.endsWith("\\"))
                time = time.substring(0, time.length() - 1);
            // DebugUtil.debug(time + " " + time.length());
            String dateStyle = DEFAULT_DATESTYLE;
            if (!StringUtil.isBlankOrNull(field.getDataStyle())) {
                dateStyle = field.getDataStyle();
            }
//          DEFAULT_TIMESTAMPSTYLE
            SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
            result = new java.sql.Timestamp(sdf.parse(time).getTime() + (isPhp ? 8 * 60 * 60 * 1000 : 0));
        } else {
            result = new java.sql.Timestamp(new java.util.Date().getTime());
        }
        // throw new Warning("" + result);
        return result;
    }
    /**
     * @param sDecimal
     * @param field
     * @return BigDecimal
     * @throws Throwable
     */
    public static BigDecimal toBigDecimal(Object sDecimal, AbstractField field)
            throws Throwable {
    	if (sDecimal==null)
    		return null;
        // BigDecimal result = null;
        if ("on".equals(sDecimal) || "yes".equals(sDecimal)) {
            return new BigDecimal("1");
        } else if ("off".equals(sDecimal) || "no".equals(sDecimal)) {
            return new BigDecimal("0");
        } else if (!StringUtil.isBlankOrNull(sDecimal)) {
            sDecimal = StringUtil.replaceAll(sDecimal.toString(), ",", "");
            if (StringUtil.isNumeric(sDecimal)) {
                return new BigDecimal(sDecimal.toString());
            }
        }
        // return new BigDecimal(Types.NUMERIC);
        return null;
    }
    /**
     * @param context
     * @param form
     * @param table
     * @param field
     * @param disValue
     * @return IRecord
     * @throws Throwable
     */
    public static IDBRecord byDisValue(ITableDBContext context, IDBRecord form,
            Table table, Field field, String disValue) throws Throwable {
        if (StringUtil.isBlankOrNull(disValue))
            return form;
        String formValue = disValue;
//        if (ExpUtil.isFormula(disValue) && disValue.endsWith("$")) {
//            formValue = ""
//                + new ExpUtil(context, table, form).evalExp(disValue
//                	.substring(1, disValue.length() - 1));
//            form.set(field.getName(), formValue);
//            return form;
//        }
        String[] showValues = StringUtil.split(disValue, ",");
        String idValues = "";
        if (field.getOption().length > 0) {
            String idValuesOption = "";
            Option[] options = field.getOption();
            for (int i = 0; i < options.length; i++) {
                for (int j = 0; j < showValues.length; j++) {
                    if (showValues[j].equals(options[i].getDisplayName())) {
                        if (idValuesOption.length() > 0)
                            idValuesOption += ",";
                        idValuesOption += options[i].getValue();
                    }
                }
            }
            if (StringUtil.isBlankOrNull(idValuesOption))
                throw new Warning(
                        "IDValues in Options is null. table.getDescription():"
                                + table.getDescription()
                                + ", field.getDescription():"
                                + field.getDescription() + ", disValue:"
                                + disValue + ". ");
            idValues += idValuesOption;
        }
        if (field.getReferenceTable() != null) {
            String idValuesRef = "";
            Table refTable = ConfigUtil.getTable(field.getReferenceTable());
            CriticalItem item = new InCriticalItem(" t_1." + ConfigUtil.getDisplayRefFields(field)[0] + " ", showValues, String.class);
            String sFilter = " and " + item.toString();
            SQLDBFilter filter = new SQLDBFilter(sFilter);
            IDBResultSet page = context.getDBM().select(context, refTable, filter, DBPage.MAXCOUNT_PERQUERY, 1);
            for (int i = 0; i < page.getTotalRecordCount(); i++) {
                if (i > 0)
                    idValuesRef += ",";
                idValuesRef += page.getRecord(i).get(field.getReferenceField());
            }
            if (StringUtil.isBlankOrNull(idValuesRef))
                throw new Warning("IDValues of Reference is null. table.name="
                        + table.getName() + ", table.description="
                        + table.getDescription() + ", field.description="
                        + field.getDescription() + ", field.disValue="
                        + disValue + ". ");
            if (!StringUtil.isBlankOrNull(idValues))
                idValues += ",";
            idValues += idValuesRef;
        }
        if (!StringUtil.isBlankOrNull(idValues))
            formValue = idValues;
        form.set(field.getName(), formValue);
        return form;
    }
    /**
     * @param context
     * @param field
     * @return String[][]
     * @throws Throwable
     */
    public static String[][] initOptions(ITableDBContext context, Field field)
            throws Throwable {
        Option[] defOptions = field.getOption();
        ArrayList<String[]> optionList = new ArrayList<String[]>();
        if (!StringUtil.isBlankOrNull(field.getReferenceTable())) {
            Table refTable = ConfigUtil.getTable(field.getReferenceTable());
            // DebugUtil.debug("+++++" + refTable.getName());
            IDBResultSet page = null;
            // context.setAttribute("hiddenQuickCondition__ignored", "1");
            page = context.getDBM().select(context, refTable, (IDBRecord)null, DBPage.MAXCOUNT_PERQUERY, 1);
            // context.setAttribute("hiddenQuickCondition__ignored", "0");
            Collection<IDBRecord> datas = null;
            if (page != null)
                datas = page.getRecords();
            else
                datas = new ArrayList<IDBRecord>();
            String[][] dbOptions = new String[datas.size()][2];
            Iterator<?> iter = datas.iterator();
            int count = 0;
            while (iter.hasNext()) {
                IDBRecord form = (IDBRecord) iter.next();
                dbOptions[count][0] = (String)form.get(getDisplayRefFields(field)[0]);
                if (field.getReferenceField() != null)
                    dbOptions[count][1] = (String)form.get(field.getReferenceField());
                else
                    dbOptions[count][1] = (String)form.get(refTable.getId().getName());
                count++;
            }
            optionList.addAll(Arrays.asList(dbOptions));
        }
        if (defOptions != null)
            for (int i = 0; i < defOptions.length; i++) {
                optionList.add(new String[] { defOptions[i].getDisplayName(), defOptions[i].getValue() });
            }
        return (String[][]) optionList.toArray(new String[0][0]);
    }
    /**
     * 获取选项显示值
     * 
     * @param context
     * @param table
     * @param field
     * @param srcValue
     * @return String
     * @throws Throwable
     */
    String getOptionNameByValue(ITableDBContext context, Table table, Field field, String srcValue) throws Throwable {
        // if (field.getName().equals("DEALLEVEL"))
        // DebugUtil.debug(">getOptionNameByValue.srcValue=" + srcValue);
        String[] values = StringUtil.split(srcValue, ",");
        if (optionMap == null) { // NOTICE 平台唯一的数据缓冲区
            // TODO 要用Cache
            optionMap = new HashMap<String, String>();
        }
        String result = "";
        for (int j = 0; j < values.length; j++) {
            String keyValue = table.getName() + NamingUtil._DELIMITER
                    + field.getName() + NamingUtil._DELIMITER + values[j];
            // if (j != 0)
            if (result.length() > 0)
                result += " "; // 分隔符
            synchronized (optionMap) {
                if (optionMap.get(keyValue) == null) {
                    String[][] options = initOptions(context, field);
                    for (int i = 0; i < options.length; i++) {
                        if (options[i][1].equals(values[j])) {
                            optionMap.put(keyValue, options[i][0]);
                            break;
                        }
                    }
                }
                // NOTICE 还是空,没找到匹配项
                if (optionMap.get(keyValue) == null)
                    optionMap.put(keyValue, values[j]);
                result += (String) optionMap.get(keyValue);
            }
        }
        return result;
    }
    void clearCache() {
        if (optionMap != null)
            optionMap.clear();
    }
    // options value caption map
    private transient HashMap<String, String> optionMap;
    private static final String[] EMPTY_STRARR = new String[0];
	public static String[] toStringArray(Object obj) {
		if (obj instanceof String[])
			return (String[])obj;
		else if (obj instanceof String) {
			return StringUtil.split(obj.toString(), ",");
		}
		return EMPTY_STRARR;
	}

}