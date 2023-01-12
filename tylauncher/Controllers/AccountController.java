package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerWindow;
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
    @FXML
    void initialize() {
        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        AccountAuthController.accountController = this;
        ManagerWindow.currentController = this;
        //все кнопки в 1 массив!
        ButtonPageController buttonPageController = new ButtonPageController();

        buttonPageController.addButton(Account_Img);
        buttonPageController.addButton(News_Img);
        buttonPageController.addButton(Forum_Img);
        buttonPageController.addButton(Message_Img);
        buttonPageController.addButton(Settings_Img);
        buttonPageController.addButton(Play_Img);

        Exit_Button.setOnMouseClicked(mouseEvent -> logout());

        donateButton.setOnMouseClicked(event ->{
            try {
                Utils.openUrl("https://typro.space/markup/donate.php");
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText(String.format("Невозможно открыть ссылку:  %s", e.getMessage()));
                e.printStackTrace();
            }
        });
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
