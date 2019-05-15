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
	<link href="../css/bootstrap.vertical-tabs.min.css" rel="stylesheet">
</head>
<body>
	<%@ include file="../common/_top.jspf" %>
	
	<div class="container-fluid">
		<div class="row" style="margin-top: 100px">
			<div class="col-md-2">
				<%@ include file="../common/_admin_left_deliver.jspf" %>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-8"><h3>출고확정 대기목록</h3></div>
					<div class="col-md-4"><br>
						<a class="btn btn-primary" href="../admin/adminServlet?action=deliverConfirm" role="button">출고 확정</a>
					</div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-10">
						<div class="panel panel-primary">
							<table class="table table-striped table-condensed">
								<tr class="active">
									<th class="col-md-1">ID</th>
									<th class="col-md-1">주문자명</th>
									<th class="col-md-3">주소</th>
									<th class="col-md-2">출고일시</th>
									<th class="col-md-2">운송회사</th>
									<th class="col-md-1">상태</th>
								</tr>
								<c:set var="dList" value="${requestScope.deliverList}"/>
								<c:forEach var="dDto" items="${dList}">
								<tr>
									<td><a href=#>${dDto.did}</a></td>
									<td>${dDto.dname}</td>
									<td>${dDto.daddr}</td>
									<td>${dDto.ddate}</td>
									<td>${dDto.dcomName}</td>
									<td>${dDto.dstatusName}</td>
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
					<div class="col-md-2">
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