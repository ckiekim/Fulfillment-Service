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
					<div class="col-md-12"><h3>주문 상세 내역</h3></div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-1"></div>
					<div class="col-md-7">
						<div class="panel panel-primary">
							<c:set var="vDto" value="${requestScope.invoiceDTO}"/>
							<div class="panel-heading">
								<div class="panel-title">주문번호: ${vDto.vid},&nbsp;&nbsp;처리상태: ${vDto.vstatusName}</div>
							</div>
							<div class="panel-body">
								주문일자:&nbsp;&nbsp;${vDto.vdate}<br>
								주문처:&nbsp;&nbsp;${vDto.vcomName}<br>
								주문자:&nbsp;&nbsp;${vDto.vname}, ${vDto.vtel}, ${vDto.vaddr}<br>
								운송처:&nbsp;&nbsp;${vDto.vlogisName}<br>
								총 금액:&nbsp;&nbsp;${vDto.vtotal}원
							</div>
							<table class="table table-striped">
								<tr class="active">
									<th class="col-md-1">코드</th>
									<th class="col-md-2">제품명</th>
									<th class="col-md-1">가격</th>
									<th class="col-md-1">수량</th>
								</tr>
								<c:set var="sList" value="${requestScope.soldProductList}"/>
								<c:forEach var="sDto" items="${sList}">
								<tr>
									<td>${sDto.sprodId}</td>
									<td>${sDto.sprodName}</td>
									<td>${sDto.sprice}</td>
									<td>${sDto.squantity}</td>
								</tr>
								</c:forEach>
								<tr align="center">
									<td colspan="4">
										<a class="btn btn-primary" href="#" onClick="history.back()" role="button">
											<i class="glyphicon glyphicon-chevron-left"></i>&nbsp;&nbsp;뒤로</a>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="col-md-4"></div>
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