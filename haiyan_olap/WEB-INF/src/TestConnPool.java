import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.OlapWrapper;

public class TestConnPool {

	public static void main(String[] args) throws SQLException {
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
		// "jdbc:mondrian:local:Jdbc=jdbc:odbc:MondrianFoodMart;"
				"Jdbc=jdbc:odbc:MondrianFoodMart;"
				//
						+ "Catalog=" + FileUtil.path + "SimpleFoodMart.xml;"
				// + "Role='California manager'"
				, "", "");
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, false, true);
		DataSource dataSource = new PoolingDataSource(connectionPool);
		// and some time later...
		Connection connection = dataSource.getConnection();
		OlapWrapper wrapper = (OlapWrapper) connection;
		OlapConnection olapConnection = wrapper.unwrap(OlapConnection.class);
		OlapStatement statement = olapConnection.createStatement();
	}
}
