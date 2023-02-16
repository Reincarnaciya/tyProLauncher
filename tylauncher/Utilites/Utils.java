package tylauncher.Utilites;

import tylauncher.Main;
import tylauncher.Managers.ManagerWindow;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Utils {
    private static final Logger logger = new Logger(Utils.class);

    public static void setAllPermissions(Path path) {
        // Набор прав, которые нужно выдать
        Set<PosixFilePermission> permissions = new HashSet<>();

        permissions.add(PosixFilePermission.OWNER_READ);
        permissions.add(PosixFilePermission.OWNER_WRITE);
        permissions.add(PosixFilePermission.OWNER_EXECUTE);

        permissions.add(PosixFilePermission.OTHERS_EXECUTE);
        permissions.add(PosixFilePermission.OTHERS_READ);
        permissions.add(PosixFilePermission.OTHERS_WRITE);

        permissions.add(PosixFilePermission.GROUP_EXECUTE);
        permissions.add(PosixFilePermission.GROUP_READ);
        permissions.add(PosixFilePermission.GROUP_WRITE);

        // Устанавливаем права на папку
        try {
            setPermissionsRecursive(path, permissions);
        } catch (Exception e) {
            logger.logError(e);
        }
    }

    private static void setPermissionsRecursive(Path path, Set<PosixFilePermission> permissions) throws Exception {
        // Устанавливаем права на текущую папку
        Files.setPosixFilePermissions(path, permissions);

        // Рекурсивно устанавливаем права на внутренние файлы и папки
        Files.list(path).forEach(child -> {
            try {
                if (Files.isDirectory(child)) {
                    setPermissionsRecursive(child, permissions);
                } else {
                    Files.setPosixFilePermissions(child, permissions);
                }
            } catch (Exception e) {
                logger.logError(e);
            }
        });
    }

    //unicode символы в понятные буковки
    public static String UniToText(String message) {
        if (!message.contains("\\")) return message;

        String str = message.split(" ")[0];
        str = str.replace("\\", "");
        String[] arr = str.split("u");
        StringBuilder text = new StringBuilder();
        for (int i = 1; i < arr.length; i++) {
            int hexVal = Integer.parseInt(arr[i], 16);
            text.append((char) hexVal);
        }
        return text.toString();
    }

    public static void DeleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                DeleteFile(f);
            }
        }
        try {
            Files.delete(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            logger.logError(e, ManagerWindow.currentController);
        }
    }

    public static void openUrl(URL url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // не поддерживаются ссылки формата "leodev.html#someTag"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url); // если windows, открываем урлу через командную строку
            } else if (os.contains("mac")) {
                rt.exec("open " + url); // аналогично в MAC
            } else if (os.contains("nix") || os.contains("nux")) {
                // c nix системами все несколько проблемотичнее
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx"};
                // Формируем строку с вызовом всем браузеров через логическое ИЛИ в shell консоли
                // "browser0 "URI" || browser1 "URI" ||..."
                StringBuilder cmd = new StringBuilder();
                for (int i = 0; i < browsers.length; i++)
                    cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static String bytesToString(String message) {
        StringBuilder mssage = new StringBuilder();
        message = message.replace(".", "");
        String[] msg = message.split(" ");
        for (String s : msg) {
            mssage.append(Utils.UniToText(s)).append(" ");
        }
        return mssage.toString();
    }

    public static void CheckLogs() {
        long numDays = 4;   //Оно должно быть лонг, честно, иначе чет всё ломается
        String dir = Main.getLauncherDir() + File.separator + "logs";
        File directory = new File(dir);
        File[] fList = directory.listFiles();

        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    long diff = new Date().getTime() - file.lastModified();
                    long cutoff = (numDays * 24 * 60 * 60 * 1000);

                    if (diff > cutoff) {
                        if (file.delete()) System.err.println("Удален старый лог-файл: " + file);
                    }
                }
            }
        }
    }


    public static void easter() {
        System.err.println("\n\n\n");
        System.err.println("                   .`\":l><~<!;,^'.                                .'^,;l><>iI:\"`.                   \n" +
                "              `I{u&88&&88888888888#/+\".                      .\"<\\*88888888&&&&&&&&n};`              \n" +
                "          .,(W&&&&&&&&&&&&&&&&&&&&&&888u~`                '>x888&&&&&&&&&&&&&&&&&&&&&&M(,.          \n" +
                "        `1W&&&&&&&&&&&&&&&&&&&&&&&&&&&&&88vI.           ;n88&&&&&&&&&&&&&&&&&&&&&&&&&&&&&W1`        \n" +
                "      `f&&&&&&&&&WWWWWWWWWWWWWWWW&&&&&&&&&&8M>        l#8&&&&&&&&&WWWWWWWWWW&&&&&&&&&&&&&&&&j`      \n" +
                "    .}&&&WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&&8z^    `v8&&&&&&WWWWWWWWWWWWWWWWWWWW&&&&&&&&&&&&&{.    \n" +
                "   'uWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&8>  l&&&&&&WWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&&&&&&&v'   \n" +
                "  'zWWWWWWWWWWWWWWWWWWWWMMMMWWWWWWWWWWWWWWWW&&&&8}?8&&&WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW&&&&&&&&&&*'  \n" +
                "  nWWWWWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&WWWWWWWWWMMMMMMMMMMMWWWWWWWWWWWWW&&&&&&&&&Wu  \n" +
                " IWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&WWWWWWWMMMMMMMMMMMMMMMMMMWWWWWWWWWWW&&&&&&&&&Wl \n" +
                " xMMWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&WWWWWMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWWW&&&&&&&&Wn \n" +
                " MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&&W \n" +
                " #MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&M \n" +
                " tMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&j \n" +
                " ;MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWW&&&&&&&&&I \n" +
                " .cMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&*. \n" +
                "  ,MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWW&&&&&&&&8,  \n" +
                "   -MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWW&&&&&&&88?   \n" +
                "    }MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWW&&&&&&&881    \n" +
                "     ?MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&&88[     \n" +
                "      ;MWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&&8&l      \n" +
                "       `vWWWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&&8c`       \n" +
                "        .]&&&WWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWW&&&&&&88[.        \n" +
                "          ^u&&&&WWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&&8u`          \n" +
                "            lM&&&&WWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&8Ml            \n" +
                "             .?&&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&&-.             \n" +
                "               '{&&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&&}'               \n" +
                "                 '1&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&{'                 \n" +
                "                   '{&&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&&['                   \n" +
                "                     '?W&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWW&&&&M-.                     \n" +
                "                       .~#&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&&&*>.                       \n" +
                "                         .Iz&&&WWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&&&v;.                         \n" +
                "                            ,n&&&&WWWWMMMMMMMMMMMMMMMMMMMMMMMWWWWW&&&&r,                            \n" +
                "                              ^f&&&&WWWWMMMMMMMMMMMMMMMMMMMWWWWW&&&&/^                              \n" +
                "                                `(&&&&WWWWWMMMMMMMMMMMMMMWWWWW&&&&1`                                \n" +
                "                                  '{&&&&WWWWWMMMMMMMMMMWWWWW&&&W]'                                  \n" +
                "                                    '[&&&&WWWWWMMMMMMWWWWW&&&W-.                                    \n" +
                "                                      '}&&&&WWWWMMMMWWWW&&&&?.                                      \n" +
                "                                        '|&&&WWWWWWWWW&&&&{.                                        \n" +
                "                                          `n&&&WWWWWW&&&f`                                          \n" +
                "                                            IW&&&WW&&&#,                                            \n" +
                "                                             'f&&&&&&(.                                             \n" +
                "                                               ~&&&&;                                               \n" +
                "                                                ,&M^                                                \n" +
                "                                                 ^`                                                 \n");
        System.err.println("\n" +
                "██     ██ ███████     ████████  ██████   ██████      ██       ██████  ██    ██ ███████     ██    ██  ██████  ██    ██     \n" +
                "██     ██ ██             ██    ██    ██ ██    ██     ██      ██    ██ ██    ██ ██           ██  ██  ██    ██ ██    ██     \n" +
                "██  █  ██ █████          ██    ██    ██ ██    ██     ██      ██    ██ ██    ██ █████         ████   ██    ██ ██    ██     \n" +
                "██ ███ ██ ██             ██    ██    ██ ██    ██     ██      ██    ██  ██  ██  ██             ██    ██    ██ ██    ██     \n" +
                " ███ ███  ███████        ██     ██████   ██████      ███████  ██████    ████   ███████        ██     ██████   ██████      \n" +
                "                                                                                                                          \n" +
                "                                                                                                                          \n");
        System.err.println("Now open the launcher normally :)");
    }

}
