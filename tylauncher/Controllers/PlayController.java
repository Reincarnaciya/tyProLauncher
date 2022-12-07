package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.*;
import tylauncher.Utilites.Managers.ManagerStart;
import tylauncher.Utilites.Managers.ManagerUpdate;
import tylauncher.Utilites.Managers.ManagerZip;

import java.io.File;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;

public class PlayController {
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
        ManagerUpdate.playController = this;
        ManagerStart.playController = this;
        ErrorInterp.playController = this;
        ManagerZip.playController = this;

        if (ManagerStart.gameIsStart) setTextOfDownload("Игра запущена");
        if (ManagerZip.unzipping) ManagerZip.UpdateInfo();

        News_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("News.fxml", A1));
        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Settings_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Settings.fxml", A1));
        Account_Img.setOnMouseClicked(mouseEvent -> {
            try {
                if (user.Auth()) {
                    Main.OpenNew("Account.fxml", A1);
                    accountController.UpdateData();
                } else Main.OpenNew("AccountAuth.fxml", A1);
            } catch (Exception e) {
                Main.OpenNew("AccountAuth.fxml", A1);
            }
        });
        Play_Button.setOnMouseClicked(mouseEvent -> {
            setTextOfDownload("Инициализация..");

            if (ManagerStart.gameIsStart) {
                setTextOfDownload("Игра запущена");
                return;
            }
            if (ManagerZip.unzipping) {
                ManagerZip.UpdateInfo();
                return;
            }
            try {
                if (user.Auth()) {
                    if (!HashCodeCheck.CheckHashWithServer()) {
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "TySci_1.16.5"));
                        Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "client1165.zip"));
                        ManagerUpdate.DownloadUpdate("TySci_1.16.5", "https://www.typro.space/files/client_mc/client1165.zip");
                    } else ManagerStart.StartMinecraft("TySci_1.16.5");
                }
            } catch (Exception e) {
                ErrorInterp.setMessageError("Необходимо авторизоваться, прежде чем начать играть", "play");
            }

        });
    }

    public void setTextOfDownload(String text) {
        Play_Button.setVisible(false);
        Progressbar_Text.setText(text);
    }

    public void PlayButtonEnabled(boolean bool) {
        Play_Button.setVisible(bool);
    }

    public void UdpateProgressBar(double progress) {
        this.Download_ProgressBar.setProgress(progress);
    }
}
