package Utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SqlSessionFactoryUtils implements Serializable {
    //凡是在dubbo中使用到的类都要做序列化处理  Serializable
    private final static Class<SqlSessionFactoryUtils> lock=SqlSessionFactoryUtils.class;

    private static SqlSessionFactory sqlSessionFactory=null;

    private SqlSessionFactoryUtils(){};

    public static SqlSessionFactory getSqlSessionFactory(){
        //判断sqlSessionFactory是否为空，不为空直接返回
        synchronized (lock){
            if (sqlSessionFactory!=null){
                return sqlSessionFactory;
            }

            //读取mybatis-config.xml文件
            String resource="mybatis-config.xml";
            InputStream inputStream;

            try {
                inputStream= Resources.getResourceAsStream(resource);
                //初始化mybatis,创建SqlSessionFactory类的实例
                sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }

            return sqlSessionFactory;
        }
    }

    public static SqlSession openSqlSession(){

        //使用此函数直接获取SqlSession

        if (sqlSessionFactory==null){
            getSqlSessionFactory();
        }
        return  sqlSessionFactory.openSession();
    }
}
