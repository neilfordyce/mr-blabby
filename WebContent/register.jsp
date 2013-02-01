<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
	<body>
	    <header></header>
	    <div class="container">
	    <h1>Register</h1>
	    <div class="register">
		<form action="register" method="post">
				<label for="firstname">First name</label>
				<input type="text" name="firstname" placeholder="firstname" value="${param.firstname}" required autofocus="autofocus">
				<br/>
				<label for="lastname">Last name</label>
				<input type="text" name="lastname" placeholder="lastname" value="${param.lastname}" required>
				<br>
				<label for="email">Email</label>
				<input type="email" name="email" placeholder="email address" value="${param.email}" required>
				<br>
				<label for="password">Password</label>
				<input type="password" name="password" placeholder="password" required>
				<br>
				<label for="confirmPassword">Confirm password</label>
				<input type="password" name="confirmPassword" placeholder="retype password" required>
				<br>
				<input type="submit" name="register" value="Register">
		</form>
		<jsp:include page="registerError.jsp"> 
		
  <jsp:param name="name" value="sos" />
</jsp:include>
		</div>
		</div>
	</body>
</html>