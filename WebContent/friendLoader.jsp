<script>
    /*
     * Ajax script to get more messages when within 200 pixels of the bottom
     */
    $(document).ready(function(){
        var startUserIndex = 0;
        var requestInProgress = false;  //make sure many requests aren't made while waiting for page to resize

        $(window).scroll(function(){  //when scrolling

            //Load more when within 200 pixels of the bottom
            if  (($(document).height() - $(window).height()) - $(window).scrollTop() < 200
                && requestInProgress != true){
                        
                requestInProgress = true;
                startUserIndex += ${userList.size};  //Get next batch of users
                        
                //Only make the request if there are more messages to get
                if(${userList.querySize} > startUserIndex){
                            
                    $.ajax({
                        cache:false,
                        data:{userListIndex:startUserIndex},
                        error:function(xhr, ajaxOptions){
                            alert(xhr.status + " :: " + xhr.statusText);
                        } 
                    }).done(function( html ) {
                        $("#friends").append(html);
                        requestInProgress = false;
                    });
                }
            }
        });
    });    
</script>
