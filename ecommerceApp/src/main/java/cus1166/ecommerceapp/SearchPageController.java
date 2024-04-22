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
import java.util.logging.Logger;

import static cus1166.ecommerceapp.ManageUserController.LOGGER;

public class SearchPageController {
    @FXML
    private Hyperlink home;
    @FXML
    private Hyperlink categories;
    @FXML
    private TextField searchbar;
    @FXML
    private Button searchLogo;
    @FXML
    private ImageView stjohnsLogo;
    @FXML
    private Button userProfileIcon;
    @FXML
    private Button cart;
}
