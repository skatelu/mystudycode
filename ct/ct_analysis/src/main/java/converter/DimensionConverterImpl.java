package converter;

import kv.base.BaseDimension;
import kv.key.ContactDimension;
import kv.key.DateDimension;
import mybatis.ContactDimensionSql;
import mybatis.DateDimensionSql;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JDBCUtil;
import utils.LRUCache;

import java.util.List;

/**
 *  1、根据传入的维度数据，得到该数据对应的在表中的主键id
 *  ** 做内存缓存，LRUCache 算法
 *      分支
 *          --缓存中有数据 -> 直接返回id
 *          --缓存中无数据 ->
 *              ** 查询Mysql
 *                  分支：
 *                      --mysql中有这条数据 -> 直接返回id -> 将本次读取到的id缓存到内存中
 *                      --Mysql中没有数据 -> 插入该条数据，并且获取本条数据id -> 插入到内存中
 */
public class DimensionConverterImpl implements DimensionConverter {
    //Logger
    private static final Logger log = LoggerFactory.getLogger(DimensionConverterImpl.class);
    //对象线程化  用于每个线程管理自己的jdbc连接器
    private ThreadLocal<SqlSession> threadLocalConnection = new ThreadLocal<SqlSession>();
    //构建内存缓存对象 暂时定位3KB<标志 , id>
    private LRUCache<String,Integer> lruCache = new LRUCache(3000);

    //释放资源 此处单独使用mybatis 需要关闭链接
    public DimensionConverterImpl(){
        //jvm关闭时释放资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> JDBCUtil.closeSqlSession(threadLocalConnection.get())));
    }

    @Override
    public int getDimensionID(BaseDimension dimension) {
        //1、根据传入的维度对象，获取对应的主键id，先从LRUCache中获取
        //时间维度：data_dimension_year_month_day,10
        //联系人维度：contact_dimension_telephone,12
        //生成缓存建
        String cacheKey = genCacheKey(dimension);
        //尝试获取缓存的id
        if(lruCache.containsKey(cacheKey)){
            return lruCache.get(cacheKey);
        }
        // 没有得到缓存id，需要执行select操作,去数据库中查询
        //获得该执行县城中的sqlSession
        SqlSession sqlSession = getSqlSession();
        int id = -1;
        synchronized (this){
            id = execSelectSql(sqlSession,dimension);
            JDBCUtil.closeSqlSession(sqlSession);
        }

        // 将查询到的id插入到缓存中
        lruCache.put(cacheKey, id);
        return id;
    }

    /**
     * 执行查询语句
     * 插入的时候返回插入数据自增的id
     * @param sqlSession
     * @param dimension
     * @return
     */
    private int execSelectSql(SqlSession sqlSession, BaseDimension dimension) {
        if (dimension instanceof DateDimension) {
            DateDimensionSql mapperDate = sqlSession.getMapper(DateDimensionSql.class);
            List<String> dateID = mapperDate.selectID(dimension);
            if(dateID.size()!=0 && !dateID.isEmpty()){
                for (String s : dateID) {
                    return Integer.valueOf(s);
                }
            }
            //执行插入
            mapperDate.insertDateDimension(dimension);
            return Integer.valueOf(((DateDimension) dimension).getId());

        } else if (dimension instanceof ContactDimension) {
            ContactDimensionSql mapperContact = sqlSession.getMapper(ContactDimensionSql.class);
            List<String> contactID = mapperContact.selectID(dimension);
            if(contactID.size()!=0 && !contactID.isEmpty()){
                for (String s : contactID) {
                    return Integer.valueOf(s);
                }
            }
            //执行插入
            mapperContact.insertContactDimension(dimension);
            return Integer.valueOf(((ContactDimension) dimension).getId());
        } else {
            throw new RuntimeException("没有匹配到对应维度信息.");
        }
    }


    /**
     * 得到当前线程维护的SqlSession对象
     * @return
     */
    private SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        sqlSession = threadLocalConnection.get();
        if(sqlSession == null){
            sqlSession = JDBCUtil.getSqlSession();
            threadLocalConnection.set(sqlSession);
        }

        return sqlSession;
    }


    /**
     * 根据维度信息得到维度对应的缓存建
     * @param dimension
     * @return
     */
    public String genCacheKey(BaseDimension dimension){
        StringBuilder sb = new StringBuilder();

        if(dimension instanceof DateDimension){
            DateDimension dateDimension = (DateDimension) dimension;
            sb.append("date_dimension")
                    .append(dateDimension.getYear())
                    .append(dateDimension.getMonth())
                    .append(dateDimension.getDay());
        }else if(dimension instanceof ContactDimension){
            ContactDimension contactDimension = (ContactDimension) dimension;
            sb.append("contact_dimension").append(contactDimension.getTelephone());
        }
        return sb.toString();
    }
}

