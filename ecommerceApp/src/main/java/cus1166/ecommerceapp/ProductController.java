package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Product;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
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

    private String imagePath;
    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    public void setData(Product product) {
        try {
            imagePath = "/cus1166/ecommerceapp/Images/" + (new File(product.getImageSrc()));

            // Construct the proper resource path
            LOGGER.log(Level.INFO, "Attempting to load image from path: {0}", imagePath);

            if (imagePath == null) {
                LOGGER.log(Level.WARNING, "Image resource not found at path: {0}", imagePath);
                productImage.setImage(new Image("/cus1166/ecommerceapp/Images/shirt.png"));
            } else {
                Image image = new Image(imagePath);
                productImage.setImage(image);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load image for product: {0}", e.getMessage());
            productImage.setImage(new Image("/cus1166/ecommerceapp/Images/shirt.png"));
        }
    }

    public ImageView getProductImage() {
        return productImage;
    }
}
