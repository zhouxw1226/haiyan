/*
 * Created on 2004-10-9
 */
package haiyan.orm.database.sql.query;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author zhouxw
 */
public abstract class SQLTable {

	/**
	 * 
	 */
	static final String ALIAS_PREFIX = "t_";
	/**
	 * 
	 */
	static final int BEGIN_INDEX = 1;
	/**
	 * 
	 */
	int index = BEGIN_INDEX;
	/**
	 * 
	 */
	ArrayList<JoinedTable> joinedTables = new ArrayList<JoinedTable>();
	/**
	 * 
	 */
	String tableName;
	/**
	 * @return int
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index
	 */
	void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return this.tableName;
	}
	/**
	 * @param tableName
	 *            The tableName to set.
	 */
	void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @param jtable
	 */
	public void addJoinedTable(JoinedTable jtable) {
		joinedTables.add(jtable);
	}
	/**
	 * @param index
	 * @param tableName
	 * @return TableNode
	 */
	SQLTable getTableNodeByName(int index, String tableName) {
		SQLTable result = null;
		if (this.tableName.equals(tableName)) {
			result = this;
		} else {
			if (joinedTables.size() > 0) {
				for (Iterator<JoinedTable> iter = joinedTables.iterator(); iter.hasNext();) {
					SQLTable t = (SQLTable) iter.next();
					result = t.getTableNodeByName(++index, tableName);
					if (result != null) {
						break;
					}
				}
			}
		}
		return result;
	}
	/**
	 * @param index
	 * @param tableName
	 * @return TableNode
	 */
	SQLTable getTableNodeByIndex(int index) {
		SQLTable result = null;
		if (this.index == index) {
			result = this;
		} else {
			if (joinedTables.size() > 0) {
				for (Iterator<JoinedTable> iter = joinedTables.iterator(); iter.hasNext();) {
					SQLTable t = (SQLTable) iter.next();
					result = t.getTableNodeByIndex(index);
					if (result != null) {
						break;
					}
				}
			}
		}
		return result;
	}
	/**
	 * @return String
	 */
	public String getTableAlias() {
		return ALIAS_PREFIX + index;
	}

}