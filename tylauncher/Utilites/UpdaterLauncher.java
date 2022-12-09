package tylauncher.Utilites;

import javafx.application.Platform;
import javafx.fxml.FXML;
import tylauncher.Controllers.PlayController;
import tylauncher.Controllers.UpdaterController;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerZip;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class UpdaterLauncher {
    private static boolean upd = false;
    public static UpdaterController updaterController;
    @FXML
    public static void checkUpdate() throws IOException {
        new Thread (()->{
            try {
                System.err.println(Thread.currentThread().getName());
                InputStreamReader inputStreamReader;
                BufferedReader bufferedReader;
                URL url = new URL("https://typro.space/vendor/launcher/CheckingVersion.php");
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                Map<String, String> arguments = new HashMap<>();
                arguments.put("Search", "Launcher");
                arguments.put("Version", Main.launcher_version);
                StringJoiner sj = new StringJoiner("&");
                for (Map.Entry<String, String> entry : arguments.entrySet())
                    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
                byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                http.connect();
                String line = null;
                try (OutputStream os = http.getOutputStream()) {
                    os.write(out);
                    if (HttpURLConnection.HTTP_OK == http.getResponseCode()) {
                        inputStreamReader = new InputStreamReader(http.getInputStream());
                        bufferedReader = new BufferedReader(inputStreamReader);
                        line = bufferedReader.readLine();
                        //while ((line = bufferedReader.readLine()) != null){  //fixme DEBUG MOD НАХУЙ
                        //    System.err.println(line);
                        //    }
                    }
                }
                System.err.println(line);
                if(line.equalsIgnoreCase("0")){
                    Platform.runLater(()-> updaterController.setUpdateAvailable(true));
                }else Platform.runLater(() ->{
                    updaterController.getA1().getScene().getWindow().setWidth(800);
                    updaterController.getA1().getScene().getWindow().setHeight(535);
                    updaterController.getA1().getScene().getWindow().centerOnScreen();
                    Main.OpenNew("AccountAuth.fxml", updaterController.getA1());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void UpdateLauncher(){
        new Thread(() -> {
            try {
                URL url = new URL("https://www.typro.space/files/launcher/Update/TyUpdaterLauncher.jar");
                HttpURLConnection updcon = (HttpURLConnection) url.openConnection();
                System.out.println(updcon);
                File client = new File(Main.getClientDir().getAbsolutePath() + File.separator, "TyUpdaterLauncher.jar");
                if(client.exists()){
                    client.delete();
                }
                long cll_web = updcon.getContentLength();
                if ((client.length() != cll_web) && cll_web > 1) {
                    BufferedInputStream bis = new BufferedInputStream(updcon.getInputStream());
                    FileOutputStream fw = new FileOutputStream(client);
                    byte[] by = new byte[1024];
                    int count = 0;
                    System.err.println("startupdate");
                    while ((count = bis.read(by)) != -1) {
                        fw.write(by, 0, count);
                        System.err.println("Скачка: " + cll_web + "/" + client.length());
                    }
                    fw.close();
                    Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + client.getAbsolutePath() + "\" " + "\"" + UserPC.pathToLauncher + "\"" +  " " + "\"" + Main.getLauncherDir().getAbsolutePath()+ "\""});
                    System.exit(0);
                }
            } catch (IOException e) {
                ErrorInterp.setMessageError(e.getMessage(), "play");
                e.printStackTrace();
            }
        }).start();
    }
}
