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
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <title>Search Results</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <div class="friends" id="friends">
               <h2>Users</h2> 
               <jsp:include page="friendFragment.jsp"/>
            </div>
            
            <div class="messages" id="messages">
                <h2>Messages</h2> 
                <jsp:include page="messageFragment.jsp"/>
            </div>
        </div>
    
            <jsp:include page="friendLoader.jsp"/>
            <jsp:include page="messageLoader.jsp"/>
    </body>
</html>
