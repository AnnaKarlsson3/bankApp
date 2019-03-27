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

    @FXML
    private void initialize(){
        System.out.println("initialize transactionsHistory");
        setChoiceBox();
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
        choiceBox.setValue(accounts.get(0)); //TODO: Change! - setting choiceBox back to first option, also when you press loadmoreBtn!
        listener();
        setTable(accounts.get(0));
    }

    void listener() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observableValue, old, neww) -> {
            setTable((Bankaccount) neww);
        });
    }

    void setTable(Bankaccount newValue){
         String accountnumber = newValue.getAccountnumber(); //TODO: gives null when you press loadmoreBtn!
         List<Transaction> trans = DB.getTransactionOfBankaccount(accountnumber, limit, offset);

        //PropertyValueFactory will fetch the necessary data from your object
        timeC.setCellValueFactory(new PropertyValueFactory<>("date"));
        messageC.setCellValueFactory(new PropertyValueFactory<>("message"));
        amountC.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tableViewTrans.getItems().clear();
        tableViewTrans.getItems().addAll(trans);
    }

    @FXML
    void loadMore(){ //TODO: Hämtar loadmore för alla konton!!
        limit = limit + 10;
        setChoiceBox();
    }

    @FXML void goToHome() { SwitchScene.switchScene("/app/home/home.fxml"); }

    @FXML void goToTransfer()  { SwitchScene.switchScene("/app/transfer/transfer.fxml"); }

}
