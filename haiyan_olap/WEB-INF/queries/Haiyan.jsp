<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%
System.out.println("Haiyan.jsp");
%>
<!-- 必须是query01 因为testpage里这么定义了 -->
<jp:mondrianQuery id="query01" 
	jdbcDriver="com.mysql.jdbc.Driver" 
	jdbcUrl="jdbc:mysql://127.0.0.1:3306/leedon?useUnicode=true&characterEncoding=UTF-8"
	jdbcUser="root"
	jdbcPassword=""
	catalogUri="/WEB-INF/queries/Haiyan.xml">
SELECT 
	{[Measures].[TOTAL_CUSTOMERS]} on columns, 
	{[USERS].[ALL_USERS]} on rows 
FROM [CUSTOMER] 
</jp:mondrianQuery>
<!-- NON EMPTY {[Measures]. ... [DEPTS].[ALL_DEPTS],,[PROVINCES].[ALL_PROVINCES])} on rows -->

<!-- 必须是title01 因为testpage里这么定义了 -->
<c:set var="title01" scope="session">Haiyan</c:set>
