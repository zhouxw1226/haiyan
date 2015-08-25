<%@ page session="true" contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%--
  the values for "arrow=xxx" are the names of the images jpivot/jpivot/table/arrow-xxx.gif
--%>
<!--

-->

<jp:mondrianQuery id="query01" 
	jdbcDriver="oracle.jdbc.driver.OracleDriver" 
	jdbcUrl="jdbc:oracle:thin:@localhost:1521:ORACLE" 
	jdbcUser="bokeiis"
	jdbcPassword="bokeiis"
	catalogUri="/WEB-INF/queries/crm/bokeiis.xml">
SELECT 
	{[Measures].[TOTAL_CUSTOMERS],[Measures].[AFFE_CUSTOMERS],[Measures].[UNAFFE_CUSTOMERS]} on columns, 
	{([USERS].[ALL_USERS],[PROVINCES].[ALL_PROVINCES])} on rows 
FROM [CUSTOMER] 
</jp:mondrianQuery>

<c:set var="title01" scope="session">Tornado</c:set>
