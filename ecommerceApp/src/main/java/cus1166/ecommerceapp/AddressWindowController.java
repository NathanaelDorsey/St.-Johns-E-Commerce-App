package cus1166.ecommerceapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddressWindowController {
    @FXML
    private TextField addressTextField;

    public void setAddress(String username) {
        String currentAddress = UserManager.getAddressFromDatabase(username);
        if (currentAddress != null) {
            addressTextField.setText(currentAddress);
        } else {
            System.out.println("Failed to fetch the current address.");
        }
    }

    @FXML
    private void saveAddress() {
        String newAddress = addressTextField.getText();
        boolean updated = UserManager.updateAddress(EccomerceApp.getUsername(), newAddress);
        if (updated) {
            closeWindow();
        } else {
            System.out.println("Failed to update address in the database.");
        }
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) addressTextField.getScene().getWindow();
        stage.close();
    }
}