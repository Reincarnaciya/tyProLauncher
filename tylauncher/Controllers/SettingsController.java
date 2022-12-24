package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.*;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerFlags;
import tylauncher.Utilites.Managers.ManagerForJSON;

import tylauncher.Utilites.Managers.ManagerWindow;
import tylauncher.Utilites.Settings;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;

public class SettingsController extends BaseController {
    @FXML
    protected Text infoText;
    @FXML
    protected Pane infoTextPane;
    @FXML
    private AnchorPane A1;
    @FXML
    private ImageView Account_Img;
    @FXML
    private ImageView Forum_Img;
    @FXML
    private CheckBox Fullscrean_Checkbox;
    @FXML
    private ImageView Message_Img;
    @FXML
    private ImageView News_Img;
    /*@FXML
    private Text OzuCount_Text;*/
    @FXML
    private Slider Ozu_Slider;
    @FXML
    private ImageView Play_Img;
    @FXML
    private Button Save_Button;
    @FXML
    private ImageView Settings_Img;
    @FXML
    private TextField X_Label;
    @FXML
    private TextField Y_Label;
    @FXML
    private TextField OzuCount_Label;

    @FXML
    private Pane updateAvailablePane;

    @FXML
    private Button updateButton;

    @FXML
    private Text SettingsSaved_Text;

    @FXML
    private Pane SettingsPane;

    @FXML
    private Text warningText;
    @FXML
    private Button openLauncherDirButton;

    @FXML
    private Pane warningPane;

    @FXML
    private CheckBox hideLauncherCheckBox;

    private static final ManagerForJSON settingsJson = new ManagerForJSON(5);

    public static final File settingsFile = new File(Main.getLauncherDir() + File.separator + "settings.json");

    private static boolean hellishTheme = false;

    @FXML
    void initialize() {
        ManagerWindow.currentController = this;
        //все кнопки в 1 массив!
        ButtonPage.reset();
        ButtonPage.setPressedNum(5);
        BooleanPageController.addButton(Account_Img);
        BooleanPageController.addButton(News_Img);
        BooleanPageController.addButton(Forum_Img);
        BooleanPageController.addButton(Message_Img);
        BooleanPageController.addButton(Settings_Img);
        BooleanPageController.addButton(Play_Img);
        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        if(hellishTheme) SettingsPane.setStyle("-fx-background-color:  ff0000;");//Пасхалка тип

        // берем файл настроек
        if (settingsFile.exists()) {
            try {
                //Считываем файл
                settingsJson.ReadJSONFile(settingsFile.getAbsolutePath());
                //Берем нужное
                Settings.setOzu(Integer.parseInt(settingsJson.GetOfIndex(0,1)));
                Settings.setX(Integer.parseInt(settingsJson.GetOfIndex(1,1)));
                Settings.setY(Integer.parseInt(settingsJson.GetOfIndex(2,1)));
                Settings.setFsc(Boolean.parseBoolean(settingsJson.GetOfIndex(3,1)));
                Settings.setHide(Boolean.parseBoolean(settingsJson.GetOfIndex(4,1)));
            } catch (Exception e) {
                //пошло по пизде? хуево
                setInfoText("Ошибка: " + e + "\n Пересоздаю файл");
                settingsFile.delete();
                e.printStackTrace();
            }
        }
        //Включаем апдейт пэйн, если есть апдейт
        if(ManagerFlags.updateAvailable){
            updateAvailablePane.setDisable(false);
            updateAvailablePane.setVisible(true);
        }

        updateButton.setOnMouseClicked(mouseEvent -> UpdaterLauncher.UpdateLauncher());

        if(ManagerFlags.lowDiskSpace){
            warningPane.setVisible(true);
            warningText.setText("Обнаружено критически малое количество свободного места на диске." +
                    " Освободите место на диске!\n" + "\n" +
                    "Свободно " + UserPC._usableDiskSpace + "Mb\n" +
                    "Требуется минимум 1500Mb");
            warningText.setVisible(true);
        }
        //Выставляем максимальное значение слайдера в зависимости от установленной на пк ОЗУ
        Ozu_Slider.setMax((int)(UserPC.getOzu()/512) * 512);
        //Ставим слайдер по умолчанию и устанавливаем текст
        Ozu_Slider.setValue(Settings.getOzu());
        OzuCount_Label.setText(String.valueOf(Settings.getOzu()));

        try {
            //Инфа в комментариях функций
            WriteSettingToFile();
            UpdateSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Листенер изменения слайдера Озу
        Ozu_Slider.valueProperty().addListener((obs, oldval, newval) -> {
            int value = Math.round(newval.floatValue() / 512) * 512;//округляем до числа кратного 512(Джава так любит)
            OzuCount_Label.setText(String.valueOf(value));
            Ozu_Slider.setOnMouseReleased(mouseEvent -> {
                Ozu_Slider.setValue(value);
                OzuCount_Label.setText(String.valueOf(value));
            });
        });
        OzuCount_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.trim().equalsIgnoreCase(String.valueOf(666))){
                SettingsPane.setStyle("-fx-background-color:  ff0000;");
                OzuCount_Label.setStyle("-fx-background-color: ff0000;");
                ErrorInterp.setMessageError("Зря..");
                A1.getScene().setCursor(Cursor.cursor(String.valueOf(Main.class.getResource("assets/HellTyMasunya.png"))));
                hellishTheme = true;
            }
            if(newValue.trim().equalsIgnoreCase("999")){
                SettingsPane.setStyle("-fx-background-color: #363636;");
                OzuCount_Label.setStyle("-fx-background-color: #363636;");
                ErrorInterp.setMessageError("Умничка :)");
                A1.getScene().setCursor(Cursor.DEFAULT);
                hellishTheme = false;
            }
            if (!newValue.matches("\\d*")) OzuCount_Label.setText(oldValue);
        });
        X_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) X_Label.setText(oldValue);
        });
        Y_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) Y_Label.setText(oldValue);
        });
        Save_Button.setOnMouseClicked(mouseEvent -> {
            Settings.setFsc(Fullscrean_Checkbox.isSelected());
            try {
                Settings.setX(Integer.parseInt(X_Label.getText()));
                Settings.setY(Integer.parseInt(Y_Label.getText()));
                Settings.setOzu(Integer.parseInt(OzuCount_Label.getText()));
                Settings.setHide(hideLauncherCheckBox.isSelected());
                WriteSettingToFile();
                UpdateSettings();
                infoTextPane.setVisible(false);
            } catch (Exception e) {
                SettingsSaved_Text.setVisible(false);
                ErrorInterp.setMessageError(e.toString());
                UpdateSettings();
                System.err.println(Settings.show());
                return;
            }
            SettingsSaved_Text.setVisible(true);
            ManagerAnimations.StartFadeAnim(SettingsSaved_Text);
        });
        //Ивенты клика на картинки
        News_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("News.fxml", A1));
        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Play_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Play.fxml", A1));

        openLauncherDirButton.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().open(new File(Main.getLauncherDir().getAbsolutePath()));
            } catch (IOException e) {
                ManagerWindow.currentController.setInfoText(e.toString());
            }
        });

        Account_Img.setOnMouseClicked(mouseEvent -> {
            try {
                if (user.Auth()) {
                    Main.OpenNew("Account.fxml", A1);
                    accountController.UpdateData();
                } else Main.OpenNew("AccountAuth.fxml", A1);
            } catch (Exception e) {
                Main.OpenNew("AccountAuth.fxml", A1);
            }
        });
    }
    void UpdateSettings() {
        OzuCount_Label.setText(String.valueOf(Settings.getOzu()));
        Ozu_Slider.setValue(Settings.getOzu());
        Y_Label.setText(String.valueOf(Settings.getY()));
        X_Label.setText(String.valueOf(Settings.getX()));
        Fullscrean_Checkbox.setSelected(Settings.getFsc());
        hideLauncherCheckBox.setSelected(Settings.getHide());
    }

    public void WriteSettingToFile() throws Exception {
        settingsJson.setOfIndex("ozu", 0, 0);
        settingsJson.setOfIndex(String.valueOf(Settings.getOzu()), 0, 1);
        settingsJson.setOfIndex("x", 1, 0);
        settingsJson.setOfIndex(String.valueOf(Settings.getX()), 1, 1);
        settingsJson.setOfIndex("y", 2, 0);
        settingsJson.setOfIndex(String.valueOf(Settings.getY()), 2, 1);
        settingsJson.setOfIndex("FullScreenMode", 3, 0);
        settingsJson.setOfIndex(String.valueOf(Settings.getFsc()), 3, 1);
        settingsJson.setOfIndex("hide", 4, 0);
        settingsJson.setOfIndex(String.valueOf(Settings.getHide()), 4, 1);
        settingsJson.WritingFile(Main.getLauncherDir() + File.separator + "settings.json");
    }


    public void setInfoText(String info) {
        infoTextPane.setVisible(true);
        infoText.setText(info);
        ManagerAnimations.StartFadeAnim(infoTextPane);
    }
    public static void setSettings(){
        if (settingsFile.exists()) {
            try {
                //Считываем файл
                settingsJson.ReadJSONFile(settingsFile.getAbsolutePath());
                //Берем нужное
                Settings.setOzu(Integer.parseInt(settingsJson.GetOfIndex(0,1)));
                Settings.setX(Integer.parseInt(settingsJson.GetOfIndex(1,1)));
                Settings.setY(Integer.parseInt(settingsJson.GetOfIndex(2,1)));
                Settings.setFsc(Boolean.parseBoolean(settingsJson.GetOfIndex(3,1)));
                Settings.setHide(Boolean.parseBoolean(settingsJson.GetOfIndex(4,1)));
            } catch (Exception e) {
                //пошло по пизде? хуево
                ManagerWindow.currentController.setInfoText("Ошибка: " + e + "\n Пересоздаю файл");
                settingsFile.delete();
                e.printStackTrace();
            }
        }
    }
}
