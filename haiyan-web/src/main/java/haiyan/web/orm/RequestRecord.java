package haiyan.web.orm;

import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.orm.database.DBRecord;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestRecord extends DBRecord {

	private static final long serialVersionUID = 1L;
	public RequestRecord(ServletRequest req, ServletResponse res, Table table) throws Throwable {
		super();
		parseRequest(req, table, this);
	}
	public RequestRecord(ServletRequest req, ServletResponse res, Table table, boolean useDefault) throws Throwable {
		super();
		parseRequest(req, table, this, useDefault);
	}
	public static void parseRequest(ServletRequest req, Table table, IDBRecord record, boolean useDefault) throws Throwable {
		for (Field field:table.getField()) {
			Object v = null;
			String uiName = field.getUiname();
			if (!StringUtil.isEmpty(uiName)) {
				v = getValue(req, uiName);
			} 
			if (StringUtil.isEmpty(v) && (useDefault || !field.isNullAllowed())) {
				if(field.getJavaType() == AbstractCommonFieldJavaTypeType.INTEGER){//TODO 其他类型
					v = Integer.valueOf(field.getDefaultValue());
				}else{
					v = field.getDefaultValue();
				}
			}
			if (!StringUtil.isEmpty(v)) {
				v = transValueType(field, v);
				String dbName = field.getName();
				record.set(dbName, v);
			} else if (!field.isNullAllowed()) {
				//throw Warning("not allow empty value, field="+);
			}
		}
	}
	public static void parseRequest(ServletRequest req, Table table, IDBRecord record) throws Throwable {
		parseRequest(req, table, record, true);
	}
	private static Object transValueType(Field field, Object v) {
		// TODO 转成各种数据类型
		if (field.getJavaType()==AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
			v = VarUtil.toBigDecimal(v);
		}
		return v;
	}
	private static String getValue(ServletRequest req, String key) throws Throwable {
		String v = (String) (req.getParameter(key)==null?req.getAttribute(key):req.getParameter(key));
		if (!StringUtil.isEmpty(v))
			v = java.net.URLDecoder.decode(v, "UTF-8");
		return v;
	}

}
