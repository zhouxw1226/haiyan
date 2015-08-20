//package haiyan.orm.database.sql;
//
//import haiyan.common.ByteUtil;
//import haiyan.common.config.ConfigUtil;
//import haiyan.common.config.DataConstant;
//import haiyan.common.exception.Warning;
//import haiyan.common.intf.database.IDBFilter;
//import haiyan.common.intf.datbase.orm.IRecord;
//import haiyan.common.intf.datbase.orm.IResultSet;
//import haiyan.common.intf.session.IContext;
//import haiyan.config.castorgen.AbstractField;
//import haiyan.config.castorgen.Haiyan;
//import haiyan.config.castorgen.Metadata;
//import haiyan.config.castorgen.Parameter;
//import haiyan.config.castorgen.Parameters;
//import haiyan.config.castorgen.Table;
//import haiyan.orm.database.sql.page.Page;
//
//import java.util.ArrayList;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//public class SOAPDBManager extends SQLDBManager {
//
//	public SOAPDBManager(SQLDatabase db, boolean notSameConn) {
//		super(db, notSameConn);
//	}
//	
//	public IRecord findByPK(Table table, String id, ITableContext context,
//			short type, int... args) throws Throwable {
//
//		IRecord form = null;
//		if (type == DBManager.CACHE || type == DBManager.DBSESSION) {
//    		form = this.getCache(context, table, id, type);
//    		if (form != null && !form.isDirty()) // 注意insert,update和delete操作对缓冲数据的影响
//    			return form;
//		}
//		
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub stub = //
//				new haiyan.localhost.haiyan_jws.HaiyanServiceStub(db.getURL());
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call call4 = //
//			(haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call)
//				haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call.class.newInstance();
//
//		Parameters ps = new Parameters();
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__sessionid");
//			pm.setValue(context.getSessionID());
//			ps.addParameter(pm);
//		}
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__action");
//			pm.setValue("exp");
//			ps.addParameter(pm);
//		}
////		{
////			String n = "__extendFilter."+table.getName();
////			Filter f = (Filter)context.getAttribute(n);
////			if (f!=null) {
////				Parameter pm = new Parameter();
////				pm.setName(n);
////				pm.setValue(f.getSql());
////				ps.addParameter(pm);
////			}
////		}
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__exp");
//			pm.setValue("DBFindByPK({"+table.getName()+"},{"+id+"},false,true)");
//			ps.addParameter(pm);
//		}
//		Metadata d = new Metadata();
//		d.setParameters(ps);
//		Haiyan g = new Haiyan();
//		g.addMetadata(d);
//		String xml = ConfigUtil.saveTable(g);
//		
//		call4.setXmlData(xml);
//		// stub.startcall(call4, new tempCallbackN1000C());
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub.CallResponse res = stub.call(call4);
//		//assertNotNull(res);
//		java.io.InputStream is = null;
//		is = res.getCallReturn().getInputStream();
//		// is = ((java.io.ByteArrayInputStream) res.getCallReturn().getContent());
//		String data = new String(ByteUtil.getBytes(is),"GBK");
////		if (data.startsWith("{")) {
////			JSONObject a = JSONObject.fromObject(data);
////			form=this.createRecord();
////			form.fromJSon(a);
////		} else
////			throw new Warning(data);
//
//		if(type==SQLDBManager.CACHE || type==SQLDBManager.DBSESSION) // DBManager.SESSION 出现这里因为当前为脏记录local cache被清掉
//		    this.updateCache(context.getDSN(), table, form, type);
//		
//		return form;
//	}
//	
//	public IResultSet findBy(final ITableContext context, final Table table, IDBFilter filter,
//			final int maxPageRecordCount, final int currPageNO,
//			int... args) throws Throwable {
//		Page p = new Page(new ArrayList<IRecord>());
//		
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub stub = //
//				new haiyan.localhost.haiyan_jws.HaiyanServiceStub(db.getURL());
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call call4 = //
//			(haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call)
//				haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call.class.newInstance();
//
//		Parameters ps = new Parameters();
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__sessionid");
//			pm.setValue(context.getSessionID());
//			ps.addParameter(pm);
//		}
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__action");
//			pm.setValue("exp");
//			ps.addParameter(pm);
//		}
////		{
////			String n = "__extendFilter."+table.getName();
////			Filter f = (Filter)context.getAttribute(n);
////			if (f!=null) {
////				Parameter pm = new Parameter();
////				pm.setName(n);
////				pm.setValue(f.getSql());
////				ps.addParameter(pm);
////			}
////		}
//		String n = DataConstant.PARAM_EXPPARA+1;
//		{
//			Parameter pm = new Parameter();
//			pm.setName(n);
//			pm.setValue(filter == null ? "" : filter.getSql());
//			ps.addParameter(pm);
//		}
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__exp");
//			pm.setValue("DBFindByFilter({"+table.getName()+"},GetPara("+n+"),"
//					+ maxPageRecordCount+","+currPageNO+",true)");
//			ps.addParameter(pm);
//		}
//		Metadata d = new Metadata();
//		d.setParameters(ps);
//		Haiyan g = new Haiyan();
//		g.addMetadata(d);
//		String xml = ConfigUtil.saveTable(g);
//		
//		call4.setXmlData(xml);
//		// stub.startcall(call4, new tempCallbackN1000C());
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub.CallResponse res = stub.call(call4);
//		//assertNotNull(res);
//		java.io.InputStream is = null;
//		is = res.getCallReturn().getInputStream();
//		// is = ((java.io.ByteArrayInputStream) res.getCallReturn().getContent());
//		String data = new String(ByteUtil.getBytes(is),"GBK");
//		if (data.startsWith("[")) {
//			JSONArray a = JSONArray.fromObject(data);
//			for (int i=0;i<a.size();i++){
//				IRecord form = new MapForm();
//				form.fromJSon(a.getJSONObject(i));
//				p.addData(form);
//			}
//		} else
//			throw new Warning(data);
//		return p;
//	}
//	
//	public Page findByForm(final Table table, IRecord filterForm,
//			final int maxPageRecordCount, final int currPageNO,
//			final ITableContext context, int... args) throws Throwable {
//		Page p = new Page(new ArrayList<IRecord>());
//		
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub stub = //
//				new haiyan.localhost.haiyan_jws.HaiyanServiceStub(db.getURL());
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call call4 = //
//			(haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call)
//				haiyan.localhost.haiyan_jws.HaiyanServiceStub.Call.class.newInstance();
//	
//		Parameters ps = new Parameters();
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__sessionid");
//			pm.setValue(context.getSessionID());
//			ps.addParameter(pm);
//		}
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__action");
//			pm.setValue("exp");
//			ps.addParameter(pm);
//		}
////		{
////			String n = "__extendFilter."+table.getName();
////			Filter f = (Filter)context.getAttribute(n);
////			if (f!=null) {
////				Parameter pm = new Parameter();
////				pm.setName(n);
////				pm.setValue(f.getSql());
////				ps.addParameter(pm);
////			}
////		}
//		String n = DataConstant.PARAM_EXPPARA+1;
//		{
//			Parameter pm = new Parameter();
//			pm.setName(n);
//			pm.setValue(filterForm == null ? "" : filterForm.toJSon().toString());
//			ps.addParameter(pm);
//		}
//		{
//			Parameter pm = new Parameter();
//			pm.setName("__exp");
//			pm.setValue("DBFindByForm({"+table.getName()+"},GetPara("+n+"),"
//					+ maxPageRecordCount+","+currPageNO+",true)");
//			ps.addParameter(pm);
//		}
//		Metadata d = new Metadata();
//		d.setParameters(ps);
//		Haiyan g = new Haiyan();
//		g.addMetadata(d);
//		String xml = ConfigUtil.saveTable(g);
//		
//		call4.setXmlData(xml);
//		// stub.startcall(call4, new tempCallbackN1000C());
//		haiyan.localhost.haiyan_jws.HaiyanServiceStub.CallResponse res = stub.call(call4);
//		//assertNotNull(res);
//		java.io.InputStream is = null;
//		is = res.getCallReturn().getInputStream();
//		// is = ((java.io.ByteArrayInputStream) res.getCallReturn().getContent());
//		String data = new String(ByteUtil.getBytes(is),"GBK");
//		if (data.startsWith("[")) {
//			JSONArray a = JSONArray.fromObject(data);
//			for (int i=0;i<a.size();i++){
//				IRecord form = this.createRecord();
//				form.fromJSon(a.getJSONObject(i));
//				p.addData(form);
//			}
//		} else 
//			throw new Warning(data);
//		return p;
//	}
//
//
//	@Override
//	public SQLTemplate getSQLRender() {
//		
//		return null;
//	}
//
//	@Override
//	public String SQLDateTimeFromStr(String sCurTime) {
//		
//		return null;
//	}
//
//	@Override
//	public String SQLCurrentTimeStamp(boolean stringType) {
//		
//		return null;
//	}
//
//	@Override
//	public String getCreateTableSQL(Table oSerTable) {
//		
//		return null;
//	}
//
//	@Override
//	public String genFieldSQL(AbstractField field) {
//		
//		return null;
//	}
//
//	@Override
//	public String genTypeSQL(AbstractField field) {
//		
//		return null;
//	}
//
//	@Override
//	public String genFieldName(AbstractField field) {
//		
//		return null;
//	}
//
//	@Override
//	protected String getValidateTablesSQL(String tableNames) {
//		
//		return null;
//	}
//
//	@Override
//	protected String getValidateTablesSQL(String[] tableNames) {
//		
//		return null;
//	}
//
//}
