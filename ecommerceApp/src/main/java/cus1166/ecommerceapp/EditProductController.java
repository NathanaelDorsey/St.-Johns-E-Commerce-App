package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditProductController {
    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> stockStatusCombo;

    private int productId; // This will be set when the dialog is initialized with data

    public void initData(int id) {
        this.productId = id;
        loadProductDetails();
    }

    private void loadProductDetails() {
        String query = "SELECT name, description, price, stock_status FROM Product WHERE product_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                descriptionField.setText(rs.getString("description"));
                priceField.setText(rs.getBigDecimal("price").toPlainString());
                stockStatusCombo.setValue(rs.getString("stock_status"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading product details: " + e.getMessage());
            // Proper error handling or user notification code should be added here
        }
    }

    @FXML
    private void handleSave() {
        String sql = "UPDATE Product SET name = ?, description = ?, price = ?, stock_status = ? WHERE product_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, descriptionField.getText());
            pstmt.setBigDecimal(3, new java.math.BigDecimal(priceField.getText()));
            pstmt.setString(4, stockStatusCombo.getSelectionModel().getSelectedItem());
            pstmt.setInt(5, productId);
            pstmt.executeUpdate();
            closeStage();
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            // Proper error handling or user notification code should be added here
        }
    }
    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}