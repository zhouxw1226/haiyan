package test.orm.nosql;

import java.io.File;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.junit.Test;

import haiyan.cache.CacheUtil;
import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;
import haiyan.common.cache.AppDataCache;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;
import test.orm.TestUser;

public class TestMongoDBM {

	public static void main(String[] args) throws Throwable {
		try {
			TestMongoDBM t = new TestMongoDBM();
			t.test1();
//			t.test0();
		} catch(Throwable e){
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
//	private static final InitialContextFactory _initialContextFactory = new InitialContextFactory(){
//		@Override
//		public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
//			return new CNCtx() {
//				
//			};
//		}
//	};
	@Test
	public void test0() throws Throwable {
		System.out.println(System.getProperty("java.io.tmpdir"));
		{
			CacheUtil.setDataCache(new AppDataCache()); // 全局用缓存框架
			ConfigUtil.setDataCache(new AppDataCache()); // 配置用缓存框架
			ConfigUtil.setExpUtil(new ExpUtil()); // 全局用公式引擎
			ConfigUtil.setORMUseCache(true); // 开启ORM多级缓存
//			Hashtable<String, String> ht = new Hashtable<String, String>();
//			ht.put(Context.INITIAL_CONTEXT_FACTORY, _initialContextFactory);
//			ht.put(Context.PROVIDER_URL, _providerURL);
//			Context initContext = new InitialContext(ht);
//			initContext.bind("java:comp/env/mongodb/haiyan", new INoSQLDataSource() {
//				@Override
//				public String getURL() throws NamingException {
//					return "localhost:27017";
//				}
//			});
		}
		{	
			File file;
			file = new File(System.getProperty("user.dir")
					+File.separator+"WEB-INF"+File.separator+"haiyan-config.xml");
			ConfigUtil.loadRootConfig(file);
			file = new File(TestUser.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("SYSORGA.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("SYSOPERATOR.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
		}
//		IClassContext contextClass = DBContextFactory.createClassContext();
//		IClassDBManager dbm = contextClass.getDBM();
//		record = dbm.select(context, class, id);
//		Table table = ConfigUtil.getTable(TEST_DBM.getClass());
		IUser user = TestUser.createUser();
		user.setDSN("mongodb");
		//ISQLDatabase db = TableDBManagerFactory.createDatabase(user.getDSN());
		//ITableDBManager dbm = new UnitOfWorkDBManager(db);
		ITableDBContext context = TableDBContextFactory.createDBContext(user);
		//context.setDBM(dbm);
		ITableDBManager dbm = context.getDBM();
		System.out.println(dbm);
	}
	@Test
	public void test1() throws Throwable {
		System.out.println(System.getProperty("java.io.tmpdir"));
		{
			CacheUtil.setDataCache(new AppDataCache()); // 全局用缓存框架
			ConfigUtil.setDataCache(new AppDataCache()); // 配置用缓存框架
			ConfigUtil.setExpUtil(new ExpUtil()); // 全局用公式引擎
			ConfigUtil.setORMUseCache(true); // 开启ORM多级缓存
//			Hashtable<String, String> ht = new Hashtable<String, String>();
//			ht.put(Context.INITIAL_CONTEXT_FACTORY, _initialContextFactory);
//			ht.put(Context.PROVIDER_URL, _providerURL);
//			Context initContext = new InitialContext(ht);
//			initContext.bind("java:comp/env/mongodb/haiyan", new INoSQLDataSource() {
//				@Override
//				public String getURL() throws NamingException {
//					return "localhost:27017";
//				}
//			});
		}
		{	
			File file;
			file = new File(System.getProperty("user.dir")
					+File.separator+"WEB-INF"+File.separator+"haiyan-config.xml");
			ConfigUtil.loadRootConfig(file);
			file = new File(TestUser.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("SYSORGA.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("SYSOPERATOR.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestUser.class.getResource("TEST_DBM2.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
		}
//		IClassContext contextClass = DBContextFactory.createClassContext();
//		IClassDBManager dbm = contextClass.getDBM();
//		record = dbm.select(context, class, id);
//		Table table = ConfigUtil.getTable(TEST_DBM.getClass());
		IUser user = TestUser.createUser();
		user.setDSN("mongodb");
		//ISQLDatabase db = TableDBManagerFactory.createDatabase(user.getDSN());
		//ITableDBManager dbm = new UnitOfWorkDBManager(db);
		ITableDBContext context = TableDBContextFactory.createDBContext(user);
		//context.setDBM(dbm);
		ITableDBManager dbm = context.getDBM();
		System.out.println(dbm);
		Table table = null;
		try {
			String id = new ObjectId().toHexString();
			IDBRecord record = null;
			boolean openTransaction = true;
			if (openTransaction) {
				dbm.openTransaction();
				table = ConfigUtil.getTable("TEST_DBM2");
			} else {
				table = ConfigUtil.getTable("TEST_DBM");
			}
			// ------------------------ select ------------------------ //
			record = dbm.select(context, table, id);
			if (record==null) {
				record = context.getDBM().createRecord();
				record.set("ID", id);
				record.set("CODE", "test1");
				record.set("NAME", "test1");
				record.set("STATUS", "1");
				dbm.insertNoSyn(context, table, record);
			}
			{
				//dbm.updateCache(context, table, record);
				System.err.println(record);
			}
			// ------------------------ delete ------------------------ //
			{
				double d1 = Math.random();
				record.set("CODE", "test"+d1);
				record.set("NAME", "test"+d1);
				record.set("STATUS", "2");
				record.setDirty();
				System.err.println(d1);
				System.err.println(record);
			}
//			{
//				record = dbm.select(context, table, id);
//				System.err.println(record);
//				dbm.delete(context, table, new String[]{id});
//				record = dbm.select(context, table, id);
//				System.err.println(record);
//				dbm.rollback(context);
//				record = dbm.select(context, table, id);
//				System.err.println(record);
//			}
			// ------------------------ rollback ------------------------ //
			{
//				if (record == null) {
//					record = dbm.createRecord();
//				}
				double d2 = Math.random();
				record.set("CODE", "test"+d2);
				record.set("NAME", "test"+d2);
				record.set("STATUS", "3");
				record.setDirty();
				System.err.println(d2);
				System.err.println(record);
			}
			{
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.rollback(context);
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			// ------------------------ update ------------------------ //
			{	
				if (record == null) {
					record = dbm.createRecord();
				}
				double d3 = Math.random();
				record.set("CODE", "test"+d3);
				record.set("NAME", "test"+d3);
				record.set("STATUS", "4");
				record.setDirty();
				System.err.println(d3);
				System.err.println(record);
			}
			{
//				record = dbm.select(context, table, id);
				record = dbm.insert(context, table, record);
//				System.err.println(record);
				dbm.update(context, table, record);
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.commit(context);
			}
			// ------------------------ query ------------------------ //
			IDBRecord queryRow = dbm.createRecord();
			String word = "test";
			Pattern pattern = Pattern.compile("^.*" + word + ".*$", Pattern.CASE_INSENSITIVE);
			queryRow.set("NAME", pattern);
			{
				long count = dbm.countBy(context, table, queryRow);
				System.err.println("count:"+count);
			}
			{
				IDBResultSet resultSet = dbm.select(context, table, queryRow, 5000, 1);
				for (IDBRecord row:resultSet.getRecords()) {
					System.err.println(row.get("ID"));
				}
			}
			{
				IDBRecordCallBack callback = new IDBRecordCallBack() {
					@Override
					public void call(IDBRecord row) {
						System.err.println(row.get("ID"));
					}
				};
				dbm.loopBy(context, table, queryRow, callback);
			}
			DebugUtil.debug("success");
		}finally{
			CloseUtil.close(context);
		}
	}
}
