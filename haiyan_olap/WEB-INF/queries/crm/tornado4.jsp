<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%--
  the values for "arrow=xxx" are the names of the images jpivot/jpivot/table/arrow-xxx.gif
--%>
<%-- 
<jp:mondrianQuery id="query01" 
	jdbcDriver="oracle.jdbc.driver.OracleDriver" 
	jdbcUrl="jdbc:oracle:thin:crm/crm@localhost:1521:ORACLE"
	catalogUri="/WEB-INF/queries/crm/Tornado4.xml">
	--%>
<jp:mondrianQuery id="query01" 
	jdbcDriver="com.mysql.jdbc.Driver" 
	jdbcUrl="jdbc:mysql://127.0.0.1:3306/leedon?useUnicode=true&characterEncoding=UTF-8"
	jdbcUser="root"
	jdbcPassword=""
	catalogUri="/WEB-INF/queries/crm/Tornado4.xml" >
SELECT 
	NON EMPTY {[Measures].[TOTAL_CUSTOMERS],[Measures].[AFFE_CUSTOMERS],[Measures].[UNAFFE_CUSTOMERS]} on columns, 
	{([DEPTS].[ALL_DEPTS],[USERS].[ALL_USERS],[PROVINCES].[ALL_PROVINCES])} on rows 
FROM [CUSTOMER] 
</jp:mondrianQuery>

<c:set var="title01" scope="session">Tornado</c:set>
