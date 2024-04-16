package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditCategoryController {
    @FXML private TextField categoryNameField;

    private int categoryId; // This will be set when the dialog is initialized with data

    public void initData(int id) {
        this.categoryId = id;
        loadCategoryDetails();
    }

    private void loadCategoryDetails() {
        String query = "SELECT category_name FROM Category WHERE category_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                categoryNameField.setText(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading category details: " + e.getMessage());
            // Proper error handling or user notification code should be added here
        }
    }

    @FXML
    private void handleSave() {
        String sql = "UPDATE Category SET category_name = ? WHERE category_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryNameField.getText());
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();
            closeStage();
        } catch (Exception e) {
            System.err.println("Error updating category: " + e.getMessage());
            // Proper error handling or user notification code should be added here
        }
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) categoryNameField.getScene().getWindow();
        stage.close();
    }
}
