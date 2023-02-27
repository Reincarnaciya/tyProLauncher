package tylauncher.Utilites.Constants;

import javafx.scene.image.Image;
import tylauncher.Main;

public class Images {

    private static final String assets = "assets/";
    private static final String NOTPICKED = assets + "notpick/";
    private static final String PICKED = assets + "picked/";

    //GLOBAL IMAGES
    public static final Image PLAY_BUTTON_BLOCKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "play_blocked.png")));
    public static final Image SETTINGS_BUTTON_INFO = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "settingsUpdate.png")));
    public static final Image STANDART_SKIN_STEVE = new Image(String.valueOf(Main.class.getResource(PICKED + "steve.png")));
    public static final Image HELL_TYMYSUNYA = new Image(String.valueOf(Main.class.getResource(assets + "HellTyMasunya.png")));
    public static final Image ICON_DEFAULT = new Image(String.valueOf(Main.class.getResource(assets + "ico.png")));
    public static final Image ICON_NEW_YEAR = new Image(String.valueOf(Main.class.getResource(assets + "icoNewYear.png")));
    public static final Image TYMASUNYA = new Image(String.valueOf(Main.class.getResource(assets + "TyMasunya.png")));

    //SERVERS IMAGES
    public static final Image TY_SCI = new Image(String.valueOf(Main.class.getResource(assets + "TySci.png")));

    //BUTTONS ON LEFT PAImage PICKED
    public static final Image ACCOUNT_BUTTON_NOTPICKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "account.png")));
    public static final Image FORUM_BUTTON_NOTPICKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "forum.png")));
    public static final Image MESSAGE_BUTTON_NOTPICKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "message.png")));
    public static final Image NEWS_BUTTON_NOTPICKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "news.png")));
    public static final Image PLAY_BUTTON_NOTPICKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "play.png")));
    public static final Image SETTINGS_BUTTON_NOTPICKED = new Image(String.valueOf(Main.class.getResource(NOTPICKED + "settings.png")));

    //BUTTONS ON LEFT PAImageKED
    public static final Image ACCOUNT_BUTTON_PICKED = new Image(String.valueOf(Main.class.getResource(PICKED + "account.png")));
    public static final Image FORUM_BUTTON_PICKED = new Image(String.valueOf(Main.class.getResource(PICKED + "forum.png")));
    public static final Image MESSAGE_BUTTON_PICKED = new Image(String.valueOf(Main.class.getResource(PICKED + "message.png")));
    public static final Image NEWS_BUTTON_PICKED = new Image(String.valueOf(Main.class.getResource(PICKED + "news.png")));
    public static final Image PLAY_BUTTON_PICKED = new Image(String.valueOf(Main.class.getResource(PICKED + "play.png")));
    public static final Image SETTINGS_BUTTON_PICKED = new Image(String.valueOf(Main.class.getResource(PICKED + "settings.png")));
}
