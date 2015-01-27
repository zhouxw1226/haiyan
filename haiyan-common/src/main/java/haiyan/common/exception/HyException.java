/*
 * Created on 2006-8-24
 *
 */
package haiyan.common.exception;

import haiyan.common.DebugUtil;
import haiyan.common.HtmlUtil;
import haiyan.common.StringUtil;
import haiyan.common.SysCode;
import haiyan.common.intf.session.IContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONObject;
import bsh.TargetError;
 

/**
 * @author zhouxw
 * 
 */
public abstract class HyException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String detailMessage = null;
    /**
     * @param desc
     */
    HyException(String desc) {
        super(desc);
        setMessage(desc);
    }
    /**
     * @param errCode
     */
    HyException(SysCode cCode) {
        super(cCode.toString());
        setMessage(cCode.toString());
    }
    /**
     * @param ex
     */
    HyException(Throwable ex) {
       super(ex);
    }
    /**
     * @param ex
     * @param desc
     */
    HyException(Throwable ex, String desc) {
        super(desc, ex);
        setMessage(desc);
    }
    /**
     * @param ex
     * @param errCode
     */
    HyException(Throwable ex, SysCode cCode) {
        super(cCode.toString(), ex);
        setMessage(cCode.toString());
    }
    private void setMessage(String s) {
        this.detailMessage = s;
//        if (Math.random()*10>5) {
//            String productKey;
//            try {
//                productKey = new String(Check.getSequence());
//                this.desc+="(@copyright 本产品授权 "+productKey+" 使用)";
//            } catch (Throwable e) {
//                DebugUtil.error(e);
//            }
//        }
    }
//    @Override
//    public void printStackTrace(java.io.PrintStream ps) {
//        if (this.target != null)
//            getSourceEx(this.target).printStackTrace(ps);
//        else
//            super.printStackTrace(ps);
//    }
//
//    @Override
//    public void printStackTrace(java.io.PrintWriter ps) {
//        if (this.target != null)
//            getSourceEx(this.target).printStackTrace(ps);
//        else
//            super.printStackTrace(ps);
//    }
//
//    @Override
//    public void printStackTrace() {
//        printStackTrace(System.err);
//    }
    @Override
    public String getMessage() {
        if (StringUtil.isEmpty(this.detailMessage)) {
            Throwable ex = getCauseEx(this);
            if (ex == this) { 
                String mes = super.getMessage();
                if (mes == null)
                    mes = super.toString();
                this.detailMessage = mes;
            } else if (ex instanceof SQLException) { // NOTICE 不能暴露数据库错误信息
                SQLException sex = (SQLException) ex;
                String mes = null;
//                mes = GenSQLException.getMessage(sex);
//                if (mes == null)
                    mes = sex.toString();
                this.detailMessage = mes;
            } else {
                String mes = ex.getMessage();
                if (mes == null)
                    mes = ex.toString();
                this.detailMessage = mes;
            }
//            if (Math.random()*10>5) {
//                String productKey;
//                try {
//                    productKey = new String(Check.getSequence());
//                    this.desc+="(@copyright 本产品授权 "+productKey+" 使用)";
//                } catch (Throwable e) {
//                    DebugUtil.error(e);
//                }
//            }
        }
        // DebugUtil.debug(">GenException.desc:" + this.desc);
        return this.detailMessage;
    }
    /**
     * @param delimiter
     * @return String
     * @throws Throwable
     */
    public String getStackTraceStr(String delimiter) throws Throwable {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            PrintStream ps = new PrintStream(bos);
            printStackTrace(ps);
            return bos.toString().replaceAll("\n", delimiter);
        } 
//        catch (Throwable e) {
//            throw e;
//        } 
        finally {
            bos.close();
        }
    }
    /***************************************************************************
     * static methods
     **************************************************************************/
    /**
     * @param info
     * @return String
     */
    public final static String getClientInfo(String info) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("info", HtmlUtil.getJSonSafeStr(info));
        return json.toString();
    }
    /**
     * @param err
     * @return String
     */
    public final static String getClientErr(String err) {
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("error", HtmlUtil.getJSonSafeStr(err));
        return json.toString();
    }
    /**
     * @param context
     * @return String
     * @throws Throwable
     */
    public final static String getMessage(IContext context) throws Throwable {
        List<Throwable> exceptions = context.getExceptions();
        if (exceptions!=null && exceptions.size() > 0) { // 暂时取第一个
            return getCauseExMes(exceptions.get(0));
        }
        return null;
    }
    /**
     * @param ex
     * @return String
     */
    public final static String getCauseExMes(Throwable ex) {
        ex = getCauseEx(ex);
        String mes = ex.getMessage();
        if (mes == null)
            return ex.toString();
        return mes;
    }
    /**
     * @param ex
     * @return Throwable
     */
    public final static Throwable getCauseEx(Throwable ex) {
        Throwable ex1 = ex;
        if (ex1 instanceof SQLException) {
            DebugUtil.debug(">SQLException.errorcode:" + ((SQLException) ex).getErrorCode());
        } else if (ex1 instanceof InvocationTargetException) {
            ex1 = ((InvocationTargetException) ex1).getTargetException();
        } else if (ex1 instanceof TargetError) {
            ex1 = ((TargetError) ex1).getTarget();
        } else if (ex1 instanceof HyException)
            if (ex1.getCause() != null)
                ex1 = ex1.getCause();
        // TargetError 追溯前寻根(因被Warning封装)
        if (ex1 instanceof TargetError) {
            while (((TargetError) ex1).getTarget() != null) {
                ex1 = ((TargetError) ex1).getTarget();
                if (!(ex1 instanceof TargetError))
                    break;
            }
        }
        // Throwable 追溯
        while (ex1.getCause() != null) {
            ex1 = ex1.getCause();
        }
        // TargetError 追溯后寻根(因被Warning封装)
        if (ex1 instanceof TargetError) {
            while (((TargetError) ex1).getTarget() != null) {
                ex1 = ((TargetError) ex1).getTarget();
                if (!(ex1 instanceof TargetError))
                    break;
            }
        }
        return ex1;
    }
    /**
     * 带ajax版本的错误提示
     * 
     * @param context
     * @param ex
     * @return String
     */
    public final static String dealWithException(IContext context, Throwable ex) {
        try {
//            if (GeneralCore.isReturnMeta(context)) {
//                DebugUtil.error("dealWithException", ex);
//                String mes = getCauseExMes(ex);
//                GeneralCore.send2Client(context, mes, -1);
//                return null;
//            } else {
                // error.jsp有DebugUtil.error
                ex = getCauseEx(ex);
                context.setAttribute("warning", Warning.getWarning(ex));
                return "error";
//            }
        } catch (Throwable ignore) {
        	ignore.printStackTrace();
            return null;
        }
    }
}
