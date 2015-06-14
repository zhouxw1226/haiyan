package haiyan.orm.intf.session;

import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IDBSession;
import haiyan.orm.intf.database.ITableDBManager;

/**
 * 基于表入口的DB上下文
 * 
 * @author ZhouXW
 *
 */
public interface ITableDBContext extends IDBSession, IContext {

	void setDBM(ITableDBManager dbm);
	ITableDBManager getDBM(); // 获取Table数据表入口级别的管理器
	Object getNextID(ITableConfig table) throws Throwable;
	
//	void setBBM(IBillDBManager bbm);
//	IBillDBManager getBBM(); // 获取Bill数据表入口级别的管理器
//	Object getNextID(IBillConfig bill) throws Throwable;
	
}
