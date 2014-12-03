/*
 * Created on 2004-10-11
 */
package haiyan.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * java类结构反射工具
 * 
 * @author zhouxw
 */
@SuppressWarnings("rawtypes")
public class InvokeUtil {

	/**
	 * 
	 */
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
			Class<?>[] methodParamTypes, Object[] paramValues) throws Throwable {
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
			Class<?>[] methodParamTypes, Object[] paramValues) throws Throwable {
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
			Class<?>[] methodParamTypes, Object[] paramValues) throws Throwable {
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
			Class<?>[] methodParamTypes, Object[] paramValues) throws Throwable {
		Boolean result;
		result = (Boolean) invokeByInstance(obj, methodName, methodParamTypes, paramValues);
		return result.booleanValue();
	}

	/**
	 * @param cls
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(Class<?> cls) throws Throwable {
		return newInstance(cls, new Class[] {}, new Object[] {});
	}

	/**
	 * @param className
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(String className) throws Throwable {
		return newInstance(className, new Class[] {}, new Object[] {});
	}

	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(String className, Class[] paramTypes,
			Object[] paramValues) throws Throwable {
		Class cls = Class.forName(className);
		// Object result = null;
		// // String desc = "RttiUtil.newInstance()";
		// if (paramTypes == null || paramValues == null)
		// result = cls.newInstance();
		// else {
		// Constructor con = cls.getConstructor(paramTypes);
		// result = con.newInstance(paramValues);
		// }
		// return result;
		return newInstance(cls, paramTypes, paramValues);
	}
	
	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstance(String className, Class[] paramTypes,
			Object[] paramValues, ClassLoader cp) throws Throwable {
		Class cls = cp.loadClass(className);// Class.forName(className);
		// Object result = null;
		// // String desc = "RttiUtil.newInstance()";
		// if (paramTypes == null || paramValues == null)
		// result = cls.newInstance();
		// else {
		// Constructor con = cls.getConstructor(paramTypes);
		// result = con.newInstance(paramValues);
		// }
		// return result;
		return newInstance(cls, paramTypes, paramValues);
	}

	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(Class cls, Class[] paramTypes,
			Object[] paramValues) throws Throwable {
		Object result = null;
		// String desc = "RttiUtil.newInstance()";
		if (paramTypes == null || paramValues == null)
			result = cls.newInstance();
		else {
			Constructor con = cls.getConstructor(paramTypes);
			result = con.newInstance(paramValues);
		}
		return result;
	}

	/**
	 * @param cls
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(Class<?> cls) throws Throwable {
		return newInstanceSingle(cls, new Class[] {}, new Object[] {});
	}

	/**
	 * @param className
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(String className) throws Throwable {
		return newInstanceSingle(className, new Class[] {}, new Object[] {});
	}

	/**
	 * @param cls
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(Class<?> cls, Class<?>[] paramTypes,
			Object[] paramValues) throws Throwable {
		if (!objMap.containsKey(cls.getName())) {
			synchronized (objMap) {
				if (!objMap.containsKey(cls.getName())) {
					Object result = newInstance(cls, paramTypes, paramValues);
					objMap.put(cls.getName(), result);
					return result;
				}
			}
		} 
		return objMap.get(cls.getName());
	}

	/**
	 * @param className
	 * @param paramTypes
	 * @param paramValues
	 * @return Object
	 * @throws Throwable
	 */
	public static Object newInstanceSingle(String className,
			Class<?>[] paramTypes, Object[] paramValues) throws Throwable {
		// Object result = null;
		// // String desc = "RttiUtil.newInstanceSingle()";
		// if (objMap.get(className) == null) {
		// synchronized (objMap) {
		// result = newInstance(className, paramTypes, paramValues);
		// // Class.forName(className).newInstance();
		// objMap.put(className, result);
		// }
		// } else {
		// result = objMap.get(className);
		// }
		// return result;
		Class<?> cls = Class.forName(className);
		return newInstanceSingle(cls, paramTypes, paramValues);
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
			Class[] methodParamTypes, Object[] paramValues) throws Throwable {
		Object result = null;
		// String desc = "RttiUtil.invoke()";
		Method m = cls.getMethod(staticMethodName, methodParamTypes);
		result = m.invoke(null, paramValues);
		return result;
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
			Class[] methodParamTypes, Object[] paramValues) throws Throwable {
		// Object result = null;
		// // String desc = "RttiUtil.invoke()";
		// Class cls = Class.forName(className);
		// Method m = cls.getMethod(staticMethodName, methodParamTypes);
		// result = m.invoke(null, paramValues);
		// return result;
		Class cls = Class.forName(className);
		return invoke(cls, staticMethodName, methodParamTypes, paramValues);
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
			Class[] methodParamTypes, Object[] paramValues) throws Throwable {
		Object result = null;
		// String desc = "RttiUtil.invokeByInstance()";
		Class cls = obj.getClass();
		Method m = cls.getMethod(methodName, methodParamTypes);
		result = m.invoke(obj, paramValues);
		return result;
	}

}
