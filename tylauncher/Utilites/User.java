package tylauncher.Utilites;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import tylauncher.Main;
import tylauncher.Managers.ManagerWeb;
import tylauncher.Utilites.Constants.URLS;

import java.util.Arrays;
import java.util.Objects;

public class User {
    private static final Logger logger = new Logger(User.class);
    private final long _id;
    public boolean wasAuth = false;
    private String _email;
    private String _password;
    private String _login;
    private String _session;
    private String _balance;
    private String _group;
    private String _endDonateTime;
    private Image _image;

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

    public boolean auth() throws Exception {
        WebAnswer.Reset();
        //if (_login.equalsIgnoreCase("test"))return true;

        //if (_login.isEmpty() || _password.isEmpty()) throw new Exception("Логин или пароль не введены");

        ManagerWeb authManagerWeb = new ManagerWeb("auth");
        authManagerWeb.setUrl(URLS.AUTH_USER);
        authManagerWeb.putAllParams(Arrays.asList("login", "password"), Arrays.asList(_login, _password));
        authManagerWeb.request();

        JsonObject answerFromServer = (JsonObject) JsonParser.parseString(authManagerWeb.getFullAnswer());
        JsonObject user = null;
        JsonObject privilege = null;


        if (answerFromServer.get("user") != null) {
            user = (JsonObject) JsonParser.parseString(answerFromServer.get("user").toString());
            privilege = (JsonObject) JsonParser.parseString(user.get("ty_privilege").toString());
        }

        if (user != null) {
            logger.logInfo(answerFromServer.toString(), user.toString(), privilege.toString());
        }

        if (answerFromServer.get("type") != null) WebAnswer.setType(answerFromServer.get("type").toString());
        if (answerFromServer.get("message") != null) WebAnswer.setMessage(answerFromServer.get("message")
                .toString().replace("\"", ""));
        if (answerFromServer.get("status") != null) WebAnswer.setStatus(answerFromServer.get("status").toString());
        if (answerFromServer.get("fields") != null) WebAnswer.setFields(answerFromServer.get("fields").toString());

        if (user != null) {
            this.wasAuth = true;
            if (user.get("user_nick") != null) _login = user.get("user_nick").toString().replace("\"", "");
            if (user.get("email") != null) _email = user.get("email").toString().replace("\"", "");
            if (user.get("ty_coin") != null) _balance = "Баланс: " + user.get("ty_coin").toString();


            if (!privilege.get("donate").isJsonNull()) _group = "Роль: " + privilege.get("donate").toString()
                    .replaceFirst("\"", "[").replaceFirst("\"", "]");

            if (!privilege.get("admin").toString().contains("null")) {
                _group = _group + " [" + privilege.get("admin").toString().replace("\"", "") + "]";
            }
            if (!privilege.get("end_time").toString().contains("null") && !privilege.get("privilegeAdmin").isJsonNull()) {
                _endDonateTime = privilege.get("end_time").toString();
                _group = _group + "\n\nИстекает: " + _endDonateTime.replace("\"", "");
            }
        }

        logger.logInfo("Информация об авторизации:", authManagerWeb.toString(), this.toString(), WebAnswer.PrintAnswer());


        if (!WebAnswer.getStatus()) {
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
        return "User{" + "_email='" + _email + '\'' + ", _id=" + _id + ", _password='sex(не увидишь ты пароля в логах, гений)" + '\'' + ", _login='" + _login + '\'' + ", _session='" + _session + '\'' + ", _balance='" + _balance + '\'' + ", _group='" + _group + '\'' + ", _image=" + _image.toString() + '}';
    }
}
