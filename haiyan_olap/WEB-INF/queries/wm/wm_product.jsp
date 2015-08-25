<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%
System.out.println("wm_product.jsp");
%>
<!-- 必须是query01 因为olap.jsp里这么定义了，放到pageContext中 -->
<jp:mondrianQuery id="query01" 
	jdbcDriver="com.mysql.jdbc.Driver" 
	jdbcUrl="jdbc:mysql://127.0.0.1:3306/leedon?useUnicode=true&characterEncoding=UTF-8"
	jdbcUser="root"
	jdbcPassword=""
	catalogUri="/WEB-INF/queries/wm_product.xml">
SELECT 
	{
		[Measures].[TOTAL_WMPRODUCT]
	} on columns, 
	{(
		[PRODUCTS].[ALL_PRODUCTS],[WAREHOUSES].[ALL_WAREHOUSES]
	)} on rows 
FROM [WMPRODUCT]
</jp:mondrianQuery>

<!-- 必须是title01 因为olap.jsp里这么定义了  从pageContext中把结果丢到session中 -->
<c:set var="title01" scope="session">库存分析</c:set>
