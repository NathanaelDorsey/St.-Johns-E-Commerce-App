package cus1166.ecommerceapp;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DBUtils {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/eccomercedb";
    public static final String DB_USER = "ForClass";
    public static final String DB_PASS = "JustForClassss";
    Connection connection = null;
    public static Connection ConnectDb(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            JOptionPane.showMessageDialog(null, "Connection Established");
            return connection;
        }
    }catch(Exception e){
    JOptionPane.showMessageDialog(null, e);
    }

        return null;
    }

}
