<!DOCTYPE HTML>
<%@ page session="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%
System.out.println("Zaofans.jsp");
%>
<!-- 必须是query01 因为testpage里这么定义了 -->
<jp:mondrianQuery id="query01" 
	jdbcDriver="com.mysql.jdbc.Driver" 
	jdbcUrl="jdbc:mysql://192.168.46.92:3306/yigo_prd?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull"
	jdbcUser="root"
	jdbcPassword="root.com"
	catalogUri="/WEB-INF/queries/zaofans/Zaofans.xml">
SELECT 
	{
		[Measures].[TOTOAL_ORDERS]
	} on columns, 
	{(
		[CITIES].[ALL_CITIES],[AREAS].[ALL_AREAS],[USERS].[ALL_USERS]
	)} on rows 
FROM [ORDERS] 
</jp:mondrianQuery>
<!-- NON EMPTY {[Measures]. ... [DEPTS].[ALL_DEPTS],,[PROVINCES].[ALL_PROVINCES])} on rows -->
<!-- 必须是title01 因为testpage里这么定义了 -->
<c:set var="title01" scope="session">早Fan</c:set>
