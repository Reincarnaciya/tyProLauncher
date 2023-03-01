package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Managers.*;
import tylauncher.Utilites.Constants.Lists;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.HashCodeCheck;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.Utils;

import java.io.File;

import static tylauncher.Main.user;

public class PlayController extends BaseController {
    private static final Logger logger = new Logger(PlayController.class);
    private static ProgressBar fpb;
    private static Text ftx;
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
        ManagerWindow.playController = this;
        initPageButton();
        ManagerDownload.playController = this;


        ManagerZip.playController = this;
        ManagerStart.playController = this;
        ManagerUpdate.playController = this;


        if (ManagerDownload.download) {
            ManagerUpdate.update.updateInfo();
        }

        if (ManagerZip.unzipping) {
            Play_Button.setVisible(false);
            ManagerZip.updateInfo(this);
            Download_ProgressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        }


        //Проверка на статус.. Чего? а, на статус того, что вообще происходит в лаунчере
        if (ManagerFlags.gameIsStart) {
            UdpateProgressBar(1);
            ManagerWindow.currentController.setInfoText("Игра запущена");
        }

        //Улавливаем ивент нажатия на кнопку "Играть"
        Play_Button.setOnMouseClicked(mouseEvent -> {
            logger.logInfo("Инициализация", ManagerWindow.currentController);
            try {
                if (!user.auth()) {
                    logger.logInfo("Необходимо авторизоваться, прежде чем начать играть", ManagerWindow.currentController);
                    return;
                }
            } catch (Exception e) {
                logger.logInfo("Необходимо авторизоваться, прежде чем начать играть", ManagerWindow.currentController);
                logger.logError(e);
                return;
            }
            try {
                HashCodeCheck hashCodeCheck = new HashCodeCheck(Lists.skippingFiles, Lists.skippingDirictories, Lists.allowedFiles, "TySci_1.16.5");
                if (!hashCodeCheck.CalcAndCheckWithServer(Main.getClientDir() + File.separator + "TySci_1.16.5")) {
                    if (new File(Main.getClientDir() + File.separator + "TySci_1.16.5").exists())
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "TySci_1.16.5"));
                    if (new File(Main.getClientDir() + File.separator + "client1165.zip").exists())
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "client1165.zip"));
                    new Thread(() -> {
                        switch (ManagerDirs.getPlatform()) {
                            case windows:
                                ManagerUpdate.DownloadUpdate("TySci_1.16.5", URLS.CLIENT_TY_SCI_WIN,
                                        Download_ProgressBar, Progressbar_Text);
                                return;
                            case linux:
                                ManagerUpdate.DownloadUpdate("TySci_1.16.5", URLS.CLIENT_TY_SCI_LINUX,
                                        Download_ProgressBar, Progressbar_Text);
                                return;
                            case macos:
                                ManagerUpdate.DownloadUpdate("TySci_1.16.5", URLS.CLIENT_TY_SCI_MACOS,
                                        Download_ProgressBar, Progressbar_Text);
                                return;
                            default:
                                throw new RuntimeException("Не удалось инициализировать ОС");
                        }
                    }).start();
                    return;
                }
            } catch (Exception e) {
                logger.logError(e, ManagerWindow.currentController);
                return;
            }
            try {
                ManagerStart starter = new ManagerStart(Settings.isAutoConnect(), Settings.getFsc(), "TySci_1.16.5");
                starter.Start();
            } catch (Exception e) {
                logger.logError(e, ManagerWindow.currentController);
            }
        });
    }

    @Override
    public void setInfoText(String text) {
        try {
            Platform.runLater(() -> {
                Play_Button.setVisible(false);
                Progressbar_Text.textProperty().setValue(text);
            });
        } catch (Exception e) {
            logger.logError(e);
        }
    }

    public void PlayButtonEnabled(boolean bool) {
        Play_Button.setVisible(bool);
    }

    public ProgressBar getProgressBar() {
        return Download_ProgressBar;
    }

    public void UdpateProgressBar(double progress) {
        this.Download_ProgressBar.setProgress(progress);
    }

    public AnchorPane getA1() {
        return A1;
    }
}
