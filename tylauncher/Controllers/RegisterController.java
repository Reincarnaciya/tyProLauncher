package tylauncher.Controllers;

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
import tylauncher.Main;
import tylauncher.Utilites.*;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerWindow;

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
        ButtonPage.reset();
        ButtonPage.setPressedNum(1);
        BooleanPageController.addButton(Account_Img);
        BooleanPageController.addButton(News_Img);
        BooleanPageController.addButton(Forum_Img);
        BooleanPageController.addButton(Message_Img);
        BooleanPageController.addButton(Settings_Img);
        BooleanPageController.addButton(Play_Img);

        Cancel_Button.setOnMouseClicked(mouseEvent -> {
            Main.OpenNew("AccountAuth.fxml", A1);
        });
        ShowPass_CheckBox.setOnAction(event -> {
            if (ShowPass_CheckBox.isSelected()) {
                CheckPass_Text.setVisible(true);
                Platform.runLater(() -> {
                    CheckPass_Text.requestFocus();
                    CheckPass_Text.selectEnd();
                    CheckPass_Text.deselect();
                });
                CheckPass_Text.setText(Password_Field.getText());

            } else {
                Platform.runLater(() -> {
                    Password_Field.requestFocus();
                    Password_Field.selectEnd();
                    Password_Field.deselect();
                });
                Password_Field.setText(CheckPass_Text.getText());
                CheckPass_Text.setVisible(false);
            }
        });
        CheckPass_Text.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ShowPass_CheckBox.isSelected()) Password_Field.setText(newValue);
        });
        Register_Button.setOnMouseClicked(mouseEvent -> {
            if (Username_Field.getText().equalsIgnoreCase("") || Password_Field.getText().equalsIgnoreCase("") || RepeatPassword_Field.getText().equalsIgnoreCase("") || Email_Field.getText().equalsIgnoreCase("")) {
                ErrorInterp.setMessageError("Заполин все поля!");
                return;
            }
            WebAnswer.Reset();
            RegisterUser.RegisterUser(Username_Field.getText(), Password_Field.getText(), RepeatPassword_Field.getText(), Email_Field.getText(), Main.launcher_version);
            if (WebAnswer.getStatus()) {
                Main.OpenNew("AccountAuth.fxml", A1);
                accountAuthController.infoTextPane.setVisible(true);
                accountAuthController.setInfoText(WebAnswer.getMessage());
                ManagerAnimations.StartFadeAnim(accountAuthController.infoTextPane);
            } else {
                if(WebAnswer.getFields().contains("email"))EmailText.setText("Электронная почта*");
                else EmailText.setText("Электронная почта");

                if(WebAnswer.getFields().contains("login"))LoginText.setText("Логин*");
                else LoginText.setText("Логин");

                if(WebAnswer.getFields().contains("password")){
                    PasswordText.setText("Пароль*");
                    RepeatPasswordText.setText("Повтор пароля*");
                }else {
                    PasswordText.setText("Пароль");
                    RepeatPasswordText.setText("Повтор пароля");
                }
                ErrorInterp.setMessageError(WebAnswer.getMessage());
            }



        });
        //Ивенты клика на картинки
        News_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("News.fxml", A1));
        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Settings_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Settings.fxml", A1));
        Play_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Play.fxml", A1));

    }

    public void setInfoText(String info) {
        infoTextPane.setVisible(true);
        infoText.setText(info);
        ManagerAnimations.StartFadeAnim(infoTextPane);
    }

}
