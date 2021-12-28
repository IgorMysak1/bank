package controller;

import com.company.BankOperation;
import com.company.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class BankOperations {
    @FXML
    public Button moveBackBtn;
    @FXML
    public TableView<BankOperation> operationsTable;
    @FXML
    public TableColumn<BankOperation, Double> moneyCol;
    @FXML
    public TableColumn<BankOperation, String> cardCol;
    @FXML
    public TableColumn<BankOperation, String> dateCol;
    @FXML
    public TableColumn<BankOperation, String> currencyCol;

    private BankOperation[] operations;

    @FXML
    //заповнюємо таблицю
    private void initialize() throws SQLException {
        initializeOperations();

        moneyCol.setCellValueFactory(new PropertyValueFactory<BankOperation, Double>("money"));
        currencyCol.setCellValueFactory(new PropertyValueFactory<BankOperation, String>("currency"));
        cardCol.setCellValueFactory(new PropertyValueFactory<BankOperation, String>("card"));
        dateCol.setCellValueFactory(new PropertyValueFactory<BankOperation, String>("date"));
        //заповнюємо таблицю
        if(operations != null)
            for(int i = 0; i < operations.length; i++){
                operationsTable.getItems().add(operations[i]);
              //  operationsTable.getItems().add(operations[i].getMoney(), operations[i].getCurrency(), operations[i].getCard(), operations[i].getDate());
            }

    }

    public void initializeOperations() throws SQLException {
        db database = new db();
        //перевірка на кількість операцій
        int count = database.countOperation();
        if(count == 0){
            operations = null;
            return;
        }
        //створюємо масив
        operations = new BankOperation[count];

        BankOperation[] temp = database.getBankOperations(count);
        //заповнюємо масив операціями з бд
        for(int i = 0; i < count; i++) {
            operations[i] = new BankOperation(temp[i]);
        }
        // count
    }
    //повернутися назад
    public void moveBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientInfo.fxml"));
        Stage stage = (Stage) moveBackBtn.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load(), 1000, 600);
        stage.setScene(scene);
    }

    public void operationsClickHandler(MouseEvent mouseEvent) {
    }
}
