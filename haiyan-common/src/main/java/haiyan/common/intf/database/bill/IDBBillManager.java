package haiyan.common.intf.database.bill;

import java.math.BigDecimal;

import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.session.IDBSession;

/**
 * 单据管理器 既是DB管理器也是功能程序上下文
 * 
 * @author ZhouXW
 *
 */
public interface IDBBillManager extends IDBSession {

	IDBBill createBill(IBillConfig billConfig);
	void loadBill(IDBBill bill) throws Throwable;
	BigDecimal createBillID(IDBBill bill) throws Throwable;
	void saveBill(IDBBill bill) throws Throwable;
	void deleteBill(IDBBill bill) throws Throwable;
	void deleteBill(String billID) throws Throwable;

}
