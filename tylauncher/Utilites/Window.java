package tylauncher.Utilites;

import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import tylauncher.Controllers.AccountAuthController;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.FXMLS;
import tylauncher.Utilites.Constants.Titles;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Window {
    public static Window currentWindow;
    public static AccountAuthController accountAuthController;
    private static final Logger logger = new Logger(Window.class);
    private final int width;
    private final int height;
    private final Stage globalStage;
    private boolean firstOpen = true;
    private String fxml;
    private Scene scene;
    private String title;
    private Parent root;


    public Window(int width, int height, String title, String fxml) {
        globalStage = Main.mainStage;
        this.title = title;
        this.width = width;
        this.height = height;
        this.fxml = fxml;


    }

    private static List<Node> getAllCheckbox(Parent parent) {
        List<Node> cbox = parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof CheckBox)
                .collect(Collectors.toList());

        parent.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Parent)
                .forEach(node -> cbox.addAll(getAllCheckbox((Parent) node)));

        return cbox;
    }

    private static List<Node> getAllHyperlinks(Parent parent) {
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

    public String getFxml() {
        return fxml;
    }

    public void open() {
        Platform.runLater(() -> {
            try {
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml))), width, height);
            } catch (IOException e) {
                logger.logInfo(e);
                return;
            }

            if (!ManagerFlags.updateAvailable) globalStage.setTitle(title);
            else globalStage.setTitle(title + Titles.UPDATE_AVAILABLE_SUFFIX);

            globalStage.setScene(scene);


            checkWindow();
        });
    }

    public void open(String infoToShow) {
        Platform.runLater(() -> {
            try {
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml))), width, height);
            } catch (IOException e) {
                logger.logInfo(e);
                return;
            }


            if (!ManagerFlags.updateAvailable) globalStage.setTitle(title);
            else globalStage.setTitle(title + Titles.UPDATE_AVAILABLE_SUFFIX);

            globalStage.setScene(scene);

            logger.logInfo(infoToShow, ManagerWindow.currentController);

            checkWindow();
        });
    }

    private void checkWindow() {
        if ((fxml.equals(FXMLS.ACCOUNT_AUTH))){
            accountAuthController.customInitController();
            if (firstOpen){
                scene.getWindow().centerOnScreen();
            }
        }


        firstOpen = false;
        root = scene.getRoot();
        new Thread(() -> {
            List<Node> buttons = getAllButtons(root);
            for (Node button : buttons) {
                Button btn = (Button) button;
                btn.setOnMousePressed(event -> btn.setStyle("-fx-background-color: #444"));
                btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: #1a1a1a;"));
                btn.setOnMouseReleased(event -> btn.setStyle(" "));
                btn.setOnMouseExited(event -> btn.setStyle(" "));
            }

            List<Node> hyperlinks = getAllHyperlinks(root);
            for (Node hyplink : hyperlinks) {
                Hyperlink hl = (Hyperlink) hyplink;
                hl.setOnMouseEntered(event -> hl.setStyle("-fx-text-fill: gray;"));
                hl.setOnMouseExited(event -> hl.setStyle(""));
            }

            List<Node> cbox = getAllCheckbox(root);
            for (Node cb : cbox) {
                CheckBox checkBox = (CheckBox) cb;
                checkBox.setCursor(Cursor.HAND);
            }
        }).start();

        logger.logInfo("Открыто окно: " + this);
        currentWindow = this;
    }

    public void setTitle(String title) {
        Platform.runLater(()->{
            this.title = title;
            globalStage.setTitle(title);
        });

    }

    public void centerOnScreen() {
        Platform.runLater(() -> scene.getWindow().centerOnScreen());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Window{");
        sb.append("firstOpen=").append(firstOpen);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", fxml='").append(fxml).append('\'');
        sb.append(", Stage=").append(globalStage);
        sb.append(", scene=").append(scene);
        sb.append(", title='").append(title).append('\'');
        sb.append(", root=").append(root);
        sb.append('}');
        return sb.toString();
    }



    public String getTitle() {
        return this.title;
    }
}
