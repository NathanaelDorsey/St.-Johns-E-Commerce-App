package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Product;

import java.util.Objects;

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
public void setData(Product product){
    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(product.getImageSrc())));

    productImage.setImage(image);
    productName.setText(product.getProductName());
    price.setText(String.valueOf(product.getPrice()));
    rating.setText(String.valueOf(product.getRating()));
}
}