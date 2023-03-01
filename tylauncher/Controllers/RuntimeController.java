package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.RuntimeDownload;

public class RuntimeController extends BaseController {
    private final String suffix = "[" + this.getClass().getName() + "] ";
    @FXML
    private AnchorPane A1;

    @FXML
    private Text infoText;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void initialize() {
        ManagerWindow.runtimeController = this;
        RuntimeDownload.runtimeController = this;
        ManagerWindow.currentController = this;
        RuntimeDownload.download(progressBar, infoText);
    }

    @Override
    public void setInfoText(String info) {
        Platform.runLater(() -> infoText.setText(info));
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
