package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tylauncher.Utilites.ErrorInterp;

public class ErrorController {
    @FXML
    private Label Error_Text;

    @FXML
    void initialize() {
        ErrorInterp.errorController = this;
    }

}
