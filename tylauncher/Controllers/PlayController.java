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
import tylauncher.Utilites.Managers.ManagerWindow;
import tylauncher.Utilites.Managers.ManagerZip;

import java.io.File;

import static tylauncher.Controllers.AccountAuthController.accountController;
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
        ManagerWindow.currentController = this;
        ManagerStart.playController = this;
        ManagerUpdate.playController = this;
        //все кнопки в 1 массив!
        ButtonPage.reset();
        ButtonPage.setPressedNum(6);
        BooleanPageController.addButton(Account_Img);
        BooleanPageController.addButton(News_Img);
        BooleanPageController.addButton(Forum_Img);
        BooleanPageController.addButton(Message_Img);
        BooleanPageController.addButton(Settings_Img);
        BooleanPageController.addButton(Play_Img);
        //Проверка на статус.. Чего? а, на статус того, что вообще происходит в лаунчере
        if (ManagerStart.gameIsStart) ManagerWindow.currentController.setInfoText("Игра запущена");
        if (ManagerZip.unzipping) ManagerZip.UpdateInfo();
        //Ивенты клика на картинки
        News_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("News.fxml", A1));
        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Settings_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Settings.fxml", A1));
        //В зависимости от того возможно ли авторизовать юзера кидаем его или в аккаунт или в авторизацию
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
        //Улавливаем ивент нажатия на кнопку "Играть"
        Play_Button.setOnMouseClicked(mouseEvent -> {
            ManagerWindow.currentController.setInfoText("Инициализация..");
            if (ManagerStart.gameIsStart) {
                ManagerWindow.currentController.setInfoText("Игра запущена");
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
                    } else {
                        System.err.println(Settings.show());
                        try {
                            ManagerStart.StartMinecraft("TySci_1.16.5");
                        } catch (Exception e) {
                            ManagerWindow.currentController.setInfoText(e.getMessage());
                        }


                    }
                }
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText ("Необходимо авторизоваться, прежде чем начать играть");
            }
        });
    }
    @Override
    public void setInfoText(String text) {
        Play_Button.setVisible(false);
        Progressbar_Text.setText(text);
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
