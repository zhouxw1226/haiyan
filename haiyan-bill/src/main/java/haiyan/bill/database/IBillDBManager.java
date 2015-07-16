package haiyan.bill.database;

import haiyan.bill.database.sql.IBillDBContext;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBFilter;
import haiyan.common.intf.database.sql.ISQLDBManager;

/**
 * 单据管理器 既是DB管理器也是功能程序上下文
 * 
 * 基于IDBBill的Manager
 * 
 * @author ZhouXW
 *
 */
public interface IBillDBManager extends ISQLDBManager {

	IDBBill createBill(IBillDBContext context, IBillConfig billConfig, boolean createBillID) throws Throwable;
	IDBBill createBill(IBillDBContext context, IBillConfig billConfig) throws Throwable;
	Object createBillID(IBillDBContext context, IDBBill bill) throws Throwable;
	
	void loadBill(IBillDBContext context, IDBBill bill) throws Throwable;
	void saveBill(IBillDBContext context, IDBBill bill) throws Throwable;
	void deleteBill(IBillDBContext context, IDBBill bill) throws Throwable;
	void deleteBill(IBillDBContext context, IBillConfig billConfig, String billID) throws Throwable;

	IDBResultSet query(IBillDBContext context, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter, 
			int pageRowCount, int pageIndex, boolean override) throws Throwable;
	IDBResultSet queryNext(IBillDBContext context, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter,
			int pageRowCount, boolean override) throws Throwable;
	IDBResultSet queryPrev(IBillDBContext context, IDBBill bill, int tableIndex, ISQLDBFilter dbFilter,
			int pageRowCount, boolean override) throws Throwable;
}
