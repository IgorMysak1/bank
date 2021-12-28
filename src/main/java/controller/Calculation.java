package controller;

import com.company.DepositOffer;
import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Calculation {
    @FXML
    public Button moveBackBtn;
    @FXML
    public Label currentDeposit;
    @FXML
    public Label incomeField;
    @FXML
    public DatePicker dateOfWithdraw;
    @FXML
    public DatePicker dateOfDeposit;
    @FXML
    public TextField depositMoney;
    @FXML
    public ChoiceBox currencies;
    @FXML
    public Button calculateButton;

    private DepositOffer dep;

    @FXML
    //берем дані про депозит з бд
    private void initialize() throws SQLException {
        db database = new db();
        dep = new DepositOffer(database.getBalanceDeposit());

        database.setDepositIdForBalance(0);


        if(dep.isUah())
            currencies.getItems().add("uan");
        if(dep.isEur())
            currencies.getItems().add("eur");
        if(dep.isUsd())
            currencies.getItems().add("usd");

        currencies.setValue("uan");
    }


    public void moveBack(ActionEvent actionEvent) throws IOException, SQLException {
        db database = new db();
        database.setDepositIdForBalance(0);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/deposits.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
    //клік
    public void calculateClick(ActionEvent actionEvent) {
        LocalDate d = dateOfDeposit.getValue();
        LocalDate w = dateOfWithdraw.getValue();
        System.out.println("Income: " + calculate(d.toString(), w.toString()));
        incomeField.setText("Income: " + calculate(d.toString(), w.toString()));
    }
    //рахуємо депозит і повертаємо
    public double calculate(String inputString1, String inputString2){

        double sum = Integer.parseInt(depositMoney.getText());
        long days = 0;
        LocalDate date1 = LocalDate.parse(inputString1);
        LocalDate date2 = LocalDate.parse(inputString2);

        days = ChronoUnit.DAYS.between(date1, date2);

        for(int i = 0; i < days/dep.getMaxDepositTime(); i++){
            sum += sum/100*dep.getGrowthPercentage();
        }

        return sum;
    }
}
