package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditUserController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;

    private User currentUser;
    private final Connection connection = DBUtils.ConnectDb();

    public void initialize() {
        setupFieldListeners();

        if (currentUser != null) {
            loadDataIntoFields();
        }
    }

    private void setupFieldListeners() {
        firstNameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                firstNameField.setStyle("-fx-background-color: " + (firstNameField.getText().trim().isEmpty() ? "salmon" : "white") + ";");
            }
        });

        lastNameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                lastNameField.setStyle("-fx-background-color: " + (lastNameField.getText().trim().isEmpty() ? "salmon" : "white") + ";");
            }
        });

        emailField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                emailField.setStyle("-fx-background-color: " + (emailField.getText().trim().isEmpty() ? "salmon" : "white") + ";");
            }
        });
    }

    private void loadDataIntoFields() {
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        emailField.setText(currentUser.getEmail());
    }

    public void setUser(User user) {
        this.currentUser = user;
        if (user != null) {
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            emailField.setText(user.getEmail());
        }
    }

    @FXML
    private void saveUser() {
        if (validateInput()) {
            currentUser.setFirstName(firstNameField.getText().trim());
            currentUser.setLastName(lastNameField.getText().trim());
            currentUser.setEmail(emailField.getText().trim());

            if (updateUserInDatabase()) {
                showAlert("Success", "User updated successfully.");
                System.out.println("User saved: " + currentUser.getFirstName() + " " + currentUser.getLastName());
            } else {
                showAlert("Error", "Failed to update user.");
            }
        } else {
            showAlert("Validation Error", "Please check your input and try again.");
        }
    }

    private boolean validateInput() {
        return !firstNameField.getText().trim().isEmpty() &&
                !lastNameField.getText().trim().isEmpty() &&
                !emailField.getText().trim().isEmpty();
    }

    private boolean updateUserInDatabase() {
        String sql = "UPDATE User SET first_name = ?, last_name = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, currentUser.getFirstName());
            pstmt.setString(2, currentUser.getLastName());
            pstmt.setString(3, currentUser.getEmail());
            pstmt.setInt(4, currentUser.getUserId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}