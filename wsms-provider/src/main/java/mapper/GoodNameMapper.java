package mapper;

import entity.GoodName;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodNameMapper {
    public List<GoodName> getGoodNameByKindId(int kingId);
    public void addGoodName(@Param("kindId")int kindId,@Param("goodName")String goodName);
    public List<GoodName> getAllGoodName();
}
