package wsms.homepage;


import entity.*;

import java.util.List;
import java.util.Map;

public interface LeaderhomeService {
    List<Kind> selectAllKind();
    Map<String,List<GoodName>> selectAllKindOfGoods();
    List<Storage> selectAllStorage();
    void addNewStorage(String name,String location);
    List<Kind> selectKindByName(String kind);
    void addGoodName(int id,String goodName);
    void addKind(String name);
    List<Order> selectNoApproveOrder();
    Map<String,Map<String,String>> getOrderFaqirenAndChulirenInfo(List<Order> list);
    void updateStatus(int id,String status);
    void updateRemark(int id,String remark);

    Map<String,List<Goods>> selectNoApproveOrderGoodsByOrderId(List<Order> o);

}
