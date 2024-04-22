package cus1166.ecommerceapp;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Product;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ComboBox<String> productcategory;

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

    private void loadCategories() {
        String query = "SELECT category_name FROM Category";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                productcategory.getItems().add(categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error fetching categories from the database.");
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
        loadCategories();
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

        loadCategories();
    }

    public void handleSaveProduct(ActionEvent event) {
        sql = "INSERT INTO Product (name, description, price, rating, stock_status, image_data) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productname.getText());
            pstmt.setString(2, productdescription.getText());
            pstmt.setDouble(3, Double.parseDouble(productprice.getText()));
            pstmt.setDouble(4, Double.parseDouble("5")); // Assuming a TextField for product rating
            pstmt.setString(5, String.valueOf(productStatus.getValue()));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Image image = productimagepreview.getImage();
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bImage, "png", bos);
            byte[] data = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            pstmt.setBinaryStream(6, bis, data.length);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert("Success", "Product successfully added.");
            } else {
                showAlert("Error", "Failed to add product.");
            }
        } catch (SQLException | IOException | NumberFormatException e) {
            showAlert("Error", "Error occurred: " + e.getMessage());
        }
    }

}
