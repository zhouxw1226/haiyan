<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!-- 
jdbc:mysql://localhost:3306/crmmis2?useUnicode=true&characterEncoding=GBK&user=root&password=sa" 
-->
<%
System.out.println("tornado1.jsp");
%>
<jp:mondrianQuery id="query01" 
	jdbcDriver="oracle.jdbc.driver.OracleDriver" 
	jdbcUrl="jdbc:oracle:thin:crm/crm@localhost:1521:ORACLE"
	catalogUri="/WEB-INF/queries/crm/Tornado1.xml">
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
