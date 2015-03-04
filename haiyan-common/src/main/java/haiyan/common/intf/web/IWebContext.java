package haiyan.common.intf.web;

import haiyan.common.intf.session.IContext;
import haiyan.common.intf.session.IWebSession;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

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

//	HttpServletRequest getServletRequest();
//	HttpServletResponse getServletResponse();
}
