package haiyan.orm.database;

import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Option;
import haiyan.config.castorgen.Table;
import haiyan.config.util.NamingUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractDBRecord implements IDBRecord {

    private static final long serialVersionUID = 1L;
    protected abstract boolean containsKey(String key);
    protected abstract Object removeParameter(String key);
    protected abstract void setParameter(String key, Object value);
    protected abstract Object getParameter(String key);
    protected abstract void setParameterValues(String key, Object[] values);
    protected abstract Object[] getParameterValues(String key);
    public abstract Map<String, Object> getDataMap(); // 内存数据容器
    private Map<String, Object> oreignDataMap = new HashMap<String, Object>(10, 1); // 原数据容器
    private Set<String> updatedKeys = new HashSet<String>(10, 1); // 修改的key
    private Set<String> deletedKeys = new HashSet<String>(10, 1); // 删除的key
    private String tableName;
    public String getTableName() {
        return this.tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
//	public final static class Blob implements Serializable {
//		private static final long serialVersionUID = 1L;
//		public Blob(String f){
//			this.fileName=f;
//			//this.data=d;
//		}
//		public String fileName;
//		//public String data;
//	}
    private List<String> blobs = new ArrayList<String>();
	public void addBlobFile(String b) {
		if (b!=null)
			blobs.add(b);
	}
	public List<String> getBlobFile() {
		return blobs;
	}
	@Override
    public void flushOreign() { // 将加载的DB数据flush到一级缓存
		Map dataMap = this.getDataMap();
		if (dataMap.size()>0) {
	    	this.deletedKeys.clear();
			this.updatedKeys.clear();
			this.oreignDataMap.clear();
			this.oreignDataMap.putAll((Map)dataMap);
		}
    }
	@Override
    public void flush() { // 将一级缓存flush到二级Cache
    }
	@Override
	public void commit() {
		try {
//			for (String b:blobs) {
//				if (b!=null) {
//					File f = new File(b+".bak");
//			File f2 = new File(b);
//					FileUtil.copy(f, f2);
//					if (f.exists())
//						f.delete();
//					DebugUtil.debug(">save file:" + b);
//				}
//			}
	    	this.deletedKeys.clear();
			this.updatedKeys.clear();
			this.blobs.clear();
			this.status = IDBRecord.DEFAULT;
			this.statusOld = IDBRecord.DEFAULT;
			Map dataMap = this.getDataMap();
			if (dataMap!=null && dataMap.size()>0) {
				Map oreignMap = this.oreignDataMap;
				if (oreignMap!=null) {
					//oreignMap.clear();
					oreignMap.putAll(dataMap);
				}
			}
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean rollback() throws Throwable {
		try {
//			for (String b:blobs) {
//				if (b!=null) {
//					File f = new File(b+".bak");
//					if (f.exists())
//						f.delete();
//					DebugUtil.debug(">delete file:" + b+".bak");
//				}
//			}
	    	this.deletedKeys.clear();
			this.updatedKeys.clear();
			this.blobs.clear();
			this.status = this.statusOld;
			Map oreignMap = this.oreignDataMap;
			if (oreignMap!=null && oreignMap.size()>0) {
				Map dataMap = this.getDataMap();
				if (dataMap!=null) {
					//dataMap.clear();
					dataMap.putAll(oreignMap);
				}
				//this.setDataMap(this.origiDataMap);
				return true;
			}
			return false;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}
    @Override
    public String toString() {
//        StringBuffer buf = new StringBuffer("");
//        Iterator<?> key = getDataMap().keySet().iterator();
//        while (key.hasNext()) {
//            String keyName = (String) key.next();
//            if (isIgnoredTo(keyName))
//                continue;
//            buf.append(keyName + "=");
//            Object[] values = getParameterValues(keyName);
//            if (values != null)
//                for (int i = 0; i < values.length; i++) {
//                    if (!StringUtil.isEmpty(values[i])) {
//                        buf.append(values[i]);
//                    }
//                    buf.append(" ");
//                }
//            buf.append(" ");
//        }
//        return buf.toString();
        return this.toJSon().toString();
    }
	/**
	 * @return JSONObject
	 */
	public JSONObject toJSon() {
		return toJSon(true, null);
	}
    /**
     * @return JSONObject
     */
    public JSONObject toJSon(boolean showAll, ArrayList<String> ignore) {
        // JSONObject json = JSONObject.fromObject(getDataMap());
        // return JSONObject.fromObject(getDataMap());
        JSONObject obj = new JSONObject();
        Iterator<?> key = getDataMap().keySet().iterator();
        while (key.hasNext()) {
            String keyName = (String) key.next();
            if (isIgnoredTo(keyName))
                continue;
			if (!showAll && ignore!=null && ignore.contains(keyName))
				continue;
            Object[] values = getParameterValues(keyName);
            if (values != null && values.length != 0) {
                String value = StringUtil.join(values, ",", "");
                obj.put(keyName, value);
            } else
                obj.put(keyName, "");
        }
        return obj;
    }
	public void fromJSon(JSONObject json2) {
		if (json2==null)
			return;
		JSONObject json;
		if (json2.containsKey("dataMap"))
			json = json2.getJSONObject("dataMap");
		else
			json = json2;
		String k; Object o;
		Iterator iter = json.keys();
		while(iter.hasNext()) {
			k = iter.next().toString();
			o = json.get(k);
//			if (o instanceof net.sf.json.JSONObject) {
//				this.fromJSon((net.sf.json.JSONObject)o);
//				break;
//			}
//			System.out.println("------====="+o.getClass());
			this.set(k, o==null?"":o.toString());
		}
	}
    /**
     * @param json
     */
    public void json2Form(JSONObject json) {
        Iterator<?> iter = json.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (!json.containsKey(key))
            	continue;
            Object val = json.get(key);
            if (val != null) {
                //if (val.startsWith("\"") && val.endsWith("\""))
                //    val = val.substring(0, val.length() - 2).substring(2);
                setParameter(key, val);
            }
        }
    }
    /**
     * @param record
     * @param fieldNames
     * @return boolean
     */
    public boolean equals(AbstractDBRecord record, String[] fieldNames) {
        if (record == null || fieldNames == null)
            return true;
        for (int i = 0; i < fieldNames.length; i++) {
            // Field field = ConfigUtil.getFieldByName(table, paras[i]);
            Object srcValue = this.getParameter(fieldNames[i]);
            Object nowValue = record.getParameter(fieldNames[i]);
            // judge same value
            boolean flag = sameValue(srcValue, nowValue);
            if (!flag) {
                return false;
            }
        }
        return true;
    }
    /**
     * @param record
     * @param fields
     * @return boolean
     */
    public boolean equals(AbstractDBRecord record, Field[] fields) {
        if (record == null || fields == null)
            return true;
        for (int i = 0; i < fields.length; i++) {
            // Field field = ConfigUtil.getFieldByName(table, paras[i]);
        	Object srcValue = this.getParameter(fields[i].getName());
        	Object nowValue = record.getParameter(fields[i].getName());
            // judge same value
            boolean flag = sameValue(srcValue, nowValue);
            if (!flag) {
                return false;
            }
        }
        return true;
    }
    /**
     * @param keyName
     * @return boolean
     */
    protected boolean isIgnoredTo(String keyName) {
        return (keyName != null && Arrays.binarySearch(DataConstant.FORM_IGNORE, keyName) >= 0);
    }
    /**
     * @param tgtRecord
     */
    public void copyTo(AbstractDBRecord tgtRecord) {
        Iterator<?> key = getDataMap().keySet().iterator();
        while (key.hasNext()) {
            String keyName = (String) key.next();
            if (isIgnoredTo(keyName))
                continue;
            Object[] values = getParameterValues(keyName);
            StringBuffer buf = new StringBuffer("");
            if (values != null)
                for (int i = 0; i < values.length; i++) {
                    if (!StringUtil.isEmpty(values[i])) {
                        if (buf.length() > 0)
                            buf.append(",");
                        buf.append(values[i]);
                    }
                }
            tgtRecord.setParameter(keyName, buf.toString());
        }
    }
    /**
     * @param record
     * @param fieldNames
     * @return String[]
     */
    public String[] diffFields(AbstractDBRecord record, String[] fieldNames) {
        if (record == null || fieldNames == null)
            return new String[0];
        Object srcValue;
        Object nowValue;
        boolean flag;
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < fieldNames.length; i++) {
            srcValue = this.getParameter(fieldNames[i]);
            nowValue = record.getParameter(fieldNames[i]);
            flag = sameValue(srcValue, nowValue);
            if (!flag) {
                list.add(fieldNames[i]);
            }
        }
        return (String[]) list.toArray(new String[0]);
    }
    /**
     * @param record
     * @param fields
     * @return String[]
     */
    public String[] diffFields(AbstractDBRecord record, Field[] fields) {
        if (record == null || fields == null)
            return new String[0];
        Object srcValue;
        Object nowValue;
        boolean flag;
        ArrayList<String> list = new ArrayList<String>();
        for (Field field:fields) {
            if (isIgnoredTo(field.getName()))
                continue;
            if (field.isVisual())
                continue;
            if (!StringUtil.isEmpty(field.getSubQuerySQL()))
                continue;
            srcValue = this.getParameter(field.getName());
            nowValue = record.getParameter(field.getName());
            flag = sameValue(srcValue, nowValue);
            if (!flag) {
                list.add(field.getName());
            }
        }
        return (String[]) list.toArray(new String[0]);
    }
    /**
     * @param record
     * @param fields
     * @param hasAll
     * @return
     */
    public String diffLogs(AbstractDBRecord record, Field[] fields, boolean hasAll) {
        if (record == null || fields == null)
            return "";
        Object disSrcValue = null;
        Object disNowValue = null;
        Object srcValue = null;
        Object nowValue = null;
        boolean isSameFlag;
        String info = "";
        for (Field field:fields) {
            if (isIgnoredTo(field.getName()))
                continue;
            if (field.isVisual())
                continue;
            if (!StringUtil.isEmpty(field.getSubQuerySQL()))
                continue;
            srcValue = this.getParameter(field.getName());
            nowValue = record.getParameter(field.getName());
            // judge same value
//            AbstractCommonFieldJavaTypeType type = field.getJavaType();
//            if (type == AbstractCommonFieldJavaTypeType.BLOB || type == AbstractCommonFieldJavaTypeType.DBBLOB)
//                isSameFlag = sameValueArray(srcValue, nowValue);
//            else
                isSameFlag = sameValue(srcValue, nowValue);
            if (!isSameFlag) {
                if (field.getOptionCount() > 0) {
                    for (Option op : field.getOption()) {
                        if (op.getValue().equals(srcValue))
                            disSrcValue = op.getDisplayName();
                        if (op.getValue().equals(nowValue))
                            disNowValue = op.getDisplayName();
                    }
                }
                if (!StringUtil.isEmpty(field.getReferenceTable())) {
                    disSrcValue = this.getParameter(NamingUtil.getDisplayFieldAlias(field));
                    disNowValue = record.getParameter(NamingUtil.getDisplayFieldAlias(field));
                    // list.add(fields[i].getName());
                    info += "{" + field.getDescription() + ": '"  + disSrcValue + "-->" + disNowValue + "'}\t\n";
                    if (!hasAll)
                        continue;
                }
                // list.add(fields[i].getName());
                info += "{" + field.getDescription() + ": '" + srcValue  + "-->" + nowValue + "'}\t\n";
            }
        }
        return info;
    }
    @Override
    public boolean contains(String key) {
        return this.containsKey(key);
    }
    @Override
    public Object remove(String name) {
        return this.removeParameter(name);
    }
    @Override
    public Object delete(String name) {
    	//Object v = this.get(name);
    	this.updatedKeys.add(name); // 要加到updateSQL占位符中
    	this.deletedKeys.add(name);
    	Object v = this.remove(name);
    	this.oreignDataMap.put(name, v);
    	return v;
    }
    @Override
    public void set(String name, Object value) {
    	this.updatedKeys.add(name);
    	this.deletedKeys.remove(name);
        this.setParameter(name, value);
    }
    @Override
    public Object get(String name) {
        return this.getParameter(name);
    }
    @Override
    public void setValues(String name, Object[] values) {
    	this.updatedKeys.add(name);
    	this.deletedKeys.remove(name);
        this.setParameterValues(name, values);
    }
    @Override
    public Object[] getValues(String name) {
        return this.getParameterValues(name);
    }
    @Override
    public String getValuesString(String name, String split) {
        String[] values = (String[])this.getValues(name);
        if (values!=null)
            return StringUtil.join(values, split==null?DataConstant.SQL_VALUE_DIM:split, "");
        return "";
    }
    @Override
    public Date getDate(String name, String dateStyle) {
    	Object value = getParameter(name);
        if (!StringUtil.isEmpty(value)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
                return sdf.parse(VarUtil.toString(value));
//                return new Date(sdf.parse(value).getTime());
            } catch (Throwable ignore) {
            	ignore.printStackTrace();
            }
        }
        return new Date();
    }
    @Override
    public String getString(String name) {
        Object value = getParameter(name);
        if (StringUtil.isEmpty(value))
        	value = "";
        return VarUtil.toString(value);
    }
    @Override
    public int getInteger(String name) {
        return getBigDecimal(name).intValue();
    }
    @Override
    public double getDouble(String name) {
        return getBigDecimal(name).doubleValue();
    }
    @Override
    public BigDecimal getBigDecimal(String name) {
        Object value = getParameter(name);
        if (StringUtil.isEmpty(value))
        	value = 0;
        return VarUtil.toBigDecimal(value);
    }
    @Override
	public Set<String> insertedKeySet() {
        return this.dataKeySet();
    }
    @Override
	public Set<String> updatedKeySet() {
    	return this.updatedKeys;
    }
    @Override
	public Set<String> deletedKeySet() {
    	return this.deletedKeys;
    }
    @Override
	public Set<String> oreignKeySet() {
        Map<String, Object> map = this.oreignDataMap;
        return map.keySet();
    }
    @Override
	public Set<String> dataKeySet() {
        Map<String, Object> map = this.getDataMap();
        return map.keySet();
    }
//    @Override
//	public Iterator<String> keyIterator() {
//        Map<String, Object> map = this.getDataMap();
//        return map.keySet().iterator();
//    }
    // 只在一个事务内有效
    private transient byte dirty = 0; // 4 UnitOfWork
    @Override
    public boolean isDirty() { // 4 UnitOfWork
        return this.dirty == 1;
    }
    @Override
    public void setDirty() { // 4 UnitOfWork
    	this.dirty = 1;
    }
    @Override
    public void clearDirty() { // 4 UnitOfWork
    	this.dirty = 0;
    }
    // 只在一个事务内有效
    private transient byte statusOld = 0;
    private transient byte status = 0;
    @Override
    public void setStatus(byte status) {
    	this.status = status;
    }
    @Override
    public byte getStatus() {
    	return this.status;
    }
    @Override
    public void clearStatus() {
    	this.status = DEFAULT;
    }
//    不同table配置根据field.key来copy的，现在只保证同table.key的版本控制
//    private short version = 0;
//    version 最好放在数据里
    @Override
	public int getVersion() {
		Object v = this.get(DataConstant.HYVERSION);
		if (StringUtil.isEmpty(v))
			v = "0";
		return VarUtil.toInt(v);
	}
    @Override
	public void updateVersion() {
    	Object v = this.get(DataConstant.HYVERSION);
		if (StringUtil.isEmpty(v))
			v = "0";
		int t = VarUtil.toInt(v) + 1;
		this.set(DataConstant.HYVERSION, "" + t);
	}
    private static boolean sameValue(Object v1, Object v2) {
        if (!StringUtil.isEmpty(v1) && StringUtil.isEmpty(v2))
            return false;
        else if (StringUtil.isEmpty(v1) && !StringUtil.isEmpty(v2))
            return false;
        else if (v1.equals(v2))
        	return true;
        else if (!VarUtil.toString(v1).trim().equalsIgnoreCase(VarUtil.toString(v2).trim()))
            return false;
        return true;
    }
	public static Map<String, Object> filterIndexRecord(Table table, IDBRecord record) {
		Map<String,Object> map = new HashMap<String,Object>();
		for (Field field:table.getField()) {
			if (!field.isIndexAllowed())
				continue;
			if (!StringUtil.isEmpty(field.getReferenceTable())) {
				String name = NamingUtil.getDisplayFieldAlias(field);
				map.put(name, record.get(name));
			} else if (field.getOptionCount()>0) {
				for (Option option:field.getOption()) {
					if (option.getValue().equals(record.get(field.getName()))) {
						String name = NamingUtil.getDisplayFieldAlias(field);
						map.put(name, option.getDisplayName());
					}
				}
			}
			String name = field.getName();
			map.put(name, record.get(name));
		}
		return map;
	}
}