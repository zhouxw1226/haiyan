package haiyan.rule.function;

import haiyan.common.VarUtil;
import haiyan.common.intf.exp.IFunction;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Table;
import haiyan.rule.IActivityContext;
import haiyan.rule.IRuleContext;
import net.sf.json.JSONObject;
  
/**
 * @author ZhouXW
 *
 */
public class DefaultFunction { 

	@IFunction(name="UpdateRule")
	public static Object updateRule(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		IContext curContext = context;
		if (curContext instanceof IRuleContext) {
			curContext = ((IRuleContext)curContext).getParent();
		}
		Object oloop = curContext.getAttribute("__loop");
		if (oloop==null) {
			curContext.setAttribute("__loop", 1);
		} else {
			curContext.setAttribute("__loop", VarUtil.toInt(oloop)+1);
		}
		if (paras.length>0) {
			boolean loop = VarUtil.toBool(paras[0]);
			if (!loop) {
				return true;
			} else {
				if (context instanceof IActivityContext) {
					JSONObject activity = ((IActivityContext)context).getCurrentActivity();
					if (activity==null)
						return true;
//						throw new Warning("当前的活动对象丢失");
					return ((IActivityContext)context).evalRule(activity);
				} else if (context instanceof IRuleContext) {
					JSONObject activity = ((IRuleContext)context).getCurrentActivity();
//					JSONObject rule = ((IRuleContext)context).getCurrentRule();
					if (activity==null)
						return true;
//						throw new Warning("当前的活动对象丢失");
//						throw new Warning("当前的规则对象丢失");
					return ((IRuleContext)context).getParent().evalRule(activity);
				}
			}
		}
		return true;
	}
	@IFunction(name="NoLoop")
	public static Object noLoop(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		IContext curContext = context;
		if (curContext instanceof IRuleContext) {
			curContext = ((IRuleContext)curContext).getParent();
		}
		Object oloop = curContext.getAttribute("__loop");
		if (oloop==null) {
			return true;
		} else {
			if (paras.length>0) {
				int max = VarUtil.toInt(paras[0]);
				boolean canloop = VarUtil.toInt(oloop)<=max-1;
				if (!canloop) {
					if (curContext instanceof IActivityContext) {
						((IActivityContext)curContext).removeCurrentActivity();
					} else if (curContext instanceof IRuleContext) {
						((IRuleContext)curContext).removeCurrentActivity();
					}
				}
				return canloop;
			}
			return VarUtil.toInt(oloop)<=0;
		}
	}

}
