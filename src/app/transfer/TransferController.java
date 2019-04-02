package app.transfer;

import app.Entities.Bankaccount;
import app.OutsideBank.CardPayment;
import app.db.DB;
import app.login.LoginController;
import app.switchScene.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import java.util.List;

public class TransferController {

    @FXML
    Button goToAccountBtn;
    @FXML
    Button overviewbtn;
    @FXML
    ChoiceBox choiceBoxFM1;
    @FXML
    ChoiceBox choiceBoxFM2;
    @FXML
    ChoiceBox choiceBoxTM;
    @FXML
    TextField textFieldTO;
    @FXML
    TextField amountFieldM;
    @FXML
    TextField amountFieldO;
    @FXML
    Button sendmoneybtn;
    @FXML
    TextField messagefield;
    @FXML
    Button sendMoneyTO;
    @FXML
    TextField messagefield2;
    @FXML
    Button cardpaymentT;

    private List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId());
    private Bankaccount bankaccount = null;
    private CardPayment cardPayment = new CardPayment();
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String message;
    private double oldBalanceFROM;
    private double newBalanceFROM;

    @FXML
    private void initialize(){
        System.out.println("initialize transfer");
        populateChoiceBoxes();
    }

    void populateChoicebox(ChoiceBox choicebox){
        choicebox.getItems().addAll(accounts);

        choicebox.setConverter(new StringConverter<Bankaccount>() {
            @Override
            public String toString(Bankaccount object) {
                return object.getType() + " (" + object.getAccountnumber() + ")";
            }

            @Override public Bankaccount fromString(String string) { return null; }
        });
        choicebox.setValue(accounts.get(0));
    }

    void populateChoiceBoxes(){
        populateChoicebox(choiceBoxFM1);
        populateChoicebox(choiceBoxTM);
        populateChoicebox(choiceBoxFM2);
    }

    /**Send money between your own accountnumbers*/
    void insertInTransactionHistory() {
        fromAccount = ((Bankaccount) choiceBoxFM1.getSelectionModel().selectedItemProperty().get()).getAccountnumber();
        toAccount = ((Bankaccount) choiceBoxTM.getSelectionModel().selectedItemProperty().get()).getAccountnumber();
        amount = Double.valueOf(amountFieldM.getText());
        message = messagefield.getText();

        DB.transactionToOwnAccounts(message, amount, fromAccount, toAccount);
    }

    void updateBalanceInFromBankaccount(){
        oldBalanceFROM = ((Bankaccount) choiceBoxFM1.getSelectionModel().selectedItemProperty().get()).getAmount();
        newBalanceFROM = oldBalanceFROM - amount;

        DB.updateAmountInBankaccount(newBalanceFROM, fromAccount);
    }

    void updateBalanceInToBankaccount(){
        double oldBalanceTO = ((Bankaccount) choiceBoxTM.getSelectionModel().selectedItemProperty().get()).getAmount();
        double newBalanceTO = oldBalanceTO + amount;

        DB.updateAmountInBankaccount(newBalanceTO, toAccount);
        System.out.println("Transfer success");
    }

    @FXML void sendMoney(){
        insertInTransactionHistory();
        updateBalanceInFromBankaccount();
        updateBalanceInToBankaccount();
        SwitchScene.switchScene("/app/transfer/transfer.fxml");
    }


    /**Send money to other accountnumbers*/
    void insertInTransactionHistoryOther() {
        fromAccount = ((Bankaccount) choiceBoxFM2.getSelectionModel().selectedItemProperty().get()).getAccountnumber();
        toAccount = textFieldTO.getText();
        amount = Double.valueOf(amountFieldO.getText());
        message = messagefield2.getText();

        //is reciver in DB:
        Bankaccount reciver = DB.getAccountFromAccountnumber(toAccount);
        if (reciver != null) {
            DB.transactionToOwnAccounts(message, amount, fromAccount, toAccount);
        }else{
            System.out.println("Accountnumber do not exists in swedenbanksdatabas!");
        }
    }

    void UpdateBalanceFrombankaccountOther() {
        oldBalanceFROM = ((Bankaccount) choiceBoxFM2.getSelectionModel().selectedItemProperty().get()).getAmount();
        newBalanceFROM = oldBalanceFROM - amount;

        DB.updateAmountInBankaccount(newBalanceFROM, fromAccount);
    }

    void UpdateBalanceToBankaccountOther() {
        bankaccount = DB.getAmountOfAccountNumber(toAccount);
        double oldBalanceTO = bankaccount.getAmount();
        double newBalanceTO = oldBalanceTO + amount;

        DB.updateAmountInBankaccount(newBalanceTO, toAccount);
    }

    @FXML void sendMoneyToOther(){
        insertInTransactionHistoryOther();
        UpdateBalanceFrombankaccountOther();
        UpdateBalanceToBankaccountOther();
        System.out.println("Transfer success");
        SwitchScene.switchScene("/app/transfer/transfer.fxml");
    }

    @FXML void goToAccount() { SwitchScene.switchScene("/app/transactionsHistory/transactionsHistory.fxml"); }

    @FXML void goToHome() { SwitchScene.switchScene("/app/home/home.fxml"); }

    @FXML void cardPayment(){cardPayment.drawMoneyFromCardAccount();}


}
