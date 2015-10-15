package haiyan.common.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haiyan.common.CloseUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IAppContext;
import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IUser;

/**
 * 功能程序上下文
 * 
 * @author ZhouXW
 *
 */
public class AppContext implements IAppContext {

	public AppContext() { 
	}
	protected IContext parent;
	public AppContext(IContext parent) { 
		this.parent = parent;
		if (parent != null) {
			this.user = parent.getUser();
			this.DSN = parent.getDSN();
		}
	}
	protected Boolean alive;
	@Override
	public Boolean isAlive() {
		return alive;
	}
	public void setAlive(Boolean alive) {
		this.alive = alive;
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
		if (this.exp!=null)
			throw new Warning("exp exists");
		this.exp = exp;
	}
	@Override
	public IExpUtil getExpUtil() {
		return this.exp;
	}
	private String DSN;
	@Override
	public void setDSN(String DSN) {
		this.DSN = DSN;
	}
	@Override
	public String getDSN() {
		return this.DSN;
	}
	private IUser user;
	@Override
	public void setUser(IUser user) {
		this.user = user;
	}
	@Override
	public IUser getUser() {
		return this.user;
	}
	@Override
	public IContext getParent() {
		return parent;
	}
	@Override
	public void close() {
//		this.release();
		this.parent = null;
		CloseUtil.close(this.exp);
		this.exp = null;
		this.atts.clear();
		this.user = null;
	}
	@Override
	public void release() {
		// NOTICE 不能在子context中关闭父亲的上下文
//		if (this.parent!=null)
//			this.parent.release();
//		this.parent = null;
	}
	@Override
	public void clear() {
	}
}
