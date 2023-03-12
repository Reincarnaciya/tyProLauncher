package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Managers.ManagerAnimations;
import tylauncher.Managers.ManagerWindow;

public abstract class BaseController {
    @FXML
    private AnchorPane A1;
    @FXML
    private Pane infoTextPane;
    @FXML
    private Text infoText;

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

    void initPageButton() {
        //Передача данного контроллера в другие классы, для доступа к функциям этого контроллера
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

    /**
     * Вывод информации
     *
     * @param info текст ошибки или информации для вывода
     */
    public void setInfoText(String info) {
        Platform.runLater(() -> {
            infoTextPane.setDisable(false);
            infoTextPane.setVisible(true);
            infoText.setText(info);
            ManagerAnimations.StartFadeAnim(infoTextPane);
        });
    }

    public void unsetText() {
        infoTextPane.setVisible(false);
    }

    public AnchorPane getA1() {
        return this.A1;
    }

}
