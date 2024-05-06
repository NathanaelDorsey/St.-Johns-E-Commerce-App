package cus1166.ecommerceapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import static cus1166.ecommerceapp.HomepageControllerAdmin.LOGGER;

public class UserManager {
    public static int getUserIdFromUsername(String username) {
        int userId = -1;
        String query = "SELECT user_id FROM user WHERE username = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve user ID", e);
        }
        return userId;
    }
    public static String getAddressFromDatabase(String username) {
        String address = null;
        int userId = getUserIdFromUsername(username); // Assuming you have a method to get user ID
        if (userId != -1) {
            String query = "SELECT address FROM user WHERE user_id = ?";
            try (Connection conn = DBUtils.ConnectDb();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        address = rs.getString("address");
                    }
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to retrieve address from the database", e);
            }
        }
        return address;
    }
    public static boolean updateAddress(String username, String newAddress) {
        String updateQuery = "UPDATE user SET address = ? WHERE username = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, newAddress);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update address", e);
            return false;
        }
    }
}
