<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Ezen Fulfillment System</title>
</head>
<body>
	<center><br>
	<h3>사용자 로그인</h3><br>
	<hr>
	<form name="loginForm" action="comServlet?action=login" method=post>
		<label><span>ID:</span>
			<input type="text" name="uid" size="10"></label>
		<label><span>Password:</span>
			<input type="password" name="upass" size="10"></label><br>
		<input type="submit" value="로그인" name="L1">&nbsp;&nbsp;
		<input type="reset" value="취소" name="L2">
	</form>
		<br><br><button onclick="location.href='comServlet?action=prepareForm'">회원 가입</button>
</body>
</html>