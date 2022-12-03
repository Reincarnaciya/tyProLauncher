package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tylauncher.Utilites.ErrorInterp;

import java.awt.*;

public class ErrorController {
    @FXML
    private Label Error_Text;




    @FXML
    void initialize(){
        ErrorInterp.errorController = this;
    }


}
