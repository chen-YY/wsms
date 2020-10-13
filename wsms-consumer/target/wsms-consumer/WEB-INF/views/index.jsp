<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta charset="UTF-8">

</head>
<body>
<h2>Hello World!</h2>
<h3>design by cyy</h3>
<%
    String message = (String) request.getAttribute("message");
    out.print("<h2>");
    out.print(message);
    out.print("</h2>");
    out.print("encoding test : 陈洋洋");

%>
</body>
</html>
