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
		@Override
		public void debug(Object info) {
			System.out.println(info);
		}
		@Override
		public void warn(Object info) {
			System.err.println(info);
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
			System.err.println(info);
			ex.printStackTrace();
		}
	}
}
