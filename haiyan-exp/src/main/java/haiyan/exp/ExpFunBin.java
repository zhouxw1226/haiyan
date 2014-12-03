package haiyan.exp;

import haiyan.common.StringUtil;

import java.util.Date;

/**
 * @author zhouxw
 */
class ExpFunBin {

	private ExpFunBin() {
	}

	/**
	 * 运算符计算.所有和Null相关的计算都返回Null.
	 * 
	 * @param op
	 *            计算符.
	 * @param paras
	 *            只有boolean, double, Null, String四种类型的参数数组.
	 * @return 只有boolean, double, Null, String这四种类型的结果.
	 * @throws ParseException
	 */
	static Object eval(String op, Object[] paras) throws ParseException {
		if (paras.length == 1) {
			if (paras[0] instanceof ExpNull)
				return ExpCore.getNull();
		} else if (paras.length == 2) {
			if ((paras[0] instanceof ExpNull) || (paras[1] instanceof ExpNull))
				return ExpCore.getNull();
		} else {
			// 目前没有三目运算符
			throw new ParseException(ParseException.Err_Paras_Number);
		}
		return evalWithoutNull(op, paras);
	}

	/**
	 * 进行非Null的单目和双目运算符计算.
	 * 这个方法中的代码采用了庸俗的类switch的代码,和java有点格格不入.主要是和想VB版的MyERP代码结构保持一些相似性.
	 * 
	 * @param op
	 *            计算符.
	 * @param paras
	 *            只有boolean, double, String三种类型的参数数组.
	 * @return 只有boolean, double, String三种类型的返回值.
	 * @throws ParseException
	 */
	private static Object evalWithoutNull(String op, Object[] paras)
			throws ParseException {
		String opT = op;
		if ("+".equals(opT)) {
			return add(paras);
		} else if ("-".equals(opT)) {
			return plus(paras);
		} else if ("^".equals(opT) || "*".equals(opT) || "/".equals(opT)) {
			return numEval(opT, paras);
		} else if ("&".equals(opT)) {
			return concat(paras);
		} else if ("=".equals(opT) || "<".equals(opT) || ">".equals(opT)
				|| "<=".equals(opT) || ">=".equals(opT) || "<>".equals(opT)) {
			return compare(opT, paras);
		} else {
			throw new ParseException(ParseException.Err_Code);
		}
	}

	/**
	 * "&"计算
	 * 
	 * @param paras
	 *            两个可转化为字符串类型的参数.
	 * @return 两个字符串相连的结果.
	 * @throws ParseException
	 */
	private static String concat(Object[] paras) throws ParseException {
		if (paras.length != 2)
			throw new ParseException(ParseException.Err_Paras_Number);
		if (paras[0] instanceof java.util.Date || paras[1] instanceof java.util.Date) {
			// 日期特殊处理 by zhouxw 20090105
			Object v0 = paras[0];
			Object v1 = paras[1];
			if (v0 instanceof java.util.Date)
				v0 = ExpTypeConvert.toString(paras[0]);
			if (v1 instanceof java.util.Date)
				v1 = ExpTypeConvert.toString(paras[1]);
			// return TypeConvert.getString(paras[0])
			// + TypeConvert.getString(paras[1]);
			return "" + v0 + v1;
		} else {
			return ExpTypeConvert.getString(paras[0]) + ExpTypeConvert.getString(paras[1]);
		}
	}

	/**
	 * 比较运算符"=","<",">","<=",">=","<>"的计算
	 * 
	 * @param op
	 *            比较运算符"=","<",">","<=",">=","<>"
	 * @param paras
	 *            参数数组
	 * @return boolean类型的返回值
	 * @throws ParseException
	 */
	private static boolean compare(String op, Object[] paras)
			throws ParseException {
		if (paras.length == 2) {
			if (paras[0] instanceof java.util.Date && paras[1] instanceof java.util.Date) {
				// for: DATE() < > >= <= <>
				// 日期特殊处理 by zhouxw 20090107 比较
				// if (paras[0] instanceof java.util.Date && TypeConvert.canGetDouble(paras[1])) {
				// return TypeConvert.toInt(paras[0]) + TypeConvert.toDouble(paras[1]);
				// } else if (paras[1] instanceof java.util.Date && TypeConvert.canGetDouble(paras[0])) {
				// return TypeConvert.toInt(paras[1]) + TypeConvert.toDouble(paras[0]);
				//
				// Date d1 = ((Date) paras[0]);
				// Date d2 = ((Date) paras[1]);
				// // if ((d1.getHours() == 0 && d1.getMinutes() == 0 && d1.getSeconds() == 0)
				// // || (d2.getHours() == 0 && d2.getMinutes() == 0 && d2.getSeconds() == 0))
				// try { // 这样无法精确比时间了
				// DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				// d1 = DateUtil.getDate(dt.format(d1));
				// d2 = DateUtil.getDate(dt.format(d2));
				// return (!d1.before(d2) && !d1.after(d2)) || d1.after(d2);
				// } catch (java.text.ParseException e) {
				// throw new RuntimeException(e);
				// }
				int lhs = ExpTypeConvert.toInt(paras[0]);
				int rhs = ExpTypeConvert.toInt(paras[1]);
				if (op.equals("=")) {
					return lhs == rhs;
				} else if (op.equals("<")) {
					return lhs < rhs;
				} else if (op.equals(">")) {
					return lhs > rhs;
				} else if (op.equals("<=")) {
					return lhs <= rhs;
				} else if (op.equals(">=")) {
					return lhs >= rhs;
				} else if (op.equals("<>")) {
					return lhs != rhs;
				}
			}
			if (!ExpTypeConvert.isDouble(paras[0]) || !ExpTypeConvert.isDouble(paras[1])) {
				// 两个参数只要有一个不能转化为double类型,进行字符串比较.
				String lhs = paras[0] == null ? "" : ExpTypeConvert.getString(paras[0]);
				String rhs = paras[1] == null ? "" : ExpTypeConvert.getString(paras[1]);
				// TODO: 这里的字符串比较没有充分考虑,特别是否要区分大小写
				if (op.equals("=")) {
					return lhs.equals(rhs);
				} else if (op.equals("<")) {
					return lhs.compareTo(rhs) < 0;
				} else if (op.equals(">")) {
					return lhs.compareTo(rhs) > 0;
				} else if (op.equals("<=")) {
					return lhs.compareTo(rhs) <= 0;
				} else if (op.equals(">=")) {
					return lhs.compareTo(rhs) >= 0;
				} else if (op.equals("<>")) {
					return !lhs.equals(rhs);
				}
			} else {
				Double lhs = ExpTypeConvert.getDouble(paras[0]);
				Double rhs = ExpTypeConvert.getDouble(paras[1]);
				if (op.equals("=")) {
					return lhs.equals(rhs);
				} else if (op.equals("<")) {
					return lhs < rhs;
				} else if (op.equals(">")) {
					return lhs > rhs;
				} else if (op.equals("<=")) {
					return lhs <= rhs;
				} else if (op.equals(">=")) {
					return lhs >= rhs;
				} else if (op.equals("<>")) {
					return lhs != rhs;
				}
			}
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * 单目运算符"-",和双目运算符"-"
	 * 
	 * @param paras
	 *            参数数组.
	 * @return double类型的计算结果.
	 * @throws ParseException
	 */
	private static Object plus(Object[] paras) throws ParseException {
		if (paras.length == 2) {
			// 双目运算符"+"
			if (paras[0] instanceof java.util.Date || paras[1] instanceof java.util.Date) {
				// for: DATE()-1
				// 日期特殊处理 by zhouxw 20090107 -号只做数字相减
				// if (paras[0] instanceof java.util.Date && TypeConvert.canGetDouble(paras[1])) {
				// return TypeConvert.toInt(paras[0]) + TypeConvert.toDouble(paras[1]);
				// } else if (paras[1] instanceof java.util.Date && TypeConvert.canGetDouble(paras[0])) {
				// return TypeConvert.toInt(paras[1]) + TypeConvert.toDouble(paras[0]);
				// }
				return ExpTypeConvert.toInt(paras[0]) - ExpTypeConvert.toInt(paras[1]);
			}
			// // 双目运算符"-"
			// if (!TypeConvert.canGetDouble(paras[0]) || !TypeConvert.canGetDouble(paras[1])) {
			// throw new ParseException(ParseException.Err_Para_Type); //
			// 两个参数只要有一个不能转化为double类型,进行字符串计算.
			// } else {
			// return TypeConvert.getDouble(paras[0]) - TypeConvert.getDouble(paras[1]);
			// }
			else if ((paras[0] instanceof String || paras[1] instanceof String)
				|| (!ExpTypeConvert.isDouble(paras[0]) || !ExpTypeConvert.isDouble(paras[1]))) {
				// for: FORMAT(DATE(),{YYYYMMDD})-1
				// 1.两个参数都是字符串类型,进行字符串计算.
				// 2.两个参数只要有一个不能转化为double类型,也进行字符串计算.
				if (paras[1] instanceof Date) {
					if (StringUtil.isNumeric(paras[0])) {
						return ExpTypeConvert.toDate(ExpTypeConvert.toInt(java.sql.Date.valueOf(paras[1].toString()))
							- Double.parseDouble(paras[0].toString()));
					}
				} else if (paras[0] instanceof Date) {
					if (StringUtil.isNumeric(paras[1])) {
						return ExpTypeConvert.toDate(ExpTypeConvert.toInt(java.sql.Date.valueOf(paras[0].toString()))
							- Double.parseDouble(paras[1].toString()));
					}
				}
				return ExpTypeConvert.getDouble(paras[0]) - ExpTypeConvert.getDouble(paras[1]);
			} else
				return ExpTypeConvert.getDouble(paras[0]) - ExpTypeConvert.getDouble(paras[1]);
		} else if (paras.length == 1) {
			// 单目运算符"+",只能处理数值类型.
			if (!ExpTypeConvert.isDouble(paras[0])) {
				throw new ParseException(ParseException.Err_Para_Type);
			}
			return -ExpTypeConvert.getDouble(paras[0]);
		} else {
			throw new ParseException(ParseException.Err_Code);
		}
	}

	/**
	 * 单目运算符"+",和双目运算符"+"
	 * 
	 * @param paras
	 *            参数数组.
	 * @return double或String类型的计算结果.
	 * @throws ParseException
	 */
	private static Object add(Object[] paras) throws ParseException {
		if (paras.length == 2) {
			// 双目运算符"+"
			if (paras[0] instanceof java.util.Date || paras[1] instanceof java.util.Date) {
				// for: DATE()+1
				// 日期特殊处理 by zhouxw 20090105 +号只做数字相加
				// if (paras[0] instanceof java.util.Date && TypeConvert.canGetDouble(paras[1])) {
				// return TypeConvert.toInt(paras[0]) + TypeConvert.toDouble(paras[1]);
				// } else if (paras[1] instanceof java.util.Date && TypeConvert.canGetDouble(paras[0])) {
				// return TypeConvert.toInt(paras[1]) + TypeConvert.toDouble(paras[0]);
				// }
				return ExpTypeConvert.toInt(paras[0]) + ExpTypeConvert.toInt(paras[1]);
			} else if ((paras[0] instanceof String || paras[1] instanceof String)
					|| (!ExpTypeConvert.isDouble(paras[0]) || !ExpTypeConvert.isDouble(paras[1]))) {
				// for: FORMAT(DATE(),{YYYYMMDD})+1
				// 1.两个参数都是字符串类型,进行字符串计算.
				// 2.两个参数只要有一个不能转化为double类型,也进行字符串计算.
				if (paras[1] instanceof Date) {
					if (StringUtil.isNumeric(paras[0])) {
						return ExpTypeConvert.toDate(ExpTypeConvert.toInt(java.sql.Date.valueOf(paras[1].toString()))
							+ Double.parseDouble(paras[0].toString()));
					}
				} else if (paras[0] instanceof Date) {
					if (StringUtil.isNumeric(paras[1])) {
						return ExpTypeConvert.toDate(ExpTypeConvert.toInt(java.sql.Date.valueOf(paras[0].toString()))
							+ Double.parseDouble(paras[1].toString()));
					}
				}
				// 严格区分+和&(+不再支持字符串连接了)
				// return TypeConvert.getString(paras[0]) + TypeConvert.getString(paras[1]);
				return ExpTypeConvert.getDouble(paras[0]) + ExpTypeConvert.getDouble(paras[1]);
			} else
				return ExpTypeConvert.getDouble(paras[0]) + ExpTypeConvert.getDouble(paras[1]);
		} else if (paras.length == 1) {
			// 单目运算符"+",只能处理数值类型.
			if (!ExpTypeConvert.isDouble(paras[0])) {
				throw new ParseException(ParseException.Err_Para_Type);
			}
			return ExpTypeConvert.getDouble(paras[0]);
		} else {
			throw new ParseException(ParseException.Err_Code);
		}
	}

	/**
	 * 数值的"^","*","/"计算
	 * 
	 * @param op
	 *            "^","*"或"/"
	 * @param paras
	 *            boolean, double, String类型的参数类型
	 * @return double类型的计算结果
	 * @throws ParseException
	 */
	private static double numEval(String op, Object[] paras)
			throws ParseException {
		if (paras.length == 2) {
			if (!ExpTypeConvert.isDouble(paras[0]) || !ExpTypeConvert.isDouble(paras[1])) {
				throw new ParseException(ParseException.Err_Para_Type); // 两个参数只要有一个不能转化为double类型,进行字符串计算.
			} else {
				if (op.equals("^")) {
					if (ExpTypeConvert.getDouble(paras[0]) < 0) {
						throw new ParseException(ParseException.Err_Para_Type, "第一个参数需要>=0");
					}
					return java.lang.Math.pow(ExpTypeConvert.getDouble(paras[0]), ExpTypeConvert.getDouble(paras[1]));
				} else if (op.equals("*")) {
					return ExpTypeConvert.getDouble(paras[0]) * ExpTypeConvert.getDouble(paras[1]);
				} else if (op.equals("/")) {
					return ExpTypeConvert.getDouble(paras[0]) / ExpTypeConvert.getDouble(paras[1]);
				}
			}
		}
		throw new ParseException(ParseException.Err_Code);
	}
}