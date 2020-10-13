<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="entity.*" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/5/5
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>店铺管理</title>

    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/home-page/homepage-title.css" type="text/css">

    <link rel="stylesheet" href="css/home-page/storeAdminHomepage.css" type="text/css">

</head>
<body>

<%
    String userInfo[] = (String[]) request.getAttribute("userInfo");

    List<Kind> kindList = (List<Kind>) request.getAttribute("kindList");
    Map<String,List<GoodName>> everyTypeOfGoodName = (Map<String,List<GoodName>>) request.getAttribute("everyTypeOfGoodName");
    List<GoodName> goodNamesList = (List<GoodName>) request.getAttribute("goodNamesList");
    Map<String,Integer> goodNumber = (Map<String,Integer>) request.getAttribute("goodNumber");
    List<StorageAdmin> storageAdminList = (List<StorageAdmin>) request.getAttribute("storageAdminList");

    List<DeliveryOrder> deliveryOrderList = (List<DeliveryOrder>) request.getAttribute("deliveryOrderList");
    Map<String,List<Goods>> goodInReceiveOrder = (Map<String,List<Goods>>) request.getAttribute("goodInReceiveOrder");


%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <!-- Brand/logo -->
    <a class="navbar-brand" href="#">仓库管理与监控/<span id="userType">分销终端</span></a>

    <!-- Links -->
    <ul class="nav nav-pills" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" data-toggle="pill" href="#contain1">申请订单</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="pill" href="#contain2">我的待收货</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="pill" href="#contain3">未设置</a>
        </li>
    </ul>

    <div id="userInfo">您好，<%=userInfo[2] %></div>
    <div><form action="logout"><input type="submit" value="退出登录" id="logoutButton" class="btn btn-danger"> </form></div>

</nav>

<div class="tab-content">

    <div id="contain1" class="container tab-pane active">
        <div>
            <!-- 这里是仓库中所有商品的数量信息 -->
            <div class="alert alert-info" style="text-align: center">
                <strong>库存商品及信息</strong>
            </div>

            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>商品</th>
                    <th>贮存数量</th>
                </tr>
                </thead>
                <tbody>

                <%
                    for (GoodName g : goodNamesList){
                %>

                <tr>
                    <td><%=g.getGoodName()%></td>
                    <td><%

                        Integer i = goodNumber.get(g.getGoodName());
                        if(i == null){
                            out.print("无商品");
                        }else {
                            out.print(i);
                            out.print("/"+ ((g.getUnit() == null || g.getUnit() == "") ? "个" : g.getUnit()));
                        }
                    %></td>

                </tr>
                <%
                    }
                %>

                </tbody>
            </table>
        </div>

        <div>
            <!-- 这里是表单信息 -->
            <form action="applayOrder" method="post">
                <div class="form-group">
                    <label for="type">订单类型：</label>
                    <select name="type" id="type">
                        <option value="B">回退订单</option>
                        <option value="C">调拨订单</option>
                    </select>
                </div>
                <input type="hidden" name="sponsorDep" value="B">
                <input type="hidden" name="sponsorUser" value="<%=userInfo[0]%>">
                <input type="hidden" name="disposeDep" value="A">


                <div class="form-group">
                    <label>订单处理人</label>
                    <select name="disposeUser">
                    <%
                        for (StorageAdmin s : storageAdminList){

                    %>
                        <option value="<%=s.getId()%>"><%=s.getName()%></option>
                    <%
                        }
                    %>
                    </select>
                </div>

                <div class="form-group">
                    <label>选择商品</label>
                    <select name="goods">
                        <%
                            for (GoodName g2 : goodNamesList){
                                Integer j = goodNumber.get(g2.getGoodName());
                                if(j != null){
                        %>

                        <option value="<%=g2.getGoodName()%>"><%=g2.getGoodName()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="form-group">
                    <input type="text" name="number" class="form-control" placeholder="商品数量" style="width: 200px;">
                </div>

                <input type="submit" value="提交订单" class="btn btn-primary">

            </form>
        </div>

    </div>

    <div id="contain2" class="container tab-pane fade">
        <!-- 这里是带收货的订单 -->

        <div class="alert alert-dark" style="text-align: center">
                <strong>待收货</strong>
        </div>

        <div id="accordion">

            <%
                for (DeliveryOrder d : deliveryOrderList){
                    List<Goods> goodsList = goodInReceiveOrder.get(String.valueOf(d.getId()));
            %>

            <div class="card">
                <div class="card-header">
                    <a class="card-link" data-toggle="collapse" href="#needReceive<%=d.getId() %>">
                        <p>订单号<%=d.getId() %></p>
                    </a>
                </div>
                <div id="needReceive<%=d.getId() %>" class="collapse" data-parent="#accordion">
                    <div class="card-body">
                        <p>订单识别码：<%=d.getUuid() %></p>
                        <p>订单商品:</p>
                        <%
                            for (Goods good : goodsList){
                        %>
                        <p>品名：<%=good.getName() %></p>
                        <p>品名：<%=good.getNumber() %></p>

                        <%
                            }
                        %>

                        <form action="storeAdminReceive" method="post">
                            <input type="hidden" name="deliveryOrderId" value="<%=d.getId() %>">
                            <input type="submit" value="收货" class="btn btn-outline-info">

                        </form>


                    </div>
                </div>
            </div>

            <%

                }
            %>
        </div>

    </div>

</div>





</body>
</html>
