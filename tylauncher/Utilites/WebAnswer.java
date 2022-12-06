package tylauncher.Utilites;

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
        String mssage = "";
        message = message.replace(".", "");
        String[] msg = message.split(" ");
        for (int i = 0; i < msg.length; i++) {
            mssage += Utils.UniToText(msg[i]) + " ";
        }
        WebAnswer.message = mssage;
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
        System.out.println("WebAnswer{\n" + "status: " + status + "\n"
                + "type: " + type + "\n"
                + "message: " + message + "\n"
                + "fields: " + fields + "\n}");
    }

}
