/*
 * Created on 2004-10-9
 */
package haiyan.orm.database.sql.query;


/**
 * @author zhouxw
 */
class InnerJoinedTable extends JoinedTable {

	/**
	 * @param tableName
	 * @param PKColumnName
	 * @param ChildTableName
	 * @param ChildTableColName
	 */
	InnerJoinedTable(String tableName, String PKColumnName,
			String ChildTableName, String ChildTableColName,
			PrimaryTable pTable, int index) {
		super(tableName, PKColumnName, ChildTableName, ChildTableColName,
				pTable, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.haiyan.genmis.util.queryengine.JoinedTable#getJoinKeyWord()
	 */
	public String getJoinKeyWord() {
		return "inner join";
	}

}
