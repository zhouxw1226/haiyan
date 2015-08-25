//package haiyan.orm.database.nosql;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.mongodb.BasicDBObject;
//
//import haiyan.common.DateUtil;
//import haiyan.common.StringUtil;
//import haiyan.common.VarUtil;
//import haiyan.common.config.DataConstant;
//import haiyan.common.intf.database.orm.IDBRecord;
//import haiyan.config.castorgen.Field;
//import haiyan.config.castorgen.Option;
//import haiyan.config.castorgen.Table;
//import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
//import haiyan.config.util.NamingUtil;
//import haiyan.orm.database.AbstractDBRecord;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
// 
//
//@SuppressWarnings({"rawtypes","unchecked"})
//public class MongoDBRecord extends BasicDBObject implements IDBRecord {
//
//    private static final long serialVersionUID = 1L;
//    private Map<String, Object> oreignDataMap = new HashMap<String, Object>(10, 1); // 原数据容器
//    private Set<String> updatedKeys = new HashSet<String>(10, 1); // 修改的key
//    private Set<String> deletedKeys = new HashSet<String>(10, 1); // 删除的key
//    private String tableName;
//    public String getTableName() {
//        return this.tableName;
//    }
//    public void setTableName(String tableName) {
//        this.tableName = tableName;
//    }
////	public final static class Blob implements Serializable {
////		private static final long serialVersionUID = 1L;
////		public Blob(String f){
////			this.fileName=f;
////			//this.data=d;
////		}
////		public String fileName;
////		//public String data;
////	}
//    private List<String> blobs = new ArrayList<String>();
//	public void addBlobFile(String b) {
//		if (b!=null)
//			blobs.add(b);
//	}
//	public List<String> getBlobFile() {
//		return blobs;
//	}
//	@Override
//    public void flushOreign() { // 将加载的DB数据flush到一级缓存
//		Map dataMap = this.getDataMap();
//		if (dataMap.size()>0) {
//	    	this.deletedKeys.clear();
//			this.updatedKeys.clear();
//			this.oreignDataMap.clear();
//			this.oreignDataMap.putAll((Map)dataMap);
//		}
//    }
//	@Override
//    public void flush() { // 将一级缓存flush到二级Cache
//    }
//	@Override
//	public void commit() {
//		try {
////			for (String b:blobs) {
////				if (b!=null) {
////					File f = new File(b+".bak");
////			File f2 = new File(b);
////					FileUtil.copy(f, f2);
////					if (f.exists())
////						f.delete();
////					DebugUtil.debug(">save file:" + b);
////				}
////			}
//	    	this.deletedKeys.clear();
//			this.updatedKeys.clear();
//			this.blobs.clear();
//			this.status = IDBRecord.DEFAULT;
//			this.statusOld = IDBRecord.DEFAULT;
//			Map dataMap = this.getDataMap();
//			if (dataMap!=null && dataMap.size()>0) {
//				Map oreignMap = this.oreignDataMap;
//				if (oreignMap!=null) {
//					//oreignMap.clear();
//					oreignMap.putAll(dataMap);
//				}
//			}
//		} catch(Throwable e) {
//			throw new RuntimeException(e);
//		}
//	}
//	@Override
//	public boolean rollback() throws Throwable {
//		try {
////			for (String b:blobs) {
////				if (b!=null) {
////					File f = new File(b+".bak");
////					if (f.exists())
////						f.delete();
////					DebugUtil.debug(">delete file:" + b+".bak");
////				}
////			}
//	    	this.deletedKeys.clear();
//			this.updatedKeys.clear();
//			this.blobs.clear();
//			this.status = this.statusOld;
//			Map oreignMap = this.oreignDataMap;
//			if (oreignMap!=null && oreignMap.size()>0) {
//				Map dataMap = this.getDataMap();
//				if (dataMap!=null) {
//					//dataMap.clear();
//					dataMap.putAll(oreignMap);
//				}
//				//this.setDataMap(this.origiDataMap);
//				return true;
//			}
//			return false;
//		} catch(Throwable e) {
//			throw new RuntimeException(e);
//		}
//	}
//    @Override
//    public String toString() {
////        StringBuffer buf = new StringBuffer("");
////        Iterator<?> key = getDataMap().keySet().iterator();
////        while (key.hasNext()) {
////            String keyName = (String) key.next();
////            if (isIgnoredTo(keyName))
////                continue;
////            buf.append(keyName + "=");
////            Object[] values = getParameterValues(keyName);
////            if (values != null)
////                for (int i = 0; i < values.length; i++) {
////                    if (!StringUtil.isEmpty(values[i])) {
////                        buf.append(values[i]);
////                    }
////                    buf.append(" ");
////                }
////            buf.append(" ");
////        }
////        return buf.toString();
//        return this.toJSon().toString();
//    }
//	/**
//	 * @return JSONObject
//	 */
//    @Override
//	public JSONObject toJSon() {
//		return toJSon(true, null);
//	}
//    /**
//     * @return JSONObject
//     */
//    public JSONObject toJSon(boolean showAll, ArrayList<String> ignore) {
//        JSONObject obj = new JSONObject();
//        Iterator<?> key = getDataMap().keySet().iterator();
//        while (key.hasNext()) {
//            String keyName = (String) key.next();
//            if (isIgnoredTo(keyName))
//                continue;
//			if (!showAll && ignore!=null && ignore.contains(keyName))
//				continue;
//			Object value = super.get(keyName);
//			if(value!=null && value instanceof Object[])
//				obj.put(keyName, JSONArray.fromObject(value));
//            else 
//                obj.put(keyName, value);
//        }
//        return obj;
//    }
//    @Override
//	public IDBRecord fromJSon(JSONObject json2) {
//    	return fromJSon(json2, false);
//    }
//    @Override
//	public IDBRecord fromJSon(JSONObject clientJSON, boolean ignoreJSON) {
//		if (clientJSON==null)
//			return this;
//		JSONObject json;
//		if (clientJSON.containsKey("dataMap"))
//			json = clientJSON.getJSONObject("dataMap");
//		else
//			json = clientJSON;
//		String k; Object o;
//		Iterator<String> iter = json.keys();
//		while(iter.hasNext()) {
//			k = iter.next();
//			o = json.get(k);
//			if (o instanceof JSONObject || o instanceof JSONArray) {
//				if (ignoreJSON) { // nothing
//					continue;
//				}
//			}
//			super.put(k.toUpperCase(), o==null?"":o);
//		}
//		return this;
//	}
//    /**
//     * @param keyName
//     * @return boolean
//     */
//    protected boolean isIgnoredTo(String keyName) {
//        return (keyName != null && Arrays.binarySearch(DataConstant.FORM_IGNORE, keyName) >= 0);
//    } 
//    /**
//     * @param record
//     * @param fieldNames
//     * @return String[]
//     */
//    public String[] diffFields(IDBRecord record, String[] fieldNames) {
//        if (record == null || fieldNames == null)
//            return new String[0];
//        Object srcValue;
//        Object nowValue;
//        boolean flag;
//        ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < fieldNames.length; i++) {
//            srcValue = super.get(fieldNames[i]);
//            nowValue = record.get(fieldNames[i]);
//            flag = sameValue(srcValue, nowValue);
//            if (!flag) {
//                list.add(fieldNames[i]);
//            }
//        }
//        return (String[]) list.toArray(new String[0]);
//    }
//    /**
//     * @param record
//     * @param fields
//     * @return String[]
//     */
//    public String[] diffFields(AbstractDBRecord record, Field[] fields) {
//        if (record == null || fields == null)
//            return new String[0];
//        Object srcValue;
//        Object nowValue;
//        boolean flag;
//        ArrayList<String> list = new ArrayList<String>();
//        for (Field field:fields) {
//            if (isIgnoredTo(field.getName()))
//                continue;
//            if (field.isVisual())
//                continue;
//            if (!StringUtil.isEmpty(field.getSubQuerySQL()))
//                continue;
//            srcValue = super.get(field.getName());
//            nowValue = record.get(field.getName());
//            flag = sameValue(srcValue, nowValue);
//            if (!flag) {
//                list.add(field.getName());
//            }
//        }
//        return (String[]) list.toArray(new String[0]);
//    }
//    /**
//     * @param record
//     * @param fields
//     * @param hasAll
//     * @return
//     */
//    public String diffLogs(AbstractDBRecord record, Field[] fields, boolean hasAll) {
//        if (record == null || fields == null)
//            return "";
//        Object disSrcValue = null;
//        Object disNowValue = null;
//        Object srcValue = null;
//        Object nowValue = null;
//        boolean isSameFlag;
//        String info = "";
//        for (Field field:fields) {
//            if (isIgnoredTo(field.getName()))
//                continue;
//            if (field.isVisual())
//                continue;
//            if (!StringUtil.isEmpty(field.getSubQuerySQL()))
//                continue;
//            srcValue = super.get(field.getName());
//            nowValue = record.get(field.getName());
//            // judge same value
////            AbstractCommonFieldJavaTypeType type = field.getJavaType();
////            if (type == AbstractCommonFieldJavaTypeType.BLOB || type == AbstractCommonFieldJavaTypeType.DBBLOB)
////                isSameFlag = sameValueArray(srcValue, nowValue);
////            else
//                isSameFlag = sameValue(srcValue, nowValue);
//            if (!isSameFlag) {
//                if (field.getOptionCount() > 0) {
//                    for (Option op : field.getOption()) {
//                        if (op.getValue().equals(srcValue))
//                            disSrcValue = op.getDisplayName();
//                        if (op.getValue().equals(nowValue))
//                            disNowValue = op.getDisplayName();
//                    }
//                }
//                if (!StringUtil.isEmpty(field.getReferenceTable())) {
//                    disSrcValue = super.get(NamingUtil.getDisplayFieldAlias(field));
//                    disNowValue = record.get(NamingUtil.getDisplayFieldAlias(field));
//                    // list.add(fields[i].getName());
//                    info += "{" + field.getDescription() + ": '"  + disSrcValue + "-->" + disNowValue + "'}\t\n";
//                    if (!hasAll)
//                        continue;
//                }
//                // list.add(fields[i].getName());
//                info += "{" + field.getDescription() + ": '" + srcValue  + "-->" + nowValue + "'}\t\n";
//            }
//        }
//        return info;
//    }
//    @Override
//    public boolean contains(String key) {
//        return super.containsField(key);
//    }
//    @Override
//    public Object remove(String name) {
//        return super.removeField(name);
//    }
//    @Override
//    public Object delete(String name) {
//    	//Object v = this.get(name);
//    	this.updatedKeys.add(name); // 要加到updateSQL占位符中
//    	this.deletedKeys.add(name);
//    	Object v = this.remove(name);
//    	this.oreignDataMap.put(name, v);
//    	return v;
//    }
//    @Override
//    public void set(String name, Object value) {
//    	this.updatedKeys.add(name);
//    	this.deletedKeys.remove(name);
//        super.append(name, value);
//    }
//    @Override
//    public Object get(String name) {
//        return super.get(name);
//    }
//    @Override
//    public void setValues(String name, Object[] values) {
//    	this.updatedKeys.add(name);
//    	this.deletedKeys.remove(name);
//        this.set(name, values);
//    }
//    @Override
//    public Object[] getValues(String name) {
//        return (Object[])this.get(name);
//    }
//    @Override
//    public String getValuesString(String name, String split) {
//        String[] values = (String[])this.getValues(name);
//        if (values!=null)
//            return StringUtil.join(values, split==null?DataConstant.SQL_VALUE_DIM:split, "");
//        return "";
//    }
//    @Override
//    public Date getDate(String name, String dateStyle) {
//    	Object value = get(name);
//        if (!StringUtil.isEmpty(value)) {
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
//                return sdf.parse(VarUtil.toString(value));
////                return new Date(sdf.parse(value).getTime());
//            } catch (Throwable ignore) {
//            	ignore.printStackTrace();
//            }
//        }
//        return new Date();
//    }
//    @Override
//    public String getString(String name) {
//        Object value = get(name);
//        if (StringUtil.isEmpty(value))
//        	value = "";
//        return VarUtil.toString(value);
//    }
//    @Override
//    public int getInteger(String name) {
//        return getBigDecimal(name).intValue();
//    }
//    @Override
//    public double getDouble(String name) {
//        return getBigDecimal(name).doubleValue();
//    }
//    @Override
//    public BigDecimal getBigDecimal(String name) {
//        Object value = get(name);
//        if (StringUtil.isEmpty(value))
//        	value = 0;
//        return VarUtil.toBigDecimal(value);
//    }
//    @Override
//	public Set<String> insertedKeySet() {
//        return this.dataKeySet();
//    }
//    @Override
//	public Set<String> updatedKeySet() {
//    	return this.updatedKeys;
//    }
//    @Override
//	public Set<String> deletedKeySet() {
//    	return this.deletedKeys;
//    }
//    @Override
//	public Set<String> oreignKeySet() {
//        Map<String, Object> map = this.oreignDataMap;
//        return map.keySet();
//    }
//    @Override
//	public Set<String> dataKeySet() {
//        Map<String, Object> map = this.getDataMap();
//        return map.keySet();
//    }
////    @Override
////	public Iterator<String> keyIterator() {
////        Map<String, Object> map = this.getDataMap();
////        return map.keySet().iterator();
////    }
//    // 只在一个事务内有效
//    private transient byte dirty = 0; // 4 UnitOfWork
//    @Override
//    public boolean isDirty() { // 4 UnitOfWork
//        return this.dirty == 1;
//    }
//    @Override
//    public void setDirty() { // 4 UnitOfWork
//    	this.dirty = 1;
//    }
//    @Override
//    public void clearDirty() { // 4 UnitOfWork
//    	this.dirty = 0;
//    }
//    // 只在一个事务内有效
//    private transient byte statusOld = 0;
//    private transient byte status = 0;
//    @Override
//    public void setStatus(byte status) {
//    	this.status = status;
//    }
//    @Override
//    public byte getStatus() {
//    	return this.status;
//    }
//    @Override
//    public void clearStatus() {
//    	this.status = DEFAULT;
//    }
////    不同table配置根据field.key来copy的，现在只保证同table.key的版本控制
////    private short version = 0;
////    version 最好放在数据里
//    @Override
//	public int getVersion() {
//		Object v = this.get(DataConstant.HYVERSION);
//		if (StringUtil.isEmpty(v))
//			v = "0";
//		return VarUtil.toInt(v);
//	}
//    @Override
//	public void updateVersion() {
//    	Object v = this.get(DataConstant.HYVERSION);
//		if (StringUtil.isEmpty(v))
//			v = "0";
//		int t = VarUtil.toInt(v) + 1;
//		this.set(DataConstant.HYVERSION, "" + t);
//	}
//    // ================================================================================== //
//    private static boolean sameValue(Object v1, Object v2) {
//        if (!StringUtil.isEmpty(v1) && StringUtil.isEmpty(v2))
//            return false;
//        else if (StringUtil.isEmpty(v1) && !StringUtil.isEmpty(v2))
//            return false;
//        else if (v1.equals(v2))
//        	return true;
//        else if (!VarUtil.toString(v1).trim().equalsIgnoreCase(VarUtil.toString(v2).trim()))
//            return false;
//        return true;
//    }
//	public static Map<String, Object> filterIndexRecord(Table table, IDBRecord record) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		for (Field field:table.getField()) {
//			if (!field.isIndexAllowed())
//				continue;
//			if (!StringUtil.isEmpty(field.getReferenceTable())) {
//				String name = NamingUtil.getDisplayFieldAlias(field);
//				map.put(name, record.get(name));
//			} else if (field.getOptionCount()>0) {
//				for (Option option:field.getOption()) {
//					if (option.getValue().equals(record.get(field.getName()))) {
//						String name = NamingUtil.getDisplayFieldAlias(field);
//						map.put(name, option.getDisplayName());
//					}
//				}
//			}
//			String name = field.getName();
//			map.put(name, record.get(name));
//		}
//		return map;
//	}
//	protected static Object transValueType(Field field, Object v) {
//		// TODO 转成各种数据类型
//		if (field.getJavaType()==AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
//			v = VarUtil.toBigDecimal(v);
//		} else if (field.getJavaType()==AbstractCommonFieldJavaTypeType.DATE) {
//			if (StringUtil.isNumeric(v)) {
//				long time = VarUtil.toLong(v);
//				Calendar gcal = GregorianCalendar.getInstance();
////				Calendar gcal = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
////				BaseCalendar gcal = CalendarSystem.getGregorianCalendar();
////				gcal.getDisplayName(field, style, locale);
////				gcal.getTime();
//				gcal.setTimeInMillis(time);
//				String style = field.getDataStyle();
//				if (StringUtil.isEmpty(style)) // 转换成UTC时间
//					style = "yyyy-MM-dd HH:mm:ss";
//				v = DateUtil.format(gcal.getTime(), style);
//			}
//		}
//		return v;
//	}
//	protected static Object getClientValue(JSONObject jsonClient, String key) throws Throwable {
//		Iterator<String> iter = jsonClient.keys();
//		while(iter.hasNext()) {
//			String _key = iter.next();
//			if (_key.equalsIgnoreCase(key)) { // 忽略大小写
//				Object v = jsonClient.has(_key)?jsonClient.get(_key):null;
////				if (!StringUtil.isEmpty(v))
////					v = java.net.URLDecoder.decode(v, "UTF-8");
//				return v;
//			}
//		}
//		return null;
//	}
//	public static void parseRequest(JSONObject jsonClient, Table table, IDBRecord record) throws Throwable {
//		for (Field field:table.getField()) {
//			Object v = null;
//			String uiName = field.getUiname();
//			if (StringUtil.isEmpty(uiName))
//				uiName = field.getName();
//			if (!StringUtil.isEmpty(uiName)) {
//				v = getClientValue(jsonClient, uiName);
//			} 
//			if (StringUtil.isEmpty(v)) {
//				String defVal = field.getDefaultValue();
//				if (!field.isNullAllowed() && !StringUtil.isEmpty(defVal)) {
//					if(field.getJavaType() == AbstractCommonFieldJavaTypeType.INTEGER){
//						v = Integer.valueOf(defVal);
//					}
////					//TODO 其他类型
////					else if(field.getJavaType() == AbstractCommonFieldJavaTypeType.BIGDECIMAL){
////						v = new BigDecimal(defVal);
////					}
//					else{
//						v = defVal;
//					}
//				}
//			}
//			if (!StringUtil.isEmpty(v)) {
//				v = transValueType(field, v);
//				String dbName = field.getName();
//				record.set(dbName, v); // setValue and setUpdateStatus
//			} else if (!field.isNullAllowed()) {
//				//throw Warning("not allow empty value, field="+);
//			}
//		}
//	}
//	@Override
//	public Map<String, Object> getDataMap() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
