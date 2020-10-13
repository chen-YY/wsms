package mapper;

import entity.Storage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageMapper {
    public List<Storage> getAllStorage();
    public void addNewStorage(@Param("name")String name,@Param("location")String location);
    public Storage getStorageById(int id);
}
