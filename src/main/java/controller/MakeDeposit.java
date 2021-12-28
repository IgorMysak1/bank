package controller;

import com.company.DepositOffer;
import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MakeDeposit {
    public Button moveBackBtn;
    public TextField moneyToDep;
    public ChoiceBox currencies;
    public Button makeDeposit;

    private DepositOffer dep;

    @FXML
    //беремо дані про депозит, виводимо які можуть бути валюти
    private void initialize() throws SQLException {
        db database = new db();
        dep = new DepositOffer(database.getBalanceDeposit());

        if(dep.isUah())
            currencies.getItems().add("uan");
        if(dep.isEur())
            currencies.getItems().add("eur");
        if(dep.isUsd())
            currencies.getItems().add("usd");

        currencies.setValue("uan");

    }

    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/deposits.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

    public void makeDepositClick(ActionEvent actionEvent) throws SQLException, IOException {
        //check
        if(!moneyToDep.getText().equals("")){
            //записуємо депозит в бд
            db database = new db();
            database.depositMoneyToBalance(currencies.getValue().toString(), Integer.parseInt(moneyToDep.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientInfo.fxml"));
            Stage stage = (Stage) moveBackBtn.getScene().getWindow();
            Scene scene = new Scene((Parent) loader.load(), 1000, 600);
            stage.setScene(scene);
        } else
            makeDeposit.setText("Try again");
    }



}
