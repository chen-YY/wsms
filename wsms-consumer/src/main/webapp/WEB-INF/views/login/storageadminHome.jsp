<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="Utils.DateUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="entity.*" %><%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/5/5
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>仓库管理</title>

        <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
        <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/home-page/homepage-title.css" type="text/css">
        <link rel="stylesheet" href="css/home-page/storageAdminHomepage.css" type="text/css">

        <script type="text/javascript" src="js/home-page/storageAdminhomeReturnTest.js" ></script>

        <script type="text/javascript">
            function check() {
                var cyy;
                cyy = '${cyy}';
                alert(cyy);


            }

        </script>

    </head>
    <body id="body">


    <%
        String[] userInfo = (String[]) request.getAttribute("userInfo");

        Storage storage = (Storage) request.getAttribute("storage");
        List<Shelf> shelfList = (List<Shelf>) request.getAttribute("shelfList");
        Map<String,List<Goods>> shelfGoods = (Map<String,List<Goods>>) request.getAttribute("shelfGoods");
        List<GoodMax> goodMaxList = (List<GoodMax>) request.getAttribute("goodMaxList");
        Map<String,Integer> everyGoodsNumber = (Map<String,Integer>) request.getAttribute("everyGoodsNumber");
        List<GoodName> goodNameList = (List<GoodName>) request.getAttribute("goodNamesList");

        List<Order> myBuyOrder = (List<Order>) request.getAttribute("myBuyOrder");
        Map<String,List<Goods>> myBuyOrderGoods = (Map<String,List<Goods>>) request.getAttribute("myBuyOrderGoods");

        List<Kind> kindList = (List<Kind>) request.getAttribute("kindList");
        Map<String,List<GoodName>> everyTypeOfGoodName = (Map<String,List<GoodName>>) request.getAttribute("everyTypeOfGoodName");

        List<Order> needMeDeliveryOrder = (List<Order>) request.getAttribute("needMeDeliveryOrder");

        Map<String,List<Goods>> theDeliveryOrderGoods = (Map<String,List<Goods>>) request.getAttribute("theDeliveryOrderGoods");

        List<Sorter> sorterList = (List<Sorter>) request.getAttribute("sorterList");

        /*
        创建两个map,分别存储各种商品的最大存储量和最大存储天数
        键为商品名，值为数值
         */

        Map<String,String> theMaxDay = new HashMap<String,String>();
        Map<String,String> theMaxNumber = new HashMap<String,String>();

        for (GoodMax goodmax1 : goodMaxList){
            theMaxDay.put(goodmax1.getGoodName(),String.valueOf(goodmax1.getMaxDay()));
            theMaxNumber.put(goodmax1.getGoodName(),String.valueOf(goodmax1.getMaxNumber()));
        }

    %>

    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <!-- Brand/logo -->
        <a class="navbar-brand" href="#">仓库管理与监控/<span id="userType">仓库管理</span></a>

        <!-- Links -->
        <ul class="nav nav-pills" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="pill" href="#contain1">商品库存信息</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#contain2">仓库货架</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#contain3">订单管理</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="pill" href="#contain4">基础信息管理</a>
            </li>
        </ul>

        <div id="userInfo">您好，<%=userInfo[2] %></div>
        <div><form action="logout"><input type="submit" value="退出登录" id="logoutButton" class="btn btn-danger"> </form></div>

    </nav>

    <div class="tab-content">

        <div id="contain1" class="container tab-pane active"><br>
            <!-- 这里显示货架上商品的存储情况 -->
            <div class="alert alert-info" style="text-align: center">
                <strong><%=storage.getName() %></strong><br>
                <%=storage.getLocation()  %>
            </div>

            <div class="row"><!-- 货架信息 -->
                <%
                    int i = 0;
                    for (Shelf s : shelfList){

                %>
                <div class="col-sm-4 shelf"><!-- 货架本体 -->
                    <div style="background-color: lightsteelblue">
                        货架号:<%=s.getId()%><!-- 货架头信息 -->
                        <br/>
                        区  域：<%=s.getArea()%>
                    </div>

                    <div><!-- 货架中的商品信息 -->
                        <%
                            List<Goods> goods = shelfGoods.get(String.valueOf(s.getId()));
                            if(goods.size() == 0){
                                out.print("<h3>货架中没有商品</h3>");
                            }else{
                                out.print("<hr/>");
                                for(Goods goods2 : goods){
                                    int flag = 0;
                        %>

                        <div class="card">
                            <div class="card-header"><a data-toggle="collapse" data-target="#<%="goods"+goods2.getName()+flag%>">批号：<%=goods2.getBatchNumber()%></a></div>
                            <div class="card-body collapse" id="<%="goods"+goods2.getName()+flag%>">
                                <h7>种类：<%=goods2.getKind()%></h7><br/>
                                <h7>品名：<%=goods2.getName()%></h7><br/>
                                <h7>入库时间：<%=goods2.getFirstInTime()%></h7><br/>
                                <h7>数量：<%=goods2.getNumber()%></h7><br/>
                                <%
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date dateIn = sdf.parse(goods2.getFirstInTime());
                                    Date dateNow = new Date();
                                    DateUtils du = new DateUtils();
                                    int days = du.dateDiff(dateNow,dateIn);
                                    int maxDays = Integer.parseInt(theMaxDay.get(goods2.getName()));

                                    String daysColor;

                                    if(days < (maxDays*0.5)){
                                        daysColor = "springgreen";
                                    }else if(days > (maxDays*0.5) && days < (maxDays*0.9)){
                                        daysColor = "yellow";
                                    }else {
                                        daysColor = "red";
                                    }

                                    if(maxDays == 0){ //这里表示未设置最大值
                                        daysColor = "springgreen";
                                    }

                                %>
                                <span style="background-color: <%=daysColor%>"><h7>存储天数：<%=days%></h7></span>
                            </div>
                        </div>

                        <%
                                    flag ++;
                                }
                            }
                        %>

                    </div>
                </div>

                <%
                        if(i % 3 == 0)
                            out.print("<br/>");
                    }
                %>
            </div>
            <div>
                <hr>
                <%
                    for (GoodName g3 : goodNameList){
                        if(!(everyGoodsNumber.get(g3.getGoodName()) == null)){

                            int max = Integer.parseInt(theMaxNumber.get(g3.getGoodName()));
                            int theNumber = everyGoodsNumber.get(g3.getGoodName());

                            //根据现存商品的数量，对比商品最大存储数量，以色卡的形式给予提示

                            String infoClass = "";

                            if(max == 0){
                                infoClass = "alert alert-success";
                            }else if(theNumber <= (max*0.5)){
                                infoClass = "alert alert-success";
                            }else if(theNumber > (max*0.5) && theNumber <= (max*0.9)){
                                infoClass = "alert alert-warning";
                            }else if(theNumber > (max*0.9)){
                                infoClass = "alert alert-danger";
                            }
                %>
                <div class="<%=infoClass%>">
                    <p>商品名称：<%=g3.getGoodName()%></p>
                    <p>仓库贮存数量：<%=everyGoodsNumber.get(g3.getGoodName())+"/"+( (g3.getUnit() == null || g3.getUnit() == "") ? "个" : g3.getUnit() )%></p>
                    <p>仓库最大贮存量：<%
                        if(theMaxNumber.get(g3.getGoodName()).equals("0"))
                            out.print("未设置");
                        else {
                            if(g3.getUnit() == null || g3.getUnit() == "")
                                out.print(theMaxNumber.get(g3.getGoodName()) + "/个");
                            else
                                out.print(theMaxNumber.get(g3.getGoodName()) + "/" +g3.getUnit());
                        }

                        %></p>
                    <hr>
                </div>


                <%
                        }
                    }
                %>



            </div>


        </div>

        <div id="contain2" class="container tab-pane fade"><br>


            <div class="alert alert-info" style="text-align: center">
                <strong><%=storage.getName() %></strong><br>
                <%=storage.getLocation()  %>
            </div>

            <div class="row"><!-- 货架信息 -->
            <%
                int j = 0;
                for (Shelf s : shelfList){

            %>
                <div class="col-sm-3 shelf">



                    <%
                        out.print("货架号："+s.getId());
                        out.print("<br>");
                        out.print("区域："+s.getArea());

                    %>

                </div>

            <%
                    if(j % 4 == 0)
                        out.print("<br/>");
                }
            %>
            </div>



            <div>
                <form action="addShelf" method="post">
                    <div class="form-group">
                        <input type="hidden" name="storageId" value="<%=storage.getId()%>" class="form-control">

                        <label for="area">新增货架区域：</label>
                        <input type="text" name="area" id="area" placeholder="区域" class="form-control">
                        <br/>

                        <input type="submit" value="增加货架" class="btn btn-success">
                    </div>
                </form>


            </div>

        </div>

        <div id="contain3" class="container tab-pane fade"><br>
            <!-- 这里是订单相关信息 -->
            <div class="alert alert-dark" style="text-align: center">
                <strong>与我相关的订单</strong>
            </div>

            <div><!-- 采购订单的入库功能 -->
                <h3>采购订单</h3>
                <div id="accordion2">

                    <%
                        for (Order o : myBuyOrder){
                            int myBuyOrderFlag = 0;
                    %>

                    <div class="card">
                        <div class="card-header">
                            <a class="card-link" data-toggle="collapse" href="#myBuyOrder<%=myBuyOrderFlag%>">
                                <p>订单号：<%=o.getId()%></p>
                            </a>
                        </div>

                        <div id="myBuyOrder<%=myBuyOrderFlag%>" class="collapse" data-parent="#accordion2">
                            <div class="card-body">
                                <p>订单当前状态：待入库</p>
                                <%
                                    List<Goods> buyOrderGoods = myBuyOrderGoods.get(String.valueOf(o.getId()));
                                    for(Goods go : buyOrderGoods){
                                %>

                                <p>商品种类：<%=go.getKind()%></p><br/>
                                <p>商品名称：<%=go.getName()%></p><br/>
                                <p>商品数量：<%=go.getNumber()%></p>

                                <form action="goodsInStore" method="post">
                                    <input type="hidden" name="orderId" value="<%=o.getId() %>">
                                    <input type="hidden" name="goodId" value="<%=go.getId()%>">
                                    <label>选择入库的货架：</label>
                                    <select name="shelfId">
                                        <%
                                            for (Shelf sf : shelfList){
                                        %>
                                        <option value="<%=sf.getId()%>"><%=sf.getId()%>号货架</option>
                                        <%
                                            }
                                        %>
                                    </select>

                                    <input type="submit" value="入库" class="btn btn-outline-success">

                                </form>


                                <%
                                    }
                                %>
                            </div>
                        </div>

                    </div>
                    <%
                            myBuyOrderFlag ++;
                        }
                    %>

                </div>


            </div>

            <div>
                <h3>采购订单申请</h3>

                <form action="storageAdminApplayBuyOrder" method="post">
                    <label>需要采购的商品：</label>
                    <select name="needBuyGoods">
                        <%
                            for (Kind kind222 : kindList){
                                List<GoodName> goodNames222 = everyTypeOfGoodName.get(kind222.getKind());
                                for (GoodName goodName222 : goodNames222){
                                    %>
                        <option value="<%=kind222.getKind()+"|"+goodName222.getGoodName() %>">
                            <%=kind222.getKind()+"|"+goodName222.getGoodName() %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>

                    <label>需要采购的数量：</label>
                    <input type="text" name="number">

                    <input type="hidden" name="sponsorUser" value="<%=userInfo[0]%>">


                    <input type="submit" value="提交采购信息" class="btn btn-outline-success">


                </form>

            </div>

            <div>
                <!-- 这里是仓库管理员需要出货的订单 -->
                <h3>
                    待出货订单
                </h3>

                <div id="accordion3">

                    <%
                        for (Order needMe : needMeDeliveryOrder){
                            int needMeFlag = 1;
                    %>

                    <div class="card">
                        <div class="card-header">
                            <a class="card-link" data-toggle="collapse" href="#needMe<%=needMeFlag%>">
                                <p>订单号：<%=needMe.getId()%></p>
                            </a>
                        </div>
                        <div id="needMe<%=needMeFlag%>" class="collapse" data-parent="#accordion3">
                            <div class="card-body">
                                <p>发起部门：销售终端</p>
                                <p>货物信息：</p>
                                <p><%=needMe.getGoods() %>,<%=needMe.getNumber() %></p>

                                <form action="sendGoods" method="post"><!-- 这里是生成出货单的表单 -->
                                    <div class="form-group">


                                        <label>选择需要出库的商品:</label>
                                        <select multiple class="form-control" style="width: 400px;">
                                            <%
                                                List<Goods> wantDeliveryGoods = theDeliveryOrderGoods.get(String.valueOf(needMe.getId()));
                                                for (Goods g : wantDeliveryGoods){
                                            %>
                                            <option><%="货架号："+g.getShelfNumber()+",品名"+g.getName()+",数量"+g.getNumber()%></option>
                                            <%
                                                }
                                            %>
                                        </select>

                                        <label>请选择责任分拣员：</label>
                                        <select name="sorterId"><!-- 本仓库的分拣员 -->
                                            <%
                                                for (Sorter so : sorterList){
                                            %>
                                            <option value="<%=so.getId()%>"><%=so.getName()+",区域"+so.getArea() %></option>

                                            <%
                                                }
                                            %>

                                        </select>
                                        <input type="hidden" name="orderId" value="<%=needMe.getId() %>">
                                        <input type="hidden" name="goodName" value="<%=needMe.getGoods()%>">
                                        <input type="hidden" name="receiveDep" value="<%=needMe.getSponsorDep()%>">
                                        <input type="hidden" name="receiveUser" value="<%=needMe.getSponsorUser()%>">

                                        <input type="submit" value="出货" class="btn btn-outline-success">


                                    </div>



                                </form>




                            </div>
                        </div>
                    </div>

                    <%
                            needMeFlag ++;
                        }
                    %>

                </div>


            </div>

        </div>


        <div id="contain4" class="container tab-pane fade"><br>
            <!-- 这里是商品的上限信息 -->
            <div id="accordion">

                <%
                    for (GoodMax goodMax : goodMaxList){

                        String formMaxDay;
                        String formMaxNumber;

                %>

                <div class="card">
                    <div class="card-header">
                        <a class="card-link" data-toggle="collapse" href="#<%=goodMax.getKind()+goodMax.getGoodName()%>">
                            种类：<%=goodMax.getKind()%>,品名：<%=goodMax.getGoodName()%>
                        </a>
                    </div>
                    <div id="<%=goodMax.getKind()+goodMax.getGoodName()%>" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <p>商品种类：<%=goodMax.getKind()%></p><br/>
                                    <p>商品名称：<%=goodMax.getGoodName()%></p><br/>
                                    <p>最大存储时间：<%
                                        if(goodMax.getMaxDay() == 0){
                                            formMaxDay = "0";
                                            out.print("未设置");
                                        }else{
                                            formMaxDay = String.valueOf(goodMax.getMaxDay());
                                            out.print(goodMax.getMaxDay());
                                        }
                                    %></p><br/>
                                    <p>最大存储数量：<%
                                        if(goodMax.getMaxNumber() == 0){
                                            formMaxNumber = "0";
                                            out.print("未设置");
                                        }else{
                                            formMaxNumber = String.valueOf(goodMax.getMaxNumber());
                                            out.print(goodMax.getMaxNumber());
                                        }
                                    %></p><br/>
                                </div>
                                <div class="col-sm-6">
                                    <h4>
                                        添加或更改贮存信息
                                    </h4>
                                    <form action="updateGoodMaxInfo" method="post">
                                        <input type="hidden" name="storageId" value="<%=storage.getId()%>">
                                        <input type="hidden" name="kind" value="<%=goodMax.getKind()%>">
                                        <input type="hidden" name="goodName" value="<%=goodMax.getGoodName()%>">

                                        <div class="form-group">
                                            <label for="maxNumber">最大贮存数量：</label>
                                            <input type="text" class="form-control" id="maxNumber" name="maxNumber" value="<%=formMaxNumber %>">
                                        </div>
                                        <div class="form-group">
                                            <label for="maxDay">最长贮存时间（天）</label>
                                            <input type="text" class="form-control" id="maxDay" name="maxDay" value="<%=formMaxDay%>">
                                        </div>

                                        <button type="submit" class="btn btn-primary">确认更改</button>
                                    </form>


                                </div>
                            </div>
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
