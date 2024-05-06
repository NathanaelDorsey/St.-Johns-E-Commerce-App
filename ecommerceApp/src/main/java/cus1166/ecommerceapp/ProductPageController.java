package cus1166.ecommerceapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Category;
import models.Product;
import models.ShoppingCart;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static cus1166.ecommerceapp.HomepageControllerAdmin.LOGGER;

public class ProductPageController {

    private Product productData;

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
    ShoppingCart shoppingCart = ShoppingCart.getInstance();
    public void addToCart(Product product) {
        shoppingCart.addToCart(product, 1);
    }
    public void setProductData(Product product) {
        if (product != null) {
            this.productData = product;
            updateUIWithProductDetails();
        } else {
            System.out.println("Product data is null.");

        }
    }

    private void updateUIWithProductDetails() {
        productname.setText(productData.getProductName());
        productprice.setText("$" + String.format("%.2f", productData.getPrice()));
        productrating.setText(String.valueOf(productData.getRating()));
        productdescription.setText(productData.getDescription());
        displayCategories();
        loadImage();
    }
    private void displayCategories() {
        if (productData.getCategories() != null) {
            productcategory.setText(productData.getCategories().stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(", ")));
        } else {
            productcategory.setText("No categories available");
        }
    }

    private void loadImage() {
        Blob imageBlob = productData.getImageBlob();
        if (imageBlob != null) {
            try (InputStream inputStream = imageBlob.getBinaryStream()) {
                Image image = new Image(inputStream);
                productImage.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
                e.printStackTrace();
                productImage.setImage(null);
            }
        } else {
            System.out.println("No image data available.");
            productImage.setImage(null);
        }
    }
    @FXML
    private void addToCartClicked(ActionEvent event) {
        addToCart(productData);
        int amount = shoppingCart.getAmountInCart(productData);
        amountincart.setText(String.valueOf(amount));
        totalcartamount.setText("$" + String.format("%.2f", shoppingCart.getTotalCartAmount()));
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

    public void switchToUserPage (javafx.event.ActionEvent event){
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


    public void switchToCartPage (javafx.event.ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/cartpage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.info("Switched to Cart page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Cart page", e);
        }
    }


}
