package tylauncher.Utilites;

public class Settings {
    private static int _ozu = Math.round(UserPC.getOzu() / 1536) * 512;
    private static int _x = 925;
    private static int _y = 530;
    private static boolean _fsc = false;
    private static boolean hide = false;
    private static boolean autoConnect = false;
    public static void setHide(boolean bool){
        hide = bool;
    }
    public static boolean getHide(){
        return hide;
    }

    public static int getOzu() {
        return _ozu;
    }

    public static void setOzu(int ozu) throws Exception {
        if (Math.round((float) ozu / 512) * 512 > UserPC.getOzu()) {
            _ozu = Math.round(UserPC.getOzu() / 1536) * 512;
            throw new Exception("Попытка выставить ОЗУ больше, чем имеешь на ПК может вызвать к синему экрану\nP.s Я проверяла");
        }
        if (Math.round((float) ozu / 512) * 512 < 1024) {
            _ozu = Math.round(UserPC.getOzu() / 1536) * 512;
            throw new Exception("Попытка выставить ОЗУ меньше, чем 1024 может вызвать к синему экрану\nP.s Я проверяла");
        }
        _ozu = Math.round((float) ozu / 512) * 512;
    }

    public static int getX() {
        return _x;
    }

    public static void setX(int x) throws Exception {
        if (x > UserPC._width || x == 0) {
            _x = 925;
            throw new Exception("Попытка выставить х больше(или 0), чем разрешение вашего экрана");
        } else _x = x;
    }

    public static int getY() {
        return _y;
    }

    public static void setY(int y) throws Exception {
        if (y > UserPC._height || y == 1) {
            _y = 530;
            throw new Exception("Попытка выставить y больше(или 0), чем разрешение вашего экрана");
        } else _y = y;
    }

    public static void reset(){
        _ozu = Math.round(UserPC.getOzu() / 1536) * 512;
        _x = 925;
        _y = 530;
        _fsc = false;
        hide = false;
        autoConnect = false;
    }
    public static int get_ozu() {
        return _ozu;
    }

    public static void set_ozu(int _ozu) {
        Settings._ozu = _ozu;
    }

    public static boolean is_fsc() {
        return _fsc;
    }

    public static void set_fsc(boolean _fsc) {
        Settings._fsc = _fsc;
    }

    public static boolean isHide() {
        return hide;
    }

    public static boolean isAutoConnect() {
        return autoConnect;
    }

    public static void setAutoConnect(boolean autoConnect) {
        Settings.autoConnect = autoConnect;
    }

    public static boolean getFsc() {
        return _fsc;
    }

    public static void setFsc(boolean fsc) {
        _fsc = fsc;
    }

    public static String show() {
        return "Settings{" + "ozu=" + _ozu + ", x=" + _x + ", y=" + _y + ", fsc=" + _fsc + ", hide=" + hide + '}';
    }


}
