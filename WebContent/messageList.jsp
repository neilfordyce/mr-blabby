<%@ page pageEncoding="UTF-8" %>

<%-- 
    Document   : profile
    Created on : 11-Feb-2013, 14:16:34
    Author     : Neil
--%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <title>Messages</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <div class="messages" id="messages">
                <h2>Messages</h2> 
                <jsp:include page="messageFragment.jsp"/>
            </div>
        </div>
        
    <jsp:include page="messageLoader.jsp"/>
    <jsp:include page="messageDelete.jsp"/>
    
    </body>
</html>
