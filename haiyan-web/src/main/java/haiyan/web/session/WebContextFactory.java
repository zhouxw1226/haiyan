package haiyan.web.session;

import haiyan.com.intf.web.IWebContext;
import haiyan.common.intf.factory.IFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * AppSession
 * 
 * @author ZhouXW
 *
 */
public class WebContextFactory implements IFactory {

	private WebContextFactory() { 
	} 
	public static IWebContext createDBContext(ServletRequest req, ServletResponse res) {
		IWebContext context = new WebContext(req, res);
		return context;
	} 
}
