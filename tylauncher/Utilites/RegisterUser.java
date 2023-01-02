package tylauncher.Utilites;

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
        System.err.println("-----------------------------REG-INFO-----------------------------");
        System.err.println(registerManager);
        WebAnswer.PrintAnswer();
        System.err.println("-----------------------------REG-INFO-----------------------------");

    }

}




