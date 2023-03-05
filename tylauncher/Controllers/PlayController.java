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
import tylauncher.Utilites.Constants.Dirs;
import tylauncher.Utilites.Constants.HashCodeCheckerConstants;
import tylauncher.Utilites.Constants.Menus;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.HashCodeCheck;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.Utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

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
    private Text TySciClientSettings;

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

        if (ManagerFlags.gameIsStart) {
            UdpateProgressBar(1);
            ManagerWindow.currentController.setInfoText("Игра запущена");
        }

        TySciClientSettings.setOnContextMenuRequested(event -> {
            Menus.TySciSettings.getContextMenu().show(TySciClientSettings, event.getScreenX(), event.getScreenY());
        });

        //Улавливаем ивент нажатия на кнопку "Играть"
        Play_Button.setOnMouseClicked(mouseEvent -> {
            logger.logInfo("Инициализация", ManagerWindow.currentController);
            if (!userAuthed()) return;
            try {
                StringBuilder clientHash = new StringBuilder();
                for (int i = 0; i < HashCodeCheckerConstants.clientWhatToCheck.length; i++){
                    System.err.println(i);
                    clientHash.append(new HashCodeCheck(Paths.get(
                            Main.getClientDir().getAbsolutePath()
                                    + File.separator
                                    + Dirs.TYSCI
                                    + File.separator
                                    + HashCodeCheckerConstants.clientWhatToCheck[i])
                    ).calculateHashCode());
                }
                String hash = clientHash.toString();
                logger.logInfo("CLIENT[" + Dirs.TYSCI + "]HASH", hash);
                new Thread(()->{
                    if (hashNorm(hash, Dirs.TYSCI)) {
                        startMinecraft(Dirs.TYSCI);
                    }
                }).start();
            } catch (Exception e) {
                logger.logError(e, ManagerWindow.currentController);
            }
        });
    }

    private void startMinecraft(String version){
        try {
            ManagerStart starter = new ManagerStart(Settings.isAutoConnect(), Settings.getFsc(), version);
            starter.Start();
        }catch (Exception e){
            logger.logError(e, ManagerWindow.currentController);
        }

    }

    private Runnable downloadClient(String url){
        return () -> {
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
                    logger.logError("Не удалось инициализировать ОС", ManagerWindow.currentController);
            }
        };
    }

    private void deleteClient(){
        if (new File(Main.getClientDir() + File.separator + "TySci_1.16.5").exists())
            Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "TySci_1.16.5"));
        if (new File(Main.getClientDir() + File.separator + "client1165.zip").exists())
            Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "client1165.zip"));
    }
    private boolean hashNorm(String hash, String client){
        try {
            ManagerWeb hashManager = new ManagerWeb("hashRequest");
            hashManager.setUrl(URLS.CLIENT_HASH);

            String vers;
            switch (client){
                case Dirs.TYSCI:
                    vers = "TY_SCI";
                    break;
                default:
                    vers = " ";
                    break;
            }

            hashManager.putAllParams(Arrays.asList("server", "os", "hash"), Arrays.asList(vers, ManagerDirs.getPlatform().toString(),hash));
            System.err.println(hashManager);
            hashManager.request();

            if (hashManager.getFullAnswer().contains("ERROR")){
                logger.logError(hashManager.getFullAnswer(), ManagerWindow.currentController);
                return false;
            }

            if (!"1".equals(hashManager.getFullAnswer())) {
                TySciClientSettings.setDisable(true);
                deleteClient();
                Thread download = new Thread(downloadClient(hashManager.getFullAnswer()));
                download.start();
                download.join();
            }
            return true;
        }catch (Exception e){
            logger.logError("Чет пошло не так: " + e.getMessage(), ManagerWindow.currentController);
            logger.logError(e);
            return false;
        }

    }


    private boolean userAuthed(){
        try {
            if (!user.auth()) {
                logger.logInfo("Необходимо авторизоваться, прежде чем начать играть", ManagerWindow.currentController);
                return false;
            }
        } catch (Exception e) {
            logger.logInfo("Необходимо авторизоваться, прежде чем начать играть", ManagerWindow.currentController);
            logger.logError(e);
            return false;
        }
        return true;
    }



    @Override
    public void setInfoText(String text) {
        Platform.runLater(() -> {
            Play_Button.setVisible(false);
            Progressbar_Text.textProperty().setValue(text);
        });
    }

    @Override
    public void unsetText(){
        Platform.runLater(()-> Play_Button.setVisible(true));
    }

    public ProgressBar getProgressBar() {
        return Download_ProgressBar;
    }

    public void UdpateProgressBar(double progress) {
        Platform.runLater(()-> this.Download_ProgressBar.setProgress(progress));
    }

    public AnchorPane getA1() {
        return A1;
    }
}
