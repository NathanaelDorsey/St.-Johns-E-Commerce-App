package cus1166.ecommerceapp;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cus1166.ecommerceapp.HomepageControllerAdmin.LOGGER;

public class HomepageController implements Initializable {
    @FXML
    private Hyperlink home;

    @FXML
    private Hyperlink categories;

    @FXML
    private Button searchLogo;

    @FXML
    private HBox SPACER1;

    @FXML
    private ImageView stjohnsLogo;

    @FXML
    private HBox SPACER;

    @FXML
    private HBox switchToUserPage;

    @FXML
    private Button userProfileIcon;

    @FXML
    private Button cart;

    @FXML
    private HBox cardLayout;
    @FXML
    private ScrollPane recentlyAddedScrollPane;

    protected static final Logger LOGGER = Logger.getLogger(HomepageController.class.getName());


    private void loadProducts() {
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM product");
             ResultSet resultSet = statement.executeQuery()) {

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product.StockStatus stockStatus = Product.StockStatus.valueOf(resultSet.getString("stock_status"));
                String description = resultSet.getString("description");
                Product product = new Product(productId, productName, price, stockStatus, description, null, 0, null);
                products.add(product);
            }

            displayProducts(products);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading products", e);
        }
    }

    private void displayProducts(List<Product> products) {
        recentlyAddedScrollPane.setContent(null);
        HBox productBox = new HBox();
        recentlyAddedScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
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

        recentlyAddedScrollPane.setContent(productBox);
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProducts();
    }
}