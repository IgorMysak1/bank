package controller;

import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.company.Bank;

public class Banks {
    @FXML
    public Button moveBackBtn;
    @FXML
    public TableView banksTable;
    @FXML
    public TableColumn nameCol;
    @FXML
    public TableColumn rateCol;
    @FXML
    public TableColumn trustCol;
    @FXML
    public TableColumn offersCol;
    @FXML
    public Button moveToDepositsButton;

    private Bank[] banks;

    @FXML
    private void initialize() throws SQLException {
        initializeBanks();
        moveToDepositsButton.setDisable(true);
        moveToDepositsButton.setVisible(false);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        trustCol.setCellValueFactory(new PropertyValueFactory<>("human_trust"));
        offersCol.setCellValueFactory(new PropertyValueFactory<>("offers"));

        for(int i = 0; i < banks.length; i++){
            banksTable.getItems().add(banks[i]);
        }
    }

    public void moveBack(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientInfo.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }



    public void initializeBanks() throws SQLException {
        db database = new db();

        banks = new Bank[database.countBanks()];

        for(int i = 0; i < banks.length; i++)
            banks[i] = new Bank(database.getBanks(i + 1));
    }
    //клік на банк, появляється кнопоска deposits
    public void banksClickHandler(MouseEvent mouseEvent) {
        moveToDepositsButton.setDisable(false);
        moveToDepositsButton.setVisible(true);
    }
    //при кліціна deposits осьосьо викликається
    public void moveToDeposits(ActionEvent actionEvent) throws SQLException, IOException {
        db database = new db();

        Bank b = new Bank((Bank) banksTable.getSelectionModel().getSelectedItem());
        database.setBankIdForBalance(database.getIndexOfBankByName(b.getName()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Deposits.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }
}
