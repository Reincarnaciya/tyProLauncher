package tylauncher.Utilites;

import static tylauncher.Utilites.Utils.bytesTostring;

public class WebAnswer {
    private static boolean status = false;
    private static int type = -1;
    private static String message = null;
    private static String fields = null;

    public static boolean getStatus() {
        return status;
    }
    public static void setStatus(String status) {
        WebAnswer.status = Boolean.parseBoolean(status);
    }
    public static int getType() {
        return type;
    }
    public static void setType(String type) {
        WebAnswer.type = Integer.parseInt(type);
    }
    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        WebAnswer.message = bytesTostring(message);
    }
    public static void Reset() {
        WebAnswer.status = false;
        WebAnswer.type = -1;
        WebAnswer.message = null;
        WebAnswer.fields = null;
    }

    public static String getFields() {
        return fields;
    }

    public static void setFields(String fields) {
        WebAnswer.fields = fields;
    }

    public static void PrintAnswer() {
        System.err.println("WebAnswer{" + "status='" + status  + "', type='" + type + "', message='" + message  + "', fields='" + fields + "'}");
    }

}