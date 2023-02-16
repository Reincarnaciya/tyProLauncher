package tylauncher.Managers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import tylauncher.Controllers.BaseController;
import tylauncher.Controllers.PlayController;
import tylauncher.Utilites.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ManagerZip {
    private static final Logger logger = new Logger(ManagerZip.class);
    public static boolean unzipping = false;
    public static PlayController playController;
    private static String fileName;
    private final String zip;
    private final String pathToOut;
    private final boolean updateInformation;
    private final BaseController baseController;
    private final Text progressText;

    /**
     * @param zip       Полный путь до архива (C://path/to/archive.zip)
     * @param pathToOut Папка, в которую разархивировать (C://path/to/out/(тут все файлы из архива будут))
     */

    public ManagerZip(String zip, String pathToOut, Text progressText, BaseController baseController) {
        this.zip = zip;
        this.pathToOut = pathToOut;
        this.progressText = progressText;
        this.updateInformation = true;
        this.baseController = baseController;
    }

    public ManagerZip(String zip, String pathToOut, Text progressText, ProgressBar progressBar, BaseController baseController) {
        Platform.runLater(() -> progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS));
        this.zip = zip;
        this.pathToOut = pathToOut;
        this.progressText = progressText;
        this.updateInformation = true;
        this.baseController = baseController;
    }

    public static void updateInfo(BaseController baseController) {
        new Thread(() -> {
            switch (fileName) {
                case "assets":
                    playController.setInfoText("Распаковываем штуки");
                    break;
                case "launcher_libraries":
                    playController.setInfoText("Распаковываем важные штуки");
                    break;
                case "libraries":
                    playController.setInfoText("Распаковываем еще более важные штуки..");
                    break;
                case "runtime":
                    playController.setInfoText("Важнее этих штук ничего нет..");
                    break;
                case "versions":
                    playController.setInfoText("Почти готово..");
                    break;
                default:
                    baseController.setInfoText("Распаковка архивов..");
                    break;
            }
        }).start();
    }

    @Override
    public String toString() {
        return "ManagerZip{" +
                "zip='" + zip + '\'' +
                ", pathToOut='" + pathToOut + '\'' +
                ", progressText=" + progressText +
                ", updateInformation=" + updateInformation +
                '}';
    }

    public void unzip() {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(zip)));
            ZipEntry entry = zipInputStream.getNextEntry();
            String[] name;
            unzipping = true;
            while (entry != null) {
                name = entry.getName().split("/");
                fileName = name[0];
                if (this.updateInformation) ManagerZip.updateInfo(baseController);
                File file = new File(pathToOut, entry.getName());
                if (entry.isDirectory()) file.mkdirs();
                else {
                    File parent = file.getParentFile();

                    if (!parent.exists()) parent.mkdirs();

                    try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
                        int bufferSize = Math.toIntExact(entry.getSize());
                        byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
                        int location;
                        while ((location = zipInputStream.read(buffer)) != -1) {
                            bufferedOutputStream.write(buffer, 0, location);
                            if (this.updateInformation) ManagerZip.updateInfo(baseController);
                        }
                    }
                }
                entry = zipInputStream.getNextEntry();
            }
            ManagerWindow.currentController.setInfoText("Готово! Масюня желает Вам приятной игры!");
            zipInputStream.closeEntry();
            zipInputStream.close();
            File file = new File(zip);
            file.delete();
            unzipping = false;
        } catch (Exception e) {
            logger.logError(e, ManagerWindow.currentController);
        }
    }


}
