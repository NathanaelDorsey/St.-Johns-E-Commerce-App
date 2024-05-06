package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Product;

import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;

import java.util.logging.Level;
import java.sql.*;

import static cus1166.ecommerceapp.ManageUserController.LOGGER;

public class ProductController {

    @FXML
    private Hyperlink home;

    @FXML
    private Hyperlink categories;

    @FXML
    private TextField searchbar;

    @FXML
    private Button searchLogo;

    @FXML
    private HBox SPACER1;

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
    private Button userProfileIcon;

    @FXML
    private Button cart;

    @FXML
    private HBox box;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productname;

    @FXML
    private Label productcategory;

    @FXML
    private Label productprice;

    @FXML
    private Label stockstatus;

    @FXML
    private Label productrating;

    @FXML
    private ImageView similarproductimage;

    @FXML
    private Label similarproductname;

    @FXML
    private Label similarproductprice;

    @FXML
    private Label similarproductrating;

    @FXML
    private Label reviewerusername;

    @FXML
    private Label reviewerrating;

    @FXML
    private Label reviewerratingdate;

    @FXML
    private Label reviewerratingdescription;

    @FXML
    private TextArea productdescription;

    @FXML
    private Button addtocartbutton;

    @FXML
    private Label amountincart;

    @FXML
    private Label totalcartamount;

    public Product productData;

    public void initData(int productId) {
        loadProduct(productId);
        loadCart();
        loadReviews();
        loadSimilarProducts();
    }

    private void loadSimilarProducts() {
    }
    private void loadReviews() {
    }
    private void loadCart() {
    }
    private void loadProduct(int productId) {
        String sql = "SELECT name, price, stock_status, image_data, rating FROM Product WHERE product_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String stockStatusStr = rs.getString("stock_status");
                Product.StockStatus stockStatus = Product.StockStatus.valueOf(stockStatusStr);
                Blob imageBlob = rs.getBlob("image_data");
                double rating = rs.getDouble("rating");
                Product loadedProduct = new Product();
                loadedProduct.setName(name);
                loadedProduct.setPrice(price);
                loadedProduct.setStockStatus(stockStatus);
                loadedProduct.setImageBlob(imageBlob);
                loadedProduct.setRating(rating);
                this.productData = loadedProduct;
                productname.setText(name != null ? name : "N/A");
                productprice.setText(String.format("$%.2f", price));
                stockstatus.setText(stockStatus != null ? String.valueOf(stockStatus) : "N/A");
                productrating.setText(String.format("%s", rating));
                if (imageBlob != null) {
                    try (InputStream inputStream = imageBlob.getBinaryStream()) {
                        Image image = new Image(inputStream);
                        productImage.setImage(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                        productImage.setImage(null);
                    }
                } else {
                    productImage.setImage(null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading product from database: " + e.getMessage());
            productData = null;
        }
    }
    public void handleImageClick(MouseEvent event) {
        if (productData == null) {
            System.out.println("No product data available. Please ensure the product is loaded correctly.");
            showAlert("Data Not Available", "Product data is not available. Please try again.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cus1166/ecommerceapp/productpage.fxml"));
            Parent root = loader.load();
            ProductPageController productPageController = loader.getController();
            productPageController.setProductData(productData);

            Stage stage = new Stage();
            stage.setTitle(productData.getProductName());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void switchToSearchPage(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/Productsearch.fxml")));
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
            LOGGER.info("Switched to Manage Users page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Manage Users page", e);
        }
    }
    public void switchToAddProductsPage(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/addproducts.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.info("Switched to Add Products page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Add Products page", e);
        }
    }
    public void switchToManageProductsPage(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/manageproducts.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.info("Switched to Manage Products page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Manage Products page", e);
        }
    }
    public void switchToHomepage(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Admins/homepageadmin.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}