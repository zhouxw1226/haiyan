/*
 * Created on 2004-9-26
 */
package haiyan.orm.database;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouxw
 */
public class DBRecord extends AbstractDBRecord {

    private static final long serialVersionUID = 1L;
    protected Map<String, Object> dataMap = new HashMap<String, Object>(10, 1); // AbstractDBRecord实现类的dataMap实现多样化
    public DBRecord() {
        super();
    }
    @Override
    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }
    @Override
    protected boolean containsKey(String name) {
        return dataMap.containsKey(name);
    }
    @Override
    protected Object removeParameter(String name) {
        return dataMap.remove(name);
    }
    @Override
    protected void setParameter(String name, Object value) {
        dataMap.put(name, value);
    }
    @Override
    protected void setParameterValues(String name, Object[] values) {
        dataMap.put(name, values);
    }
    @Override
    protected Object getParameter(String name) {
		if (name == null)
			return null;
		if (dataMap==null)
			return null;
        Object obj = dataMap.get(name);
        return obj;
    }
    @Override
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