package haiyan.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericTypeValidator;

/**
 * StringUtil 及 ArrayUtil
 * 
 * @author zhouxw
 */
public final class StringUtil {
	/**
	 * EMPTY_STRINGARR
	 */
	public static final String[] EMPTY_STRINGARR = new String[0];
	/**
	 * Comment for <code>EMPTY_STRING</code>
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * Comment for <code>DOT</code>
	 */
	public static final char DOT = '.';

	/**
	 * Comment for <code>UNDERSCORE</code>
	 */
	public static final char UNDERSCORE = '_';

	/**
	 * Comment for <code>COMMA</code>
	 */
	public static final String COMMA = ",";

	/**
	 * Comment for <code>OPEN_PAREN</code>
	 */
	public static final String OPEN_PAREN = "(";

	/**
	 * Comment for <code>CLOSE_PAREN</code>
	 */
	public static final String CLOSE_PAREN = ")";

	// /**
	// * @param arrObj
	// * @param seperator
	// * @return String
	// */
	// public static String join(Object[] arrObj, String seperator) {
	// String sReturn = "";
	// for (Object s : arrObj) {
	// sReturn += s + seperator;
	// }
	// // Delete last ;
	// if (sReturn.length() != 0) {
	// sReturn = sReturn.substring(0, sReturn.length()
	// - seperator.length());
	// }
	// return sReturn;
	// }
	//
//	/**
//	 * @deprecated
//	 * @param arrObj
//	 * @param seperator
//	 * @return String
//	 */
//	public static String join(String seperator, Object[] arrObj) {
//		return join(arrObj, seperator);
//	}
//
//	/**
//	 * @param seperator
//	 * @param arrObj
//	 * @return String
//	 */
//	public static String join(Object[] arrObj, String seperator) {
//		int length = arrObj.length;
//		if (length == 0)
//			return EMPTY_STRING;
//		arrObj[0] = arrObj[0] == null ? "" : arrObj[0];
//		StringBuffer buf = new StringBuffer(length
//				* arrObj[0].toString().length()).append(arrObj[0]);
//		for (int i = 1; i < length; i++) {
//			arrObj[i] = arrObj[i] == null ? "" : arrObj[i];
//			buf.append(seperator).append(arrObj[i]);
//		}
//		return buf.toString();
//	}
//
//	/**
//	 * @param seperator
//	 * @param objects
//	 * @return String
//	 */
//	public static String join(Iterator<?> objects, String seperator) {
//		StringBuffer buf = new StringBuffer();
//		buf.append(objects.next());
//		while (objects.hasNext()) {
//			buf.append(seperator).append(objects.next());
//		}
//		return buf.toString();
//	}
	/**
	 * @param x
	 * @param sep
	 * @param y
	 * @return String[]
	 */
	public static String[] add(String[] x, String sep, String[] y) {
		String[] result = new String[x.length];
		for (int i = 0; i < x.length; i++) {
			result[i] = x[i] + sep + y[i];
		}
		return result;
	}

	/**
	 * @param seperator
	 * @param strings
	 * @return
	 */
	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	/**
	 * @param seperator
	 * @param objects
	 * @return
	 */
	public static String join(String seperator, Iterator<?> objects) {
		StringBuffer buf = new StringBuffer();
		buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}
	/**
	 * @param string
	 * @param times
	 * @return String
	 */
	public static String repeat(String string, int times) {
		HyStringBuffer buf = new HyStringBuffer(string.length() * times);
		for (int i = 0; i < times; i++)
			buf.append(string);
		return buf.toString();
	}
	/**
	 * @param str
	 * @param seperators
	 * @return String[]
	 */
	public static String[] splitHasNVL(String str, String seperators,
			boolean flag) {
		ArrayList<String> result = new ArrayList<String>();
		String temp = str;
		while (temp != null && temp.indexOf(seperators) != -1) {
			String value = temp.substring(0, temp.indexOf(seperators));
			// System.out.println(value);
			result.add(value);
			temp = temp.substring(
					temp.indexOf(seperators) + seperators.length(),
					temp.length());
		}
		if (flag)
			result.add(temp);
		else if (!isBlankOrNull(temp))
			result.add(temp);
		String[] datas = new String[result.size()];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = (String) result.get(i);
		}
		return datas;
	}
	/**
	 * @param str
	 * @param seperators
	 * @return String[]
	 */
	public static String[] splitHasNVL(String str, String seperators) {
		return splitHasNVL(str, seperators, false);
	}
	/**
	 * @param list
	 * @param seperators
	 * @param include
	 * @return String[]
	 */
	public static String[] split(String list, String seperators) {
		return split(list, seperators, false);
	}
	/**
	 * @param list
	 * @param seperators
	 * @param include
	 * @return String[]
	 */
	public static String[] split(String list, String seperators, boolean include) {
		if (list == null)
			return new String[0];
		// boolean include = false;
		// if (len(args) > 0)
		// include = args[0];
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}
	/**
	 * @param str
	 * @param div
	 * @return String[]
	 */
	public static String[] splitUniq(String str, String div) {
		StringTokenizer stk = new StringTokenizer(str, div);
		ArrayList<String> valueList = new ArrayList<String>();
		while (stk.hasMoreElements()) {
			valueList.add(stk.nextToken());
		}
		return valueList.toArray(new String[0]);
	}
	/**
	 * @param str
	 * @param i
	 * @return String
	 */
	public static String left(String str, int i) {
		if (i > str.length())
			i = str.length();
		return str.substring(0, i);
	}
	/**
	 * @param str
	 * @param i
	 * @return String
	 */
	public static String right(String str, int i) {
		if (i > str.length())
			i = str.length();
		return str.substring(str.length() - i, str.length());
	}
	/**
	 * @param str
	 * @param i
	 * @param len
	 */
	public static String mid(String str, int i, int len) {
		if (i + len > str.length())
			return str.substring(i, str.length());
		return str.substring(i, i + len);
	}
	/**
	 * @param str
	 * @param i
	 * @return String
	 */
	public static String mid(String str, int i) {
		return mid(str, i, str.length());
	}
	/**
	 * @param str
	 * @return int
	 */
	public static int len(String str) {
		if (str == null)
			return 0;
		else
			return str.length();
	}
	/**
	 * @param args
	 * @return int
	 */
	public static int len(Object[] args) {
		if (args == null)
			return 0;
		else
			return args.length;
	}
	/**
	 * @param qualifiedName
	 * @return String
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, ".");
	}
	/**
	 * @param qualifiedName
	 * @param seperator
	 * @return String
	 */
	public static String unqualify(String qualifiedName, String seperator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(seperator) + 1);
	}
	/**
	 * @param qualifiedName
	 * @return String
	 */
	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		if (loc < 0) {
			return EMPTY_STRING;
		} else {
			return qualifiedName.substring(0, loc);
		}
	}
	/**
	 * @param strs
	 * @param suffix
	 * @return String[]
	 */
	public static String[] suffix(String[] strs, String suffix) {
		if (suffix == null)
			return strs;
		String[] result = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = suffix(strs[i], suffix);
		}
		return result;
	}
	/**
	 * @param str
	 * @param suffix
	 * @return String
	 */
	public static String suffix(String str, String suffix) {
		return (suffix == null) ? str : str + suffix;
	}
	/**
	 * @param strs
	 * @param prefix
	 * @return String[]
	 */
	public static String[] prefix(String[] strs, String prefix) {
		if (prefix == null)
			return strs;
		String[] result = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = prefix + strs[i];
		}
		return result;
	}
	/**
	 * @param qualifiedName
	 * @return String
	 */
	public static String root(String qualifiedName) {
		int loc = qualifiedName.indexOf(".");
		return (loc < 0) ? qualifiedName : qualifiedName.substring(0, loc);
	}
	/**
	 * @param tfString
	 * @return boolean
	 */
	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}
	/**
	 * @param array
	 * @param delimiter
	 * @param label
	 * @return
	 */
	public static String joinSQL(Object[] array, String delimiter, String label) {
		return toArrString(array, delimiter, label, true);
	}
	/**
	 * @param array
	 * @param delimiter
	 * @param label
	 * @return
	 */
	public static String join(Object[] array, String delimiter, String label) {
		return toArrString(array, delimiter, label, false);
	}
	/**
	 * @param array
	 * @param delimiter
	 * @param label
	 * @return
	 */
	public static String join(Object[] array, String delimiter) {
		return toArrString(array, delimiter, "", false);
	}
	private static String toArrString(Object[] array, String delimiter, String label, boolean unSQLInj) {
		int len = array.length;
		if (len == 0)
			return StringUtil.EMPTY_STRING;
		if (delimiter == null)
			delimiter = COMMA;
		Object s;
		HyStringBuffer buf = new HyStringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++) {
			s = array[i];
			if (s == null)
				s = "";
			if (unSQLInj)
				s = unSqlInjection(s.toString());
			buf.append(label + array[i] + label).append(delimiter);
		}
		{
			s = array[len - 1];
			if (s == null)
				s = "";
			if (unSQLInj)
				s = unSqlInjection(s.toString());
			buf.append(label + s + label);
		}
		return buf.toString();
	}
	/**
	 * @param array
	 * @param delimiter
	 * @param label
	 * @return
	 */
	public static String join(net.sf.json.JSONArray array, String delimiter) {
		return toArrString(array, delimiter, true);
	}
	/**
	 * @param array
	 * @param delimiter
	 * @param wrap
	 * @return
	 */
	public static String join(net.sf.json.JSONArray array, String delimiter, boolean wrap) {
		return toArrString(array, delimiter, wrap);
	}
	private static String toArrString(net.sf.json.JSONArray array, String delimiter, boolean wrap) {
		int len = array.size();
		if (len == 0)
			return StringUtil.EMPTY_STRING;
		if (delimiter == null)
			delimiter = COMMA;
		Object a;
		HyStringBuffer buf = new HyStringBuffer(len * 12);
		if (wrap)
			buf.append(delimiter);
		for (int i = 0; i < len - 1; i++) {
			a = array.get(i);
			if (a == null)
				a = "";
			buf.append(a).append(delimiter);
		}
		{
			a = array.get(len-1);
			if (a == null)
				a = "";
			buf.append(a);
		}
		if (wrap)
			buf.append(delimiter);
		return buf.toString();
	}
	/**
	 * @param string
	 * @param placeholders
	 * @param replacements
	 * @return String[]
	 */
	public static String[] multiply(String string, Iterator<?> placeholders, Iterator<?> replacements) {
		String[] result = new String[] { string };
		while (placeholders.hasNext()) {
			result = multiply(result, (String) placeholders.next(), (String[]) replacements.next());
		}
		return result;
	}
	/**
	 * @param strings
	 * @param placeholder
	 * @param replacements
	 * @return String[]
	 */
	static String[] multiply(String[] strings, String placeholder,
			String[] replacements) {
		String[] results = new String[replacements.length * strings.length];
		int n = 0;
		for (int i = 0; i < replacements.length; i++) {
			for (int j = 0; j < strings.length; j++) {
				results[n++] = replace(strings[j], placeholder, replacements[i]);
			}
		}
		return results;
	}
	/**
	 * key:value;
	 * 
	 * @return HashMap Returns the stylepairs.
	 */
	public static HashMap<String, String> getPairs(String str) {
		HashMap<String, String> pairs = new HashMap<String, String>();
		if (!isBlankOrNull(str)) {
			String[] arr = split(str, ";");
			for (String pair : arr) {
				String[] arr2 = split(pair, ":");
				if (arr2.length == 2)
					pairs.put(arr2[0], arr2[1]);
			}
		}
		return pairs;
	}
	/**
	 * @param fileName
	 * @return String
	 */
	public static String getExtName(String fileName) {
		String[] names = fileName.split("\\.");
		String extName = "";
		if (names != null && names.length > 0)
			extName = names[names.length - 1];
		return extName;
	}
	/**
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @return String
	 */
	public static String replace(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new HyStringBuffer(template.substring(0, loc))
					.append(replacement)
					.append(template.substring(loc + placeholder.length()))
					.toString();
		}
	}
	/**
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @return String
	 */
	public static String replaceAll(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new HyStringBuffer(template.substring(0, loc))
				.append(replacement)
				.append(replaceAll(
					template.substring(loc + placeholder.length()),
					placeholder, replacement)).toString();
		}
	}
	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		return isBlankOrNull(obj.toString());
	}
	public static boolean isWebEmpty(Object obj) {
		return isEmpty(obj) || "undefined".equals(obj);
	}
	public static boolean isJSONEmpty(Object obj) {
		return isEmpty(obj) || "{}".equals(obj);
	}
	public static boolean isJSONArrayEmpty(Object obj) {
		return isEmpty(obj) || "[]".equals(obj);
	}
	/**
	 * @param str
	 * @return boolean
	 */
	public static boolean isValueEmpty(Object obj) {
		if (obj == null)
			return true;
		return isStrBlankOrNull(obj.toString());
	}
	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isBlankOrNull(Object obj) {
		if (obj == null)
			return true;
		return isBlankOrNull(obj.toString());
	}
	/**
	 * for wfevar access visit
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlankOrNull(String str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}
	/**
	 * @param str
	 * @return boolean
	 */
	public static boolean isTrimBlankOrNull(String str) {
		if (isBlankOrNull(str) || str.trim().length() == 0)
			return true;
		else
			return false;
	}
	/**
	 * @param str
	 * @return boolean
	 */
	public static boolean isStrBlankOrNull(String str) {
		if (isTrimBlankOrNull(str) || "null".equalsIgnoreCase(str))
			return true;
		else
			return false;
	}
	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isDate(Object obj) {
		if (obj == null)
			return false;
		return isDate(obj.toString());
	}
	/**
	 * @param str
	 * @return boolean
	 */
	static boolean isDate(String str) {
		Date result = GenericTypeValidator.formatDate(str, null);
		if (result != null)
			return true;
		return false;
	}
	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null)
			return false;
		return isNumeric(obj.toString());
	}
	/**
	 * @param str
	 * @return boolean
	 */
	static boolean isNumeric(String str) {
		Double result = GenericTypeValidator.formatDouble(str);
		if (result != null)
			return true;
		return false;
	}
	/**
	 * @param obj
	 * @return boolean
	 */
	public static boolean isBoolean(Object obj) {
		if (obj == null)
			return false;
		return isBoolean(obj.toString());
	}
	/**
	 * @param str
	 * @return boolean
	 */
	static boolean isBoolean(String str) {
		return (str != null && (str.equalsIgnoreCase("TRUE")
				|| str.equalsIgnoreCase("FALSE") || isNumeric(str)));
	}
	/**
	 * @param s
	 * @return
	 */
	public final static String unSqlInjection(String s) {
		if (StringUtil.isEmpty(s))
			return "";
		return s.replaceAll(".*([';]+|(--)+).*", " ");
	}
	/**
	 * @param str
	 * @return
	 */
	public static int countChineseChar(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		return m.groupCount();
//		if (m.find()) {
//			return true;
//		}
//		return false;
	}
	// public static void main(String[] args) {
	// DebugUtil.debug(isNumeric("2326,23623.23"));
	// }
}