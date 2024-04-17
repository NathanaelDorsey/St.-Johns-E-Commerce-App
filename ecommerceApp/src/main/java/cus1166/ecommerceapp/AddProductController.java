package cus1166.ecommerceapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Product;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static cus1166.ecommerceapp.ProductController.LOGGER;


public class AddProductController implements Initializable {
    @FXML
    private HBox SPACER;

    @FXML
    private Button addcategory;

    @FXML
    private Hyperlink addproducts;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button cart;

    @FXML
    private Hyperlink categories;

    @FXML
    private Hyperlink home;

    @FXML
    private Button listitem;

    @FXML
    private Hyperlink manageproducts;

    @FXML
    private Hyperlink manageusers;

    @FXML
    private ComboBox<Product> productStatus;

    @FXML
    private ComboBox<?> productcategory;

    @FXML
    private TextArea productdescription;

    @FXML
    private ImageView productimagepreview;

    @FXML
    private TextField productname;

    @FXML
    private TextField productprice;

    @FXML
    private TextField productsize;

    @FXML
    private Button searchLogo;

    @FXML
    private TextField searchbar;

    @FXML
    private ImageView stjohnsLogo;

    @FXML
    private Button uploadimage;

    @FXML
    private Button userProfileIcon;

    private String sql = "";

    private Connection conn;

    private PreparedStatement pstmt;



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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




    public void handleLoadImageAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Image");
        File file = fc.showOpenDialog(null);
            if (file != null){
                Image image = new Image(file.toURI().toString());
                productimagepreview.setImage(image);
            }
    }

    public void handleAddCategory(ActionEvent event) {

    }

    public void handleSaveProduct(ActionEvent event) {
        sql = "INSERT INTO Product (name, description, price, rating, stock_status, image_data) VALUES (?,?,?,?,?,?,?,?)";

    }
}
