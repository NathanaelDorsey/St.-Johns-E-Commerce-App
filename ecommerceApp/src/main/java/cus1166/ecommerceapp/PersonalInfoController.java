package cus1166.ecommerceapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalInfoController {
    private static final Logger LOGGER = Logger.getLogger(PersonalInfoController.class.getName());

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;
    public void initData() {
        String username = EccomerceApp.getUsername();
        if (username != null) {
            loadUserInfo(username);
        } else {
            redirectToLoginPage();
        }
    }

    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cus1166/ecommerceapp/loginpage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = findCurrentStage();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load login page", e);
        }
    }
    private Stage findCurrentStage() {
        Window window = null;
        for (Window w : Window.getWindows()) {
            if (w instanceof Stage) {
                window = w;
                break;
            }
        }
        return (Stage) window;
    }

    private void loadUserInfo(String username) {
        String query = "SELECT first_name, last_name, email, phone_number, address FROM user WHERE username = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                emailField.setText(rs.getString("email"));
                phoneNumberField.setText(rs.getString("phone_number"));
                addressField.setText(rs.getString("address"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load user information", e);
        }
    }
    @FXML
    private void handleSaveAction(ActionEvent event) {
        String username = EccomerceApp.getUsername();
        if (!validateInput()) {
            return;
        }
        String updateQuery = "UPDATE user SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ? WHERE username = ?";
        try (Connection conn = DBUtils.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, firstNameField.getText());
            stmt.setString(2, lastNameField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, phoneNumberField.getText());
            stmt.setString(5, addressField.getText());
            stmt.setString(6, username);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.info("User information updated successfully.");
                showAlert("Success", "Your information has been updated successfully!");
            } else {
                LOGGER.warning("No changes were made to update the user information.");
                showAlert("Update Failed", "No changes were detected in your information. Update failed.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update user information", e);
            showAlert("Database Error", "An error occurred while updating your information. Please try again.");
        }
    }

    private boolean validateInput() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!emailField.getText().matches(emailRegex)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }
        if (!phoneNumberField.getText().matches("\\d{10}")) {
            showAlert("Invalid Phone Number", "Please enter a valid 10-digit phone number.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
