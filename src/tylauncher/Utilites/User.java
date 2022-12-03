package tylauncher.Utilites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tylauncher.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerError;
import java.util.*;

public class User {
    private String _email;
    private long _id;
    private String _password;
    private String _login;
    private String _session;
    private String _balance;
    private String _group;
    private Image _image;

    public User() {
        this._id = -1;
        this._email = "";
        this._password = "";
        this._login = "";
        this._session = "";
        this._balance = "0";
        this._group = "[Игрок]";
        this._image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/picked/steve.png")));
    }

    public User(String login, String password) {
        this._password = password.trim().toLowerCase();
        this._login = login.trim().toLowerCase();
        this._group = "[Игрок]";
        this._id = 0;
        this._balance = "0";
        this._email = "ty.pro";
        this._session = null;
        this._image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/picked/steve.png")));
    }

    public User(String login, String password,  String session) {
        this._password = password;
        this._login = login;
        this._session = session;
    }


    public boolean Auth() throws Exception {
        WebAnswer.reset();
        if (_login.isEmpty() || _password.isEmpty()) throw new Exception("Логин или пароль не введены");

        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;

        URL url = new URL("https://typro.space/vendor/launcher/login_launcher.php");
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        Map<String, String> param = new HashMap<>();
        param.put("login", this._login);
        param.put("password", this._password);
        param.put("version", Main.launcher_version);

        StringJoiner stringJoiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : param.entrySet())
            stringJoiner.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = stringJoiner.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpURLConnection.setConnectTimeout(1000);
        httpURLConnection.connect();

        try(OutputStream outputStream = httpURLConnection.getOutputStream()){
            outputStream.write(out);
            if(HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()){
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;

                line = bufferedReader.readLine()
                        .replace("\"", "")
                        .replace("}", "")
                        .replace("{", "")
                        .replace("]", "")
                        .replace("[", "");

                System.out.println("Ответ с сервера после обработки: " + line);

                String[] answer = line.split("[,:]");

                System.out.println("Ответ с сервера массивом: "+ Arrays.toString(answer));

                WebAnswer.reset();

                if(answer[0].equals("status")) WebAnswer.setStatus(answer[1]);
                if(answer[2].equals("type")) WebAnswer.setType(answer[3]);
                if(answer[4].equals("message")) WebAnswer.setMessage(answer[5]);
                if(answer[2].equals("user") && answer[3].equals("id")) this._id = Integer.parseInt(answer[4]);
                if(answer[5].equals("login")) this._login = answer[6];
                if(answer.length > 7){
                    if(answer[7].equals("email")) this._email = answer[8];
                    if(answer[9].equals("session")) this._session = answer[10];
                    if(answer[11].equals("coin")) this._balance = answer[12];
                }


                return WebAnswer.getStatus();

            }else throw new Exception(String.valueOf(httpURLConnection.getResponseMessage()));
        }
    }

    public String GetBalance() {
        return _balance;
    }

    public String GetGroup() {
        return _group;
    }

    public String GetLogin() {
        return _login;
    }

    public Image GetImage(){
        return this._image;
    }

    public String GetPassword(){
        return this._password;
    }

    public String getSession() {
        return _session;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public void setLogin(String _login) {
        this._login = _login;
    }

    public void setSession(String _session) {
        this._session = _session;
    }

    public void setBalance(String _balance) {
        this._balance = _balance;
    }

    public void setGroup(String _group) {
        this._group = _group;
    }

    public void setImage(Image _image) {
        this._image = _image;
    }

    public void reset(){
        this._password = "";
        this._session = "";
        this._login = "";
        this._group = "[Игрок]";
        this._balance = "0$";
        this._image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/picked/steve.png")));
    }

    @Override
    public String toString() {
        return "User{" +
                "_email='" + _email + '\'' +
                ", _id=" + _id +
                ", _password='" + _password + '\'' +
                ", _login='" + _login + '\'' +
                ", _session='" + _session + '\'' +
                ", _balance='" + _balance + '\'' +
                ", _group='" + _group + '\'' +
                ", _image=" + _image +
                '}';
    }
}
