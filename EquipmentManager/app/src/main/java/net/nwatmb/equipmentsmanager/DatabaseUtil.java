package net.nwatmb.equipmentsmanager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.ConnectionProperties;



/**
 * Created by NWATMB on 2015/8/28.
 */
public class DatabaseUtil {
    public static Connection openConnection(String url, String user, String passwd)
    {
        Connection connection = null;
        // 1、指定数据库驱动组件
        // 2、连接数据库
                        /*
                        本地数据库位置配置为127.0.0.1，
                        端口号可在本地CMD下进入MySQL后运行status查看
                        */
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME).newInstance();
            connection = DriverManager.getConnection(url, user, passwd);
        } catch (ClassNotFoundException e) {
            connection = null;
        } catch (SQLException e) {
            connection = null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void query(Connection conn, String sql) {

        if (conn == null) {       //未连接数据库
            return;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("ID");
                int nameColumnIndex = result.findColumn("Name");
//                System.out.println("id\t\t" + "name");
                while (!result.isAfterLast()) {
                    System.out.print(result.getString(idColumnIndex) + "\t\t");
                    System.out.println(result.getString(nameColumnIndex));
                    result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
    }
}
