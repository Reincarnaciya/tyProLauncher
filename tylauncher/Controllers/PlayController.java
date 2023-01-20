package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.HashCodeCheck;
import tylauncher.Utilites.Managers.*;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.Utils;

import java.io.File;

import static tylauncher.Main.user;

public class PlayController extends BaseController{
    @FXML
    private AnchorPane A1;
    @FXML
    private ImageView Account_Img;
    @FXML
    private ImageView Forum_Img;
    @FXML
    private ImageView Message_Img;
    @FXML
    private ImageView News_Img;
    @FXML
    private ImageView Play_Img;
    @FXML
    private ImageView Settings_Img;
    @FXML
    private Button Play_Button;
    @FXML
    private Text Progressbar_Text;
    @FXML
    private ProgressBar Download_ProgressBar;

    @FXML
    void initialize() {
        ManagerZip.playController = this;
        ManagerStart.playController = this;
        ManagerUpdate.playController = this;

        initPageButton();

        //Проверка на статус.. Чего? а, на статус того, что вообще происходит в лаунчере
        if (ManagerFlags.gameIsStart){
            UdpateProgressBar(1);
            ManagerWindow.currentController.setInfoText("Игра запущена");
        }
        if (ManagerZip.unzipping){
            UdpateProgressBar(1);
            ManagerZip.UpdateInfo();
        }

        //Улавливаем ивент нажатия на кнопку "Играть"
        Play_Button.setOnMouseClicked(mouseEvent -> {
            ManagerWindow.currentController.setInfoText("Инициализация..");
            if (ManagerFlags.gameIsStart) {
                ManagerWindow.currentController.setInfoText("Игра запущена");
                return;
            }
            if (ManagerZip.unzipping) {
                ManagerZip.UpdateInfo();
                return;
            }
            try {
                if (!user.auth()) {
                    ManagerWindow.currentController.setInfoText("Необходимо авторизоваться, прежде чем начать играть");
                    return;
                }
            }catch (Exception e){
                ManagerWindow.currentController.setInfoText("Необходимо авторизоваться, прежде чем начать играть");
                e.printStackTrace();
                return;
            }
            try {
                if (!HashCodeCheck.CheckHashWithServer()) {
                    if (new File(Main.getClientDir() + File.separator + "TySci_1.16.5").exists())
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "TySci_1.16.5"));
                    if (new File(Main.getClientDir() + File.separator + "client1165.zip").exists())
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "client1165.zip"));
                    ManagerUpdate.DownloadUpdate("TySci_1.16.5", "https://www.typro.space/files/client_mc/client1165.zip");
                    return;
                }
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText(e.getMessage());
                e.printStackTrace();
                return;
            }
            try {
                ManagerStart starter = new ManagerStart(Settings.isAutoConnect(), Settings.getFsc(), "TySci_1.16.5");
                starter.Start();
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText(e.getMessage());
                e.printStackTrace();
            }
        });
    }
    @Override
    public void setInfoText(String text) {
        Platform.runLater(()->{
            Play_Button.setVisible(false);
            Progressbar_Text.setText(text);
        });

    }

    public void PlayButtonEnabled(boolean bool) {
        Play_Button.setVisible(bool);
    }

    public void UdpateProgressBar(double progress) {
        this.Download_ProgressBar.setProgress(progress);
    }

    public AnchorPane getA1(){
        return A1;
    }
}
