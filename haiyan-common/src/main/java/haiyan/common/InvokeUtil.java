/*
 * Created on 2004-10-11
 */
package haiyan.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import haiyan.common.exception.Warning;

/**
 * java类结构反射工具
 * 
 * @author zhouxw
 */
@SuppressWarnings("rawtypes")
public class InvokeUtil {
	private final static HashMap<String, Object> objMap = new HashMap<String, Object>();
	/**
	 * @param className
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return String
	 * @throws Throwable
	 */
	public static String getString(String className, String staticMethodName,
			Class<?>[] methodParamTypes, Object[] paramValues) {
		String result = "";
		result = (String) invoke(className, staticMethodName, methodParamTypes, paramValues);
		return result;
	}
	/**
	 * @param className
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return boolean
	 * @throws Throwable
	 */
	public static boolean getBoolean(String className, String staticMethodName,
			Class<?>[] methodParamTypes, Object[] paramValues) {
		Boolean result;
		result = (Boolean) invoke(className, staticMethodName, methodParamTypes, paramValues);
		return result.booleanValue();
	}
	/**
	 * @param className
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return String
	 * @throws Throwable
	 */
	public static String getStringByInstance(Object obj, String methodName,
			Class<?>[] methodParamTypes, Object[] paramValues) {
		String result = "";
		result = (String) invokeByInstance(obj, methodName, methodParamTypes, paramValues);
		return result;
	}
	/**
	 * @param className
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return boolean
	 * @throws Throwable
	 */
	public static boolean getBooleanByInstance(Object obj, String methodName,
			Class<?>[] methodParamTypes, Object[] paramValues) {
		Boolean result;
		result = (Boolean) invokeByInstance(obj, methodName, methodParamTypes, paramValues);
		return result.booleanValue();
	}
	/**
	 * @param key
	 * @return
	 * @throws Throwable 
	 */
	public static Object getSingleBean(String key) {
		String className = PropUtil.getInvokeProperty(key);
		return newInstanceSingle(className);
	}
	/**
	 * @param key
	 * @return
	 * @throws Throwable 
	 */
	public static Object getBean(String key) {
		String className = PropUtil.getInvokeProperty(key);
		return newInstance(className);
	}
	/**
	 * @param cls
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(Class<?> cls) {
		return newInstance(cls, new Class[] {}, new Object[] {});
	}
	/**
	 * @param className
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(String className) {
		return newInstance(className, new Class[] {}, new Object[] {});
	}
	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(String className, Class[] paramTypes, Object[] paramValues) {
		if (StringUtil.isEmpty(className))
			return null;
		try {
			Class cls = Class.forName(className);
			return newInstance(cls, paramTypes, paramValues);
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(String className, Class[] paramTypes, Object[] paramValues, ClassLoader cp) {
		if (StringUtil.isEmpty(className))
			return null;
		try {
			Class cls = cp.loadClass(className);
			return newInstance(cls, paramTypes, paramValues);
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	/**
	 * @param cls
	 * @param paramTypes
	 * @param paramValues
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(Class cls, Class[] paramTypes, Object[] paramValues) {
		if (cls==null)
			return null;
		try {
			Object result = null;
			// String desc = "RttiUtil.newInstance()";
			if (paramTypes == null || paramValues == null)
				result = cls.newInstance();
			else {
				Constructor con = cls.getConstructor(paramTypes);
				result = con.newInstance(paramValues);
			}
			return result;
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	/**
	 * @param cls
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(Class<?> cls) {
		return newInstanceSingle(cls, new Class[] {}, new Object[] {});
	}
	/**
	 * @param className
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(String className) {
		return newInstanceSingle(className, new Class[] {}, new Object[] {});
	}
	/**
	 * @param cls
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(Class<?> cls, Class<?>[] paramTypes, Object[] paramValues) {
		if (cls==null)
			return null;
		try {
			String key = cls.getName();
			if (!objMap.containsKey(key)) {
				synchronized (objMap) {
					if (!objMap.containsKey(key)) {
						Object result = newInstance(cls, paramTypes, paramValues);
						objMap.put(key, result);
						return result;
					}
				}
			} 
			return objMap.get(cls.getName());
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(String className,
			Class<?>[] paramTypes, Object[] paramValues) {
		if (StringUtil.isEmpty(className))
			return null;
		//ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			//Thread.currentThread().setContextClassLoader(InvokeUtil.class.getClassLoader());
			Class cls = Class.forName(className);//,true,InvokeUtil.class.getClassLoader()//,Thread.currentThread().getContextClassLoader());
			return newInstanceSingle(cls, paramTypes, paramValues);
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}finally{
			//Thread.currentThread().setContextClassLoader(cl);
		}
	}
	/**
	 * static invoke
	 * 
	 * @param cls
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public static Object invoke(Class cls, String staticMethodName,
			Class[] methodParamTypes, Object[] paramValues) {
		if (cls==null)
			return null;
		try {
			Method m = cls.getMethod(staticMethodName, methodParamTypes);
			Object result = m.invoke(null, paramValues);
			return result;
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}
	/**
	 * static invoke
	 * 
	 * @param className
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object invoke(String className, String staticMethodName,
			Class[] methodParamTypes, Object[] paramValues) {
		if (StringUtil.isEmpty(className))
			return null;
		//ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			//Thread.currentThread().setContextClassLoader(InvokeUtil.class.getClassLoader());
			Class cls = Class.forName(className);//,true,InvokeUtil.class.getClassLoader()//,Thread.currentThread().getContextClassLoader());
			return invoke(cls, staticMethodName, methodParamTypes, paramValues);
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}finally{
			//Thread.currentThread().setContextClassLoader(cl);
		}
	}
	/**
	 * instance invoke
	 * 
	 * @param className
	 * @param staticMethodName
	 * @param methodParamNames
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeByInstance(Object obj, String methodName,
			Class[] methodParamTypes, Object[] paramValues) {
		if (obj==null)
			return null;
		try {
			Class cls = obj.getClass();
			Method m = cls.getMethod(methodName, methodParamTypes);
			Object result = m.invoke(obj, paramValues);
			return result;
		}catch(Throwable e){
			throw Warning.wrapException(e);
		}
	}

}
