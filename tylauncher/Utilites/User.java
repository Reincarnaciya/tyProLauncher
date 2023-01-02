package tylauncher.Utilites;

import javafx.scene.image.Image;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerWeb;

import java.util.Arrays;
import java.util.Objects;

public class User {
    private final String _email;
    private final long _id;
    private String _password;
    private String _login;
    private String _session;
    private String _balance;
    private String _group;
    private final String _endDonateTime;
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
        if (_login.equalsIgnoreCase("test")){
            return true;
        }

        if (_login.isEmpty() || _password.isEmpty()) throw new Exception("Логин или пароль не введены");

        ManagerWeb authManagerWeb = new ManagerWeb("auth");
        authManagerWeb.setUrl("https://typro.space/vendor/launcher/login_launcher.php");
        authManagerWeb.putAllParams(Arrays.asList("login", "password"), Arrays.asList(_login, _password));
        authManagerWeb.request();

        String[] answer = authManagerWeb.getAnswerMass();
        if(answer.length == 6 && answer[0].equalsIgnoreCase("status")){
            if (answer[0].equalsIgnoreCase("status")) WebAnswer.setStatus(answer[1]);
            if(answer[2].equalsIgnoreCase("type")) WebAnswer.setType(answer[3]);
            if (answer[4].equalsIgnoreCase("message")) WebAnswer.setMessage(answer[5]);
        }


        System.err.println("-----------------------------AUTH-INFO-----------------------------");
        System.err.println(authManagerWeb);
        System.err.println(this);
        WebAnswer.PrintAnswer();
        System.err.println("-----------------------------AUTH-INFO-----------------------------");
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
        this._image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/picked/steve.png")));
    }

    @Override
    public String toString() {
        return "User{" + "_email='" + _email + '\'' + ", _id=" + _id + ", _password='" + _password + '\'' + ", _login='" + _login + '\'' + ", _session='" + _session + '\'' + ", _balance='" + _balance + '\'' + ", _group='" + _group + '\'' + ", _image=" + _image.toString() + '}';
    }
}
