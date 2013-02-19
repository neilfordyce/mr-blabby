<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>                
<c:forEach items="${messageList.message}" var="element">
    <div class="divider-horizontal"></div>
    <div class="message-block">
        <div class="time">${element.formattedTime} </div>
        <div class="sender"><a href="/MrBlabby/message/<c:out value="${element.sender}"></c:out>">${element.sender}</a></div>
        <div class="message">${element.message} </div>
    </div>
</c:forEach>