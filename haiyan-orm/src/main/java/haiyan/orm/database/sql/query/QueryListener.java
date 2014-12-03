package haiyan.orm.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhouxw
 */
public interface QueryListener {

	/**
	 * @param sql
	 * @return String
	 */
	public StringBuffer wrapSQL(StringBuffer sql);

	/**
	 * @param selectPS
	 * @param lastIndex
	 * @throws SQLException
	 */
	public void setSelectPS(PreparedStatement selectPS, int lastIndex)
			throws SQLException;
}
