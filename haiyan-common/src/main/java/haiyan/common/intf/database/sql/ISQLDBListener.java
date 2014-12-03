package haiyan.common.intf.database.sql;

import java.sql.ResultSet;

/**
 * @author ZhouXW
 * 
 */
public interface ISQLDBListener {

	void listen(ResultSet rs) throws Throwable;
}
