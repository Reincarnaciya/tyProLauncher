package tylauncher.Utilites;

import javafx.application.Platform;
import javafx.fxml.FXML;
import tylauncher.Controllers.SettingsController;
import tylauncher.Controllers.UpdaterController;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWeb;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.Tasks.CheckUpdTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdaterLauncher {
    private static final Logger logger = new Logger(UpdaterLauncher.class);
    private static final ManagerWeb updateManagerWeb = new ManagerWeb("checkLauncherUpdate");
    public static UpdaterController updaterController;
    public static SettingsController settingsController;

    @FXML
    public static void checkUpdate() {
        new Thread(() -> {
            try {
                //"https://typro.space/vendor/launcher/CheckingVersion.php"
                updateManagerWeb.setUrl(URLS.CHECK_LAUCHER_VERS);
                updateManagerWeb.putAllParams(Arrays.asList("hash", "version"), Arrays.asList("rehtrjtkykyjhtjhjotrjhoitrjoihjoith", Main.launcher_version));
                updateManagerWeb.request();

                String line = updateManagerWeb.getFullAnswer();
                if (!line.equalsIgnoreCase("1")) {
                    Platform.runLater(() -> updaterController.setUpdateAvailable(true));
                } else
                    new Thread(() -> {
                        if (RuntimeDownload.checkRuntime()) ManagerWindow.ACCOUNT_AUTH.open();
                        else ManagerWindow.RUNTIME_DOWNLOAD.open();
                    }).start();
            } catch (Exception e) {
                logger.logInfo(e, ManagerWindow.currentController);
            }finally {
                checkUpdTask();
            }
        }).start();

    }

    public static void UpdateLauncher() {
        new Thread(() -> {
            try {
                HttpURLConnection updcon = (HttpURLConnection) URLS.UPDATER_LAUNCHER_DOWNLOAD.openConnection();
                File client = new File(Main.getClientDir().getAbsolutePath() + File.separator, "TyUpdaterLauncher.jar");

                if (client.exists()) client.delete();

                long cll_web = updcon.getContentLength();
                if ((client.length() != cll_web) && cll_web > 1) {
                    BufferedInputStream bis = new BufferedInputStream(updcon.getInputStream());
                    FileOutputStream fw = new FileOutputStream(client);
                    byte[] by = new byte[1024];
                    int count;
                    while ((count = bis.read(by)) != -1) {
                        fw.write(by, 0, count);
                        System.err.println("Скачка: " + cll_web + "/" + client.length());
                    }
                    fw.close();

                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar \"" + client.getAbsolutePath() + "\" " + "\"" + UserPC._pathToLauncher + "\"" + " " + "\"" + Main.getLauncherDir().getAbsolutePath() + "\""});
                    ManagerFlags.updateAvailable = false;
                    Main.exit();
                }
            } catch (IOException e) {
                logger.logInfo(e, ManagerWindow.currentController);
            }
        }).start();
    }


    private static void checkUpdTask(){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new CheckUpdTask(), 10, 10, TimeUnit.MINUTES);
    }



}
