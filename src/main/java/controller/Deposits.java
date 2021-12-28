package controller;

import com.company.Bank;
import com.company.DepositOffer;
import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Deposits {
    @FXML
    public TableView depositsTable;
    @FXML
    public TableColumn numbCol;
    @FXML
    public TableColumn maxSumCol;
    @FXML
    public TableColumn timeCol;
    @FXML
    public TableColumn percentCol;
    @FXML
    public TableColumn usdCol;
    @FXML
    public TableColumn uanCol;
    @FXML
    public TableColumn eurCol;
    @FXML
    public TableColumn termCol;
    @FXML
    public Button moveBackBtn;
    @FXML
    public Button calculateDepBtn;
    @FXML
    public Button makeDepositBtn;
    public TextField percentField;
    public TextField timeField;
    public TextField maxSumField;
    public Button searchBtn;
    public Button showAllBtn;

    private Bank banks;

    @FXML
    //заповнює таблицю
    private void initialize() throws SQLException {
        calculateDepBtn.setVisible(false);
        calculateDepBtn.setDisable(true);
        makeDepositBtn.setVisible(false);
        makeDepositBtn.setDisable(true);

        initializeDeposits();


        maxSumCol.setCellValueFactory(new PropertyValueFactory<>("maxSum"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("maxDepositTime"));
        percentCol.setCellValueFactory(new PropertyValueFactory<>("growthPercentage"));
        uanCol.setCellValueFactory(new PropertyValueFactory<>("uah"));
        eurCol.setCellValueFactory(new PropertyValueFactory<>("eur"));
        usdCol.setCellValueFactory(new PropertyValueFactory<>("usd"));
        termCol.setCellValueFactory(new PropertyValueFactory<>("terminationPossibility"));

        for(int i = 0; i < banks.getRegularDepositOffers().length; i++){
            depositsTable.getItems().add(banks.getRegularDepositOffers(i));
        }
    }


    public void initializeDeposits() throws SQLException {
        db database = new db();

        banks = new Bank(database.getBanks(database.getIndexOfBank()));
    }

    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Banks.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

    //відображення make deposits && Calculate for deposits
    public void depositsClickHandler(MouseEvent mouseEvent) {
        calculateDepBtn.setVisible(true);
        calculateDepBtn.setDisable(false);
        makeDepositBtn.setVisible(true);
        makeDepositBtn.setDisable(false);
    }


    public void calculateClick(ActionEvent actionEvent) throws SQLException, IOException {
        DepositOffer d = new DepositOffer((DepositOffer) depositsTable.getSelectionModel().getSelectedItem());
        db database = new db();
        database.setDepositIdForBalance(database.getIndexOfDepositByProperties(d));

        //

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Calculation.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);

    }

    public void makeDepositClick(ActionEvent actionEvent) throws SQLException, IOException {
        DepositOffer d = new DepositOffer((DepositOffer) depositsTable.getSelectionModel().getSelectedItem());
        db database = new db();

        database.setDepositIdForBalance(database.getIndexOfDepositByProperties(d));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MakeDeposit.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
    //показати всі депозити
    public void showAllClick(ActionEvent actionEvent) {
        depositsTable.getItems().clear();

        for(int i = 0; i < banks.getRegularDepositOffers().length; i++){
            depositsTable.getItems().add(banks.getRegularDepositOffers(i));
        }
    }
    //пошук депозитів за параметрами
    public void searchClick(ActionEvent actionEvent) {
        double maxSum = 0;
        int time = Integer.MAX_VALUE;
        double percent = 0;

        if(!maxSumField.getText().isEmpty())
            maxSum = Double.parseDouble(maxSumField.getText());
        if(!timeField.getText().isEmpty())
            time = Integer.parseInt(timeField.getText());
        if(!percentField.getText().isEmpty())
            percent = Double.parseDouble(percentField.getText());

        depositsTable.getItems().clear();

        for(int i = 0; i < banks.getRegularDepositOffers().length; i++){
            if(banks.getRegularDepositOffers(i).getGrowthPercentage() >= percent &&
                    banks.getRegularDepositOffers(i).getMaxSum() >= maxSum &&
                    banks.getRegularDepositOffers(i).getMaxDepositTime() <= time)
                depositsTable.getItems().add(banks.getRegularDepositOffers(i));
        }
    }
}
