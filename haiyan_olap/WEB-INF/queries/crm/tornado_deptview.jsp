<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%--
  the values for "arrow=xxx" are the names of the images jpivot/jpivot/table/arrow-xxx.gif
--%>
<!--
jdbc:mysql://localhost:3306/crmmis2?useUnicode=true&characterEncoding=GBK&user=root&password=sa" 

driver="com.microsoft.sqlserver.jdbc.SQLServerDriver" url="jdbc:sqlserver://127.0.0.1:1433;databaseName=qbq3;" 
	username="qbq3" password="sa"
-->
<%
System.out.println("tornado_deptview.jsp");
%>
<jp:mondrianQuery id="query01" 
	jdbcDriver="oracle.jdbc.driver.OracleDriver" 
	jdbcUrl="jdbc:oracle:thin:bkcrm/bkcrm@localhost:1521:ORACLE"
	catalogUri="/WEB-INF/queries/tornado_deptview.xml">
SELECT 
	{
		[Measures].[TOTAL_CUSTOMERS]
		, [Measures].[YX_CUSTOMERS]
		, [Measures].[WX_CUSTOMERS]
		, [Measures].[SJ_CUSTOMERS]
		, [Measures].[LX_CUSTOMERS]
		, [Measures].[HT_CUSTOMERS]
		, [Measures].[CAL_SJCZL]
		, [Measures].[HTNAME]
		, [Measures].[HTMONEY]
	} on columns 
	, {
		([MONS].[ALL_MONS], [USERS].[ALL_USERS], [CUSS].[ALL_CUSS])
	} on rows 
FROM [CUSTOMER] 
</jp:mondrianQuery>

<c:set var="title01" scope="session">CRM</c:set>
