package haiyan.web.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLDecoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import haiyan.common.SecurityUtil;
import haiyan.common.StringUtil;
import haiyan.common.XmlUtil;
import haiyan.common.intf.web.IWebContext;
import haiyan.common.session.AppContext;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	@Override
	public JSONArray getJSONArrayParameter(String key, String decode) throws Throwable {
		return getJSONArrayParameter(key, decode, false);
	}
	@Override
	public JSONObject getJSONParameter(String key, String decode) throws Throwable {
		return getJSONParameter(key, decode, false);
	}
	@Override
	public JSONArray getJSONArrayParameter(String key, String decode, boolean checkSign) throws Throwable {
		String sData = req.getParameter(key);
		if (sData==null)
			return null;
		if (!StringUtil.isEmpty(decode))
			sData = URLDecoder.decode(sData, decode);
		if (checkSign) {
			SecurityUtil.checkSign(this, sData);
		}
		JSONArray jsonData = JSONArray.fromObject(sData);
		return jsonData;
	}
	@Override
	public JSONObject getJSONParameter(String key, String decode, boolean checkSign) throws Throwable {
		String sData = req.getParameter(key);
		if (sData==null)
			return null;
		if (!StringUtil.isEmpty(decode))
			sData = URLDecoder.decode(sData, decode);
		if (checkSign) {
			SecurityUtil.checkSign(this, sData);
		}
		JSONObject jsonData = JSONObject.fromObject(sData);
		return jsonData;
	}
	@Override
	public JSONObject getJSONBody() throws Throwable {
		BufferedReader reader = req.getReader(); 
		StringBuilder sb = new StringBuilder();
        String line = null;  
        while ((line = reader.readLine()) != null) {  
            sb.append(line);
        }
        String datas = sb.toString();
        return JSONObject.fromObject(datas);
	}
	@Override
	public JSONArray getJSONArrayBody() throws Throwable {
		BufferedReader reader = req.getReader(); 
		StringBuilder sb = new StringBuilder();
        String line = null;  
        while ((line = reader.readLine()) != null) {  
            sb.append(line);
        }
        String datas = sb.toString();
        return JSONArray.fromObject(datas);
	}
	@Override
	public JSON getXML2JSONBody() throws Throwable {
		BufferedReader reader = req.getReader(); 
		StringBuilder sb = new StringBuilder();
        String line = null;  
        while ((line = reader.readLine()) != null) {  
            sb.append(line);
        }
        String datas = sb.toString();
        return XmlUtil.xmltoJson(datas);
	}
}
