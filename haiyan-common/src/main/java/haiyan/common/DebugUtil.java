package haiyan.common;

import haiyan.common.intf.ILogger;

import java.lang.reflect.Method;
 
public class DebugUtil {
	// 默认的logger实现
	public static ILogger logger = new ILogger() {
		@Override
		public void debug(Object info) {
			System.out.println(info);
		}
		@Override
		public void info(Object info) {
			System.out.println(info);
		}
		@Override
		public void warn(Object info) {
			System.out.println(info);
		}
		@Override
		public void error(Object info) {
			if (info instanceof Throwable) 
				((Throwable)info).printStackTrace();
			else
				System.err.println(info);
		}
		@Override
		public void error(Object info, Throwable ex) {
			ex.printStackTrace();
			System.err.println(info);
		}
	};
	private static int connNum = 0;
	public static synchronized void addConnNum() {
		connNum++;
		debug(">------------------------------------------< connNum=" + connNum);
	}
	public static synchronized void deleteConnNum() {
		connNum--;
		debug(">------------------------------------------< connNum=" + connNum);
	}
	/**
	 * @param info
	 */
	public static void debug(Object info) {
		if (logger != null)
			logger.debug(info); // haiyan.exe没有记录。。。
	}
	/**
	 * @param info
	 * @param ex
	 */
	public static void error(Object info, Throwable ex) {
		if (logger != null)
			logger.error(info, ex);
	}
	/**
	 * @param obj
	 */
	public static void error(Object info) {
		if (logger != null)
			logger.error(info);
	}
	public static void warn(Object info) {
		if (logger != null)
			logger.warn(info);
	}
	/**
	 * @param obj
	 */
	public static void asert(boolean cond) {
		if (cond)
			System.err.print("!!断言");
	}
	/**
	 * @param obj
	 * @return String
	 */
	private static String seePropertyValue(Object obj) {
		String className = obj.getClass().getName();
		String result = "\r\n\t"
				+ className.substring(className.lastIndexOf('.') + 1, className
						.length()) + " \r\n\t";
		Method m[] = obj.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			try {
				String name = m[i].getName();
				String param = "";
				if (name.startsWith("get")
						&& m[i].getParameterTypes().length == 0
						&& !name.equals("getClass")) {
					param = name.substring(3, name.length());
					result += "[" + param + ":" + m[i].invoke(obj) + "] ";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return seePropertyValue(this.getClass());
	}

}
