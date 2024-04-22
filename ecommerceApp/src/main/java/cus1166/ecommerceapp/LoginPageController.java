package cus1166.ecommerceapp;

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
    private ComboBox<String> accountType;
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

        connection = DBUtils.ConnectDb();
        try {
            String sql = "";
            if (accountType.getValue() == null) {
                JOptionPane.showMessageDialog(null, "Please select an account type.");
                return;
            }

            String selectedAccountType = accountType.getValue().toString();
            System.out.println("Selected Account Type: " + selectedAccountType);

            if ("user".equals(selectedAccountType)) {
                sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            } else if ("Administrator".equals(selectedAccountType)) {
                sql = "SELECT * FROM user WHERE username = ? AND password = ? AND is_admin = 1";
                selectedAccountType = "administrator";
            }

            if (sql.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No SQL query is set for the selected account type.");
                return;
            }

            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, xNumber.getText().trim());
                pst.setString(2, password.getText().trim());
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Successfully logged in.");
                        if (selectedAccountType == "administrator") {
                             root = FXMLLoader.load(getClass().getResource("Admins/homepageadmin.fxml"));
                        }else{root = FXMLLoader.load(getClass().getResource("homepage.fxml"));}
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
        root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTosPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tospage.fxml"));
        stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

}
}
