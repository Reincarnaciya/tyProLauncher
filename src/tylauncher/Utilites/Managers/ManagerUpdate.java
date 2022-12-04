package tylauncher.Utilites.Managers;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.AccountController;
import tylauncher.Controllers.PlayController;
import tylauncher.Main;
import tylauncher.Utilites.ErrorInterp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManagerUpdate {

    private static final String suffix = "[ManagerUpdate] ";
    public static PlayController playController;
    public static boolean downloading = false;

    private static long clientLength = 0;
    private static long cllweb = 0;

    public static void DownloadUpdate(String version, String urld) {

        if(downloading){
            updateInfo();
        }else {
        new Thread(() -> {
            try {
                playController.setTextOfDownload("Инициализация загрузки");
                downloading = true;
                playController.PlayButtonEnabled(false);

                URL url = new URL(urld);
                HttpURLConnection updcon = (HttpURLConnection) url.openConnection();
                System.out.println(updcon);
                File client = new File(Main.getClientDir().getAbsolutePath() + File.separator ,"client1165.zip");
                long cll_web = updcon.getContentLength();


                if ((client.length() != cll_web) && cll_web > 1){
                    BufferedInputStream bis = new BufferedInputStream(updcon.getInputStream());
                    FileOutputStream fw = new FileOutputStream(client);

                    byte[] by = new byte[1024];
                    int count = 0;

                    while ((count = bis.read(by)) != -1){
                        fw.write(by, 0, count);
                        clientLength = client.length();
                        cllweb = cll_web;
                        updateInfo();
                    }
                    fw.close();
                    downloading = false;
                    ManagerZip.Unzip(Main.getClientDir().getAbsolutePath() + File.separator + "client1165.zip", Main.getClientDir().getAbsolutePath() + File.separator  + version + File.separator);
                }

            } catch (IOException e) {
                downloading = false;
                ErrorInterp.setMessageError(e.getMessage(), "play");
                e.printStackTrace();
            }
        })
                .start();
    }
    }
    public static void updateInfo(){
        new Thread(() -> {
            if(!downloading){
                return;
            }
            playController.setTextOfDownload(("Скачано " + ((int) clientLength / 1048576) + "Мбайт из " + (cllweb / 1048576) + "Мб"));
            playController.udpateProgressBar((double)((clientLength/10485)/(cllweb/1048576))/100);
            ManagerZip.updateInfo();
        }).start();
    }
}
