<%-- 
    Document   : profile
    Created on : 11-Feb-2013, 14:16:34
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <h1>Profile</h1>
            ${user.email}
            ${user.lastname}
            ${user.firstname}
            
            <form action="message" method="post">
                <input type="text" name="message" placeholder="w'azzzzzup..." required/>
                <input type="submit" name="submit" value="Post"/>
            </form>
        </div>
    </body>
</html>
