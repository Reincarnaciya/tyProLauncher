package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tylauncher.Main;
import tylauncher.Utilites.UpdaterLauncher;

public class UpdaterController {
    @FXML
    private ProgressIndicator IndicatorUpdater;

    @FXML
    private Text TextUpdater;

    @FXML
    protected AnchorPane A1;

    @FXML
    private Pane UpdateOrNoPane;

    @FXML
    private Pane UpdatingPane;

    @FXML
    private Button LaterButton;

    @FXML
    private Button UpdateButton;

    public static boolean updateAvailable = false;

    @FXML
    void initialize(){
        UpdaterLauncher.updaterController = this;

        LaterButton.setOnMouseClicked(mouseEvent -> {
            System.err.println("Later");
            Main.OpenNew("AccountAuth.fxml", A1);
        });
        UpdateButton.setOnMouseClicked(mouseEvent ->{
            System.err.println("Update");
            UpdaterLauncher.UpdateLauncher();
            updateAvailable = false;
        });
    }
    public void setUpdateAvailable(boolean available){
        updateAvailable = available;
        TextUpdater.setText("Найдено обновление!");
        if(available){
            Stage stage = (Stage) TextUpdater.getScene().getWindow();
            Platform.runLater(() -> stage.setTitle("Найдено обновление"));
            UpdatingPane.setVisible(false);
            UpdatingPane.setDisable(true);
            UpdateOrNoPane.setVisible(true);
            UpdateOrNoPane.setDisable(false);

        }
    }

    public AnchorPane getA1(){
        return this.A1;
    }
}