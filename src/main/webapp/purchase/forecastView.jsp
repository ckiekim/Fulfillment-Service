<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="weather.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../common/_header.jspf" %>
</head>

<body>
	<%@ include file="../common/_top.jspf" %>
      	<div class="col-md-10">
	      	<ul class="nav nav-tabs">
				<li><a href="../view/mainView.jsp">Devices</a></li>
				<li><a href="../view/photoList.jsp">사진</a></li>
				<li><a href="../view/historySensor.jsp">이력</a></li>
				<li><a href="#">모니터링</a></li>
			</ul>
			<%
			String baseDate = null;
			String baseTime = null;
			WeatherDAO dao = new WeatherDAO();
			//ArrayList<WeatherDTO> list = dao.weatherGetValueList();
			%>
			<br>
			<p><div id="baseDT"></div></p>
			<table class="table table-striped table-condensed">
				<thead>
					<tr><th rowspan="2" class="col-md-2">예보&nbsp;일시</th>
						<th class="col-md-2">하늘</th><th class="col-md-1">최저</th>
						<th class="col-md-1">최고</th><th class="col-md-1">습도</th>
						<th class="col-md-2">강수확률</th><th class="col-md-1">비/눈</th>
						<th class="col-md-1">강수량</th><th class="col-md-1">적설량</th>
					<tr><th> </th> <th>(℃)</th> <th>(℃)</th> <th>(％)</th>
						<th>(％)</th> <th> </th> <th>(㎜)</th> <th>(㎝)</th> </tr>
				</thead>
				<tbody>
				<%
				for(WeatherDTO dto : list) {
				%>
					<tr> 
						<td><%=dto.getFcst_date()%>&nbsp;&nbsp;<%=dto.getFcst_time()%></td>
						<td><c:set var="sky" value="<%=dto.getSky()%>"/>
							<c:choose>
								<c:when test="${sky eq '1'}">맑음</c:when>
								<c:when test="${sky eq '2'}">구름조금</c:when>
								<c:when test="${sky eq '3'}">구름많음</c:when>
								<c:when test="${sky eq '4'}">흐림</c:when>
								<c:otherwise>${sky}</c:otherwise>
							</c:choose></td>
						<td><c:set var="tmn" value="<%=dto.getTmn()%>"/>
							<c:if test="${tmn ne '-50'}">${tmn}</c:if></td>
						<td><c:set var="tmx" value="<%=dto.getTmx()%>"/>
							<c:if test="${tmx ne '-50'}">${tmx}</c:if></td>
						<td><%=dto.getReh()%></td>
						<td><%=dto.getPop()%></td> 
						<td><c:set var="pty" value="<%=dto.getPty()%>"/>
							<c:choose>
								<c:when test="${pty eq '0'}">-</c:when>
								<c:when test="${pty eq '1'}">비</c:when>
								<c:when test="${pty eq '2'}">비/눈</c:when>
								<c:when test="${pty eq '3'}">눈</c:when>
								<c:otherwise>${pty}</c:otherwise>
							</c:choose></td>						
						<td><c:set var="r06" value="<%=dto.getR06()%>"/>
							<c:if test="${r06 ne '-1'}">${r06}</c:if></td> 
						<td><c:set var="s06" value="<%=dto.getS06()%>"/>
							<c:if test="${s06 ne '-1'}">${s06}</c:if></td>
					</tr>
				<%
					baseDate = dto.getBase_date();
					baseTime = dto.getBase_time();
				}
				%>
				</tbody>
			</table>
			<script type="text/javascript">
				var base = document.getElementById("baseDT");
				base.textContent = '발표일시 : ' + '<%=baseDate%>' + ' ' + '<%=baseTime%>';
			</script>
      	</div>
	<%@ include file="../common/_bottom.jspf" %>
</body>
</html>