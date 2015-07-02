package haiyan.rule;

import haiyan.common.DebugUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.session.AppContext;
import net.sf.json.JSONObject;

public class RuleContext extends AppContext implements IRuleContext {

	public RuleContext() {
		super();
	}
	public RuleContext(IActivityContext parent) {
		super(parent);
	}
	@Override
	public IActivityContext getParent() {
		return (IActivityContext)this.parent;
	}
	@Override
	public Object evalRule(JSONObject rule) throws Throwable {
		IExpUtil expUtil = this.getExpUtil();
		String codeRule = rule.getString("code");
		DebugUtil.debug("规则编号：\n"+codeRule+"\n");
		String condition = rule.getString("condition");
		boolean pass = VarUtil.toBool(expUtil.evalExp(condition));
		if (pass) {
			String result = rule.getString("result");
			expUtil.evalExp(result);
		}
		return pass;
	}
	@Override
	public JSONObject removeCurrentActivity() {
		JSONObject json = (JSONObject)this.getParent().getAttribute("__activity");
		this.removeAttribute("__activity");
		return json;
	}
	@Override
	public JSONObject getCurrentActivity() {
		return (JSONObject)this.getParent().getAttribute("__activity");
	}
	@Override
	public JSONObject getCurrentRule() {
		return (JSONObject)this.getAttribute("__rule");
	}

}
