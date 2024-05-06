package cus1166.ecommerceapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Tables;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static cus1166.ecommerceapp.ManageUserController.LOGGER;


public class ManageProductController implements Initializable {

    @FXML
    private Hyperlink home, categories, manageusers, addproducts, manageproducts;
    @FXML
    private TextField searchbar;
    @FXML
    private Button searchLogo, userProfileIcon, cart, categoriesbtn, productsbtn, savebtn, updatebtn, deletebtn;
    @FXML
    private HBox SPACER1, SPACER;
    @FXML
    private ImageView stjohnsLogo;
    @FXML
    private TableView<Tables> tableView;
    @FXML
    private TableColumn<Tables, Integer> colID;
    @FXML
    private TableColumn<Tables, String> colName;
    @FXML
    private Label labelProductName;

    @FXML
    private Label labelProductPrice;

    @FXML
    private Label labelStockStatus;

    @FXML
    private ImageView productImageView;


    private enum TableType {
        PRODUCT, CATEGORY
    }

    private TableType currentType = TableType.PRODUCT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        try {
            showTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && currentType == TableType.PRODUCT) {
                Tables selectedProduct = (Tables) newSelection;
                fetchAndDisplayProductDetails(selectedProduct.getId());
            }
        });
    }
    private void fetchAndDisplayProductDetails(int productId) {
        String sql = "SELECT name, price, stock_status, image_data FROM Product WHERE product_id = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String stockStatus = rs.getString("stock_status");
                Blob imageBlob = rs.getBlob("image_data");

                // Update UI Elements for name, price, and stock status
                labelProductName.setText("Name: " + (name != null ? name : "N/A"));
                labelProductPrice.setText("Price: $" + (price != 0 ? String.format("%.2f", price) : "N/A"));
                labelStockStatus.setText("Stock Status: " + (stockStatus != null ? stockStatus : "N/A"));


                if (imageBlob != null) {
                    Image image = new Image(imageBlob.getBinaryStream());
                    productImageView.setImage(image);
                } else {
                    productImageView.setImage(null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            clearProductDetails();
        } catch (Exception e) {
            System.err.println("Error loading image from database: " + e.getMessage());
            productImageView.setImage(null);
        }
    }

    private void clearProductDetails() {
        labelProductName.setText("Name: N/A");
        labelProductPrice.setText("Price: N/A");
        labelStockStatus.setText("Stock Status: N/A");
        productImageView.setImage(null);
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

    @FXML
    void switchTableType(javafx.event.ActionEvent event) {
        Button btn = (Button) event.getSource();
        if (btn == categoriesbtn) {
            currentType = TableType.CATEGORY;
        } else if (btn == productsbtn) {
            currentType = TableType.PRODUCT;
        }
        try {
            showTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openEditDialog(javafx.event.ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            Tables selected = tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    currentType == TableType.PRODUCT ? "Admins/editproduct.fxml" : "Admins/editcategory.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(currentType == TableType.PRODUCT ? "Edit Product" : "Edit Category");
            stage.setScene(new Scene(loader.load()));

            if (currentType == TableType.PRODUCT) {
                EditProductController controller = loader.getController();
                controller.initData(selected.getId());
            } else {
                EditCategoryController controller = loader.getController();
                controller.initData(selected.getId());
            }

            stage.showAndWait();
            showTable();
        } catch (Exception e) {
            System.err.println("Error opening edit dialog: " + e.getMessage());
        }
    }

    public void showTable() throws SQLException {
        ObservableList<Tables> list = getTableList();
        tableView.setItems(list);
        tableView.refresh();
    }

    @FXML
    void handleSaveAction(javafx.event.ActionEvent event) {
        for (Tables item : tableView.getItems()) {
            updateItemInDatabase(item);
        }
    }


    private void updateItemInDatabase(Tables item) {
        String columnId = currentType == TableType.PRODUCT ? "product_id" : "category_id";
        String tableName = currentType == TableType.PRODUCT ? "Product" : "Category";
        String updateQuery = "UPDATE " + tableName + " SET name = ? WHERE " + columnId + " = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, item.getTableName());
            pstmt.setInt(2, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    @FXML
    void handleDeleteAction(javafx.event.ActionEvent event) {
        Tables selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("No Selection", "No Item Selected", "Please select an item to delete.");
            return;
        }
        deleteItemFromDatabase(selectedItem);
        tableView.getItems().remove(selectedItem);
    }

    private void deleteItemFromDatabase(Tables item) {
        String columnId = currentType == TableType.PRODUCT ? "product_id" : "category_id";
        String tableName = currentType == TableType.PRODUCT ? "Product" : "Category";
        String deleteQuery = "DELETE FROM " + tableName + " WHERE " + columnId + " = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, item.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                showAlert("Error", "Deletion Failed", "The item was not deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting item: " + e.getMessage());
            showAlert("SQL Error", "Error Deleting Item", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private ObservableList<Tables> getTableList() throws SQLException {
        ObservableList<Tables> list = FXCollections.observableArrayList();
        String tableName = currentType == TableType.PRODUCT ? "Product" : "Category";
        String columnName = currentType == TableType.PRODUCT ? "name" : "category_name";
        String columnId = currentType == TableType.PRODUCT ? "product_id" : "category_id";
        String sql = "SELECT " + columnId + ", " + columnName + " FROM " + tableName;
        System.out.println("Executing query: " + sql);

        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int rowCount = 0;
            while (rs.next()) {
                int id = rs.getInt(columnId);
                String name = rs.getString(columnName);
                list.add(new Tables(id, name));
                System.out.println("Adding to list: id=" + id + ", name=" + name);
                rowCount++;
            }
            System.out.println("Number of rows fetched: " + rowCount);
            System.out.println(list);
        } catch (SQLException e) {
            System.err.println("Error fetching data from the database: " + e.getMessage());
        }
        return list;

    }

}