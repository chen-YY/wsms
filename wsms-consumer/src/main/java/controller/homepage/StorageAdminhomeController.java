package controller.homepage;

import Utils.SwitchUtils;
import entity.DeliveryOrder;
import entity.Leader;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsms.homepage.LeaderhomeService;
import wsms.homepage.StorageAdminhomeService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class StorageAdminhomeController {

    @Autowired
    StorageAdminhomeService storageAdminhomeService;

    @Autowired
    LeaderhomeService leaderhomeService;


    @RequestMapping("addShelf")
    public ModelAndView addShelf(String storageId,String area){


        storageAdminhomeService.addShelf(Integer.parseInt(storageId),area);

        ModelAndView mav = SwitchUtils.storageAdmin(leaderhomeService,storageAdminhomeService);

        return mav;

    }

    @RequestMapping("updateGoodMaxInfo")
    public ModelAndView updateGoodMaxInfo(String storageId,String kind,String goodName,String maxNumber,String maxDay){


        storageAdminhomeService.updateGoodMaxInfo(Integer.parseInt(storageId),kind,goodName,maxNumber,maxDay);

        ModelAndView mav = SwitchUtils.storageAdmin(leaderhomeService,storageAdminhomeService);

        return mav;
    }

    @RequestMapping("goodsInStore")
    public ModelAndView goodInStorage(String goodId,String shelfId,String orderId){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        storageAdminhomeService.goodsInStore(Integer.parseInt(goodId),Integer.parseInt(shelfId),date);

        storageAdminhomeService.closeOrder(Integer.parseInt(orderId),date);

        ModelAndView mav = SwitchUtils.storageAdmin(leaderhomeService,storageAdminhomeService);


        return mav;
    }

    @RequestMapping("storageAdminApplayBuyOrder")
    public ModelAndView storageAdminApplayBuyOrder(String needBuyGoods,String number,String sponsorUser){


        String goodinfo[] = needBuyGoods.split("\\|");
        String kind = goodinfo[0];
        String name = goodinfo[1];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());

        String uuid = UUID.randomUUID().toString().replace("-","");

        storageAdminhomeService.storageAdminApplayBuyOrder(Integer.parseInt(sponsorUser),time,uuid);

        Order order = storageAdminhomeService.selectOrderByUUID(uuid);

        String batchNumber = String.valueOf(System.currentTimeMillis());

        storageAdminhomeService.storageAdminAddBuyOrderGoods(batchNumber,kind,name,Integer.parseInt(number),time,order.getId());

        ModelAndView mav = SwitchUtils.storageAdmin(leaderhomeService,storageAdminhomeService);

        return mav;
    }

    @RequestMapping("sendGoods")
    public ModelAndView sendGoods(String goodName,String receiveDep,String receiveUser,String sorterId,String orderId){

        //根据参数，创建一个出货单

        String uuid = UUID.randomUUID().toString().replace("-","");

        storageAdminhomeService.addDeliveryOrder(receiveDep,Integer.parseInt(receiveUser),Integer.parseInt(sorterId),uuid);



        //返回这个订单的id

        DeliveryOrder deliveryOrder = storageAdminhomeService.selectDeliveryOrderByUUID(uuid);

        //根据商品名称，更改商品状态为出货单商品，并绑定出货单id

        storageAdminhomeService.letGoodsInDeliveryOrder(goodName,deliveryOrder.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());

        storageAdminhomeService.closeOrder(Integer.parseInt(orderId),time);


        ModelAndView mav = SwitchUtils.storageAdmin(leaderhomeService,storageAdminhomeService);
        return mav;

    }

}
