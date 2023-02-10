package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Utils;

import java.io.File;

import static tylauncher.Main.user;

public class AccountController extends BaseController{
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

    private static boolean firstOpen = true; //Флаг, определяющий впервые ли открыта сцена
    @FXML
    void initialize() {
        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        AccountAuthController.accountController = this;

        initPageButton();

        Platform.runLater(()->{
            Stage stage = (Stage) A1.getScene().getWindow();
            //Меняем размеры окна и текст окна
            stage.setWidth(800);
            stage.setHeight(535);
            if(firstOpen) stage.centerOnScreen();
            if(ManagerFlags.updateAvailable) stage.setTitle("Typical Launcher (Доступно обновление)");
            else stage.setTitle("Typical Launcher");
        });

        Exit_Button.setOnMouseClicked(mouseEvent -> logout());

        donateButton.setOnMouseClicked(event ->{
            try {
                Utils.openUrl("https://typro.space/markup/donate.php");
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText(String.format("Невозможно открыть ссылку:  %s", e.getMessage()));
                e.printStackTrace();
            }
        });
        firstOpen = false;
    }
    //Обновление информации о юзере
    public void UpdateData() {
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
        ManagerWindow.OpenNew("AccountAuth.fxml", A1);// Выкинуть в авторизацию
    }


}
