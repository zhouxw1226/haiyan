package haiyan.web.orm;

import haiyan.common.DateUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.orm.database.DBRecord;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.servlet.ServletRequest;

public abstract class AbstractRequestRecord extends DBRecord {
	private static final long serialVersionUID = 1L;
	protected static Object transValueType(Field field, Object v) {
		// TODO 转成各种数据类型
		if (field.getJavaType()==AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
			v = VarUtil.toBigDecimal(v);
		} else if (field.getJavaType()==AbstractCommonFieldJavaTypeType.DATE) {
			if (StringUtil.isNumeric(v)) {
				long time = VarUtil.toLong(v);
				Calendar gcal = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
////			BaseCalendar gcal = CalendarSystem.getGregorianCalendar();
				gcal.setTimeInMillis(time);
//				gcal.getTime();
//				gcal.getDisplayName(field, style, locale)
				String style = field.getDataStyle();
				if (StringUtil.isEmpty(style)) // 转换成UTC时间
					style = "yyyy-MM-dd HH:mm:ss";
				v = DateUtil.format(gcal.getTime(), style);
				//v = DateUtil.format(new Date(time), style);
			}
		}
		return v;
	}
	protected static String getClientValue(ServletRequest req, String key) throws Throwable {
		String v = (String) (req.getParameter(key)==null?req.getAttribute(key):req.getParameter(key));
		if (!StringUtil.isEmpty(v))
			v = java.net.URLDecoder.decode(v, "UTF-8");
		return v;
	}
}
