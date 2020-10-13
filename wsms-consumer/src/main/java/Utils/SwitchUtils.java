package Utils;

import com.sun.org.apache.xpath.internal.operations.Mod;
import entity.*;
import org.springframework.web.servlet.ModelAndView;
import wsms.homepage.LeaderhomeService;
import wsms.homepage.SorterhomeService;
import wsms.homepage.StorageAdminhomeService;
import wsms.homepage.StoreAdminhomeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwitchUtils {

    public static ModelAndView leader(LeaderhomeService leaderhomeService){
        ModelAndView mav = new ModelAndView();

        String userInfo[] = {"1","1399636557@qq.com","陈洋洋"};

        mav.addObject("userInfo",userInfo);

        //获取商品种类及名称信息
        List<Kind> kindList = leaderhomeService.selectAllKind();
        Map<String,List<GoodName>> everyTypeOfGoodName = new HashMap<>();
        everyTypeOfGoodName = leaderhomeService.selectAllKindOfGoods();

        System.out.print("88888888888888888888888888888888888888执行刷新过程");

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

        return mav;
    }


    public static ModelAndView storageAdmin(LeaderhomeService leaderhomeService,StorageAdminhomeService storageAdminhomeService){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/storageadminHome");

        String userInfo[] = {"1","1399636557@163.com","陈洋"};

        mav.addObject("userInfo",userInfo);


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
        return mav;
    }


    public static ModelAndView storeAdmin(LeaderhomeService leaderhomeService,StorageAdminhomeService storageAdminhomeService,StoreAdminhomeService storeAdminhomeService){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/storeadminHome");

        String userInfo[] = {"1","1399636557@189.com","陈大洋"};

        mav.addObject("userInfo",userInfo);

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

        return mav;
    }

    public static ModelAndView sorter(SorterhomeService sorterhomeService){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/sorterHome");

        String userInfo[] = {"1","279205512@163.com","胡八一"};

        mav.addObject("userInfo",userInfo);

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

        return mav;


    }



}
