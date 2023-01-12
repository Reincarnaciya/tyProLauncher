package tylauncher.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerWeb;
import tylauncher.Utilites.Managers.ManagerWindow;
import tylauncher.Utilites.WebAnswer;

import java.util.Arrays;

public class RegisterController extends BaseController{
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
    void initialize() {
        ManagerWindow.currentController = this;
        // ЛОГИКА КАК В АВТОРИЗАЦИИ, Я ЗАЕБАЛСЯ КОММЕНТАРИИ ОДНОТИПНЫЕ ПИСАТЬ
        ButtonPageController buttonPageController = new ButtonPageController();

        buttonPageController.addButton(Account_Img);
        buttonPageController.addButton(News_Img);
        buttonPageController.addButton(Forum_Img);
        buttonPageController.addButton(Message_Img);
        buttonPageController.addButton(Settings_Img);
        buttonPageController.addButton(Play_Img);

        Cancel_Button.setOnMouseClicked(mouseEvent -> ManagerWindow.OpenNew("AccountAuth.fxml", A1));
        ShowPass_CheckBox.setOnAction(event -> {
            if(!ShowPass_CheckBox.isSelected()){
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

        Register_Button.setOnMouseClicked(mouseEvent -> {
            if (Username_Field.getText().equalsIgnoreCase("") ||
                    Password_Field.getText().equalsIgnoreCase("") ||
                    RepeatPassword_Field.getText().equalsIgnoreCase("") ||
                    Email_Field.getText().equalsIgnoreCase("")) {
                ManagerWindow.currentController.setInfoText ("Заполни все поля!");
                return;
            }
            try {
                if(!regUser(Username_Field.getText(), Password_Field.getText(),
                        RepeatPassword_Field.getText(), Email_Field.getText())){
                    ManagerWindow.currentController.setInfoText(WebAnswer.getMessage());
                    inputErrorCheck(WebAnswer.getFields());
                    return;
                }
            }catch (Exception e){
                ManagerWindow.currentController.setInfoText(String.format("Ошибка при регистрации: \n%s", e.getMessage()));
                return;
            }
            ManagerWindow.OpenNew("AccountAuth.fxml", A1);
            Platform.runLater(()-> ManagerWindow.currentController.setInfoText(WebAnswer.getMessage()));

        });
    }

    private boolean regUser(String username, String password, String repeatPassword, String email) throws Exception {
        WebAnswer.Reset();
        ManagerWeb regUser = new ManagerWeb("regUser");
        regUser.setUrl("https://typro.space/vendor/launcher/register_launcher.php");
        regUser.putAllParams(Arrays.asList("login", "password", "email", "repeat_password"),
                Arrays.asList(username, password, email, repeatPassword));
        regUser.request();

        JsonObject object = (JsonObject) JsonParser.parseString(regUser.getFullAnswer());

        if(object.get("type") != null) WebAnswer.setType(object.get("type").toString().replace("\"", ""));
        if(object.get("message") != null)WebAnswer.setMessage(object.get("message").toString().replace("\"", ""));
        if(object.get("status") != null)WebAnswer.setStatus(object.get("status").toString().replace("\"", ""));
        if(object.get("fields") != null)WebAnswer.setFields(object.get("fields").toString().replace("\"", ""));

        System.err.println("-----------------------------REG-INFO-----------------------------");
        System.err.println(regUser);
        System.err.println(object);
        WebAnswer.PrintAnswer();
        System.err.println("-----------------------------REG-INFO-----------------------------");

        return WebAnswer.getStatus();
    }

    private void inputErrorCheck(String[] fields){
        for (String s:fields) {
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
