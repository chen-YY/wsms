<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/5/5
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" import="java.util.Map" %>
<%@ page language="java" %>
<%@ page language="java" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="entity.*" %>

<html>
<head>
    <title>决策用户</title>

    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="css/home-page/homepage-title.css" type="text/css">
    <link rel="stylesheet" href="css/home-page/body.css" type="text/css">

    <script type="text/javascript" src="js/home-page/leader-home-addTypeAndGoods-formcheck.js"></script>

</head>
<body id="body">

<%
    String userInfo[] = (String[]) request.getAttribute("userInfo");


%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <!-- Brand/logo -->
    <a class="navbar-brand" href="#">仓库管理与监控/<span id="userType">决策</span></a>

    <!-- Links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="#contain1">商品列表</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#contain2">仓库信息</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#contain3">订单审批</a>
        </li>
    </ul>

    <div id="userInfo">您好，<%=request.getSession().getAttribute("name") %></div>
    <div><form action="logout"><input type="submit" value="退出登录" id="logoutButton" class="btn btn-danger"> </form></div>
</nav>

    <div id="contain1" class="row" name="goods">
        <div id="goodsList" class="col-sm-8">

            <div class="alert alert-info" style="text-align: center">
                商品种类分布表
            </div>

        <%

            List<Kind> kingList = (List<Kind>) request.getAttribute("kindList");
            Map<String,List<GoodName>> goodName = new HashMap<>();
            goodName = (Map<String, List<GoodName>>) request.getAttribute("everyTypeOfGoodName");

        %>


            <table class="table table-hover goodsInfo" >

                <thead>
                <tr>
                    <th>商品种类</th>
                    <th>商品名称</th>
                </tr>
                </thead>

                <%
                    for( Kind k : kingList){
                        List<GoodName> theGoodName = goodName.get(k.getKind());
                        for (GoodName g : theGoodName){
                            out.print("<tr>");

                            out.print("<th>");
                            out.print(k.getKind());
                            out.print("</th>");

                            out.print("<th>");
                            out.print(g.getGoodName());
                            out.print("</th>");

                            out.print("<tr>");
                        }

                    }
                %>
            </table>
        </div>
        <div class="col-sm-4" style="background-color: aquamarine">
            <!-- 商品列表旁边的表单 -->
            <div>
                <div style="margin-top: 20px;margin-left: 10px;">
                    <h2>新增种类和商品</h2>
                </div>
                <div style="margin-left: 10px;margin-bottom: 20px;">
                    <h7>可以添加现有种类下的新商品，或者新的种类以及商品</h7>
                </div>
                <form action="addTypeandGoods" method="post">
                    <div class="form-group">
                        <label for="goodsType">种类</label>
                        <input type="text" class="form-control" name="goodsType" id="goodsType" required>
                    </div>
                    <div class="form-group">
                        <label for="goodsName">品名</label>
                        <input type="text" class="form-control" name="goodsName" id="goodsName" required>
                    </div>

                    <div style="margin-top: 30px;margin-left: 90px;">
                        <input type="submit" value="提交" class="btn btn-success" />
                        <input type="reset" value="重新填写" class="btn btn-warning" />
                    </div>

                </form>

            </div>


        </div>

    </div>

    <div id="contain2" class="row" name="storage">
        <div class="col-sm-4" id="addStorageInfo" >
            <!-- 用于添加新的仓库信息的表单 -->
            <div style="margin-top: 20px;margin-left: 10px;">
                <h2>新添加一个仓库节点</h2>
            </div>
            <div style="margin-left: 10px;margin-bottom: 20px;">
                <h7>可以添加仓库的名称和仓库的详细地址信息</h7>
            </div>

            <form action="addStorage" method="post">
                <div class="form-group">
                    <label for="storageName">仓库名称</label>
                    <input type="text" name="storageName" id="storageName" class="form-control" required/>
                </div>

                <div class="form-group">
                    <label for="location">地址</label>
                    <input type="text" name="location" id="location" class="form-control" required />
                </div>

                <div style="margin-top: 30px;margin-left: 90px;">
                    <input type="submit" value="提交" class="btn btn-success" />
                    <input type="reset" value="重新填写" class="btn btn-warning" />
                </div>

            </form>

        </div>
        <div class="col-sm-8" id="storageInfo">
            <div class="alert alert-primary" style="text-align: center">
                  仓库信息表
            </div>

            <%
                List<Storage> storageList = (List<Storage>) request.getAttribute("storageList");
            %>

            <table class="table table-hover goodsInfo" >

                <thead>
                <tr>
                    <th>仓库名</th>
                    <th>地址</th>
                </tr>
                </thead>

                <%
                    for (Storage s : storageList){
                        out.print("<tr>");

                        out.print("<th>");
                        out.print(s.getName());
                        out.print("</th>");

                        out.print("<th>");
                        out.print(s.getLocation());
                        out.print("</th>");

                        out.print("</tr>");

                    }
                %>
            </table>
        </div>
    </div>

    <div id="contain3">
        <!-- 订单审批 -->

        <div class="alert alert-info" style="text-align: center">
            <strong>订单审批</strong>
        </div>

        <div id="accordion"><!-- 作为卡片的父元素，在一个父元素下，只有一个卡片显示 -->

        <%
            //获取未审批的订单列表
            List<Order> noApproveOrderList = (List<Order>) request.getAttribute("noApproveOrderList");

            //获取列表中的发起人，处理人 等信息
            Map<String,Map<String,String>> FandCInfo = (Map<String,Map<String,String>>) request.getAttribute("FandCInfo");

            //获取列表订单的商品信息
            Map<String,List<Goods>> goodsList = (Map<String,List<Goods>>) request.getAttribute("goodsList");


            if(noApproveOrderList.size() == 0){
                //没有记录


        %>

            <div>目前暂无待审批订单</div>


            <%
            }else{
                for (Order o : noApproveOrderList){
                    int flag = 1;
        %>

        <!-- 循环体 -->

            <div class="card">
                <div class="card-header">
                    <a class="card-link" data-toggle="collapse" href="#order<%=flag%>">
                        订单编号：<%=o.getId() %>
                    </a>
                </div>
                <div id="order<%=flag%>" class="collapse" data-parent="#accordion">
                    <div class="card-body">
                        <!-- 订单的详细信息 -->
                        <div>订单编号：<%=o.getId() %></div>
                        <div>订单类型：<%
                        if(o.getType().equals("A")){
                            out.print("采购订单");
                        }else if(o.getType().equals("B")){
                            out.print("回退订单");
                        }else{
                            out.print("调拨订单");
                        }


                        %></div>
                        <div>发起部门：<% if(o.getSponsorDep().equals("A")){
                            out.print("仓库管理部门");
                        }else if(o.getSponsorDep().equals("B")){
                            out.print("销售终端");
                        }
                        %></div>
                        <div>发起人：<%=FandCInfo.get(Integer.toString(o.getId())).get("sponsorUser") %></div>
                        <div>处理部门：<% if(o.getDisposeDep().equals("A")){
                            out.print("仓库管理部门");
                        }else{
                            out.print("采购部门");
                        }
                        %></div>
                        <div>处理人：<%=FandCInfo.get(Integer.toString(o.getId())).get("disposeUser") %></div>
                        <div>申请时间：<%=o.getStarTime()%></div>
                        <div>当前状态：待审批</div>
                        
                        <div>
                            <hr/>
                            订单商品：
                            <!-- 订单中的商品信息 -->
                            <%

                                if(!(o.getGoods() == "" || o.getGoods() == null)){
                                    out.print("<p>");
                                    out.print(o.getGoods());
                                    out.print(o.getNumber());

                                    out.print("</p>");
                                }



                                List<Goods> goods1 = goodsList.get(String.valueOf(o.getId()));

                                for (Goods g : goods1){
                                    %>

                                    <p>批号：<%=g.getBatchNumber()%></p>
                                    <p>类型：<%=g.getKind()%></p>
                                    <p>品名：<%=g.getName()%></p>
                                    <p>数量：<%=g.getNumber()%></p>
                            <hr/>

                                    <%
                                }
                            %>
                        </div>

                        <div>
                            <!-- 修改订单状态表单 -->
                            <form action="approve" method="post">
                                <input type="hidden" name="id" value="<%=o.getId() %>">

                                <label><input type="radio" name="result" value="yes" required/>通过</label>
                                <label><input type="radio" name="result" value="no" required/>否决</label>
                                <br/>
                                <label>备注意见</label><input type="text" name="remark" placeholder="备注" >

                                <input type="submit" value="确定">

                            </form>

                        </div>

                    </div>
                </div>
            </div>

        <%
                    flag ++;
                }
            }
        %>
        </div>

    </div>


<%--    <div id="contain4">--%>

<%--        <div class="alert alert-info" style="text-align: center">--%>
<%--            <strong>流转报表</strong>--%>
<%--        </div>--%>


<%--    </div>--%>

</body>
</html>
