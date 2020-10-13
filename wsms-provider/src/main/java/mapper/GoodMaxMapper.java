package mapper;

import entity.GoodMax;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodMaxMapper {
    public GoodMax getGoodMaxByKindAndGood(@Param("kind")String kind,@Param("good")String good);
    public void addNewInfo(@Param("storageId")int storageId,@Param("kind")String kind,@Param("goodName")String goodName);
    public List<GoodMax> getAllByStorageId(int storageId);
    public void updateGoodMaxInfo(@Param("storageId")int storageId,@Param("kind")String kind,@Param("goodName")String goodName,@Param("maxNumber")String manNumber,@Param("maxDay")String maxDay);

}
