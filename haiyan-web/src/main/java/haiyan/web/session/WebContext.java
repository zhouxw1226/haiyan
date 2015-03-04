package haiyan.web.session;

import haiyan.common.StringUtil;
import haiyan.common.intf.web.IWebContext;
import haiyan.common.session.AppContext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class WebContext extends AppContext implements IWebContext {
	private ServletRequest req;
	private ServletResponse res;
	public WebContext(ServletRequest req, ServletResponse res) {
		this.req = req;
		this.res = res;
	}
	public ServletRequest getServletRequest() {
		return req;
	}
	public ServletResponse getServletResponse() {
		return res;
	}
	@Override
	public Writer getWriter() throws IOException {
		return res.getWriter();
	}
	@Override
	public OutputStream getOutputStream() throws IOException {
		return res.getOutputStream();
	}
	@Override
	public Object getParameter(String key) {
		return req.getParameter(key);
	} 
	@Override
	public Object removeAttribute(String key) {
		Object o = req.getAttribute(key);
		req.removeAttribute(key);
		return o;
	}
	@Override
	public void setAttribute(String key, Object value) {
		req.setAttribute(key, value);
	}
	@Override
	public Object getAttribute(String key) {
		Object o = req.getAttribute(key);
		if (StringUtil.isEmpty(o))
			o = req.getParameter(key);
		return o;
	}
	@Override
	public boolean hasAttribute(String key) {
		Object o = req.getAttribute(key);
		if (StringUtil.isEmpty(o))
			o = req.getParameter(key);
		return o!=null;
	}
	@Override
	public Boolean isAlive() {
		return req!=null && res!=null && !res.isCommitted();
	}
	@Override
	public void close() {
		super.close();
		req = null;
		res = null;
	}
}
