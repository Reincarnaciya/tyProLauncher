package tylauncher.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import tylauncher.Managers.ManagerWindow;


public class ConfirmController {
    @FXML
    private Button yes;
    @FXML
    private Button cancel;

    @FXML
    void initialize(){
        ManagerWindow.confirmController = this;
    }


    public Button getBtn(){
        return yes;
    }


}
