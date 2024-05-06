package cus1166.ecommerceapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import models.Product;
import models.ShoppingCart;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;

import static cus1166.ecommerceapp.HomepageControllerAdmin.LOGGER;

public class CartPageAdminController {

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
    private TableView<Product> carttable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> amountColumn;

    @FXML
    private Label productname;

    @FXML
    private Label price;

    @FXML
    private Label amountincart;

    @FXML
    private TextField amountaddremove;

    @FXML
    private Button addcartbtn;

    @FXML
    private Button rmvcartbtn;

    @FXML
    private ImageView productimage;

    @FXML
    private Label subtotal;

    @FXML
    private Label salestax;

    @FXML
    private Label ordertotal;

    @FXML
    private Button clearcartbtn;

    @FXML
    private Button checkoutbtn;

    private ShoppingCart shoppingCart;

    public void initialize() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        shoppingCart = ShoppingCart.getInstance();
        updateCartUI();

        carttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadItemDetails(newSelection);
            }
        });
    }

    private void loadItemDetails(Product newSelection) {
        if (newSelection != null) {
            productname.setText(newSelection.getProductName());
            price.setText(String.format("$%.2f", newSelection.getPrice()));
            int amount = shoppingCart.getAmountInCart(newSelection);
            amountincart.setText(String.valueOf(amount));

            loadImage(newSelection.getImageBlob());
        } else {
            productname.setText("Select a product");
            price.setText("");
            amountincart.setText("");
            productimage.setImage(null);
        }
    }

    private void loadImage(Blob imageBlob) {
        if (imageBlob != null) {
            try (InputStream inputStream = imageBlob.getBinaryStream()) {
                Image image = new Image(inputStream);
                productimage.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
                productimage.setImage(null);
            }
        } else {
            System.out.println("No image data available.");
            productimage.setImage(null);
        }
    }


    private void updateCartUI() {
        ObservableList<Product> cartItems = FXCollections.observableArrayList(shoppingCart.getCartItems());
        carttable.setItems(cartItems);
        double subtotalValue = calculateSubtotal();
        double salesTaxValue = calculateSalesTax(subtotalValue);
        double orderTotalValue = subtotalValue + salesTaxValue;
        subtotal.setText(String.format("$%.2f", subtotalValue));
        salestax.setText(String.format("$%.2f", salesTaxValue));
        ordertotal.setText(String.format("$%.2f", orderTotalValue));
    }


    private double calculateSubtotal() {

        double subtotal = 0.0;
        for (Product product : shoppingCart.getCartItems()) {
            subtotal += product.getPrice() * shoppingCart.getAmountInCart(product);
        }
        return subtotal;
    }

    private double calculateSalesTax(double subtotal) {
        final double SALES_TAX_RATE = 0.08;
        return subtotal * SALES_TAX_RATE;
    }

    @FXML
    private void addToCartClicked(ActionEvent event) {
        try {
            Product selectedProduct = carttable.getSelectionModel().getSelectedItem();
            int quantityToAdd = Integer.parseInt(amountaddremove.getText());
            if (selectedProduct != null && quantityToAdd > 0) {
                shoppingCart.addToCart(selectedProduct, quantityToAdd);
                updateCartUI();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for quantity");
        }
    }

    @FXML
    private void removeFromCartClicked(ActionEvent event) {
        Product selectedProduct = carttable.getSelectionModel().getSelectedItem();
        int quantityToRemove = Integer.parseInt(amountaddremove.getText());
        if (selectedProduct != null) {
            shoppingCart.removeFromCart(selectedProduct, quantityToRemove);
            updateCartUI();
        }
    }

    @FXML
    private void clearCartClicked(ActionEvent event) {
        shoppingCart.clearCart();
        updateCartUI();
    }


    @FXML
    private void checkoutClicked(ActionEvent event) {
        String username = EccomerceApp.getUsername();
        if (username != null && !username.isEmpty()) {
            int userId = UserManager.getUserIdFromUsername(username);
            if (userId != -1) {
                String address = UserManager.getAddressFromDatabase(username);
                if (address != null && !address.isEmpty()) {
                    showAddressWindow(address);
                    placeOrder(userId);
                } else {
                    System.out.println("Address not found for user: " + username);
                }
            } else {
                System.out.println("User ID not found for username: " + username);
            }
        } else {
            System.out.println("Username not found in session.");
        }
    }

    private void showAddressWindow(String address) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cus1166/ecommerceapp/AddressWindow.fxml"));
            Parent root = loader.load();
            AddressWindowController controller = loader.getController();
            controller.setAddress(EccomerceApp.getUsername());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Change Address");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showOrderPlacedConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Placed");
        alert.setHeaderText(null);
        alert.setContentText("Your order has been successfully placed!");
        alert.showAndWait();
    }

    private void placeOrder(int userId) {
        String insertOrderQuery = "INSERT INTO `order` (user_id, status, date_ordered, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(insertOrderQuery)) {
            stmt.setInt(1, userId);
            stmt.setString(2, "Placed");
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setDouble(4, calculateOrderTotal());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order placed successfully.");
                showOrderPlacedConfirmation();
            } else {
                System.out.println("Failed to place order.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to place order", e);
        }
    }

    private double calculateOrderTotal() {
        return (calculateSubtotal() + calculateSalesTax(calculateSubtotal()));
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/adminsettings.fxml")));
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cus1166/ecommerceapp/Admins/cartpage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            LOGGER.info("Switched to Cart page successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to switch to Cart page", e);
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
}
