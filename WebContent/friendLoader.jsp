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
                            
                    $.ajax({
                        cache:false,
                        data:{messageListIndex:startMessageIndex},
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
