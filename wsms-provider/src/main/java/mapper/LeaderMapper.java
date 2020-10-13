package mapper;

import entity.Leader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaderMapper {
    public Leader getLeader(int id);
    public Leader getLeaderByAandP(@Param("account")String account, @Param("password")String password);
    public List<Leader> getAllLeader();
}
