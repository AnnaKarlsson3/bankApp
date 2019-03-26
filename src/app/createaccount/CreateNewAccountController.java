package app.createaccount;

import app.Entities.Bankaccount;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CreateNewAccountController {

    @FXML
    ChoiceBox choiceNewAccount;
    @FXML
    TextField textfieldAccontName;
    @FXML
    Button createbtn;
    @FXML
    ChoiceBox chooseAccountDelete;
    @FXML
    Button deletebtn;
    @FXML
    ChoiceBox chooseAccountName;
    @FXML
    TextField textfieldNewAccountName;
    @FXML
    Button updatebtn;

    List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId());
    String newAccountNumber;

    @FXML
    private void initialize(){
        populateCreateNewAccountChoiceBox();
        populateDeleteChoiceBox();
        populateUpdateChoiceBox();
    }

    void populateCreateNewAccountChoiceBox(){
        List <String> typeChoices = new ArrayList<>();
        typeChoices.add("Salaryaccount");
        typeChoices.add("Cardaccount");
        typeChoices.add("Savingsaccount");
        typeChoices.add("Otheraccount");

        choiceNewAccount.getItems().addAll(typeChoices);

        choiceNewAccount.setValue(typeChoices.get(0));
    }

    void createRandomAccountNumber(){
        boolean numberExcistNotNull = true;
        while(numberExcistNotNull) {
            Random random = new Random();
            List<Long> randomList = random.longs(11, 0, 10).boxed().collect(Collectors.toList());

            StringBuilder sb = new StringBuilder();

            for (Long s : randomList) {
                sb.append(s);
            }

            sb.toString();
            newAccountNumber = String.valueOf(sb);
            System.out.println(newAccountNumber);

            //check if accountnumber already exict in DB
            Bankaccount numberExcist = DB.getAccountFromAccountnumber(newAccountNumber);
            if (numberExcist == null) {
                numberExcistNotNull = false;
            }
            else{
                System.out.println("generating new accountnumber");
            }

        }
    }

    @FXML void createAccount(){

        createRandomAccountNumber();
        String accountName = textfieldAccontName.getText();
        double amount = 0;
        String type = (String) choiceNewAccount.getSelectionModel().selectedItemProperty().get();

        DB.createNewAccount(newAccountNumber, type, accountName, amount, LoginController.getUser().getId());
    }


    //populera choicebox med accountnumbers skicka vald accountnumber till DB.delete
    void populateDeleteChoiceBox (){

        chooseAccountDelete.getItems().addAll(accounts);

        chooseAccountDelete.setConverter(new StringConverter<Bankaccount>() {
            @Override
            public String toString(Bankaccount object) {
                return object.getType() + " (" + object.getAccountnumber() + ")";
            }

            @Override public Bankaccount fromString(String string) { return null; }
        });
        chooseAccountDelete.setValue(accounts.get(0));
    }

    @FXML void deleteAccout(){
        String accountnumber = ((Bankaccount) chooseAccountDelete.getSelectionModel().selectedItemProperty().get()).getAccountnumber();

        DB.deleteAccountNumber(accountnumber);
    }

    void populateUpdateChoiceBox(){
        chooseAccountName.getItems().addAll(accounts);

        chooseAccountName.setConverter(new StringConverter<Bankaccount>() {
            @Override
            public String toString(Bankaccount object) {
                return object.getAccountname();
            }

            @Override public Bankaccount fromString(String string) { return null; }
        });
        chooseAccountName.setValue(accounts.get(0));
    }

    @FXML void updateAccountName(){
        //uppdatera accountname i db med det nya
        String oldAccountName = ((Bankaccount) chooseAccountDelete.getSelectionModel().selectedItemProperty().get()).getAccountname();
        String newAccountName = textfieldNewAccountName.getText();

        DB.updateAccountNameInBankaccount(oldAccountName, newAccountName, LoginController.getUser().getId());

    }
}
