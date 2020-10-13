package wsms.homepage;

import Utils.SqlSessionFactoryUtils;
import com.alibaba.dubbo.config.annotation.Service;
import entity.*;
import mapper.*;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

@Service
public class StorageAdminhomeServiceImpl implements StorageAdminhomeService {

    @Override
    public Storage selectStorageById(int id) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        Storage storage = storageMapper.getStorageById(id);

        sqlSession.close();


        return storage;
    }

    @Override
    public List<Shelf> selectShelfBuStorageId(int id) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        ShelfMapper shelfMapper = sqlSession.getMapper(ShelfMapper.class);

        List<Shelf> shelfList = shelfMapper.getShelfByStorageId(id);

        sqlSession.close();

        return shelfList;
    }

    @Override
    public void addShelf(int storageId, String area) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        ShelfMapper shelfMapper = sqlSession.getMapper(ShelfMapper.class);

        shelfMapper.addShelf(storageId,area);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public Map<String, List<Goods>> selectShelfGoods(List<Shelf> shelfList) {

        //获取货架上的商品


        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        Map<String,List<Goods>> shelfGoods = new HashMap<String,List<Goods>>();

        for(Shelf s : shelfList){
            List<Goods> goods = goodsMapper.getShelfGoodsBuShelfId(s.getId());

            shelfGoods.put(String.valueOf(s.getId()),goods);

        }
        sqlSession.close();

        return shelfGoods;
    }

    @Override
    public void addGoodMaxInfo(int id, String kind, String goodName) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodMaxMapper goodMaxMapper = sqlSession.getMapper(GoodMaxMapper.class);

        goodMaxMapper.addNewInfo(id,kind,goodName);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public GoodMax selectGoodMaxByKindAndGood(String kind, String good) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodMaxMapper goodMaxMapper = sqlSession.getMapper(GoodMaxMapper.class);

        GoodMax goodMax = goodMaxMapper.getGoodMaxByKindAndGood(kind,good);

        sqlSession.close();

        return goodMax;
    }

    @Override
    public List<GoodMax> selectAllGoodMaxByStorageId(int storageId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodMaxMapper goodMaxMapper = sqlSession.getMapper(GoodMaxMapper.class);

        List<GoodMax> goodMaxList = goodMaxMapper.getAllByStorageId(storageId);

        sqlSession.close();
        return goodMaxList;
    }

    @Override
    public void updateGoodMaxInfo(int storageId, String kind, String goodName, String maxNumber, String maxDay) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodMaxMapper goodMaxMapper = sqlSession.getMapper(GoodMaxMapper.class);

        goodMaxMapper.updateGoodMaxInfo(storageId,kind,goodName,maxNumber,maxDay);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Map<String, Integer> selectGoodNumberInaStorage(List<Shelf> shelfList) {

        /*
        先获取所有商品种类，循环每种商品种类
        循环过程中，查找商品在某个仓库（根据shelfList查找）in 子句
        使用sum求和
         */
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodNameMapper goodNameMapper = sqlSession.getMapper(GoodNameMapper.class);
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        List<GoodName> goodNameList = goodNameMapper.getAllGoodName();

        Map<String,Integer> goodNumber = new HashMap<String,Integer>();

        List<Integer> shelfListId = new ArrayList<Integer>();

        for(Shelf s : shelfList){
            shelfListId.add(s.getId());
        }

        for (GoodName goodName : goodNameList){

            Integer number = goodsMapper.getGoodNumberInaStorage(shelfListId,goodName.getGoodName());

            goodNumber.put(goodName.getGoodName(),number);
        }

        sqlSession.close();

        return goodNumber;
    }

    @Override
    public List<GoodName> selectAllGoodName() {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodNameMapper goodNameMapper = sqlSession.getMapper(GoodNameMapper.class);

        List<GoodName> goodNameList = goodNameMapper.getAllGoodName();

        return goodNameList;
    }

    @Override
    public List<Order> selectBuyOrder(int sponsorUser, String type) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        List<Order> myBuyOrder = orderMapper.getBuyOrder(sponsorUser,type);

        sqlSession.close();

        return myBuyOrder;

    }

    @Override
    public Map<String, List<Goods>> selectGoodsBuOrderId(List<Order> orderList) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        Map<String,List<Goods>> orderGoodsInfo = new HashMap<>();

        for (Order o : orderList){

            List<Goods> g = goodsMapper.getOrderGoodsByOrderId(o.getId());

            orderGoodsInfo.put(String.valueOf(o.getId()),g);
        }

        sqlSession.close();

        return orderGoodsInfo;
    }

    @Override
    public void goodsInStore(int id, int shelfId, String firstInTime) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        goodsMapper.goodInStore(id,shelfId,firstInTime);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public void closeOrder(int id,String endTime) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        orderMapper.closeOrder(id,endTime);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void storageAdminApplayBuyOrder(int sponsorUser, String time, String UUID) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        orderMapper.storageAdminApplayBuyOrder(sponsorUser,time,UUID);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public Order selectOrderByUUID(String UUID) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        Order order = orderMapper.getOrderByUUID(UUID);
        sqlSession.close();

        return order;
    }

    @Override
    public void storageAdminAddBuyOrderGoods(String batchNumber, String kind, String name, int number, String firstInTime, int orderId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        goodsMapper.storageAdminAddBuyOrderGoods(batchNumber,kind,name,number,firstInTime,orderId);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public List<Order> storageAdminNeedDeliveryOrder(int disposeUser) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        List<Order> orderList = orderMapper.storageAdminNeedDeliveryOrder(disposeUser);

        sqlSession.close();

        return orderList;
    }

    @Override
    public List<Goods> selectInStorageGoodsByName(String name) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        List<Goods> goodsList = goodsMapper.getInStorageGoodsByName(name);

        sqlSession.close();

        return goodsList;
    }

    @Override
    public List<Sorter> selectSorterByStorageId(int storageId) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        SorterMapper sorterMapper = sqlSession.getMapper(SorterMapper.class);

        List<Sorter> sorterList = sorterMapper.getSorterByStorageId(storageId);
        sqlSession.close();

        return sorterList;
    }

    @Override
    public void addDeliveryOrder(String receiveDep, int receiveUser, int sorterId,String uuid) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        deliveryOrderMapper.addDeliveryOrder(receiveDep,receiveUser,sorterId,uuid);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public DeliveryOrder selectDeliveryOrderByUUID(String uuid) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DeliveryOrderMapper deliveryOrderMapper = sqlSession.getMapper(DeliveryOrderMapper.class);

        DeliveryOrder deliveryOrder = deliveryOrderMapper.getDeliveryOrderByUUID(uuid);
        sqlSession.close();

        return deliveryOrder;

    }

    @Override
    public void letGoodsInDeliveryOrder(String goodName, int deliveryOrderId) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        goodsMapper.updateStorageGoodstoDeliveryOrder(goodName,deliveryOrderId);

        sqlSession.commit();
        sqlSession.close();

    }
}
