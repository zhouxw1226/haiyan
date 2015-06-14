package haiyan.common.session;

import haiyan.common.CloseUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IAppContext;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能程序上下文
 * 
 * @author ZhouXW
 *
 */
public class AppContext implements IAppContext {

	public AppContext() { 
	}
	protected Boolean alive;
	@Override
	public Boolean isAlive() {
		return alive;
	}
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	protected IContext parent;
	public AppContext(IContext parent) { 
		this.parent=parent;
		if (parent!=null) {
			this.user=parent.getUser();
			this.DSN=parent.getDSN();
		}
	}
	protected Map<String,Object> atts = new HashMap<String, Object>();
	@Override
	public Object removeAttribute(String key) {
		return atts.remove(key);
	}
	@Override
	public void setAttribute(String key, Object value) {
		atts.put(key, value);
	}
	@Override
	public Object getAttribute(String key) {
		Object v = atts.get(key);
		if (v!=null)
			return v;
		if (parent!=null)
			return parent.getAttribute(key);
		return v;
	}
	@Override
	public boolean hasAttribute(String key) {
		boolean b = atts.containsKey(key);
		if (b) 
			return b;
		if (parent!=null)
			return parent.hasAttribute(key);
		return false;
	}
	private List<Throwable> exceptions = new ArrayList<Throwable>();
	@Override
	public void addException(Throwable e) {
		exceptions.add(e);
	}
	@Override
	public List<Throwable> getExceptions() {
		return exceptions;
	}
	private IExpUtil exp;
	@Override
	public void setExpUtil(IExpUtil exp) {
		this.exp = exp;
	}
	@Override
	public IExpUtil getExpUtil() {
		return this.exp;
	}
	private IUser user;
	@Override
	public void setUser(IUser user) {
		this.user = user;
	}
	@Override
	public IUser getUser() {
		if (this.user!=null)
			return user;
		if (this.parent!=null)
			return this.parent.getUser();
		return null;
	}
	private String DSN;
	@Override
	public void setDSN(String DSN) {
		this.DSN = DSN;
	}
	@Override
	public String getDSN() {
		return !StringUtil.isBlankOrNull(this.DSN)?this.DSN:(user!=null?user.getDSN():null);
	}
	@Override
	public void close() {
		CloseUtil.close(this.exp);
		this.atts.clear();
		this.parent=null;
		this.user = null;
		this.exp = null;
	}
	@Override
	public void clear() {
	}
//	@Override
//	public void setDBM(ITableDBManager dbm) {
//		// TODO Auto-generated method stub
//	}
//	@Override
//	public ITableDBManager getDBM() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public void setBBM(IBillDBManager bbm) {
//		// TODO Auto-generated method stub
//	}
//	@Override
//	public IBillDBManager getBBM() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
