<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="style.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
	<body>
		<jsp:include page="nav.jsp"/>
		<div class="container">
			<h1>Oh no!</h1>
			<br>
			<h2>It looks like the email address ${email} has already been registered</h2>
			<ul>
				<li><a href="register">Register</a></li>
				<li><a href="passwordReset">Forgotten your password</a></li>
			</ul>
		</div>
	</body>
</html>