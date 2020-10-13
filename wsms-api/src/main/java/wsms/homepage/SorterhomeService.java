package wsms.homepage;

import entity.DeliveryOrder;
import entity.Goods;
import entity.StoreAdmin;

import java.util.List;
import java.util.Map;

public interface SorterhomeService {
    public List<DeliveryOrder> sorterGetNeedDeliveryOrder(int sorterId);
    public List<DeliveryOrder> sorterGetAllDomeDeliveryOrder(int sorterId);
    public Map<String,List<Goods>> soterGetDeliveryOrderGoods(List<DeliveryOrder> deliveryOrderList);
    public void letGoodsSend(int goodId);


    //返回所有的店主信息，用于显示出货单信息
    public Map<String, StoreAdmin> selectAllStoraAdmin();

    //更改出货单，完成出货
    public void updateDeliveryOrderToB(int deliveryOrderId);
}
