import java.util.List;

import mondrian.olap.Cell;
import mondrian.olap.DriverManager;
import mondrian.olap.Member;
import mondrian.olap.Position;
import mondrian.olap.Query;
import mondrian.olap.Result;
import mondrian.olap.Util;

/**
 * @author zhouxw
 * 
 */
public class TestPrintMondrian {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Connection connection = DriverManager
		// .getConnection(
		// "Provider=mondrian;"
		// + "Jdbc=jdbc:odbc:MondrianFoodMart;"
		// + "Catalog=E:/eclipse_workspace/mondrian3/demo/FoodMart.xml;",
		// null);// , false
		// // Query query = connection
		// // .parseQuery("SELECT {[Measures].[Unit Sales], [Measures].[Store
		// // Sales]} on columns,"
		// // + " {[Product].children} on rows "
		// // + "FROM [Sales] "
		// // + "WHERE ([Time].[1997].[Q1], [Store].[CA].[San Francisco])");
		// Query query = connection
		// .parseQuery("select {[Measures].[Unit Sales]} on columns from
		// Sales");
		// Result result = connection.execute(query);
		// result.print(new PrintWriter(System.out));
		// // result.print(new PrintWriter(new
		// FileOutputStream("F://test.html")));
		// //
		String connString = "Provider=mondrian;"
				+ "Jdbc=jdbc:odbc:MondrianFoodMart;" + "Catalog="
				+ FileUtil.path + "/FoodMart.xml;";
		String queryString = ""
				+ "with member [Measures].[Total Store Sales] as 'Sum(YTD(),[Measures].[Store Sales])' "
				+ "select "
				+ "{[Measures].[Total Store Sales]} on columns, "
				+ "{TopCount([Product].[Product Department].members, 5, [Measures].[Total Store Sales])} on rows "
				+ "from Sales " + "where ([Time].[1997].[Q2].[4]) ";
		// String queryString = ""
		// + "SELECT {[Measures].[Unit Sales]} on columns, "
		// + "TopCount([Product].[Brand].members, "
		// + "Parameter(\"Count\", NUMERIC, 10, \"Number of products to show\"),
		// "
		// + "(Parameter(\"Region\", [Store], [Store].[USA].[CA]), "
		// + "[Measures].[Unit Sales])) on rows " + "FROM Sales ";
		String html = new TestPrintMondrian().print(connString, queryString)
				.toString();
		FileUtil.String2File(html, "F:/testMondrian.html", false);
	}

	/**
	 * @param connectString
	 * @param queryString
	 * @return StringBuffer
	 */
	public StringBuffer print(String connectString, String queryString) {
		mondrian.olap.Connection mdxConnection = null;
		StringBuffer html = new StringBuffer();

		// execute the query
		try {
			mdxConnection = DriverManager.getConnection(connectString, null);
			Query q = mdxConnection.parseQuery(queryString);
			Result result = mdxConnection.execute(q);
			// ÇÐÃæ
			List<Position> slicers = result.getSlicerAxis().getPositions();
			html.append("<table class='resulttable' cellspacing=1 border=0>");
			final String nl = System.getProperty("line.separator");
			html.append(nl);
			// ÁÐ
			List<Position> columns = result.getAxes()[0].getPositions();
			// ÐÐ
			List<Position> rows = null;
			if (result.getAxes().length == 2)
				rows = result.getAxes()[1].getPositions();

			int columnWidth = columns.get(0).size();
			int rowWidth = 0;
			if (result.getAxes().length == 2)
				rowWidth = result.getAxes()[1].getPositions().get(0).size();

			for (int j = 0; j < columnWidth; j++) {
				html.append("<tr>");

				// if it has more than 1 dimension
				if (j == 0 && result.getAxes().length > 1) {
					// Print the top-left cell, and fill it with slicer members.
					html.append("<td nowrap class='slicer' rowspan='"
							+ columnWidth + "' colspan='" + rowWidth + "'>");
					for (int i = 0; i < slicers.size(); i++) {
						Position position = slicers.get(i);
						for (int k = 0; k < position.size(); k++) {
							if (k > 0) {
								html.append("<br/>");
							}
							Member member = position.get(k);
							html.append(member.getUniqueName());
						}
					}
					html.append("&nbsp;</td>" + nl);
				}

				// Print the column headings.
				for (int i = 0; i < columns.size(); i++) {
					Member member = columns.get(i).get(j);
					int width = 1;
					while ((i + 1) < columns.size()
							&& columns.get(i + 1).get(j) == member) {
						i++;
						width++;
					}
					html.append("<td nowrap class='columnheading' colspan='"
							+ width + "'>" + member.getUniqueName() + "</td>");
				}
				html.append("</tr>" + nl);
			}
			// if is two axes, show
			if (result.getAxes().length > 1) {
				for (int i = 0; i < rows.size(); i++) {
					html.append("<tr>");
					final Position row = rows.get(i);
					for (int j = 0; j < row.size(); j++) {
						Member member = row.get(j);
						html.append("<td nowrap class='rowheading'>"
								+ member.getUniqueName() + "</td>");
					}
					for (int j = 0; j < columns.size(); j++) {
						showCell(html, result.getCell(new int[] { j, i }));
					}
					html.append("</tr>");
				}
			} else {
				html.append("<tr>");
				for (int i = 0; i < columns.size(); i++) {
					showCell(html, result.getCell(new int[] { i }));
				}
				html.append("</tr>");
			}
			html.append("</table>");
		} catch (Throwable ex) {
			ex.printStackTrace();
			final String[] strings = Util.convertStackToString(ex);
			html.append("Error:<pre><blockquote>");
			for (int i = 0; i < strings.length; i++) {
				html.append(strings[i]);
			}
			html.append("</blockquote></pre>");
		} finally {
			if (mdxConnection != null) {
				mdxConnection.close();
			}
		}
		return html;
	}

	private void showCell(StringBuffer out, Cell cell) {
		out.append("<td class='cell'>" + cell.getFormattedValue() + "</td>");
	}
}
