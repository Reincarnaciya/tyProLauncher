package tylauncher.Utilites;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tylauncher.Controllers.*;
import tylauncher.Main;

import java.io.IOException;
import java.util.Objects;



public class ErrorInterp {
    public static AccountAuthController accountAuthController;
    public static RegisterController registerController;
    public static ErrorController errorController;
    public static SettingsController settingsController;
    public static PlayController playController;


    //переписать всю эту поеботу из многооконности в другую поеботу, с которой ебанины будет меньше
    public static void setMessageError(String error, String where){
        if(where.equalsIgnoreCase("accountauth")){
            accountAuthController.setInfoText(error);
        }else if(where.equalsIgnoreCase("register")){
            registerController.setInfoText(error);
        }else if(where.equalsIgnoreCase("settings")){
            settingsController.setInfoText(error);
        }else if(where.equalsIgnoreCase("play")){
            playController.setTextOfDownload(error);
        }

    }
}
