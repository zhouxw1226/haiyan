package haiyan.bill;

import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.AppUser;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBContextFactory;
import haiyan.orm.database.sql.DBBillAutoID;

import java.math.BigDecimal;

public class TestDBBillAutoID {

	public static void main(String[] args) throws Throwable {
		TestLoadConfig.loadConfig();
		IUser user = new AppUser();
		user.setDSN("MYSQL");
		IContext context = DBContextFactory.createDBContext(user);
		System.out.println("-----------------");
		for (int i = 0; i < 10; i++) {
			BigDecimal id = DBBillAutoID.genNewID(context, ConfigUtil.getTable("TEST_DBM"), 100);
			System.out.println(i + ":" + id);
		}
		System.out.println("-----------------");
		for (int i = 0; i < 90; i++) {
			BigDecimal id = DBBillAutoID.genNewID(context, ConfigUtil.getTable("TEST_DBM"), 10);
			System.out.println(i + ":" + id);
		}
		System.out.println("-----------------");
		for (int i = 0; i < 90; i++) {
			String id = DBBillAutoID.genShortID(context, ConfigUtil.getTable("TEST_DBM"), 1);
			System.out.println(i + ":" + id);
		}
		System.out.println("-----------------");
//		for (int i = 0; i < 90; i++) {
//			String id = DBBillAutoID.shortUrl(i);
//			System.out.println(i + ":" + id);
//		}
		context.close();
	}

}
