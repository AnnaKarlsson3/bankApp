package app.transfer;

import app.Entities.Bankaccount;
import app.Main;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
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
    Label sentlabel;
    @FXML
    Button sendMoneyTO;
    @FXML
    TextField messagefield2;
    @FXML
    Label sentlabel2;
    @FXML
    Button cardpaymentT;

    private List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId());
    private Bankaccount bankaccount = null;

    @FXML
    private void initialize(){
        System.out.println("initialize transfer");
        populateChoiceBoxes();
        sentlabel.setVisible(false);
        sentlabel2.setVisible(false);
    }

    void populateChoicebox(ChoiceBox choicebox){
        //populate choiceBox from bankaccount
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

    void fromMyAccountToMyAccount(){
        //Send money between your own accountnumber
        //Log in transactions
        String fromAccount = ((Bankaccount) choiceBoxFM1.getSelectionModel().selectedItemProperty().get()).getAccountnumber();
        String toAccount = ((Bankaccount) choiceBoxTM.getSelectionModel().selectedItemProperty().get()).getAccountnumber();
        Double amount = Double.valueOf(amountFieldM.getText());
        String message = messagefield.getText();

        DB.transactionToOwnAccounts(message, amount, fromAccount, toAccount);

        //Update balance/amount in FROM bankaccount
        Double oldBalanceFROM = ((Bankaccount) choiceBoxFM1.getSelectionModel().selectedItemProperty().get()).getAmount();
        Double newBalanceFROM = oldBalanceFROM - amount;

        DB.updateAmountInBankaccount(newBalanceFROM, fromAccount);

        //Update balance/amount in TO bankaccount
        Double oldBalanceTO = ((Bankaccount) choiceBoxTM.getSelectionModel().selectedItemProperty().get()).getAmount();
        Double newBalanceTO = oldBalanceTO + amount;

        DB.updateAmountInBankaccount(newBalanceTO, toAccount);
    }

    @FXML void sendMoney(){
        fromMyAccountToMyAccount();
        sentlabel.setVisible(true);

    }

    void fromMyAccountToOtherAccount(){
    //hämta type och accountnumber från bankaccount
    //Läs in banknummer från textfield
        String fromAccount = ((Bankaccount) choiceBoxFM2.getSelectionModel().selectedItemProperty().get()).getAccountnumber();
        String toAccount = textFieldTO.getText();
        Double amount = Double.valueOf(amountFieldO.getText());
        String message = messagefield2.getText();

        Bankaccount reciver = DB.getAccountFromAccountnumber(toAccount);

        //TODO kolla om det är 0 på kontot!
        //kollar om motagare finns i databasen
        if(reciver != null) {
        DB.transactionToOwnAccounts(message, amount, fromAccount, toAccount);

        //Update balance/amount in FROM bankaccount
        Double oldBalanceFROM = ((Bankaccount) choiceBoxFM2.getSelectionModel().selectedItemProperty().get()).getAmount();
        Double newBalanceFROM = oldBalanceFROM - amount;

        DB.updateAmountInBankaccount(newBalanceFROM, fromAccount);


        //TODO kolla om man skickar kontonr utanför db - if(toAccount.equals())
            //Update balance/amount in TO bankaccount
            bankaccount = DB.getAmountOfAccountNumber(toAccount);
            Double oldBalanceTO = bankaccount.getAmount();
            Double newBalanceTO = oldBalanceTO + amount;

            DB.updateAmountInBankaccount(newBalanceTO, toAccount);
        }
        else{
            System.out.println("Kontonummer finns ej i swedenbanksdatabas!");
        }
    }

    @FXML void sendMoneyToOther(){
        fromMyAccountToOtherAccount();
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

    @FXML void goToAccount() { switchScene("/app/transactionsHistory/transactionsHistory.fxml"); }


    @FXML void goToHome()  {
        switchScene("/app/home/home.fxml");

    }

    @FXML void cardPayment(){}


}
