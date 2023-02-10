package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tylauncher.Utilites.RuntimeDownload;

public class RuntimeController extends BaseController{
    @FXML
    private AnchorPane A1;

    @FXML
    private Text infoText;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void initialize(){
        RuntimeDownload.runtimeController = this;
        Platform.runLater(()->{
            Stage stage = (Stage) A1.getScene().getWindow();
            //Меняем размеры окна и текст окна
            stage.setWidth(343);
            stage.setHeight(199);
            stage.centerOnScreen();
        });


        RuntimeDownload.download(progressBar, infoText);
    }

    @Override
    public void setInfoText(String info) {
        Platform.runLater(()-> infoText.setText(info));
    }

}
