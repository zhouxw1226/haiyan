/*
 * Created on 2004-10-9
 */
package haiyan.orm.database.sql.query;


/**
 * @author zhouxw
 */
abstract class JoinedTable extends SQLTable {

	private String PKColumnName;

	private String childTableAlias;

	private String childTableColName;

	private PrimaryTable pTable;

	/**
	 * @param tableName
	 * @param pkColumnName
	 * @param hildTableName
	 * @param childTableColName
	 * @param pTable
	 * @param index
	 */
	JoinedTable(String tableName, String pkColumnName, String hildTableName,
			String childTableColName, PrimaryTable pTable, int index) {
		this.tableName = tableName;
		this.PKColumnName = pkColumnName;
		this.childTableAlias = hildTableName;
		this.childTableColName = childTableColName;
		this.pTable = pTable;
		this.index = index;
	}

	/**
	 * @return
	 */
	String getChildTableAlias() {
		return childTableAlias;
	}

	/**
	 * @return
	 */
	PrimaryTable getPTable() {
		return pTable;
	}

	/**
	 * @return Returns the childTableColName.
	 */
	String getChildTableColName() {
		return childTableColName;
	}

	/**
	 * @param childTableColName
	 *            The childTableColName to set.
	 */
	void setChildTableColName(String childTableColName) {
		this.childTableColName = childTableColName;
	}

	/**
	 * @return Returns the pKColumnName.
	 */
	String getPKColumnName() {
		return PKColumnName;
	}

	/**
	 * @param columnName
	 *            The pKColumnName to set.
	 */
	void setPKColumnName(String columnName) {
		PKColumnName = columnName;
	}

	/**
	 * @return Returns the childTableName.
	 */
	String getChildTableName() {
		return childTableAlias;
	}

	/**
	 * @param childTableName
	 *            The childTableName to set.
	 */
	void setChildTableName(String childTableName) {
		childTableAlias = childTableName;
	}

	/**
	 * @return String
	 */
	String getJoinSQL() {
		//
		StringBuffer buf = new StringBuffer();
		String thisAlias = getTableAlias(); //
		buf.append(" ").append(getJoinKeyWord()).append(" ").append(this.getTableName()).append(" ").append(thisAlias)
			.append(" on ").append(thisAlias).append(".").append(this.PKColumnName)
			.append("=").append(childTableAlias).append(".").append(this.childTableColName);
		JoinedTable joinedTable = null;
		//
		for (int i = 0; i < joinedTables.size(); i++) {
			joinedTable = (JoinedTable) joinedTables.get(i);
			buf.append(joinedTable.getJoinSQL());
		}
		return buf.toString();
	}

	/**
	 * @return String
	 */
	abstract String getJoinKeyWord();

}