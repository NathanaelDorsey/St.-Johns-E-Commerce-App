package cus1166.ecommerceapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBUtils {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;

    static {
        // Load database properties
        try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            prop.load(input);
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPass = prop.getProperty("db.pass");
        } catch (Exception e) {
            throw new RuntimeException("Error loading database configuration", e);
        }
    }

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
