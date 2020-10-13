package controller.login;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsms.Login.LoginService;
import wsms.homepage.LeaderhomeService;
import wsms.homepage.SorterhomeService;
import wsms.homepage.StorageAdminhomeService;
import wsms.homepage.StoreAdminhomeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    LeaderhomeService leaderhomeService;

    @Autowired
    StorageAdminhomeService storageAdminhomeService;

    @Autowired
    StoreAdminhomeService storeAdminhomeService;

    @Autowired
    SorterhomeService sorterhomeService;

    //这里跳转去登录页面
    @RequestMapping("toLogin")
    public ModelAndView toLogin(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login");
        return mav;
    }

    //执行登录的逻辑步骤
    @RequestMapping("login")
    public ModelAndView login(String account, String password, String accountType, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();


        String userInfo[];
        String result = loginService.login(account,password,accountType);

        /*
        判断字符串是否相等不能用 ==
        System.out.println(result == "no message");  false
        System.out.println(result.equals("no message")); true
        */


        //map用于根据不同的账户类型返回不同的页面
        Map<String,String> views = new HashMap<String, String>();
        views.put("leader","login/leaderHome");
        views.put("storageadmin","login/storageadminHome");
        views.put("storeadmin","login/storeadminHome");
        views.put("sorter","login/sorterHome");

        if(result.equals("no message")){
            mav.setViewName("login/loginFault");
            return mav;
        }else{
            //这里是登录成功后返回信息模块
            userInfo = result.split("\\|");
            mav.setViewName(views.get(accountType));
            mav.addObject("userInfo",userInfo);

            HttpSession session = request.getSession();

            switch (accountType){
                //根据不同的账户类型，做不同的响应动作
                case "leader" :{
                    //添加session
                    session.setAttribute("id",userInfo[0]);
                    session.setAttribute("account",userInfo[1]);
                    session.setAttribute("name",userInfo[2]);

                    //获取关于首页的信息

                    //获取商品种类及名称信息
                    List<Kind> kindList = leaderhomeService.selectAllKind();
                    Map<String,List<GoodName>> everyTypeOfGoodName = new HashMap<>();
                    everyTypeOfGoodName = leaderhomeService.selectAllKindOfGoods();

                    //获取仓库信息
                    List<Storage> storageList = leaderhomeService.selectAllStorage();

                    //获取未审批的订单信息
                    List<Order> noApproveOrderList = leaderhomeService.selectNoApproveOrder();

                    //获取订单中的发起人以及处理人信息
                    Map<String,Map<String,String>> FandCInfo = leaderhomeService.getOrderFaqirenAndChulirenInfo(noApproveOrderList);

                    //获取订单中的商品信息
                    Map<String,List<Goods>> goodsList = leaderhomeService.selectNoApproveOrderGoodsByOrderId(noApproveOrderList);

                    mav.addObject("kindList",kindList);
                    mav.addObject("everyTypeOfGoodName",everyTypeOfGoodName);
                    mav.addObject("storageList",storageList);
                    mav.addObject("noApproveOrderList",noApproveOrderList);
                    mav.addObject("FandCInfo",FandCInfo);
                    mav.addObject("goodsList",goodsList);

                    break;
                }
                case "storageadmin" :{//仓库管理员

                    session.setAttribute("id",userInfo[0]);
                    session.setAttribute("account",userInfo[1]);
                    session.setAttribute("name",userInfo[2]);



                    //返回管理的仓库信息
                    Storage storage = storageAdminhomeService.selectStorageById(Integer.parseInt(userInfo[0]));

                    //获取仓库的货架信息
                    List<Shelf> shelfList = storageAdminhomeService.selectShelfBuStorageId(storage.getId());

                    //返回每个货架上存储的商品信息
                    Map<String,List<Goods>> shelfGoods = storageAdminhomeService.selectShelfGoods(shelfList);

                    //返回商品的数量、日期上限信息
                    List<GoodMax> goodMaxList = storageAdminhomeService.selectAllGoodMaxByStorageId(storage.getId());

                    //返回商品的存储数量信息，相同的商品相同计数
                    Map<String,Integer> everyGoodsNumber = storageAdminhomeService.selectGoodNumberInaStorage(shelfList);

                    //返回所有的商品名
                    List<GoodName> goodNamesList = storageAdminhomeService.selectAllGoodName();

                    //返回我的采购订单
                    List<Order> myBuyOrder = storageAdminhomeService.selectBuyOrder(Integer.parseInt(userInfo[0]),"A");

                    //返回采购订单中的商品
                    Map<String,List<Goods>> myBuyOrderGoods = storageAdminhomeService.selectGoodsBuOrderId(myBuyOrder);

                    //返回需要我处理的出货单
                    List<Order> needMeDeliveryOrder = storageAdminhomeService.storageAdminNeedDeliveryOrder(Integer.parseInt(userInfo[0]));

                    //根据仓库id 返回分拣员列表
                    List<Sorter> sorterList = storageAdminhomeService.selectSorterByStorageId(storage.getId());








                    mav.addObject("cyy","陈洋洋的测试信息");
                    mav.addObject("storage",storage);
                    mav.addObject("shelfList",shelfList);
                    mav.addObject("shelfGoods",shelfGoods);
                    mav.addObject("goodMaxList",goodMaxList);
                    mav.addObject("everyGoodsNumber",everyGoodsNumber);
                    mav.addObject("goodNamesList",goodNamesList);
                    mav.addObject("myBuyOrder",myBuyOrder);
                    mav.addObject("myBuyOrderGoods",myBuyOrderGoods);
                    mav.addObject("needMeDeliveryOrder",needMeDeliveryOrder);
                    mav.addObject("sorterList",sorterList);

                    /*
                    更新本仓库的物品上限信息
                    根据所有的商品种类以及商品名称，遍历查询表中是否有这个组合，如果有，则跳过
                    如果没有，则插入                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             一条。
                     */
                    List<Kind> kindList = leaderhomeService.selectAllKind();
                    Map<String,List<GoodName>> everyTypeOfGoodName = new HashMap<>();
                    everyTypeOfGoodName = leaderhomeService.selectAllKindOfGoods();

                    mav.addObject("kindList",kindList);
                    mav.addObject("everyTypeOfGoodName",everyTypeOfGoodName);

                    for (Kind k : kindList){
                        List<GoodName> goodNameList = everyTypeOfGoodName.get(k.getKind());
                        for(GoodName goodName : goodNameList){

                            GoodMax goodMax = storageAdminhomeService.selectGoodMaxByKindAndGood(k.getKind(),goodName.getGoodName());

                            if(goodMax == null){

                                storageAdminhomeService.addGoodMaxInfo(storage.getId(),k.getKind(),goodName.getGoodName());

                            }
                        }
                    }


                    //如果需要出货的订单不为空，则存入对应的商品

                    if(!(needMeDeliveryOrder.size() == 0)){
                        Map<String,List<Goods>> theDeliveryOrderGoods = new HashMap<>();
                        for (Order o : needMeDeliveryOrder){
                            List<Goods> goods222 = storageAdminhomeService.selectInStorageGoodsByName(o.getGoods());
                            theDeliveryOrderGoods.put(String.valueOf(o.getId()),goods222);
                        }
                        mav.addObject("theDeliveryOrderGoods",theDeliveryOrderGoods);
                    }





                    break;
                }
                case "storeadmin" :{

                    //返回商品列表信息
                    List<Kind> kindList = leaderhomeService.selectAllKind();
                    Map<String,List<GoodName>> everyTypeOfGoodName = new HashMap<>();
                    everyTypeOfGoodName = leaderhomeService.selectAllKindOfGoods();

                    //所有商品名称
                    List<GoodName> goodNamesList = storageAdminhomeService.selectAllGoodName();

                    //返回所有商品存储信息
                    Map<String,Integer> goodNumber = storeAdminhomeService.selectAllGoodsNumber(goodNamesList);

                    //返回所有的仓库管理员信息，供选择
                    List<StorageAdmin> storageAdminList = storeAdminhomeService.selectAllStorageAdmin();

                    //返回需要我收货的订单
                    List<DeliveryOrder> deliveryOrderList = storeAdminhomeService.selectReceiveOrder(Integer.parseInt(userInfo[0]));

                    //返回收货单中的商品信息
                    Map<String,List<Goods>> goodInReceiveOrder = storeAdminhomeService.goodInReceiveOrder(deliveryOrderList);



                    mav.addObject("kindList",kindList);
                    mav.addObject("everyTypeOfGoodName",everyTypeOfGoodName);
                    mav.addObject("goodNamesList",goodNamesList);
                    mav.addObject("goodNumber",goodNumber);
                    mav.addObject("storageAdminList",storageAdminList);
                    mav.addObject("deliveryOrderList",deliveryOrderList);
                    mav.addObject("goodInReceiveOrder",goodInReceiveOrder);



                    break;
                }
                case "sorter" :{

                    //返回我的订单
                    List<DeliveryOrder> myDeliveryOrder = sorterhomeService.sorterGetNeedDeliveryOrder(Integer.parseInt(userInfo[0]));

                    //返回历史订单
                    List<DeliveryOrder> myHistoryDeliveryOrder = sorterhomeService.sorterGetAllDomeDeliveryOrder(Integer.parseInt(userInfo[0]));

                    //返回需要出货订单商品的信息
                    Map<String,List<Goods>> deliveryOrderGoods = sorterhomeService.soterGetDeliveryOrderGoods(myDeliveryOrder);

                    //返回店主信息
                    Map<String,StoreAdmin> storeAdminInfo = sorterhomeService.selectAllStoraAdmin();


                    mav.addObject("myDeliveryOrder",myDeliveryOrder);
                    mav.addObject("myHistoryDeliveryOrder",myHistoryDeliveryOrder);
                    mav.addObject("deliveryOrderGoods",deliveryOrderGoods);
                    mav.addObject("storeAdminInfo",storeAdminInfo);



                    break;
                }
            }

            System.out.println("这里是controller打印用户信息，检查是否分割正常。");
            for (String s : userInfo){
                System.out.println(s);
            }
            return mav;
        }
        /*
        参数传递测试
        mav.addObject("message",account+accountType+password);
        mav.setViewName("index");

         */
    }

    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();
        session.removeAttribute("id");
        session.removeAttribute("account");
        session.removeAttribute("name");

        mav.setViewName("login/login");

        return mav;

    }


}
