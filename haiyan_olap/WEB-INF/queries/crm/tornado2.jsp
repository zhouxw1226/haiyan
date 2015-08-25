<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!-- 
net.sourceforge.jtds.jdbc.Driver
jdbc:mysql://localhost:3306/crmmis2?useUnicode=true&characterEncoding=GBK&user=root&password=sa" 

oracle.jdbc.driver.OracleDriver
jdbc:oracle:thin:crm/crm@localhost:1521:ORACLE

com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc:sqlserver://127.0.0.1:1433;databaseName=londer;username=qbq3;password=sa;
-->
<%
System.out.println("tornado2.jsp");
%>
<jp:mondrianQuery id="query01" 
	jdbcDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver" 
	jdbcUrl="jdbc:sqlserver://127.0.0.1:1433;databaseName=londer;username=qbq3;password=sa;"
	catalogUri="/WEB-INF/queries/crm/Tornado2.xml">
SELECT 
	{
		[Measures].[TOTAL_CUSTOMERS]
		, [Measures].[YX_CUSTOMERS]
		, [Measures].[WX_CUSTOMERS]
		, [Measures].[SJ_CUSTOMERS]
		, [Measures].[LX_CUSTOMERS]
		, [Measures].[HT_CUSTOMERS]
	} on columns, 
	{[USERS].[ALL_USERS]} on rows 
FROM [CUSTOMER] 
</jp:mondrianQuery>
<c:set var="title01" scope="session">CRM 总体指标分析</c:set>
