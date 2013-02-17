<%@ page pageEncoding="UTF-8" %>

<%-- 
    Document   : profile
    Created on : 11-Feb-2013, 14:16:34
    Author     : Neil
--%>
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
            <div class="messages" id="messages">
                <h2>Messages</h2> 
                <jsp:include page="messageFragment.jsp"/>

            </div>
        </div>

        <script>
            $(document).ready(function(){
                var requestCount = 0;

                $(window).scroll(function(){  //when scrolling

                    //When near the bottom of the page
                    if  (($(document).height() - $(window).height()) - $(window).scrollTop() < 1000 ){
                        
                        requestCount += ${messageList.size};  //Get next batch of messages
                        
                        //Only make the request if there are more messages to get
                        if(${messageList.querySize} > requestCount){
                            var dataRequestObject = {messageListIndex:requestCount};
                            
                            $.ajax({
                                url:'http://localhost:8080/MrBlabby/message/',
                                cache:false,
                                data:dataRequestObject,
                                error:function(xhr, ajaxOptions){
                                    alert(xhr.status + " :: " + xhr.statusText);
                                } 
                            }).done(function( html ) {
                                $("#messages").append(html);
                            });
                        }
                    }
                });
            });
        </script>
    </body>
</html>
