package tylauncher.Utilites.Constants;

import tylauncher.Utilites.Logger;

import java.net.MalformedURLException;
import java.net.URL;

public class URLS {
    public static final URL AUTH_USER;
    public static final URL REG_USER;
    public static final URL DONATE;
    public static final URL CLIENT_TY_SCI_WIN;
    public static final URL CLIENT_TY_SCI_LINUX;
    public static final URL CLIENT_TY_SCI_MACOS;
    public static final URL USER_AGREEMENT;
    public static final URL USER_HASHS;
    public static final URL CLIENT_HASH;
    public static final URL JAVA_HASH;
    public static final URL CHECK_LAUCHER_VERS;
    public static final URL UPDATER_LAUNCHER_DOWNLOAD;
    public static final URL ROFL_URL;
    public static final URL MAIN_SITE_PAGE;
    private static final Logger logger = new Logger(URLS.class);

    static {
        try {
            AUTH_USER = new URL("https://typro.space/vendor/login/signin.php");
            REG_USER = new URL("https://typro.space/vendor/login/signup.php");
            DONATE = new URL("https://typro.space/markup/donate.php");
            CLIENT_TY_SCI_WIN = new URL("https://www.typro.space/files/client_mc/client1165.zip");
            USER_AGREEMENT = new URL("https://typro.space//markup/regulations/UserAgreement.php");
            USER_HASHS = new URL("https://typro.space/vendor/launcher/login_get_hash_launcher.php");
            CLIENT_HASH = new URL("https://typro.space/vendor/server/check_hash_client.php");
            JAVA_HASH = new URL("https://typro.space/vendor/server/check_java_hash.php");
            CHECK_LAUCHER_VERS = new URL("https://typro.space/vendor/launcher/CheckingVersion.php");
            UPDATER_LAUNCHER_DOWNLOAD = new URL("https://www.typro.space/files/Update/TyUpdaterLauncher.jar");
            CLIENT_TY_SCI_LINUX = new URL("https://google.com"); // TODO: 15.02.2023 ADD LINKS
            CLIENT_TY_SCI_MACOS = new URL("https://google.com");
            ROFL_URL = new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            MAIN_SITE_PAGE = new URL("https://typro.space");
        } catch (MalformedURLException e) {
            logger.logError(e);
            throw new RuntimeException();
        }
    }
}
