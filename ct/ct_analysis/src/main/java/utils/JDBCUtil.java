package utils;


import mybatis.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

/**
 * mybatis 链接数据库
 */
public class JDBCUtil {
    public static SqlSessionFactory sql = null;
    static{
//        InputStream in = ClassLoader.getSystemResource("./mybatis-config.xml");
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sql = new SqlSessionFactoryBuilder().build(reader);
    }

    /**
     * 获得mybatis 链接
     * @return
     */
    public static SqlSession getSqlSession(){
        return sql.openSession(ExecutorType.BATCH,false);//设置为批量且不自动提交
    }

    public static void closeSqlSession(SqlSession sqlSession){
        if(sqlSession != null){
            sqlSession.close();
        }
    }
}
