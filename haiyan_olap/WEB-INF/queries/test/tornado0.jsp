<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!-- 
net.sourceforge.jtds.jdbc.Driver
jdbc:mysql://localhost:3306/crmmis2?useUnicode=true&characterEncoding=GBK&user=root&password=sa" 

oracle.jdbc.driver.OracleDriver
jdbc:oracle:thin:crm/crm@localhost:1521:ORACLE

com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc:sqlserver://127.0.0.1:1433;databaseName=londer;username=qbq3;password=sa;

mondrianQuery id="query01" 
	jdbcDriver="oracle.jdbc.driver.OracleDriver" 
	jdbcUrl="jdbc:oracle:thin:bkcrm/bkcrm@localhost:1521:ORCL"
	catalogUri="/WEB-INF/queries/Tornado0.xml"
-->
<%
System.out.println("tornado0.jsp");
%>

<jp:mondrianQuery id="query01" 
	jdbcDriver="com.mysql.jdbc.Driver" 
	jdbcUrl="jdbc:mysql://127.0.0.1:3306/leedon?useUnicode=true&characterEncoding=UTF-8"
	jdbcUser="root"
	jdbcPassword=""
	catalogUri="/WEB-INF/queries/Tornado0.xml">
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

<c:set var="title01" scope="session">CRM 总体指标分析</c:set>
