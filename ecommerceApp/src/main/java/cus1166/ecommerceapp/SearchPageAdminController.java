package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static cus1166.ecommerceapp.ManageUserController.LOGGER;

public class SearchPageAdminController {
    @FXML
    private Hyperlink home;

    @FXML
    private Hyperlink categories;

    @FXML
    private TextField searchbar;

    @FXML
    private Button searchLogo;

    @FXML
    private ImageView stjohnsLogo;

    @FXML
    private HBox SPACER;

    @FXML
    private Hyperlink manageusers;

    @FXML
    private Hyperlink addproducts;

    @FXML
    private Hyperlink manageproducts;

    @FXML
    private HBox switchToUserPage;

    @FXML
    private Button userProfileIcon;

    @FXML
    private HBox switchToCartPage;

    @FXML
    private Button cart;

    @FXML
    private ScrollPane productcontainer;

    private List<Product> allProducts = new ArrayList<>();

    public void initialize() {
        loadProducts();
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAndDisplayProducts(newValue);
        });
    }

    private void filterAndDisplayProducts(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            displayProducts(allProducts);
        } else {
            List<Product> filteredProducts = filterProducts(searchText);
            displayProducts(filteredProducts);
        }
    }

    private List<Product> filterProducts(String searchText) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getProductName().toLowerCase().contains(searchText.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
    private void loadProducts() {
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM product");
             ResultSet resultSet = statement.executeQuery()) {
            allProducts.clear();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product.StockStatus stockStatus = Product.StockStatus.valueOf(resultSet.getString("stock_status"));
                String description = resultSet.getString("description");
                Product product = new Product(productId, productName, price, stockStatus, description, null, 0, null);
                allProducts.add(product);
            }
            displayProducts(allProducts);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading products", e);
        }
    }
    private void displayProducts(List<Product> products) {
        productcontainer.setContent(null);
        VBox productBox = new VBox();
        productcontainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cus1166/ecommerceapp/productcard.fxml"));
                Parent productCard = loader.load();
                ProductController controller = loader.getController();
                controller.initData(product.getProductId());
                productBox.getChildren().add(productCard);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error loading product card", e);
            }
        }
        productcontainer.setContent(productBox);
    }

    public void switchToSearchPage(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Productsearch.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToManageUsersPage(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/manageusers.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            HomepageControllerAdmin.LOGGER.info("Switched to Manage Users page successfully.");
        } catch (Exception e) {
            HomepageControllerAdmin.LOGGER.log(Level.SEVERE, "Failed to switch to Manage Users page", e);
        }
    }

    public void switchToAddProductsPage(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/addproducts.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            HomepageControllerAdmin.LOGGER.info("Switched to Add Products page successfully.");
        } catch (Exception e) {
            HomepageControllerAdmin.LOGGER.log(Level.SEVERE, "Failed to switch to Add Products page", e);
        }
    }

    public void switchToManageProductsPage(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/manageproducts.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            HomepageControllerAdmin.LOGGER.info("Switched to Manage Products page successfully.");
        } catch (Exception e) {
            HomepageControllerAdmin.LOGGER.log(Level.SEVERE, "Failed to switch to Manage Products page", e);
        }
    }
    public void switchToHomepage(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Admins/homepageadmin.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserPage (javafx.event.ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/usersettings.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            HomepageControllerAdmin.LOGGER.info("Switched to User page successfully.");
        } catch (Exception e) {
            HomepageControllerAdmin.LOGGER.log(Level.SEVERE, "Failed to switch to Cart page", e);
        }
    }


    public void switchToCartPage (javafx.event.ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/cartpage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            HomepageControllerAdmin.LOGGER.info("Switched to Cart page successfully.");
        } catch (Exception e) {
            HomepageControllerAdmin.LOGGER.log(Level.SEVERE, "Failed to switch to Cart page", e);
        }
    }
    public void switchToCategoriesPage(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/categoriespage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Categories page", e);
        }
    }
}
