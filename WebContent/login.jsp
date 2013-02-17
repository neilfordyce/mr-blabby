<%-- 
    Document   : login
    Created on : 11-Feb-2013, 13:23:57
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <h1>Login</h1>
            <div class="glass">
                <form action="login" method="post">
                    <label for="email">Email</label>
                    <input type="email" name="email" placeholder="email address" required/>
                    <br>
                    <label for="password">Password</label>
                    <input type="password" name="password" placeholder="password" required id="password"/>
                    <br>
                    <input type="submit" name="login" value="Login" id="login">
                </form>
            </div>
        </div>
    </body>
</html>
