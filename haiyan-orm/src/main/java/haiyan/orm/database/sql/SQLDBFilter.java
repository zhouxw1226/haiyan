/*
 * Created on 2007-6-12
 *
 */
package haiyan.orm.database.sql;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxw
 * 
 */
public class SQLDBFilter implements IDBFilter {
	
	private String mainSQL;
	private String sqlFilter;
	private Object[] values;
	private List<Object> params = new ArrayList<Object>();
	/**
	 * @param sql
	 * @param values
	 */
	protected SQLDBFilter(String sql) {
		this.sqlFilter = sql;
		this.sqlFilter = this.sqlFilter == null ? "" : this.sqlFilter;
	}
	/**
	 * @param sql
	 * @param values
	 */
	public SQLDBFilter(String sql, Object[] values) {
		this.sqlFilter = sql;
		this.sqlFilter = this.sqlFilter == null ? "" : this.sqlFilter;
		this.values = values;
	}
	@Override
	public String getFilter() {
		return sqlFilter;
	}
	@Override
	public void setFilter(String sFilter) {
		this.sqlFilter = sFilter;
	}
	@Override
	public List<Object> getParas() {
		return this.params;
	}
	private static final String PA = "?";
	private static final String PA_STR = PA; // "''?''";
	/**
	 * @return String
	 */
	public String getSql() {
		if (mainSQL!=null)
			return mainSQL;
		String result = sqlFilter, s;
		if (values != null)
			for (int i = 0; i < values.length; i++) {
//				if (values[i] == null)
//					// continue;
				s = "";
				if (values[i] == null) {
					s = PA;
					params.add("null");
				} else if (values[i] instanceof Object[]) {
					Object[] vals = (Object[])values[i];
					for (int j = 0; j < vals.length; j++) {
						if (j != 0)
							s += ",";
						s += PA;
						params.add(vals[j]);
					}
//					params.add(values[i]);
				} else { // if (values[i] instanceof String) 
					s = PA_STR;
					params.add(values[i]);
				} 
//				else if (values[i] instanceof Integer) {
//					s = PA;
//					params.add(values[i]);
//				} else if (values[i] instanceof Date) {
////					s = DateUtil.format((Date)values[i], "yyyy-MM-dd HH:mm:ss");
//					s = PA;
//					params.add(values[i]);
//				}
				//else
				//	throw new Warning("Unsupport Object!");
				result = StringUtil.replaceAll(result, "{" + i + "}", s);
			}
		// result = String.format(result, values);
		DebugUtil.debug(">filter: " + result);
		mainSQL = result;
		return mainSQL;
	}
	@Override
	public boolean filter(IDBRecord record) {
		return true;
	}
	@Override 
	public String toString() {
		return this.sqlFilter;
	}
}