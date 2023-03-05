package tylauncher.Utilites;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.RuntimeController;
import tylauncher.Main;
import tylauncher.Managers.*;
import tylauncher.Utilites.Constants.URLS;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

public class RuntimeDownload {
    private static final Logger logger = new Logger(RuntimeDownload.class);
    public static RuntimeController runtimeController;
    private static URL urlDownload;

    public static boolean checkRuntime() {
        String hash;
        try {
            HashCodeCheck runtimeHashCheck = new HashCodeCheck(Main.getRuntimeDir().toPath());
            hash = runtimeHashCheck.calculateHashCode();
            logger.logInfo("RUNTIME HASH " + hash);
        } catch (NoSuchAlgorithmException | IOException e) {
            logger.logError(e);
            return false;
        }
        return checkWithServer(hash);
    }

    private static boolean checkWithServer(String hash) {
        ManagerWeb runtimeHashCheck = new ManagerWeb("runtimeHashCheck");
        runtimeHashCheck.setUrl(URLS.JAVA_HASH);
        runtimeHashCheck.putAllParams(Arrays.asList("os", "cpu", "hash_runtime", "good_ozu"), Arrays.asList(String.valueOf(ManagerDirs.getPlatform()), System.getProperty("os.arch"), hash, String.valueOf(UserPC.getOzu() > 4096)));
        logger.logInfo(runtimeHashCheck.toString());
        try {
            runtimeHashCheck.request();
        } catch (Exception e) {
            logger.logError(e);
        }


        if ("1".equals(runtimeHashCheck.getFullAnswer())) return true;

        try {
            urlDownload = new URL(runtimeHashCheck.getFullAnswer());
        } catch (MalformedURLException e) {
            logger.logError(e);
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void download(ProgressBar progressBar, Text infoText) {
        try {
            File runtime = new File(Main.getRuntimeDir() + File.separator + "jre8");
            if (runtime.exists()) {
                Utils.DeleteFile(runtime);
            } else {
                logger.logInfo("Папка рантайма не найдена. Пропускаю удаление");
            }
        } catch (Exception e) {
            logger.logError("Не удалось удалить файл: ", e.getMessage(), e.toString());
        }

        new Thread(() -> {
            try {
                ManagerDownload runtimeDownload = new ManagerDownload(urlDownload, Main.getRuntimeDir().getAbsolutePath(), progressBar, infoText);
                runtimeDownload.download();
                logger.logInfo("Распаковываем рантаймы :D");
                ManagerZip mz = new ManagerZip(Main.getRuntimeDir().getAbsolutePath() + File.separator + runtimeDownload.getFileName(),
                        Main.getRuntimeDir().getAbsolutePath() + File.separator, infoText, runtimeController.getProgressBar(), runtimeController);
                mz.unzip();

                Utils.setAllPermissions(Main.getRuntimeDir().toPath());
                ManagerWindow.ACCOUNT_AUTH.open();
            } catch (Exception e) {
                logger.logError(e, ManagerWindow.currentController);
            }

        }).start();
    }
}