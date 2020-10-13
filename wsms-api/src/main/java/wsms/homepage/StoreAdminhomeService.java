package wsms.homepage;

import entity.DeliveryOrder;
import entity.GoodName;
import entity.Goods;
import entity.StorageAdmin;

import java.util.List;
import java.util.Map;

public interface StoreAdminhomeService {
    Map<String,Integer> selectAllGoodsNumber(List<GoodName> goodNameList);
    List<StorageAdmin> selectAllStorageAdmin();

    void applayOrder(String type,String sponsorDep,int sponsorUser,String disposeDep,int disposeUser,String goods,int number,String startTime);

    //获取收货单
    List<DeliveryOrder> selectReceiveOrder(int storeAdminId);

    //获取收货单中的商品
    Map<String,List<Goods>> goodInReceiveOrder(List<DeliveryOrder> deliveryOrderList);

    //店主收货
    void storeReceiveGoods(int deliveryOrderId);

}
