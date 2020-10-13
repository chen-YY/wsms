package wsms;

import Utils.SqlSessionFactoryUtils;
import com.alibaba.dubbo.config.annotation.Service;
import entity.Leader;
import mapper.LeaderMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@Service
public class TestImpl implements Test {

    public String sayHello(String name){
        System.out.println("执行Test的实现");

        SqlSession sqlSession= SqlSessionFactoryUtils.openSqlSession();
        LeaderMapper LeaderMapper=sqlSession.getMapper(LeaderMapper.class);
        String sqlInfo = LeaderMapper.getLeader(1).toString();

        Leader leader = LeaderMapper.getLeaderByAandP("1399636557@qq.com","1157857412");

        return "hello " + name + sqlInfo + "|" + leader.getName() + leader.getPassword();
    }
    public void selectAllLeader(){
        SqlSession sqlSession= SqlSessionFactoryUtils.openSqlSession();
        LeaderMapper leaderMapper=sqlSession.getMapper(LeaderMapper.class);

        List<Leader> leaderList = leaderMapper.getAllLeader();

        for (Leader l : leaderList){
            System.out.println(l.getAccount()+ "|"+l.getName());
        }

    }

}
