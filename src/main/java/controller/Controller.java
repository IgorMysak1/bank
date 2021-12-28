package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button registerBtn;
    @FXML
    private Button loginBtn;

    public void loginClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

    public void registerClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
}
