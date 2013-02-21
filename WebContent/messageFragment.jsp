<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>                
<c:forEach items="${messageList.message}" var="element">
    <div class="divider-horizontal"></div>
    <div class="message-block">
        <div class="time">${element.formattedTime} </div>
        <div class="sender"><a href="${pageContext.request.contextPath}/message/<c:out value="${element.sender}"></c:out>">${element.sender}</a></div>
        <c:if test="${user.email == element.sender}">
        <div class="delete"><a href="${pageContext.request.contextPath}/message/${element.id}/delete">Delete</a></div>
        </c:if>
        <div class="message">${element.message} </div>
    </div>
</c:forEach>