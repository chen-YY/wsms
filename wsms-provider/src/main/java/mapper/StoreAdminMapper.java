package mapper;

import entity.StoreAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreAdminMapper {
    public StoreAdmin getStoreAdminByAandP(@Param("account")String account, @Param("password")String password);
    public StoreAdmin getStoreAdminById(int id);
    public List<StoreAdmin> getAllStorageAdmin();

}
