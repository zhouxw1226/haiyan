package test.orm;

import haiyan.cache.CacheUtil;
import haiyan.cache.EHDataCache;
import haiyan.common.CloseUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.database.sql.TableDBManagerFactory;
import haiyan.orm.database.sql.UnitOfWorkDBManager;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.session.ITableDBContext;

import java.io.File;

import org.junit.Test;

public class TestDomainManager {

	public static void main(String[] args) throws Throwable {
		try {
			TestDomainManager t = new TestDomainManager();
			t.test1();
		} finally {
			System.exit(0);
		}
	}
	@Test
	public void test1() throws Throwable {
		System.out.println(System.getProperty("java.io.tmpdir"));
		{
			CacheUtil.setDataCache(new EHDataCache()); // 全局用缓存框架
			ConfigUtil.setDataCache(new EHDataCache()); // 配置用缓存框架
			ConfigUtil.setExpUtil(new ExpUtil()); // 全局用公式引擎
			ConfigUtil.setORMUseCache(true); // 开启ORM多级缓存
		}
		{
			File file;
			file = new File(System.getProperty("user.dir")
					+File.separator+"WEB-INF"+File.separator+"haiyan-config.xml");
			ConfigUtil.loadRootConfig(file);
			file = new File(TestDomainManager.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDomainManager.class.getResource("SYSORGA.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDomainManager.class.getResource("SYSOPERATOR.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDomainManager.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
		}
//		IClassContext contextClass = DBContextFactory.createClassContext();
//		IClassDBManager dbm = contextClass.getDBM();
//		record = dbm.select(context, class, id);
//		Table table = ConfigUtil.getTable(TEST_DBM.getClass());
		IUser user = TestUser.createUser();
		ISQLDatabase db = TableDBManagerFactory.createDatabase(user.getDSN());
		ITableDBManager dbm = new UnitOfWorkDBManager(db);
		ITableDBContext context = TableDBContextFactory.createDBContext(user);
		context.setDBM(dbm);
		Table table = ConfigUtil.getTable("TEST_DBM");
		try {
//			{
//				IExpUtil exp = new ExpUtil(context);
//				context.setExpUtil(exp);
//			}
			String id = "10000";
			IDBRecord record = null;
			//ITableDBManager dbm = context.getDBM();
			dbm.openTransaction();
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
			{
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.delete(context, table, new String[]{id});
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.rollback(context);
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			// ------------------------ rollback ------------------------ //
			{	
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
				double d3 = Math.random();
				record.set("CODE", "test"+d3);
				record.set("NAME", "test"+d3);
				record.set("STATUS", "4");
				record.setDirty();
				System.err.println(d3);
				System.err.println(record);
			}
			{
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.update(context, table, record);
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.commit(context);
			}
			// ------------------------ query ------------------------ //
			IDBRecord queryRow = dbm.createRecord();
			queryRow.set("NAME", "%create%");
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
		}catch(Throwable e){
			throw e;
		}finally{
			CloseUtil.close(context);
		}
	}

}
