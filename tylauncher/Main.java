package tylauncher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tylauncher.Utilites.*;
import tylauncher.Utilites.Managers.ManagerDirs;
import tylauncher.Utilites.Managers.ManagerFlags;
import tylauncher.Utilites.Managers.ManagerStart;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static tylauncher.Controllers.SettingsController.setSettings;
import static tylauncher.Controllers.SettingsController.settingsFile;

public class Main extends Application {
    public static Stage mainStage = null;
    public static final String launcher_version = "02.0";
    private static TrayIcon trayIcon;
    private static final ManagerDirs _launcherDir = new ManagerDirs("TyPro");
    private static final ManagerDirs _clientDir = new ManagerDirs("TyPro/clients/");

    public static User user = new User();
    public static File getLauncherDir() {
        return _launcherDir.getWorkDir();
    }
    public static File getClientDir() {
        return _clientDir.getWorkDir();
    }

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
                    btn.getScene().getWindow().setOnCloseRequest(event -> exit());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public static void main(String[] args) throws IOException {

        if(args.length > 0 && args[0].equalsIgnoreCase("love")){
            easter();
            System.exit(0);
        }
        if(new File(getClientDir() + File.separator + "TyUpdaterLauncher.jar").exists()){
            Utils.DeleteFile(new File(getClientDir() + File.separator + "TyUpdaterLauncher.jar"));
        }


        if(UserPC._usableDiskSpace < 1500){
            ManagerFlags.lowDiskSpace = true;
        }
        Test();

        File dir_logs = new File(getLauncherDir() + File.separator + "logs");
        if (!dir_logs.exists()) dir_logs.mkdirs();
        // время для логов
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Moscow")); // Europe/Moscow
        // очищаем от говна
        String logfile = now.toString().replace(":", "-")
                .replace("]", "").replace("[", "")
                .replace(".", "").replace("/", "-")
                + ".log";
        // лог файл
        File log = new File(dir_logs + File.separator + "LogFile_" + logfile);
        if (!log.exists()) {
            log.createNewFile();
        }
        // запись в файл и вывод в консоль
        PrintStream out = new PrintStream(Files.newOutputStream(log.toPath()));
        PrintStream dual = new DualStream(System.out, out);
        System.setOut(dual);
        dual = new DualStream(System.err, out);
        System.setErr(dual);
        CheckLogs();
        UserPC.Show();
        launch();

    }

    public static void exit() {

        System.err.println("exit");
        if(ManagerStart.gameIsStart){
            return;
        }
        SystemTray.getSystemTray().remove(trayIcon);

        Platform.exit();

        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, FontFormatException {
        Font.loadFont(getClass().getResourceAsStream("Minecraft.ttf"), 16);
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("FXMLfiles/Updater.fxml")));
        }catch (IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle("Updater");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("StyleSheets/font.css")).toExternalForm());
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/icoNewYear.png"))));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getScene().getWindow().centerOnScreen();
        primaryStage.show();
        mainStage = primaryStage;

        //Настройка выпоадающего списка при нажатии пкм на иконку трея
        java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                Main.class.getResourceAsStream("StyleSheets/Minecraft.ttf"));

        PopupMenu popupMenu = new PopupMenu();

        MenuItem exit = new MenuItem("Выход");
        exit.addActionListener(e -> exit());

        MenuItem showUnshow = new MenuItem("Показать/скрыть");
        showUnshow.addActionListener(e -> Platform.runLater(() -> mainStage.setIconified(!mainStage.isIconified())));

        popupMenu.add(showUnshow);
        popupMenu.addSeparator();
        popupMenu.add(exit);

        popupMenu.setFont(font.deriveFont(14f));


        java.awt.Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("assets/icoNewYear.png"));
        ManagerWindow.currentController.getA1().getScene().getWindow().setOnCloseRequest(event -> Main.exit());
        trayIcon = new TrayIcon(img, "TypicalLauncher", popupMenu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(mouseEvent -> Platform.runLater(()->{
            Stage st = (Stage) ManagerWindow.currentController.getA1().getScene().getWindow();
            st.setIconified(!st.isIconified());
        }));
        try {
            SystemTray.getSystemTray().add(trayIcon);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            UpdaterLauncher.checkUpdate();
            setSettings();
        }catch (Exception e){
            settingsFile.delete();
            System.err.println("Файл настроек был успешно поломан и восстановлен.");
            e.printStackTrace();
        }



    }

    public static void CheckLogs() {
        long numDays = 4;   //Оно должно быть лонг, честно, иначе чет всё ломается
        String dir = getLauncherDir() + File.separator + "logs";
        File directory = new File(dir);
        File[] fList = directory.listFiles();

        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    long diff = new Date().getTime() - file.lastModified();
                    long cutoff = (numDays * 24 * 60 * 60 * 1000);

                    if (diff > cutoff) {
                        if (file.delete()) System.err.println("Удален старый лог-файл: " + file);
                    }
                }
            }
        }
    }

    private static void Test() {

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



    static void easter(){
        System.err.println("\n\n\n");
        System.err.println("                   .`\":l><~<!;,^'.                                .'^,;l><>iI:\"`.                   \n" +
                "              `I{u&88&&88888888888#/+\".                      .\"<\\*88888888&&&&&&&&n};`              \n" +
                "          .,(W&&&&&&&&&&&&&&&&&&&&&&888u~`                '>x888&&&&&&&&&&&&&&&&&&&&&&M(,.          \n" +
                "        `1W&&&&&&&&&&&&&&&&&&&&&&&&&&&&&88vI.           ;n88&&&&&&&&&&&&&&&&&&&&&&&&&&&&&W1`        \n" +
                "      `f&&&&&&&&&WWWWWWWWWWWWWWWW&&&&&&&&&&8M>        l#8&&&&&&&&&WWWWWWWWWW&&&&&&&&&&&&&&&&j`      \n" +
                "    .}&&&WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&&8z^    `v8&&&&&&WWWWWWWWWWWWWWWWWWWW&&&&&&&&&&&&&{.    \n" +
                "   'uWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&8>  l&&&&&&WWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&&&&&&&v'   \n" +
                "  'zWWWWWWWWWWWWWWWWWWWWMMMMWWWWWWWWWWWWWWWW&&&&8}?8&&&WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&&&&&&*'  \n" +
                "  nWWWWWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&WWWWWWWWWMMMMMMMMMMMWWWWWWWWWWWWW&&&&&&&&&Wu  \n" +
                " IWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&WWWWWWWMMMMMMMMMMMMMMMMMMWWWWWWWWWWW&&&&&&&&&Wl \n" +
                " xMMWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&WWWWWMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWWW&&&&&&&&Wn \n" +
                " MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&&W \n" +
                " #MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&M \n" +
                " tMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&j \n" +
                " ;MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWW&&&&&&&&&I \n" +
                " .cMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&*. \n" +
                "  ,MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&8,  \n" +
                "   -MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWW&&&&&&&88?   \n" +
                "    }MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWW&&&&&&&881    \n" +
                "     ?MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&&88[     \n" +
                "      ;MWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&&8&l      \n" +
                "       `vWWWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&&8c`       \n" +
                "        .]&&&WWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&88[.        \n" +
                "          ^u&&&&WWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&&8u`          \n" +
                "            lM&&&&WWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&8Ml            \n" +
                "             .?&&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&&-.             \n" +
                "               '{&&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&&}'               \n" +
                "                 '1&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&{'                 \n" +
                "                   '{&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&['                   \n" +
                "                     '?W&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&M-.                     \n" +
                "                       .~#&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&&&*>.                       \n" +
                "                         .Iz&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&&&v;.                         \n" +
                "                            ,n&&&&WWWWMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&&&r,                            \n" +
                "                              ^f&&&&WWWWMMMMMMMMMMMMMMMMMMMWWWWW&&&&/^                              \n" +
                "                                `(&&&&WWWWWMMMMMMMMMMMMMMWWWWW&&&&1`                                \n" +
                "                                  '{&&&&WWWWWMMMMMMMMMMWWWWW&&&W]'                                  \n" +
                "                                    '[&&&&WWWWWMMMMMMWWWWW&&&W-.                                    \n" +
                "                                      '}&&&&WWWWMMMMWWWW&&&&?.                                      \n" +
                "                                        '|&&&WWWWWWWWW&&&&{.                                        \n" +
                "                                          `n&&&WWWWWW&&&f`                                          \n" +
                "                                            IW&&&WW&&&#,                                            \n" +
                "                                             'f&&&&&&(.                                             \n" +
                "                                               ~&&&&;                                               \n" +
                "                                                ,&M^                                                \n" +
                "                                                 ^`                                                 \n");
        System.err.println("\n" +
                "██     ██ ███████     ████████  ██████   ██████      ██       ██████  ██    ██ ███████     ██    ██  ██████  ██    ██     \n" +
                "██     ██ ██             ██    ██    ██ ██    ██     ██      ██    ██ ██    ██ ██           ██  ██  ██    ██ ██    ██     \n" +
                "██  █  ██ █████          ██    ██    ██ ██    ██     ██      ██    ██ ██    ██ █████         ████   ██    ██ ██    ██     \n" +
                "██ ███ ██ ██             ██    ██    ██ ██    ██     ██      ██    ██  ██  ██  ██             ██    ██    ██ ██    ██     \n" +
                " ███ ███  ███████        ██     ██████   ██████      ███████  ██████    ████   ███████        ██     ██████   ██████      \n" +
                "                                                                                                                          \n" +
                "                                                                                                                          \n");
        System.err.println("Now open the launcher normally :)");
    }
}
