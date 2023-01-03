package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Utilites.Managers.ManagerWindow;

public class ForumController extends BaseController{
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

    @FXML
    void initialize() {
        ManagerWindow.currentController = this;
        //все кнопки в 1 массив!
        ButtonPageController buttonPageController = new ButtonPageController();

        buttonPageController.addButton(Account_Img);
        buttonPageController.addButton(News_Img);
        buttonPageController.addButton(Forum_Img);
        buttonPageController.addButton(Message_Img);
        buttonPageController.addButton(Settings_Img);
        buttonPageController.addButton(Play_Img);
    }
}
