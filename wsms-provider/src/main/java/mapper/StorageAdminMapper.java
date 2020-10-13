package mapper;

import entity.Storage;
import entity.StorageAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageAdminMapper {
    public StorageAdmin getStorageAdminByAandP(@Param("account")String account, @Param("password")String password);
    public StorageAdmin getStorageAdminById(int id);
    public List<StorageAdmin> getAllStorageAdmin();
}
