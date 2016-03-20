/*
 * Created on 2004-9-26
 */
package haiyan.orm.database;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author zhouxw
 */
public class DBRecord extends AbstractDBRecord {

    private static final long serialVersionUID = 1L;
    protected Map<String, Object> dataMap = new HashMap<String, Object>(10, 1); // AbstractDBRecord实现类的dataMap实现多样化
    public DBRecord() {
        super();
    }
    public DBRecord(JSONObject json) {
        super();
        this.fromJSon(json, true);
    }
    @Override
    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }
    @Override
    protected boolean containsKey(String name) {
		if (name == null)
			return false;
        return dataMap.containsKey(name);
    }
    @Override
    protected Object removeParameter(String name) {
		if (name == null)
			return null;
        return dataMap.remove(name);
    }
    @Override
    protected void setParameter(String name, Object value) {
		if (name == null)
			return;
        dataMap.put(name, value);
    }
    @Override
    protected void setParameterValues(String name, Object[] values) {
		if (name == null)
			return;
        dataMap.put(name, values);
    }
    @Override
    protected Object getParameter(String name) {
		if (name == null)
			return null;
        return dataMap.get(name);
    }
    @Override
    protected Object[] getParameterValues(String name) {
		if (name == null)
			return null;
        Object obj = dataMap.get(name);
        Object[] result = obj == null ? null : (obj instanceof Object[] ? (Object[]) obj : new Object[] { obj });
        return result;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void putAll(Map map) {
		this.dataMap.putAll(map);
	}

} 