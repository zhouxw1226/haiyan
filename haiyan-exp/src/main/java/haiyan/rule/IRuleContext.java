package haiyan.rule;

import haiyan.common.intf.session.IContext;
import net.sf.json.JSONObject;

public interface IRuleContext extends IContext {
	Object evalRule(JSONObject rule) throws Throwable;
	JSONObject getCurrentRule();
	JSONObject getCurrentActivity();
	JSONObject removeCurrentActivity();
	IActivityContext getParent();
}
