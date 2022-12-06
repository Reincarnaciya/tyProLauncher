package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tylauncher.Main;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;

public class MessageController {
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
    void initialize(){
        News_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("News.fxml", A1);
        });
        Forum_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("Forum.fxml", A1);
        });
        Settings_Img.setOnMouseClicked(mouseEvent -> {
             Main.OpenNew("Settings.fxml", A1);
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
}
