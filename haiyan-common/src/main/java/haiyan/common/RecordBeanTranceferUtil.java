package haiyan.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import haiyan.common.annotation.GetMethod;
import haiyan.common.annotation.SetMethod;
import haiyan.common.intf.database.orm.IDBRecord;
import net.sf.json.JSONObject;

/**
 * IDBRecord和普通的Bean互转的工具类，
 * 用注解的方式执行普通Bean的get\set方法
 * @author 商杰
 *
 */
public class RecordBeanTranceferUtil {
	public static IDBRecord bean2Record(Object bean, IDBRecord record) throws Throwable{
		Method[] methods = bean.getClass().getMethods();
		for(Method method : methods){
			if(!method.isAnnotationPresent(GetMethod.class))
				continue;
			GetMethod getMethod = method.getAnnotation(GetMethod.class);
			String key = getMethod.value().toUpperCase();
			Object value = null;
			try {
				value = method.invoke(bean);
			} catch (Throwable e) {
				DebugUtil.error("key:"+key+",value:"+value, e);
//				throw e;
			}
			record.set(key, value);
		}
		return record;
	}
	/**
	 * record有的字段才会覆盖bean
	 * 
	 * @param record
	 * @param bean
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object recordCovertBean(IDBRecord record, Object bean) throws Throwable{
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(SetMethod.class))
				continue;
			SetMethod setMethod = method.getAnnotation(SetMethod.class);
			Class<?> firstClass = method.getParameterTypes()[0];
			String key = setMethod.value().toUpperCase();
			if (!record.contains(key))
				continue;
			Object value = record.get(key);
			try {
				if (value!=null && value.getClass()!=firstClass) {
					if (firstClass==java.math.BigDecimal.class) {
						value = new java.math.BigDecimal(value.toString());
					} else if (firstClass==java.util.Date.class) {
						value = DateUtil.getDate(value.toString());
					} else if (firstClass==java.lang.Integer.class) {
						value = Integer.parseInt(value.toString());
					} else if (firstClass==java.lang.Long.class) {
						value = Long.parseLong(value.toString());
					} else if (firstClass==java.lang.Double.class) {
						value = Double.parseDouble(value.toString());
					} else if (firstClass==java.lang.Float.class) {
						value = Float.parseFloat(value.toString());
					} else if (firstClass==java.lang.Boolean.class) {
						value = Boolean.parseBoolean(value.toString());
					} else if (firstClass==java.lang.Byte.class) {
						value = Byte.parseByte(value.toString());
					} else if (firstClass==java.util.Map.class) {
						JSONObject json = JSONObject.fromObject(value);
						Map<?, ?> map = new HashMap();
						map.putAll(json);
						value = map;
//						value = Byte.parseByte(value.toString());
					} else if (firstClass==Object[].class) { // 内存数组都直接放入
						//value = value;
					} else {
						value = value.toString();
					}
				} 
				method.invoke(bean,value);
			} catch (Throwable e) {
				DebugUtil.error("key:"+key+",value:"+value, e);
//				throw e;
			}
		}
		return bean;
	}
	/**
	 * record全字段覆盖bean
	 * 
	 * @param record
	 * @param bean
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object record2Bean(IDBRecord record, Object bean) throws Throwable{
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(SetMethod.class))
				continue;
			SetMethod setMethod = method.getAnnotation(SetMethod.class);
			Class<?> firstClass = method.getParameterTypes()[0];
			String key = setMethod.value().toUpperCase();
			Object value = record.get(key);
			try {
				if (value!=null && value.getClass()!=firstClass) {
					if (firstClass==java.math.BigDecimal.class) {
						value = new java.math.BigDecimal(value.toString());
					} else if (firstClass==java.util.Date.class) {
						value = DateUtil.getDate(value.toString());
					} else if (firstClass==java.lang.Integer.class) {
						value = Integer.parseInt(value.toString());
					} else if (firstClass==java.lang.Long.class) {
						value = Long.parseLong(value.toString());
					} else if (firstClass==java.lang.Double.class) {
						value = Double.parseDouble(value.toString());
					} else if (firstClass==java.lang.Float.class) {
						value = Float.parseFloat(value.toString());
					} else if (firstClass==java.lang.Boolean.class) {
						value = Boolean.parseBoolean(value.toString());
					} else if (firstClass==java.lang.Byte.class) {
						value = Byte.parseByte(value.toString());
					} else if (firstClass==java.util.Map.class) {
						JSONObject json = JSONObject.fromObject(value);
						Map<?, ?> map = new HashMap();
						map.putAll(json);
						value = map;
//						value = Byte.parseByte(value.toString());
					} else if (firstClass==Object[].class) { // 内存数组都直接放入
						//value = value;
					} else {
						value = value.toString();
					}
				} 
				method.invoke(bean, value);
			} catch (Throwable e) {
				DebugUtil.error("key:"+key+",value:"+value, e);
//				throw e;
			}
		}
		return bean;
	}
}
