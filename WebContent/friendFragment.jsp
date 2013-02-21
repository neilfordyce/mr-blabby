<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>                
<c:forEach items="${userList.users}" var="friend">
    <div class="divider-horizontal"></div>
    <div class="message-block">
        <div class="time"><a href="/MrBlabby/profile/<c:out value="${friend.email}"></c:out>">${friend.email}</a></div>
        <div class="sender">${friend.firstname} </div>
        <div class="message">${friend.lastname} </div>
    </div>
</c:forEach>
