package app.switchScene;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SwitchScene {


    public SwitchScene(String pathname){
        switchScene(pathname);
    }

    public static void switchScene(String pathname) {
        try {
            Parent bla = FXMLLoader.load(SwitchScene.class.getResource(pathname));
            Scene scene = new Scene(bla, 800 , 600);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
