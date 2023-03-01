package tylauncher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tylauncher.Managers.ManagerWindow;

public class ForumController extends BaseController {
    private final String suffix = "[" + this.getClass().getName() + "] ";
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
        ManagerWindow.forumController = this;
        initPageButton();
    }
}
