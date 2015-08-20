package haiyan.web.orm;

import javax.servlet.ServletRequest;

import haiyan.common.StringUtil;
import haiyan.orm.database.DBRecord;

public abstract class AbstractRequestRecord extends DBRecord {
	private static final long serialVersionUID = 1L;
	protected static Object getClientValue(ServletRequest req, String key) throws Throwable {
		Object v = req.getParameter(key)==null?req.getAttribute(key):req.getParameter(key);
		if (!StringUtil.isEmpty(v) && v instanceof String)
			v = java.net.URLDecoder.decode((String)v, "UTF-8");
		return v;
	}
}
