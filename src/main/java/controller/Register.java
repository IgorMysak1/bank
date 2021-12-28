package controller;

import com.company.Client;
import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Register {
    @FXML
    public Button moveBackBtn;
    public TextField firstName;
    public TextField secondName;
    public TextField age;
    public TextField phoneNumber;
    public TextField passportId;
    public Button submitBtn;
    public TextField username;
    public PasswordField password;
    public Label error;

    private Client client;


    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }


    public void submitClick(ActionEvent actionEvent) throws SQLException, IOException {
        if(checkFields()){
            error.setText("");
            //check if username doesnt exist in database
            db database = new db();
            //перевірка чи username унікальний
            if(!database.isUsernameCorrect(username.getText())){
                error.setText("Change username");
            } else{
                int id = database.insertClient(firstName.getText(),
                        secondName.getText(),
                        Integer.parseInt(age.getText()),
                        Integer.parseInt(passportId.getText()),
                        Double.parseDouble(phoneNumber.getText()));

                database.insertUser(id, username.getText(), password.getText());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
                Stage stage = (Stage) moveBackBtn.getScene().getWindow();
                Scene scene = new Scene((Parent) loader.load(), 1000, 600);
                stage.setScene(scene);
            }
        } else error.setText("Fill in the fields");
    }

    private boolean checkFields(){
        if(firstName.getText().isEmpty())
            return false;
        if(secondName.getText().isEmpty())
            return false;
        if(age.getText().isEmpty())
            return false;
        if(passportId.getText().isEmpty())
            return false;
        if(phoneNumber.getText().isEmpty())
            return false;
        if(username.getText().isEmpty())
            return false;

        return !password.getText().isEmpty();
    }
}
