package com.yjl.esper.io_mysql;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnectMysql {
    static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称
    static final String USER = "root";
    static final String PASS = "123456";

    static Connection conn = null;
    static Statement stat = null;

    static {

        // 注册驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 创建链接
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        // 执行查询
        stat = (Statement) conn.createStatement();
        String sql = "SELECT * FROM account";
        ResultSet rs = stat.executeQuery(sql);
        // 输出查询结果
        while(rs.next()){
            System.out.print(rs.getInt("id")+",");
            System.out.print(rs.getString("name")+",");
            System.out.print("\n");
        }
        // 关闭
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
