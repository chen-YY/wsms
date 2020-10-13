package wsms.login;

import Utils.SqlSessionFactoryUtils;
import com.alibaba.dubbo.config.annotation.Service;
import entity.Leader;
import entity.Sorter;
import entity.StorageAdmin;
import entity.StoreAdmin;
import mapper.*;
import org.apache.ibatis.session.SqlSession;
import wsms.Login.LoginService;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    public String login(String account,String password,String accountType){

        System.out.println("执行登录");

        SqlSession sqlSession= SqlSessionFactoryUtils.openSqlSession();

       if (accountType.equals("leader")){
           LeaderMapper leaderMapper = sqlSession.getMapper(LeaderMapper.class);
           Leader leader = leaderMapper.getLeaderByAandP(account,password);

           if(leader == null){
               return "no message";
           }
           sqlSession.close();

           return leader.getId() + "|" + leader.getAccount()+ "|"+leader.getName();

       }else if(accountType.equals("storageadmin")){
           StorageAdminMapper storageAdminMapper = sqlSession.getMapper(StorageAdminMapper.class);
           StorageAdmin storageAdmin = storageAdminMapper.getStorageAdminByAandP(account,password);

           if(storageAdmin == null){
               return "no message";
           }
           sqlSession.close();

           return storageAdmin.getId() + "|" + storageAdmin.getAccount()+ "|"+storageAdmin.getName()+"|"+storageAdmin.getStorageId();

       }else if(accountType.equals("storeadmin")){
           StoreAdminMapper storeAdminMapper = sqlSession.getMapper(StoreAdminMapper.class);
           StoreAdmin storeAdmin = storeAdminMapper.getStoreAdminByAandP(account,password);

           if(storeAdmin == null){
               return "no message";
           }
           sqlSession.close();

           return storeAdmin.getId() + "|" + storeAdmin.getAccount()+ "|"+storeAdmin.getName();
       }else if (accountType.equals("sorter")){
           SorterMapper sorterMapper = sqlSession.getMapper(SorterMapper.class);
           Sorter sorter = sorterMapper.getSorterByAandP(account,password);

           if(sorter == null){
               return "no message";
           }
           sqlSession.close();

           return sorter.getId() + "|" + sorter.getAccount()+ "|"+sorter.getName();
       }

       return "no message";
    }

    @Override
    public StorageAdmin selectStorageAdminById(int id) {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StorageAdminMapper storageAdminMapper = sqlSession.getMapper(StorageAdminMapper.class);

        StorageAdmin storageAdmin = storageAdminMapper.getStorageAdminById(id);

        sqlSession.close();

        return storageAdmin;
    }


    @Override
    public StoreAdmin selectStoreAdminById(int id) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        StoreAdminMapper storeAdminMapper = sqlSession.getMapper(StoreAdminMapper.class);

        StoreAdmin storeAdmin = storeAdminMapper.getStoreAdminById(id);

        sqlSession.close();

        return storeAdmin;
    }

    public String test(){
        return "test";
    }

}
