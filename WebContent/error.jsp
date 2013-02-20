<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <div class="container">
            <h1>Oh no!</h1>
            <br>
            <h2>Sorry <c:out value="${name}"/>, the database wouldn't let me add your details.</h2>
            <ul>
                <li><a href="register">Register</a></li>
                <li><a href="passwordReset">Forgotten your password</a></li>
            </ul>
        </div>
    </body>
</html>