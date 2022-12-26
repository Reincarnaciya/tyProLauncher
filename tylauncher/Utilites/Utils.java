package tylauncher.Utilites;

import javafx.scene.Node;
import javafx.scene.control.Button;
import tylauncher.Utilites.Managers.ManagerWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String suffix = "[UTIL] ";
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
        String text = "";
        for (int i = 1; i < arr.length; i++) {
            int hexVal = Integer.parseInt(arr[i], 16);
            text += (char) hexVal;
        }
        return text;
    }

    public static void DeleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                DeleteFile(f);
            }
        }
        file.delete();
    }
    public static void openUrl(String url) throws IOException {
        Desktop.getDesktop().browse(URI.create(url));
    }
    public static String bytesTostring(String message){
        String mssage = "";
        message = message.replace(".", "");
        String[] msg = message.split(" ");
        for (int i = 0; i < msg.length; i++) {
            mssage += Utils.UniToText(msg[i]) + " ";
        }
        return mssage;
    }
}
