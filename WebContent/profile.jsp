<%-- 
    Document   : profile
    Created on : 11-Feb-2013, 14:16:34
    Author     : Neil
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
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
            <h2><c:out value="${profile.firstname} ${profile.lastname}"></c:out></h2>

            <div class="profile-messages messages" id="messages">
                <h2>Messages</h2>
                <jsp:include page="/messageFragment.jsp"/>
            </div>
            

            <form action="message" method="post" class="messages profile-friends">
                <h2>Create Post</h2>
                <textarea class="message" id="message" name="message" placeholder="w'azzzzzup..." maxlength="141" required rows="4" cols="43"></textarea>
                <span id="counter"></span>
                <br>
                <input type="submit" name="submit" value="Post"/>
            </form>
             
            
            <br>
            <c:if test="${friendable}">
                <a href="<c:url value="/friend/${profile.email}"></c:url>">Add ${profile.firstname} as a friend</a>      
            </c:if>
            <br>

            <div class="profile-friends messages" id="friends">
                <h2>Friends</h2>
                <jsp:include page="/friendFragment.jsp"/>
            </div>
            

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
        
        <jsp:include page="messageLoader.jsp"/>
    </body>
</html>

