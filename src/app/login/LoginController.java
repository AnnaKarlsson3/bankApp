package app.login;

import app.Entities.User;
import app.db.DB;
import app.switchScene.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label errorlable;


    // Use this in other Controllers to get "the currently logged in user".
    private static User user = null;
    public static User getUser() { return user; }

    @FXML
    private void initialize() {
        System.out.println("initialize login");
        errorlable.setVisible(false);
    }

    @FXML
    void loadUser(){
       user = DB.getMatchingUser(username.getText(), password.getText());

        if (user == null){
            System.out.println("error");
            errorlable.setVisible(true);
        }
        else{
            goToHome();
        }
    }

    @FXML void goToHome() {SwitchScene.switchScene("/app/home/home.fxml"); }
}
