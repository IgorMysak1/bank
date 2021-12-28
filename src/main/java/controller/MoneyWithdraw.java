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
import javafx.stage.Stage;
import sun.misc.Cleaner;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MoneyWithdraw {
    public Button moveBackBtn;
    public Label money;
    public Label message;
    public TextField card;
    public Button proceedBtn;

    private Client client;
    private double income;
    private String currency;

    @FXML
    private void initialize() throws SQLException {
        db database = new db();
        client = new Client(database.getClientById());
        //не може втратити прибуток
        if(client.getDepositOffer().isTerminationPossibility()) {
            message.setVisible(false);
            currency = client.getBalance().getUan() != 0 ? "uan" :
                    (client.getBalance().getEur() != 0 ? "eur" : "usd");
            income = calculate();
            if(income == 0)
                income = client.getBalance().getUan() != 0 ? client.getBalance().getUan() :
                        (client.getBalance().getEur() != 0 ? client.getBalance().getEur() : client.getBalance().getUsd());
            money.setText(income + " " + currency);
        } else{//може втратити прибуток
            currency = client.getBalance().getUan() != 0 ? "uan" :
                    (client.getBalance().getEur() != 0 ? "eur" : "usd");
            income = client.getBalance().getUan() != 0 ? client.getBalance().getUan() :
                    (client.getBalance().getEur() != 0 ? client.getBalance().getEur() : client.getBalance().getUsd());
            money.setText(income + " " + currency);
        }

    }
    //порахувати суму грошей яку заробив клієнт
    public double calculate(){
        String inputString1 = client.getBalance().getDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String inputString2 = dtf.format(now);

        double sum = client.getBalance().getUan() == 0 ? client.getBalance().getUan() :
                (client.getBalance().getEur() == 0 ? client.getBalance().getEur() : client.getBalance().getUsd());
        long days = 0;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < days/client.getDepositOffer().getMaxDepositTime(); i++){
            sum += sum/100*client.getDepositOffer().getGrowthPercentage();
        }

        return sum;
    }
    //вернутися назад
    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientInfo.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

    public void proceedClick(ActionEvent actionEvent) throws SQLException, IOException {
        //перевірка чи введена карта
        if(!card.getText().isEmpty()){
            db database = new db();

            //записуємо операцію в бд
            database.createOperation(card.getText(), currency, income);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientInfo.fxml"));
            Stage stage = (Stage) moveBackBtn.getScene().getWindow();
            Scene scene = new Scene((Parent) loader.load(), 1000, 600);
            stage.setScene(scene);
        }
    }
}
