<%@ page session="true" pageEncoding="UTF-8" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!-- 
    * Change uri attribute to your deployment of this webapp.
    * The dataSource attribute is necessary for Mondrian's XMLA.
-->
<jp:xmlaQuery id="query01"
    uri="http://1.1.9.9:3000/m/xmla"
    dataSource="Provider=Mondrian;DataSource=Tornado;"
    catalog="Tornado">
SELECT 
	{
		[Measures].[TOTAL_CUSTOMERS]
	} on columns, 
	{[USERS].[ALL_USERS]} on rows 
FROM [CUSTOMER] 
</jp:xmlaQuery>

<c:set var="title01" scope="session">Accessing Mondrian By XMLA</c:set>
