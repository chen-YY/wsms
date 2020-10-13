package mapper;

import entity.DeliveryOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeliveryOrderMapper {
    public void addDeliveryOrder(@Param("receiveDep")String receiveDep,@Param("receiveUser")int receiveUser,@Param("sorterId")int sorterId,@Param("uuid")String uuid);
    public DeliveryOrder getDeliveryOrderByUUID(String uuid);
    public List<DeliveryOrder> sorterGetNeedDeliveryOrder(int sorterId);
    public List<DeliveryOrder> sorterGetAllDoneDeliveryOrder(int sorterId);
    public void updateDeliveryOrderToB(int id);

    //收货部门查找收货单
    public List<DeliveryOrder> storeAdminGetReceiveOrder(int storeAdminId);

    //店主接收货物，更改出货单状态
    public void storeReceiveGoods(int deliveryOrderId);


}
