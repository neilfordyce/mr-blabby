<script type="text/javascript">
    /*
     * Ajax script to make a delete request
     */
    function deleteFriend(deleteID)
    {
        $.ajax({
            url:"${pageContext.request.contextPath}/friend/" + deleteID,
            type:"DELETE",
            cache:false
        }).done(function() {
            //when completed refresh so deletion can be seen
            location.reload();
        });
    }
    
</script>

