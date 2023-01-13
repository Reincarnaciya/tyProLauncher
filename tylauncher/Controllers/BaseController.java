package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tylauncher.Utilites.Managers.ManagerAnimations;

public abstract class BaseController {
    @FXML
    private AnchorPane A1;
    @FXML
    private Pane infoTextPane;
    @FXML
    private Text infoText;

    /**
     * Вывод информации
     * @param info
     * текст ошибки или информации для вывода
     */
    public void setInfoText(String info) {
        Platform.runLater(()->{
            infoTextPane.setVisible(true);
            infoText.setText(info);
            ManagerAnimations.StartFadeAnim(infoTextPane);
        });
    }
    public void unsetText(){
        infoTextPane.setVisible(false);
    }
    public AnchorPane getA1(){
        return this.A1;
    }

}
