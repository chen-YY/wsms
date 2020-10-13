package wsms.homepage;

import Utils.SqlSessionFactoryUtils;
import com.alibaba.dubbo.config.annotation.Service;
import entity.*;
import mapper.*;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaderhomeServiceImpl implements LeaderhomeService {

    @Override
    public List<Kind> selectAllKind() {
        //获取所有的商品种类

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        KindMappper kindMappper = sqlSession.getMapper(KindMappper.class);
        List<Kind> kingList = kindMappper.getAllKind();

        sqlSession.close();

        return kingList;
    }

    @Override
    public Map<String, List<GoodName>> selectAllKindOfGoods() {

        Map<String,List<GoodName>> everyKindOfGoodName = new HashMap<>();

        //获取每个种类下的商品
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodNameMapper goodNameMapper = sqlSession.getMapper(GoodNameMapper.class);

        List<Kind> kindList = selectAllKind();
        for (Kind k : kindList){
            //循环查询
            List<GoodName> goodNames = goodNameMapper.getGoodNameByKindId(k.getId());
            everyKindOfGoodName.put(k.getKind(),goodNames);
        }
        sqlSession.close();

        return everyKindOfGoodName;
    }

    @Override
    public List<Storage> selectAllStorage() {
        //获取所有仓库信息,返货仓库信息列表

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        List<Storage> storageList = storageMapper.getAllStorage();

        if(storageList.size() == 0){
            return null;
        }else{
            //有结果则返回查询结果，没有结果返回空值
            sqlSession.close();
            return storageList;
        }
    }

    @Override
    public void addNewStorage(String name, String location) {
        //添加一条新的仓库信息

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StorageMapper storageMapper = sqlSession.getMapper(StorageMapper.class);

        storageMapper.addNewStorage(name,location);

        //插入数据需要 commit
        sqlSession.commit();

        sqlSession.close();

    }

    @Override
    public List<Kind> selectKindByName(String kind) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        KindMappper kindMappper = sqlSession.getMapper(KindMappper.class);

        List<Kind> kindList = kindMappper.getKindByName(kind);

        sqlSession.close();
        return kindList;
    }

    @Override
    public void addGoodName(int id, String goodName) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodNameMapper goodNameMapper = sqlSession.getMapper(GoodNameMapper.class);

        goodNameMapper.addGoodName(id,goodName);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void addKind(String name) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        KindMappper kindMappper = sqlSession.getMapper(KindMappper.class);

        kindMappper.addKind(name);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public List<Order> selectNoApproveOrder() {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        List<Order> noApproveOrderList = orderMapper.getNoApproveOrder();

        sqlSession.close();


        return noApproveOrderList;
    }

    @Override
    public Map<String, Map<String, String>> getOrderFaqirenAndChulirenInfo(List<Order> list) {

        /*
        这个函数用于匹配每一个未审批订单中的发起人以及处理人信息
         */

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();

        StorageAdminMapper storageAdminMapper = sqlSession.getMapper(StorageAdminMapper.class);
        StoreAdminMapper storeAdminMapper = sqlSession.getMapper(StoreAdminMapper.class);


        Map<String,Map<String,String>> info = new HashMap<String,Map<String,String>>();

        for (Order o : list){
            Map<String,String> FandC = new HashMap<String,String>();

            if(o.getSponsorDep().equals("A")){
                StorageAdmin storageAdmin = storageAdminMapper.getStorageAdminById(o.getSponsorUser());
                FandC.put("sponsorUser",storageAdmin.getName());
            }else{
                StoreAdmin storeAdmin = storeAdminMapper.getStoreAdminById(o.getSponsorUser());
                FandC.put("sponsorUser",storeAdmin.getName());
            }

            if(o.getDisposeDep().equals("A")){
                StorageAdmin storageAdmin = storageAdminMapper.getStorageAdminById(o.getDisposeUser());
                FandC.put("disposeUser",storageAdmin.getName());
            }else{
                FandC.put("disposeUser","采购部门");
            }

            info.put(Integer.toString(o.getId()),FandC);
        }

        sqlSession.close();

        return info;
    }

    @Override
    public void updateStatus(int id, String status) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        orderMapper.updateStatus(id,status);

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public void updateRemark(int id, String remark) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        orderMapper.updateRemark(id,remark);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Map<String, List<Goods>> selectNoApproveOrderGoodsByOrderId(List<Order> o) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);

        Map<String,List<Goods>> goods = new HashMap<String,List<Goods>>();

        for (Order order : o){

            List<Goods> goodsList = goodsMapper.getOrderGoodsByOrderId(order.getId());

            goods.put(String.valueOf(order.getId()),goodsList);

        }

        sqlSession.close();

        return goods;
    }
}
