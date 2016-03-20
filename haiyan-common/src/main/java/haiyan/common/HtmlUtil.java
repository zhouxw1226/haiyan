/*
 * Created on 2004-9-30
 */
package haiyan.common;

import haiyan.common.intf.session.IContext;

/**
 * @author zhouxw
 */
public class HtmlUtil {

	private HtmlUtil() {
	}
	/**
	 * @param context
	 */
	public final static void setNoPageCache(IContext context) {
//		context.setHeader("Cache-Control", "no-cache");
//		// Forces caches to obtain a new copy of the page from the origin server
//		context.setHeader("Cache-Control", "no-store");
//		// Directs caches not to store the page under any circumstance
//		context.setDateHeader("Expires", 0);
//		// Causes the proxy cache to see the page as "stale"
//		context.setHeader("Pragma", "no-cache");
//		// HTTP 1.0 backward compatibility
	}
	/**
	 * @param str
	 * @return String
	 */
	public static String getReportSafeStr(String str) {
		String result = str;
		if (result != null) {
			result = StringUtil.replaceAll(result, "\\", "|");//
			result = StringUtil.replaceAll(result, "/", "|");//
		} else
			result = "";
		return result;
	}
	/**
	 * @param action
	 * @return String
	 */
	public static String getActionSafeStr(String str) {
//		return action == null ? "" : getJSonSafeStr(action);
		String result = str;
		if (result != null) {
			// result = result.replaceAll("\\t", "");//不能转用户数据
			// result = result.replaceAll("\\n", "");//
			result = result.replaceAll("\\\\", "\\\\" + "\\\\");//
			result = result.replaceAll("\"", "\\\\" + "\"");//
			result = result.replaceAll("'", "\\\\" + "'");//
		} else
			result = "";
		return result;
	}
	/**
	 * @param str
	 * @return String
	 */
	public static String getJSonSafeStr(String str) {
		return quote(str);
//		String result = str;
//		if (result != null) {
//			// result = result.replaceAll("\\t", "");//不能转用户数据
//			// result = result.replaceAll("\\n", "");//
//			result = result.replaceAll("\\\\", "\\\\" + "\\\\");//
//			result = result.replaceAll("\"", "\\\\" + "\"");//
//			result = result.replaceAll("'", "\\\\" + "'");//
//		} else
//			result = "";
//		return result;
	}
	// JSONUtils.quote
	public static String quote(String string)
	 /*     */   {
//	 /* 471 */     if (isFunction(string)) {
//	 /* 472 */       return string;
//	 /*     */     }
	 /* 474 */     if ((string == null) || (string.length() == 0)) {
//	 /* 475 */       return "\"\"";
		 				return "";
	 /*     */     }
	 /*     */ 
	 /* 479 */     char c = '\0';
	 /*     */ 
	 /* 481 */     int len = string.length();
	 /* 482 */     StringBufferUtil sb = new StringBufferUtil(len + 4);
	 /*     */ 
//	 /* 485 */     sb.append('"');
	 /* 486 */     for (int i = 0; i < len; ++i) {
	 /* 487 */       char b = c;
	 /* 488 */       c = string.charAt(i);
	 /* 489 */       switch (c)
	 /*     */       {
	 /*     */       case '"':
	 /*     */       case '\\':
	 /* 492 */         sb.append('\\');
	 /* 493 */         sb.append(c);
	 /* 494 */         break;
	 /*     */       case '/':
	 /* 496 */         if (b == '<') {
	 /* 497 */           sb.append('\\');
	 /*     */         }
	 /* 499 */         sb.append(c);
	 /* 500 */         break;
	 /*     */       case '\b':
	 /* 502 */         sb.append("\\b");
	 /* 503 */         break;
	 /*     */       case '\t':
	 /* 505 */         sb.append("\\t");
	 /* 506 */         break;
	 /*     */       case '\n':
	 /* 508 */         sb.append("\\n");
	 /* 509 */         break;
	 /*     */       case '\f':
	 /* 511 */         sb.append("\\f");
	 /* 512 */         break;
	 /*     */       case '\r':
	 /* 514 */         sb.append("\\r");
	 /* 515 */         break;
	 /*     */       default:
	 /* 517 */         if (c < ' ') {
	 /* 518 */           String t = "000" + Integer.toHexString(c);
	 /* 519 */           sb.append("\\u").append(t.substring(t.length() - 4));
	 /*     */         }
	 /*     */         else {
	 /* 522 */           sb.append(c);
	 /*     */         }
	 /*     */       }
	 /*     */     }
//	 /* 526 */     sb.append('"');
	 /* 527 */     return sb.toString();
	 /*     */   }
//	/**
//	 * @param str
//	 * @return String
//	 */
//	public static String getJSonUnSafeStr(String str) {
//		String result = str;
//		if (result != null) {
//			result = result.replaceAll("\\\\" + "'", "'");//
//			result = result.replaceAll("\\\\" + "\"", "\"");//
//			result = result.replaceAll("\\\\" + "\\\\", "\\\\");//
//		} else
//			result = "";
//		return result;
//	}
	/**
	 * @param str
	 * @return String
	 */
	public static String getJsSafeStr(String str) {
		String result = str;
		if (result != null) {
			result = result.replaceAll("\\\\", "\\\\" + "\\\\");//
			result = result.replaceAll("\"", "\\\\" + "\"");//
		} else
			result = "";
		return result;
	}
	/**
	 * @param str
	 * @return String
	 */
	public static String getJsUnSafeStr(String str) {
		String result = str;
		if (result != null) {
			result = result.replaceAll("\\\\" + "\\\\", "\\\\");//
			result = result.replaceAll("\\\\" + "\"", "\"");//
		} else
			result = "";
		return result;
	}
	/**
	 * return safe html
	 * 
	 * @param str
	 * @return String
	 */
	public static String getHtmlSafeStr(String str) {
		String result = str;
		if (result != null) {
			result = result.replaceAll("\n", "");
			result = result.replaceAll("\"", "&quot;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
		} else
			result = "";
		return result;
	}
	/**
	 * @param str
	 * @return String
	 */
	public static String getHtmlUnSafeStr(String str) {
		String result = str;
		if (str != null) {
			// NOTICE 20081103 zhouxw 是否要转这个符号?
			result = result.replaceAll("&amp;", "&");
			result = result.replaceAll("&quot;", "\"");
			result = result.replaceAll("&lt;", "<");
			result = result.replaceAll("&gt;", ">");
		} else
			result = "";
		return result;
	}
//	/**
//	 * @param context
//	 * @param name
//	 * @return String
//	 */
//	public static String getHiddenStrForParamValue(IContext context,
//			String name) {
//		String str = (String)context.getAttribute(name);
//		// DebugUtil.debug("+++++++++++" + name + " " + str);
//		HyStringBuffer buf = new HyStringBuffer();
//		// if (str != null) {
//		str = StringUtil.isStrBlankOrNull(str) ? "" : str;
//		buf.append("<input type='hidden' id='").append(name).append("' name='")
//				.append(name).append("' value='").append(str).appendln("'/>");
//		// }
//		return buf.toString();
//	}
//	/**
//	 * @param context
//	 * @param name
//	 * @return String
//	 */
//	public static String getHiddenStrForParamValues(IContext context,
//			String name) {
//		String[] str = context.getParameterValues(name);
//		// DebugUtil.debug("+++++++++++" + name + " " + str);
//		StringBuffer buf = new StringBuffer();
//		if (str != null) {
//			if (name.equals(DataConstant.SELECTED_ID)) {
//				Table table = context.getParaTable(); // ConfigUtil.getTable(context.getParameter(DataConstant.OPERATION_DATA_PARAM));
//				String result = "";
//				int count = 0;
//				for (int i = 0; i < str.length; i++) {
//					if (!StringUtil.isTrimBlankOrNull(str[i])) {
//						if (count != 0)
//							result += ",";
//						result += str[i];
//						count++;
//					}
//				}
//				if ((table.getOperation() != null && table.getOperation().getSelectedHidden())
//					|| (context.getParameter(DataConstant.EXTEND_OPERATION_CODE_PARAM_CHOOSE_MODE) != null 
//					&& context.getParameter(DataConstant.EXTEND_OPERATION_CODE_PARAM_CHOOSE_MODE).equals(DataConstant.CHOOSE_MODE_SINGLE))) {
//					buf.append("<input type='hidden' id='").append(name)
//							.append("' name='").append(name)
//							.append("' value='").append(result).appendln("'/>");
//				} else { // 2005-12-02 BUG zhouxw
//					buf.append("<input type='hidden' id='").append(name)
//						.append("' name='").append(name)
//						.append("' value='").append("").appendln("'/>");
//				}
//			} else {
//				for (int i = 0; i < str.length; i++) {
//					buf.append("<input type='hidden' id='").append(name)
//						.append("' name='").append(name)
//						.append("' value='").append(str[i]).appendln("'/>");
//				}
//			}
//		}
//		return buf.toString();
//	}

}