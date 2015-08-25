package haiyan.bill.database.sql;

import haiyan.bill.database.IBillDBManager;
import haiyan.common.SysCode;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDatabase;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.common.intf.factory.IFactory;
import haiyan.orm.database.sql.AbstractDBManagerFactory;

public class BillDBManagerFactory extends AbstractDBManagerFactory implements IFactory {

	private BillDBManagerFactory() {
	}
	public static IBillDBManager createDBManager(String DSN)  {
		try {
			IDatabase database = createDatabase(DSN);
			if (database==null)
				throw new Warning(SysCode.SysCodeNum.NO_MATCHEDSQLDATABASE,SysCode.SysCodeMessage.NO_MATCHEDSQLDATABASE);
			return new SQLBillDBManager((ISQLDatabase)database);
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}

}
