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
        ButtonPageController buttonPageController = new ButtonPageController();
        buttonPageController.addButton(Account_Img);
        buttonPageController.addButton(News_Img);
        buttonPageController.addButton(Forum_Img);
        buttonPageController.addButton(Message_Img);
        buttonPageController.addButton(Settings_Img);
        buttonPageController.addButton(Play_Img);
        //Проверка на статус.. Чего? а, на статус того, что вообще происходит в лаунчере
        if (ManagerStart.gameIsStart) ManagerWindow.currentController.setInfoText("Игра запущена");
        if (ManagerZip.unzipping) ManagerZip.UpdateInfo();

        //Улавливаем ивент нажатия на кнопку "Играть"
        Play_Button.setOnMouseClicked(mouseEvent -> {
            try {
                if(user.Auth()) ManagerStart.StartMinecraft("TySci_1.16.5");
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText(e.getMessage());
            }
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
                        try {
                            Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "TySci_1.16.5"));
                            Utils.DeleteFile(new File(Main.getClientDir() + File.separator + "client1165.zip"));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        ManagerUpdate.DownloadUpdate("TySci_1.16.5", "https://www.typro.space/files/client_mc/client1165.zip");
                    } else {
                        ManagerStart.StartMinecraft("TySci_1.16.5");
                    }
                }else {
                    ManagerWindow.currentController.setInfoText ("Необходимо авторизоваться, прежде чем начать играть");
                }
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText("Необходимо авторизоваться, прежде чем начать играть");
                e.printStackTrace();
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
