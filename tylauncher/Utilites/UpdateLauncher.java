package tylauncher.Utilites;

import tylauncher.Main;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class UpdateLauncher {
    public static void checkUpdate() throws IOException {
        new Thread(()->{
            try {
                InputStreamReader inputStreamReader;
                BufferedReader bufferedReader;
                URL url = new URL("https://typro.space/vendor/launcher/CheckingVersion.php");
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                Map<String, String> arguments = new HashMap<>();
                arguments.put("Search", "Launcher");
                arguments.put("Version", "0.0");
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
                if(line.equalsIgnoreCase("0")){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();




    }
}
