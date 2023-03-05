package tylauncher.Managers;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import tylauncher.Controllers.PlayController;
import tylauncher.Main;
import tylauncher.Utilites.Logger;
import tylauncher.Utilites.Settings;

import java.io.File;
import java.net.URL;

public class ManagerUpdate {

    private static final Logger logger = new Logger(ManagerUpdate.class);
    public static PlayController playController;

    public static ManagerDownload update;

    /**
     * Коннект, скачать, разархивировать, что не понятно?
     *
     * @param version Имя папки, в которую разархивируется клиент.
     * @param urld    Ссылка на файл
     */
    public static void DownloadUpdate(String version, URL urld, ProgressBar progressBar, Text text) {
        try {
            update = new ManagerDownload(urld,
                    Main.getClientDir().getAbsolutePath(), progressBar, text);
            update.download();

            ManagerZip mz = new ManagerZip(Main.getClientDir().getAbsolutePath() + File.separator + update.getFileName(),
                    Main.getClientDir().getAbsolutePath() + File.separator + version, text, playController.getProgressBar(), playController);
            mz.unzip();
        } catch (Exception e) {
            logger.logError(e, ManagerWindow.currentController);
        }

    }
}
