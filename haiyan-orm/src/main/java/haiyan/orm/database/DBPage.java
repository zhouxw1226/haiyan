package haiyan.orm.database;

import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouxw
 */
public class DBPage implements Serializable, IDBResultSet {

	private static final long serialVersionUID = 1L;
	//private static transient final String BLANK = "&nbsp;&nbsp;";
	public static transient final String PAGE_NO_PARAM_NAME = DataConstant.PAGE_NO_PARAM_NAME;
	public static transient final String MAX_COUNT_PER_PAGE_NAME = DataConstant.MAX_COUNT_PER_PAGE_NAME;
	public static transient final int[] opts = { 5, 10, 50, 100, 500, 1000 };
	public static transient int MAXCOUNTPERQUERY = 5000;
	public static transient int MAXNUMPERPAGE = 100;
	private int maxPageRecordCount = MAXNUMPERPAGE;
	private int currPageNO = 1;
	private long totalRecordCount = 0;
	private String tableName;
	private List<IDBRecord> records = null; // new PageList<IRecord>();
	public DBPage() {
	}
	public DBPage(List<IDBRecord> arrayList) {
		this.records = arrayList;
	}
//	/**
//	 * @param context
//	 * @throws Throwable
//	 */
//	public static int getCurrPageNO(IContext context) throws Throwable {
//		int num = 1;
//		int maxNumPerPageInt = getMaxNumPerPage(context);
//		String temp = (String)context.getAttribute(PAGE_NO_PARAM_NAME);
//		if (!StringUtil.isBlankOrNull(temp)) {
//			num = Integer.parseInt(temp);
//		} else {
//			// ext parameter
//			if (!StringUtil.isBlankOrNull((String)context.getAttribute("start"))) {
//				if (maxNumPerPageInt != 0) {
//					int start = num;
//					if (!"NaN".equals((String)context.getAttribute("start")))
//						start = Integer.parseInt((String)context.getAttribute("start"));
//					num = start / maxNumPerPageInt + 1;
//				} else
//					num = 1;
//			}
//		}
//		return num;
//	}
//	/**
//	 * @param context
//	 * @throws Throwable
//	 */
//	public static int getMaxNumPerPage(IContext context) throws Throwable {
//		int num = MAXNUMPERPAGE;
//		String temp = (String)context.getAttribute(MAX_COUNT_PER_PAGE_NAME);
//		if (!StringUtil.isBlankOrNull(temp)) {
//			num = Integer.parseInt(temp);
//		} else {
//			// ext parameter
//			if (!StringUtil.isBlankOrNull((String)context.getAttribute("limit"))) {
//				int limit = num;
//				if (!"NaN".equals((String)context.getAttribute("limit")))
//					limit = Integer.parseInt((String)context.getAttribute("limit"));
//				num = limit;
//			}
//		}
//		return num;
//	}
//	/**
//	 * @param parser
//	 * @return int
//	 */
//	public static int getCurrPageNO(Parser parser) {
//		for (Parameter param : parser.getParameters().getParameter()) {
//			if (param.getName().equalsIgnoreCase(PAGE_NO_PARAM_NAME))
//				return new Integer(param.getValue());
//		}
//		return 1;
//	}
//	/**
//	 * @param parser
//	 * @return int
//	 */
//	public static int getMaxNumPerPage(Parser parser) {
//		for (Parameter param : parser.getParameters().getParameter()) {
//			if (param.getName().equalsIgnoreCase(MAX_COUNT_PER_PAGE_NAME))
//				return new Integer(param.getValue());
//		}
//		return MAXNUMPERPAGE;
//	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @param currPageNO
	 */
	public void setCurrPageNO(int currPageNO) throws DBPageException {
		this.currPageNO = currPageNO;
	}
	/**
	 * 当前页码
	 * 
	 * @return int
	 */
	public int getCurrPageNO() {
		return this.currPageNO;
	}
	/**
	 * 最大每页行数
	 * 
	 * @return int
	 */
	public int getMaxPageRecordCount() {
		return this.maxPageRecordCount;
	}
	/**
	 * 设置最大每页行数
	 * 
	 * @param count
	 */
	public void setMaxPageRecordCount(int count) {
		this.maxPageRecordCount = count;
	}
	/**
	 * 查询出来的总记录行数
	 * 
	 * @return int
	 */
	public long getTotalRecordCount() {
		if (this.totalRecordCount==0 && this.records.size()>0) // 临时列表大小
			return this.records.size();
		return this.totalRecordCount; // db设置的值
	}
	/**
	 * 总记录行数
	 * 
	 * @param i
	 */
	public void setTotalRecordCount(long i) {
		this.totalRecordCount = i;
	}
	/**
	 * 查询出来的总页数
	 * 
	 * @return int
	 */
	public int getTotalPageCount() {
		if (this.maxPageRecordCount == 0) {
			return 0;
		}
		int pageCount = (int)(this.getTotalRecordCount() / this.maxPageRecordCount);
		int temp = (int)(this.getTotalRecordCount() % this.maxPageRecordCount);
		if (temp > 0) {
			pageCount += 1;
		}
		return pageCount;
	}
	/**
	 * 当前可访问有效数据的行数
	 * 
	 * @return int
	 */
	public int size() {
		if (this.records != null)
			return this.records.size();
		return 0;
	}
	/**
	 * @param index
	 * @return IRecord
	 */
	public IDBRecord getRecord(int index) {
		if (this.records.size() <= index)
			return null;
		return this.records.get(index);
	}
	/**
	 * @return ArrayList
	 */
	public List<IDBRecord> getRecords() {
		return this.records;
	}
	/**
	 * @return int
	 */
	public int getRecordCount() {
		return this.records!=null?this.records.size():0;
	}
	/**
	 * @param data
	 */
	public void setRecords(List<IDBRecord> data) {
		this.records = data;
	}
	/**
	 * @param index
	 * @param record
	 */
	public void addRecord(int index, IDBRecord record) {
		this.records.add(index, record);
	}
	/**
	 * @param record
	 */
	public void addRecord(IDBRecord record) {
		this.records.add(record);
	}
	/**
	 * @return String
	 */
	public String toString() {
		StringBuffer result = new StringBuffer("");
		Iterator<?> iter = this.getRecords().iterator();
		while (iter.hasNext()) {
			IDBRecord form = (IDBRecord) iter.next();
			if (result.length() > 0)
				result.append("\n");
			result.append(form.toString());
		}
		// DebugUtil.debug(result);
		return result.toString();
	}
	private int activeIndex = 0;
	@Override
	public void setActiveRecord(int index) {
		if (index>=this.size())
			throw new Warning("Out of Data's Bounds");
		this.activeIndex = index;
	}
	@Override
	public IDBRecord getActiveRecord() {
		if (activeIndex>=this.size())
			return null;
		return this.getRecord(activeIndex);
	}
}
///**
//* @param jsonArray
//*/
//public void json2Page(JSONArray jsonArray) {
//	// json2Page
//	for (int i = 0; i < jsonArray.size(); i++) {
//		JSONObject json = jsonArray.getJSONObject(i);
//		IRecord form = new HashRecord();
//		form.json2Form(json);
//		this.data.add(form);
//	}
//}
///**
//* @return JSONArray
//*/
//public JSONArray toJSon(int type, Table table) {
//	// NOTICE 不可用PageList构造 fromObject获取不到元素
//	ArrayList<String> ignore = new ArrayList<String>();
//	if (type>0) 
//		for (com.haiyan.genmis.castorgen.Field fld:table.getField()) {
//			if (fld!=null && fld.getListColumn()!=null) {
//				com.haiyan.genmis.castorgen.ListColumn lc = fld.getListColumn();
//				if (type==1&&lc.getNoedit())
//					ignore.add(fld.getName());
//				else if (type==2&&lc.getNoquery())
//					ignore.add(fld.getName());
//				else if (type==3&&lc.getNowrap())
//					ignore.add(fld.getName());
//			}
//		}
//	boolean showAll = true;
//	if (ignore.size()>0) 
//		showAll = false;
//	JSONArray list = new JSONArray();
//	//ArrayList<JSONObject> list = new ArrayList<JSONObject>();
//	Iterator<?> iter = this.getData().iterator();
//	while (iter.hasNext()) {
//		Object o = iter.next();
//		if (o instanceof IRecord) {
//			IRecord form = (IRecord)o;
//			if (form != null)
//				list.add(form.toJSon(showAll, ignore));
//		} else {
//			DebugUtil.error("Page.toJSon非法数据类型:"+o);
//		}
//	}
//	// JSONObject json = JSONObject.fromObject(list);
//	// DebugUtil.debug("" + json);
//	//return JSONArray.fromObject(list);
//	return list;
//}
///**
//* @return JSONArray
//*/
//public JSONArray toJSon() {
//	return toJSon(0, null);
//}
///**
//* 单表Page数据序列化
//* 
//* @param table
//* @param parser
//*/
//public void toParser(Table table, Parser parser) {
//	// to json
//	if (parser instanceof JSonParser) {
//		String tableName = null;
//		if (table != null)
//			tableName = table.getName();
//		else if (this.getData().size() > 0)
//			tableName = this.getData().get(0)
//					.getParameter(DataConstant.OPERATION_DATA_PARAM);
//		JSonParser jparser = (JSonParser) parser;
//		jparser.setJsonData(this.toJSon().toString());
//		jparser.setTotalCount(this.getData().size());
//		jparser.setTableName(tableName);
//		// NOTICE jsonparser现只支持处理一个table的数据 所以可以放置tableName
//		// dealJSonParser(((JSonParser) parser), tableName);
//		// JSonUtil.getInstance().bean2Json(this.toJSon())
//		// this.toJsonParser(table, parser);
//	} else {
//		// 现在只支持一个rows标签
//		Rows rows = new Rows();
//		Iterator<?> iter = this.getData().iterator();
//		// int count = 0;
//		while (iter.hasNext()) {
//			IRecord form = (IRecord) iter.next();
//			Row row = form.toRow();
//			if (table != null) {
//				String tableName = table.getName();
//				row.setName(tableName);
//				row.setTable(tableName);
//			} else {
//				row.setName(form.get(DataConstant.PAGE_DATA_PARAM));
//				row.setTable(form.get(DataConstant.OPERATION_DATA_PARAM));
//			}
//			form.remove(DataConstant.PAGE_DATA_PARAM);
//			form.remove(DataConstant.OPERATION_DATA_PARAM);
//			rows.addRow(row);
//			// count++;
//		}
//		parser.setRows(rows);
//		// // if (parser instanceof MetadataParser)
//		// this.toProtocolParser(table, parser);
//	}
//}
///**
//* 多表Page数据序列化
//* 
//* 应该只有 ArrayListPage 会用到此API, 因为Page数据是注入的可能属于不同的表
//* 
//* @param tlist
//* @param parser
//*/
//public void toParser(ArrayList<Table> tlist, Parser parser) {
//	// to json
//	if (parser instanceof JSonParser) {
//		String tableName = null;
//		if (tlist.size() > 0)
//			tableName = tlist.get(0).getName();
//		else if (this.getSize() > 0)
//			tableName = this.getData(0).get(DataConstant.OPERATION_DATA_PARAM);
//		JSonParser jparser = (JSonParser) parser;
//		jparser.setJsonData(this.toJSon().toString());
//		jparser.setTotalCount(this.getSize());
//		jparser.setTableName(tableName);
//		// NOTICE jsonparser现只支持处理一个table的数据 所以可以放置tableName
//		// dealJSonParser(((JSonParser) parser), tableName);
//		// JSonUtil.getInstance().bean2Json(this.toJSon())
//		// this.toJsonParser(table, parser);
//	} else {
//		// 现在只支持一个rows标签
//		Rows rows = new Rows();
//		Iterator<?> iter = this.getData().iterator();
//		int count = 0;
//		while (iter.hasNext()) {
//			IRecord form = (IRecord) iter.next();
//			Row row = form.toRow();
//			if (tlist != null && count < tlist.size() && tlist.get(count) != null) {
//				String tableName = tlist.get(count).getName();
//				row.setName(tableName);
//				row.setTable(tableName);
//			} else { // 默认PAGE TABLE
//				row.setName(form.get(DataConstant.PAGE_DATA_PARAM));
//				row.setTable(form.get(DataConstant.OPERATION_DATA_PARAM));
//			}
//			form.remove(DataConstant.PAGE_DATA_PARAM);
//			form.remove(DataConstant.OPERATION_DATA_PARAM);
//			rows.addRow(row);
//			count++;
//		}
//		parser.setRows(rows);
//		// if (parser.getGenmis() != null
//		// && parser.getGenmis().getMetadataCount() > 0)
//		// parser.getGenmis().getMetadata(0).setRows(formSet);
//		// if (parser instanceof MetadataParser)
//		// this.toProtocolParser(tlist, parser);
//	}
//}
///**
//* @return String
//*/
//public String toXML(Table table) {
//	StringBuffer result = new StringBuffer("");
//	result.append("<dataset>");
//	result.append("<datasize>" + this.getSize() + "</datasize>");
//	Iterator<?> iter = this.getData().iterator();
//	int count = 0;
//	while (iter.hasNext()) {
//		IRecord form = (IRecord) iter.next();
//		if (table == null)
//			form.setXmlLable("data_" + count);
//		else
//			form.setXmlLable(table.getName());
//		result.append(form.toXML());
//		count++;
//	}
//	result.append("</dataset>");
//	// DebugUtil.debug(result);
//	return result.toString();
//}
//// /**
//// * @param table
//// * @param parser
//// */
//// private void toProtocolParser(Table table, ProtocolParser parser) {
//// // 现在只支持一个rows标签
//// Rows rows = new Rows();
//// Iterator<?> iter = this.getData().iterator();
//// int count = 0;
//// while (iter.hasNext()) {
//// IRecord form = (IRecord) iter.next();
//// Row row = form.toRow();
//// if (table != null) {
//// String tableName = table.getName();
//// row.setName(tableName);
//// row.setTable(tableName);
//// } else {
//// row.setName(form.getParameter(DataConstant.PAGE_DATA_PARAM));
//// row.setTable(form
//// .getParameter(DataConstant.OPERATION_DATA_PARAM));
//// }
//// rows.addRow(row);
//// count++;
//// }
//// parser.setRows(0, rows);
//// // if (parser.getGenmis() != null
//// // && parser.getGenmis().getMetadataCount() > 0)
//// // parser.getGenmis().getMetadata(0).setRows(formSet);
//// }
////
//// /**
//// * @param tlist
//// * @param parser
//// */
//// private void toProtocolParser(ArrayList<Table> tlist, ProtocolParser
//// parser) {
//// // 现在只支持一个rows标签
//// Rows rows = new Rows();
//// Iterator<?> iter = this.getData().iterator();
//// int count = 0;
//// while (iter.hasNext()) {
//// IRecord form = (IRecord) iter.next();
//// Row row = form.toRow();
//// if (tlist != null && count < tlist.size()
//// && tlist.get(count) != null) {
//// String tableName = tlist.get(count).getName();
//// row.setName(tableName);
//// row.setTable(tableName);
//// } else { // 默认PAGE TABLE
//// row.setName(form.getParameter(DataConstant.PAGE_DATA_PARAM));
//// row.setTable(form
//// .getParameter(DataConstant.OPERATION_DATA_PARAM));
//// }
//// rows.addRow(row);
//// count++;
//// }
//// parser.setRows(0, rows);
//// // if (parser.getGenmis() != null
//// // && parser.getGenmis().getMetadataCount() > 0)
//// // parser.getGenmis().getMetadata(0).setRows(formSet);
//// }
////
//// /**
//// * @param visCompMap
//// */
//// public void setVisCompPage(HashMap<String, String> visCompMap) {
//// this.visCompMap = visCompMap;
//// }
//// HashMap<String, String> visCompMap = null; // 不能用这个，会导致service内存不够
///**
//* @param form
//* @param hasSelect
//* @return String
//*/
//@Deprecated
//public String getPagingHTMLCode(String form, boolean hasSelect) {
//	int currPageNO = getCurrPageNO();
//	// int maxRecordPerPageCount = getMaxPageRecordCount();
//	int totalPageCount = getTotalPageCount();
//	int totalRecordCount = getTotalRecordCount();
//	//
//	haiyan.common.HyStringBuffer buf = new haiyan.common.HyStringBuffer();
//	int hashCode = this.hashCode();
//	String MAX_COUNT_ID = MAX_COUNT_PER_PAGE_NAME + hashCode;
//	String PAGE_NO_ID = PAGE_NO_PARAM_NAME + hashCode;
//	String FUNC_NAME = "__go2page" + hashCode;
//	//
//	buf.append("<SCRIPT LANGUAGE=javascript>\n");
//
//	buf.append("function " + FUNC_NAME + "(num){\n");
//
//	buf.append("if (typeof " + form + "=='undefined')alert('表单: " + form + " 丢失.');\n");
//
//	buf.appendln("if (typeof " + form + "." + MAX_COUNT_PER_PAGE_NAME + "=='undefined')");
//	buf.append(form).append(".insertAdjacentHTML(\"AfterBegin\",")
//			.append("\"<input type='hidden' id='")
//			.append(MAX_COUNT_PER_PAGE_NAME).append("' name='")
//			.append(MAX_COUNT_PER_PAGE_NAME).append("' value='' >\");\n");
//	buf.append(form + "." + MAX_COUNT_PER_PAGE_NAME + ".value=document.getElementById('" + MAX_COUNT_ID + "').value;\n");
//
//	buf.appendln("if (typeof " + form + "." + PAGE_NO_PARAM_NAME + "=='undefined')");
//	buf.append(form).append(".insertAdjacentHTML(\"AfterBegin\",")
//			.append("\"<input type='hidden' id='")
//			.append(PAGE_NO_PARAM_NAME).append("' name='")
//			.append(PAGE_NO_PARAM_NAME).append("' value='' >\");\n");
//	buf.append(form + "." + PAGE_NO_PARAM_NAME + ".value=num;\n");
//
//	buf.append(form).append(".submit();\n");
//	// buf.append(compID + ".value=1;\n ");
//	buf.append("}\n");
//
//	buf.append("</SCRIPT>\n");
//	//
//	buf.append("<span class=\"haiyan_paging\">");
//	buf.append(SysCode.create(null, 101043, "total_page", new String[] { String.valueOf(totalRecordCount) }).toString()).append(BLANK);
//	buf.append(SysCode.create(null, 101044, "curr_page", new String[] { String.valueOf(currPageNO), String.valueOf(totalPageCount) })
//			.toString()).append(BLANK);
//	// buf.append(blank);
//	if (currPageNO == 1 || maxPageRecordCount == 0) {
//		buf.append(
//				"[" + SysCode.create(null, 101047, "first_page").toString() + "]")
//				.append("[" + SysCode.create(null, 101045, "pre_page").toString() + "]");
//	} else {
//		buf.append("<a href=\"javascript:" + FUNC_NAME + "(1);\">");
//		buf.append(
//				"[" + SysCode.create(null, 101047, "first_page").toString() + "]")
//				.append("</a>");
//
//		buf.append("<a href=\"javascript:" + FUNC_NAME + "(" + (this.currPageNO - 1) + ");\">");
//		buf.append("[" + SysCode.create(null, 101045, "pre_page").toString() + "]")
//				.append("</a>");
//	}
//	// buf.append(blank);
//	if (currPageNO == totalPageCount || maxPageRecordCount == 0) {
//		buf.append(
//				"[" + SysCode.create(null, 101046, "next_page").toString() + "]")
//				.append("[" + SysCode.create(null, 101048, "last_page").toString() + "]");
//	} else {
//		buf.append("<a href=\"javascript:" + FUNC_NAME + "("
//				+ (this.currPageNO + 1) + ");\">");
//		buf.append(
//				"[" + SysCode.create(null, 101046, "next_page").toString() + "]")
//				.append("</a>");
//
//		buf.append("<a href=\"javascript:" + FUNC_NAME + "(" + totalPageCount + ");\">");
//		buf.append(
//				"[" + SysCode.create(null, 101048, "last_page").toString() + "]")
//				.append("</a>");
//	}
//	// buf.append(blank);
//	String comp = "document.getElementById('" + PAGE_NO_ID + "')";
//	buf.append(BLANK)
//			.append("<input id=\"" + PAGE_NO_ID + "\" name=\"" + PAGE_NO_ID + "\" size=3 value=" + currPageNO + ">")
//			.append("<input type=\"button\" class=\"zbutton\" value=\"" + SysCode.create(null, 101049, "goto_page").toString() + "\" ")
//			.append(" onclick=\"javascript:")
//			.append("var temp=parseInt(" + comp + ".value);")
//			.append("if(isNaN(temp)||temp<1){temp=1;}")
//			// .append("if(isNaN(" + comp + ".value)||" + comp + ".value<1){" + comp + ".value=1;}")//
//			.append("else if(temp>" + totalPageCount + "){temp=" + totalPageCount + ";}") //
//			.append(comp + ".value=temp;") //
//			.append(FUNC_NAME + "(" + comp + ".value);\">") //
//			.append(BLANK);
//	//
//	if (hasSelect) {
//		buf.append("<select id=\"" + MAX_COUNT_ID + "\" name=\"" + MAX_COUNT_ID + "\" onchange=\"" + FUNC_NAME + "(1);\">");
//		String selected = "";
//		for (int i = 0; i < opts.length; i++) {
//			if (opts[i] == maxPageRecordCount) {
//				selected = "selected";
//			} else {
//				selected = "";
//			}
//			buf.append("<option value=\"" + opts[i] + "\" " + selected + ">"
//					+ SysCode.create(null, 101050, "num_per_page", new String[] { String.valueOf(opts[i]) }).toString());
//		}
//		buf.append("</select>");
//	} else {
//		buf.append("[" + SysCode.create(null, 101050, "num_per_page", new String[] { String.valueOf(maxPageRecordCount) }).toString() + "]");
//		buf.append("<input type=\"hidden\" id=\"" + MAX_COUNT_ID
//				+ "\" name=\"" + MAX_COUNT_ID + "\" value=\"" + maxPageRecordCount + "\";\">");
//	}
//	buf.append("</span>");
//	return buf.toString();
//}
///**
//* @param form
//* @return String
//*/
//@Deprecated
//public String getPagingHTMLCode(String form) {
//	return this.getPagingHTMLCode(form, true);
//}
// /**
// * @return String
// */
// public String getID() {
// // if (this.data instanceof PageList)
// // return ((PageList) this.data).getID();
// return "" + this.hashCode();
// }