/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drugstoreController;
import java.sql.*;

/**
 *
 * @author User
 */
public class ConnectionDB {
    public Connection getConnection(){
       Connection conn = null;
       try{
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           String url = "jdbc:sqlserver://DESKTOP-31DIV54\\SQLEXPRESS:1433;"+""
                   + "user = sa; password = 123;databaseName=QUANLYTHUOC";
           conn = DriverManager.getConnection(url);
           if(conn != null){
               System.out.println("Kết nối thành công!");
           }
       } catch (Exception ex){
           System.out.println(ex.toString());
       }
       return conn;
    }
}
