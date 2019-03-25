package app.home;

import app.Entities.Bankaccount;
import app.Entities.User;
import app.Main;
import app.account.AccountController;
import app.cardPayment.CardPayment;
import app.db.DB;
import app.login.LoginController;
import app.transaction.TransactionController;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

   /* @FXML
    void setListView(){
        List<Bankaccount> accounts = DB.getaccountsOfUser(LoginController.getUser().getId());
        List<String> collect = accounts.stream().map(x -> x.getType() + ": " + x.getAccountnumber() + "    Amount: " + x.getAmount() + "kr" ).collect(Collectors.toList());
        listView.setStyle("-fx-font-weight: bold");
        listView.getItems().addAll(collect);
        //listView.getSelectionModel().getSelectionMode(SelectionMode.valueOf());
    }*/

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


    /*@FXML
    void goToAccount() throws IOException {
        //accountTable.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        Scene scene = new Scene( fxmlInstance, 800, 600 );

        // Make sure that you display "the correct account" based on which one you clicked on
//            AccountController controller = loader.getController();
//            controller.setAccount(accountFromDB);

        // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        Main.stage.setScene(scene);
        Main.stage.show();

    }*/

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

    @FXML void goToAccount() { switchScene("/app/account/account.fxml"); }


    @FXML void goToTransfer()  {
        switchScene("/app/transfer/transfer.fxml");

    }

    @FXML void cardPayment(){
        cardPayment.drawMoneyFromCardAccount();
    }

}
