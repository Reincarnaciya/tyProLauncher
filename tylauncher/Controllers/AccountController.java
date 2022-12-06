package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Main;

import java.io.File;

import static tylauncher.Main.user;

public class AccountController {
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
    void initialize(){
        AccountAuthController.accountController = this;

        News_Img.setOnMouseClicked(mouseEvent -> {
            Main.OpenNew("News.fxml", A1);
       });
       Forum_Img.setOnMouseClicked(mouseEvent -> {
            Main.OpenNew("Forum.fxml", A1);
       });
       Message_Img.setOnMouseClicked(mouseEvent -> {
            Main.OpenNew("Message.fxml", A1);
       });
       Settings_Img.setOnMouseClicked(mouseEvent -> {
            Main.OpenNew("Settings.fxml", A1);
       });
       Play_Img.setOnMouseClicked(mouseEvent -> {
            Main.OpenNew("Play.fxml", A1);
       });



        Exit_Button.setOnMouseClicked(mouseEvent-> exit());
    }



    public void UpdateData(){
        Username_Text.setText(user.GetLogin());
        Balance_Text.setText(user.GetBalance());
        Group_Text.setText(user.GetGroup());
        Skin_Image.setImage(user.GetImage());
    }

    void exit(){
        File f = new File(Main.getLauncherDir() + File.separator + "auth.json");
        f.delete();
        user.Reset();
        Main.OpenNew("AccountAuth.fxml", A1);
    }

    



    
}
