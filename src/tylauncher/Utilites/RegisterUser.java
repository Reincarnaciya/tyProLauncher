package tylauncher.Utilites;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static tylauncher.Utilites.Utils.searchMassChar;

public class RegisterUser {
    private static final  String suffix = "[RegisterUser] ";
    public static void registerUser(String username, String password, String repeat_password, String email, String vers){

        try {
            InputStreamReader isR;
            BufferedReader bfR;

            URL url = new URL("https://typro.space/vendor/launcher/register_launcher.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Map<String, String> arguments = new HashMap<>();
            arguments.put("login", username);
            arguments.put("password", password);
            arguments.put("email", email);
            arguments.put("repeat_password", repeat_password);
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);

            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.setRequestProperty("User-Agent", vers);
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
                if(HttpURLConnection.HTTP_OK == http.getResponseCode()){
                    isR = new InputStreamReader(http.getInputStream());
                    bfR = new BufferedReader(isR);
                    String line;

                    line = bfR.readLine();

                    //while ((line = bfR.readLine()) != null){  //fixme DEBUG MOD НАХУЙ
                    //System.err.println(line);
                    //}

                    line = line.replace("\"", "").replace("}", "").replace("{", "")
                            .replace("]", "").replace("[", "");

                    System.err.println(suffix + line);

                    String[] end_reg = line.split("[,\\-:]");

                    if(line.contains("fields")){
                        System.out.println(suffix + "В ответе присутствует поле fields");

                        System.out.println(suffix + "Массив: " + Arrays.toString(end_reg));

                        WebAnswer.setStatus(end_reg[searchMassChar(end_reg, "status") + 1]);
                        WebAnswer.setType(end_reg[searchMassChar(end_reg, "type") + 1]);
                        WebAnswer.setMessage(end_reg[searchMassChar(end_reg, "message") + 1]);
                        System.out.println(suffix + "Длинна масива - " + end_reg.length);

                        String temp = "";

                        if((end_reg.length  - searchMassChar(end_reg, "fields") - 1)  > 1){
                            for(int i = 0; i < (end_reg.length  - searchMassChar(end_reg, "fields") - 1); i++){
                                temp = temp + " " + (end_reg[searchMassChar(end_reg, "fields") + 1 + i]);
                            }
                            WebAnswer.setFields(temp);
                        } else {
                            WebAnswer.setFields(end_reg[searchMassChar(end_reg, "fields") + 1]);
                        }
                    }else{
                        System.out.println(suffix + "Филдов нет, но вы держитесь");
                        WebAnswer.setStatus(end_reg[searchMassChar(end_reg, "status") + 1]);
                        WebAnswer.setMessage(end_reg[searchMassChar(end_reg, "message") + 1]);
                    }

                    System.out.println(suffix + "Отправлен запрос на регистрацию с параметрами:" + "\n" + arguments);
                    WebAnswer.printAnswer();
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }
    }


}
