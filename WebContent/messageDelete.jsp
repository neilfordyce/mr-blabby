<script type="text/javascript">
    /*
     * Ajax script to make a delete request
     */
    //$(document).ready(function (){

    function deleteMessage(deleteID)
    {
        $.ajax({
            url:"${pageContext.request.contextPath}/message/" + deleteID,
            type:"DELETE",
            cache:false
        }).done(function() {
            //when completed refresh so deletion can be seen
            document.location.reload(true);
        });
    }
    
</script>

