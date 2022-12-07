package tylauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tylauncher.Utilites.HashCodeCheck;
import tylauncher.Utilites.DualStream;
import tylauncher.Utilites.Managers.ManagerDirs;
import tylauncher.Utilites.User;
import tylauncher.Utilites.UserPC;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class Main extends Application {

    public static final String launcher_version = "tyLauncher1";
    private static final ManagerDirs _launcherDir = new ManagerDirs("TyLauncher");
    private static final ManagerDirs _clientDir = new ManagerDirs("TyPro");
    public static User user = new User();
    public static File getLauncherDir() {
        return _launcherDir.getWorkDir();
    }

    public static File getClientDir() {
        return _clientDir.getWorkDir();
    }

    public static void OpenNew(String fxml, AnchorPane pane) {
        AnchorPane pane1;
        try {
            pane.setStyle("");
            pane1 = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("FXMLfiles/" + fxml)));
            pane.getChildren().setAll(pane1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Test();

        File dir_logs = new File(getLauncherDir() + File.separator + "logs");
        if (!dir_logs.exists()) {
            dir_logs.mkdirs();
        }
        // время для логов
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Moscow")); // Europe/Moscow
        // очищаем от говна
        String logfile = now.toString().replace(":", "-")
                .replace("]", "").replace("[", "").replace(".", "").replace("/", "-") + ".log";
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
        launch(args);
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
        try {
            System.err.println(HashCodeCheck.getHash(Main.getClientDir() + File.separator + "TySci_1.16.5"));
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("Minecraft.ttf"), 16);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("FXMLfiles/AccountAuth.fxml")));
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle("Typical Launcher");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("StyleSheets/font.css")).toExternalForm());
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/ico.png"))));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.getScene().getWindow().setOnCloseRequest(Even -> System.exit(0));
    }

}
