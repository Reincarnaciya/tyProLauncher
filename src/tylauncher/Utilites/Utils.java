package tylauncher.Utilites;
import javafx.scene.text.Text;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {
    private static final  String suffix = "[UTIL] ";
    //Поиск элемента в массиве
    public static int searchMassChar(String [] array, String whatSearch){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(whatSearch)) {
                return i;
            }
        }
        return -1;
    }
    //unicode символы в понятные буковки
    public static String UniToText(String message) {
        if(!message.contains("\\")){
            return message;
        }
        String str = message.split(" ")[0];
        str = str.replace("\\","");
        String[] arr = str.split("u");

        String text = "";
        for(int i = 1; i < arr.length; i++){
            int hexVal = Integer.parseInt(arr[i], 16);
            text += (char)hexVal;
        }
        return text;
    }
}
