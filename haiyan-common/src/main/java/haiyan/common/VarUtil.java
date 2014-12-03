package haiyan.common;

import haiyan.common.exception.Warning;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;

public class VarUtil {

	private VarUtil() {
	}

	public static boolean isEmpty(Object v) {
		if (v == null) {
			return true;
		}
		if ((v instanceof String))
			return ((String) v).length() == 0;
		if ((v instanceof Integer))
			return ((Integer) v).equals(Integer.valueOf(0));
		if ((v instanceof Double))
			return ((Double) v).equals(Double.valueOf(0.0D));
		if ((v instanceof Integer[]))
			return (((Integer[]) (Integer[]) v).length > 0)
					&& (((Integer[]) (Integer[]) v)[0].intValue() <= 0);
		if ((v instanceof Double[]))
			return (((Double[]) (Double[]) v).length > 0)
					&& (((Double[]) (Double[]) v)[0].doubleValue() <= 0.0D);
		if (((v instanceof java.util.Date)) || ((v instanceof java.sql.Date))) {
			return false;
		}
		return false;
	}

	public static boolean before(Object d1, Object d2) {
		if (((d1 instanceof java.util.Date))
				&& ((d2 instanceof java.util.Date)))
			return toDate(d1).before(toDate(d2));
		if (((d1 instanceof Integer)) && ((d2 instanceof Integer)))
			return toInt(d1) < toInt(d2);
		if (((d1 instanceof String)) && ((d2 instanceof String)))
			return toString(d1).compareTo(toString(d2)) < 0;
		if ((StringUtil.isNumeric(d1)) && (StringUtil.isNumeric(d2)))
			return toInt(d1) < toInt(d2);
		if (((StringUtil.isNumeric(d1)) && ((d2 instanceof java.util.Date)))
				|| ((StringUtil.isNumeric(d2)) && ((d1 instanceof java.util.Date)))) {
			return toInt(d1) < toInt(d2);
		}
		if (((d1 instanceof String)) || ((d2 instanceof String))) {
			return toInt(toDate(d1)) < toInt(toDate(d2));
		}

		if ((d1 != null) && (d2 == null))
			return true;
		if ((d1 == null) && (d2 != null))
			return false;
		if ((d1 == null) && (d2 == null)) {
			return false;
		}
		throw new Warning("非法类型");
	}

	public static Object getMaxPeriod(Object v1, Object v2) {
		if (before(v1, v2)) {
			return v2;
		}
		return v1;
	}

	public static Object getMinPeriod(Object v1, Object v2) {
		if (before(v1, v2)) {
			return v1;
		}
		return v2;
	}

	public static Timestamp toTimestamp(Object value) {
		if ((StringUtil.isEmpty(value)) || ((value instanceof Null))) {
			return null;
		}
		if ((value instanceof String)) {
			if ("null".equalsIgnoreCase(value.toString().trim())) {
				return null;
			}

			java.util.Date d = toDate(value);
			return new Timestamp(d.getTime());
		}
		java.util.Date d = toDate(value);
		if (d == null)
			return null;
		return new Timestamp(d.getTime());
	}

	public static java.util.Date toDate(Object value) {
		if ((value == null) || ((value instanceof Null)))
			return null;
		if ((value instanceof Long)) {
			long l = ((Long) value).longValue();
			l = l * 1000L * 60L * 60L * 24L - 28800000L;
			return new java.util.Date(l);
		}
		if ((value instanceof Integer)) {
			long l = ((Integer) value).intValue();
			l = l * 1000L * 60L * 60L * 24L - 28800000L;
			return new java.util.Date(l);
		}
		if ((value instanceof Double)) {
			long l = ((Double) value).longValue();
			l = l * 1000L * 60L * 60L * 24L - 28800000L;
			return new java.util.Date(l);
		}
		if ((value instanceof java.util.Date))
			return (java.util.Date) value;
		if ((value instanceof String)) {
			if ("".equals(value))
				return null;
			if (StringUtil.isNumeric(value)) {
				Double d = Double.valueOf(Double.parseDouble((String) value));
				int l = d.intValue();
				l = l * 1000 * 60 * 60 * 24 - 28800000;
				return new java.util.Date(l);
			}
			try {
				return DateUtil.getDate("" + value);
			} catch (Exception e) {
				DebugUtil.debug(">error:传入的时间值为：[" + value + "]" + e.getMessage());
				if (StringUtil.isNumeric(value))
					return toDate(Integer.valueOf(new Double("" + value)
							.intValue()));
				if ((("" + value).length() == 8)
						&& (("" + value).lastIndexOf(":") == 5)) {
					return DateUtil.getDate("" + value, "HH:mm:ss");
				}
				throw new Warning(e);
			} catch (Throwable e) {
				DebugUtil.debug(">error:" + e.getMessage());
				if (StringUtil.isNumeric(value)) {
					return toDate(Integer.valueOf(new Double("" + value).intValue()));
				}
				throw Warning.wrapException(e);
			}

		}

		return null;
	}

	public static int toInt(Object value) {
		if (value == null)
			return 0;
		if ((value instanceof String)) {
			if (!StringUtil.isNumeric(value)) {
				return 0;
			}
			Double d = Double.valueOf(Double.parseDouble((String) value));
			return d.intValue();
		}
		if ((value instanceof Integer))
			return ((Integer) value).intValue();
		if ((value instanceof Long))
			return ((Long) value).intValue();
		if ((value instanceof Double))
			return ((Double) value).intValue();
		if ((value instanceof Boolean))
			return ((Boolean) value).booleanValue() ? -1 : 0;
		if ((value instanceof java.util.Date)) {
			return Long.valueOf((((java.util.Date) value).getTime() + 28800000L) / 86400000L).intValue();
		}
		if ((value instanceof BigDecimal)) {
			throw new Warning("No expectation");
		}
		if ((value instanceof Float))
			return ((Float) value).intValue();
		if (((value instanceof Object[]))
				&& (((Object[]) (Object[]) value).length == 1)) {
			return toInt(((Object[]) (Object[]) value)[0]);
		}
		return 0;
	}

	public static long toLong(Object value) {
		if (value == null)
			return 0L;
		if ((value instanceof String)) {
			if (!StringUtil.isNumeric(value)) {
				return 0L;
			}
			Double d = Double.valueOf(Double.parseDouble((String) value));
			return d.longValue();
		}
		if ((value instanceof Integer))
			return ((Integer) value).longValue();
		if ((value instanceof Long))
			return ((Long) value).longValue();
		if ((value instanceof Double))
			return ((Double) value).longValue();
		if ((value instanceof Boolean))
			return ((Boolean) value).booleanValue() ? -1L : 0L;
		if ((value instanceof java.util.Date)) {
			return Long.valueOf((((java.util.Date) value).getTime() + 28800000L) / 86400000L).longValue();
		}
		if ((value instanceof BigDecimal)) {
			throw new Warning("No expectation");
		}
		if ((value instanceof Float))
			return ((Float) value).longValue();
		if (((value instanceof Object[]))
				&& (((Object[]) (Object[]) value).length == 1)) {
			return toLong(((Object[]) (Object[]) value)[0]);
		}
		return 0L;
	}
	public static Integer[] toIntArray(Object value) {
		if (value == null)
			return null;
		if ((value instanceof Object[])) {
			Object[] t = (Object[]) (Object[]) value;
			Integer[] v = new Integer[t.length];
			for (int i = 0; i < t.length; i++) {
				v[i] = Integer.valueOf(toInt(t[i]));
			}
			return v;
		}
		if ((value instanceof Integer[]))
			return (Integer[]) (Integer[]) value;
		if ((value instanceof Integer)) {
			Integer[] v = new Integer[1];
			v[0] = ((Integer) value);
			return v;
		}
		DebugUtil.error(" class " + value.getClass() + " to Integer[] not impl ");
		return null;
	}
	public static double[] toDoubleArray(Object value)  {
		if (value == null)
			return null;
		if (((value instanceof Object[])) || ((value instanceof Integer[]))) {
			Object[] t = (Object[]) (Object[]) value;
			double[] v = new double[t.length];
			for (int i = 0; i < t.length; i++) {
				v[i] = toDouble(t[i]).doubleValue();
			}
			return v;
		}
		if ((value instanceof double[]))
			return (double[]) (double[]) value;
		if ((value instanceof Double)) {
			double[] v = new double[1];
			v[0] = Double.parseDouble(value.toString());
			return v;
		}
		if ((value instanceof Integer)) {
			double[] v = new double[1];
			v[0] = toDouble(value).doubleValue();
			return v;
		}
		DebugUtil
				.error(" class " + value.getClass() + " to double[] not impl ");
		return null;
	}
	public static java.util.Date[] toDateArray(Object value) {
		if (value == null)
			return null;
		if ((value instanceof Object[])) {
			Object[] t = (Object[]) (Object[]) value;
			java.util.Date[] v = new java.util.Date[t.length];
			for (int i = 0; i < t.length; i++) {
				v[i] = toDate(t[i]);
			}
			return v;
		}
		if ((value instanceof java.util.Date[]))
			return (java.util.Date[]) (java.util.Date[]) value;
		if ((value instanceof java.util.Date)) {
			java.util.Date[] v = new java.util.Date[1];
			v[0] = ((java.util.Date) value);
			return v;
		}
		DebugUtil.error(" class " + value.getClass()
				+ " to java.util.Date[] not impl ");
		return null;
	}

	public static String adaptFormat(String value) {
		String newValue = value;
		if (value.indexOf(46) >= 0) {
			int index = value.length() - 1;
			int lastIndex = 0;
			while (index > 0) {
				if (value.charAt(index) == '.') {
					lastIndex = index;
					break;
				}
				if (value.charAt(index) != '0') {
					lastIndex = index + 1;
					break;
				}
				index--;
			}
			if ((lastIndex > 0) && (lastIndex < value.length())) {
				newValue = value.substring(0, lastIndex);
			}
		}
		return newValue;
	}

	public static String toString(Object value) {
		if (value == null)
			return "";
		if ((value instanceof String))
			return (String) value;
		if ((value instanceof Integer))
			return ((Integer) value).toString();
		if ((value instanceof Double)) {
			BigDecimal b = new BigDecimal(value.toString());
			return adaptFormat(b.toString());
		}
		if ((value instanceof Boolean))
			return ((Boolean) value).booleanValue() ? "true" : "false";
		if ((value instanceof Timestamp)) {
			return DateUtil.format((Timestamp) value, "yyyy-MM-dd HH:mm:ss");
		}
		if ((value instanceof Time)) {
			return DateUtil.format((Time) value, "yyyy-MM-dd HH:mm:ss");
		}
		if ((value instanceof java.util.Date)) {
			return DateUtil.format((java.util.Date) value, "yyyy-MM-dd");
		}
		if ((value instanceof Integer[]))
			return StringUtil.join((Object[]) value, ";");
		if ((value instanceof Long))
			return ((Long) value).toString();
		if ((value instanceof BigDecimal))
			return ((BigDecimal) value).toString();
		if ((value instanceof List)) {
			return value.toString();
		}
		return "";
	}
	public static boolean toBool(Object obj) {
		if (StringUtil.isNumeric(obj)) {
			return toInt(obj) != 0;
		}
		if (StringUtil.isBoolean(obj)) {
			return new Boolean(obj.toString()).booleanValue();
		}
		return false;
	}
	public static boolean toBoolean(Object value) {
		if (value == null)
			return false;
		if ((value instanceof String)) {
			if (StringUtil.isNumeric((String) value)) {
				return Double.valueOf((String) value).doubleValue() != 0.0D;
			}
			return Boolean.parseBoolean((String) value);
		}
		if ((value instanceof Integer))
			return !value.equals(Integer.valueOf(0));
		if ((value instanceof Double))
			return !value.equals(Double.valueOf(0.0D));
		if ((value instanceof Boolean)) {
			return ((Boolean) value).booleanValue();
		}
		return false;
	}
	public static Double toDouble(Object o)  {
		if (o == null)
			return Double.valueOf(0.0D);
		if ((o instanceof Double)) {
			Double d = (Double) o;
			return d;
		}
		if ((o instanceof Integer)) {
			Integer i = (Integer) o;
			return new Double(i.intValue());
		}
		if ((o instanceof Long)) {
			Long i = (Long) o;
			return new Double(i.longValue());
		}
		if ((o instanceof BigDecimal)) {
			BigDecimal bd = (BigDecimal) o;
			return Double.valueOf(bd.doubleValue());
		}
		if ((o instanceof String))
			try {
				if (o.toString().length() == 0) {
					return Double.valueOf(0.0D);
				}
				return Double.valueOf(Double.parseDouble((String) o));
			} catch (NumberFormatException e) {
				throw new Warning("Incompatible type or unsupported data format");
			}
		if ((o instanceof java.util.Date)) {
			return new Double(toInt(o));
		}
		throw new Warning("Incompatible type or unsupported data format");
	}
	public static BigDecimal toBigDecimal(Object o) {
		if (o == null)
			return BigDecimal.ZERO;
		if ((o instanceof Double))
			return new BigDecimal(Double.toString(((Double) o).doubleValue()));
		if ((o instanceof Integer))
			return BigDecimal.valueOf(((Integer) o).intValue());
		if ((o instanceof Long))
			return BigDecimal.valueOf(((Long) o).longValue());
		if ((o instanceof BigDecimal))
			return (BigDecimal) o;
		if ((o instanceof String)) {
			if (((String) o).isEmpty()) {
				return BigDecimal.ZERO;
			}
			return new BigDecimal((String) o);
		}
		if ((o instanceof java.util.Date)) {
			return BigDecimal.valueOf(((java.util.Date) o).getTime());
		}
		throw new Warning("Incompatible type or unsupported data format");
	}
	public static Boolean toShallowBoolean(Object o, boolean dealNullTrue)  {
		return Boolean.valueOf(shallowCheckVariable(o, dealNullTrue));
	}
	public static Boolean toDeepBoolean(Object o, boolean dealNullTrue)  {
		return Boolean.valueOf(deepCheckVariable(o, dealNullTrue));
	}
	public static boolean shallowCheckVariable(Object o, boolean dealNullTrue) {
		if (o == null) {
			return dealNullTrue;
		}
		if ((o instanceof Integer)) {
			Integer intValue = (Integer) o;
			return intValue.intValue() != 0;
		}
		if ((o instanceof Double)) {
			Double doubleValue = (Double) o;
			return doubleValue.doubleValue() != 0.0D;
		}
		if ((o instanceof Boolean)) {
			Boolean booleanValue = (Boolean) o;
			return booleanValue.booleanValue();
		}
		if ((o instanceof String)) {
			String stringValue = (String) o;
			return stringValue.length() > 0;
		}
		return false;
	}
	public static boolean deepCheckVariable(Object o, boolean dealNullTrue) {
		if (o == null) {
			return dealNullTrue;
		}
		if ((o instanceof Integer)) {
			Integer intValue = (Integer) o;
			return intValue.intValue() != 0;
		}
		if ((o instanceof Double)) {
			Double doubleValue = (Double) o;
			return doubleValue.doubleValue() != 0.0D;
		}
		if ((o instanceof Boolean)) {
			Boolean booleanValue = (Boolean) o;
			return booleanValue.booleanValue();
		}
		if ((o instanceof String)) {
			String stringValue = (String) o;
			return stringValue.equalsIgnoreCase("true");
		}
		return false;
	}
	public static String toString(Object o, String valueWhenNull) {
		if (o == null)
			return valueWhenNull;
		return o.toString();
	}
	public static boolean isEquals(Object o1, Object o2)  {
		if (o1 == null)
			return o2 == null;
		if ((o1 instanceof Long))
			return ((Long) o1).equals(Long.valueOf(toLong(o2)));
		if ((o1 instanceof Integer))
			return ((Integer) o1).equals(Integer.valueOf(toInt(o2)));
		if ((o1 instanceof String))
			return ((String) o1).equals(toString(o2));
		if ((o1 instanceof Double))
			return ((Double) o1).compareTo(toDouble(o2)) == 0;
		if ((o1 instanceof java.util.Date))
			return ((java.util.Date) o1).equals(toDate(o2));
		if ((o1 instanceof BigDecimal)) {
			return toBigDecimal(o1).compareTo(toBigDecimal(o2)) == 0;
		}
		throw new Warning("比较时未处理的类型" + o1 + ":" + o2);
	}

	public static Object[] toObjectArray(Object value) {
		if (value == null)
			return null;
		if ((value instanceof Object[])) {
			return (Object[]) (Object[]) value;
		}
		Object[] v = new Object[1];
		v[0] = value;
		return v;
	}

	public static Object[] toParameterTypes(Object[] paras, Class<?>[] paraTypes)  {
		for (int index = 0; index < paraTypes.length; index++) {
			Class<?> type = paraTypes[index];
			if ((type == Integer.class) || (type == Integer.TYPE))
				paras[index] = Integer.valueOf(toInt(paras[index]));
			else if ((type == Double.class) || (type == Double.TYPE))
				paras[index] = toDouble(paras[index]);
			else if ((type == Long.class) || (type == Long.TYPE))
				paras[index] = Long.valueOf(toLong(paras[index]));
			else if ((type == Float.class) || (type == Float.TYPE))
				paras[index] = Float.valueOf(Float
						.parseFloat(toString(paras[index])));
			else if ((type == Boolean.class) || (type == Boolean.TYPE))
				paras[index] = Boolean.valueOf(toBoolean(paras[index]));
			else if (type == java.util.Date.class)
				paras[index] = toDate(paras[index]);
			else if (type == BigDecimal.class)
				paras[index] = toBigDecimal(paras[index]);
			else if (type == String.class)
				paras[index] = toString(paras[index]);
			else if (type != Object.class) {
				continue;
			}
		}
		return paras;
	}

	public static Serializable toObject(byte[] bytes) throws Throwable {
		if (bytes == null)
			return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream dis = new ObjectInputStream(bis);
		Serializable s = null;
		try {
			s = (Serializable) dis.readObject();
		} finally {
			CloseUtil.close(dis);
			CloseUtil.close(bis);
		}
		return s;
	}
}
