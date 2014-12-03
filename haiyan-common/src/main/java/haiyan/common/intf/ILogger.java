package haiyan.common.intf;

/**
 * @author ZhouXW
 * 
 */
public interface ILogger {

	public void debug(Object info);

	public void error(Object info);

	public void error(Object info, Throwable ex);

	public void warn(Object info);

	/**
	 * @author zhouxw
	 * @deprecated
	 * 
	 */
	public static class ILoggerAdapter implements ILogger {

		public void debug(Object info) {
			System.out.println(info);
		}

		public void error(Object info) {
			System.err.println(info);
		}

		public void error(Object info, Throwable ex) {
			System.err.println(info);
			ex.printStackTrace();
		}

		public void warn(Object info) {
			System.err.println(info);
		}

	}
}
