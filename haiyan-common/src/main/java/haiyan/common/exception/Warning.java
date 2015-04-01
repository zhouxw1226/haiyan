package haiyan.common.exception;

import haiyan.common.SysCode;
import haiyan.common.intf.session.IUser;
 

/**
 * 
 * @author zhouxw
 */
public class Warning extends HyException {

    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String debug;
    public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getDebug() {
		return debug;
	}
	public void setDebug(String debug) {
		this.debug = debug;
	}
	/**
     * @param errCode
     */
    public Warning(SysCode errCode) {
        super(errCode);
        this.errorCode = errCode.getCode();
    }
    /**
     * @param errCode
     */
    public Warning(int errorCode, Throwable e) {
        super(SysCode.create(null, errorCode, e.getMessage(), null));
        this.errorCode = errorCode;
//        this.exception = e;
    }
    /**
     * @param errCode
     */
    public Warning(int errorCode, Throwable e, String[] paras) {
        super(SysCode.create(null, errorCode, e.getMessage(), paras));
        this.errorCode = errorCode;
//        this.exception = e;
    }
    /**
     * @param errCode
     */
    public Warning(int errorCode, String source) {
        super(SysCode.create(null, errorCode, source, null));
        this.errorCode = errorCode;
//        this.source = source;
    }
    /**
     * @param errCode
     */
    public Warning(int errorCode, String source, String debug) {
        super(SysCode.create(null, errorCode, source, null));
        this.errorCode = errorCode;
//        this.source = source;
    }
    /**
     * @param errCode
     */
    public Warning(int errorCode, String source, String[] paras) {
        super(SysCode.create(null, errorCode, source, paras));
        this.errorCode = errorCode;
//        this.source = source;
    }
    /**
     * @param errCode
     */
    public Warning(IUser user, int errorCode, String source, String[] paras) {
        super(SysCode.create(user, errorCode, source, paras));
        this.errorCode = errorCode;
//        this.source = source;
    }
    /**
     * @param errCode
     */
    public Warning(IUser user, int errorCode, String source) {
        super(SysCode.create(user, errorCode, source, null));
        this.errorCode = errorCode;
//        this.source = source;
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