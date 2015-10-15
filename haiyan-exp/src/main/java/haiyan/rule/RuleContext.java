package haiyan.rule;

import haiyan.common.DebugUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.session.AppContext;
import haiyan.exp.ExpUtil;
import net.sf.json.JSONObject;

/**
 * @author ZhouXW
 *
 */
public class RuleContext extends AppContext implements IRuleContext {
	public static final String RULE_TAG_CODE = "code";
	public static final String RULE_TAG_CONDITION = "condition";
	public static final String RULE_TAG_ACTION = "action";
	public static final String RULE_VAR = "__rule";
	public RuleContext(IActivityContext parent, JSONObject rule) {
		super(parent);
		this.rule = rule;
		this.setExpUtil(new ExpUtil(this));
	}
	@Override
	public IActivityContext getParent() {
		return (IActivityContext)this.parent;
	}
	private JSONObject rule;
	@Override
	public Object evaluate() throws Throwable {
		IExpUtil expUtil = this.getExpUtil();
		String codeRule = rule.getString(RULE_TAG_CODE);
		DebugUtil.debug("规则编号："+codeRule);
		String condition = rule.getString(RULE_TAG_CONDITION);
		DebugUtil.debug("规则条件："+condition);
		boolean pass = VarUtil.toBool(expUtil.evalExp(condition));
		if (pass) {
			String action = rule.getString(RULE_TAG_ACTION);
			pass = VarUtil.toBool(expUtil.evalExp(action));
			DebugUtil.debug("规则结果："+pass);
			return pass;
		}
		DebugUtil.debug("规则未执行但结果：true");
		return true;
	}
	@Override
	public void removeActivity() {
		this.getParent().removeActivity();
	}
	@Override
	public JSONObject getActivity() {
		return this.getParent().getActivity();
	}
	@Override
	public void setActivity(JSONObject activity) {
		this.getParent().setActivity(activity);
	}
	@Override
	public void removeRule() {
		this.rule = null;
	}
	@Override
	public JSONObject getRule() {
		return this.rule;
	}
	@Override
	public void setRule(JSONObject rule) {
		this.rule = rule;
	}

}
