package haiyan.exp;

/**
 * @author zhouxw
 */
class ExpFunShortCircuit {

	private ExpFunShortCircuit() {
	}

	static Object eval(SimpleNode node, ParserVisitor visitor, Object data)
			throws ParseException {
		String f = node.text;
		if ("if".equalsIgnoreCase(f) || "case".equalsIgnoreCase(f) || "switch".equalsIgnoreCase(f)) {
			return ifFun(node, visitor, data);
		} else if ("and".equalsIgnoreCase(f)) {
			return and(node, visitor, data);
		} else if ("or".equalsIgnoreCase(f)) {
			return or(node, visitor, data);
		}
		throw new ParseException(ParseException.Err_Code);
	}

	//
	private static Object ifFun(SimpleNode node, ParserVisitor visitor,
			Object data) throws ParseException {
		// System.out.println("#node.jjtGetNumChildren()="
		// + node.jjtGetNumChildren());
		// if (node.jjtGetNumChildren() != 3)
		// throw new ParseException(ParseException.Err_Paras_Number);
		// if (node.jjtGetNumChildren() < 3)
		// return new ExpNull();
		// if (node.jjtGetNumChildren() % 2 != 0)
		// throw new ParseException(ParseException.Err_Paras_Number,
		// "IF公式参数个数错误，应该为2的倍数");
		for (int i = 0; i < node.children.length; i += 2) {
			// Object p1 = node.children[i].jjtAccept(visitor, data);
			Object p1 = node.children[i].jjtAccept(visitor, data);
			Object p2 = true;
			if (i == node.children.length - 1) { // 奇数取最后的奇数结果
				return p1;
			} else {
				if (ExpTypeConvert.getBoolean(p1) == true) {
					p2 = node.children[i + 1].jjtAccept(visitor, data);
					return p2;
				}
				// if (p2 instanceof Boolean && false == (Boolean) p2)
				// return false;
				// else
				// return p2;
			}
			// return p1;
		}
		return true;
	}

	//
	private static boolean and(SimpleNode node, ParserVisitor visitor,
			Object data) throws ParseException {
		// if (node.jjtGetNumChildren() == 0)
		// throw new ParseException(ParseException.Err_Paras_Number);
		if (node.jjtGetNumChildren() == 0)
			return true;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Object p1 = node.children[i].jjtAccept(visitor, data);
			if (!ExpTypeConvert.getBoolean(p1))
				return false;
		}
		return true;
	}

	//
	private static boolean or(SimpleNode node, ParserVisitor visitor,
			Object data) throws ParseException {
		// if (node.jjtGetNumChildren() == 0)
		// throw new ParseException(ParseException.Err_Paras_Number);
		if (node.jjtGetNumChildren() == 0)
			return false;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Object p1 = node.children[i].jjtAccept(visitor, data);
			if (ExpTypeConvert.getBoolean(p1))
				return true;
		}
		return false;
	}
}
