/*
 * Created on 2007-4-16
 *
 */
package haiyan.common;

import haiyan.common.config.DataConstant;
import haiyan.common.config.Language;
import haiyan.common.intf.session.IUser;

/**
 * @author zhouxw
 * 
 */ 
public class SysCode {
	public static final class SysCodeNum {
		public static final int ERROR_1001 = 10001;
		public static final int ERROR_1002 = 10002;
		public static final int NO_MATCHEDDSN = 20001; 
		public static final int NO_MATCHEDSQLDATABASE = 20002; 
		public static final int NO_MATCHEDDBMANAGER = 20003; 
		public static final int NOT_INIT_RESULTSETS = 30001; 
		public static final int NOT_INIT_FILTERS = 30002;
	}
	public static final class SysCodeMessage {
		public static final String ERROR_1001 = "没有对ConfigUtil注入配置用IDataCache";
		public static final String ERROR_1002 = "没有对ConfigUtil注入配置用IExpUtil";
		public static final String NO_MATCHEDDSN = "没有匹配的DSN选项"; 
		public static final String NO_MATCHEDSQLDATABASE = "没有匹配的ISQLDatabase选项";
		public static final String NO_MATCHEDDBMANAGER = "没有匹配的IDBManager选项";
		public static final String NOT_INIT_RESULTSETS = "IDBResultSet没有初始化"; 
		public static final String NOT_INIT_FILTERS = "IDBFilter没有初始化"; 
	}
	private int code = -1;
	private String source = null;
	private String detailMessage = null;
	/**
	 * @param code
	 * @param source
	 * @param detailMessage
	 */
	public SysCode(IUser user, int code, String source) {
		super();
		this.code = code;
		this.source = source;
		this.detailMessage = getConfigDes(user, source, null);
	}
	/**
	 * @param code
	 * @param source
	 */
	public SysCode(IUser user, int code, String source, String[] values) {
		super();
		this.code = code;
		this.source = source;
		this.detailMessage = getConfigDes(user, source, values);
	}
	/**
	 * @return int
	 */
	public int getCode() {
		return this.code;
	}
	/**
	 * @return String
	 */
	public String getSource() {
		return this.source;
	}
	@Override
	public String toString() {
		return this.detailMessage;
	}
	/**
	 * @param user
	 * @param source
	 * @param values
	 * @return
	 */
	public final String getConfigDes(IUser user, String source, String[] values) {
		String languageName = null;
		String label = null;
		try {
			languageName = Language.getLanguage(user).getName(); // ConfigUtil.getLanguage(user).getName();
			label = DataConstant.CONST_PROMPT_NAME + "." + source;
			String configMessage = PropUtil.getProperty(languageName, label, null);
			// DebugUtil.debug(">" + label + "=" + configCode);
			if (!StringUtil.isBlankOrNull(configMessage)) {
				if (values != null)
					for (int j = 0; j < values.length; j++) {
						int index = configMessage.indexOf("{" + j + "}");
						int indexLen = ("{" + j + "}").length();
						String tempErr = "";
						if (index != -1) {
							tempErr = configMessage.substring(0, index);
							tempErr += values[j];
							tempErr += configMessage.substring(index + indexLen,
									configMessage.length());
						} else {
							tempErr += configMessage;
						}
						configMessage = tempErr;
					}
			} else {
				configMessage = source;
			}
			this.detailMessage = configMessage;
			return configMessage;
		} catch (Throwable ignore) {
			ignore.printStackTrace();
			this.detailMessage = source;
			return source;
		}
	}
	public static SysCode create(IUser user, int errorCode, String source, String[] paras) {
		return new SysCode(user, errorCode, source, paras);
	}
	public static SysCode create(IUser user, int errorCode, String source) {
		return new SysCode(user, errorCode, source);
	}
}