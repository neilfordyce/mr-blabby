<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<div class="nav">

    <ul class="nav">
        <li class="logo">Banter</li>
        <div class="container">
            <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/message">Messages</a></li>

            <li>
                <form class="search" action="${pageContext.request.contextPath}/search" method="post">
                    <input class="search" name="search" type="search" placeholder="search...">
                    <input class="search" type="submit" value="">
                </form>
            </li>

            <div class="divider-vertical"></div>

            <li id="username"><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/>
                <br><c:out value="${user.email}"/></li>

            <div class="divider-vertical"></div>

            <li><a id="button" class="settings-button">Settings</a></li>


            <li><div class="menu" id="slider">
                    <span></span>
                    <a href="/MrBlabby/logout">Logout</a>
                    <br><a href="${pageContext.request.contextPath}/update/profile">Update Details</a>
                    <br><a href="/MrBlabby/message/<c:out value="${user.email}"></c:out>">${user.email}</a>
                </div>
            </li>
        </div>

    </ul>


</div>


<script>
    $(document).ready(function () {
        $("#slider").hide();
    });
    $("#button").click(function () {
        $("#slider").fadeToggle("fast");
    });
</script>

