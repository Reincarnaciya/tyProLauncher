package tylauncher.Managers;

import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.PlayController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ManagerDownload {
    public static PlayController playController;
    public static boolean download = false;
    private final URL url;
    private final File outputFile;
    private int clientLength = 0;
    private int webLength = 0;

    private static int cll;
    private static int webL;

    private boolean type = false;
    private ProgressBar downloadBar;
    private Text infoText;
    private final String fileName;

    public ManagerDownload(String url, String pathToOut, @Nullable ProgressBar downloadBar, @Nullable Text infoText) throws MalformedURLException {
       this.url = new URL(url);
       this.fileName =  url.substring(url.lastIndexOf('/')+1);
       this.outputFile = new File(pathToOut, this.fileName);

       if(downloadBar == null || infoText == null){
           type = true;
       }else {
           this.downloadBar = downloadBar;
           this.infoText = infoText;
       }

    }

    public String getFileName() {
        return fileName;
    }

    public void download() throws Exception {
        HttpURLConnection updcon = (HttpURLConnection) this.url.openConnection();

        this.webLength = updcon.getContentLength();
        webL = this.webLength;

        download = true;

        if(webLength > 1){
            BufferedInputStream bufferedInputStream = new BufferedInputStream(updcon.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            byte[] bytes = new byte[1024];
            int count;
            while ((count = bufferedInputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes, 0, count);
                clientLength = (int) outputFile.length();
                cll = this.clientLength;
                if (!type) updateInfo();

            }
            fileOutputStream.close();
            bufferedInputStream.close();
            updcon.disconnect();
        }else {
            download = false;
            throw new Exception("Веб файл хуйня");
        }
        download = false;
    }

    public void updateInfo(){
        new Thread(()-> {
            Platform.runLater(()->{
                try {
                    playController.UdpateProgressBar((double) ((clientLength / 10485) / (webLength / 1048576)) / 100);
                    playController.setInfoText(("Скачано " + (clientLength / 1048576) + "Мбайт из " + (webLength / 1048576) + "Мб"));
                }catch (Exception e){
                    this.downloadBar.setProgress(((double) ((clientLength / 10485) / (webLength / 1048576)) / 100));
                    this.infoText.setText(("Скачано " + (clientLength / 1048576) + "Мбайт из " + (webLength / 1048576) + "Мб"));
                }
            });
        }).start();

    }

    @Override
    public String toString() {
        return "ManagerDownload{" +
                "url=" + url +
                ", outputFile=" + outputFile +
                ", clientLength=" + clientLength +
                ", webLength=" + webLength +
                '}';
    }
}
