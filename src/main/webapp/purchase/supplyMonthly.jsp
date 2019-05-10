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
	<link href="../css/jquery-ui.min.css" rel="stylesheet">
</head>
<body>
	<%@ include file="../common/_top.jspf" %>
	
	<div class="container-fluid">
		<div class="row" style="margin-top: 100px">
			<div class="col-md-2">
				<div class="list-group">
					<a href="purchaseServlet?action=list" class="list-group-item">공급요청 목록</a>
					<a href="purchaseServlet?action=purchaseList" class="list-group-item">일별 공급내역</a>
					<a href="#" class="list-group-item active">월별 공급내역</a>
					<a href="#" class="list-group-item">정산</a>
				</div>
			</div>
			<div class="col-md-10">
				<div class="row" style="margin-left: 30px">
					<div class="col-md-7"><h3>월별 공급내역 : ${requestScope.purchaseMonth}</h3></div>
					<div class="col-md-5"><br>
						<form action="purchaseServlet?action=purchaseMonthly&page=1" class="form-horizontal" method="post">
							<div class="form-group">
								<label class="control-label">년월:&nbsp;&nbsp;</label>
								<input type="text" name="month" id="monthpicker">&nbsp;&nbsp;
								<input class="btn btn-primary btn-sm" type="submit" value="검색">
							</div>
						</form>
						</form>
					</div>
					<div class="col-md-12"><hr></div>
					<div class="col-md-10">
						<div class="panel panel-primary">
							<table class="table table-striped table-condensed">
								<tr class="active">
									<th class="col-md-1">ID</th>
									<th class="col-md-1">상품ID</th>
									<th class="col-md-2">상품명</th>
									<th class="col-md-1">단가</th>
									<th class="col-md-1">발주수량</th>
									<th class="col-md-2">입고일시</th>
									<th class="col-md-1">상태</th>
									<th class="col-md-1">재고수량</th>
								</tr>
								<c:set var="rList" value="${requestScope.purchaseSuppliedList}"/>
								<c:forEach var="purchase" items="${rList}">
								<tr>
									<td><a href=#>${purchase.rid}</a></td>
									<td>${purchase.rprodId}</td>
									<td>${purchase.rprodName}</td>
									<td>${purchase.rprice}</td>
									<td>${purchase.rquantity}</td>
									<td>${purchase.rdate}</td>
									<td>${purchase.rstatusName}</td>
									<td>${purchase.rpstock}</td>
								</tr>
								</c:forEach>
	 								<tr align="center"><td colspan="8">
									<c:set var="pList" value="${requestScope.pageList}"/>
									<nav>
									  <ul class="pagination">
									    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
											<c:forEach var="page" items="${pList}">
												<c:choose>
													<c:when test="${requestScope.currentPage == page}">
														<li class="active"><a href="#">${page}<span class="sr-only">(current)</span></a></li>
													</c:when>
													<c:otherwise>
														<li><a href="adminServlet?action=purchaseMonthly&page=${page}">${page}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
									    <li class="disabled"><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
									  </ul>
									</nav>
								</td></tr>
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
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/jquery.mtz.monthpicker.js"></script>
	<script>
	    /* MonthPicker 옵션 */
	    var currentYear = (new Date()).getFullYear();
	    var startYear = currentYear-5;
	    var options = {
	            startYear: startYear,
	            finalYear: currentYear,
	            pattern: 'yyyy-mm',
	            monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	    };
		/* MonthPicker Set */
		$('#monthpicker').monthpicker(options);
		/* 버튼 클릭시 MonthPicker Show */
		$('#btn_monthpicker').bind('click', function () {
			$('#monthpicker').monthpicker('show');
		});
	</script>
</body>
</html>