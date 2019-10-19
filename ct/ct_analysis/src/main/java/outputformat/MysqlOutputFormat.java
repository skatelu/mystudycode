package outputformat;

import converter.DimensionConverter;
import converter.DimensionConverterImpl;
import kv.key.ComDimension;
import kv.value.CountDurationValue;
import mybatis.Mapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.ibatis.session.SqlSession;
import utils.JDBCUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MysqlOutputFormat extends OutputFormat<ComDimension, CountDurationValue> {

    private OutputCommitter committer = null;

    @Override
    public RecordWriter<ComDimension, CountDurationValue> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        //初始化JDBC连接器对象
        SqlSession sqlSession = null;
        Mapper mapper = null;
        sqlSession = JDBCUtil.getSqlSession();
        mapper = sqlSession.getMapper(Mapper.class);


        return new MysqlRecordWriter(sqlSession, mapper);
    }

    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
        //输出校检
    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if(committer == null){
            //根据源码配置，仿照实现的
            String name = context.getConfiguration().get(FileOutputFormat.OUTDIR);
            Path outputPath = name==null?null:new Path(name);
            committer = new FileOutputCommitter(outputPath, context);
        }
        return committer;
    }

    static class MysqlRecordWriter extends RecordWriter<ComDimension,CountDurationValue>{
        DimensionConverter dci = new DimensionConverterImpl();
        private SqlSession sqlSession = null;
        private Mapper mapper = null;
        private int count = 0;
        private final int BATCH_SIZE = 500;

        public MysqlRecordWriter(SqlSession sqlSession,Mapper mapper) {
            this.sqlSession = sqlSession;
            this.mapper = mapper;
        }

        @Override
        public void write(ComDimension key, CountDurationValue value) throws IOException, InterruptedException {
            //tb_call
            //id_date_contact,id_date_dimension,id_contact,call_sum,call_duration_sum

            //year month day 获得时间日期的id
            int id_date_dimension = dci.getDimensionID(key.getDateDimension());
            //telephone name  获得电话，姓名的id
            int id_contact = dci.getDimensionID(key.getDateDimension());

            String id_date_contact = id_date_dimension + "_" + id_contact;

            int call_sum = Integer.valueOf(value.getCallSum());
            int call_duration_sum = Integer.valueOf(value.getCallDurationSum());
            Map map = new HashMap<String, String>();
            map.put("id_date_dimension", id_date_dimension);
            map.put("id_contact", id_contact);
            map.put("id_date_contact", id_date_contact);
            map.put("call_sum", call_sum);
            map.put("id_date_contact", id_date_contact);
            //执行插入
            mapper.insertContact(map);
            count++;
            if(count >= BATCH_SIZE){
                sqlSession.commit();
                count = 0;
                sqlSession.clearCache();
            }

        }

        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            //最后一次提交在这里面
            if(sqlSession != null){
                sqlSession.commit();
            }
            JDBCUtil.closeSqlSession(sqlSession);
        }
    }
}
