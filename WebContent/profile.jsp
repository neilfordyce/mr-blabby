<%-- 
    Document   : profile
    Created on : 11-Feb-2013, 14:16:34
    Author     : Neil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <title>Profile</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <h1>Profile</h1>
            
            <form action="message" method="post">
                <textarea class="message" name="message" id="message" placeholder="w'azzzzzup..." maxlength="141" required></textarea>
                <br>
                <input type="submit" name="submit" value="Post"/>
            </form>
            
            <span id="counter"></span> 
        </div>
        
        <script>
            //Function from: http://stackoverflow.com/questions/2136647/character-countdown-like-on-twitter
            function updateCounter() {
                // 141 is the max message length
                var remaining = 141 - $(message).val().length;
                $(counter).text(remaining + " characters remaining");
            }

            $(document).ready(function() {
                updateCounter();
                $(message).change(updateCounter);
                $(message).keyup(updateCounter);
            });
        </script>
    </body>
</html>