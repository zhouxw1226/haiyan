package haiyan.common.session;

import haiyan.common.DebugUtil;
import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public abstract class SessionMap<K, V> extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	protected static long checkTime = 180000L;
	protected static long overTime = 900000L;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static final transient ArrayList<SessionMap> mapManager = new ArrayList();
	protected final transient Hashtable<String, Long> sessions = new Hashtable<String, Long>();
	static {
		try {
			String sCheckTime = null;
			try {
				sCheckTime = PropUtil.getProperty("server.checkTime");
			} catch (Throwable localThrowable1) {
			}
			if (!StringUtil.isBlankOrNull(sCheckTime)) {
				checkTime = Double.valueOf(sCheckTime).intValue() * 1000;
			}
			String sOverTime = null;
			try {
				sOverTime = PropUtil.getProperty("server.overTime");
			} catch (Throwable localThrowable2) {
			}
			if (!StringUtil.isBlankOrNull(sOverTime)) {
				overTime = Double.valueOf(sOverTime).intValue() * 1000;
			}
		} catch (Throwable ex) {
			DebugUtil.error(ex);
		}
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(SessionMap.checkTime);
					} catch (Throwable ignore) {
						ignore.printStackTrace();
					}
					try {
						synchronized (SessionMap.mapManager) {
							for (SessionMap<?, ?> map : SessionMap.mapManager)
								map.closeOvertime();
						}
					} catch (Throwable e) {
						DebugUtil.error(e);
					}
				}
			}
		}.start();
	}
	public SessionMap() {
		mapManager.add(this);
	}
	@Override
	public Object get(Object key) {
		Object v = super.get(key);
		this.sessions.put(VarUtil.toString(key),
				Long.valueOf(System.currentTimeMillis()));
		return v;
	}
	@Override
	public Object put(String key, Object value) {
		synchronized (this) {
			Object v = super.put(key, value);
			this.sessions.put(VarUtil.toString(key),
					Long.valueOf(System.currentTimeMillis()));
			return v;
		}
	}
	@Override
	public Object remove(Object key) {
		synchronized (this) {
			Object v = super.remove(key);
			this.sessions.remove(VarUtil.toString(key));
			return v;
		}
	}
	@Override
	public void clear() {
		synchronized (this) {
			super.clear();
			this.sessions.clear();
		}
	}
	protected abstract boolean isOverTime(String paramString);
	protected void closeOvertime() {
		List<String> list = new ArrayList<String>();
		Iterator<String> iter = this.sessions.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (isOverTime(key)) {
				// iter.remove();
				// remove(key);
				list.add(key);
			}
		}
		synchronized (this) {
			for (String key : list) {
				this.remove(key);
			}
		}
	}
}