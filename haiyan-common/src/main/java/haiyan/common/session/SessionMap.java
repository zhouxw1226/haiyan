package haiyan.common.session;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * TODO 最好继承ECacheMap
 * 
 * @author zhouxw
 */
public abstract class SessionMap<K,V> extends TreeMap<String, Object> {

	private static final long serialVersionUID = 1L;

	protected static long checkTime = 1000 * 60 * 3; // ms 轮询时间 3min

	protected static long overTime = 1000 * 60 * 15; // ms 超时时间 15min

	// SessionMap管理列表
	@SuppressWarnings("rawtypes")
	protected static transient final ArrayList<SessionMap> mapManager = new ArrayList<SessionMap>();

	// 每个SessionMap有自己的sessions,记录最近访问时间
	protected transient final Hashtable<String, Long> sessions = new Hashtable<String, Long>();

	static {
		try {
			ResourceBundle core = PropertyResourceBundle.getBundle("core");
			String sCheckTime = null;
			try {
				sCheckTime = core.getString("server.checkTime");
			} catch (Throwable e) {
			}
			if (!StringUtil.isBlankOrNull(sCheckTime)) { // s
				checkTime = Double.valueOf(sCheckTime).intValue() * 1000;
			}
			String sOverTime = null;
			try {
				sOverTime = core.getString("server.overTime");
			} catch (Throwable e) {
			}
			if (!StringUtil.isBlankOrNull(sOverTime)) { // s
				overTime = Double.valueOf(sOverTime).intValue() * 1000;
			}
		} catch (Throwable ex) {
			DebugUtil.error(ex);
		}
		// map gc
		new java.lang.Thread() {

			@SuppressWarnings("rawtypes")
			public void run() {
				while (true) {
					try {
						java.lang.Thread.sleep(checkTime);
					} catch (Throwable e) {
						e.printStackTrace(); // ignore
					}
					try {
						synchronized (mapManager) {
							for (SessionMap map : mapManager) {
								map.closeOvertime();
							}
						}
					} catch (Throwable e) {
						DebugUtil.error(e);
					}
				}
			}

		}.start();
	}
	public SessionMap() {
		// synchronized (mapManager) {
		mapManager.add(this);
		// }
	}
	public Object put(String key, Object value) {
		Object v = super.put(key, value);
		// synchronized (sessions) {
		sessions.put(VarUtil.toString(key), System.currentTimeMillis());
		// }
		return v;
	}
	@Override
	public Object get(Object key) {
		Object v = super.get(key);
		// synchronized (sessions) {
		sessions.put(VarUtil.toString(key), System.currentTimeMillis());
		// }
		return v;
	}
	@Override
	public Object remove(Object key) {
		Object v = super.remove(key);
		// synchronized (sessions) {
		sessions.remove(VarUtil.toString(key));
		// }
		return v;
	}
	@Override
	public void clear() {
		super.clear();
		// synchronized (sessions) {
		sessions.clear();
		// }
	}
	protected abstract boolean isOverTime(String key);
	// {
	// return System.currentTimeMillis() - sessions.get(key) > overTime;
	// }
	protected void closeOvertime() {
		synchronized (this) {
			// 产生key的新迭代集合
			Iterator<String> iter = sessions.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (isOverTime(key)) { // 该变量超时
					iter.remove(); // 迭代里删除
					remove(key); // 本体删除
				}
			}
		}
	}
}