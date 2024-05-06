package cus1166.ecommerceapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.Category;


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

import static cus1166.ecommerceapp.ManageUserController.LOGGER;


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
    private ComboBox<String> productStatus;

    @FXML
    private ComboBox<Category> productcategory;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategories();
        setupCategoryComboBox();
    }
    private void setupCategoryComboBox() {
        productcategory.setCellFactory((comboBox) -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        productcategory.setButtonCell(new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }
    private void loadCategories() {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        String query = "SELECT category_id, category_name FROM Category";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                categories.add(new Category(categoryId, categoryName));
            }
            productcategory.setItems(categories);
        } catch (SQLException e) {
            showAlert("Database Error", "Error fetching categories from the database.");
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/adminsettings.fxml")));
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/cartpage.fxml")));
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/categoriespage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Categories page", e);
        }
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


    public void handleSaveProduct(ActionEvent event) {
        Category selectedCategory = productcategory.getValue();
        if (selectedCategory == null) {
            showAlert("Error", "No category selected.");
            return;
        }

        String sql = "INSERT INTO Product (name, description, price, rating, stock_status, image_data) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, productname.getText());
            pstmt.setString(2, productdescription.getText());
            pstmt.setDouble(3, Double.parseDouble(productprice.getText()));
            pstmt.setDouble(4, 5.0);
            pstmt.setString(5, String.valueOf(productStatus.getValue()));


            if (productimagepreview.getImage() != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                BufferedImage bImage = SwingFXUtils.fromFXImage(productimagepreview.getImage(), null);
                ImageIO.write(bImage, "png", bos);
                byte[] data = bos.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                pstmt.setBinaryStream(6, bis, data.length);
            } else {
                pstmt.setBinaryStream(6, null);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int productId = generatedKeys.getInt(1);
                        linkProductToCategory(productId, selectedCategory.getId());
                        showAlert("Success", "Product successfully added.");
                    }
                }
            } else {
                showAlert("Error", "Failed to add product.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error occurred: " + e.getMessage());
        } catch (IOException e) {
            showAlert("File Error", "Error processing image: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please check number inputs: " + e.getMessage());
        }
    }

    private void linkProductToCategory(int productId, int categoryId) throws SQLException {
        String sql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert("Error", "Error linking product to category: " + e.getMessage());
        }
    }



}
