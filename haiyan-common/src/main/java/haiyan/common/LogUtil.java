/*
 * Created on 2007-3-22
 */
package haiyan.common;

import org.apache.log4j.Logger;
 
/**
 * @author zhouxw
 * 
 */
public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);
	public static Logger getLogger() {
//		Throwable t = new Throwable();
//		StackTraceElement directCaller = t.getStackTrace()[1];
//		return Logger.getLogger(directCaller.getClassName());
		return logger;
	}
//	public static Logger getLogger() {
//        Throwable t  = new Throwable();
//        StackTraceElement directCaller = t.getStackTrace()[1];
//        return LoggerFactory.getLogger(directCaller.getClassName());
//    }
	public static final int NONE = -1;
	public static final int DB = 0;
	public static final int FILE = 1;
	public static final int ALL = 2;
	public static int loggerType = 1; 
	/**
	 * @param obj
	 * @param ex
	 */
	public static void error(Object obj, Throwable ex) {
		if (loggerType > 0)
			getLogger().error(obj==null?null:obj.toString(), ex);
	}
	/**
	 * @param obj
	 */
	public static void error(Object obj) {
		if (loggerType > 0) {
			if (obj instanceof Throwable)
				getLogger().error("LogUtil.ERROR", (Throwable)obj);
			else
				getLogger().error(obj==null?null:obj.toString());
		}
	}
	/**
	 * @param obj
	 */
	public static void warn(Object obj) {
		if (loggerType > 0)
			if (obj instanceof Throwable)
				getLogger().warn("LogUtil.ERROR", (Throwable)obj);
			else
				getLogger().warn(obj==null?null:obj.toString());
	}
	/**
	 * @param obj
	 */
	public static void info(Object obj) {
		if (loggerType > 0)
			if (obj instanceof Throwable)
				getLogger().info("LogUtil.ERROR", (Throwable)obj);
			else
				getLogger().info(obj==null?null:obj.toString());
	}

}