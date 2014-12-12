package haiyan.common.session;

import haiyan.common.CloseUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.database.IDBManager;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IAppContext;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 功能程序上下文
 * 
 * @author ZhouXW
 *
 */
public class AppContext implements IAppContext {

	public AppContext() { 
	}
	private Boolean alive;
	@Override
	public Boolean isAlive() {
		return alive;
	}
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	private IContext parent;
	public AppContext(IContext parent) { 
		this.parent=parent;
		this.user=parent.getUser();
		this.DSN=parent.getDSN();
	}
	private Map<String,Object> atts = new HashMap<String, Object>();
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
	private IDBManager dbm;
	@Override
	public void setDBM(IDBManager dbm) {
		if (this.dbm!=null && this.dbm.isAlive())
			CloseUtil.close(this.dbm);
		this.dbm = dbm;
	}
	@Override
	public IDBManager getDBM() {
		return dbm;
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
		return user;
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
	public Object getNextID(String tableName) {
		return UUID.randomUUID().toString();
	}
	@Override
	public void close() {
		CloseUtil.close(this.dbm);
		CloseUtil.close(this.exp);
		this.atts.clear();
		this.parent=null;
		this.user = null;
		this.dbm = null;
		this.exp = null;
	}
	@Override
	public void clear() {
		if (this.dbm!=null)
			this.dbm.clear();
	}
}
