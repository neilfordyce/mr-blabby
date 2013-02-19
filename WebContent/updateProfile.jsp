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
            <div class="glass">
                <form action="update/profile" method="post">
                    <label for="firstname">First name</label>
                    <input type="text" name="firstname" placeholder="firstname" value="<c:out value="${param.firstname}"/>" autofocus="autofocus"/>
                    <br>
                    <label for="lastname">Last name</label>
                    <input type="text" name="lastname" placeholder="lastname" value="<c:out value="${param.lastname}"/>" />
                    <br>
                    <label for="email">Email</label>
                    <input type="email" name="email" placeholder="email address" value="<c:out value="${param.email}"/>" />
                    <br>
                    <label for="password">Password</label>
                    <input type="password" name="password" placeholder="password" required id="password" onkeyup="checkPass();"/>
                    <br>
                    <label for="confirmPassword">Confirm password</label>
                    <input type="password" name="confirmPassword" placeholder="retype password" required id="confirmPassword" onkeyup="checkPass();"/>
                    <br>
                    <input type="submit" name="register" value="Register" id="register"/>
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