package tylauncher.Utilites.Managers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import tylauncher.Main;
import tylauncher.Utilites.ErrorInterp;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ManagerZip {
    public static void Unzip(String zip, String pathToOut, Text text) throws IOException {

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry = zis.getNextEntry();
            String[] name;
            while (entry != null) {
                name = entry.getName().toString().split("/");

                switch (name[0]) {
                    case "assets":
                        text.setText("Распаковываем штуки");
                        break;
                    case "launcher_libraries":
                        text.setText("Распаковываем важные штуки");
                        break;
                    case "libraries":
                        text.setText("Прогоняем Масюню..");
                        break;
                    case "runtime":
                        text.setText("Прогнали!");
                        break;
                    case "versions":
                        text.setText("Почти готово..");
                        break;
                }

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
            text.setText("Готово! Масюня желает Вам приятной игры!");
            zis.closeEntry();
        }
        File file = new File (zip);
        file.delete();
        try {
            ManagerStart.StartMinecraft(text, "TySci_1.16.5");
        }catch (Exception e){
            ErrorInterp.setMessageError(e.getMessage(), "play");
            e.printStackTrace();
        }




    }








}
