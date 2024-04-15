package cus1166.ecommerceapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomepageControllerAdmin implements Initializable {

    @FXML
    private Hyperlink home;

    @FXML
    private Hyperlink categories;

    @FXML
    private TextField searchbar;

    @FXML
    private ImageView searchLogo;

    @FXML
    private ImageView stjohnsLogo;

    @FXML
    private HBox SPACER;

    @FXML
    private Hyperlink manageusers;

    @FXML
    private Hyperlink addproducts;

    @FXML
    private Hyperlink removeproducts;

    @FXML
    private ImageView userProfileIcon;

    @FXML
    private ImageView cart;

    @FXML
    private HBox cardLayout;

    private static final Logger LOGGER = Logger.getLogger(HomepageControllerAdmin.class.getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Product> recentlyAdded = recentlyAdded();  // Ensure this method is properly fetching products
            for (Product product : recentlyAdded) {
                URL fxmlUrl = getClass().getResource("/cus1166/ecommerceapp/productcard.fxml");
                if (fxmlUrl == null) {
                    throw new RuntimeException("FXML resource not found: productcard.fxml");
                }
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                HBox box = loader.load();
                ProductController productController = loader.getController();
                productController.setData(product);

                // Ensure the image path is correct
                URL imageUrl = getClass().getResource("/" + product.getImageSrc());
                if (imageUrl == null) {
                    LOGGER.log(Level.WARNING, "Image resource not found: {0}", product.getImageSrc());
                    continue;  // Skip this product or handle the error appropriately
                }
                Image image = new Image(imageUrl.toExternalForm());
                productController.getProductImage().setImage(image);
                cardLayout.getChildren().add(box);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception when loading product cards", e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load UI components, please check the application setup.");
            alert.showAndWait();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred", e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }
    private List<Product> recentlyAdded() {
        List<Product> products = new ArrayList<>();
        // Ensure your SQL query correctly reflects the actual database schema
        String sql = "SELECT product_id, name, price, stock_status, description, image_url, rating FROM product ORDER BY product_id DESC LIMIT 10";

        try (Connection conn = DBUtils.ConnectDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Product.StockStatus stockStatus = Product.StockStatus.valueOf(rs.getString("stock_status").toUpperCase().replace(" ", "_"));
                String description = rs.getString("description");
                String imageSrc = rs.getString("image_url"); // Correctly mapping the column name
                double rating = rs.getDouble("rating");

                // Print the retrieved product details
                System.out.println("Product ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Stock Status: " + stockStatus);
                System.out.println("Description: " + description);
                System.out.println("Image URL: " + imageSrc);
                System.out.println("Rating: " + rating);

                // Assuming the Product constructor takes these parameters in this order
                products.add(new Product(id, name, price, stockStatus, description, imageSrc, rating));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching recently added products: " + e.getMessage());
        }
        return products;
    }
    public void switchToAddProductsPage(ActionEvent event) throws IOException {
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

}
