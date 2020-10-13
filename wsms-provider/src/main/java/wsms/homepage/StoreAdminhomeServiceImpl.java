package wsms.homepage;

import Utils.SqlSessionFactoryUtils;
import entity.DeliveryOrder;
import entity.GoodName;
import entity.Goods;
import entity.StorageAdmin;
import mapper.DeliveryOrderMapper;
import mapper.GoodsMapper;
import mapper.OrderMapper;
import mapper.StorageAdminMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StoreAdminhomeServiceImpl implements StoreAdminhomeService {

    @Override
    public Map<String, Integer> selectAllGoodsNumber(List<GoodName> goodNameList) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        Map<String,Integer> goodNumber = new HashMap<>();

        for(GoodName g : goodNameList){
            Integer i = goodsMapper.getGoodNumberBuGoodName(g.getGoodName());
            goodNumber.put(g.getGoodName(),i);
        }
        sqlSession.close();

        return goodNumber;
    }

    @Override
    public List<StorageAdmin> selectAllStorageAdmin() {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StorageAdminMapper storageAdminMapper = sqlSession.getMapper(StorageAdminMapper.class);

        List<StorageAdmin> storageAdminList = storageAdminMapper.getAllStorageAdmin();

        sqlSession.close();
        return storageAdminList;
    }

    @Override
    public void applayOrder(String type, String sponsorDep, int sponsorUser, String disposeDep, int disposeUser, String goods, int number, String startTime) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        String uuid = UUID.randomUUID().toString().replace("-","");

        orderMapper.storeAddOrder(type,sponsorDep,sponsorUser,disposeDep,disposeUser,goods,number,startTime,uuid);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public List<DeliveryOrder> selectReceiveOrder(int storeAdminId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        List<DeliveryOrder> deliveryOrderList = deliveryOrderMapper.storeAdminGetReceiveOrder(storeAdminId);

        sqlSession.close();

        return deliveryOrderList;
    }

    @Override
    public Map<String, List<Goods>> goodInReceiveOrder(List<DeliveryOrder> deliveryOrderList) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        Map<String,List<Goods>> goods = new HashMap<>();

        for (DeliveryOrder d : deliveryOrderList){
            List<Goods> goodsList = goodsMapper.storeAdminGetDeliveryOrderGoods(d.getId());

            goods.put(String.valueOf(d.getId()),goodsList);

        }

        sqlSession.close();

        return goods;
    }

    @Override
    public void storeReceiveGoods(int deliveryOrderId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        deliveryOrderMapper.storeReceiveGoods(deliveryOrderId);

        sqlSession.commit();
        sqlSession.close();

    }
}
