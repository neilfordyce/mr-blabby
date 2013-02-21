<%-- 
    Document   : searchResult.jsp
    Created on : 20-Feb-2013, 17:18:42
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <jsp:include page="style.jsp"/>
        <title>Search Results</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <h2>Search - <c:out value="${searchTerm}"></c:out></h2>
            <br>
            
            <div class="right-module messages" id="messages">
                <h2>Messages</h2> 
                <jsp:include page="messageFragment.jsp"/>
            </div>

            <div class="left-module messages" id="friends">
                <h2>Users</h2> 
                <jsp:include page="friendFragment.jsp"/>
            </div>

        </div>

        <jsp:include page="friendLoader.jsp"/>
        <jsp:include page="messageLoader.jsp"/>
    </body>
</html>
