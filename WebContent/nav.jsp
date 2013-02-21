<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<div class="nav">

    <ul class="nav">
        <li><a href="/MrBlabby/followers">Followers</a></li>
        <li><a href="/MrBlabby/following">Following</a></li>

        <li>
            <form class="search" action="search" method="post">
                <input class="search" name="search" type="search" placeholder="search...">
                <input class="search" type="submit" value="">
            </form>
        </li>

        <div class="divider-vertical"></div>

        <li id="username"><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/>
            <br><c:out value="${user.email}"/></li>

        <div class="divider-vertical"></div>

        <li><a id="button" class="settings-button">Settings</a></li>

        <li><div class="menu" id="slider"><a href="/MrBlabby/logout">Logout</a>
                <br><a href="${pageContext.request.contextPath}/update/profile">Update Details</a>
                <br><a href="/MrBlabby/message/<c:out value="${user.email}"></c:out>">${user.email}</a></div>
        </li>
        
    </ul>
</div>


<script>
    $(document).ready(function () {
        $("#slider").hide();
    });
    $("#button").click(function () {
        $("#slider").slideToggle("fast");
    });
</script>

