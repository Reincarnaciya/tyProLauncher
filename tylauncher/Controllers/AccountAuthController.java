package tylauncher.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sun.rmi.runtime.Log;
import tylauncher.Main;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.Tooltips;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.WebAnswer;
import tylauncher.Utilites.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static tylauncher.Main.user;

public class AccountAuthController extends BaseController {
    private static final Logger logger = new Logger(AccountAuthController.class);
    public static AccountController accountController;
    private static boolean firstOpen = true; //Флаг, определяющий впервые ли открыта сцена
    private final File AuthFile = new File((Main.getLauncherDir() + File.separator + "auth.json"));//Файл, в котором хранятся настройки авторизации
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

    //Инициализация сцены
    @FXML
    void initialize() {
        initPageButton();

        Window.accountAuthController = this;

        ShowPass_CheckBox.setTooltip(Tooltips.SHOW_PASSWORD);
        AutoAuth_CheckBox.setTooltip(Tooltips.AUTO_AUTH);


        //Улавливаем событие нажатия на кнопку
        Auth_Button.setOnMouseClicked(mouseEvent -> {
            try {
                startAuth();
                if (AutoAuth_CheckBox.isSelected() && user.auth())
                    savePass();
            } catch (Exception e) {
                logger.logError(e, ManagerWindow.currentController);
            }
        });

        //btn.setOnMousePressed(event -> btn.setStyle("-fx-background-color: #444"));
        //                    btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: #1a1a1a;"));

        //Улавливаем событие изменение чекбокса Просмотра пароля
        ShowPass_CheckBox.setOnAction(event -> {
            if (!ShowPass_CheckBox.isSelected()) {
                Platform.runLater(() -> {
                    Password_Field.requestFocus();
                    Password_Field.selectEnd();
                    Password_Field.deselect();
                });
                Password_Field.setText(ShowPassText.getText());
                ShowPassText.setVisible(false);
                return;
            }
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
        });

        //Синхронизация значение текста между 2-я полями пароля и показа пароля
        ShowPassText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ShowPass_CheckBox.isSelected()) Password_Field.setText(newValue);
        });
        //При нажатии на гиперссылку регистрации
        Reg_HyperLynk.setOnMouseClicked(mouseEvent -> {
            user.Reset();//Да-да
            ManagerWindow.REGISTER.open();
        });




        //Проверка на существование файла авторизации и последующая попытка авторизации
        if (AuthFile.exists() && firstOpen) {
            try (BufferedReader brf = new BufferedReader(new FileReader(AuthFile))) {
                JsonObject auth = (JsonObject) JsonParser.parseString(brf.readLine());
                String login = auth.get("login").toString().replace("\"", "");
                String password = auth.get("password").toString().replace("\"", "");
                user.setLogin(login);
                user.setPassword(password);
                startAuth();  //Отдельная функция авторизации
            } catch (Exception e) {
                if (!e.getMessage().contains("Сайт")) {
                    logger.logInfo("Файл с логином и паролем поломался :(\nУдаляю..", ManagerWindow.currentController);
                    AuthFile.delete();
                } else {
                    logger.logError(e.getMessage(), ManagerWindow.currentController);
                }
            }
        }
        firstOpen = false;
    }

    //Функция сейва пароляы
    void savePass() throws Exception {
        if (!AuthFile.exists()) AuthFile.createNewFile();
        try (JsonWriter writer = new JsonWriter(new FileWriter(AuthFile))) {
            writer.beginObject();
            writer.name("login");
            writer.value(user.GetLogin());
            writer.name("password");
            writer.value(user.GetPassword());
            writer.endObject();
        } catch (Exception e) {
            logger.logError(e);
        }
    }

    public void startAuth() {
        user.setLogin(Login_Field.getText());
        user.setPassword(Password_Field.getText());
        try {
            if (user.auth()) {
                ManagerWindow.ACCOUNT.open();
            } else {
                user.Reset();
                logger.logInfo(WebAnswer.getMessage(), ManagerWindow.currentController);
            }
        }catch (Exception e){
            logger.logError(e, ManagerWindow.currentController);
        }

    }


    public void customInitController(){
        Platform.runLater(()->{
            setEnterTape();
        });

    }

    public void setEnterTape(){
        Login_Field.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            if (event.isControlDown() && keyCode.equals(KeyCode.BACK_SPACE)){
                Login_Field.clear();
            }
            if(keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.DOWN)){
                Password_Field.requestFocus();
                Password_Field.selectEnd();
                Password_Field.deselect();
            }
        });

        Password_Field.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            if (event.isControlDown() && keyCode.equals(KeyCode.BACK_SPACE)){
                Password_Field.clear();
            }
            if (keyCode.equals(KeyCode.ENTER)){
                startAuth();
            }
            if (keyCode.equals(KeyCode.UP)){
                Login_Field.requestFocus();
                Login_Field.selectEnd();
                Login_Field.deselect();
            }
        });

        ShowPassText.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            if (event.isControlDown() && keyCode.equals(KeyCode.BACK_SPACE)){
                ShowPassText.clear();
            }
            if (keyCode.equals(KeyCode.ENTER)){
                startAuth();
            }
            if (keyCode.equals(KeyCode.UP)){
                Login_Field.requestFocus();
                Login_Field.selectEnd();
                Login_Field.deselect();
            }
        });

    }


}




