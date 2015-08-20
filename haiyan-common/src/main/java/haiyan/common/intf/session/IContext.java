package haiyan.common.intf.session;

import haiyan.common.intf.exp.IExpUtil;

import java.util.List;

/**
 * 抽象上下文接口
 * 
 * @author ZhouXW
 *
 */
public interface IContext extends ISession {

	IContext getParent();
	void setExpUtil(IExpUtil exp);
	IExpUtil getExpUtil();
	Object removeAttribute(String key);
	Object getAttribute(String key);
	boolean hasAttribute(String key);
	void setAttribute(String key, Object val);
	void addException(Throwable e);
	List<Throwable> getExceptions();
	
//	void setDBM(ITableDBManager dbm);
//	ITableDBManager getDBM();
//	void setBBM(IBillDBManager bbm);
//	IBillDBManager getBBM();
	void setUser(IUser user);
	IUser getUser();
	void setDSN(String DSN);
	String getDSN();
	void clear();
	void close();

}
