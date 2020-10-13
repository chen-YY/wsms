package start;

import Utils.SqlSessionFactoryUtils;
import mapper.LeaderMapper;
import org.apache.ibatis.session.SqlSession;

public class sqlTest {
    public static void main(String[] args) {
        SqlSession sqlSession= SqlSessionFactoryUtils.openSqlSession();
        LeaderMapper LeaderMapper=sqlSession.getMapper(LeaderMapper.class);
        System.out.println(LeaderMapper.getLeader(1).toString());
    }
}
