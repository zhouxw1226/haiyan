package haiyan.rule.function;

import haiyan.common.VarUtil;
import haiyan.common.intf.exp.IFunction;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Table;
import haiyan.rule.ActivityContext;
import haiyan.rule.IActivityContext;
import haiyan.rule.IRuleContext;
import haiyan.rule.RuleContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
  
/**
 * @author ZhouXW
 *
 */
public class DefaultFunction { 
	private static final String LOOP = "__loop";
	private static IContext getVarContext(IContext context) {
		if (context instanceof IRuleContext) {
			context = ((IRuleContext)context).getParent();
		}
		if (context instanceof IActivityContext) {
			context = ((IActivityContext)context).getParent();
		}
		return context;
	}
//	@IFunction(name="__ZJDZ")
//	public static Object __ZJDZ(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
//		IContext varContext = getVarContext(context);
//		IDBRecord record = (IDBRecord)varContext.getAttribute("__record");
//		double d0 = record.getDouble("SALE_MONEY");
//		double d1 = d0*(1-VarUtil.toDouble(paras[0]));
//		record.set("SALE_MONEY", d1);
//		return true;
//	}
	@IFunction(name="Echo")
	public static Object echo(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		System.out.println(paras[0]);
		return true;
	}
	@IFunction(name="Goto")
	public static Object goto_(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		//IContext varContext = getVarContext(context);
		String ruleCode = VarUtil.toString(paras[0]);
		JSONObject activity = null;
		if (context instanceof IActivityContext) {
			activity = ((IActivityContext)context).getActivity();
		} else if (context instanceof IRuleContext) {
			activity = ((IRuleContext)context).getActivity();
		}
		JSONArray rules = activity.getJSONArray(ActivityContext.ACTIVITY_TAG_RULES);
		for (int i=0;i<rules.size();i++) {
			JSONObject rule = rules.getJSONObject(i);
			if (ruleCode.equalsIgnoreCase(rule.getString(RuleContext.RULE_TAG_CODE))) {
				((IRuleContext)context).setRule(rule);
				((IRuleContext)context).evaluate();
				break;
			}
		}
		return false;
	}
	@IFunction(name="UpdateActivity")
	public static Object updateRule(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		IContext varContext = getVarContext(context);
		Object oloop = varContext.getAttribute(LOOP);
		if (oloop==null) {
			varContext.setAttribute(LOOP, 1);
		} else {
			varContext.setAttribute(LOOP, VarUtil.toInt(oloop)+1);
		}
		if (paras.length>0) {
			boolean loop = VarUtil.toBool(paras[0]);
			if (!loop) {
				return true;
			} else {
				if (context instanceof IActivityContext) {
					IActivityContext c = ((IActivityContext)context);
					JSONObject activity = c.getActivity();
					if (activity==null)
						return true;
					return c.evaluate();
				} else if (context instanceof IRuleContext) {
					IRuleContext c = ((IRuleContext)context);
					JSONObject activity = c.getActivity();
					if (activity==null)
						return true;
					return c.getParent().evaluate();
				}
			}
		}
		return true;
	}
	@IFunction(name="GetLoop")
	public static Object getLoop(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		IContext varContext = getVarContext(context);
		Object oloop = varContext.getAttribute(LOOP);
		return VarUtil.toInt(oloop);
	}
	@IFunction(name="NoLoop")
	public static Object noLoop(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		IContext varContext = getVarContext(context);
		Object oloop = varContext.getAttribute(LOOP);
		if (oloop==null) {
			return true;
		} else {
			if (paras.length>0) {
				int max = VarUtil.toInt(paras[0]);
				boolean canloop = VarUtil.toInt(oloop)<=max-1;
				if (!canloop) {
					if (varContext instanceof IActivityContext) {
						((IActivityContext)varContext).removeActivity();
					} else if (varContext instanceof IRuleContext) {
						((IRuleContext)varContext).removeActivity();
					}
				}
				return canloop;
			}
			return VarUtil.toInt(oloop)<=0;
		}
	}

}
