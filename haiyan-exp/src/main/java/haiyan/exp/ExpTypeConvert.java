package haiyan.exp;

import haiyan.common.DateUtil;
import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 

/**
 * user defined
 * 
 * @author suddenzhou
 * 
 */
public class ExpTypeConvert {

	/**
	 * 将数据转化为Date类型
	 * 
	 * @param value
	 * @return Date
	 */
	public static Date toDate(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Long) {
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			long l = (Long) value;
			l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
			return new Date(l);
		} else if (value instanceof Integer) {
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			long l = (Integer) value;
			l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
			return new Date(l);
		} else if (value instanceof Double) {
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			long l = ((Double) value).longValue();
			l = l * 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 8;
			return new Date(l);
		} else if (value instanceof Date) {
			return (Date) value;
		} else if (value instanceof String) {
			if ("".equals(value))
				return null;
			try {
				return DateUtil.getDate("" + value);
				// return new SimpleDateFormat("yyyy-MM-dd").parse((String)
				// value);
			} catch (Throwable ex) {
				DebugUtil.debug(">error:" + ex.getMessage());
				if (StringUtil.isNumeric(value))
					return toDate(new Double("" + value).intValue());
				throw Warning.getWarning(ex);
			}
		}
		assert false;
		return null;
	}

	/**
	 * 将数据转化为int型
	 * 
	 * @param value
	 * @return int
	 */
	public static int toInt(Object value) {
		if (value == null) {
			return 0;
		} else if (value instanceof String) {
			if (!StringUtil.isNumeric(value)) {
				return 0;
			}
			return Double.valueOf((String) value).intValue();
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Long) {
			return ((Long) value).intValue();
		} else if (value instanceof Double) {
			return ((Double) value).intValue();
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? -1 : 0;
		} else if (value instanceof Date) { // 将日期转化为int型,为了公式计算方便
			// Java Date类型Thu Jan 01 08:00:00 CST 1970的getTime == 0
			return Long.valueOf(
					(((Date) value).getTime() + 1000 * 60 * 60 * 8) / (1000 * 60 * 60 * 24)).intValue();
		} else if (value instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) value;
			return bd.intValue();
		}
		assert false;
		return 0;
	}

	/**
	 * 将数据转化为一个字符串型
	 * 
	 * @param value
	 * @return String
	 */
	public static String toString(Object value) {
		if (value == null) {
			return "";
		} else if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Integer) {
			return ((Integer) value).toString();
		} else if (value instanceof Double) {
			return ((Double) value).toString();
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? "true" : "false";
		} else if (value instanceof Timestamp) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.format((Timestamp) value);
		} else if (value instanceof Time) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.format((Time) value);
		} else if (value instanceof Date) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format((Date) value);
		} else {
			return value.toString();
		}
		// assert false;
		// return "";
	}

	/**
	 * 将数据转化为boolean型
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean toBoolean(Object value) {
		if (value == null) {
			return false;
		} else if (value instanceof String) {
			return Boolean.parseBoolean((String) value);
		} else if (value instanceof Integer) {
			return value.equals(-1);
		} else if (value instanceof Double) {
			return value.equals(-1);
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		}
		assert false;
		return false;
	}

	// cast to double
	public static Double toDouble(Object o) throws ParseException {
		if (o == null)
			return null;
		if (o instanceof Double) {
			Double d = (Double) o;
			return d;
		} else if (o instanceof Integer) {
			Integer i = (Integer) o;
			return new Double(i);
		} else if (o instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) o;
			return bd.doubleValue();
		} else if (o instanceof String) {
			return Double.parseDouble((String) o);
		} else {
			throw new ParseException("unknown value=" + o);
		}
	}


	// cast to string
	public static String toString(Object o, String valueWhenNull) {
		if (o == null)
			return valueWhenNull;
		return o.toString();
	}

	/**
	 * for exp
	 *
	 * @param v
	 * @return Boolean
	 * @throws ParseException
	 */
	static Boolean getBoolean(Object v) throws ParseException {
		if (v == null) {
			return true;
		} else if (v instanceof ExpNull) {
			throw new ParseException("null");
		} else if (v instanceof Boolean) {
			return (Boolean) v;
		} else if (v instanceof Double) {
			return !((Double) v == 0);
		} else if (v instanceof Integer) {
			return !((Integer) v == 0);
		} else if (v instanceof String) {
			return ((String) v).equalsIgnoreCase("true");
		} else {
			return true;
		}
	}

	/**
	 * for exp
	 *
	 * @param v
	 * @return String
	 * @throws ParseException
	 */
	static String getString(Object v) throws ParseException {
		if (v == null)
			return "";
		if (v instanceof ExpNull) {
			throw new ParseException("null");
		} else if (v instanceof String) {
			return (String) v;
		} else if (v instanceof Boolean) {
			return (Boolean) v ? "true" : "false";
		} else if (v instanceof Double) {
			if (((Double) v) == (((Double) v).intValue()))
				return ((Integer) ((Double) v).intValue()).toString();
			else
				return ((Double) v).toString();
		} else if (v instanceof Integer) {
			return ((Integer) v).toString();
		} else if (v instanceof Long) {
			return ((Long) v).toString();
		} else if (v instanceof BigDecimal) {
			return ((BigDecimal) v).toString();
		} else if (v instanceof String[]) {
			return StringUtil.join(((String[]) v), ",", "");
		} else {
			return v.toString();
		}
	}

	/**
	 * for exp
	 *
	 * @param v
	 * @return Double
	 * @throws ParseException
	 */
	static Double getDouble(Object v) throws ParseException {
		if (StringUtil.isEmpty(v))
			return 0.0;
		if (v instanceof ExpNull) {
			throw new ParseException("null");
		} else if (v instanceof String) {
			return Double.parseDouble((String) v);
		} else if (v instanceof Boolean) {
			return (Boolean) v ? 1.0 : 0.0;
		} else if (v instanceof Double) {
			return (Double) v;
		} else if (v instanceof Integer) {
			return ((Integer) v).doubleValue();
		} else if (v instanceof Long) {
			return ((Long) v).doubleValue();
		} else if (v instanceof BigDecimal) {
			return ((BigDecimal) v).doubleValue();
		} else if (v instanceof Date) {
			return (double) ((Date) v).getTime();
		} else {
			return 0.0;
		}
	}

	/**
	 * for exp
	 **/
	public static boolean isDouble(Object v) {
		if (v instanceof ExpNull) {
			return false;
		} else if (v instanceof Boolean) {
			return true;
		} else if (v instanceof String) {
			try {
				Double.parseDouble((String) v);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (v instanceof Double) {
			return true;
		} else if (v instanceof Integer) {
			return true;
		} else if (v instanceof Long) {
			return true;
		} else if (v instanceof BigDecimal) {
			return true;
		} else if (v instanceof Date) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param d
	 * @return String
	 * @throws ParseException
	 */
	public static String toRmb(double d) throws ParseException {
		if (d > 9999999999999.99 || d < -9999999999999.99) {
			throw new ParseException(ParseException.Err_Para_Type);
		}
		return ChineseMoney.getChineseMoney(d);
	}

	// // cast to shallow boolean
	// static Boolean toShallowBoolean(Object o, boolean dealNullTrue) {
	// return BooleanUtil.shallowCheckVariable(o, dealNullTrue);
	// }
	//
	// // cast to deep boolean
	// static Boolean toDeepBoolean(Object o, boolean dealNullTrue) {
	// return BooleanUtil.deepCheckVariable(o, dealNullTrue);
	// }
	// /**
	// * @param str
	// * @return String
	// */
	// static String py(String str) {
	// return PYUtil.toPY(str);
	// }
}

// http://www.cujava.com/read.php?66
class ChineseMoney {

	private static String number[] = { "", "壹", "贰", "叁", "肆", "伍", "陆", "柒",
			"捌", "玖" };

	private static String unit[] = { "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟",
			"亿", "拾", "佰", "仟", "万" };

	private static String small[] = { "角", "分" };

	private static String strNumber, strUnit, strAll;

	private static String onlyInt(long intInt) {
		String strInt;
		strInt = String.valueOf(intInt);
		strNumber = "";
		strUnit = "";
		strAll = "";
		int l = strInt.length();
		int j, k, zeorCount; // zeorCount这个拼写错误是原代码中的,保留这个错误拼写.
		zeorCount = 0;
		for (k = 0; k < l; k++) {
			String strTemp = strInt.substring(k, k + 1);
			int intTemp = Integer.parseInt(strTemp);
			strNumber = number[intTemp];
			j = l - 1 - k;
			if (intTemp == 0) {
				strUnit = "";
				zeorCount++;
				if ("万".equals(unit[j]) || "亿".equals(unit[j])) {
					if (zeorCount < 4) {
						strUnit = unit[j];
					}
					zeorCount = 0;
				} else if ("圆".equals(unit[j]) && intInt != 0) {
					strUnit = unit[j];
				}
			} else {
				strUnit = unit[j];
				if (zeorCount != 0 && intTemp != 0)
					strNumber = "零" + strNumber;
				zeorCount = 0;
			}
			strAll += strNumber + strUnit;
		}
		strAll = strAll.replaceAll("壹拾", "拾");
		return strAll;

	}

	private static String onlySmall(int intSmall) {

		strNumber = "";
		strUnit = "";
		strAll = "";
		String strSmall, strTemp;
		strSmall = String.valueOf(intSmall);
		int i;
		if (intSmall >= 10) {
			for (i = 0; i < strSmall.length(); i++) {
				strTemp = String.valueOf(intSmall).substring(i, i + 1);
				if (Integer.parseInt(strTemp) != 0) {
					strNumber = number[Integer.parseInt(strTemp)];
					strUnit = small[i];
					strAll += strNumber + strUnit;
				}
			}
		} else {
			if (intSmall != 0) {
				strNumber = number[intSmall];
				strUnit = small[1];
				strAll += strNumber + strUnit;
			}
		}

		return strAll;
	}

	static String getChineseMoney(double money) {
		// 四舍五入
		double number = (java.lang.Math.abs(money) * 100 + 0.5) / 100;

		String strAll, strChineseInt, strChineseSmall, strZheng;

		// int intInt,intSmall;
		long intInt;
		int intSmall;
		strChineseInt = "";
		strChineseSmall = "";
		strZheng = "";

		// 整数部分
		intInt = (long) (number * 100 / 100);
		if (intInt != 0) {
			strChineseInt = onlyInt(intInt);
		}
		// 小数部分
		double temp = (number - intInt) * 100 * 100 / 100;
		// 对小数部分四舍五入
		intSmall = (int) (temp * 100 + 0.5) / 100;
		if (intInt == 0 && intSmall == 0)
			return "零圆整"; // 这个要通用处理比较麻烦,随便写一下就是了.
		if (intSmall != 0) {
			strChineseSmall = onlySmall(intSmall);
		} else {
			strZheng = "整";
		}
		strAll = strChineseInt + strChineseSmall + strZheng;
		if (money < 0)
			strAll = "负" + strAll;
		return strAll;
	}

	static void main(String args[]) throws IOException {
		// ChineseMoney cm = new ChineseMoney();
		double money;
		String strMoney, strChineseMoney;
		strMoney = "236236.01";
		// 读取
		// System.out.println("输入货币(四舍五入):");
		// BufferedReader cin = new BufferedReader(
		// new InputStreamReader(System.in));
		// strMoney = cin.readLine();
		money = Double.parseDouble(strMoney);
		strChineseMoney = ChineseMoney.getChineseMoney(money);
		System.out.println(strChineseMoney);
	}
}

//
// class ChinaMoney {
//
// static String convert(Double d) {
// String num = "num";
// String dw = "dw";
// String m = d.toString();
// String mm[] = null;
// mm = m.split("\\.");
// String money = mm[0];
//
// String result = num.charAt(Integer.parseInt("" + mm[1].charAt(0)))
// + "��" + num.charAt(Integer.parseInt("" + mm[1].charAt(1)))
// + "��";
//
// for (int i = 0; i < money.length(); i++) {
// String str = "";
// int n = Integer.parseInt(money.substring(money.length() - i - 1,
// money.length() - i));
// str = str + num.charAt(n);
// if (i == 0) {
// str = str + dw.charAt(i);
// } else if ((i + 4) % 8 == 0) {
// str = str + dw.charAt(4);
// } else if (i % 8 == 0) {
// str = str + dw.charAt(5);
// } else {
// str = str + dw.charAt(i % 4);
// }
// result = str + result;
// }
//
// result = result.replaceAll("��([^����Բ�Ƿ�])", "��");
// result = result.replaceAll("����+��", "����");
// result = result.replaceAll("��+", "��");
// result = result.replaceAll("��([����Բ])", "$1");
// // result = result.replaceAll("^...", "");
//
// return result;
// }
// }
