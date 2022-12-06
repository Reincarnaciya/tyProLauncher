package tylauncher.Utilites.Managers;

import tylauncher.Controllers.PlayController;
import tylauncher.Utilites.ErrorInterp;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ManagerZip {
    public static boolean unzipping = false;
    public static PlayController playController;

    private static String fileName;

    public static void Unzip(String zip, String pathToOut) throws IOException {
        if(ManagerUpdate.downloading){
            return;
        }
        if(unzipping){
            updateInfo();
            return;
        }

        unzipping = true;
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry = zis.getNextEntry();
            String[] name;
            while (entry != null) {
                name = entry.getName().toString().split("/");

                fileName = name[0];

                updateInfo();

                File file = new File(pathToOut, entry.getName());

                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
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
            playController.setTextOfDownload("Готово! Масюня желает Вам приятной игры!");
            zis.closeEntry();
            unzipping = false;
        }
        File file = new File (zip);
        file.delete();
        try {
            ManagerStart.StartMinecraft("TySci_1.16.5");
        }catch (Exception e){
            unzipping = false;
            ErrorInterp.setMessageError(e.getMessage(), "play");
            e.printStackTrace();
        }




    }


    public static void updateInfo() {
        if(!unzipping){
            return;
        }
        playController.udpateProgressBar(1);
        new Thread(() -> {
            switch (fileName) {
                case "assets":
                    playController.setTextOfDownload("Распаковываем штуки");
                    break;
                case "launcher_libraries":
                    playController.setTextOfDownload("Распаковываем важные штуки");
                    break;
                case "libraries":
                    playController.setTextOfDownload("Прогоняем Масюню..");
                    break;
                case "runtime":
                    playController.setTextOfDownload("Прогнали!");
                    break;
                case "versions":
                    playController.setTextOfDownload("Почти готово..");
                    break;
            }
        }).start();


    }
}
