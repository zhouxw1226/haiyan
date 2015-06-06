package haiyan.bill.database.sql;

import haiyan.common.exception.Warning;
import haiyan.common.intf.database.bill.IDBBillManager;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;

public class DBBillManagerFactory implements IFactory {

	private DBBillManagerFactory() {
	}
	public static IDBBillManager createDBBillManager(IContext context)  {
		try {
			return new SQLDBBillManager(context);
		}catch(Throwable ex){
			throw Warning.wrapException(ex);
		}
	}

}
