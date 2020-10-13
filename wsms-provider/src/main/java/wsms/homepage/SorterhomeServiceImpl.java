package wsms.homepage;

import Utils.SqlSessionFactoryUtils;
import com.alibaba.dubbo.config.annotation.Service;
import entity.DeliveryOrder;
import entity.Goods;
import entity.StorageAdmin;
import entity.StoreAdmin;
import mapper.DeliveryOrderMapper;
import mapper.GoodsMapper;
import mapper.StoreAdminMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SorterhomeServiceImpl implements SorterhomeService {

    @Override
    public List<DeliveryOrder> sorterGetNeedDeliveryOrder(int sorterId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        List<DeliveryOrder> deliveryOrderList = deliveryOrderMapper.sorterGetNeedDeliveryOrder(sorterId);

        sqlSession.close();

        return deliveryOrderList;
    }

    @Override
    public List<DeliveryOrder> sorterGetAllDomeDeliveryOrder(int sorterId) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        List<DeliveryOrder> deliveryOrderList = deliveryOrderMapper.sorterGetAllDoneDeliveryOrder(sorterId);

        sqlSession.close();

        return deliveryOrderList;
    }

    @Override
    public Map<String,List<Goods>> soterGetDeliveryOrderGoods(List<DeliveryOrder> deliveryOrderList) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);


        Map<String,List<Goods>> deliveryOrderGoods = new HashMap<>();

        for(DeliveryOrder d : deliveryOrderList){

            List<Goods> goodsList = goodsMapper.soterGetDeliveryOrderGoods(d.getId());

            deliveryOrderGoods.put(String.valueOf(d.getId()),goodsList);
        }



        sqlSession.close();
        return deliveryOrderGoods;
    }

    @Override
    public void letGoodsSend(int goodId) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        goodsMapper.letGoodsSend(goodId);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Map<String, StoreAdmin> selectAllStoraAdmin() {

        Map<String, StoreAdmin> s = new HashMap<>();
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StoreAdminMapper storeAdminMapper = sqlSession.getMapper(StoreAdminMapper.class);

        List<StoreAdmin> storeAdminList = storeAdminMapper.getAllStorageAdmin();

        for(StoreAdmin sss : storeAdminList){
            s.put(String.valueOf(sss.getId()),sss);
        }

        return s;
    }

    @Override
    public void updateDeliveryOrderToB(int deliveryOrderId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        deliveryOrderMapper.updateDeliveryOrderToB(deliveryOrderId);

        sqlSession.commit();

        sqlSession.close();
    }
}
