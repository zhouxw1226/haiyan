package haiyan.rule;

import haiyan.common.intf.session.IContext;
import net.sf.json.JSONObject;

public interface IActivityContext extends IContext {
	Object evalRule(JSONObject activity) throws Throwable;
	JSONObject getCurrentActivity();
	JSONObject removeCurrentActivity();
}
