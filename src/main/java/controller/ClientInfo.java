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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.java2d.pipe.SpanClipRenderer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClientInfo {
    public Button moveBackBtn;
    public TextField firstName;
    public TextField secondName;
    public TextField age;
    public TextField passportId;
    public TextField phoneNumber;
    public Button editBtn;
    public Button cancelBtn;
    public Label dateOfDep;
    public Label money;
    public Button terminateBtn;
    public Button submitBtn;
    public Pane depositInfo;
    public Button showBanksBtn;
    public Label uan;
    public Label eur;
    public Label usd;
    public Button bankOperationsButton;
    private Client client;

    @FXML
    //метод щоб заповнити дані яких не вистачає
    private void initialize() throws SQLException {
        db database = new db();
        client = new Client(database.getClientById());
        //заповнюємо з бд
        firstName.setText(client.getFirstName());
        secondName.setText(client.getSecondName());
        age.setText("" + client.getAge());
        passportId.setText("" + client.getPassportId());
        phoneNumber.setText("" + client.getPhoneNumber());
        //заборана на зміну даних
        firstName.setEditable(false);
        secondName.setEditable(false);
        age.setEditable(false);
        passportId.setEditable(false);
        phoneNumber.setEditable(false);


        submitBtn.setVisible(false);
        submitBtn.setDisable(true);

        cancelBtn.setDisable(true);
        cancelBtn.setVisible(false);
        //якщо є або нема депозиту щось там робимо
        if(client.getDepositOffer() == null) {
            depositInfo.setVisible(false);
            depositInfo.setDisable(true);
        } else{
            //вивід скільки лаве на базі
            uan.setText("uan: " + client.getBalance().getUan());
            eur.setText("eur: " + client.getBalance().getEur());
            usd.setText("usd: " + client.getBalance().getUsd());
            //дата і кеш на базі
            dateOfDep.setText("Date of deposit: " + client.getBalance().getDate());
            money.setText("Current income: " + calculate());
        }

    }
    //дозволяємо зміни інфи про клієнта
    public void editClick(ActionEvent actionEvent) {
        firstName.setEditable(true);
        secondName.setEditable(true);
        age.setEditable(true);
        passportId.setEditable(true);
        phoneNumber.setEditable(true);

        editBtn.setVisible(false);
        editBtn.setDisable(true);
        submitBtn.setVisible(true);
        submitBtn.setDisable(false);

        bankOperationsButton.setVisible(false);
        bankOperationsButton.setDisable(true);

        cancelBtn.setDisable(false);
        cancelBtn.setVisible(true);

    }
    //скасувати зміни
    public void cancelClick(ActionEvent actionEvent) {
        firstName.setEditable(false);
        secondName.setEditable(false);
        age.setEditable(false);
        passportId.setEditable(false);
        phoneNumber.setEditable(false);

        submitBtn.setVisible(false);
        submitBtn.setDisable(true);
        editBtn.setVisible(true);
        editBtn.setDisable(false);

        bankOperationsButton.setVisible(true);
        bankOperationsButton.setDisable(false);

        cancelBtn.setDisable(true);
        cancelBtn.setVisible(false);


        firstName.setText(client.getFirstName());
        secondName.setText(client.getSecondName());
        age.setText("" + client.getAge());
        passportId.setText("" + client.getPassportId());
        phoneNumber.setText("" + client.getPhoneNumber());
    }
    //зняти гроші
    public void terminateClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyWithdraw.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
    //підтвердити зміни про клієнта
    public void submitClick(ActionEvent actionEvent) throws SQLException {
        firstName.setEditable(false);
        secondName.setEditable(false);
        age.setEditable(false);
        passportId.setEditable(false);
        phoneNumber.setEditable(false);

        submitBtn.setVisible(false);
        submitBtn.setDisable(true);
        editBtn.setVisible(true);
        editBtn.setDisable(false);

        bankOperationsButton.setVisible(true);
        bankOperationsButton.setDisable(false);
        //перезаписуємо дані про користувача
        Client newClient = new Client(firstName.getText(),
                secondName.getText(),
                Integer.parseInt(age.getText()),
                Integer.parseInt(passportId.getText()),
                Double.parseDouble(phoneNumber.getText()));

        //change in database
        //закидаємо в бд
        db database = new db();
        database.changeClientInfo(newClient);

        client = new Client(newClient);
    }
    //кнопка exit
    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

    public void showBanksClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Banks.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
    //порахувати current income
    public double calculate(){
        String inputString1 = client.getBalance().getDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String inputString2 = dtf.format(now);

        double sum = client.getBalance().getUan() != 0 ? client.getBalance().getUan() :
                (client.getBalance().getEur() != 0 ? client.getBalance().getEur() : client.getBalance().getUsd());
        long days = 0;
        //отримати дату
        try {
            Date date1;
            if (inputString1.equals("0"))
                date1 = myFormat.parse(inputString2);
            else date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //нарахувати %
        for(int i = 0; i < days/client.getDepositOffer().getMaxDepositTime(); i++){
            sum += sum/100*client.getDepositOffer().getGrowthPercentage();
        }

        return sum;
    }
    //втикнути на всі операції
    public void bankOperationsClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BankOperations.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

}
