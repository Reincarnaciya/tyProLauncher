package tylauncher.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.ErrorInterp;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Managers.ManagerForJSON;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.UserPC;

import java.io.File;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;


public class SettingsController {
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
    private Text OzuCount_Text;

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
    private Text SettingsSaved_Text;

    @FXML
    protected Text infoText;

    @FXML
    protected Pane infoTextPane;

    public static Settings settings = new Settings();

    private ManagerForJSON settingsJson = new ManagerForJSON(4);

    private File settingsFile = new File(Main.getLauncherDir() + File.separator + "settings.json");

    @FXML
    void initialize(){
        ErrorInterp.settingsController = this;

        if(settingsFile.exists()){
            try {
                settings.setOzu(Integer.parseInt(ReadParamsFromFile()[0]));
                settings.setX(Integer.parseInt(ReadParamsFromFile()[1]));
                settings.setY(Integer.parseInt(ReadParamsFromFile()[2]));
                settings.setFsc(Boolean.parseBoolean(ReadParamsFromFile()[3]));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Ozu_Slider.setMax(UserPC.getOzu());
            Ozu_Slider.setValue(Math.round((UserPC.getOzu()/3)/512 * 512));
            OzuCount_Label.setText(String.valueOf(Math.round((UserPC.getOzu()/3)/512 * 512)));
        }
        try {
            WriteSettingToFile();
            UpdateSettings();
        }catch (Exception e){
            e.printStackTrace();
        }



        Ozu_Slider.valueProperty().addListener((obs, oldval, newval ) -> {
            int value = Math.round(newval.intValue()/512)*512;
            OzuCount_Label.setText(String.valueOf(newval.intValue()));
            Ozu_Slider.setOnMouseReleased(mouseEvent -> {
                Ozu_Slider.setValue(value);
                OzuCount_Label.setText(String.valueOf(value));
            });
        });

        OzuCount_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) OzuCount_Label.setText(oldValue);
        });


        X_Label.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*"))X_Label.setText(oldValue);
        });


        Y_Label.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")) Y_Label.setText(oldValue);
            }
        });

        Save_Button.setOnMouseClicked(mouseEvent ->{

            settings.setFsc(Fullscrean_Checkbox.isSelected());
            try {
                settings.setX(Integer.parseInt(X_Label.getText()));
                settings.setY(Integer.parseInt(Y_Label.getText()));
                settings.setOzu(Integer.parseInt(OzuCount_Label.getText()));
                WriteSettingToFile();
                UpdateSettings();
                infoTextPane.setVisible(false);
            }catch (Exception e){
                SettingsSaved_Text.setVisible(false);
                ErrorInterp.setMessageError(e.toString(), "settings");
                UpdateSettings();
                System.err.println(settings.toString());
                return;
            }

            SettingsSaved_Text.setVisible(true);
            ManagerAnimations.StartFadeAnim(SettingsSaved_Text);
        });




        News_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("News.fxml", A1);
        });
        Forum_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("Forum.fxml", A1);
        });
        Message_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("Message.fxml", A1);
        });
        Play_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("Play.fxml", A1);
        });
        Account_Img.setOnMouseClicked(mouseEvent ->{
            try {
                if(user.Auth()){
                    Main.OpenNew("Account.fxml", A1);
                    accountController.UpdateData();
                }
                else Main.OpenNew("AccountAuth.fxml", A1);
            } catch (Exception e) {
                Main.OpenNew("AccountAuth.fxml", A1);
            }
        });
    }
    void UpdateSettings(){
        OzuCount_Label.setText(String.valueOf(settings.getOzu()));
        Ozu_Slider.setValue(settings.getOzu());
        Y_Label.setText(String.valueOf(settings.getY()));
        X_Label.setText(String.valueOf(settings.getX()));
        if(settings.getFsc()) Fullscrean_Checkbox.setSelected(true);
        else Fullscrean_Checkbox.setSelected(false);
    }

    public void WriteSettingToFile() throws Exception {
        settingsJson.setOfIndex("ozu", 0, 0);
        settingsJson.setOfIndex(String.valueOf(settings.getOzu()), 0, 1);
        settingsJson.setOfIndex("x", 1, 0);
        settingsJson.setOfIndex(String.valueOf(settings.getX()), 1, 1);
        settingsJson.setOfIndex("y", 2, 0);
        settingsJson.setOfIndex(String.valueOf(settings.getY()), 2, 1);
        settingsJson.setOfIndex("FullScreenMode", 3, 0);
        settingsJson.setOfIndex(String.valueOf(settings.getFsc()), 3, 1);

        settingsJson.WritingFile(Main.getLauncherDir() + File.separator + "settings.json");
    }

    public void ResetSettings() throws Exception {
        settingsJson.setOfIndex("ozu", 0, 0);
        settingsJson.setOfIndex("512", 0, 1);
        settingsJson.setOfIndex("x", 1, 0);
        settingsJson.setOfIndex("800", 1, 1);
        settingsJson.setOfIndex("y", 2, 0);
        settingsJson.setOfIndex("600", 2, 1);
        settingsJson.setOfIndex("FullScreenMode", 3, 0);
        settingsJson.setOfIndex("false", 3, 1);

        settingsJson.WritingFile(Main.getLauncherDir() + File.separator + "settings.json");
    }

    public String[] ReadParamsFromFile() throws Exception {
        settingsJson.ReadJSONFile(settingsFile.getAbsolutePath());
        
        String ozu = settingsJson.GetOfIndex(0, 1);
        String x = settingsJson.GetOfIndex(1,1);
        String y = settingsJson.GetOfIndex(2,1);
        String FullScreenMode = settingsJson.GetOfIndex(3,1);

        return new String[]{ozu, x, y, FullScreenMode};
    }

    public void setInfoText(String info){
        infoTextPane.setVisible(true);
        infoText.setText(info);
    }












}
