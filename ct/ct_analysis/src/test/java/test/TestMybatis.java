package test;

import kv.key.ContactDimension;
import kv.key.DateDimension;
import mybatis.ContactDimensionSql;
import mybatis.DateDimensionSql;
import mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import utils.JDBCUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestMybatis {

    @Test
    public void testMB(){
        SqlSession sqlSession = JDBCUtil.getSqlSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        Map<String, Object> stringObjectMap = mapper.selectBlog();
        for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
            System.out.println("key:" + stringObjectEntry.getKey() + "=====" + "Value" + stringObjectEntry.getValue());
        }
        sqlSession.close();
    }

    @Test
    public void testDate() throws SQLException {
        SqlSession sqlSession = JDBCUtil.getSqlSession();
        sqlSession.getConnection().setAutoCommit(true);
        DateDimensionSql mapper = sqlSession.getMapper(DateDimensionSql.class);

        DateDimension dateDimension = new DateDimension();

        dateDimension.setYear("1991");
        dateDimension.setMonth("12");
        dateDimension.setDay("13");

/*        mapper.insertDateDimension(dateDimension);
        System.out.println("返回的ID值为:" + (dateDimension.getId()));*/

        List<String> strings = mapper.selectID(dateDimension);
        for (String string : strings) {
            System.out.println(string);
        }

        //sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testContact() throws SQLException {
        SqlSession sqlSession = JDBCUtil.getSqlSession();
        sqlSession.getConnection().setAutoCommit(true);
        ContactDimensionSql mapper = sqlSession.getMapper(ContactDimensionSql.class);

        ContactDimension contactDimension = new ContactDimension();
        contactDimension.setName("你好");
        contactDimension.setTelephone("123548125152");

/*        mapper.insertContactDimension(contactDimension);
        System.out.println(contactDimension.getId());*/

        List<String> strings = mapper.selectID(contactDimension);
        for (String string : strings) {
            System.out.println(string);
        }

        JDBCUtil.closeSqlSession(sqlSession);
    }
}
