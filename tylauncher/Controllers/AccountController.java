package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.BooleanPageController;
import tylauncher.Utilites.ButtonPage;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.io.File;

import static tylauncher.Main.user;

public class AccountController extends BaseController{

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
        AccountAuthController.accountController = this;
        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        ManagerWindow.currentController = this;
        //все кнопки в 1 массив!
        ButtonPage.reset();
        ButtonPage.setPressedNum(1);
        BooleanPageController.addButton(Account_Img);
        BooleanPageController.addButton(News_Img);
        BooleanPageController.addButton(Forum_Img);
        BooleanPageController.addButton(Message_Img);
        BooleanPageController.addButton(Settings_Img);
        BooleanPageController.addButton(Play_Img);


        //Ивенты клика на картинки
        News_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("News.fxml", A1));
        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Settings_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Settings.fxml", A1));
        Play_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Play.fxml", A1));
        Exit_Button.setOnMouseClicked(mouseEvent -> Exit());
    }
    //Обновление информации о юзере
    public void UpdateData() {
        Username_Text.setText(user.GetLogin());
        Balance_Text.setText(user.GetBalance());
        Group_Text.setText(user.GetGroup());
        Skin_Image.setImage(user.GetImage());
    }
    //Функция выхода из аккаунта
    void Exit() {
        File f = new File(Main.getLauncherDir() + File.separator + "auth.json");//Файл настроек
        f.delete();//Удалить нахуй
        user.Reset();//Ресетнуть всё
        user.wasAuth = false;//Флаг авторизации - не авторизовавылся
        Main.OpenNew("AccountAuth.fxml", A1);// Выкинуть в авторизацию
    }
    //@Override
   // public void setInfoText(String info) {
   //     infoTextPane.setVisible(true);
   //     infoText.setText(info);
   //     ManagerAnimations.StartFadeAnim(infoTextPane);
   // }


}
