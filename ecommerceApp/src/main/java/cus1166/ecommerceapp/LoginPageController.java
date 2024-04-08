package cus1166.ecommerceapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
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
    private ImageView userProfileIcon;

    @FXML
    private Label username;

    @FXML
    private ImageView cart;

    @FXML
    private ComboBox<String> type;
    @FXML
    private TextField xNumber;
    @FXML
    private TextField password;

    @FXML
    private TextField rxNumber;

    @FXML
    private TextField rpassword;

    @FXML
    private TextField confirmPassword;

    @FXML
    private TextField email;

    @FXML
    private CheckBox checkbox;
    private Stage stage;
    private Parent root;
    private Scene scene;
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private void Register(ActionEvent event) {
        if(rxNumber == null||
        rpassword == null||
        confirmPassword == null
        ) {
            JOptionPane.showMessageDialog(null, "Please Fill All Fields.");
            return;
        }
        else if(rpassword.getText().equals(confirmPassword.getText())){
            connection = DBUtils.ConnectDb();
            String sql = "insert into user (username, password, email) values (?,?,?)";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, rxNumber.getText());
                pst.setString(2, rpassword.getText());
                pst.setString(3, email.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully registered.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error adding participant: " + ex.getMessage());
            }
        }
        }

        @FXML
private void Login(ActionEvent event){
    connection = DBUtils.ConnectDb();
    String accountType = null;
    if (Objects.equals(type.toString(), "user")){
    accountType = "user";
    }else if(Objects.equals(type.toString(), "admin")){
        accountType = "administrator";
    }
        String sql = "select * from" + accountType + "where username =? and password =?";
    try {
        pst = connection.prepareStatement(sql);
        pst.setString(1, xNumber.getText());
        pst.setString(2, password.getText());
        rs = pst.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Successfully logged in.");
            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            stage= (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
    public void switchToLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTosPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tospage.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

}
}
