<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="style.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update</title>
    </head>
    <body>
        <jsp:include page="nav.jsp"/>
        <div class="container">
            <h1>Update Details</h1>
            
            <h4>Only enter details into fields you want to update.</h4>
            
            <div class="glass">
                <form method="post">
                    <label for="firstname">First name</label>
                    <input type="text" name="firstname" placeholder="firstname" autofocus="autofocus" value=""/>
                    
                    <label for="lastname">Last name</label>
                    <input type="text" name="lastname" placeholder="lastname" value=""/>
                    
                    <label for="password">Password</label>
                    <input type="password" name="password" placeholder="password" id="password" onkeyup="checkPass();" value=""/>
                    
                    <label for="confirmPassword">Confirm password</label>
                    <input type="password" name="confirmPassword" placeholder="retype password" id="confirmPassword" onkeyup="checkPass();" value=""/>
                    <br>
                    <input type="submit" name="update" value="Update" id="update"/>
                </form>
            </div>
        </div>

        <script type="text/javascript">
            /*
             * Checks password and confirm password match
             * If they don't a custom HTML5 validation box appears on submission
             */
            function checkPass(){
                var password = document.getElementById("password").value;
                var confirmPassword = document.getElementById("confirmPassword").value;
			
                if( password != confirmPassword && confirmPassword != ""){
                    document.getElementById("confirmPassword").setCustomValidity("Passwords do not match");
                }
                else{
                    document.getElementById("confirmPassword").setCustomValidity("");
                }
		    
            }
        </script>         
    </body>
</html>