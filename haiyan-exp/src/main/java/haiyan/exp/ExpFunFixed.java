package haiyan.exp;

import haiyan.common.StringUtil;

/**
 * @author zhouxw
 */
class ExpFunFixed {

	private ExpFunFixed() {
	}

	/**
     * 
     */
	static Object eval(String f, Object[] paras) throws Throwable {
		if ("Abs".equalsIgnoreCase(f) || "Char".equalsIgnoreCase(f)
			|| "Int".equalsIgnoreCase(f) || "Dbl".equalsIgnoreCase(f)
			|| "Double".equalsIgnoreCase(f) || "RmbDx".equalsIgnoreCase(f)
			|| "Sign".equalsIgnoreCase(f)) {
			return evalDouble(f, paras);
		} else if ("Round".equalsIgnoreCase(f) || "Trunc".equalsIgnoreCase(f)) {
			return roundOrTrunc(f, paras);
		} else if ("Code".equalsIgnoreCase(f)) {
			return code(paras);
		} else if ("Exact".equalsIgnoreCase(f)) {
			return exact(paras);
		} else if ("Find".equalsIgnoreCase(f) || "FindB".equalsIgnoreCase(f)) {
			return find(f, paras);
		} else if ("Left".equalsIgnoreCase(f) || "LeftB".equalsIgnoreCase(f)
				|| "Right".equalsIgnoreCase(f) || "RightB".equalsIgnoreCase(f)) {
			return leftOrRight(f, paras);
		} else if ("Len".equalsIgnoreCase(f) || "LenB".equalsIgnoreCase(f)) {
			return len(f, paras);
		} else if ("Sub".equalsIgnoreCase(f) || "Mid".equalsIgnoreCase(f)
				|| "MidB".equalsIgnoreCase(f)) {
			return mid(f, paras);
		} else if ("Mod".equalsIgnoreCase(f)) {
			return mod(paras);
		} else if ("Not".equalsIgnoreCase(f)) {
			return not(paras);
		}
		throw new ParseException(ParseException.Err_Code);
	}
	
	/**
	 * @param f
	 * @param paras
	 * @return Object
	 * @throws ParseException
	 */
	private static Object evalDouble(String f, Object[] paras)
			throws Throwable {
		if (paras.length != 1)
			throw new ParseException(ParseException.Err_Paras_Number);
		if (StringUtil.isEmpty(paras[0]))
			return 0;
		if (!ExpTypeConvert.isDouble(paras[0]))
			throw new ParseException(ParseException.Err_Para_Type);
		Double d = ExpTypeConvert.getDouble(paras[0]);
		if ("Abs".equalsIgnoreCase(f)) {
			return java.lang.Math.abs(d);//
		} else if ("Char".equalsIgnoreCase(f)) {
			return charFun(d);
		} else if ("Int".equalsIgnoreCase(f)) {
			return (double) (d.intValue()); //
		} else if ("RmbDx".equalsIgnoreCase(f)) {
			return ExpTypeConvert.toRmb(d); //
		} else if ("Sign".equalsIgnoreCase(f)) {
			return java.lang.Math.signum(d);
		} else if ("Dbl".equalsIgnoreCase(f) || "Double".equalsIgnoreCase(f)) {
			return d;
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * @param d
	 * @return String
	 * @throws ParseException
	 */
	private static String charFun(Double d) throws Throwable {
		// System.out.println("#EvalFunFixed.charFun.value=" + d);
		// int i = d.intValue();
		// if (i < 0 || i > (2 << 16 - 1))
		// throw new ParseException(ParseException.Err_Para_Type);
		// char[] cs = { (char) i };
		// return new String(cs);
		return d.toString();
	}

	/**
	 * @param paras
	 * @return double
	 * @throws ParseException
	 */
	private static double code(Object[] paras) throws Throwable {
		if (paras.length != 1)
			throw new ParseException(ParseException.Err_Paras_Number);
		String p = ExpTypeConvert.getString(paras[0]);
		if (p.length() == 0)
			throw new ParseException(ParseException.Err_Para_Type);
		return p.charAt(0);
	}

	/**
	 * @param paras
	 * @return boolean
	 * @throws ParseException
	 */
	private static boolean exact(Object[] paras) throws Throwable {
		if (paras.length != 2)
			throw new ParseException(ParseException.Err_Paras_Number);
		return ExpTypeConvert.getString(paras[0]).equals(ExpTypeConvert.getString(paras[1]));
	}

	/**
	 * @param f
	 * @param paras
	 * @return double
	 * @throws ParseException
	 */
	private static double find(String f, Object[] paras) throws Throwable {
		if (paras.length < 2 || paras.length > 3)
			throw new ParseException(ParseException.Err_Para_Type);
		String s1 = ExpTypeConvert.getString(paras[0]);
		String s2 = ExpTypeConvert.getString(paras[1]);
		int start = 0;
		if (paras.length == 3) {
			if (ExpTypeConvert.getString(paras[2]).length() != 0)
				start = ExpTypeConvert.getDouble(paras[2]).intValue() - 1;
		}
		if ("Find".equalsIgnoreCase(f)) {
			Integer pos = s1.indexOf(s2, start) + 1;
			return pos.doubleValue();
		} else if ("FindB".equalsIgnoreCase(f)) {
			Integer pos = s1.indexOf(s2, start / 2) + 1;
			return ((Integer) (pos * 2 - 1)).doubleValue();
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * @param f
	 * @param paras
	 * @return String
	 * @throws ParseException
	 */
	private static String leftOrRight(String f, Object[] paras)
			throws Throwable {
		if (paras.length != 2)
			throw new ParseException(ParseException.Err_Para_Type);
		String s = ExpTypeConvert.getString(paras[0]);
		if (!ExpTypeConvert.isDouble(paras[1]))
			throw new ParseException(ParseException.Err_Para_Type);
		int len = ExpTypeConvert.getDouble(paras[1]).intValue();
		if (len < 0 || len > s.length())
			throw new ParseException(ParseException.Err_Para_Type);
		if ("Left".equalsIgnoreCase(f)) {
			return s.substring(0, len);
		} else if ("LeftB".equalsIgnoreCase(f)) {
			return s.substring(0, len / 2);
		} else if ("Right".equalsIgnoreCase(f)) {
			return s.substring(s.length() - len);
		} else if ("RightB".equalsIgnoreCase(f)) {
			return s.substring(s.length() - len / 2);
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * @param f
	 * @param paras
	 * @return double
	 * @throws ParseException
	 */
	private static double len(String f, Object[] paras) throws Throwable {
		if (paras.length != 1)
			throw new ParseException(ParseException.Err_Para_Type);
		String s = ExpTypeConvert.getString(paras[0]);
		if ("Len".equalsIgnoreCase(f)) {
			return (double) (s.length());
		} else if ("LenB".equalsIgnoreCase(f)) {
			return (double) (s.length() * 2);
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * @param f
	 * @param paras
	 * @return String
	 * @throws ParseException
	 */
	private static String mid(String f, Object[] paras) throws Throwable {
		if (paras.length != 3)
			throw new ParseException(ParseException.Err_Para_Type);
		String s = ExpTypeConvert.getString(paras[0]);
		if (!ExpTypeConvert.isDouble(paras[1])
				|| !ExpTypeConvert.isDouble(paras[2]))
			throw new ParseException(ParseException.Err_Para_Type);
		int start = ExpTypeConvert.getDouble(paras[1]).intValue();
		int len = ExpTypeConvert.getDouble(paras[2]).intValue();
		if (start < 0 || len < 0)
			throw new ParseException(ParseException.Err_Para_Type);
		if ("Mid".equalsIgnoreCase(f) || "Sub".equalsIgnoreCase(f)) {
			if (len + start > s.length())
				throw new ParseException(ParseException.Err_Para_Type);
			start = start - 1; //
			return s.substring(start, start + len);
		} else if ("MidB".equalsIgnoreCase(f)) {
			if (len / 2 + start / 2 > s.length())
				throw new ParseException(ParseException.Err_Para_Type);
			start = start - 1;
			return s.substring(start / 2, start / 2 + len / 2);
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * @param f
	 * @param paras
	 * @return double
	 * @throws ParseException
	 */
	private static double roundOrTrunc(String f, Object[] paras)
			throws Throwable {
		if ((paras.length == 0) || (paras.length > 2))
			throw new ParseException(ParseException.Err_Paras_Number);
		if (!ExpTypeConvert.isDouble(paras[0]))
			throw new ParseException(ParseException.Err_Para_Type);
		double d = ExpTypeConvert.getDouble(paras[0]);
		int i = 0; //
		if (paras.length == 2 && !("".equals(paras[1]))) {
			if (!ExpTypeConvert.isDouble(paras[1]))
				throw new ParseException(ParseException.Err_Para_Type);
			else
				i = ((Double) ExpTypeConvert.getDouble(paras[1])).intValue();
		}
		double tenPowerI = java.lang.Math.pow(10.0, (double) i);
		if ("Round".equalsIgnoreCase(f)) {
			double dTemp = d * tenPowerI + 0.5 * java.lang.Math.signum(d);
			dTemp = dTemp > 0 ? Math.floor(dTemp) : Math.ceil(dTemp);
			return dTemp / tenPowerI;
		} else if ("Trunc".equalsIgnoreCase(f)) {
			double dTemp = d * tenPowerI;
			dTemp = dTemp > 0 ? Math.floor(dTemp) : Math.ceil(dTemp);
			return dTemp / tenPowerI;
		}
		throw new ParseException(ParseException.Err_Code);
	}

	/**
	 * @param paras
	 * @return double
	 * @throws ParseException
	 */
	private static double mod(Object[] paras) throws Throwable {
		if (paras.length != 2)
			throw new ParseException(ParseException.Err_Paras_Number);
		if (!ExpTypeConvert.isDouble(paras[0]) || !ExpTypeConvert.isDouble(paras[1]))
			throw new ParseException(ParseException.Err_Para_Type);
		return ExpTypeConvert.getDouble(paras[0]) % ExpTypeConvert.getDouble(paras[1]);
	}

	/**
	 * @param paras
	 * @return boolean
	 * @throws ParseException
	 */
	private static boolean not(Object[] paras) throws Throwable {
		if (paras.length != 1)
			throw new ParseException(ParseException.Err_Paras_Number);
		return !ExpTypeConvert.getBoolean(paras[0]);
	}
}
