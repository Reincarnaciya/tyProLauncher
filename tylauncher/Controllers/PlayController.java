package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Managers.*;
import tylauncher.Utilites.HashCodeCheck;
import tylauncher.Utilites.Lists;
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

    private static ProgressBar fpb;

    private static Text ftx;

    @FXML
    void initialize() {
        initPageButton();
        ManagerDownload.playController = this;


        ManagerZip.playController = this;
        ManagerStart.playController = this;
        ManagerUpdate.playController = this;



        if (ManagerZip.unzipping){
            Play_Button.setVisible(false);
            ManagerZip.updateInfo(this);
            System.err.println("status = unzipping");
            return;
        }

        if(ManagerDownload.download) {
            ManagerUpdate.update.updateInfo();
            System.err.println("status = download");
            return;
        }

        //Проверка на статус.. Чего? а, на статус того, что вообще происходит в лаунчере
        if (ManagerFlags.gameIsStart){
            UdpateProgressBar(1);
            ManagerWindow.currentController.setInfoText("Игра запущена");
        }

        //Улавливаем ивент нажатия на кнопку "Играть"
        Play_Button.setOnMouseClicked(mouseEvent -> {
            ManagerWindow.currentController.setInfoText("Инициализация..");

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
                HashCodeCheck hashCodeCheck = new HashCodeCheck(Lists.skippingFiles, Lists.skippingDirictories,Lists.allowedFiles,"TySci_1.16.5");
                if (!hashCodeCheck.CalcAndCheckWithServer(Main.getClientDir() + File.separator + "TySci_1.16.5")) {
                    if (new File(Main.getClientDir() + File.separator + "TySci_1.16.5").exists())
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "TySci_1.16.5"));
                    if (new File(Main.getClientDir() + File.separator + "client1165.zip").exists())
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "client1165.zip"));
                    new Thread(()->
                            ManagerUpdate.DownloadUpdate("TySci_1.16.5", "https://www.typro.space/files/client_mc/client1165.zip",
                                    Download_ProgressBar, Progressbar_Text)).start();

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
        try {
            Platform.runLater(()->{
                Play_Button.setVisible(false);
                Progressbar_Text.textProperty().setValue(text);
            });
        }catch (Exception e){
            System.err.println("Не вышло");
            e.printStackTrace();
        }

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
