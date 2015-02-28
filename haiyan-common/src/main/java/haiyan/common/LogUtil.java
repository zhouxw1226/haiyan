/*
 * Created on 2007-3-22
 *
 */
package haiyan.common;

import org.apache.log4j.Logger;

/**
 * @author zhouxw
 * 
 */
public class LogUtil {
 
	private static Logger logger = Logger.getLogger(LogUtil.class);
 
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
			logger.error(obj, ex);
	}

	/**
	 * @param obj
	 */
	public static void error(Object obj) {
		if (loggerType > 0) {
			if (obj instanceof Throwable)
				logger.error("LogUtil.ERROR", (Throwable)obj);
			else
				logger.error(obj);
		}
	}

	/**
	 * @param obj
	 */
	public static void warn(Object obj) {
		if (loggerType > 0)
			if (obj instanceof Throwable)
				logger.warn("LogUtil.ERROR", (Throwable)obj);
			else
				logger.warn(obj);
	}

	/**
	 * @param obj
	 */
	public static void info(Object obj) {
		if (loggerType > 0)
			if (obj instanceof Throwable)
				logger.info("LogUtil.ERROR", (Throwable)obj);
			else
				logger.info(obj);
	}
 

}