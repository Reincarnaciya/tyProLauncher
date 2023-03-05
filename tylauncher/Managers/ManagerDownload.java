package tylauncher.Managers;

import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.PlayController;
import tylauncher.Utilites.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManagerDownload {
    private static final Logger logger = new Logger(ManagerDownload.class);
    public static PlayController playController;
    public static boolean download = false;
    private final URL url;
    private final File outputFile;
    private final String fileName;
    private int clientLength = 0;
    private int webLength = 0;
    private boolean type = false;
    private ProgressBar downloadBar;
    private Text infoText;

    public ManagerDownload(URL url, String pathToOut, @Nullable ProgressBar downloadBar, @Nullable Text infoText){
        System.err.println(url + "\n" + pathToOut + "\n" + downloadBar + "\n" + infoText);
        this.url = url;
        this.fileName = url.toString().substring(url.toString().lastIndexOf('/') + 1);
        this.outputFile = new File(pathToOut, this.fileName);

        if (downloadBar == null || infoText == null) {
            type = true;
        } else {
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

        download = true;

        if (webLength > 1) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(updcon.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            byte[] bytes = new byte[1024];
            int count;
            while ((count = bufferedInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, count);
                clientLength = (int) outputFile.length();
                int cll = this.clientLength;
                if (!type) updateInfo();

            }
            fileOutputStream.close();
            bufferedInputStream.close();
            updcon.disconnect();
        } else {
            download = false;
            throw new Exception("Веб файл хуйня");
        }
        download = false;
    }

    public void updateInfo() {
        new Thread(() -> Platform.runLater(() -> {
            try {
                playController.UdpateProgressBar((double) ((clientLength / 10485) / (webLength / 1048576)) / 100);
                playController.setInfoText(("Скачано " + (clientLength / 1048576) + "Мбайт из " + (webLength / 1048576) + "Мб"));
            } catch (Exception e) {
                this.downloadBar.setProgress(((double) ((clientLength / 10485) / (webLength / 1048576)) / 100));
                this.infoText.setText(("Скачано " + (clientLength / 1048576) + "Мбайт из " + (webLength / 1048576) + "Мб"));
            }
        })).start();

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
