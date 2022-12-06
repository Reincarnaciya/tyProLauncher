package tylauncher.Utilites;

public class Settings {
    private int _ozu = Math.round((float) UserPC.getOzu() / 1536) * 512;
    private int _x = 800;
    private int _y = 600;
    private boolean _fsc = false;

    public int getOzu() {
        return _ozu;
    }

    public void setOzu(int ozu) throws Exception {
        if (Math.round((float) ozu / 512) * 512 > UserPC.getOzu()) {
            if((Math.floor((float) ozu / 512) * 512) <= UserPC.getOzu()){
                this._ozu = (int)Math.floor((float) ozu / 512) * 512;
                return;
            }
            this._ozu = Math.round((float) UserPC.getOzu() / 1536) * 512;
            throw new Exception("Попытка выставить ОЗУ больше, чем имеешь на ПК может вызвать к синему экрану\nP.s Я проверяла");
        }
        this._ozu = Math.round((float) ozu / 512) * 512;
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) throws Exception {
        if (x > UserPC.getWidth()) {
            this._x = 800;
            throw new Exception("Попытка выставить х больше, чем разрешение вашего экрана");
        } else this._x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) throws Exception {
        if (y > UserPC.getWidth()) {
            this._y = 600;
            throw new Exception("Попытка выставить y больше, чем разрешение вашего экрана");
        } else this._y = y;
    }

    public boolean getFsc() {
        return _fsc;
    }

    public void setFsc(boolean fsc) {
        this._fsc = fsc;
    }

    @Override
    public String toString() {
        return "Settings{" + "ozu=" + _ozu + ", x=" + _x + ", y=" + _y + ", fsc=" + _fsc + '}';
    }
}
