package mapper;

import entity.Shelf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShelfMapper {
    List<Shelf> getShelfByStorageId(int id);
    void addShelf(@Param("storageId")int storageId,@Param("area")String area);
}
