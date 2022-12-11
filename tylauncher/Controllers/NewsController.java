package tylauncher.Controllers;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tylauncher.Main;
import tylauncher.Utilites.BooleanPageController;
import tylauncher.Utilites.ButtonPage;
import tylauncher.Utilites.Managers.ManagerAnimations;
import tylauncher.Utilites.Utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;

public class NewsController {
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
    private Text inDevText;

    private int clicks = 0;
    @FXML
    void initialize() {
        ButtonPage.reset();
        ButtonPage.setPressedNum(2);
        BooleanPageController.addButton(Account_Img);
        BooleanPageController.addButton(News_Img);
        BooleanPageController.addButton(Forum_Img);
        BooleanPageController.addButton(Message_Img);
        BooleanPageController.addButton(Settings_Img);
        BooleanPageController.addButton(Play_Img);

        inDevText.setOnMouseClicked(mouseEvent -> {
            clicks++;
            System.err.println(clicks);
            switch (clicks){
                case 1:
                    inDevText.setText("Ай   ಥ﹏ಥ");
                    break;
                case 2:
                    inDevText.setText("Ой..Он меня заметил? ._.");
                    break;
                case 3:
                    inDevText.setText(".");
                    break;
                case 4:
                    inDevText.setText("..");
                    break;
                case 5:
                    inDevText.setText("...");
                    break;
                case 6:
                    inDevText.setText("*Делает вид что его тут нет* \n\n\n(〃＞＿＜;〃)");
                    break;
                case 10:
                    inDevText.setText("Не тыкай, больно");
                    break;
                case 15:
                    inDevText.setText("Прошу тебя, как человека, хватит.");
                    break;
                case 20:
                    inDevText.setText("Ты будешь кликать и дальше?");
                    break;
                case 30:
                    inDevText.setText("Ладно, я просто перестану общаться с тобой");
                    break;
                case 40:
                    inDevText.setText("Я так не могу..Перестань :*(");
                    break;
                case 50:
                    inDevText.setText("ДА ХВАТИТ!");
                    inDevText.setStyle("-fx-fill: ff0000; -fx-font-size: 42px;");
                    break;
                case 60:
                    inDevText.setText("Продолжишь кликать - выключусь");
                    break;
                case 70:
                    inDevText.setText("Я.НЕ.ШУЧУ");
                    inDevText.setStyle("-fx-fill: ff4d00;");
                    break;
                case 80:
                    inDevText.setText("ХВАТИТ!");
                    inDevText.setStyle("-fx-fill: ff0000; -fx-font-size: 42px;");
                    break;
                case 90:
                    inDevText.setText("Ладно, извини, что накричал, но прекрати, пожалуйста.. ~(>_<~)");
                    inDevText.setStyle("");
                    break;
                case 100:
                    inDevText.setText("Ты кликнул уже 100 раз..Пожалей свою мышку, если тебе меня не жаль..");
                    break;
                case 110:
                    inDevText.setText("(ｏ・_・)ノ”(ノ_");
                    break;
                case 130:
                    inDevText.setText("(×_×)⌒☆");
                    break;
                case 150:
                    inDevText.setText("СУКА ХВАТИТ, ЛУЧШЕ БЫ ДОНАТ ТАК ПОКУПАЛИ, КАК НА МЕНЯ КЛИКАЮТ.. \n\n\n凸(￣ヘ￣)");
                    inDevText.setStyle("-fx-fill: ff0000; -fx-font-size: 42px;");
                    break;
                case 160:
                    inDevText.setText("Да ну нахуй.");
                    inDevText.setStyle("");
                    break;
                case 170:
                    inDevText.setText("}:->");
                    break;
                case 180:
                    try {
                        Utils.openUrl("http://www.youtube.com/watch?v=dQw4w9WgXcQ");
                        inDevText.setText("Может это его отвлечет..");
                    } catch (IOException e) {
                        inDevText.setText("У ТЕБЯ БРАУЗЕР НЕ ОТКРЫВАЕТСЯ, КАК ТЫ ЖИВЕШЬ????");
                    }
                    break;
                case 190:
                    inDevText.setText("(・`ω´・)");
                case 200:
                    inDevText.setText("Как ты за.. 200 раз уже.. Я так не могу..");
                    break;
                case 210:
                    System.err.println("Да ну тебя знаешь куда? Пиздуй донат покупать за всю боль, которую ты мне причинил");
                    try {
                        Utils.openUrl("http://typro.space");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(-1);
            }


        });



        Forum_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Forum.fxml", A1));
        Message_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Message.fxml", A1));
        Settings_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Settings.fxml", A1));
        Play_Img.setOnMouseClicked(mouseEvent -> Main.OpenNew("Play.fxml", A1));
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
}
