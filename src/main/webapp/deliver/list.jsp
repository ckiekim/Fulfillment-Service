<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- ==================================================================== -->
	<title>Ezen Fulfillment System</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<%@ include file="../common/_top.jspf" %>
	
	<div class="container-fluid">
		<div class="row" style="margin-top: 100px">
			<div class="col-md-2">
				<div class="list-group">
					<a href="#" class="list-group-item active">목록</a>
					<a href="#" class="list-group-item">일별</a>
					<a href="#" class="list-group-item">월별</a>
					<a href="#" class="list-group-item">정산</a>
				</div>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-7"><h3>출고대기목록 조회</h3></div>
					<div class="col-md-5"><br>
						<a class="btn btn-primary" href="deliverServlet?action=release&time=am" role="button">오전 출고처리</a>&nbsp;&nbsp;&nbsp;
						<a class="btn btn-primary" href="deliverServlet?action=release&time=pm" role="button">오후 출고처리</a>
					</div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-11">
						<div class="panel panel-primary">
							<table class="table table-striped table-condensed">
								<tr class="active">
									<th class="col-md-1">ID</th>
									<th class="col-md-1">주문자명</th>
									<th class="col-md-2">연락처</th>
									<th class="col-md-3">주소</th>
									<th class="col-md-2">주문일시</th>
									<th class="col-md-1">금액</th>
									<th class="col-md-1">상태</th>
								</tr>
								<c:set var="vList" value="${requestScope.deliveryWaitList}"/>
								<c:forEach var="invoice" items="${vList}">
								<tr>
									<td><a href=#>${invoice.vid}</a></td>
									<td>${invoice.vname}</td>
									<td>${invoice.vtel}</td>
									<td>${invoice.vaddr}</td>
									<td>${invoice.vdate}</td>
									<td>${invoice.vtotal}</td>
									<td>${invoice.vstatus}</td>
								</tr>
								</c:forEach>
<%-- 								<tr align="center"><td colspan="7">
									<c:set var="pList" value="${requestScope.pageList}"/>
									<nav>
									  <ul class="pagination">
									    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
											<c:forEach var="page" items="${pList}">
												<c:choose>
													<c:when test="${currentInvoicePage == page}">
														<li class="active"><a href="#">${page}<span class="sr-only">(current)</span></a></li>
													</c:when>
													<c:otherwise>
														<li><a href="adminServlet?action=invoice&page=${page}">${page}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
									    <li class="disabled"><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
									  </ul>
									</nav>
								</td></tr> --%>
							</table>
						</div>
					</div>
					<div class="col-md-1">
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/_bottom.jspf" %>
	<!-- ==================================================================== -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
</body>
</html>