package test.orm;

import haiyan.cache.CacheUtil;
import haiyan.cache.EHDataCache;
import haiyan.common.CloseUtil;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.database.ITableDBManager;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.DBContextFactory;
import haiyan.orm.database.sql.DBManagerFactory;

import java.io.File;
import java.util.UUID;

import org.junit.Test;

public class TestDBManager {

	public static void main(String[] args) throws Throwable {
		TestDBManager t = new TestDBManager();
		t.test1();
		System.exit(0);
	}
	@Test
	public void test1() throws Throwable {
		{
//			IDataCache cache = new RedisDataCacheRemote();
//			cache.setServers(PropUtil.getProperty("REDISCACHE.SERVERS").split(";"));
//			cache.initialize();
//			CacheUtil.setDataCache(cache); // 全局用缓存框架
//			CacheUtil.setDataCache(new EHDataCache()); // 全局用缓存框架
//			ConfigUtil.setDataCache(new EHDataCache()); // 配置用缓存框架
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
			file = new File(TestDBManager.class.getResource("SYS.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDBManager.class.getResource("SYSORGA.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDBManager.class.getResource("SYSOPERATOR.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
			file = new File(TestDBManager.class.getResource("TEST_DBM.xml").getPath());
			ConfigUtil.loadTableConfig(file, true);
		}
//		IClassContext contextClass = DBContextFactory.createClassContext();
//		IClassDBManager dbm = contextClass.getDBM();
//		record = dbm.select(context, class, id);
//		Table table = ConfigUtil.getTable(TEST_DBM.getClass());
		IUser user = TestUser.createUser();
		ITableDBContext context = DBContextFactory.createDBContext(user);
		Table table = ConfigUtil.getTable("TEST_DBM");
		try {
//			{
//				IExpUtil exp = new ExpUtil(context);
//				context.setExpUtil(exp);
//			}
			String id = UUID.randomUUID().toString();
			IDBRecord record = null;
			ITableDBManager dbm = context.getDBM();
			dbm.openTransaction();
			// ------------------------ insert ------------------------ //
			{
				record = context.getDBM().createRecord();
				record.set("ID", id);
				record.set("CODE", "test1");
				record.set("NAME", "test1");
				record.set("STATUS", "1");
				dbm.insertNoSyn(context, table, record);
			}
			// ------------------------ select ------------------------ //
			{
				record = dbm.select(context, table, id);
				//dbm.updateCache(context, table, record);
				System.err.println(record);
				
				IDBResultSet rst = dbm.select(context, table, new String[]{id,id});
				//dbm.updateCache(context, table, record);
				System.err.println(rst);
			}
			// ------------------------ update ------------------------ //
			{
				double d1 = Math.random();
				record.set("CODE", "test"+d1);
				record.set("NAME", "test"+d1);
				record.set("STATUS", "2");
				record.delete("NAME");
				//record.setDirty();
				System.err.println(d1);
				System.err.println(record);
			}
			{
				dbm.update(context, table, record);
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.rollback();
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			{
				if (record==null) {
					record = context.getDBM().createRecord();
					record.set("ID", id);
					record.set("CODE", "test1");
					record.set("NAME", "test1");
					record.set("STATUS", "1");
					dbm.insertNoSyn(context, table, record);
					dbm.commit();
				}
				record = dbm.select(context, table, id);
				System.err.println(record);
				double d1 = Math.random();
				record.set("CODE", "test"+d1);
				record.set("NAME", "test"+d1);
				record.set("STATUS", "2");
				record.delete("NAME");
				//record.setDirty();
				System.err.println(d1);
				System.err.println(record);
			}
			{
				dbm.update(context, table, record);
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.commit();
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			// ------------------------ delete ------------------------ //
			{
				dbm.delete(context, table, new String[]{id});
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			// ------------------------ insert ------------------------ //
			{
				if (record==null) {
					record = context.getDBM().createRecord();
					record.set("ID", id);
					record.set("CODE", "test1");
					record.set("NAME", "test1");
					record.set("STATUS", "1");
					dbm.insertNoSyn(context, table, record);
				}
			}
			// ------------------------ select ------------------------ //
			{
				record = dbm.select(context, table, id);
				//dbm.updateCache(context, table, record);
				System.err.println(record);
			}
			// ------------------------ update and commit ------------------------ //
			{
				double d1 = Math.random();
				record.set("CODE", "test"+d1);
				record.set("NAME", "test"+d1);
				record.set("STATUS", "2");
				//record.setDirty();
				System.err.println(d1);
				System.err.println(record);
			}
			{
				dbm.update(context, table, record);
				dbm.commit();
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			// ------------------------ delete and rollback ------------------------ //
			{
				dbm.delete(context, table, new String[]{id});
				record = dbm.select(context, table, id);
				System.err.println(record);
				dbm.rollback();
				record = dbm.select(context, table, id);
				System.err.println(record);
			}
			// ------------------------ query ------------------------ //
			IDBRecord queryRow = dbm.createRecord();
			queryRow.set("NAME", "%test%");
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
				//dbm.close();
				ITableDBManager dbm2 = (ITableDBManager)DBManagerFactory.createDBManager("ORACLE");
				context.setDBM(dbm2); // auto close dbm
				dbm2.loopBy(context, table, queryRow, callback);
				CloseUtil.close(dbm2);
			}
		}finally{
			CloseUtil.close(context);
		}
	}

}
