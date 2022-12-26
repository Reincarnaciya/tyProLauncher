package tylauncher.Utilites;

import tylauncher.Utilites.Managers.ManagerWeb;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static tylauncher.Utilites.Utils.searchMassChar;

public class RegisterUser {
    private static final String suffix = "[RegisterUser] ";
    private static final ManagerWeb registerManager = new ManagerWeb("POST");

    public static void RegUser(String username, String password, String repeat_password, String email) {
        WebAnswer.Reset();
        try {
            registerManager.setUrl("https://typro.space/vendor/launcher/register_launcher.php");
        }catch (MalformedURLException e) {
            ManagerWindow.currentController.setInfoText("Если вы видите эту ошибку, значит что-то серьезно пошло не так. Скиньте логи администрации.");
            e.printStackTrace();
        }

        registerManager.putParam("login", username);
        registerManager.putParam("password", password);
        registerManager.putParam("email", email);
        registerManager.putParam("repeat_password", repeat_password);

        try {
            registerManager.request();
        } catch (Exception e) {
            ManagerWindow.currentController.setInfoText(e.toString());
            e.printStackTrace();
        }



        String line = registerManager.getAnswer();

        String[] end_reg = line.split("[,\\-:]");
        if (line.contains("fields")) {
            WebAnswer.setStatus(end_reg[searchMassChar(end_reg, "status") + 1]);
            WebAnswer.setType(end_reg[searchMassChar(end_reg, "type") + 1]);
            WebAnswer.setMessage(end_reg[searchMassChar(end_reg, "message") + 1]);
            String temp = "";
            if ((end_reg.length - searchMassChar(end_reg, "fields") - 1) > 1) {
                for (int i = 0; i < (end_reg.length - searchMassChar(end_reg, "fields") - 1); i++) {
                    temp = temp + " " + (end_reg[searchMassChar(end_reg, "fields") + 1 + i]);
                }
                WebAnswer.setFields(temp);
            } else {
                WebAnswer.setFields(end_reg[searchMassChar(end_reg, "fields") + 1]);
            }
        } else {
            WebAnswer.setStatus(end_reg[searchMassChar(end_reg, "status") + 1]);
            WebAnswer.setMessage(end_reg[searchMassChar(end_reg, "message") + 1]);
        }
        WebAnswer.PrintAnswer();
    }

}




