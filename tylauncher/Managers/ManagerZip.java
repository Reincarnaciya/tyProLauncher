package tylauncher.Managers;

import tylauncher.Controllers.PlayController;
import tylauncher.Utilites.Settings;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ManagerZip {
    public static boolean unzipping = false;
    public static PlayController playController;
    private static String fileName;


    /**
     *
     * @param zip
     * Полный путь до архива (C://path/to/archive.zip)
     * @param pathToOut
     * Папка, в которую разархивировать (C://path/to/out/(тут все файлы из архива будут))
     * @throws IOException
     * Это просто пиздец.
     * Возникат, если файлы повреждены, или хуево скачались. В любом случае, в консоли вы увидите  ;)
     */
    public static void Unzip(String zip, String pathToOut) throws IOException {
        if (ManagerUpdate.downloading) return;

        if (unzipping) {
            UpdateInfo();
            return;
        }
        unzipping = true;
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(zip)))) {
            ZipEntry entry = zis.getNextEntry();
            String[] name;
            while (entry != null) {
                name = entry.getName().split("/");
                fileName = name[0];
                UpdateInfo();
                File file = new File(pathToOut, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
                        int bufferSize = Math.toIntExact(entry.getSize());
                        byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
                        int location;
                        while ((location = zis.read(buffer)) != -1) {
                            bos.write(buffer, 0, location);
                        }
                    }
                }
                entry = zis.getNextEntry();
            }
            ManagerWindow.currentController.setInfoText("Готово! Масюня желает Вам приятной игры!");
            zis.closeEntry();
            unzipping = false;
        }
        File file = new File(zip);
        file.delete();
        try {
            ManagerStart starter = new ManagerStart(Settings.isAutoConnect(), Settings.getFsc(), "TySci_1.16.5");
            starter.Start();
        } catch (Exception e) {
            unzipping = false;
            ManagerWindow.currentController.setInfoText (e.getMessage());
            e.printStackTrace();
        }

    }


    public static void UpdateInfo() {
        if (!unzipping) return;
        playController.UdpateProgressBar(1);
        new Thread(() -> {
            switch (fileName) {
                case "assets":
                    ManagerWindow.currentController.setInfoText("Распаковываем штуки");
                    break;
                case "launcher_libraries":
                    ManagerWindow.currentController.setInfoText("Распаковываем важные штуки");
                    break;
                case "libraries":
                    ManagerWindow.currentController.setInfoText("Прогоняем Масюню..");
                    break;
                case "runtime":
                    ManagerWindow.currentController.setInfoText("Прогнали!");
                    break;
                case "versions":
                    ManagerWindow.currentController.setInfoText("Почти готово..");
                    break;
            }
        }).start();

    }
}
