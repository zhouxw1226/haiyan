package haiyan.exp;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
class ExpVisitor implements ParserVisitor {

	private IExpContext ExpContext;

	ExpVisitor(IExpContext newExpContext) {
		this.ExpContext = newExpContext;
	}

	public Object visit(SimpleNode node, Object data) throws Throwable {
		throw new ParseException(ParseException.Err_Code);
	}

	public Object visit(CTreeRoot node, Object data) throws Throwable {
		return node.children[0].jjtAccept(this, data);
	}

	public Object visit(CTreeBinOp node, Object data) throws Throwable {
		Object[] paras = getParas(node, data);
		return ExpFunBin.eval(node.text, paras);
	}

	private Object[] getParas(SimpleNode node, Object data)
			throws Throwable {
		Object[] paras = new Object[node.jjtGetNumChildren()];
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			paras[i] = node.children[i].jjtAccept(this, data);
		}
		return paras;
	}

	public Object visit(CTreeBoolConstrant node, Object data)
			throws Throwable {
		return node.getValue();
	}

	public Object visit(CTreeNullConstrant node, Object data)
			throws Throwable {
		return ExpCore.getNull();
	}

	@SuppressWarnings("unchecked")
	public Object visit(CTreeVar node, Object data) throws Throwable {
//		try {
			String sFormula = node.text;
			// if (node.text.equalsIgnoreCase("CurValue")
			// || node.text.equalsIgnoreCase("OldValue")) {
			// sFormula = node.text;
			// } else {
			// sFormula = "Value";
			// }
			// IFunctionEval oData = null;
			// IFunctionEval funImpl = pGetFormulaObject((IFunctionEval)
			// ExpContext,
			// sFormula, oData);
			// IFunctionEval funImpl = ExpContext.getImplInstance(sFormula);
			IExpFunction funImpl = getImpInstance(sFormula);
			if (funImpl != null && !"undefined".equalsIgnoreCase(sFormula)) {
				// return funImpl.eval(node.text, null);
				Object[] paras = new Object[1];
				paras[0] = node.text;
				return funImpl.eval(ExpContext, sFormula, paras);
			}
//		} catch (Throwable ex) {
//			throw Warning.getWarning(ex);
//		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Object visit(CTreeFunOp node, Object data) throws Throwable {
//		try {
			Object[] paras = getParas(node, data);
			// IFunctionEval funImpl = ExpContext.getImplInstance(node.text);
			IExpFunction funImpl = getImpInstance(node.text);
			if (funImpl != null) {
				return funImpl.eval(ExpContext, node.text, paras);
			}
//		} catch (Throwable ex) {
//			throw Warning.getWarning(ex);
//		}
		return true;
	}

	public Object visit(CTreeNumConstrant node, Object data)
			throws Throwable {
		return node.getValue();
	}

	public Object visit(CTreeStrConstant node, Object data) throws Throwable {
		return node.getValue();
	}

	public Object visit(CTreeFunShortCircuit node, Object data)
			throws Throwable {
		return ExpFunShortCircuit.eval(node, this, data);
	}

	public Object visit(CTreeFunFixed node, Object data) throws Throwable {
		Object[] paras = getParas(node, data);
		return ExpFunFixed.eval(node.text, paras);
	}
	private static Map<String, IExpFunction> CACHE = new HashMap<String, IExpFunction>();
	private IExpFunction getImpInstance(String sFormula) throws Throwable {
		if (CACHE.containsKey(sFormula))
			return CACHE.get(sFormula);
		IExpFunction funImpl = null;
		IExpContext oExpContext = ExpContext;
		while (oExpContext != null) {
			// 默认implinstance
			funImpl = oExpContext.getImplInstance(sFormula);
			if (funImpl != null) {
				break;
			}
//			扩展implinstance
//			String[] extendClz = oExpContext.getExtendImpClass();
//			if (extendClz != null && extendClz.length > 0) {
//				for (int l = 0; l < extendClz.length; l++) {
//					funImpl = oExpContext.getExtendImpInstance(extendClz[l], sFormula);
//					if (funImpl != null) {
//						break;
//					}
//				}
//			}
//			if (funImpl != null) {
//				break;
//			}
			oExpContext = oExpContext.getNextLayer();
		}
		if (funImpl != null) {
			CACHE.put(sFormula, funImpl);
		}
		return funImpl;
	}
}
