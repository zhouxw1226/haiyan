package haiyan.common.exception;

import haiyan.common.DebugUtil;
import haiyan.common.SysCode;
import haiyan.common.intf.session.IUser;
 
/**
 * 
 * @author zhouxw
 */
public class Warning extends HyException {

    private static final long serialVersionUID = 1L;
    private int errorCode;
    private int statusCode;
    private String debug;
    public int getErrCode() {
		return errorCode;
	}
	public void setErrCode(int errorCode) {
		this.errorCode = errorCode;
	}
    public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getDebug() {
		return debug;
	}
	public void setDebug(String debug) {
		this.debug = debug;
	}
    // ================================================================= //
    /**
     * @param statusCode
     */
    public Warning(int statusCode, int errorCode, Throwable e) {
        super(SysCode.create(null, statusCode, e.getMessage(), null));
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, int errorCode, Throwable e, String[] paras) {
        super(SysCode.create(null, statusCode, e.getMessage(), paras));
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, int errorCode, String source) {
        super(SysCode.create(null, statusCode, source, null));
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, int errorCode, String source, String debug) {
        super(SysCode.create(null, statusCode, source, null));
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        DebugUtil.error(debug);
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, int errorCode, String source, String[] paras) {
        super(SysCode.create(null, statusCode, source, paras));
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
    // ================================================================= //
    /**
     * @param statusCode
     */
    public Warning(int statusCode, Throwable e) {
        super(SysCode.create(null, statusCode, e.getMessage(), null));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, Throwable e, String[] paras) {
        super(SysCode.create(null, statusCode, e.getMessage(), paras));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, String source) {
        super(SysCode.create(null, statusCode, source, null));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, String source, String debug) {
        super(SysCode.create(null, statusCode, source, null));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
        DebugUtil.error(debug);
    }
    /**
     * @param errCode
     */
    public Warning(int statusCode, String source, String[] paras) {
        super(SysCode.create(null, statusCode, source, paras));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
    }
    // ================================================================= //
    /**
     * @param errCode
     */
    public Warning(IUser user, int statusCode, String source, String[] paras) {
        super(SysCode.create(user, statusCode, source, paras));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
    }
    /**
     * @param errCode
     */
    public Warning(IUser user, int statusCode, String source) {
        super(SysCode.create(user, statusCode, source, null));
        this.statusCode = statusCode;
        this.errorCode = statusCode;
    }
    // ================================================================= //
	/**
     * @param sysCode
     */
    public Warning(SysCode sysCode) {
        super(sysCode);
        this.statusCode = sysCode.getCode();
        this.errorCode = statusCode;
    }
    /**
     * @param desc
     */
    public Warning(String desc) {
        super(desc);
    }
    /**
     * ex wrap
     * 
     * @param ex
     */
    public Warning(Throwable ex) {
        super(ex);
    }
    /**
     * @param ex
     * @param errCode
     */
    public Warning(Throwable ex, SysCode errCode) {
        super(ex, errCode);
    }
    /**
     * @param ex
     * @param source
     */
    public Warning(Throwable ex, String source) {
        super(ex, source);
    }
    // ================================================================= //
    /**
     * @param ex
     * @return Warning
     */
    public static Warning getWarning(Throwable ex) {
        Warning warn;
        if (ex instanceof Warning) {
            warn = (Warning) ex;
        } else {
            warn = new Warning(ex);
        }
        return warn;
    }
    /**
     * @param ex
     * @return
     */
    public static Warning wrapWarning(Warning ex) {
    	return getWarning(ex);
    }
    /**
     * @param ex
     * @return
     */
    public static Warning wrapException(Throwable ex) {
    	return getWarning(ex);
    }
    /**
     * @param e
     * @return String
     */
    public static String getMessage(Throwable e) {
        return getWarning(e).getMessage();
    }
    
}