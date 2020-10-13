package wsms.homepage;

import entity.*;

import java.util.List;
import java.util.Map;

public interface StorageAdminhomeService {
    Storage selectStorageById(int id);
    List<Shelf> selectShelfBuStorageId(int id);
    void addShelf(int storageId,String area);
    Map<String,List<Goods>> selectShelfGoods(List<Shelf> shelfList);
    void addGoodMaxInfo(int id,String kind,String goodName);
    GoodMax selectGoodMaxByKindAndGood(String kind,String good);
    List<GoodMax> selectAllGoodMaxByStorageId(int storageId);
    void updateGoodMaxInfo(int storageId,String kind,String goodName,String maxNumber,String maxDay);
    Map<String,Integer> selectGoodNumberInaStorage(List<Shelf> shelfList);
    List<GoodName> selectAllGoodName();
    List<Order> selectBuyOrder(int sponsorUser,String type);

    //根据订单id,返回和订单关联的商品，主要用于采购订单的入库工作，String 是订单号
    Map<String,List<Goods>> selectGoodsBuOrderId(List<Order> orderList);

    //商品入库
    void goodsInStore(int id,int shelfId,String firstInTime);

    //关闭订单
    void closeOrder(int id,String endTime);

    //仓库管理员申请采购订单
    void storageAdminApplayBuyOrder(int sponsorUser,String time,String UUID);

    Order selectOrderByUUID(String UUID);

    //添加采购订单商品
    void storageAdminAddBuyOrderGoods(String batchNumber,String kind,String name,int number,String firstInTime,int orderId);

    //仓库管理员获取需要出货的订单
    List<Order> storageAdminNeedDeliveryOrder(int disposeUser);

    //根据商品名获取在存储的商品信息
    List<Goods> selectInStorageGoodsByName(String name);

    //根据仓库id 获得分拣员列表
    List<Sorter> selectSorterByStorageId(int storageId);

    //创建一个出货单
    void addDeliveryOrder(String receiveDep,int receiveUser,int sorterId,String uuid);

    //根据UUID返回一个出货单
    DeliveryOrder selectDeliveryOrderByUUID(String uuid);

    //根据商品名称，修改其到出货单中
    void letGoodsInDeliveryOrder(String goodName,int deliveryOrderId);

}
