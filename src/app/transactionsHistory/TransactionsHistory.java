package app.transactionsHistory;


import app.Entities.Bankaccount;
import app.Entities.Transaction;
import app.Main;
import app.db.DB;
import app.login.LoginController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class TransactionsHistory {

    @FXML
    VBox transactionBox;
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
    @FXML
    Button cardpaymentA;

    private int offset = 0;
    private int limit = 10;
    private  List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId());

    @FXML
    private void initialize(){
        System.out.println("initialize transactionsHistory");
        setChoiceBox();
        listener();
    }

    void setChoiceBox(){

        choiceBox.getItems().addAll(accounts);

        choiceBox.setConverter(new StringConverter<Bankaccount>() {
            @Override
            public String toString(Bankaccount object) {
                return object.getType();
            }

            @Override public Bankaccount fromString(String string) { return null; }
        });
        choiceBox.setValue(accounts.get(0));
        setTable(accounts.get(0));

    }

    void listener() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observableValue, old, neww) -> {
            setTable((Bankaccount) neww);
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
        choiceBox.getItems().clear();
        setChoiceBox();
    }



    @FXML void switchScene(String pathname)  {
        try {
            Parent bla = FXMLLoader.load(getClass().getResource(pathname));
            Scene scene = new Scene(bla, 800 , 600);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML void goToHome() { switchScene("/app/home/home.fxml"); }


    @FXML void goToTransfer()  {
        switchScene("/app/transfer/transfer.fxml");

    }

    @FXML void cardPayment(){}



}
