package haiyan.rule;

import haiyan.common.DebugUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.session.AppContext;
import net.sf.json.JSONObject;

/**
 * @author ZhouXW
 *
 */
public class RuleContext extends AppContext implements IRuleContext {

	private static final String RULE = "__rule";
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
		this.setAttribute(RULE, rule);
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
		JSONObject json = this.getParent().getCurrentActivity();
		this.getParent().removeCurrentActivity();
		return json;
	}
	@Override
	public JSONObject getCurrentActivity() {
		return this.getParent().getCurrentActivity();
	}
	@Override
	public JSONObject getCurrentRule() {
		return (JSONObject)this.getAttribute(RULE);
	}

}
