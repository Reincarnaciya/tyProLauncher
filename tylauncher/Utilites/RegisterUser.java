package tylauncher.Utilites;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import tylauncher.Utilites.Managers.ManagerWeb;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.net.MalformedURLException;
import java.util.Arrays;

import static tylauncher.Utilites.Utils.searchMassChar;

public class RegisterUser {
    private static final String suffix = "[RegisterUser] ";
    private static final ManagerWeb registerManager = new ManagerWeb("Register");

    public static void RegUser(String username, String password, String repeat_password, String email) {
        WebAnswer.Reset();

        registerManager.setUrl("https://typro.space/vendor/launcher/register_launcher.php");
        registerManager.putAllParams(Arrays.asList("login", "password", "email", "repeat_password"),
                Arrays.asList(username, password, email, repeat_password));


        try {
            registerManager.request();
        } catch (Exception e) {
            ManagerWindow.currentController.setInfoText(e.getMessage());
            e.printStackTrace();
        }

        JsonObject object = (JsonObject) new JsonParser().parse(registerManager.getFullAnswer());

        if(!(object.get("type") == null)) WebAnswer.setType(object.get("type").toString());
        if(!(object.get("message") == null)) WebAnswer.setMessage(object.get("message").toString());
        if(!(object.get("status") == null))WebAnswer.setStatus(object.get("status").toString());
        if(!(object.get("fields") == null))WebAnswer.setFields(object.get("fields").toString());

        System.err.println("-----------------------------REG-INFO-----------------------------");
        System.err.println(registerManager);
        System.err.println(object);
        WebAnswer.PrintAnswer();
        System.err.println("-----------------------------REG-INFO-----------------------------");

    }

}




