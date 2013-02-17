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
            //$(window).scroll(function(){
            $(window).scroll(function(){
            //$("button").click(function(){
            if  ( ($(document).height() - $(window).height()) - $(window).scrollTop() < 1000 ){
            if(${messageList.querySize} > requestCount){
            requestCount += ${messageList.size};
            var dataRequestObject={messageListIndex:requestCount};
            
            var dataRequestHeader={messageListIndex:requestCount};
            $.ajax({
                url:'http://localhost:8080/MrBlabby/message/',
                cache:false,
                header:dataRequestHeader,
                data:dataRequestObject,
                success:function(){ alert("Request Done" + requestCount);},
                error:function(xhr, ajaxOptions){
                    alert(xhr.status + " :: " + xhr.statusText);
                } 
            }).done(function( html ) {
                    $("#messages").append(html);
            });
            }
            }
                //if  ( ($(document).height() - $(window).height()) - $(window).scrollTop() < 1000 ){
            //do stuff
            
           /* $("#dav").load("/MrBlabby/message/", function(response, status, xhr) {
                        if (status == "error") {
                            var msg = "Sorry but there was an error: ";
                            $("#error").html(msg + xhr.status + " " + xhr.statusText);
                        }
                    });
                });*/

            });
            });
        </script>
    </body>
</html>
