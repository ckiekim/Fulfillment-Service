<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, company.*" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Ezen Fulfillment System</title>
</head>
<body>
	<h3>사용자 등록</h3>
	<hr>
	<form name="registerForm" action="../company/comServlet?action=register" method=post>
		아이디:<input type="text" name="uid" size="10"><br>
		패스워드:<input type="password" name="upass" size="10"><br>
		이름:<input type="text" name="uname" size="10"><br>
		회사:
			<select name="ucomId">
			<c:set var="cList" value="${requestScope.companyList}"/>
			<c:forEach var="company" items="${cList}">
				<option value=${company.cid}>${company.cname}</option>
			</c:forEach>
			</select><br>
		<input type="submit" value="회원 가입" name="R1">&nbsp;&nbsp;
			<input type="reset" value="재작성" name="R2">
	</form>
</body>
</html>