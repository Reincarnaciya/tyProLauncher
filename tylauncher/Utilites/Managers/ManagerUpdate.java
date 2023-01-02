package tylauncher.Utilites.Managers;

import tylauncher.Controllers.PlayController;
import tylauncher.Main;

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

    /**
     * Коннект, скачать, разархивировать, что не понятно?
     * @param version
     * Имя папки, в которую разархивируется клиент.
     * @param urld
     * Ссылка на файл
     */
    public static void DownloadUpdate(String version, String urld) {
        if (downloading) {
            UpdateInfo();
            return;
        }
        new Thread(() -> {
            try {
                ManagerWindow.currentController.setInfoText("Инициализация загрузки");
                downloading = true;
                playController.PlayButtonEnabled(false);
                URL url = new URL(urld);
                HttpURLConnection updcon = (HttpURLConnection) url.openConnection();
                System.out.println(updcon);
                File client = new File(Main.getClientDir().getAbsolutePath() + File.separator, "client1165.zip");
                long cll_web = updcon.getContentLength();
                if ((client.length() != cll_web) && cll_web > 1) {
                    BufferedInputStream bis = new BufferedInputStream(updcon.getInputStream());
                    FileOutputStream fw = new FileOutputStream(client);
                    playController.getA1().getScene().getWindow().setOnCloseRequest(event -> {
                        try {
                            fw.close();
                            bis.close();
                            Main.exit();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    byte[] by = new byte[1024];
                    int count = 0;
                    while ((count = bis.read(by)) != -1) {
                        fw.write(by, 0, count);
                        clientLength = client.length();
                        cllweb = cll_web;
                        UpdateInfo();
                    }
                    fw.close();
                    downloading = false;
                    ManagerZip.Unzip(Main.getClientDir().getAbsolutePath() + File.separator + "client1165.zip", Main.getClientDir().getAbsolutePath() + File.separator + version + File.separator);
                }

            } catch (java.net.SocketException e){
                playController.setInfoText("Соединение прервано, пробую повторить закачку.." + e.getMessage());
                DownloadUpdate(version, urld);
            } catch (IOException e) {
                playController.setInfoText(e.getMessage());
                e.printStackTrace();
            }

        }).start();
    }
    public static void UpdateInfo() {
        new Thread(() -> {
            if (!downloading) {
                return;
            }
            playController.setInfoText(("Скачано " + ((int) clientLength / 1048576) + "Мбайт из " + (cllweb / 1048576) + "Мб"));
            playController.UdpateProgressBar((double) ((clientLength / 10485) / (cllweb / 1048576)) / 100);
            ManagerZip.UpdateInfo();
        }).start();
    }
}
