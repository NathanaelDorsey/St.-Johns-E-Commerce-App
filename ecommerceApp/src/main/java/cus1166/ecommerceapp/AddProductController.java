package cus1166.ecommerceapp;

import javafx.collections.FXCollections;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProductController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private ImageView productimagepreview;
    @FXML
    private TextField productname, productprice, productstock;
    @FXML
    private TextArea productdescription;
    @FXML
    private ComboBox<String> productcategory;

    private Connection connection;
    private File selectedFile;
    private static final Logger LOGGER = Logger.getLogger(AddProductController.class.getName());

    public List<String> fetchCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT category_name FROM category";
        try (Connection conn = DBUtils.ConnectDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
        return categories;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productcategory.setItems(FXCollections.observableArrayList(fetchCategories()));
    }

    public void UploadImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFile = file;  // Store selected file for later use
            try {
                Image image = new Image(file.toURI().toString());
                productimagepreview.setImage(image);  // Display selected image in ImageView
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error loading image: {0}", e.getMessage());
            }
        }
    }

    private String saveImageToFileSystem(File file) {
        String destinationPath = "/Images"; // Correct path for the resource directory
        File destFile = new File(destinationPath, file.getName());
        try {
            Files.createDirectories(destFile.getParentFile().toPath());
            Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destFile.getPath(); // Return the file path for database storage
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving image file: {0}", e.getMessage());
            return null;
        }
    }

    public void AddItemAction(ActionEvent event) {
        // Error handling for empty fields
        if (productname.getText().trim().isEmpty() || productcategory.getSelectionModel().isEmpty() || productprice.getText().trim().isEmpty() || productstock.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        try {
            connection = DBUtils.ConnectDb();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO product (name, price, stock_status, description, rating, image_url) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, productname.getText());
                pst.setDouble(2, Double.parseDouble(productprice.getText()));
                int stock = Integer.parseInt(productstock.getText().trim());
                pst.setString(3, stock > 0 ? "In Stock" : "Out of Stock");
                pst.setString(4, productdescription.getText());
                pst.setInt(5, 5); // Assuming fixed rating for example
                pst.setString(6, selectedFile.getName()); // Store image path
                pst.executeUpdate();

                // Handle the rest of database operations...
                connection.commit();
                JOptionPane.showMessageDialog(null, "Successfully added item.");
            } catch (SQLException e) {
                connection.rollback();
                JOptionPane.showMessageDialog(null, "Error adding item: " + e.getMessage());
            } finally {
                if (connection != null) connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error connecting to database.");
        }
    }

    private int getCategoryId(String categoryName) {
        String sql = "SELECT category.category_id FROM category WHERE category_name = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching category ID: " + e.getMessage());
        }
        return -1;
    }

    private int getLastInsertedProductId() {
        String sql = "SELECT LAST_INSERT_ID()";
        try (Connection conn = DBUtils.ConnectDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching last inserted product ID: " + e.getMessage());
        }
        return -1;
    }
    public void switchToHomepage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Admins/homepageadmin.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
