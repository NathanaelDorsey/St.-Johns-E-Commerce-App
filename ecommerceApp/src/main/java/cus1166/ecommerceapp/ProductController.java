package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.sql.*;

import static cus1166.ecommerceapp.ManageUserController.LOGGER;

public class ProductController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadproduct();
        loadcart();
        loadreviews();
        loadsimilarproducts();
    }

    private void loadsimilarproducts() {
    }

    private void loadreviews() {
    }

    private void loadcart() {
    }


    private void loadproduct() {
        String sql = "SELECT name, price, stock_status, image_data, rating FROM Product WHERE product_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String stockStatus = rs.getString("stock_status");
                Blob imageBlob = rs.getBlob("image_data");

                productname.setText("Name: " + (name != null ? name : "N/A"));
                productprice.setText("Price: $" + (price != 0 ? String.format("%.2f", price) : "N/A"));
                stockstatus.setText("Stock Status: " + (stockStatus != null ? stockStatus : "N/A"));


                if (imageBlob != null) {
                    Image image = new Image(imageBlob.getBinaryStream());
                    productImage.setImage(image);
                } else {
                    productImage.setImage(null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading image from database: " + e.getMessage());
            productImage.setImage(null);
        }
    }


    public void switchToSearchPage(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("searchpage.fxml")));
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