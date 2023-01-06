package tylauncher.Utilites.Managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
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
            AnchorPane pane1;
            try {
                pane.setStyle("");
                pane1 = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("FXMLfiles/" + fxml)));
                pane.getChildren().setAll(pane1);

                List<Node> buttons = getAllButtons(pane1);
                for (Node button : buttons){
                    Button btn = (Button) button;
                    btn.setOnMousePressed(event -> btn.setStyle("-fx-background-color: #444"));
                    btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: #1a1a1a;"));
                    btn.setOnMouseReleased(event -> btn.setStyle(" "));
                    btn.setOnMouseExited(event -> btn.setStyle(" "));
                }

                List<Node> hyperlinks = getAllHyperlinks(pane1);
                for (Node hyplink : hyperlinks){
                    Hyperlink hl = (Hyperlink) hyplink;
                    hl.setOnMouseEntered(event -> hl.setStyle("-fx-text-fill: gray;"));
                    hl.setOnMouseExited(event -> hl.setStyle(""));
                }

                List<Node> cbox = getAllCheckbox(pane1);
                for(Node cb : cbox){
                    CheckBox checkBox = (CheckBox) cb;
                    checkBox.setCursor(Cursor.HAND);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Функция просто для нахождения всех кнопок на каком-либо объекте(Pane, AnchorPane и т.д). по этой же логике можно
     * написать другие функции для нахождения чего-либо
     * @param parent
     * Объект, с которого получаем все кнопки
     * @return
     * Возвращает ЛИСТ кнопок
     * pathToClientHyperLink.setOnMouseClicked(event -> {
     *             pathToClientHyperLink.setVisited(false);
     *         });
     *         pathToClientHyperLink.setOnMouseEntered(event -> {
     *             pathToClientHyperLink.setStyle("-fx-text-fill: gray;");
     *         });
     *         pathToClientHyperLink.setOnMouseExited(event -> {
     *             pathToClientHyperLink.setStyle("");
     *         });
     */

    private static List<Node> getAllCheckbox(Parent parent){
        List<Node> cbox = parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof CheckBox)
                .collect(Collectors.toList());

        parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Parent)
                .forEach(node -> cbox.addAll(getAllCheckbox((Parent) node)));

        return cbox;
    }

    private static List<Node> getAllHyperlinks(Parent parent){
        List<Node> hyperlinks = parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Hyperlink)
                .collect(Collectors.toList());

        parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Parent)
                .forEach(node -> hyperlinks.addAll(getAllHyperlinks((Parent) node)));

        return hyperlinks;
    }

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
