package haiyan.rule;

import haiyan.common.CloseUtil;
import haiyan.common.DateUtil;
import haiyan.common.DebugUtil;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.exp.ExpUtil;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ActivityContext extends AppContext implements IActivityContext {

	public ActivityContext() {
		super();
	}
	public ActivityContext(IContext parent) {
		super(parent);
	}
	@Override
	public Object evalRule(JSONObject activity) throws Throwable {
		IRuleContext context = null;
		try {
			context = new RuleContext(this);
			this.setAttribute("__activity", activity);
			IExpUtil expUtil = new ExpUtil(context);
			context.setExpUtil(expUtil);
			
			String code = activity.getString("code");
			JSONArray timeRange = activity.getJSONArray("time");
			String startStr = timeRange.getString(0);
			String endStr = timeRange.getString(1);
			Date start = DateUtil.getDate(startStr);
			Date end = DateUtil.getDate(endStr);
			Date curr = DateUtil.getDate(DateUtil.getLastTime("yyyy-MM-dd HH:mm:ss"));
			if (curr.before(end) && curr.after(start)) {
				DebugUtil.debug("活动编号：\n"+code+"\n");
				DebugUtil.debug("活动有效：\n"+start+"\n"+end+"\n"+curr+"\n");
				String userStr = activity.getString("users"); 
				DebugUtil.debug("活动对象：\n"+userStr+"\n");
				JSONArray rules = activity.getJSONArray("rules");
				for (int i=0;i<rules.size();i++) {
					JSONObject rule = rules.getJSONObject(i);
					context.setAttribute("__rule", rule);
					context.evalRule(rule);
				}
				return true;
			} else {
				DebugUtil.debug("活动编号：\n"+code+"\n");
				DebugUtil.debug("活动无效：\n"+start+"\n"+end+"\n"+curr+"\n");
				return false;
			}
		}finally{
			CloseUtil.close(context);
		}
	}
	@Override
	public JSONObject removeCurrentActivity() {
		JSONObject json = (JSONObject)this.getAttribute("__activity");
		this.removeAttribute("__activity");
		return json;
	}
	@Override
	public JSONObject getCurrentActivity() {
		return (JSONObject)this.getAttribute("__activity");
	}

}
