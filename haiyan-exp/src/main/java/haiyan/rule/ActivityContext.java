package haiyan.rule;

import java.util.Date;

import haiyan.common.CloseUtil;
import haiyan.common.DateUtil;
import haiyan.common.DebugUtil;
import haiyan.common.VarUtil;
import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.exp.ExpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author ZhouXW
 *
 */
public class ActivityContext extends AppContext implements IActivityContext {
	public static final String ACTIVITY_TAG_CODE = "code";
	public static final String ACTIVITY_TAG_ACTIVE = "active";
	public static final String ACTIVITY_TAG_TYPE = "type";
	public static final String ACTIVITY_VAR_TYPE = "__type";
	public static final String ACTIVITY_TAG_TIMERANGE = "time";
	public static final String ACTIVITY_TAG_DISPTIMERANGE = "dispTime";
	public static final String ACTIVITY_TAG_RULES = "rules";
	public static final String ACTIVITY_TAG_RULEDATAS = "ruleDatas";
	public static final String ACTIVITY_TAG_DATAS = "datas";
	public static final String ACTIVITY_TAG_PRECONDITION = "precondition";
	public ActivityContext(IContext parent, JSONObject activity) {
		super(parent);
		this.activity = activity;
		this.setExpUtil(new ExpUtil(this));
	}
	private JSONObject activity;
	@Override
	public Object evaluate() throws Throwable {
		String code = activity.getString(ACTIVITY_TAG_CODE);
		int active = activity.getInt(ACTIVITY_TAG_ACTIVE);
		if (active!=1) {
			DebugUtil.debug("活动编号："+ACTIVITY_TAG_CODE+"="+code);
			DebugUtil.debug("活动无效："+ACTIVITY_TAG_ACTIVE+"="+active);
			return false;
		}
//		String __type = (String)this.getAttribute(ACTIVITY_VAR_TYPE);
//		String type = activity.getString(ACTIVITY_TAG_TYPE);
//		if (!__type.equalsIgnoreCase(type)) {
//			DebugUtil.debug("活动编号："+ACTIVITY_TAG_CODE+"="+code);
//			DebugUtil.debug("活动无效："+ACTIVITY_TAG_TYPE+"="+type+","+ACTIVITY_VAR_TYPE+"="+__type);
//			return false;
//		}
		JSONArray timeRange = activity.getJSONArray(ACTIVITY_TAG_TIMERANGE);
		String startStr = timeRange.getString(0);
		String endStr = timeRange.getString(1);
		Date start = DateUtil.getDate(startStr);
		Date end = DateUtil.getDate(endStr);
//		Date now = DateUtil.getDate(DateUtil.getLastTime("yyyy-MM-dd HH:mm:ss"));
//		if (now.before(end) && now.after(start)) {
			DebugUtil.debug("活动编号："+ACTIVITY_TAG_CODE+"="+code);
//			DebugUtil.debug("活动有效："+ACTIVITY_TAG_TIMERANGE+"=["+start+","+end+","+now+"]");
			//String userRules = activity.getString("userRules"); 
			//DebugUtil.debug("活动对象规则："+userRules);
			JSONArray rules = activity.getJSONArray(ACTIVITY_TAG_RULES);
			for (int i=0;i<rules.size();i++) {
				JSONObject rule = rules.getJSONObject(i);
				IRuleContext context = null;
				try {
					context = new RuleContext(this, rule);
					String codeRule = rule.getString(ACTIVITY_TAG_CODE);
					DebugUtil.debug("活动规则：codeRule="+codeRule);
					Object o = context.evaluate();
					if (!VarUtil.toBool(o)) {
						DebugUtil.debug("活动规则跳转或终止：codeRule="+codeRule);
						return false;
					}
				}finally{
					CloseUtil.close(context);
				}
			}
			return true;
//		} else {
//			DebugUtil.debug("活动编号："+ACTIVITY_TAG_CODE+"="+code);
//			DebugUtil.debug("活动无效："+ACTIVITY_TAG_TIMERANGE+"=["+start+","+end+","+now+"]");
//			return false;
//		}
	}
	@Override
	public JSONObject getActivity() {
		return activity;
	}
	@Override
	public void setActivity(JSONObject activity) {
		this.activity = activity;
	}
	@Override
	public void removeActivity() {
		this.activity = null;
	}

}
