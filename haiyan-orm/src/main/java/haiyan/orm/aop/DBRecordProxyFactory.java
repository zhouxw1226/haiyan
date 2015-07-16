package haiyan.orm.aop;

import haiyan.common.intf.database.orm.IDBRecordBean;
import haiyan.orm.database.DBRecord;
import haiyan.orm.database.DBRecordUtil;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DBRecordProxyFactory {
	private static MethodInterceptor PROXY = new DBRecordMethodInterceptorImpl();
	@SuppressWarnings("rawtypes")
	public static IDBRecordBean createDBRecordProxy(Class clz) {
		Enhancer en = new Enhancer();  
	    en.setSuperclass(clz); //进行代理  
	    en.setCallback(PROXY); //生成代理实例  
	    return (IDBRecordBean)en.create();
	}
	private static class DBRecordMethodInterceptorImpl implements MethodInterceptor {
		@Override
	    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)  
	            throws Throwable {
			String methodName = method.getName();
			if (methodName.toLowerCase().startsWith("set")) {
				if (!methodName.equalsIgnoreCase("setClass")) {
					if (args.length>0) {
						String attrName = methodName.substring(3);
						DBRecordUtil.put(((DBRecord)obj),attrName.toUpperCase(),args[0]);
					}
					return null;
				}
			} else if (methodName.toLowerCase().startsWith("get")) {
				if (!methodName.equalsIgnoreCase("getClass")) {
					String attrName = methodName.substring(3); 
					Object v = DBRecordUtil.get(((DBRecord)obj),attrName.toUpperCase());
					return v;
				}
			}
	        return proxy.invokeSuper(obj, args);
	    }  
	}
}  
