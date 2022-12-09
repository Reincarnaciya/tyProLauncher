package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.*;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerForJSON;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static tylauncher.Main.user;

public class AccountAuthController {

    @FXML
    protected Text infoText;
    @FXML
    protected Pane infoTextPane;
    @FXML
    private AnchorPane A1;
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
    private Text Password_Text;
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
    private final File AuthFile = new File((Main.getLauncherDir() + File.separator + "auth.json"));
    public ManagerForJSON Auth = new ManagerForJSON(2);

    @FXML
    void initialize() {
        RegisterController.accountAuthController = this;
        ErrorInterp.accountAuthController = this;

        ButtonPage.reset();
        ButtonPage.setPressedNum(1);
        BooleanPageController.addButton(Account_Img);
        BooleanPageController.addButton(News_Img);
        BooleanPageController.addButton(Forum_Img);
        BooleanPageController.addButton(Message_Img);
        BooleanPageController.addButton(Settings_Img);
        BooleanPageController.addButton(Play_Img);
        Platform.runLater(()->{
            A1.getScene().getWindow().setWidth(800);
            A1.getScene().getWindow().setHeight(530);
        });


        if (AuthFile.exists()) {
            try {
                Auth.ReadJSONFile(AuthFile.getAbsolutePath());
                String login = Auth.GetOfIndex(0, 1);
                String password = Auth.GetOfIndex(1, 1);
                user.setLogin(login);
                user.setPassword(password);
                StartAuth();
            } catch (Exception e) {
                ErrorInterp.setMessageError("Файл с логином и паролем поломался :( Удаляю..", "accountAuth");
                AuthFile.delete();
            }
        }
        Auth_Button.setOnMouseClicked(mouseEvent -> {
            user.setLogin(Login_Field.getText());
            user.setPassword(Password_Field.getText());
            try {
                StartAuth();
                if (AutoAuth_CheckBox.isSelected() && user.Auth()) SavePass();
            } catch (Exception e) {
                ErrorInterp.setMessageError(e.getMessage(), "accountauth");
            }
        });
        ShowPass_CheckBox.setOnAction(event -> {
            if (ShowPass_CheckBox.isSelected()) {
                ShowPassText.setVisible(true);
                Platform.runLater(() -> {
                    ShowPassText.requestFocus();
                    ShowPassText.selectEnd();
                    ShowPassText.deselect();
                });
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
        ShowPassText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ShowPass_CheckBox.isSelected()) {
                Password_Field.setText(newValue);
            }
        });
        Reg_HyperLynk.setOnMouseClicked(mouseEvent -> {
            user.Reset();
            Main.OpenNew("Register.fxml", A1);
        });
        News_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("News.fxml", A1));
        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Settings_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Settings.fxml", A1));
        Play_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Play.fxml", A1));

    }


    public void setInfoText(String text) {
        infoTextPane.setVisible(true);
        infoText.setText(text);
        ManagerAnimations.StartFadeAnim(infoTextPane);
    }

    void SavePass() throws Exception {
        ManagerForJSON auth = new ManagerForJSON(3);
        auth.setOfIndex("username", 0, 0);
        auth.setOfIndex(user.GetLogin(), 0, 1);
        auth.setOfIndex("password", 1, 0);
        auth.setOfIndex(user.GetPassword(), 1, 1);
        auth.WritingFile(Main.getLauncherDir().getAbsolutePath() + File.separator + "auth.json");
    }

    public void StartAuth() throws Exception {

        WebAnswer.Reset();
        if (user.Auth()) {
            Main.OpenNew("Account.fxml", A1);
            accountController.UpdateData();
            WebAnswer.PrintAnswer();
            System.err.println(user.toString());
        } else {
            ErrorInterp.setMessageError(WebAnswer.getMessage(), "accountauth");
        }
    }

}




