import mondrian.test.loader.MondrianFoodMartLoader;

public class TestLoadSQL {
	// -verbose -tables -data -indexes
	// -jdbcDrivers=com.mysql.jdbc.Driver
	// -inputFile=/mondrian/demo/FoodMartCreateData.sql
	// -outputJdbcURL="jdbc:mysql://localhost/foodmart?user=foodmart&password=foodmart"
	public static void main(String[] args) {
		args = new String[] {
				"-verbose",
				"-tables",
				"-data",
				"-indexes",
				"-jdbcDrivers=com.mysql.jdbc.Driver",
				"-inputFile=../WEB-INF/queries/FoodMartCreateData.sql",
				"-outputJdbcURL=jdbc:mysql://localhost:3306/foodmart?useUnicode=true&amp;characterEncoding=GBK&user=root&password=sa" };
		MondrianFoodMartLoader.main(args);
		// [-verbose] [-tables] [-data] [-indexes]
		// -jdbcDrivers=<jdbcDrivers>
		// -outputJdbcURL=<jdbcURL>
		// [ [ [-outputJdbcUser=user] [-outputJdbcPassword=password]
		// [-outputJdbcBatchSize=<batch size>] ]
		// | -outputDirectory=<directory name>
		// ]
		// [ [-inputJdbcURL=<jdbcURL> [-inputJdbcUser=user]
		// [-inputJdbcPassword=password] ]
		// | [-inputFile=<file name>]
		// ]
	}
}
