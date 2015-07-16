package haiyan.web.orm;

import haiyan.common.StringUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestRecord extends AbstractRequestRecord {

	private static final long serialVersionUID = 1L;
	public RequestRecord(ServletRequest req, ServletResponse res, Table table) throws Throwable {
		super();
		parseRequest(req, table, this);
	}
	public static void parseRequest(ServletRequest req, Table table, IDBRecord record) throws Throwable {
		for (Field field:table.getField()) {
			Object v = null;
			String uiName = field.getUiname();
			if (!StringUtil.isEmpty(uiName)) {
				v = getClientValue(req, uiName);
			} 
			if (StringUtil.isEmpty(v)) {
				if (!field.isNullAllowed()) {
					if(field.getJavaType() == AbstractCommonFieldJavaTypeType.INTEGER){
						v = Integer.valueOf(field.getDefaultValue());
					}
//					//TODO 其他类型
//					else if(field.getJavaType() == AbstractCommonFieldJavaTypeType.BIGDECIMAL){
//						v = new BigDecimal(field.getDefaultValue());
//					}
					else{
						v = field.getDefaultValue();
					}
				}
			}
			if (!StringUtil.isEmpty(v)) {
				v = transValueType(field, v);
				String dbName = field.getName();
				record.set(dbName, v); // setValue and setUpdateStatus
			} else if (!field.isNullAllowed()) {
				//throw Warning("not allow empty value, field="+);
			}
		}
	}

}
