<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Date</title>
</head>
<body>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss"); %>

<h1>Current Time: <%= df.format(new java.util.Date()) %> </h1>

</body>
</html>