package tylauncher.Utilites.Managers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import tylauncher.Controllers.BaseController;
import tylauncher.Main;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ManagerWindow {
    public static BaseController currentController;

    /**
     * Открывает в текущем окне другую сцену(или не сцену, хз как назвать, крч другой fxml)
     * @param fxml
     * Имя fxml файла, который расположен в FXMLfile/fxmlfile.fxml (c .fxml)
     * @param pane
     * Текущее окно, которое будет заменено на новое
     */
    public static void OpenNew(String fxml, AnchorPane pane) {
        Platform.runLater(()->{
            AnchorPane pane1;
            try {
                pane.setStyle("");
                pane1 = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("FXMLfiles/" + fxml)));
                pane.getChildren().setAll(pane1);

                List<Node> buttons = getAllButtons(pane1);
                for (Node button : buttons){
                    javafx.scene.control.Button btn = (Button) button;
                    btn.setOnMousePressed(event -> btn.setStyle("-fx-background-color: #444"));
                    btn.setOnMouseReleased(event -> btn.setStyle(" "));
                    btn.getScene().getWindow().setOnCloseRequest(event -> Main.exit());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Функция просто для нахождения всех кнопок на каком-либо объекте(Pane, AnchorPane и т.д). по этой же логике можно
     * написать другие функции для нахождения чего-либо
     * @param parent
     * Объект, с которого получаем все кнопки
     * @return
     * Возвращает ЛИСТ кнопок
     */
    private static List<Node> getAllButtons(Parent parent) {
        List<Node> buttons = parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Button)
                .collect(Collectors.toList());

        parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Parent)
                .forEach(node -> buttons.addAll(getAllButtons((Parent) node)));

        return buttons;
    }
}
