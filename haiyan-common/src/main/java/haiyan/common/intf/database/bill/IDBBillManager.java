package haiyan.common.intf.database.bill;

import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.session.IDBSession;

/**
 * 单据管理器 既是DB管理器也是功能程序上下文
 * 
 * 基于IDBBill的Manager
 * 
 * @author ZhouXW
 *
 */
public interface IDBBillManager extends IDBSession {

	IDBBill createBill(IBillConfig billConfig, boolean createBillID) throws Throwable;
	IDBBill createBill(IBillConfig billConfig) throws Throwable;
	Object createBillID(IDBBill bill) throws Throwable;
	
	void loadBill(IDBBill bill) throws Throwable;
	void saveBill(IDBBill bill) throws Throwable;
	void deleteBill(IDBBill bill) throws Throwable;
	void deleteBill(IBillConfig billConfig, String billID) throws Throwable;

}
