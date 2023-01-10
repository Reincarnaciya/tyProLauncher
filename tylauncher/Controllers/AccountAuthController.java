package tylauncher.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerFlags;
import tylauncher.Utilites.Managers.ManagerWindow;
import tylauncher.Utilites.WebAnswer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static tylauncher.Main.user;

public class AccountAuthController extends BaseController{

    @FXML
    protected Text infoText;//Текст информации
    @FXML
    protected Pane infoTextPane;//Панель, на которой все ошибки/инф-ия
    @FXML
    private AnchorPane A1;//Главная панель
    @FXML
    private ImageView Account_Img; //Картинка аккаунта, да
    @FXML
    private Button Auth_Button; //Кнопка авторизации
    @FXML
    private CheckBox AutoAuth_CheckBox; //Чек бокс на авто вход
    @FXML
    private ImageView Forum_Img; //Картинка форуума
    @FXML
    private TextField Login_Field; // поле ввода логина
    @FXML
    private ImageView Message_Img; //Картинка сообщений
    @FXML
    private ImageView News_Img;
    @FXML
    private PasswordField Password_Field;
    @FXML
    private ImageView Play_Img;
    @FXML
    private Hyperlink Reg_HyperLynk;
    @FXML
    private ImageView Settings_Img;
    @FXML
    private CheckBox ShowPass_CheckBox;
    @FXML
    private TextField ShowPassText;

    public static AccountController accountController;
    private final File AuthFile = new File((Main.getLauncherDir() + File.separator + "auth.json"));//Файл, в котором хранятся настройки авторизации

    private static boolean firstOpen = true; //Флаг, определяющий впервые ли открыта сцена
    //Инициализация сцены
    @FXML
    void initialize() {

        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        ManagerWindow.currentController = this;
        //все кнопки в 1 массив!
        ButtonPageController buttonPageController = new ButtonPageController();
        buttonPageController.addButton(Account_Img);
        buttonPageController.addButton(News_Img);
        buttonPageController.addButton(Forum_Img);
        buttonPageController.addButton(Message_Img);
        buttonPageController.addButton(Settings_Img);
        buttonPageController.addButton(Play_Img);

        //Выполняем в основном потоке(javafx)
        Platform.runLater(()->{
            //Меняем размеры окна и текст окна
            A1.getScene().getWindow().setWidth(800);
            A1.getScene().getWindow().setHeight(535);
            Stage stage = (Stage) A1.getScene().getWindow();
            if(firstOpen) A1.getScene().getWindow().centerOnScreen();
            firstOpen = false;
            if(ManagerFlags.updateAvailable) stage.setTitle("Typical Launcher (Доступно обновление)");
            else stage.setTitle("Typical Launcher");
        });

        //Проверка на существование файла авторизации и последующая попытка авторизацииtry(BufferedReader bfr = new BufferedReader(new FileReader(settingsFile))) {
        //            JsonObject settings = (JsonObject) JsonParser.parseString(bfr.readLine());
        if (AuthFile.exists() && firstOpen) {
            try(BufferedReader brf = new BufferedReader(new FileReader(AuthFile))) {
                JsonObject auth = (JsonObject) JsonParser.parseString(brf.readLine());
                String login = auth.get("login").toString().replace("\"", "");
                String password = auth.get("password").toString().replace("\"", "");
                user.setLogin(login);
                user.setPassword(password);
                StartAuth();  //Отдельная функция авторизации
            } catch (Exception e) {
                if(!e.getMessage().contains("Сайт")){
                    ManagerWindow.currentController.setInfoText("Файл с логином и паролем поломался :(\nУдаляю..");
                    AuthFile.delete();
                }else {
                    ManagerWindow.currentController.setInfoText(e.getMessage());
                }
                e.printStackTrace();

            }
        }
        //Улавливаем событие нажатия на кнопку
        Auth_Button.setOnMouseClicked(mouseEvent -> {
            //Юзеру задаем логин и пароль
            user.setLogin(Login_Field.getText());
            user.setPassword(Password_Field.getText());
            try {
                StartAuth();
                if (AutoAuth_CheckBox.isSelected() && user.Auth()) SavePass(); //При успешной авторизации и с поставленной галочкой
                // на запоминании пароля вызываем функцию сейва данных в файл и пропускаем юзера дальше в лаунчер
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText (e.getMessage());
                e.printStackTrace();
            }
        });
        //Улавливаем событие изменение чекбокса Просмотра пароля
        ShowPass_CheckBox.setOnAction(event -> {
            if (ShowPass_CheckBox.isSelected()) {//если чекбокс нажат
                //Костыль, да-да, по-другому не знаю как сделать
                ShowPassText.setVisible(true);
                //выполняем всё в мэйн потоке
                Platform.runLater(() -> {
                    ShowPassText.requestFocus();
                    ShowPassText.selectEnd();
                    ShowPassText.deselect();
                });
                //Меняем текст
                ShowPassText.setText(Password_Field.getText());

            } else {
                Platform.runLater(() -> {
                    Password_Field.requestFocus();
                    Password_Field.selectEnd();
                    Password_Field.deselect();
                });
                Password_Field.setText(ShowPassText.getText());
                ShowPassText.setVisible(false);
            }
        });
        //Синхронизация значение текста между 2-я полями пароля и показа пароля
        ShowPassText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ShowPass_CheckBox.isSelected()) {
                Password_Field.setText(newValue);
            }
        });
        //При нажатии на гиперссылку регистрации
        Reg_HyperLynk.setOnMouseClicked(mouseEvent -> {
            user.Reset();//Да-да
            ManagerWindow.OpenNew("Register.fxml", A1);
        });
    }

    public void setInfoText(String text) {
        infoTextPane.setVisible(true);
        infoText.setText(text);
        ManagerAnimations.StartFadeAnim(infoTextPane);
    }
    //Функция сейва пароля
    void SavePass() throws Exception {
        if(!AuthFile.exists()) AuthFile.createNewFile();
        try(JsonWriter writer = new JsonWriter(new FileWriter(AuthFile))) {
            writer.beginObject();
            writer.name("login");
            writer.value(user.GetLogin());
            writer.name("password");
            writer.value(user.GetPassword());
            writer.endObject();
        }
    }

    public void StartAuth() throws Exception {
        if (user.Auth()) {
            //Да-да, в классе юзера уже есть функция авторизации, но тут другое, вы не понимаете!
            ManagerWindow.OpenNew("Account.fxml", A1);//пропускаем юзера дальше
            accountController.UpdateData();//Обновляем информацию об аккаунте юзера
            //Дебаг
            WebAnswer.PrintAnswer();
            System.err.println(user);
        } else {
            user.Reset();
            ManagerWindow.currentController.setInfoText (WebAnswer.getMessage());
        }
    }
}




