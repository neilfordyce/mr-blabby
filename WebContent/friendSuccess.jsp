<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <h1>Success</h1>
            <br>
            <h2>You're now friends with <c:out value="${email}"/>.</h2>
        </div>
    </body>
</html>