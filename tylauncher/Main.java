package tylauncher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import tylauncher.Utilites.*;
import tylauncher.Utilites.Managers.ManagerDirs;
import tylauncher.Utilites.Managers.ManagerFlags;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import static tylauncher.Controllers.SettingsController.setSettings;
import static tylauncher.Controllers.SettingsController.settingsFile;
import static tylauncher.Utilites.Utils.CheckLogs;
import static tylauncher.Utilites.Utils.easter;

public class Main extends Application {
    public static Stage mainStage = null;
    public static final String launcher_version = "0.0";
    private static TrayIcon trayIcon;
    private static final ManagerDirs _launcherDir = new ManagerDirs("TyPro", 1);
    private static final ManagerDirs _clientDir = new ManagerDirs("TyPro/clients/", 1);

    public static User user = new User();
    public static File getLauncherDir() {
        return _launcherDir.getWorkDir();
    }
    public static File getClientDir() {
        return _clientDir.getWorkDir();
    }


    public static void main(String[] args) throws IOException {

        if(args.length > 0 && args[0].equalsIgnoreCase("love")){
            easter();
            System.exit(0);
        }
        try {
            if(new File(getClientDir() + File.separator + "TyUpdaterLauncher.jar").exists()){
                Utils.DeleteFile(new File(getClientDir() + File.separator + "TyUpdaterLauncher.jar"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(UserPC._usableDiskSpace < 1500) ManagerFlags.lowDiskSpace = true;

        Test();

        File dir_logs = new File(getLauncherDir() + File.separator + "logs");
        if (!dir_logs.exists()) dir_logs.mkdirs();

        // время для логов
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));

        // очищаем от говна
        String logfile = now.toString()
                .replace(":", "-")
                .replace("]", "")
                .replace("[", "")
                .replace(".", "")
                .replace("/", "-")
                + ".log";

        // лог файл
        File log = new File(dir_logs + File.separator + "LogFile_" + logfile);
        if (!log.exists()) log.createNewFile();

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
    /**
     * полностью закрывает всё, удаляет иконку из трея.. Да блять, сам посмотри что оно делает..
     */
    public static void exit() {
        System.err.println("exit");
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

        Window window = ManagerWindow.currentController.getA1().getScene().getWindow();
        window.setOnCloseRequest(event -> Main.exit());

        //Настройка выпоадающего списка при нажатии пкм на иконку трея
        java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Objects.requireNonNull
                (Main.class.getResourceAsStream("StyleSheets/Minecraft.ttf")));

        PopupMenu popupMenu = new PopupMenu();

        MenuItem exit = new MenuItem("Выход");
        exit.addActionListener(e -> exit());

        MenuItem showUnShow = new MenuItem("Показать/скрыть");
        showUnShow.addActionListener(e -> Platform.runLater(() -> mainStage.setIconified(!mainStage.isIconified())));

        popupMenu.add(showUnShow);
        popupMenu.addSeparator();
        popupMenu.add(exit);

        popupMenu.setFont(font.deriveFont(14f));

        java.awt.Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("assets/icoNewYear.png"));

        trayIcon = new TrayIcon(img, "TypicalLauncher", popupMenu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(mouseEvent -> Platform.runLater(()->{
            Stage st = (Stage) window;
            st.setIconified(!st.isIconified());
        }));

        try {
            SystemTray.getSystemTray().add(trayIcon);
            UpdaterLauncher.checkUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            setSettings();
        }catch (Exception e){
            settingsFile.delete();
            System.err.println("Файл настроек был успешно поломан и восстановлен.");
            e.printStackTrace();
        }
    }
    private static void Test() {}
}
