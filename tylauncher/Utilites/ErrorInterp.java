package tylauncher.Utilites;

import tylauncher.Controllers.*;

public class ErrorInterp {
    public static AccountAuthController accountAuthController;
    public static RegisterController registerController;
    public static ErrorController errorController;
    public static SettingsController settingsController;
    public static PlayController playController;

    //переписать всю эту поеботу из многооконности в другую поеботу, с которой ебанины будет меньше
    public static void setMessageError(String error, String where) {
        if (where.equalsIgnoreCase("accountauth")) {
            accountAuthController.setInfoText(error);
        } else if (where.equalsIgnoreCase("register")) {
            registerController.setInfoText(error);
        } else if (where.equalsIgnoreCase("settings")) {
            settingsController.setInfoText(error);
        } else if (where.equalsIgnoreCase("play")) {
            playController.setTextOfDownload(error);
        }

    }
}
