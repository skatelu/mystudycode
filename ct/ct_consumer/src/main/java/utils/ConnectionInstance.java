package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * 用于创建Connection链接，保证整个链接只有一个connection
 */
public class ConnectionInstance {

    private static Connection conn;
    public static synchronized Connection getConnection(Configuration conf){
        if(conn == null || conn.isClosed()){
            try {
                conn = ConnectionFactory.createConnection(conf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }


}
