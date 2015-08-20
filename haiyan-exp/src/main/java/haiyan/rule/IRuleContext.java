package haiyan.rule;

import haiyan.common.intf.session.IContext;
import net.sf.json.JSONObject;

public interface IRuleContext extends IContext {
	Object evaluate() throws Throwable;
	IActivityContext getParent();
	JSONObject getActivity();
	void removeActivity();
	void setActivity(JSONObject activity);
	JSONObject getRule();
	void removeRule();
	void setRule(JSONObject rule);
}
