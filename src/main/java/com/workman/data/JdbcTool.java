package com.workman.data;

import java.sql.*;
import java.util.*;

public class JdbcTool {

    //驱动程序名
    String driver = "";
    //URL指向要访问的数据库名mydata
    String url = "";
    //MySQL配置时的用户名
    String user = "";
    //MySQL配置时的密码
    String password = "";

    public JdbcTool(Properties properties) {
        this.driver = properties.getProperty("jdbc.driver");
        this.url = properties.getProperty("jdbc.url");
        this.user = properties.getProperty("jdbc.username");
        this.password = properties.getProperty("jdbc.password");
    }

    public List getData(String sql) throws SQLException {
        //声明Connection对象
        Connection con = null;

        ResultSet resultSet = null;
        List list = null;
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            //3.ResultSet类，用来存放获取的结果集！！
            resultSet = statement.executeQuery(sql);

            list = convertListStr(resultSet);

        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("数据库数据成功获取！！");
            resultSet.close();
            con.close();
        }

        return list;
    }

    private static List convertList(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

    private static List convertListStr(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        int j = 1;
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(j, rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
            j++;
        }
        return list;
    }
}
