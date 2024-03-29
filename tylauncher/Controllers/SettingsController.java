package tylauncher.Controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.Tooltips;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.UpdaterLauncher;
import tylauncher.Utilites.UserPC;
import tylauncher.Utilites.Window;

import java.awt.*;
import java.io.*;

public class SettingsController extends BaseController {
    public static final File settingsFile = new File(Main.getLauncherDir() + File.separator + "settings.json");
    private static final Logger logger = new Logger(SettingsController.class);
    private static final int settingsCount = 7;
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
    private CheckBox autoConnectCheckBox;
    @FXML
    private Pane warningPane;
    @FXML
    private Hyperlink pathToClientHyperLink;
    @FXML
    private CheckBox hideLauncherCheckBox;
    @FXML
    private Button resetBtn;
    @FXML
    private Text launcherVersionText;


    private boolean reset = false;


    @FXML
    void initialize() {
        ManagerWindow.settingsController = this;
        updateVisual();
        initPageButton();
        setToolTips();
        launcherVersionText.setText(launcherVersionText.getText() + Main.launcher_version);

        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
        if (ManagerFlags.hellishTheme) SettingsPane.setStyle("-fx-background-color:  ff0000;");//Пасхалка тип

        //Включаем апдейт пэйн, если есть апдейт
        if (ManagerFlags.updateAvailable) {
            updateAvailablePane.setDisable(false);
            updateAvailablePane.setVisible(true);
        }

        updateButton.setOnMouseClicked(mouseEvent -> UpdaterLauncher.UpdateLauncher());

        resetBtn.setOnMouseClicked(event -> {
            if (!reset) {
                logger.logInfo("Точно хочешь сбросить все настройки? Если да, то нажми еще раз на кнопку!", ManagerWindow.currentController);
                reset = true;
                return;
            }
            ManagerWindow.currentController.unsetText();

            Settings.reset();
            Main.resetClientDir();
            pathToClientHyperLink.setText(Main.getClientDir().getAbsolutePath());
            writeSettingsToFile();
            updateVisual();

            logger.logInfo("Настройки успешно сброшены", ManagerWindow.currentController);
            reset = false;
        });



        if (ManagerFlags.lowDiskSpace) {
            warningPane.setVisible(true);
            warningText.setText("Обнаружено критически малое количество свободного места на диске." +
                    " Освободите место на диске!\n" + "\n" +
                    "Свободно " + UserPC._usableDiskSpace + "Mb\n" +
                    "Требуется минимум 1500Mb");
            warningText.setVisible(true);
        }
        //Выставляем максимальное значение слайдера в зависимости от установленной на пк ОЗУ
        Ozu_Slider.setMax((int) (UserPC.getOzu() / 512) * 512);
        //Ставим слайдер по умолчанию и устанавливаем текст
        Ozu_Slider.setValue(Settings.getOzu());
        OzuCount_Label.setText(String.valueOf(Settings.getOzu()));


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
            easterCheck(newValue);
            if (!newValue.matches("\\d*")) OzuCount_Label.setText(oldValue);
        });

        X_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) X_Label.setText(oldValue);
        });

        Y_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) Y_Label.setText(oldValue);
        });


        Save_Button.setOnMouseClicked(mouseEvent -> {
            try {
                updateLogicalSettings();
            } catch (Exception e) {
                ManagerWindow.currentController.setInfoText(e.getMessage().contains("input") ? "Введи нормальное значение" : e.getMessage());
                logger.logError(e);
                updateVisual();
                return;
            }
            updateVisual();
            writeSettingsToFile();
            logger.logInfo("Настройки успешно сохранены :)", ManagerWindow.currentController);
        });

        pathToClientHyperLink.setText(Main.getClientDir().getAbsolutePath());
        pathToClientHyperLink.setOnMouseClicked(event -> {
            try {
                DirectoryChooser dc = new DirectoryChooser();
                dc.setInitialDirectory(Main.getClientDir());
                dc.setTitle("Выбор папки, куда будут скачиваться все клиенты");
                File f = dc.showDialog(ManagerWindow.currentController.getA1().getScene().getWindow());
                if (f == null) return;
                for (char c : f.getAbsolutePath().toCharArray()) {
                    if (Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CYRILLIC)) {
                        throw new Exception("Путь не должен содержать русских букв");
                    }
                }
                pathToClientHyperLink.setText(f.getAbsolutePath());
            } catch (Exception e) {
                logger.logError("Ошибка: " + e.getMessage() + "\nСбрасываю папку клиента..");
                logger.logError(e);
                Main.resetClientDir();
                updateVisual();
            }
        });


        openLauncherDirButton.setOnMouseClicked(event -> {
            File f = new File(Main.getLauncherDir().getPath() + File.separator);
            if (!UserPC._os.contains("win")) {
                ProcessBuilder pb = new ProcessBuilder("xdg-open", f.getAbsolutePath());
                try {
                    pb.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            try {
                Desktop.getDesktop().open(f);
            } catch (Exception e) {
                logger.logInfo("Чет окно не открылось:" + e.getMessage(), ManagerWindow.currentController);
                logger.logError(e);
            }
        });
    }

    void updateVisual() {
        OzuCount_Label.setText(String.valueOf(Settings.getOzu()));
        Ozu_Slider.setValue(Settings.getOzu());
        Y_Label.setText(String.valueOf(Settings.getY()));
        X_Label.setText(String.valueOf(Settings.getX()));
        Fullscrean_Checkbox.setSelected(Settings.getFsc());
        hideLauncherCheckBox.setSelected(Settings.getHide());
        autoConnectCheckBox.setSelected(Settings.isAutoConnect());
        pathToClientHyperLink.setText(Main.getClientDir().getAbsolutePath());
    }

    public void updateLogicalSettings() throws Exception {
        Settings.setFsc(Fullscrean_Checkbox.isSelected());
        if (X_Label.getText().isEmpty() || Y_Label.getText().isEmpty()) {
            Settings.reset();
            throw new Exception("Игрики и иксы не могут быть пусты! Сбрасываю настройки");
        }
        if (OzuCount_Label.getText().equalsIgnoreCase("") || OzuCount_Label.getText().isEmpty()) {
            Settings.reset();
            throw new Exception("Чел..Ты реально думаешь, что можно выставить оперативу в 0?");
        }
        Settings.setX(Integer.parseInt(X_Label.getText()));
        Settings.setY(Integer.parseInt(Y_Label.getText()));
        Settings.setOzu(Integer.parseInt(OzuCount_Label.getText()));
        Settings.setHide(hideLauncherCheckBox.isSelected());
        Settings.setAutoConnect(autoConnectCheckBox.isSelected());
        Main.setClientDir(new File(pathToClientHyperLink.getText()));
    }
    public static void writeSettingsToFile() {
        try (JsonWriter writer = new JsonWriter(new FileWriter(settingsFile))) {
            writer.beginObject();
            writer.name("ozu");
            writer.value(Settings.getOzu());
            writer.name("x");
            writer.value(Settings.getX());
            writer.name("y");
            writer.value(Settings.getY());
            writer.name("fsc");
            writer.value(Settings.getFsc());
            writer.name("hide");
            writer.value(Settings.getHide());
            writer.name("autoConnect");
            writer.value(Settings.isAutoConnect());
            writer.name("clientDir");
            writer.value(Main.getClientDir().getAbsolutePath());
            writer.endObject();
            writer.close();
            SettingsController.readSettingsFromFileToSettings();
        } catch (Exception e) {
            logger.logError(e);
        }
    }

    public static void readSettingsFromFileToSettings() throws Exception {
        try (BufferedReader bfr = new BufferedReader(new FileReader(settingsFile))) {
            if (!settingsFile.exists()) settingsFile.createNewFile();
            JsonObject settings;

            try {
                settings = (JsonObject) JsonParser.parseString(bfr.readLine());
            }catch (NullPointerException ignore){
                repairSettings();
                throw new Exception("Файл настроек сломался, пересоздаю.");
            }catch (Exception e) {
                repairSettings();
                logger.logError(e);
                throw new Exception("Файл настроек сломался, пересоздаю.");
            }


            if (settings.size() != settingsCount) {
                repairSettings();
                throw new Exception("Файл настроек сломался, пересоздаю.");
            }
            try {
                Settings.setOzu(Integer.parseInt(settings.get("ozu").toString()));
                Settings.setX(Integer.parseInt(settings.get("x").toString()));
                Settings.setY(Integer.parseInt(settings.get("y").toString()));
                Settings.setFsc(Boolean.parseBoolean(settings.get("fsc").toString()));
                Settings.setHide(Boolean.parseBoolean(settings.get("hide").toString()));
                Settings.setAutoConnect(Boolean.parseBoolean(settings.get("autoConnect").toString()));
                Main.setClientDir(new File(settings.get("clientDir").toString().replace("\"", "")));
            } catch (Exception e) {
                repairSettings();
                logger.logError(e);
                throw new Exception("Файл настроек сломался, пересоздаю.");
            }
        } catch (IOException e) {
            repairSettings();
            logger.logError(e);
            throw new Exception("Файл настроек сломался, пересоздаю.");
        }
    }

    private static void repairSettings() {
        if (!settingsFile.exists()){
            try {
                if (settingsFile.createNewFile()){
                    logger.logInfo("Файл настроек успешно создан");
                }else {
                    logger.logError("Не удалось создать файл настроек. И че делать?");
                }
            }catch (Exception e){
                logger.logError(e);
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(settingsFile))) {
            bfw.write("");
        } catch (IOException e) {
            logger.logError(e);
        }
    }
    private void setToolTips(){
        hideLauncherCheckBox.setTooltip(Tooltips.COLLAPSE_LAUNCHER);
        autoConnectCheckBox.setTooltip(Tooltips.AUTO_CONNECT_TO_SERVER);
        pathToClientHyperLink.setTooltip(Tooltips.DONT_DID_IT);
    }

    private void easterCheck(String newValue) {
        if (newValue.trim().equalsIgnoreCase(String.valueOf(666))) {
            SettingsPane.setStyle("-fx-background-color:  ff0000;");
            OzuCount_Label.setStyle("-fx-background-color: ff0000;");
            ManagerWindow.currentController.setInfoText("Зря..");
            A1.getScene().setCursor(Cursor.cursor(String.valueOf(Main.class.getResource("assets/HellTyMasunya.png"))));
            ManagerFlags.hellishTheme = true;
        }
        if (newValue.trim().equalsIgnoreCase("999")) {
            SettingsPane.setStyle("-fx-background-color: #363636;");
            OzuCount_Label.setStyle("-fx-background-color: #363636;");
            ManagerWindow.currentController.setInfoText("Умничка :)");
            A1.getScene().setCursor(Cursor.DEFAULT);
            ManagerFlags.hellishTheme = false;
        }
    }


}
