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
            <h1>Cheers <c:out value="${name}"/>,</h1>
            <br>
            <h2>Your account has been created.</h2>
            <br><a href="${pageContext.request.contextPath}/login">Go to login</a>
        </div>    
    </body>
</html>