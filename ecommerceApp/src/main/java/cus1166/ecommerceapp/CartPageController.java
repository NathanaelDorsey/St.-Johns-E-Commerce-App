package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Product;

import java.util.Objects;

public class CartPageController {

    @FXML
    private HBox box;

    @FXML
    private Label cartAmount;

    @FXML
    private Label price;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label rating;

}
