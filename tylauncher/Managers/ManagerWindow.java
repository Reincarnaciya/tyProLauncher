package tylauncher.Managers;

import tylauncher.Controllers.BaseController;
import tylauncher.Utilites.Constants.FXMLS;
import tylauncher.Utilites.Constants.Titles;
import tylauncher.Utilites.Window;

public class ManagerWindow {

    public static BaseController currentController;


    public static Window ACCOUNT;
    public static Window ACCOUNT_AUTH;
    public static Window FORUM;
    public static Window MESSAGE;
    public static Window NEWS;
    public static Window PLAY;
    public static Window REGISTER;
    public static Window RUNTIME_DOWNLOAD;
    public static Window SETTINGS;
    public static Window UPDATER;

    public static void initWindows() {
        ACCOUNT = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.ACCOUNT);
        ACCOUNT_AUTH = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.ACCOUNT_AUTH);
        FORUM = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.FORUM);
        MESSAGE = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.MESSAGE);
        NEWS = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.NEWS);
        PLAY = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.PLAY);
        REGISTER = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.REGISTER);
        RUNTIME_DOWNLOAD = new Window(350, 140, Titles.BASE_LAUNCHER_TITLE, FXMLS.RUNTIME_DOWNLOAD);
        SETTINGS = new Window(800, 500, Titles.BASE_LAUNCHER_TITLE, FXMLS.SETTINGS);
        UPDATER = new Window(350, 100, Titles.BASE_LAUNCHER_TITLE, FXMLS.UPDATER);
    }


}
