package mapper;

import entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface OrderMapper {
    List<Order> getNoApproveOrder();
    void updateStatus(@Param("id")int id,@Param("status")String status);//修改订单状态
    void updateRemark(@Param("id")int id,@Param("remark")String remark);//修改订单备注

    //店主申请一个调拨订单
    void storeAddOrder(@Param("type")String type,@Param("sponsorDep")String sponsorDep,@Param("sponsorUser")int sponsorUser,@Param("disposeDep")String disposeDep,@Param("disposeUser")int disposeUser,@Param("goods")String goods,@Param("number") int number,@Param("startTime")String startTime,@Param("UUID")String UUID);

    //获取采购订单
    List<Order> getBuyOrder(@Param("sponsorUser")int sponsorUser,@Param("type")String type);

    void closeOrder(@Param("id")int id,@Param("endTime")String endTime);

    //仓库管理员申请一个采购订单
    void storageAdminApplayBuyOrder(@Param("sponsorUser")int sponsorUser,@Param("time")String time,@Param("UUID")String UUID);

    Order getOrderByUUID(String UUID);

    //仓库管理员获取需要出货的订单
    List<Order> storageAdminNeedDeliveryOrder(int disposeUser);


}
