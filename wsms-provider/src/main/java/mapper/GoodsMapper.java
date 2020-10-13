package mapper;

import entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    List<Goods> getOrderGoodsByOrderId(int id);
    List<Goods> getShelfGoodsBuShelfId(int id);
    Integer getGoodNumberInaStorage(@Param("shelfListId")List<Integer>shelfListId, @Param("goodsName")String goodsName);
    Integer getGoodNumberBuGoodName(String goodName);
    void goodInStore(@Param("id")int id,@Param("shelfNumber")int shelfNumber,@Param("firstInTime")String firstInTime);

    void storageAdminAddBuyOrderGoods(@Param("batchNumber")String batchNumber,@Param("kind")String kind,@Param("name")String name,@Param("number")int number,@Param("firstInTime")String firstInTime,@Param("orderId")int orderId);

    //根据商品名查找再存的商品
    List<Goods> getInStorageGoodsByName(String name);

    //根据商品名，把所有存储状态的商品全部转移到出货单中
    void updateStorageGoodstoDeliveryOrder(@Param("goodName")String goodName,@Param("deliveryOrderId")int deliveryOrderId);

    //获取出货单中的商品
    List<Goods> soterGetDeliveryOrderGoods(int deliveryOrderId);

    //更改出货单中的商品，使得商品出货
    void letGoodsSend(int goodId);

    List<Goods> storeAdminGetDeliveryOrderGoods(int deliveryOrderId);


}
