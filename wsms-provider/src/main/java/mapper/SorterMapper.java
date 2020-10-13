package mapper;

import entity.Sorter;
import entity.Storage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SorterMapper {
    public Sorter getSorterByAandP(@Param("account")String account,@Param("password")String password);
    public List<Sorter> getSorterByStorageId(int storageId);
}
