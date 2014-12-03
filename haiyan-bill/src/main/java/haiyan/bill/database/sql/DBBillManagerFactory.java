package haiyan.bill.database.sql;

import haiyan.bill.database.DBBill;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.database.IDBBill;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.bill.IDBBillManager;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.factory.IFactory;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.BillID;
import haiyan.config.castorgen.BillTable;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBPage;

import java.math.BigDecimal;
import java.util.Iterator;

public class DBBillManagerFactory implements IFactory {

	private DBBillManagerFactory() {
	}
	private static class SQLDBBillManager implements IDBBillManager {
		private ITableDBContext context;
		protected SQLDBBillManager(IContext context) {
			this.context = (ITableDBContext)context;
		}
		@Override
		public void openTransaction() throws Throwable {
			this.context.openTransaction();
		}
		@Override
		public void setAutoCommit(boolean b) throws Throwable {
			this.context.setAutoCommit(b);
		}
		@Override
		public void commit() throws Throwable {
			this.context.commit();
		}
		@Override
		public void rollback() throws Throwable {
			this.context.rollback();
		}
		@Override
		public String getDSN() {
			return this.context.getDSN();
		}
		@Override
		public boolean isAlive() {
			return context.isAlive();
		}
		@Override
		public void clear() {
			this.context.clear();
		}
		@Override
		public void close() {
			// TODO SQLDBBillManager.close();
		}
		@Override
		public IDBBill createBill(IBillConfig billConfig) {
			IDBBill bill = new DBBill(this.context.getUser(), billConfig);
			return bill;
		} 
		@Override
		public BigDecimal createBillID(IDBBill bill) throws Throwable {
			IDBResultSet[] resultSets = bill.getResultSets();
			Bill billConfig = (Bill)bill.getBillConfig();
			Table mainTable = ConfigUtil.getMainTable(billConfig);
			{
				BigDecimal billID = DBBillAutoNumber.requestID(this.context, mainTable, 1);
				bill.setBillID(billID);
			}
			BillTable[] billTables = billConfig.getBillTable();
			for (int i=0;i<billTables.length;i++) {
//				BillTable billTable = billTables[i];
//				Table table = ConfigUtil.getTable(billTable.getDbName());
//				resultSets[i] = context.getDBM().select(context, table, filters[i], DBPage.MAXCOUNTPERQUERY, 1);
				IDBResultSet rst = resultSets[i];
				Iterator<IDBRecord> iter = rst.getRecords().iterator(); 
				while(iter.hasNext()) {
					IDBRecord record = iter.next();
					BillID billIDConfig = billConfig.getBillID(i);
					record.set(billIDConfig.getDbName(), bill.getBillID());
				}
			}
			return (BigDecimal)bill.getBillID();
		}
		@Override
		public void loadBill(IDBBill bill) throws Throwable {
			IDBResultSet[] resultSets = bill.getResultSets();
			IDBFilter[] filters = bill.getFilters();
			Bill billConfig = (Bill)bill.getBillConfig();
			BillTable[] billTables = billConfig.getBillTable();
			for (int i=0;i<billTables.length;i++) {
				BillTable billTable = billTables[i];
				Table table = ConfigUtil.getTable(billTable.getDbName());
				resultSets[i] = context.getDBM().select(context, table, filters[i], DBPage.MAXCOUNTPERQUERY, 1);
			}
		}
		@Override
		public void saveBill(IDBBill bill) throws Throwable {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void deleteBill(IDBBill bill) throws Throwable {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void deleteBill(String billID) throws Throwable {
			// TODO Auto-generated method stub
			
		}
	}
	public static IDBBillManager createBillManager(IContext context)  {
		try {
			return new SQLDBBillManager(context);
		}catch(Throwable e){
			throw new RuntimeException(e);
		}
	}

}
