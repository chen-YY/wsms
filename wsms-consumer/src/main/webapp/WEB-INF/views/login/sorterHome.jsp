<%@ page import="entity.DeliveryOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Goods" %>
<%@ page import="java.util.Map" %>
<%@ page import="entity.StoreAdmin" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/5/5
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>分拣</title>

    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="css/home-page/homepage-title.css" type="text/css">
    <link rel="stylesheet" href="css/home-page/sorterHomepage.css" type="text/css">
</head>
<body>

<%
    /*
    获取用户信息
     */

    String userInfo[] = (String[]) request.getAttribute("userInfo");

    List<DeliveryOrder> myDeliveryOrder = (List<DeliveryOrder>) request.getAttribute("myDeliveryOrder");
    List<DeliveryOrder> myHistoryDeliveryOrder = (List<DeliveryOrder>) request.getAttribute("myHistoryDeliveryOrder");

    Map<String,List<Goods>> deliveryOrderGoods = (Map<String,List<Goods>>) request.getAttribute("deliveryOrderGoods");

    Map<String, StoreAdmin> storeAdminInfo = (Map<String, StoreAdmin>) request.getAttribute("storeAdminInfo");

    System.out.print(myDeliveryOrder);



%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <!-- Brand/logo -->
    <a class="navbar-brand" href="#">仓库管理与监控/<span id="userType">分拣</span></a>

    <!-- Links -->

    <div id="userInfo">您好，<%=userInfo[2] %></div>
    <div><form action="logout"><input type="submit" value="退出登录" id="logoutButton" class="btn btn-danger"> </form></div>

    <div></div>
</nav>

<div id="contain1">
    <div class="alert alert-primary" style="text-align: center">
            <strong>新的分拣订单</strong>
    </div>

    <div id="accordion">

        <%
            for (DeliveryOrder o : myDeliveryOrder){
                int flag = 1;
                StoreAdmin storeAdmin = storeAdminInfo.get(String.valueOf(o.getReceiveUser()));
                List<Goods> goodsList = deliveryOrderGoods.get(String.valueOf(o.getId()));
        %>

        <div class="card">
            <div class="card-header">
                <a class="card-link" data-toggle="collapse" href="#deliveryOrder<%=flag%>">
                    <p>订单号：<%=o.getId() %></p>
                </a>
            </div>
            <div id="deliveryOrder<%=flag%>" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <p>接收部门：销售终端</p>
                    <p>接收人：<%=storeAdmin.getName() %></p>
                    <p>店面：<%=storeAdmin.getStoreName() %></p>
                    <p>货物信息：</p>
                    <%
                        for (Goods g : goodsList){
                    %>
                    <p>商品名称:<%=g.getName()%></p>
                    <p>商品数量:<%=g.getNumber()%></p>
                    <p>商品批号:<%=g.getBatchNumber()%></p>
                    <p>货架号:<%=g.getShelfNumber()%></p>
                    <hr>

                    <form action="chuhuo" method="post">
                        <input type="hidden" name="deliveryOrderId" value="<%=o.getId() %>">
                        <input type="hidden" name="goodId" value="<%=g.getId() %>">
                        <input type="submit" value="分拣完成" class="btn btn-outline-success" >
                    </form>




                    <%
                        }
                    %>



                </div>
            </div>
        </div>

        <%
                flag += 1;
            }
        %>
    </div>




</div>



<div id="contain2">

    <div class="alert alert-info" style="text-align: center">
        <strong>历史分拣订单信息</strong>
    </div>

    <div id="accordion2">
        <%
            for (DeliveryOrder de : myHistoryDeliveryOrder){
                StoreAdmin storeAdmin2 = storeAdminInfo.get(String.valueOf(de.getReceiveUser()));
        %>

        <div class="card">
            <div class="card-header">
                <a class="card-link" data-toggle="collapse" href="#deliveryOrder<%=de.getId()%>">
                    <p>订单编号：<%=de.getId()%></p>
                </a>
            </div>
            <div id="deliveryOrder<%=de.getId()%>" class="collapse" data-parent="#accordion2">
                <div class="card-body">
                    <p>接收部门：销售终端</p>
                    <p>接收人:<%=storeAdmin2.getName() %></p>
                    <p>店铺：<%=storeAdmin2.getStoreName() %></p>
                </div>
            </div>
        </div>

        <%
            }
        %>





    </div>







</div>

<div id="contain3">


</div>






</body>
</html>
