package controller;

import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Login {
    @FXML
    public javafx.scene.control.TextField userName;
    @FXML
    public PasswordField userPassword;
    @FXML
    public Button loginButton;
    @FXML
    public Button moveBackBtn;

    private db database;

    public Login() throws SQLException {
        database = new db();
    }


    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        //Перевірки чи дані для входу правильні
        if(database.passwordCheck(userName.getText(), userPassword.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientInfo.fxml"));
            Stage stage = (Stage) moveBackBtn.getScene().getWindow();
            Scene scene = new Scene((Parent) loader.load(), 1000, 600);
            stage.setScene(scene);
        }

    }
    //ернутися назад
    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
}
