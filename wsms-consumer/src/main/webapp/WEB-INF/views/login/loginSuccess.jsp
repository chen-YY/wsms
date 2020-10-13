<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/4/26
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
    <h2>登录成功界面</h2>
    <h3>登录人员信息：</h3>
    <%
        String userInfo[] = (String[]) request.getAttribute("userInfo");
        for (String info : userInfo){
            out.print("<p>"+info+"</p>");
        }

    %>

</body>
</html>
