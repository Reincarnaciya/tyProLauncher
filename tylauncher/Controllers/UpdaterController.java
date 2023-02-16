package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.UpdaterLauncher;

public class UpdaterController extends BaseController {
    private final String suffix = "[" + this.getClass().getName() + "] ";
    @FXML
    protected AnchorPane A1;
    @FXML
    private ProgressIndicator IndicatorUpdater;
    @FXML
    private Text TextUpdater;
    @FXML
    private Pane UpdateOrNoPane;
    @FXML
    private Pane UpdatingPane;
    @FXML
    private Button LaterButton;

    @FXML
    private Button UpdateButton;

    @FXML
    void initialize() {
        UpdaterLauncher.updaterController = this;
        ManagerWindow.currentController = this;

        LaterButton.setOnMouseClicked(mouseEvent -> ManagerWindow.ACCOUNT_AUTH.open());

        UpdateButton.setOnMouseClicked(mouseEvent -> UpdaterLauncher.UpdateLauncher());
    }

    public void setUpdateAvailable(boolean available) {
        ManagerFlags.updateAvailable = available;
        TextUpdater.setText("Найдено обновление!");
        if (available) {
            Stage stage = (Stage) TextUpdater.getScene().getWindow();
            Platform.runLater(() -> stage.setTitle("Найдено обновление"));
            UpdatingPane.setVisible(false);
            UpdatingPane.setDisable(true);
            UpdateOrNoPane.setVisible(true);
            UpdateOrNoPane.setDisable(false);
        }
    }

    @Override
    void initPageButton() {
        System.err.println("nothing to init");
    }

    @Override
    public void setInfoText(String info) {
        Platform.runLater(() -> TextUpdater.setText(info));
    }

    public AnchorPane getA1() {
        return this.A1;
    }
}
