package tylauncher.Utilites;

import tylauncher.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Objects;

public class Utils {
    //Поиск элемента в массиве
    public static int searchMassChar(String[] array, String whatSearch) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(whatSearch)) {
                return i;
            }
        }
        return -1;
    }
    //unicode символы в понятные буковки
    public static String UniToText(String message) {
        if (!message.contains("\\")) {
            return message;
        }
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
        file.delete();
    }
    public static void openUrl(String url) throws IOException {
        Desktop.getDesktop().browse(URI.create(url));
    }
    public static String bytesToString(String message){
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

    public static void easter(){
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
