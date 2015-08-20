package haiyan.exp;

import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.exp.IFunction;
import haiyan.common.intf.session.IContext;
import haiyan.common.session.AppContext;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author suddenzhou
 * 
 */
public class ExpUtil implements IExpUtil {

	public static final String TABLE = "__exp_table";
	public static final String RECORD = "__exp_record";
	public static final String ITEM = "__exp_item";
	public static final String IDS = "__exp_ids";
	public static final String DATAS = "__exp_datas";
	public static final String RESULT = "__exp_result";
	// ------------------------------------------------ //
	public static final String BILLNAME = "__exp_billname";
	public static final String WFEVARFORM = "__exp_wfvarform";
	// ------------------------------------------------ //
	public static final void evalGlobal(String formula) throws Throwable {
		IContext context = new AppContext();
		try {
			IExpUtil exp = new ExpUtil(context);
			context.setExpUtil(exp);
			exp.evalExp(formula);
		}finally{
			CloseUtil.close(context);
		}
	}
	public static final void evalGlobal(String formula, Table table) throws Throwable {
		IContext context = new AppContext();
		try {
			IExpUtil exp = new ExpUtil(context, table);
			context.setExpUtil(exp);
			exp.evalExp(formula);
		}finally{
			CloseUtil.close(context);
		}
	}
	// ------------------------------------------------------------------------------ //
	private IExpContext<IContext> expContext;
	/**
	 * @param param
	 * @return boolean
	 */
	public static boolean isFormula(String param) {
		return param != null
				&& (param.startsWith("^") || param.startsWith("?")
				 || param.startsWith("=") || param.startsWith("$"));
	}
//	/** 
//	 * @param expContextInstance
//	 */
//	public ExpUtil(IExpContext<IContext> expContextInstance) {
//		this.expContext = expContextInstance;
//	}
	/**
	 */
	public ExpUtil() {
		this.expContext = new ExpContextImpl();
	}
	/**
	 * @param context
	 */
	public ExpUtil(IContext context) {
		this.expContext = new ExpContextImpl(context);
	}
	/**
	 * @param context
	 * @param table
	 */
	public ExpUtil(IContext context, Table table) {
		this.expContext = new ExpContextImpl(context, table);
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 */
	public ExpUtil(IContext context, Table table, IDBRecord record) {
		this.expContext = new ExpContextImpl(context, table, record);
	}
	/**
	 * @param context
	 * @param table
	 * @param ids
	 */
	public ExpUtil(IContext context, Table table, String[] ids) {
		this.expContext = new ExpContextImpl(context, table, ids);
	}
	/**
	 * @param context
	 * @param table
	 * @param datas
	 */
	public ExpUtil(IContext context, Table table, ArrayList<IDBRecord> datas) {
		this.expContext = new ExpContextImpl(context, table, datas);
	}
	/**
	 * @param context
	 * @param table
	 * @param datas
	 */
	public ExpUtil(IContext context, Table table, IDBResultSet records) {
		this.expContext = new ExpContextImpl(context, table, records);
	}
	/**
	 * @param key
	 * @param value
	 */
	public void setParameter(String key, Object value) {
		this.expContext.setParameter(key, value);
	}
	/**
	 * @param key
	 * @return value
	 */
	public Object getParameter(String key) {
		return this.expContext.getParameter(key);
	}
	/**
	 * @param key
	 */
	public Object removeParameter(String key) {
		return this.expContext.removeParameter(key);
	}
    /**
     * @param f
     */
    public void setForm(IDBRecord f) {
        this.expContext.setParameter(RECORD, f);
    }
    /**
     * @param datas
     */
    public void setDatas(ArrayList<IDBRecord> datas) {
        this.expContext.setParameter(DATAS, datas);
    }
    @Override
	public Object evalExp(String exp) throws Throwable {
		if (exp == null)
			return null;
//		// String[] ids = (String[])this.expContextInstance.getParameter(IDS);
//		if (ids != null) {
//			IContext context = expContext.getContext();
//			if (context instanceof ITableContext) {
//				ITableContext tableContext = (ITableContext)context;
//				Table table = (Table) expContext.getParameter(ExpUtil.TABLE);
//				String batch = (String)tableContext.getAttribute("__batch");
//				if ("1".equals(batch) || ids.length==0) {
//					IRecord r = tableContext.getDBM().createRecord();
//					r.set(table.getId().getName(), ids);
//					expContext.setParameter(RECORD, r);
//					return evalExpRecord(exp);
//				} else {
//					IRecord r;
//					for (String id : ids) {
//						r = tableContext.getDBM().select(tableContext, table, id);
//						if (r == null)
//							r = tableContext.getDBM().createRecord();
//						expContext.setParameter(RECORD, r); // 逐行执行
//						if (ids.length==1)
//							return evalExpRecord(exp);
//						else
//							evalExpRecord(exp);
//					}
//				}
//			}
//			return true;
//		} else {
//			return evalExpRecord(exp);
//		}
		return pEvalExp(exp);
	}
	private Object pEvalExp(String exp) throws Throwable {
//		DebugUtil.debug("**** Exp.start:\t" + exp);
		exp = exp.trim();
		if (exp.startsWith(";"))
			exp = exp.substring(1);
		if (exp.endsWith(";"))
			exp = exp.substring(0, exp.length() - 1);
		Object res = ExpCore.getInstance().eval(expContext, exp);
//		String[] exps = StringUtil.split(exp, ";");
//		Object[] vars = new Object[exps.length];
//		expContext.setInnerVars(vars);
//		for (int i = 0; i < exps.length; i++) {
//			vars[i] = ExpCore.getInstance().eval(expContext, exps[i]);
//		}
//		Object res = vars[vars.length - 1];
		DebugUtil.debug("[**** Exp.end:\t"+exp+"\t计算结果\t"+res+" ****]");
		// Arrays.dd();
		// System.out.println("exp.dealed:" + res);
		// Exp.getInstance().eval(expContextInstance, expression);
		// System.out.println(o);
//		// vars = nul;
//		if (res instanceof String || res instanceof Double
//			|| res instanceof Integer || res instanceof java.util.Date)
//			DebugUtil.debug("--Exp.result:" + res);
		return res;
	}
//	private static HashMap<String, String> localFuncMap = null;
//	public static synchronized String byLocal(String localName) {
//		if (localFuncMap == null) {
//			localFuncMap = new HashMap<String, String>();
//			try {
//				// formula_lang-zh_CN_GB2312
//				Language lang = ConfigUtil.getLanguage();
//				ResourceBundle res = ConfigUtil.getPropertyBundle("formula_" + lang.getName());
//				Iterator<String> iter = res.keySet().iterator();
//				while (iter.hasNext()) {
//					String funcName = iter.next();
//					String localNameStr = res.getString(funcName);
//					String[] localNames = StringUtil.split(localNameStr, ";");
//					for (String LocalN : localNames) {
//						localFuncMap.put(LocalN, funcName);
//					}
//				}
//			} catch (Throwable e) {
//				// e.printStackTrace(); //ignore
//				DebugUtil.error(e);
//			}
//		}
//		return localFuncMap.get(localName);
//	}
	@Override
	public void close() {
		this.expContext.close();
		this.expContext = null;
	}
	private static Map<String, Method> CACHE = new HashMap<String, Method>();
	private static AtomicInteger inited = new AtomicInteger(0);
	@SuppressWarnings("rawtypes")
	private static void pInit() throws Throwable {
		if (inited.get()==0) {
			String[] classes = ConfigUtil.getFunctionScanClasses();
			for (String className:classes) {
				Class clz = Thread.currentThread().getContextClassLoader().loadClass(className.trim());
//				Annotation[] annos = clz.getDeclaredAnnotations();
//				for (Annotation anno:annos) {
//					System.out.println(anno);
//				}
				Method[] methods = clz.getDeclaredMethods();
				for (Method method:methods) {
					IFunction ifn = method.getAnnotation(IFunction.class);
					if (ifn==null || ifn.name()==null)
						continue;
					CACHE.put(ifn.name().toLowerCase(), method);
//					annos = method.getDeclaredAnnotations();
//					for (Annotation anno:annos) {
//						IFunction ifn = ((IFunction)anno);
//						System.out.println(ifn.getClass());
//						System.out.println(ifn.name());
//					}
				}
			}
			inited.addAndGet(1);
		}
	}
	/**
	 * @author suddenzhou
	 */
	private static class ExpContextImpl implements IExpContext<IContext> {

		private static final long serialVersionUID = 1L;
		// // 公式层次的上下文变量不会影响Session级别的context(IContext)里的变量
		// private transient HashMap<String, Object> paras = new HashMap<String, Object>();
		private transient Object[] vars; // 公式面变量
		private transient IContext context;
		// ////////////////////////////////////////
		public ExpContextImpl() {
		}
		public ExpContextImpl(IContext context) {
			this.context = context;
		}
		public ExpContextImpl(IContext context, Table table) {
			this.context = context;
			this.setParameter(TABLE, table);
		}
		public ExpContextImpl(IContext context, Table table, IDBRecord record) {
			this.context = context;
			this.setParameter(TABLE, table);
			this.setParameter(RECORD, record);
		}
		public ExpContextImpl(IContext context, Table table, String[] ids) {
			this.context = context;
			this.setParameter(TABLE, table);
			this.setParameter(IDS, ids);
		}
		public ExpContextImpl(IContext context, Table table, ArrayList<IDBRecord> datas) {
			this.context = context;
			this.setParameter(TABLE, table);
			this.setParameter(DATAS, datas);
		}
		public ExpContextImpl(IContext context, Table table, IDBResultSet records) {
			this.context = context;
			this.setParameter(TABLE, table);
			this.setParameter(RESULT, records);
		}
		@Override
		public IExpContext<IContext> getNextLayer() {
			return null;
		}
		@Override
		public IContext getContext() throws Throwable {
			return this.context;
		}
//		@Override
//		public ITableDBManager getDBM() throws Throwable {
//			if (this.context==null)
//				return null;
//			return this.context.getDBM();
//		}
//		@Override
//		public IBillDBManager getBBM() throws Throwable {
//			if (this.context==null)
//				return null;
//			return this.context.getBBM();
//		}
		@Override
		public void setParameter(String key, Object value) {
			if (this.context==null)
				return;
			this.context.setAttribute(key, value);
		}
		@Override
		public Object getParameter(String key) {
			if (this.context==null)
				return null;
			return this.context.getAttribute(key);
		}
		@Override
		public Object removeParameter(String key) {
			if (this.context==null)
				return null;
			Object o = this.getParameter(key);
			this.context.removeAttribute(key);
			return o;
		}
		@Override
		public void setInnerVars(Object[] vars) {
			this.vars = vars;
		}
		@Override
		public Object[] getInnerVars() {
			return this.vars;
		}
		@Override
		public void close() {
			this.vars = null;
			// NOTICE CloseUtil.close(this.context) 不用做最外层已经做了 直接=null
			this.context = null;
		}
		@Override
		public String[] getExtendImpClass() throws Throwable {
			return ConfigUtil.getFunctionScanClasses();
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public IExpFunction<IContext> getImplInstance(String funcName)
				throws Throwable {
			String t = funcName;//byLocal(funcName);
			if (!StringUtil.isBlankOrNull(t))
				funcName = t;
			pInit();
			if (CACHE.containsKey(funcName.toLowerCase())) {
				return new IExpFunction() { 
					private static final long serialVersionUID = 1L;
					@Override
					public Object eval(IExpContext context, String funcName, Object[] paras) throws Throwable { 
						Method m = CACHE.get(funcName.toLowerCase());
						Table table = (Table)context.getParameter(TABLE);
						IDBRecord record = (IDBRecord)context.getParameter(RECORD);
						String[] ids = (String[])context.getParameter(IDS);
						List<IDBRecord> datas = (List) context.getParameter(DATAS);
						IDBResultSet rst = (IDBResultSet) context.getParameter(RESULT);
						if (record!=null)
							return m.invoke(null, new Object[]{context.getContext(), table, (Object)record, paras});
						else if (ids!=null)
							return m.invoke(null, new Object[]{context.getContext(), table, (Object)ids, paras});
						else if (datas!=null)
							return m.invoke(null, new Object[]{context.getContext(), table, (Object)datas, paras});
						else if (rst!=null)
							return m.invoke(null, new Object[]{context.getContext(), table, (Object)rst, paras});
						else if (table!=null)
							return m.invoke(null, new Object[]{context.getContext(), table, (Object)null, paras});
						else if (context.getContext()!=null)
							return m.invoke(null, new Object[]{context.getContext(), (Table)null, (Object)null, paras});
						else
							return m.invoke(null, new Object[]{(IContext)null, (Table)null, (Object)null, paras});
					}
					
				};
			}
			return null;
		}
		public IExpFunction<IContext> getExtendImpInstance(String sExtendProg, String funcName) throws Throwable {
//			String t = funcName;//byLocal(funcName);
//			if (!StringUtil.isBlankOrNull(t))
//				funcName = t;
//			pInit();
//			if (CACHE.containsKey(funcName.toLowerCase())) {
//				return new IExpFunction() { 
//					private static final long serialVersionUID = 1L;
//					@Override
//					public Object eval(IExpContext context, String funcName,
//							Object[] paras) throws Throwable { 
//						Method m = CACHE.get(funcName.toLowerCase());
//						Table table = (Table)context.getParameter(TABLE);
//						IRecord record = (IRecord)context.getParameter(RECORD);
//						String[] ids = (String[])context.getParameter(IDS);
//						List<IRecord> datas = (List) context.getParameter(DATAS);
//						if (record!=null)
//							return m.invoke(null, new Object[]{context.getContext(), table, record, paras});
//						else if (ids!=null)
//							return m.invoke(null, new Object[]{context.getContext(), table, ids, paras});
//						else if (datas!=null)
//							return m.invoke(null, new Object[]{context.getContext(), table, datas, paras});
//						else if (table!=null)
//							return m.invoke(null, new Object[]{context.getContext(), table, paras});
//						else
//							return m.invoke(null, new Object[]{context.getContext(), paras});
//					}
//					
//				};
//			}
			return null;
//			String className = null;
//			try {
//				className = FORMULA_RESOURCE.getString(funcName);
//			} catch (Throwable e) {
//				DebugUtil.warn(e);
//			}
//			IExpFunction<IContext> fun = null;
//			if (!StringUtil.isBlankOrNull(className)) {
//				fun = (IExpFunction) InvokeUtil.newInstanceSingle(className);
//			}
//			return fun;
		}

	}

}

//// 核心测试
//class TestExpUtil extends TestCase {
//
//	static ExpUtil expUtil = null;
//
//	public static void main(String[] args) throws Throwable {
//		test();
//	}
//
//	private static void test() throws Throwable {
//		IRecord form = initTest();
//		String str3 = "BSH({CreatePONO.bsh})";
//		// str3 = "If(true,debug(1),true,debug(2))";
//		// str3 = "长度({1234567890})";
//		System.out.println(expUtil.evalExp(str3));
//		System.out.println(form.get("PONO"));
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str3)));
//
//		// // parseString("result:" + "aBS(LefT())");
//		// System.out.println(expUtil.evalExp("GetValue(HEADID)"));
//		// System.out.println(expUtil
//		// .evalExp("DBFindByPK(T_SCM_RKD,GetValue(HEADID))"));
//		// System.out.println(expUtil.evalExp("GetDbl(SJRKL)"));
//		// System.out.println(expUtil
//		// .evalExp("GetDbl(DBFindByPK(T_SCM_RKD,GetValue(HEADID)),RKL)"));
//		// System.out.println(expUtil.evalExp("GetDbl(SJRKL)"));
//		// String strt = "&quot; and
//		// t_1.HEADID='&quot;&amp;GetValue(HEADID)&amp;&quot;'&quot;";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(strt)));
//		//
//		// String str0 = "FilterSum(T_SCM_RKD_D,SJRKL,&quot;and
//		// t_1.HEADID='&quot;&amp;GetValue(HEADID)&amp;&quot;'&quot;)";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str0)));
//		// String str1 = "GetDbl(DBFindByPK(T_SCM_RKD,GetValue(HEADID)),RKL)";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str1)));
//		// String str2 = "if(" + str0 + "&lt;" + str1
//		// + ",(&quot;正常&quot;),warning(&quot;大于入库单总数&quot;))";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str2)));
//		//
//		// String str3 = "ByIndex(DBFilter(T_SCM_KUCUN,&quot;and
//		// t_1.PRODUCT_ID='&quot;&amp;GetValue(PRODUCT_ID)&amp;&quot;'&quot;),0);";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str3)));
//		// String str4 =
//		// "SetValue(GetVar(0),XCL,GetDbl(GetVar(0),XCL)+GetDbl(SJRKL),KYL,GetDbl(GetVar(0),KYL)+GetDbl(SJRKL));";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str4)));
//		// String str5 = "DBUpdate(GetVar(1));";
//		// System.out.println(expUtil.evalExp(HtmlUtil.getHtmlUnSafeStr(str5)));
//		//
//		// System.out.println("result:"
//		// + expUtil.evalExp("GetDouble(\"MONEY\")+1"));
//		// System.out.println("result:"
//		// + expUtil.evalExp("Pow(GetDouble(\"MONEY\")+1,2)"));
//		// System.out.println("result:"
//		// + expUtil.evalExp("Rmb(Pow(GetDouble(\"MONEY\")+1,2))"));
//		// System.out
//		// .println("result:" + expUtil.evalExp("SetValue(\"MONEY\",0)"));
//		// System.out.println("result:"
//		// + expUtil.evalExp("if(1-0=0,true,or(false,false))"));
//	}
//
//	private static Object evalExp(String string) throws Throwable {
////		if (expUtil == null)
////			initTest();
//		return expUtil.evalExp(string);
//	}
//
////	private static IRecord initTest() throws Throwable {
////		// System.out.println(" sdfsa( ) ddf ".trim());
////		Table table = ConfigUtil.getTable("T_DIC_ORGA");
////		IContext context = new TempIContext("1");
////		IRecord form = new HashRecord();
////		form.set("HEADID", "fb8d4680-0050-46f9-bc1a-135298a91213");
////		form.set("SJRKL", "99999.99");
////		form.set("PONO", "");
////		form.set("EST_SALES", "FC");
////		// System.out.println("setForm(MONEY,999.99)");
////		// tx = null;
////		// try {
////		// DBTransaction tx = new DBTransactionImp();
////		// context.getDBM().nager context.getDBM().= new
////		// context.getDBM().nagerFactory().getDBManager();
////		expUtil = new ExpUtil(context, table, form);
////		// try {
////		// // .testVariable();
////		// parseString("aBS(LefT())");
////		// expUtil.evalExp("GetDouble(\"MONEY\")+1");
////		// expUtil.evalExp("Pow(GetDouble(\"MONEY\")+1,2)");
////		// expUtil.evalExp("Rmb(Pow(GetDouble(\"MONEY\")+1,2))");
////		// expUtil.evalExp("Test(\"gogogo\")");
////		// expUtil.evalExp("if(1-0=0,true,or(false,false))");
////		// } catch (ParseException e) {
////		// e.printStackTrace();
////		// }
////		// try {
////		// new ExpUtil().testConvert();
////		// } catch (ParseException e) {
////		// e.printStackTrace();
////		// }
////		// try {
////		// new ExpUtil().testEval();
////		// } catch (ParseException e) {
////		// e.printStackTrace();
////		// }
////		// try {
////		// new ExpUtil().testEval2();
////		// } catch (ParseException e) {
////		// e.printStackTrace();
////		// }
////		// } finally {
////		// if (tx != null)
////		// tx.close();
////		// }
////		return form;
////	}
//
//	static String parseString(String str) throws Throwable {
//		Parser parser = new Parser(new java.io.StringReader(str));
//		CTreeRoot n = (CTreeRoot) parser.Start();
//		// 字符串测试分析器
//		PostfixVisitor v = new PostfixVisitor();
//		StringBuffer bf = new StringBuffer();
//		bf = (StringBuffer) v.visit(n, bf);
//		//
//		System.out
//				.println(">parse:" + bf.toString().replaceAll("\"", "\\\\\""));
//		// System.out.print("assertTrue(parseString(\""
//		// + str.replaceAll("\"", "\\\\\"") + "\").equals(\""
//		// + bf.toString().replaceAll("\"", "\\\\\"") + "\"));");
//		return bf.toString();
//	}
//
//	static void testConvert() throws Throwable {
//		assertFalse(ExpTypeConvert.isDouble("a1.1"));
//		assertTrue(ExpTypeConvert.isDouble("1.1"));
//		System.out.println(ExpTypeConvert.toRmb(100.001));
//		System.out.println(ExpTypeConvert.toRmb(10011001010.001));
//		System.out.println(ExpTypeConvert.toRmb(10001000100010000000f));
//	}
//
//	static void testParse() throws Throwable {
//		// parseString("IF(BillKey()=\"caigoudingdan\",kucunshuliang-kucunshouhuoshuliang+kucuntuihuoshuliang,0)");
//		assertTrue(parseString("GetProp(abs)").equals("GetProp(C_abs)"));
//		assertTrue(parseString("GetProp(Code)").equals("GetProp(C_Code)"));
//		assertTrue(parseString("GetProp(Code)+abs+code+if+and+or+getprop")
//				.equals("+(+(+(+(+(+(GetProp(C_Code),V_abs),V_code),V_if),V_and),V_or),V_getprop)"));
//		assertTrue(parseString("GetProp(if)").equals("GetProp(C_if)"));
//		assertTrue(parseString("GetProp(and)").equals("GetProp(C_and)"));
//		assertTrue(parseString("GetProp(or)").equals("GetProp(C_or)"));
//		assertTrue(parseString("GetProp(getprop)").equals("GetProp(C_getprop)"));
//		assertTrue(parseString("GetProp(Supplier,Code,1)").equals(
//				"GetProp(C_Supplier,C_Code,N_1)"));
//		assertTrue(parseString("MATERIALDTL.DealerID").equals(
//				"V_MATERIALDTL.DealerID"));
//		assertTrue(parseString("Supplier=Getprop(MtlID,MATERIALDTL.DealerID)")
//				.equals("=(V_Supplier,Getprop(C_MtlID,C_MATERIALDTL.DealerID))"));
//		assertTrue(parseString("if(Sequence=0,\"Sequence\",Sequence)").equals(
//				"S_if(=(V_Sequence,N_0),C_Sequence,V_Sequence)"));
//		assertTrue(parseString("a+bb").equals("+(V_a,V_bb)"));
//		assertTrue(parseString("1+1").equals("+(N_1,N_1)"));
//		assertTrue(parseString("a@+b@b").equals("+(V_a@,V_b@b)"));
//		assertTrue(parseString("1+1").equals("+(N_1,N_1)"));
//		assertTrue(parseString("True>=fALse+true-False").equals(
//				">=(B_True,-(+(B_fALse,B_true),B_False))"));
//		assertTrue(parseString("-1*-1--1^-1").equals(
//				"-(*(-(N_1),-(N_1)),^(-(N_1),-(N_1)))"));
//		assertTrue(parseString("hiHi(1,&Habd2)").equals("hiHi(N_1,N_&Habd2)"));
//		assertTrue(parseString(
//				"hihi(1,&Habd2,&o2347,2345863957483&,&Habd2&,1.1)").equals(
//				"hihi(N_1,N_&Habd2,N_&o2347,N_2345863957483&,N_&Habd2&,N_1.1)"));
//		assertTrue(parseString("6=\"5\"&4+3*2^1").equals(
//				"=(N_6,&(C_5,+(N_4,*(N_3,^(N_2,N_1)))))"));
//		assertTrue(parseString("6=hihi&4+3*2^1").equals(
//				"=(N_6,&(V_hihi,+(N_4,*(N_3,^(N_2,N_1)))))"));
//		assertTrue(parseString("hihi(\"haHa\"\"\",1+2,(3*4),haha(1,2),ha)+hI")
//				.equals("+(hihi(C_haHa\"\",+(N_1,N_2),*(N_3,N_4),haha(N_1,N_2),C_ha),V_hI)"));
//		assertTrue(parseString(
//				"hihi(\"haha(\"\"\",1+2,(3*4),haha(1,haha(),2),ha)+hi")
//				.equals("+(hihi(C_haha(\"\",+(N_1,N_2),*(N_3,N_4),haha(N_1,haha(),N_2),C_ha),V_hi)"));
//		//
//		assertTrue(parseString("and(true, if(true,hihi,or(true,false)))")
//				.equals("S_and(B_true,S_if(B_true,V_hihi,S_or(B_true,B_false)))"));
//		//
//		assertTrue(parseString("aBS(LefT())").equals("F_aBS(F_LefT())"));
//		//
//		assertTrue(parseString("hihi(haha)").equals("hihi(C_haha)"));
//		assertTrue(parseString("hihi(haha +hehe,hihi(hihi()),hi)").equals(
//				"hihi(+(V_haha,V_hehe),hihi(hihi()),C_hi)"));
//		assertTrue(parseString("hihi(haha +hehe,hi*hi,hi(hihi()),hi)").equals(
//				"hihi(+(V_haha,V_hehe),*(V_hi,V_hi),hi(hihi()),C_hi)"));
//		assertTrue(parseString(
//				"if(haha +hehe,hi*hi,and(hihi,int(haha)),(hihi()),hi)")
//				.equals("S_if(+(V_haha,V_hehe),*(V_hi,V_hi),S_and(V_hihi,F_int(V_haha)),hihi(),V_hi)"));
//		//
//		assertTrue(parseString("1>2").equals(">(N_1,N_2)"));
//		assertTrue(parseString("hihi(1>2)").equals("hihi(>(N_1,N_2))"));
//		assertTrue(parseString("hihi(((1+1)=3))").equals(
//				"hihi(=(+(N_1,N_1),N_3))"));
//		assertTrue(parseString("int(1.1)+hihi(((1+1)=3))").equals(
//				"+(F_int(N_1.1),hihi(=(+(N_1,N_1),N_3)))"));
//		assertTrue(parseString("int(1.1)+InT(1.2)+Int(((1+1)>3))").equals(
//				"+(+(F_int(N_1.1),F_InT(N_1.2)),F_Int(>(+(N_1,N_1),N_3)))"));
//		assertTrue(parseString("Int(1+1>3)").equals("F_Int(>(+(N_1,N_1),N_3))"));
//		//
//		assertTrue(parseString("hihi(,,hihi,)").equals("hihi(C_,C_,C_hihi,C_)"));
//		assertTrue(parseString("and(,,hihi,)").equals("S_and(C_,C_,V_hihi,C_)"));
//		//
//		parseString("\" \"");
//	}
//
//	static void testEval1() throws Throwable {
//		assertEquals(evalExp("1+1.1"), 2.1);
//		assertEquals(evalExp("1+1.1+4"), 6.1);
//		assertEquals(evalExp("int(1.1)+InT(1.2)+Int(((1+1)))"), 4.0);
//		// "+"
//		assertEquals(evalExp("\"1\"+\"2\""), "12");
//		assertEquals(evalExp("1+\"2a\""), "12a");
//		assertEquals(evalExp("1++\"2\""), 3.0); //
//		assertEquals(evalExp("+1++true"), 2.0);
//		// "-"
//		assertEquals(evalExp("\"1\"-\"2\""), -1.0);
//		// assertEquals(evalExp("1+\"2a\""), "12a");
//		assertEquals(evalExp("1--\"2\""), 3.0); //
//		assertEquals(evalExp("-1--true"), 0.0);
//		// "*","/","^"
//		assertEquals(evalExp("2*3^2/6"), 3.0);
//		// "=","<",">","<=",">=","<>"
//		assertTrue((Boolean) evalExp("\"ab\"=\"ab\""));
//		assertTrue((Boolean) evalExp("\"Ab\"<\"ab\"")); //
//		assertTrue((Boolean) evalExp("\"bc\">\"abc\""));
//		assertTrue((Boolean) evalExp("\"abc\"<=\"bc\""));
//		assertTrue((Boolean) evalExp("\"bc\">=\"abc\""));
//		assertTrue((Boolean) evalExp("\"b\"<>\"ab\""));
//		assertTrue((Boolean) evalExp("\"2\"<\"12\"")); //
//		assertTrue((Boolean) evalExp("1=True"));
//		assertFalse((Boolean) evalExp("1+1=3")); // 1+1=3??
//		assertTrue((Boolean) evalExp("FALse<True"));
//		assertTrue((Boolean) evalExp("1<\"a\""));
//		assertTrue((Boolean) evalExp("11>true"));
//		assertTrue((Boolean) evalExp("1-0<=1"));
//		assertTrue((Boolean) evalExp("1-0>=1"));
//		assertTrue((Boolean) evalExp("1+1<>3"));
//		//
//		evalExp("\"&\"");
//		// Round
//		assertEquals(evalExp("round(10.123,2)"), 10.12);
//		assertEquals(evalExp("round(10.125,2)"), 10.13);
//		assertEquals(evalExp("round(10.123,)"), 10.0);
//		assertEquals(evalExp("round(-10.523)"), -11.0);
//		assertEquals(evalExp("round(-10.423)"), -10.0);
//		assertEquals(evalExp("TrunC(10.123,2)"), 10.12);
//		assertEquals(evalExp("TrunC(10.125,2)"), 10.12);
//		assertEquals(evalExp("TrunC(10.123,)"), 10.0);
//		assertEquals(evalExp("TrunC(-10.523)"), -10.0);
//		assertEquals(evalExp("TrunC(-10.423)"), -10.0);
//		// Char
//		evalExp("cHar(79.01)");
//		evalExp("cHar(1000.01)");
//		evalExp("cHar(Code(\"Ophihi\"))");
//		evalExp("cHar(Code(\"aaa\"))");
//		//
//
//	}
//
//	void testEval2() throws Throwable {
//		//
//		System.out.println(""
//				+ ((Double) (Double.parseDouble("0.00001"))).toString());
//		System.out.println(""
//				+ ((Double) (Double.parseDouble("0.001"))).toString());
//		// assertTrue((Boolean)(evalExp("Exact(0.001,\"0.001\")")));
//		assertTrue((Boolean) (evalExp("Exact(\"0.001\",\"0.001\")")));
//		assertEquals(evalExp("find(\"hihihi\",\"ih\")"), 2.0);
//		assertEquals(evalExp("find(\"hihihi\",\"h\")"), 1.0);
//		assertEquals(evalExp("find(\"hihihi\",\"ih\",3)"), 4.0);
//		assertEquals(evalExp("findb(\"hihihi\",\"ih\")"), 3.0);
//		assertEquals(evalExp("findb(\"hihihi\",\"h\")"), 1.0);
//		assertEquals(evalExp("findb(\"hihihi\",\"ih\",5)"), 7.0);
//		assertEquals(evalExp("Left(\"hi1234\",2)"), "hi");
//		assertEquals(evalExp("LeftB(\"hi1234\",2)"), "h");
//		assertEquals(evalExp("LeftB(\"hi1234\",1)"), "");
//		assertEquals(evalExp("LeftB(\"hi1234\",3)"), "h");
//		assertEquals(evalExp("Right(\"hi1234\",2)"), "34");
//		assertEquals(evalExp("RightB(\"hi1234\",2)"), "4");
//		assertEquals(evalExp("RightB(\"hi1234\",3)"), "4");
//		assertEquals(evalExp("RightB(\"hi1234\",1)"), "");
//		assertEquals(evalExp("len(\"hi1234\")"), 6.0);
//		assertEquals(evalExp("lenB(\"hi1234\")"), 12.0);
//		assertEquals(evalExp("sub(\"hi1234\",2,3)"), "i12");
//		assertEquals(evalExp("mid(\"hi1234\",2,3)"), "i12");
//		assertEquals(evalExp("midB(\"hi1234\",2,5)"), "hi");
//		assertEquals(evalExp("midB(\"hi1234\",3,3)"), "i");
//
//		assertEquals(evalExp("mod(7,2)"), 1.0);
//		assertEquals(evalExp("sign(7)"), 1.0);
//		assertEquals(evalExp("siGn(-7)"), -1.0);
//		assertEquals(evalExp("sign(0)"), 0.0);
//		assertTrue((Boolean) evalExp("not(1+1=3)"));
//		// if or
//		assertEquals(evalExp("if(1+1=3,if(),2)"), 2.0);
//		assertEquals(evalExp("if(1+1<>3,2,if())"), 2.0);
//		evalExp("if(1+1<>3,2)");
//		evalExp("if(1+1=3,2,if())");//
//		evalExp("case(1+1<>3,2)");
//		assertFalse((Boolean) evalExp("and(1+1=3,and())"));
//		evalExp("and(1+1<>3,and())"); //
//		assertTrue((Boolean) evalExp("and(True,true,true,true,true,true,true,true,1=1,1=1,1=1,1=1)"));
//		assertTrue((Boolean) evalExp("or(1+1<>3,or())"));
//		evalExp("or(1+1<>3,or())");
//		assertFalse((Boolean) evalExp("or(1+1=3,false,false,false,false,false,false,1=2,1=2,1=2,1=2)"));
//		assertTrue((Boolean) evalExp("aNd(tRue,TRUe)"));
//	}
//}