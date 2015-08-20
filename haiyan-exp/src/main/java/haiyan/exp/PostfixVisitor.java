package haiyan.exp;

/**
 * @author suddenzhou
 * 
 */
public class PostfixVisitor implements ParserVisitor {

	private Boolean bFirstPara = true;

	public Object visit(SimpleNode node, Object data) throws Throwable {
		return null;
	}

	public Object visit(CTreeRoot node, Object data) throws Throwable {
		// StringBuffer sb = (StringBuffer) data;
		// sb.append("(");
		bFirstPara = true;
		// sb = (StringBuffer)node.childrenAccept(this, data);
		node.childrenAccept(this, data);
		// sb.append(")");
		return data;
	}

	public Object visit(CTreeBinOp node, Object data) throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		sb.append(node.text);
		sb.append("(");
		bFirstPara = true;
		sb = ((StringBuffer) node.childrenAccept(this, data));
		sb.append(")");
		bFirstPara = false;
		return data;
	}

	public Object visit(CTreeBoolConstrant node, Object data)
			throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		bFirstPara = false;
		sb.append("B_");
		sb.append(node.text);
		return data;
	}

	public Object visit(CTreeNullConstrant node, Object data)
			throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		bFirstPara = false;
		sb.append("NULL");
		return data;
	}

	public Object visit(CTreeVar node, Object data) throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		bFirstPara = false;
		sb.append("V_");
		sb.append(node.text);
		return data;
	}

	public Object visit(CTreeNumConstrant node, Object data)
			throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		bFirstPara = false;
		sb.append("N_");
		sb.append(node.text);
		return data;
	}

	public Object visit(CTreeStrConstant node, Object data) throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		bFirstPara = false;
		sb.append("C_");
		sb.append(node.text);
		return data;
	}

	public Object visit(CTreeFunOp node, Object data) throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		sb.append(node.text);
		sb.append("(");
		bFirstPara = true;
		sb = ((StringBuffer) node.childrenAccept(this, data));
		sb.append(")");
		bFirstPara = false;
		return data;
	}

	public Object visit(CTreeFunShortCircuit node, Object data)
			throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		sb.append("S_");
		sb.append(node.text);
		sb.append("(");
		bFirstPara = true;
		sb = ((StringBuffer) node.childrenAccept(this, data));
		sb.append(")");
		bFirstPara = false;
		return data;
	}

	public Object visit(CTreeFunFixed node, Object data) throws Throwable {
		StringBuffer sb = (StringBuffer) data;
		if (!bFirstPara)
			sb.append(",");
		sb.append("F_");
		sb.append(node.text);
		sb.append("(");
		bFirstPara = true;
		sb = ((StringBuffer) node.childrenAccept(this, data));
		sb.append(")");
		bFirstPara = false;
		return data;
	}

}
