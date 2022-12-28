package tylauncher.Utilites;

import javafx.application.Platform;
import javafx.fxml.FXML;
import tylauncher.Controllers.UpdaterController;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerWeb;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class UpdaterLauncher {
    public static UpdaterController updaterController;
    private static ManagerWeb updateManagerWeb = new ManagerWeb("POST");
    @FXML
    public static void checkUpdate() {
        new Thread (()->{
            try {

                //"https://typro.space/vendor/launcher/CheckingVersion.php"
                updateManagerWeb.setUrl("https://typro.space/vendor/launcher/CheckingVersion.php");
                updateManagerWeb.putParam("hash", "rehtrjtkykyjhtjhjotrjhoitrjoihjoith");
                updateManagerWeb.putParam("version", Main.launcher_version);
                updateManagerWeb.request();

                String line = updateManagerWeb.getAnswer();
                System.err.println(line);
                if(!line.equalsIgnoreCase("1")){
                    Platform.runLater(()-> updaterController.setUpdateAvailable(true));
                }else Platform.runLater(() ->{
                    ManagerWindow.currentController.getA1().getScene().getWindow().setWidth(800);
                    ManagerWindow.currentController.getA1().getScene().getWindow().setHeight(535);
                    ManagerWindow.currentController.getA1().getScene().getWindow().centerOnScreen();
                    Main.OpenNew("AccountAuth.fxml", ManagerWindow.currentController.getA1());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void UpdateLauncher(){
        new Thread(() -> {
            try {
                URL url = new URL("https://www.typro.space/files/launcher/Update/TyUpdaterLauncher.jar");
                HttpURLConnection updcon = (HttpURLConnection) url.openConnection();
                System.out.println(updcon);
                File client = new File(Main.getClientDir().getAbsolutePath() + File.separator, "TyUpdaterLauncher.jar");
                if(client.exists()){
                    client.delete();
                }
                long cll_web = updcon.getContentLength();
                if ((client.length() != cll_web) && cll_web > 1) {
                    BufferedInputStream bis = new BufferedInputStream(updcon.getInputStream());
                    FileOutputStream fw = new FileOutputStream(client);
                    byte[] by = new byte[1024];
                    int count = 0;
                    System.err.println("startupdate");
                    while ((count = bis.read(by)) != -1) {
                        fw.write(by, 0, count);
                        System.err.println("Скачка: " + cll_web + "/" + client.length());
                    }
                    fw.close();
                    Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + client.getAbsolutePath() + "\" " + "\"" + UserPC._pathToLauncher + "\"" +  " " + "\"" + Main.getLauncherDir().getAbsolutePath()+ "\""});
                    Main.exit();
                }
            } catch (IOException e) {
                ManagerWindow.currentController.setInfoText (e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}
