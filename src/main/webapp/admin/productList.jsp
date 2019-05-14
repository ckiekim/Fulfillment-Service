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
				<%@ include file="../common/_admin_left.jspf" %>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-12"><h3>상품 조회</h3></div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-10">
						<div class="panel panel-primary">
							<table class="table table-striped table-condensed">
								<tr class="active">
									<th class="col-md-2">품목코드</th>
									<th class="col-md-3">제품명</th>
									<th class="col-md-2">가격</th>
									<th class="col-md-1">재고수량</th>
									<th class="col-md-2">카테고리</th>
								</tr>
								<c:set var="pList" value="${requestScope.productList}"/>
								<c:forEach var="pDto" items="${pList}">
								<tr>
									<td>${pDto.pid}</td>
									<td><a data-target="#modal${pDto.pid}" data-toggle="modal">
											${pDto.pname}</a></td>
									<td>${pDto.pprice}</td>
									<td>${pDto.pstock}</td>
									<td>${pDto.pcategory}</td>
								</tr>
								</c:forEach>
								<tr align="center"><td colspan="5"><br>
									<div class="btn-group">
										<button type="button" class="btn btn-primary">카테고리</button>
										<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
										    <span class="caret"></span>
										    <span class="sr-only">Toggle Dropdown</span>
										</button>
										<ul class="dropdown-menu" role="menu">
										    <li><a href="../admin/adminServlet?action=productList&category=가전">가전</a></li>
										    <li><a href="../admin/adminServlet?action=productList&category=스포츠">스포츠</a></li>
										    <li><a href="../admin/adminServlet?action=productList&category=식품">식품</a></li>
										</ul>
									</div><br><br>
								</td></tr>
							</table>
						</div>
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/_bottom.jspf" %>
	
	<div class="row">
	<c:forEach var="pDto" items="${pList}">
		<div class="modal" id="modal${pDto.pid}" tabindex="-1">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">${pDto.pname}</h4>
					</div>
					<div class="modal-body" style="text-align:center;">
						<table class="table table-default">
							<tr class="info"><td>항목</td><td>내용</td></tr>
							<tr><td>아이디</td><td>${pDto.pid}</td></tr>
							<tr><td>가격</td><td>${pDto.pprice}</td></tr>
							<tr><td>사진</td><td width="300" height="300">
								<img src="../img/${pDto.pimage}"></td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	</div>
	<!-- ==================================================================== -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
</body>
</html>