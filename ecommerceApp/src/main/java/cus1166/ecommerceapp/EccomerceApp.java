package cus1166.ecommerceapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EccomerceApp extends Application {

    private static String username;
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EccomerceApp.class.getResource("/cus1166/ecommerceapp/loginpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
        stage.setTitle("StormMarket");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}