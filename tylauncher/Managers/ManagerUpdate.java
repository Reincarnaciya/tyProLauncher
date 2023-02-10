package tylauncher.Managers;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.PlayController;
import tylauncher.Main;
import tylauncher.Utilites.Settings;

import java.io.File;

public class ManagerUpdate {

    private static final String suffix = "[ManagerUpdate] ";
    public static PlayController playController;

    public static ManagerDownload update;

    /**
     * Коннект, скачать, разархивировать, что не понятно?
     * @param version
     * Имя папки, в которую разархивируется клиент.
     * @param urld
     * Ссылка на файл
     */
    public static void DownloadUpdate(String version, String urld, ProgressBar progressBar, Text text) {
        try {
            update = new ManagerDownload(urld,
                    Main.getClientDir().getAbsolutePath(), progressBar, text);
            update.download();

            ManagerZip mz = new ManagerZip(Main.getClientDir().getAbsolutePath() + File.separator + update.getFileName(),
                    Main.getClientDir().getAbsolutePath() + File.separator + version, text, playController);
            System.err.println("===================UNZIP (DOWNLOAD UPD)===================\n" + mz);
            mz.unzip();
            ManagerStart starter = new ManagerStart(Settings.isAutoConnect(), Settings.getFsc(), version);
            starter.Start();
        } catch (Exception e) {
            e.printStackTrace();
            ManagerWindow.currentController.setInfoText(e.getMessage());
        }

    }
}
