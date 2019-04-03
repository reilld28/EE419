<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import=".UserBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User logged Successfully</title>
</head>
<body>

<center>
<% UserBean currentUser = (UserBean (session.getAttribute("currentSessionUser")));%>
Welcome <%= currentUser.getname() %>
</center>

</body>
</html>