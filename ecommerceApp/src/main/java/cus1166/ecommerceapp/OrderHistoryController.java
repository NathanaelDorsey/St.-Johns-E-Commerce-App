package cus1166.ecommerceapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Order;

import java.sql.*;

import java.io.IOException;
import java.util.logging.Level;

import static cus1166.ecommerceapp.HomepageControllerAdmin.LOGGER;

public class OrderHistoryController {
    
    @FXML
    private TableView<Order> orderHistoryTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TableColumn<Order, String> dateOrderedColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    public void initData() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("dateOrdered"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        String username = EccomerceApp.getUsername();
        int userId = UserManager.getUserIdFromUsername(username);
        if (username != null) {
            loadOrderHistory(userId);
        } else {
            redirectToLoginPage();
        }
    }

    private void loadOrderHistory(int userId) {
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement("SELECT order_id, status, date_ordered, total_price FROM `order` WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            orderHistoryTable.getItems().clear();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String status = rs.getString("status");
                Timestamp dateOrdered = rs.getTimestamp("date_ordered");
                double totalPrice = rs.getDouble("total_price");
                Order order = new Order(orderId, status, dateOrdered, totalPrice);
                orderHistoryTable.getItems().add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cus1166/ecommerceapp/loginpage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = findCurrentStage();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load login page", e);
        }
    }
    private Stage findCurrentStage() {
        Window window = null;
        for (Window w : Window.getWindows()) {
            if (w instanceof Stage) {
                window = w;
                break;
            }
        }
        return (Stage) window;
    }
}
