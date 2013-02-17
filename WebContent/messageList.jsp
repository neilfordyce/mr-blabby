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
        <title>Messages</title>
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
            /*
             * Ajax script to get more messages when within 200 pixels of the bottom
             */
            $(document).ready(function(){
                var startMessageIndex = 0;
                var requestInProgress = false;  //make sure many requests aren't made while waiting for page to resize

                $(window).scroll(function(){  //when scrolling

                    //Load more when within 200 pixels of the bottom
                    if  (($(document).height() - $(window).height()) - $(window).scrollTop() < 200
                            && requestInProgress != true){
                        
                        requestInProgress = true;
                        startMessageIndex += ${messageList.size};  //Get next batch of messages
                        
                        //Only make the request if there are more messages to get
                        if(${messageList.querySize} > startMessageIndex){
                            var dataRequestObject = {messageListIndex:startMessageIndex};
                            
                            $.ajax({
                                cache:false,
                                data:dataRequestObject,
                                error:function(xhr, ajaxOptions){
                                    alert(xhr.status + " :: " + xhr.statusText);
                                } 
                            }).done(function( html ) {
                                $("#messages").append(html);
                                requestInProgress = false;
                            });
                        }
                    }
                });
            });
        </script>
    </body>
</html>
