package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Product;

import java.io.ByteArrayInputStream;

import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductController {

    @FXML
    private Label productName;
    @FXML
    private ImageView productImage;
    @FXML
    private HBox box;
    @FXML
    private Label price;
    @FXML
    private Label rating;
    static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    public void setData(Product product) {
        try {
            Blob imageBlob = product.getImageBlob();
            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            productImage.setImage(image);

            productName.setText(product.getProductName());
            price.setText(String.valueOf(product.getPrice()));
            rating.setText(String.valueOf(product.getRating()));

            LOGGER.log(Level.INFO, "Image loaded successfully for product: {0}", product.getProductName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load image for product: {0}", e.getMessage());
        }
    }

    public ImageView getProductImage() {
        return productImage;
    }
}
