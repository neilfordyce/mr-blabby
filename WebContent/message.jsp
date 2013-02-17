<%-- 
    Document   : message
    Created on : 14-Feb-2013, 19:58:11
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean 
    id="message"
    scope="page" 
    class="uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.Message"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><jsp:getProperty name="message" property="sender"/></h1>
        <h4><jsp:getProperty name="message" property="formattedCreatedDuration"/></h4>
        <h4><jsp:getProperty name="message" property="message"/></h4>
    </body>
</html>
