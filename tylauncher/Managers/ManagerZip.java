package tylauncher.Managers;

import javafx.scene.text.Text;
import tylauncher.Controllers.BaseController;
import tylauncher.Controllers.PlayController;

import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ManagerZip {
    public static boolean unzipping = false;
    public static PlayController playController;
    private static String fileName;
    private final String zip;
    private final String pathToOut;
    private Text progressText;

    private final boolean updateInformation;

    private final BaseController baseController;

    @Override
    public String toString() {
        return "ManagerZip{" +
                "zip='" + zip + '\'' +
                ", pathToOut='" + pathToOut + '\'' +
                ", progressText=" + progressText +
                ", updateInformation=" + updateInformation +
                '}';
    }

    /**
     *
     * @param zip
     * Полный путь до архива (C://path/to/archive.zip)
     * @param pathToOut
     * Папка, в которую разархивировать (C://path/to/out/(тут все файлы из архива будут))
     */
    public ManagerZip(String zip, String pathToOut, BaseController baseController) {
        this.zip = zip;
        this.pathToOut = pathToOut;
        this.updateInformation = false;
        this.baseController = baseController;
    }

    public ManagerZip(String zip, String pathToOut, Text progressText, BaseController baseController) {
        this.zip = zip;
        this.pathToOut = pathToOut;
        this.progressText = progressText;
        this.updateInformation = true;
        this.baseController = baseController;
    }


    public void unzip() {

        System.err.println("=========================UNZIPPING=========================");
        System.err.printf("ZIP = %s, pathToOut = %s%n", zip, pathToOut);
        try {
            ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(zip)));
            ZipEntry entry = zis.getNextEntry();
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

                    try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
                        int bufferSize = Math.toIntExact(entry.getSize());
                        byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
                        int location;
                        while ((location = zis.read(buffer)) != -1) {
                            bos.write(buffer, 0, location);
                            if (this.updateInformation) ManagerZip.updateInfo(baseController);
                        }
                    }
                }
                entry = zis.getNextEntry();
            }
            ManagerWindow.currentController.setInfoText("Готово! Масюня желает Вам приятной игры!");
            zis.closeEntry();
            zis.close();
            File file = new File(zip);
            file.delete();
            unzipping = false;
        }catch (Exception e){
            e.printStackTrace();
            ManagerWindow.currentController.setInfoText(e.getMessage());
        }
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
}
