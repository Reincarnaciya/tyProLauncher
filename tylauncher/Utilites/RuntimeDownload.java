package tylauncher.Utilites;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.RuntimeController;
import tylauncher.Main;
import tylauncher.Managers.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

public class RuntimeDownload {
    public static RuntimeController runtimeController;
    private static String urlDownload;

    public static boolean checkRuntime() {
        String hash = " ";
        try {
            HashCodeCheck runtimeHashCheck = new HashCodeCheck(Collections.singletonList("jre8"), "runtime");
            hash = runtimeHashCheck.calculateHashes(Main.getRuntimeDir().getAbsolutePath());


        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return checkWithServer(hash);
    }

    private static boolean checkWithServer(String hash) {
        ManagerWeb runtimeHashCheck = new ManagerWeb("runtimeHashCheck");
        runtimeHashCheck.setUrl("https://typro.space/vendor/server/check_java_hash.php");
        runtimeHashCheck.putAllParams(Arrays.asList("os","cpu","hash_runtime", "good_ozu"), Arrays.asList(String.valueOf(ManagerDirs.getPlatform()), System.getProperty("os.arch"), hash, String.valueOf(UserPC.getOzu()>4096)));
        try {
            runtimeHashCheck.request();
        }catch (Exception e){
            System.err.println(runtimeHashCheck);
        }


        System.err.println("========================RUNTIME DOWNLOAD========================\n" + runtimeHashCheck);

        if ("1".equals(runtimeHashCheck.getFullAnswer())) {
            return true;
        }
        urlDownload = runtimeHashCheck.getFullAnswer();
        return false;
    }

    public static void download(ProgressBar progressBar, Text infoText) {
        try {
            File runtime = new File(Main.getRuntimeDir() + File.separator + "jre8");
            if(runtime.exists()){
                Utils.DeleteFile(runtime);
            }else {
                System.err.println("Папка рантайма не найдена. Пропускаю удаление");
            }
        }catch (Exception e){
            System.err.println("Не удалось удалить файл: " + e.getMessage());
        }

        new Thread(()->{
            try {
                ManagerDownload test = new ManagerDownload(urlDownload, Main.getRuntimeDir().getAbsolutePath(), progressBar, infoText);
                test.download();
                infoText.setText("Распаковываем рантаймы :D");
                ManagerZip mz = new ManagerZip(Main.getRuntimeDir().getAbsolutePath() + File.separator + test.getFileName(),
                        Main.getRuntimeDir().getAbsolutePath() + File.separator + "jre8", infoText, runtimeController);
                mz.unzip();
                Platform.runLater(()-> ManagerWindow.OpenNew("AccountAuth.fxml", ManagerWindow.currentController.getA1()));
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }).start();
    }
}