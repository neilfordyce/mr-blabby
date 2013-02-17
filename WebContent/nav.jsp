<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean 
    id="user"
    scope="session" 
    class="uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User"/>

<ul class="nav">
    <li><a href="/MrBlabby/followers">Followers</a></li>
    <li><a href="/MrBlabby/following">Following</a></li>
    <li><a href="/MrBlabby/followers">Followers</a></li>
    <li><div class="divider-vertical"></div></li>

    <li><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/>
    <br><c:out value="${user.email}"/></li>
</ul>

