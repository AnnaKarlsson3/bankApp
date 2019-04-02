package app.transactionsHistory;

import app.Entities.Bankaccount;
import app.Entities.Transaction;
import app.db.DB;
import app.login.LoginController;
import app.switchScene.SwitchScene;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.util.List;

public class TransactionsHistory {

    @FXML
    Button overviewbtn;
    @FXML
    ChoiceBox choiceBox;
    @FXML
    TableView tableViewTrans;
    @FXML
    TableColumn timeC;
    @FXML
    TableColumn messageC;
    @FXML
    TableColumn amountC;
    @FXML
    TableColumn balanceC;
    @FXML
    Button transferbtn;
    @FXML
    Button loadmorebtn;

    private int offset = 0;
    private int limit = 10;
    private  List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId());
    private Bankaccount currentBankAccount = accounts.get(0);

    @FXML
    private void initialize(){
        System.out.println("initialize transactionsHistory");
        setChoiceBox();
        setTable(currentBankAccount);
        listener();
    }

    void setChoiceBox(){
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(accounts);

        choiceBox.setConverter(new StringConverter<Bankaccount>() {
            @Override
            public String toString(Bankaccount object) {
                return object.getType();
            }

            @Override public Bankaccount fromString(String string) { return null; }
        });
        choiceBox.setValue(accounts.get(0));
    }

    void listener() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observableValue, old, neww) -> {
            currentBankAccount = (Bankaccount) neww;
            limit = 10;
            setTable(currentBankAccount);
        });
    }

    void setTable(Bankaccount newValue){
         String accountnumber = newValue.getAccountnumber();
         List<Transaction> trans = DB.getTransactionOfBankaccount(accountnumber, limit, offset);

        //PropertyValueFactory will fetch the necessary data from your object
        timeC.setCellValueFactory(new PropertyValueFactory<>("date"));
        messageC.setCellValueFactory(new PropertyValueFactory<>("message"));
        amountC.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tableViewTrans.getItems().clear();
        tableViewTrans.getItems().addAll(trans);
    }

    @FXML
    void loadMore(){
        limit = limit + 100;
        setTable(currentBankAccount);
    }

    @FXML void goToHome() { SwitchScene.switchScene("/app/home/home.fxml"); }

    @FXML void goToTransfer()  { SwitchScene.switchScene("/app/transfer/transfer.fxml"); }

}
