package haiyan.rule;

import haiyan.common.intf.session.IContext;
import net.sf.json.JSONObject;

public interface IActivityContext extends IContext {
	Object evaluate() throws Throwable;
	JSONObject getActivity();
	void removeActivity();
	void setActivity(JSONObject activity);
}
