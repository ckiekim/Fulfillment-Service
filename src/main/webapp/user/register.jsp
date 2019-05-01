<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.*" %> 
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
	<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.12.1/dist/css/bootstrap-select.min.css"> -->
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-10">
				<div class="jumbotron">
					<p><h2>사용자 등록</h2></p>
					<p><h5>Ezen Fulfillment System을 이용하려면 사용자 등록을 해주세요.</h5></p>
				</div><br><br>
				<form action="userServlet?action=register" class="form-horizontal" method="POST" onSubmit="return isValidLogin();">
					<div class="form-group">
						<label class="col-md-4 control-label">아이디</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="uid" id="uid">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">패스워드</label>
						<div class="col-md-3">
							<input type="password" class="form-control" name="upass" id="upass">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">이름</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="uname" id="uname">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">회사</label>
						<div class="col-md-3">
							<!-- <select class=”selectpicker“ name="ucomId"> -->
							<select class="form-control" name="ucomId">
								<c:set var="cList" value="${requestScope.companyList}"/>
								<c:forEach var="company" items="${cList}">
									<option value=${company.cid}>${company.cname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-4 col-md-6">
							<input class="btn btn-primary" type="submit" value="확인">&nbsp;&nbsp;
							<button class="btn btn-default" type="reset" type="button">취소</button>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-1"></div>
		</div>
		</div>
	</div>

	<!-- ==================================================================== -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.12.1/dist/js/bootstrap-select.min.js"></script> -->
	<script src="../js/bootstrap.min.js"></script>
</body>
</html>