package test.common;

import haiyan.common.RecordBeanTranceferUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

public class TestRecordTrancefer {
	public static void main(String[] args) throws Throwable {
		TestRecordTrancefer util = new TestRecordTrancefer();
		util.testUser2Record();
		util.testRecord2User();
	}
	@Test
	public  void testUser2Record() throws Throwable{
		User user = new User();
		user.setCode("userCode");
		user.setDeptID("userDeptID");
		user.setAlive(true);
		IDBRecord record = new DBRecord();
		record = RecordBeanTranceferUtil.bean2Record(user, record);//user2Record( user);
		Assert.assertTrue(record.get("code").equals("userCode") && record.get("deptID").equals("userDeptID") && record.get("alive").equals(true));
	}
	@Test
	public  void testRecord2User() throws Throwable{
		IDBRecord record = new DBRecord();
		record.set("ID", "userID");
		record.set("name", "userName");
		record.set("DSN", "userDSN");
		record.set("alive", true);
		User user = new User();
		IUser result = (IUser) RecordBeanTranceferUtil.record2Bean(record, user);//record2User(record);
		Assert.assertTrue(result.getId().equals("userID") && result.getName().equals("userName") && result.getDSN().equals("userDSN") && result.isAlive());
	}
	@SuppressWarnings({"rawtypes","unchecked"})
	private class DBRecord implements IDBRecord{
	    private Map<String, Object> oreignDataMap = new HashMap<String, Object>(10, 1);
	    private Set<String> updatedKeys = new HashSet<String>(10, 1);
	    private Set<String> deletedKeys = new HashSet<String>(10, 1);
//	    private String tableName;
//	    public String getTableName() {
//	        return this.tableName;
//	    }
//	    public void setTableName(String tableName) {
//	        this.tableName = tableName;
//	    }
//	    private List<String> blobs = new ArrayList<String>();
//		public void addBlobFile(String b) {
//			if (b!=null)
//				blobs.add(b);
//		}
//		public List<String> getBlobFile() {
//			return blobs;
//		} 
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
//				for (String b:blobs) {
//					if (b!=null) {
//						File f = new File(b+".bak");
//						File f2 = new File(b);
//						FileUtil.copy(f, f2);
//						if (f.exists())
//							f.delete();
//						DebugUtil.debug(">save file:" + b);
//					}
//				}
//				blobs.clear();
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
//				for (String b:blobs) {
//					if (b!=null) {
//						File f = new File(b+".bak");
//						if (f.exists())
//							f.delete();
//						DebugUtil.debug(">delete file:" + b+".bak");
//					}
//				}
				this.deletedKeys.clear();
				this.updatedKeys.clear();
//				blobs.clear();
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
	    public JSONObject toJSon(boolean showAll, List<String> ignore) {
	        // JSONObject json = JSONObject.fromObject(getDataMap());
	        // return JSONObject.fromObject(getDataMap());
	        JSONObject json = new JSONObject();
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
	                json.put(keyName, value);
	            } else
	                json.put(keyName, "");
	        }
	        return json;
	    }
//		public void fromJSon(JSONObject json2) {
//			if (json2==null)
//				return;
//			JSONObject json;
//			if (json2.containsKey("dataMap"))
//				json = json2.getJSONObject("dataMap");
//			else
//				json = json2;
//			String k; Object o;
//			Iterator iter = json.keys();
//			while(iter.hasNext()) {
//				k = iter.next().toString();
//				o = json.get(k);
////				if (o instanceof net.sf.json.JSONObject) {
////					this.fromJSon((net.sf.json.JSONObject)o);
////					break;
////				}
////				System.out.println("------====="+o.getClass());
//				this.set(k, o==null?"":o.toString());
//			}
//		}
//	    /**
//	     * @param json
//	     */
//	    public void json2Form(JSONObject json) {
//	        Iterator<?> iter = json.keys();
//	        while (iter.hasNext()) {
//	            String key = (String) iter.next();
//	            if (!json.containsKey(key))
//	            	continue;
//	            Object val = json.get(key);
//	            if (val != null) {
//	                //if (val.startsWith("\"") && val.endsWith("\""))
//	                //    val = val.substring(0, val.length() - 2).substring(2);
//	                setParameter(key, val);
//	            }
//	        }
//	    }
	    /**
	     * @param keyName
	     * @return boolean
	     */
	    protected boolean isIgnoredTo(String keyName) {
	        return (keyName != null && Arrays.binarySearch(DataConstant.FORM_IGNORE, keyName) >= 0);
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
	    	this.oreignDataMap.put(name, this.get(name));
	    	this.updatedKeys.add(name); // 要加到updateSQL占位符中
	    	this.deletedKeys.add(name);
	    	return this.remove(name);
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
	        if (!StringUtil.isBlankOrNull(value)) {
	            try {
	                SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
	                return sdf.parse(VarUtil.toString(value));
//	                return new Date(sdf.parse(value).getTime());
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
		public Set<String> deletedKeySet() {
	    	return this.deletedKeys;
	    }
	    @Override
		public Set<String> updatedKeySet() {
	    	return this.updatedKeys;
	    }
	    @Override
		public Set<String> oreignKeySet() {
	        Map<String, Object> map = this.oreignDataMap;
	        return map.keySet();
	    }
	    @Override
		public Set<String> insertedKeySet() {
	        return this.dataKeySet();
	    }
	    @Override
		public Set<String> dataKeySet() {
	        Map<String, Object> map = this.getDataMap();
	        return map.keySet();
	    }
//	    @Override
//		public Iterator<String> keyIterator() {
//	        Map<String, Object> map = this.getDataMap();
//	        return map.keySet().iterator();
//	    }
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
//	    不同table配置根据field.key来copy的，现在只保证同table.key的版本控制
//	    private short version = 0;
//	    version 最好放在数据里
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
		
		private static final long serialVersionUID = 1L;
	    protected Map<String, Object> dataMap = new HashMap<String, Object>(10, 1); // AbstractDBRecord实现类的dataMap实现多样化
	    public DBRecord() {
	        super();
	    }
	    public Map<String, Object> getDataMap() {
	        return this.dataMap;
	    }
	    protected boolean containsKey(String name) {
	        return dataMap.containsKey(name);
	    }
	    protected Object removeParameter(String name) {
	        return dataMap.remove(name);
	    }
	    protected void setParameter(String name, Object value) {
	        dataMap.put(name, value);
	    }
	    protected void setParameterValues(String name, Object[] values) {
	        dataMap.put(name, values);
	    }
	    protected Object getParameter(String name) {
			if (name == null)
				return null;
			if (dataMap==null)
				return null;
	        Object obj = dataMap.get(name);
	        return obj;
	    }
	    protected Object[] getParameterValues(String name) {
			if (name == null)
				return null;
			if (dataMap==null)
				return null;
	        Object obj = dataMap.get(name);
	        Object[] result = obj == null ? null : (obj instanceof Object[] ? (Object[]) obj : new Object[] { obj });
	        return result;
	    }
		
	}
}
