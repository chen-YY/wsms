<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/4/26
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <title>登录</title>
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js" ></script>
    <script type="text/javascript" src="js/login/check.js"></script>
    <script type="text/javascript" src="js/login/testjQuery.js"></script>
    <script type="text/javascript" src="js/MyScript.js"></script>

    <link rel="stylesheet" type="text/css" href="css/login/toLogin.css">

    <!-- bootstrap4 -->
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>


</head>
<body id="body">

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <!-- Brand/logo -->
    <a class="navbar-brand" href="#">仓库管理与监控</a>
</nav>

    <div class="container" style="background-color: lightsteelblue" id="formContainer">

        <form action="login" method="post" name="myForm" onsubmit="return check()">
            <label for="account">账户</label>
            <input type="text" name="account" id="account" class="form-control" placeholder="请输入账户名"/>

            <label for="password">密码</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="请输入密码"/>

            <p>账户类型：</p>
            <label><input type="radio" name="accountType" value="leader" />决策用户</label>
            <label><input type="radio" name="accountType" value="storageadmin" />仓库管理</label>
            <label><input type="radio" name="accountType" value="storeadmin" />店主</label>
            <label><input type="radio" name="accountType" value="sorter" />分拣专员</label>

            <br/>
            <input type="submit" value="提交" class="btn btn-primary">
            <input type="reset" value="重新填写" class="btn btn-warning">

<%--        <br/>
            <input type="button" value="jQueryTest" id="test"/>
            <input type="button" onclick="helloWorld()" value="jsTest" />--%>
        </form>

    </div>

</body>
</html>
