package app.home;

import app.Entities.Bankaccount;
import app.Main;
import app.OutsideBank.CardPayment;
import app.db.DB;
import app.login.LoginController;
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
    ListView listView;
    @FXML
    Label userLabel;
    @FXML
    TableView accountTable;
    @FXML
    TableColumn accountNameC;
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

    private CardPayment cardPayment = new CardPayment();

    @FXML
    private void initialize(){
        // load accounts from db using LoginController.user.getId() and display them
        setUsernameLable();
        setTableview();
    }


    @FXML
    void setTableview(){
        List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId()); //skickar userID till DB

        //PropertyValueFactory will fetch the necessary data from your object
        accountNameC.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountNumberC.setCellValueFactory(new PropertyValueFactory<>("accountnumber"));
        amountC.setCellValueFactory(new PropertyValueFactory<>("amount"));

        accountTable.getItems().addAll(accounts);
    }

    @FXML
    void setUsernameLable(){
        userLabel.setText( LoginController.getUser().getFirstname() + " " + LoginController.getUser().getLastname());
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

    @FXML void goToAccount() { switchScene("/app/transactionHistory/transactionsHistory.fxml"); }

    @FXML void goToTransfer()  {
        switchScene("/app/transfer/transfer.fxml");
    }

    @FXML void cardPayment(){
        cardPayment.drawMoneyFromCardAccount();
    }
}
