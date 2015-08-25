<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
	<head>
		<title>早Fan-Olap</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/mdxtable.css">
		<link rel="stylesheet" type="text/css" href="css/mdxnavi.css">
	</head>
	<body>
		<p>
			<h2>Olap Demonstration</h2>
			<c:if test="${query01 != null}">
	 			<jp:destroyQuery id="query01"/>
			</c:if>
			<h3>Olap Engine</h3>
			<ul>
				<li><a href="olap.jsp?query=Zaofans" >早Fan-订单分析</a></li>
				<!-- 
				<li><a href="olap.jsp?query=tornado4" target="_blank">Haiyan-库存分析</a></li>
				<li><a href="olap.jsp?query=wm_product" target="_blank">Haiyan-WMProduct2</a></li>
				-->
				<!-- 
				<br />
				<li><a href="olap.jsp?query=tornado" target="_blank">TORNADO -View_PID</a></li>
				<li><a href="olap.jsp?query=tornado0" target="_blank">TORNADO0-CRM_View_2_Hierarchies</a></li>
				<li><a href="olap.jsp?query=tornado1" target="_blank">TORNADO1-Join</a></li>
				<li><a href="olap.jsp?query=tornado2" target="_blank">TORNADO2-CRM_View_Union</a></li>
				<li><a href="olap.jsp?query=tornado3" target="_blank">TORNADO3-Other1</a></li>
				<li><a href="olap.jsp?query=tornado4" target="_blank">TORNADO4-Other2</a></li>
				<br />
				<li><a href="olap.jsp?query=mondrian" target="_blank">Slice and Dice with two hierarchies</a></li>
				<li><a href="olap.jsp?query=fourhier" target="_blank">...and with four hierachies</a></li>
				<li><a href="olap.jsp?query=arrows" target="_blank">Arrows in Cells</a></li>
				<li><a href="olap.jsp?query=colors" target="_blank">Colors in Cells</a></li>
				<li><a href="olap.jsp?query=testquery" target="_blank">Test data</a></li>
				<li><a href="demo/test/param1.jsp?query=testquery" target="_blank">Dynamic parameters with test data</a></li>
				<li><a href="demo/test/param3.jsp?query=mondrian" target="_blank">Dynamic parameters with Mondrian</a></li>
				-->
			</ul>
		<p/>
		<!--
		<h3>Using XML/A</h3>
		<p/>
			XML/A is a Web services protocol and standard that allows JPivot to connect to Microsoft 
			Analysis Services and Mondrian on other machines. This example connects to Mondrian via
			XML/A where you are running JPivot.
		<p/>
		<ul>
			<li><a href="olap.jsp?query=mondrianXMLA">Slice and Dice with two hierarchies</a></li>
		</ul>
		<p/>
		-->
	</body>
</html>
