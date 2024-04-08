package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    @FXML
    private Label home;

    @FXML
    private Label categories;

    @FXML
    private ImageView searchLogo;

    @FXML
    private TextField searchbar;

    @FXML
    private ImageView stjohnsLogo;

    @FXML
    private HBox SPACER;
    @FXML
    private HBox cardLayout;

    @FXML
    private ImageView userProfileIcon;

    @FXML
    private Label username;

    @FXML
    private ImageView cart;
    private List<Product> recentlyAdded;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("productcard.fxml"));
                HBox box = loader.load();
                ProductController productController = loader.getController();
                productController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(box);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private List<Product> recentlyAdded(){
        List<Product> products = new ArrayList<Product>();
        return products;
    }
}