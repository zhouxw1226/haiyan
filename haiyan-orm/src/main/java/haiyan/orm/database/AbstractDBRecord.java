package haiyan.orm.database;

import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Option;
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
//	public abstract void setDataMap(Map map);
    public abstract Map<String, Object> getDataMap();
    private Map<String, Object> oreignDataMap = new HashMap<String, Object>(10, 1);
    private Set<String> updatedKeys = new HashSet<String>(10, 1);
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
//					File f2 = new File(b);
//					FileUtil.copy(f, f2);
//					if (f.exists())
//						f.delete();
//					DebugUtil.debug(">save file:" + b);
//				}
//			}
			blobs.clear();
			Map dataMap = this.getDataMap();
			if (dataMap!=null && dataMap.size()>0) {
				this.oreignDataMap.clear();
				this.oreignDataMap.putAll((Map)dataMap);
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
			this.updatedKeys.clear();
			blobs.clear();
			if (this.oreignDataMap!=null && this.oreignDataMap.size()>0) {
				Map dataMap = this.getDataMap();
				if (dataMap!=null) {
					dataMap.clear();
					dataMap.putAll((Map)this.oreignDataMap);
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
        StringBuffer buf = new StringBuffer("");
        Iterator<?> key = getDataMap().keySet().iterator();
        while (key.hasNext()) {
            String keyName = (String) key.next();
            if (isIgnoredTo(keyName))
                continue;
            buf.append(keyName + "=");
            Object[] values = getParameterValues(keyName);
            if (values != null)
                for (int i = 0; i < values.length; i++) {
                    if (!StringUtil.isBlankOrNull(values[i])) {
                        buf.append(values[i]);
                    }
                    buf.append(" ");
                }
            buf.append(" ");
        }
        return buf.toString();
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
            // int count = 0;
            if (values != null)
                for (int i = 0; i < values.length; i++) {
                    // DebugUtil.debug(values[i].getBytes().length);
                    if (!StringUtil.isBlankOrNull(values[i])) {
                        // if (values[i].getBytes().length <
                        // DataConstant.MAXLENTH_LOG)
                        // if (count > 0)
                        if (buf.length() > 0)
                            buf.append(",");
                        buf.append(values[i]);
                        // count++;
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
            // Field field = ConfigUtil.getFieldByName(table, paras[i]);
            srcValue = this.getParameter(fieldNames[i]);
            nowValue = record.getParameter(fieldNames[i]);
            // judge same value
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
            if (!StringUtil.isBlankOrNull(field.getSubQuerySQL()))
                continue;
            // Field field = ConfigUtil.getFieldByName(table, paras[i]);
            srcValue = this.getParameter(field.getName());
            nowValue = record.getParameter(field.getName());
            // judge same value
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
            if (!StringUtil.isBlankOrNull(field.getSubQuerySQL()))
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
                if (!StringUtil.isBlankOrNull(field.getReferenceTable())) {
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
    public void set(String name, Object value) {
    	this.updatedKeys.add(name);
        this.setParameter(name, value);
    }
    @Override
    public Object get(String name) {
        return this.getParameter(name);
    }
    @Override
    public void setValues(String name, Object[] values) {
    	this.updatedKeys.add(name);
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
        if (!StringUtil.isBlankOrNull(value)) {
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
        if (StringUtil.isBlankOrNull(value))
        	value = 0;
        //value = StringUtil.isBlankOrNull(value) ? "0" : value.replaceAll(",", "");
        return VarUtil.toBigDecimal(value);
    }
    @Override
	public Set<String> inertKeySet() {
        return keySet();
    }
    @Override
	public Set<String> updateKeySet() {
    	return this.updatedKeys;
    }
    @Override
	public Set<String> oreignKeySet() {
        Map<String, Object> map = this.oreignDataMap;
        return map.keySet();
    }
    @Override
	public Set<String> keySet() {
        Map<String, Object> map = this.getDataMap();
        return map.keySet();
    }
//    @Override
//	public Iterator<String> keyIterator() {
//        Map<String, Object> map = this.getDataMap();
//        return map.keySet().iterator();
//    }
    // 只在一个事务内有效
    private transient byte dirty = 0;
    @Override
    public boolean isDirty() {
        return dirty == 1;
    }
    @Override
    public void setDirty() {
        dirty = 1;
    }
    @Override
    public void clearDirty() {
        dirty = 0;
    }
//    不同table配置根据field.key来copy的，现在只保证同table.key的版本控制
//    private short version = 0;
//    version 最好放在数据里
    @Override
	public int getVersion() {
		Object v = this.get(DataConstant.HYVERSION);
		if (StringUtil.isBlankOrNull(v))
			v = "0";
		return VarUtil.toInt(v);
	}
    @Override
	public void updateVersion() {
    	Object v = this.get(DataConstant.HYVERSION);
		if (StringUtil.isBlankOrNull(v))
			v = "0";
		int t = VarUtil.toInt(v) + 1;
		this.set(DataConstant.HYVERSION, "" + t);
	}
//    private static boolean sameValueArray(Object v1, Object v2) {
//        if (!StringUtil.isBlankOrNull(v1) && StringUtil.isBlankOrNull(v2))
//            return false;
//        else if (StringUtil.isBlankOrNull(v1) && !StringUtil.isBlankOrNull(v2))
//            return false;
//        else {
//            String[] a1 = StringUtil.split(VarUtil.toString(v1).trim(), ",");
//            String[] a2 = StringUtil.split(VarUtil.toString(v2).trim(), ",");
//            return Arrays.equals(a1, a2);
//        }
//    }
    private static boolean sameValue(Object v1, Object v2) {
        if (!StringUtil.isBlankOrNull(v1) && StringUtil.isBlankOrNull(v2))
            return false;
        else if (StringUtil.isBlankOrNull(v1) && !StringUtil.isBlankOrNull(v2))
            return false;
        else if (v1.equals(v2))
        	return true;
        else if (!VarUtil.toString(v1).trim().equalsIgnoreCase(VarUtil.toString(v2).trim()))
            return false;
        return true;
    }
}
///**
//* @param table
//*/
//public Row toRow() {
//  Row row = new Row();
//  Iterator<?> key = getDataMap().keySet().iterator();
//  while (key.hasNext()) {
//      String keyName = (String) key.next();
//      if (isIgnoredTo(keyName))
//          continue;
//      String value = this.getValuesString(keyName);
//      Column col = new Column();
//      col.setName(keyName);
//      col.setContent(value);
//      row.addColumn(col);
//  }
//  return row;
//}
///**
//* @param table
//* @param row
//*/
//public void toRow(Table table, Row row) {
//  // form.setName(table.getName());
//  // form.setTable(table.getName());
//  Column[] cols = row.getColumn();
//  if (table != null) {
//      Field[] tfields = table.getField();
//      for (int i = 0; i < tfields.length; i++) {
//          String keyName = tfields[i].getName();
//          Column col = null;
//          for (int j = 0; j < cols.length; j++) {
//              if (cols[j].getName().equals(keyName)) {
//                  col = cols[j];
//                  break;
//              }
//          }
//          if (isIgnoredTo(keyName))
//              continue;
//          String value = this.getValuesString(keyName);
//          if (col!=null) {
//              col.setContent(value);
//              continue;
//          }
//          col = new Column();
//          col.setName(keyName);
//          col.setContent(value);
//          row.addColumn(col);
//      }
//  } else
//      for (int i = 0; i < cols.length; i++) {
//          String colName = cols[i].getName();
//          cols[i].setContent(get(colName));
//      }
//}
///**
//* 会被treeform重构
//* @deprecated
//* @return ArrayList
//*/
//@SuppressWarnings("unchecked")
//public ArrayList<IDBRecord> getChildren() {
//  return new ArrayList();
//}
///**
//* 1对1、1对多，采用延迟加载的方式获取，不持久化这些数据(临时用)
//* 
//* transient 但是Cache后需要 在考虑下。。。(只在当前请求中使用)
//*/
//protected transient Map<String, IDBRecord> one2oneFormMap = new HashMap<String, IDBRecord>();
///**
//* @param tableName
//* @return Qbq3Form
//*/
//public IDBRecord getOne2oneForm(String tableName) {
//return this.one2oneFormMap.get(tableName.toUpperCase());
//}
///**
//* @param table
//* @return Qbq3Form
//*/
//public IDBRecord getOne2oneForm(Table table) {
//  // 反序列化后one2oneFormMap可能为null
//  if (one2oneFormMap != null && table != null)
//      return this.one2oneFormMap.get(table.getName().toUpperCase());
//  return null;
//}
///**
//* @param tableName
//* @param one2oneForm
//*/
//public void setOne2oneForm(String tableName, IDBRecord one2oneForm) {
//this.one2oneFormMap.put(tableName.toUpperCase(), one2oneForm);
//}
///**
//* @param table
//* @param one2oneForm
//*/
//public void setOne2oneForm(Table table, IDBRecord one2oneForm) {
//  // 反序列化后one2oneFormMap可能为null
//  if (one2oneFormMap == null)
//      one2oneFormMap = new HashMap<String, IDBRecord>();
//  if (table == null || one2oneForm == null)
//      return;
//  this.one2oneFormMap.put(table.getName().toUpperCase(), one2oneForm);
//}
///**
//* @deprecated
//* 多对多，不持久化这些数据(临时用)
//*/
//protected transient Map<String, List<IDBRecord>> mappingRecords = new HashMap<String, List<IDBRecord>>();
///**
//* @deprecated
//* 
//*             不推荐使用，最多只能查第一个Page.MAXCOUNTPERQUERY条，但是是高效的
//* 
//* @param context
//* @param dbm
//* @param refTblKey
//*            关联表key
//* @param refFldKey
//*            关联表字段key(关联表外键)
//* @param thisFldKey
//*            本表字段key(外键)
//* @return List<Qbq3Form>
//* @throws Throwable
//*/
//public List<DefaultRecord> getList(IContext context, IDBManager dbm,
//      String refTblKey, String refFldKey, String thisFldKey)
//      throws Throwable {
//  // if (tableName == null && "FORM".equalsIgnoreCase(tableName))
//  // throw new Warning("mainTableName is null");
//  String key = (refTblKey + "_" + refFldKey).toUpperCase();
//  if (mappingFormMap.get(key) == null) {
//      // String thisFldKey =
//      // ConfigUtil.getChildTableRefFieldName(parentTable);
//      // String refFldKey =
//      // ConfigUtil.getChildTableRefFieldName(parentTable);
//  	IRecord filterForm = new HashRecord();
//      filterForm.set(refFldKey, this.getParameter(thisFldKey));
//      List<IRecord> list = dbm.findBy(refTblKey, filterForm, Page.MAXCOUNTPERQUERY, 1, context).getData();
//      mappingFormMap.put(key, list);
//  }
//  return mappingFormMap.get(key);
//}
///**
//* @param context
//* @param dbm
//* @param field
//* @param maxPageRecord
//* @param currPageNO
//* @return Page
//* @throws Throwable
//*/
//public Page getPage(IContext context, IDBManager dbm, Field field,
//      int maxPageRecord, int currPageNO) throws Throwable {
//  // Field field = ConfigUtil.getFieldByName(table, thisFldKey);
//  String refTblKey = ConfigUtil.getReferenceFieldName(field);
//  IRecord filterForm = new HashRecord();
//  filterForm.set(refTblKey, this.getParameter(field.getName()));
//  return dbm.findByForm(refTblKey, filterForm, maxPageRecord, currPageNO, context);
//}
///**
//* @param context
//* @param dbm
//* @param table
//* @param thisFldKey
//* @param maxPageRecord
//* @param currPageNO
//* @return Page
//* @throws Throwable
//*/
//public Page getPage(SrvContext context, DBManager dbm, Table table,
//      String thisFldKey, int maxPageRecord, int currPageNO)
//      throws Throwable {
//  Field field = ConfigUtil.getFieldByName(table, thisFldKey);
//  return this.getPage(context, dbm, field, maxPageRecord, currPageNO);
//}
///**
//* sychronized的取数过程
//* 
//* @param context
//* @param dbm
//* @param refTblKey
//*            关联表key
//* @param refFldKey
//*            关联表字段key(关联表外键)
//* @param thisFldKey
//*            本表字段key(外键)
//* @param maxPageRecord
//* @param currPageNO
//* @return Page 分页对象
//* @throws Throwable
//*/
//public Page getPage(SrvContext context, DBManager dbm, String linkTblKey,
//      String linkFldKey, String thisFldKey, int maxPageRecord,
//      int currPageNO) throws Throwable {
//  DefaultRecord filterForm = new MapForm();
//  filterForm.set(linkFldKey, this.getParameter(thisFldKey));
//  return dbm.findByForm(linkTblKey, filterForm, maxPageRecord, currPageNO, context);
//}
//if (tableName == null && "FORM".equalsIgnoreCase(tableName))
//throw new Warning("mainTableName is null");
//String thisFldKey =
//ConfigUtil.getChildTableRefFieldName(parentTable);
//String refFldKey = ConfigUtil.getChildTableRefFieldName(parentTable);