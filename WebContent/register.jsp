<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
	<body>
		<form action="register" method="post">
			<ul>
				<li>
				<label for="email">Email</label>
				<input type="email" name="email" placeholder="email address" required></li>
				
				<li>
				<label for="password">Password</label>
				<input type="password" name="password" placeholder="password " required></li>
				
				<li>
				<input type="submit" name="register" value="Register"></li>
			</ul>
		</form> 
	</body>
</html>