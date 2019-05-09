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
					<a href="#" class="list-group-item active">사용자 조회</a>
					<a href="adminServlet?action=productList&category=가전" class="list-group-item">상품 조회</a>
					<a href="adminServlet?action=invoice&page=1" class="list-group-item">주문</a>
					<a href="adminServlet?action=deliver" class="list-group-item">출고</a>
					<a href="adminServlet?action=purchase" class="list-group-item">입고</a>
					<a href="adminServlet?action=inventory&page=1" class="list-group-item">재고</a>
					<a href="#" class="list-group-item">정산</a>
				</div>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-12"><h3>사용자 조회</h3></div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-9">
						<div class="panel panel-primary">
							<table class="table table-striped">
								<tr class="active">
									<th class="col-md-2">사용자ID</th>
									<th class="col-md-2">사용자명</th>
									<th class="col-md-3">회사명</th>
									<th class="col-md-2">역할</th>
								</tr>
								<c:set var="uList" value="${requestScope.userList}"/>
								<c:forEach var="user" items="${uList}">
								<tr>
									<td>${user.uid}</td>
									<td>${user.uname}</td>
									<td><a data-target="#modal${user.ucomId}" data-toggle="modal">
											${user.ucomName}</a></td>
									<td>${user.ucomRole}</td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/_bottom.jspf" %>
	
	<div class="row">
	<c:set var="cList" value="${requestScope.companyList}"/>
	<c:forEach var="cDto" items="${cList}">
		<div class="modal" id="modal${cDto.cid}" tabindex="-1">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">${cDto.cname}</h4>
					</div>
					<div class="modal-body" style="text-align:center;">
						<table class="table table-default">
							<tr class="info"><td>항목</td><td>내용</td></tr>
							<tr><td>아이디</td><td>${cDto.cid}</td></tr>
							<tr><td>역할</td><td>${cDto.crole}</td></tr>
							<tr><td>전화</td><td>${cDto.ctel}</td></tr>
							<tr><td>팩스</td><td>${cDto.cfax}</td></tr>
							<tr><td>이메일</td><td>${cDto.cmail}</td></tr>
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