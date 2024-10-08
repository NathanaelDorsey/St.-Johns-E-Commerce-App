package cus1166.ecommerceapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import java.util.logging.Logger;

import static cus1166.ecommerceapp.HomepageControllerAdmin.LOGGER;

public class CategoryPageController {

    @FXML
    private Hyperlink clothingtab;

    @FXML
    private Hyperlink gifttab;

    @FXML
    private Hyperlink electronictab;

    @FXML
    private Hyperlink schooltab;

    @FXML
    private Hyperlink alumnitab;

    @FXML
    private Hyperlink graduationtab;

    @FXML
    private Hyperlink dormtab;

    @FXML
    private Hyperlink healthtab;

    @FXML
    private Hyperlink bookstab;

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
    private HBox switchToUserPage;

    @FXML
    private Button userProfileIcon;

    @FXML
    private HBox switchToCartPage;

    @FXML
    private Button cart;

    @FXML
    private ScrollPane productContainer;

    @FXML
    private void initialize() {
        productContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @FXML
    void loadProductsForCategory(ActionEvent event) {
        Hyperlink selectedCategory = (Hyperlink) event.getSource();
        String category = selectedCategory.getText();
        productContainer.setContent(null);
        List<Product> products = getProductsForCategory(category);
        displayProducts(products);
    }

    private void displayProducts(List<Product> products) {
        VBox productBox = new VBox();
        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cus1166/ecommerceapp/productcard.fxml"));
                Parent productCard = loader.load();
                ProductController controller = loader.getController();
                controller.initData(product.getProductId());
                productBox.getChildren().add(productCard);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error loading product card", e);
            }
        }
        productContainer.setContent(productBox);
    }

    private List<Product> getProductsForCategory(String category) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.product_id, p.name, p.description, p.price " +
                "FROM product p " +
                "INNER JOIN product_category pc ON p.product_id = pc.product_id " +
                "INNER JOIN category c ON pc.category_id = c.category_id " +
                "WHERE c.category_name = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Product product = new Product(productId, name, price, null, description, null, 0, null);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error retrieving products for category", e);
        }
        return products;
    }


    public void switchToSearchPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Productsearchusers.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHomepage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/homepage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserPage (ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/usersettings.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.info("Switched to User page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Cart page", e);
        }
    }


    public void switchToCartPage (ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/cartpageusers.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.info("Switched to Cart page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Cart page", e);
        }
    }
    public void switchToCategoriesPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/categoriespageusers.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Categories page", e);
        }
    }
}
