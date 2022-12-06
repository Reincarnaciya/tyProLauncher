package tylauncher.Utilites.Managers;

import javafx.scene.text.Text;
import tylauncher.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ManagerPacket { // path: зачем это вообще нужно?
    private static final String suffix = "[PacketManager] ";

    public static void Unzip(String source, String out, Text text) throws IOException {
        text.setText("Начинаем распавковку");

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {
            text.setText("Начинаем распавковку");
            ZipEntry entry = zis.getNextEntry();
            String[] name;
            while (entry != null) {
                name = entry.getName().split("/");
                if (name[0].equals("assets")) {
                    text.setText("Распаковываем штуки");
                } else if (name[0].equals("launcher_libraries")) {
                    text.setText("Распаковываем важные штуки");
                } else if (name[0].equals("libraries")) {
                    text.setText("Прогоняем Масюню..");
                } else if (name[0].equals("runtime")) {
                    text.setText("Прогнали!");
                } else if (name[0].equals("versions")) {
                    text.setText("Почти готово..");
                }

                System.out.println(suffix + "name0 - " + name[0]);
                File file = new File(out, entry.getName());

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
            text.setText("Готово!");
            zis.closeEntry();

        }
        File file = new File(source);
        if (file.delete()) { //ТУТ ФАЙЛ ДОЛЖЕН УДАЛИТЬСЯ
            System.out.println(suffix + file.getName() + " deleted");
        } else {
            System.out.println(suffix + file.getName() + " not deleted");
        }
    }

    public static void getUpdate() {
        new Thread(() -> {
            try {
                //pt.getScene().getWindow().setOnCloseRequest(Event -> System.exit(0));
                URL url = new URL("https://www.typro.space/files/client_mc/client1165.zip");
                HttpURLConnection updcon;
                updcon = (HttpURLConnection) url.openConnection();
                System.out.println(updcon);
                File client = new File(Main.getClientDir() + File.separator, "client1165.zip");
                long cll_web = updcon.getContentLength();
                FileOutputStream fileOutputStream;

                if ((client.length() != cll_web) && cll_web > 1) {
                    BufferedInputStream bis = new BufferedInputStream(updcon.getInputStream());

                    fileOutputStream = new FileOutputStream(client);

                    byte[] by = new byte[1024];
                    int count = 0;

                    while ((count = bis.read(by)) != -1) {
                        fileOutputStream.write(by, 0, count);
                        System.out.println(suffix + "Скачано " + ((int) client.length() / 1048576) + "Мбайт из " + (cll_web / 1048576) + "Мб");
                        //pb.setProgress((double)((client.length()/10485)/(cll_web/1048576))/100);
                        //pt.setText("Скачано " + ((int) client.length() / 1048576) + "Мбайт из " + (cll_web / 1048576) + "Мб");
                    }
                    fileOutputStream.close();

                    //unzip(ManagerLauncher.GetClientDir().getAbsolutePath() + File.separator + "client1165.zip", ManagerLauncher.GetClientDir().getAbsolutePath() + File.separator,pt);
                }

                //start_m(pb, pbt);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}