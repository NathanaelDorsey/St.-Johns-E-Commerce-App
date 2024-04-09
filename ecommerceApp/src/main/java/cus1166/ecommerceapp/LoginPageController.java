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
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
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
        if (rxNumber.getText().trim().isEmpty() ||
                rpassword.getText().trim().isEmpty() ||
                confirmPassword.getText().trim().isEmpty() ||
                email.getText().trim().isEmpty() ||
                !checkbox.isSelected()) {

            JOptionPane.showMessageDialog(null, "Please fill all fields and check the box.");
        } else if (!rpassword.getText().equals(confirmPassword.getText())) {
            JOptionPane.showMessageDialog(null, "Passwords do not match.");
        } else {
            connection = DBUtils.ConnectDb();
            if (connection != null) {
                String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
                try (PreparedStatement pst = connection.prepareStatement(sql)) {
                    pst.setString(1, rxNumber.getText());
                    pst.setString(2, rpassword.getText());
                    pst.setString(3, email.getText());
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Successfully registered.");
                    stage= (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding user: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error connecting to database.");
            }
        }
    }
    @FXML
    private void Login(ActionEvent event) {
        if (xNumber.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
            return;
        }

        String dbUrl, dbUser, dbPass;
        try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            prop.load(input);
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPass = prop.getProperty("db.pass");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading database configuration: " + e.getMessage());
            return;
        }

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            String accountType = null;
            if ("user".equals(type.toString())) {
                accountType = "user";
            } else if ("admin".equals(type.toString())) {
                accountType = "administrator";
            }
            String sql = "SELECT * FROM " + accountType + " WHERE username = ? AND password = ?";
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, xNumber.getText().trim());
                pst.setString(2, password.getText().trim());
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Successfully logged in.");
                        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading FXML: " + e.getMessage());
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
