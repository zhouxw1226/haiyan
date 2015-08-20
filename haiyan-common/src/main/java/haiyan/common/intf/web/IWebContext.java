package haiyan.common.intf.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IWebSession;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Web程序上下文
 * 
 * @author ZhouXW
 *
 */
public interface IWebContext extends IWebSession, IContext {
	OutputStream getOutputStream() throws IOException;
	Writer getWriter() throws IOException;
	Object getParameter(String key);
	JSONObject getJSONParameter(String key,String decode) throws Throwable;
	JSONArray getJSONArrayParameter(String key, String decode) throws Throwable;
	JSONObject getJSONParameter(String key,String decode, boolean checkSign) throws Throwable;
	JSONArray getJSONArrayParameter(String key, String decode, boolean checkSign) throws Throwable;
	JSONObject getJSONBody() throws Throwable;
	JSONArray getJSONArrayBody() throws Throwable;
	JSON getXML2JSONBody() throws Throwable;
//	HttpServletRequest getServletRequest();
//	HttpServletResponse getServletResponse();
}
