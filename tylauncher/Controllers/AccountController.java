package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.Utils;

import java.io.File;

import static tylauncher.Main.user;

public class AccountController extends BaseController {
    private static final Logger logger = new Logger(AccountController.class);
    @FXML
    private Button donateButton;
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
    private ImageView Skin_Image;
    @FXML
    private Text Username_Text;
    @FXML
    private Text Balance_Text;
    @FXML
    private Text Group_Text;
    @FXML
    private Button Exit_Button;

    @FXML
    void initialize() {
        this.UpdateData();
        ManagerWindow.accountController = this;
        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        AccountAuthController.accountController = this;

        initPageButton();


        Exit_Button.setOnMouseClicked(mouseEvent -> logout());

        donateButton.setOnMouseClicked(event -> {
            try {
                Utils.openUrl(URLS.DONATE);
            } catch (Exception e) {
                logger.logError("Невозможно открыть ссылку:", e.toString());
            }
        });
    }

    //Обновление информации о юзере
    private void UpdateData() {
        Username_Text.setText(user.GetLogin());
        Balance_Text.setText(user.GetBalance());
        Group_Text.setText(user.GetGroup());
        Skin_Image.setImage(user.GetImage());
    }

    //Функция выхода из аккаунта
    void logout() {
        File f = new File(Main.getLauncherDir() + File.separator + "auth.json");//Файл настроек
        f.delete();//Удалить нахуй
        user.Reset();//Ресетнуть всё
        user.wasAuth = false;//Флаг авторизации - не авторизовавылся
        ManagerWindow.ACCOUNT_AUTH.open();// Выкинуть в авторизацию
    }


}
