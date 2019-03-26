package app.home;

import app.Entities.Bankaccount;
import app.Main;
import app.OutsideBank.CardPayment;
import app.OutsideBank.Salary;
import app.db.DB;
import app.login.LoginController;
import app.switchScene.SwitchScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class HomeController {

    @FXML
    Label userLabel;
    @FXML
    TableView accountTable;
    @FXML
    TableColumn accountNameC;
    @FXML
    TableColumn accounttypeC;
    @FXML
    TableColumn accountNumberC;
    @FXML
    TableColumn amountC;
    @FXML
    Button goToAccountBtn;
    @FXML
    Button transferbtn;
    @FXML
    Button cardpaymentH;
    @FXML
    Button createNewAccountbtn;
    @FXML
    Button deleteAccountbtn;
    @FXML
    Button  changeAccountNamebtn;


    Salary salaryClass = new Salary();
    Thread salaryThread = new Thread(salaryClass, "salaryThread");

    @FXML
    private void initialize(){
        // load accounts from db using LoginController.user.getId() and display them
        setUsernameLable();
        setTableview();
        salaryThread.start();
    }


    @FXML
    void setTableview(){
        List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId()); //skickar userID till DB

        //PropertyValueFactory will fetch the necessary data from your object
        accountNameC.setCellValueFactory(new PropertyValueFactory<>("accountname"));
        accounttypeC.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountNumberC.setCellValueFactory(new PropertyValueFactory<>("accountnumber"));
        amountC.setCellValueFactory(new PropertyValueFactory<>("amount"));

        accountTable.getItems().addAll(accounts);
    }

    @FXML
    void setUsernameLable(){
        userLabel.setText( LoginController.getUser().getFirstname() + " " + LoginController.getUser().getLastname());
    }



    @FXML void goToAccount() { SwitchScene.switchScene("/app/transactionsHistory/transactionsHistory.fxml"); }

    @FXML void goToTransfer()  {
        SwitchScene.switchScene("/app/transfer/transfer.fxml");
    }

    @FXML void goToCreateNewAccount(){SwitchScene.switchScene("/app/createaccount/createNewAccount.fxml");}

    @FXML void cardPayment(){
    }
}
