package haiyan.web.orm;

import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
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
		for (Field field:table.getField()) {
			Object v = null;
			String uiName = field.getUiname();
			if (!StringUtil.isEmpty(uiName)) {
				v = getValue(req, uiName);
			} 
			if (StringUtil.isEmpty(v)) {
				v = field.getDefaultValue(); 
			}
			// TODO 转成各种数据类型
			if (field.getJavaType()==AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
				v = VarUtil.toBigDecimal(v);
			}
			if (!StringUtil.isEmpty(v)) {
				String dbName = field.getName();
				this.set(dbName, v);
			} else if (!field.isNullAllowed()) {
				//throw Warning("not allow empty value, field="+);
			}
		}
	}
	private static String getValue(ServletRequest req, String key) throws Throwable {
		String v = (String) (req.getParameter(key)==null?req.getAttribute(key):req.getParameter(key));
		if (!StringUtil.isEmpty(v))
			v = java.net.URLDecoder.decode(v, "UTF-8");
		return v;
	}

}
