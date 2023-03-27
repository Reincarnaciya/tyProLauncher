package tylauncher.Utilites;

import javafx.application.Platform;
import javafx.fxml.FXML;
import tylauncher.Controllers.SettingsController;
import tylauncher.Controllers.UpdaterController;
import tylauncher.Main;
import tylauncher.Managers.ManagerDirs;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWeb;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.Tasks.CheckUpdTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdaterLauncher {
    private static final Logger logger = new Logger(UpdaterLauncher.class);
    private static final ManagerWeb updateManagerWeb = new ManagerWeb("checkLauncherUpdate");
    public static UpdaterController updaterController;

    /**
     * Асинхронно проверяет обновления
     * */
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
            }
        }).start();

    }

    /**
     * Асинхронно запускает апдейтер
    * */
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

                    if(ManagerDirs.getPlatform().equals(ManagerDirs.OS.windows)) Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar \"" + client.getAbsolutePath() + "\" " + "\"" + UserPC._pathToLauncher + "\"" + " " + "\"" + Main.getLauncherDir().getAbsolutePath() + "\""});
                    else if (ManagerDirs.getPlatform().equals(ManagerDirs.OS.linux)) { // если линукс
                        System.err.println("OS = linux");
                        String sudoUser = System.getenv("SUDO_USER");
                        System.err.println("SudoUser = " + sudoUser);
                        String command = "java -jar " + client.getAbsolutePath() + " \"" + File.separator + UserPC._pathToLauncher + "\" \"" + Main.getLauncherDir().getAbsolutePath() + "\"";
                        System.err.println("command = " + command);
                        String[] cmdArray;
                        if (sudoUser != null) { // если программма запущена с правами админа
                            cmdArray = new String[]{"sudo " + command};
                            System.err.println("SudoUser != null;\n cmdArray = " + Arrays.toString(cmdArray));
                        } else {
                            cmdArray = new String[]{command};
                            System.err.println("SudoUser == null;\n cmdArray = " + Arrays.toString(cmdArray));
                        }
                        Runtime.getRuntime().exec(cmdArray);
                    }
                    else Runtime.getRuntime().exec(new String[]{"open", "-a", "Terminal.app", "-n", "java -jar \"" + client.getAbsolutePath() + "\" " + "\"" + UserPC._pathToLauncher + "\"" + " " + "\" " + Main.getLauncherDir().getAbsolutePath() + "\""});
                    ManagerFlags.updateAvailable = false;
                    Main.exit();
                }
            } catch (IOException e) {
                logger.logInfo(e, ManagerWindow.currentController);
            }
        }).start();
    }





}
