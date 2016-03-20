/*
 * Created on 2004-10-9
 */
package haiyan.orm.database.sql.query;

import haiyan.common.StringBufferUtil;
import haiyan.common.SysCode;
import haiyan.common.exception.Warning;

import java.util.HashMap;

/**
 * @author zhouxw
 */
public class PrimaryTable extends SQLTable {

	private String selectColumnSQL = "";
	private String primaryTableSQL = null;

	/**
	 * @param tableName
	 * @param selectColumnSQL
	 */
	public PrimaryTable(String tableName, String selectColumnSQL) {
		this.tableName = tableName;
		this.selectColumnSQL = selectColumnSQL;
	}

	/**
	 * @return String
	 */
	public String getFormSQL() {
		StringBufferUtil buf = new StringBufferUtil();
		if (this.primaryTableSQL!=null) 
			buf.append(this.primaryTableSQL).append(" ").append(getFirstTableAlias());
		else
			buf.append(this.tableName).append(" ").append(getFirstTableAlias());
		JoinedTable joinedTable = null;
		for (int i = 0; i < joinedTables.size(); i++) {
			joinedTable = (JoinedTable) joinedTables.get(i);
			buf.append(" ").append(joinedTable.getJoinSQL()).append(" ");
		}
		return buf.toString();
	}
	
	public void setPrimaryTableSQL(String sql) {
		this.primaryTableSQL = sql;
	}

	/**
	 * @return String
	 */
	public String getSQL() {
		return this.selectColumnSQL + getFormSQL();
	}

	/**
	 * @return String
	 */
	public String getSelectColumnSQL() {
		return this.selectColumnSQL;
	}

	/**
	 * @param sql
	 */
	public void setSelectColumnSQL(String sql) {
		this.selectColumnSQL = sql;
	}

	private transient HashMap<String, String> aliasMap = new HashMap<String, String>();

	/**
	 * @param index
	 * @return String
	 */
	public String getTableAliasByIndex(int index) {
		SQLTable tn = getTableNodeByIndex(index);
		if (tn == null) {
			throw new Warning(SysCode.create(null, 100026, "error_table_field_index", new String[] { tableName, String.valueOf(index) }));
		}
		// t_n第几张关联表
		return tn.getTableAlias();
	}

	/**
	 * @return String
	 */
	public String getFirstTableAlias() {
		String alias = "";
		if (aliasMap.get(tableName) == null) {
			SQLTable tn = getTableNodeByName(getIndex(), tableName);
			if (tn == null) {
				// String[] errValues = new String[] { tableName, String.valueOf(index) };
				throw new Warning(null, 100026, "error_table_field_index", new String[] { tableName, String.valueOf(index) });
			}
			alias = tn.getTableAlias();
			aliasMap.put(tableName, alias);
		} else {
			alias = (String) aliasMap.get(tableName);
		}
		return alias;
	}
}