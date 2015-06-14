package haiyan.bill.database.sql;

import haiyan.bill.database.IBillDBManager;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IDBSession;
import haiyan.orm.intf.session.ITableDBContext;

public interface IBillDBContext extends IDBSession, IContext {
	void setBBM(IBillDBManager bbm);
	IBillDBManager getBBM(); // 获取Table数据表入口级别的管理器
	Object getNextID(IBillConfig bill) throws Throwable;

	ITableDBContext getTableDBContext(int tableIndex);
	void setTableDBContext(int tableIndex, ITableDBContext context);
}
