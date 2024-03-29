package tylauncher.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Managers.ManagerWeb;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.Utils;
import tylauncher.Utilites.WebAnswer;

import java.io.IOException;
import java.util.Arrays;

public class RegisterController extends BaseController {
    private static final Logger logger = new Logger(RegisterController.class);
    public static AccountAuthController accountAuthController;
    @FXML
    private AnchorPane A1;
    @FXML
    private ImageView Account_Img;
    @FXML
    private Button Cancel_Button;
    @FXML
    private TextField CheckPass_Text;
    @FXML
    private CheckBox ShowPass_CheckBox;
    @FXML
    private TextField Email_Field;
    @FXML
    private ImageView Forum_Img;
    @FXML
    private ImageView Message_Img;
    @FXML
    private ImageView News_Img;
    @FXML
    private PasswordField Password_Field;
    @FXML
    private ImageView Play_Img;
    @FXML
    private Button Register_Button;
    @FXML
    private PasswordField RepeatPassword_Field;
    @FXML
    private ImageView Settings_Img;
    @FXML
    private TextField Username_Field;
    @FXML
    private Pane infoTextPane;
    @FXML
    private Text infoText;
    @FXML
    private Text RepeatPasswordText;
    @FXML
    private Text EmailText;
    @FXML
    private Text PasswordText;
    @FXML
    private Text LoginText;
    @FXML
    private CheckBox acceptCheckBox;
    @FXML
    private Hyperlink eula;


    @FXML
    void initialize() {
        ManagerWindow.registerController = this;
        initPageButton();

        Cancel_Button.setOnMouseClicked(mouseEvent -> ManagerWindow.ACCOUNT_AUTH.open());
        ShowPass_CheckBox.setOnAction(event -> {
            if (!ShowPass_CheckBox.isSelected()) {
                Platform.runLater(() -> {
                    Password_Field.requestFocus();
                    Password_Field.selectEnd();
                    Password_Field.deselect();
                });
                Password_Field.setText(CheckPass_Text.getText());
                CheckPass_Text.setVisible(false);
                return;
            }
            CheckPass_Text.setVisible(true);
            Platform.runLater(() -> {
                CheckPass_Text.requestFocus();
                CheckPass_Text.selectEnd();
                CheckPass_Text.deselect();
            });
            CheckPass_Text.setText(Password_Field.getText());
        });

        CheckPass_Text.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ShowPass_CheckBox.isSelected()) Password_Field.setText(newValue);
        });

        eula.setOnMouseClicked(event -> {
            eula.setVisited(false);
            try {
                Utils.openUrl(URLS.USER_AGREEMENT);
            } catch (IOException e) {
                logger.logError("Не удалось открыть ссылку. У Вас есть браузер на пк?..", ManagerWindow.currentController);
                logger.logError(e);
            }
        });

        Register_Button.setOnMouseClicked(mouseEvent -> {
            if (Username_Field.getText().equalsIgnoreCase("") ||
                    Password_Field.getText().equalsIgnoreCase("") ||
                    RepeatPassword_Field.getText().equalsIgnoreCase("") ||
                    Email_Field.getText().equalsIgnoreCase("")) {
                logger.logInfo("Заполните все поля", ManagerWindow.currentController);
                return;
            }
            try {
                if (!regUser(Username_Field.getText(), Password_Field.getText(),
                        RepeatPassword_Field.getText(), Email_Field.getText())) {
                    logger.logInfo(WebAnswer.getMessage(), ManagerWindow.currentController);
                    inputErrorCheck(WebAnswer.getFields());
                    return;
                }
            } catch (Exception e) {
                logger.logError("Ошибка при регистрации:", e.getMessage());
                return;
            }
            ManagerWindow.ACCOUNT_AUTH.open(WebAnswer.getMessage());
        });
    }

    private boolean regUser(String username, String password, String repeatPassword, String email) throws Exception {
        WebAnswer.Reset();
        ManagerWeb regUser = new ManagerWeb("regUser");
        regUser.setUrl(URLS.REG_USER);//typro.space/vendor/launcher/register_launcher.php
        regUser.putAllParams(Arrays.asList("login", "password", "email", "repeat_password", "agreement"),
                Arrays.asList(username, password, email, repeatPassword, acceptCheckBox.isSelected() ? "on" : "off"));
        regUser.request();

        JsonObject object = (JsonObject) JsonParser.parseString(regUser.getFullAnswer());

        if (object.get("type") != null) WebAnswer.setType(object.get("type").toString().replace("\"", ""));
        if (object.get("message") != null) WebAnswer.setMessage(object.get("message").toString().replace("\"", ""));
        if (object.get("status") != null) WebAnswer.setStatus(object.get("status").toString().replace("\"", ""));
        if (object.get("fields") != null) WebAnswer.setFields(object.get("fields").toString().replace("\"", ""));


        logger.logInfo(regUser.toString(), object.toString(), WebAnswer.PrintAnswer());

        return WebAnswer.getStatus();
    }

    private void inputErrorCheck(String[] fields) {
        for (String s : fields) {
            if (s.contains("email")) EmailText.setText("Электронная почта*");
            else EmailText.setText("Электронная почта");
            if (s.contains("login")) LoginText.setText("Логин*");
            else LoginText.setText("Логин");
            if (s.contains("password")) {
                PasswordText.setText("Пароль*");
                RepeatPasswordText.setText("Повтор пароля*");
            } else {
                PasswordText.setText("Пароль");
                RepeatPasswordText.setText("Повтор пароля");
            }
        }
    }
}
