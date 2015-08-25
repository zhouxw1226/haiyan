<%@ page session="true" contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%--
  the values for "arrow=xxx" are the names of the images jpivot/jpivot/table/arrow-xxx.gif
--%>
<!--
jdbc:mysql://localhost:3306/crmmis2?useUnicode=true&characterEncoding=GBK&user=root&password=sa" 
-->
<%
System.out.println("tornado3.jsp");
%>
<jp:mondrianQuery id="query01" 
	jdbcDriver="oracle.jdbc.driver.OracleDriver" 
	jdbcUrl="jdbc:oracle:thin:crm/crm@localhost:1521:ORACLE"
	catalogUri="/WEB-INF/queries/crm/Tornado3.xml">
SELECT 
	{[Measures].[TOTAL_CUSTOMERS],[Measures].[AFFE_CUSTOMERS],[Measures].[UNAFFE_CUSTOMERS]} on columns, 
	{([USERS].[ALL_USERS],[PROVINCES].[ALL_PROVINCES])} on rows 
FROM [CUSTOMER] 
</jp:mondrianQuery>

<c:set var="title01" scope="session">Tornado</c:set>
