package tylauncher.Utilites;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerWeb;

import java.util.Arrays;
import java.util.Objects;

public class User {
    private String _email;
    private final long _id;
    private String _password;
    private String _login;
    private String _session;
    private String _balance;
    private String _group;
    private String _endDonateTime;
    private Image _image;
    public boolean wasAuth = false;

    public User() {
        this._id = -1;
        this._email = "";
        this._password = "";
        this._login = "";
        this._session = "";
        this._balance = "0";
        this._group = "[Игрок]";
        this._endDonateTime = null;
        this._image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/picked/steve.png")));
    }
    public boolean Auth() throws Exception {
        WebAnswer.Reset();
        //if (_login.equalsIgnoreCase("test"))return true;


        if (_login.isEmpty() || _password.isEmpty()) throw new Exception("Логин или пароль не введены");

        ManagerWeb authManagerWeb = new ManagerWeb("auth");
        authManagerWeb.setUrl("https://typro.space/vendor/launcher/login_launcher.php");
        authManagerWeb.putAllParams(Arrays.asList("login", "password"), Arrays.asList(_login, _password));
        authManagerWeb.request();

        JsonObject answerFromServer = (JsonObject) JsonParser.parseString(authManagerWeb.getFullAnswer());
        JsonObject user = null;
        JsonObject privilege = null;

        if (answerFromServer.get("user") != null){
            user = (JsonObject) JsonParser.parseString(answerFromServer.get("user").toString());
            privilege = (JsonObject) JsonParser.parseString(user.get("privilege").toString());
        }
        System.err.println("------------------------JSON--------------------------");
        System.err.println(answerFromServer);
        System.err.println(user);
        System.err.println(privilege);
        System.err.println("------------------------JSON--------------------------");

        if(answerFromServer.get("type") != null) WebAnswer.setType(answerFromServer.get("type").toString());
        if(answerFromServer.get("message") != null) WebAnswer.setMessage(answerFromServer.get("message")
                .toString().replace("\"", ""));
        if(answerFromServer.get("status") != null) WebAnswer.setStatus(answerFromServer.get("status").toString());
        if(answerFromServer.get("fields") != null) WebAnswer.setFields(answerFromServer.get("fields").toString());

        if(user != null){
            System.err.println(user.get("user_nick"));
            this.wasAuth = true;
            if(user.get("user_nick") != null) _login = user.get("user_nick").toString().replace("\"", "");
            if(user.get("email") != null) _email = user.get("email").toString().replace("\"", "");
            if(user.get("ty_coin") != null) _balance = "Баланс: " + user.get("ty_coin").toString();

            if(privilege.get("privilegeDonate") != null) _group = "Роль: " + privilege.get("privilegeDonate").toString()
                    .replaceFirst("\"", "[").replaceFirst("\"", "]");

            if (!privilege.get("privilegeAdmin").toString().contains("null")){
                _group = _group + " [" + privilege.get("privilegeAdmin").toString().replace("\"", "") + "]";
            }
            if(!privilege.get("end_time").toString().contains("null")){
                _endDonateTime = privilege.get("end_time").toString();
                _group = _group + "\n\nИстекает: " + _endDonateTime.replace("\"", "");
            }
        }




        System.err.println("-----------------------------AUTH-INFO-----------------------------");
        System.err.println(authManagerWeb);
        System.err.println(this);
        WebAnswer.PrintAnswer();
        System.err.println("-----------------------------AUTH-INFO-----------------------------");
        if(!WebAnswer.getStatus()){
            Reset();
        }
        return WebAnswer.getStatus();
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

    public Image GetImage() {
        return this._image;
    }

    public String GetPassword() {
        return this._password;
    }

    public String getSession() {
        return _session;
    }
    public void setSession(String _session) {
        this._session = _session;
    }
    public String getEmail() {
        return _email;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public void setLogin(String _login) {
        this._login = _login;
    }

    public void setImage(Image _image) {
        this._image = _image;
    }

    public void Reset() {
        this._password = "";
        this._session = "";
        this._login = "";
        this._group = "[Игрок]";
        this._balance = "0$";
        this.wasAuth = false;
        this._image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/picked/steve.png")));
    }

    @Override
    public String toString() {
        return "User{" + "_email='" + _email + '\'' + ", _id=" + _id + ", _password='" + _password + '\'' + ", _login='" + _login + '\'' + ", _session='" + _session + '\'' + ", _balance='" + _balance + '\'' + ", _group='" + _group + '\'' + ", _image=" + _image.toString() + '}';
    }
}
