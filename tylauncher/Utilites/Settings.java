package tylauncher.Utilites;

public class Settings {
    private int _ozu = Math.round(UserPC._ozu / 1536) * 512;
    private int _x = 800;
    private int _y = 600;
    private boolean _fsc = false;

    public int getOzu() {
        return _ozu;
    }

    public void setOzu(int ozu) throws Exception {
        if(UserPC._javaBit.equalsIgnoreCase("32")){
            if(ozu > 4096){
                throw new Exception("На 32-х битной версии джавы лучше не ставить больше 4 гб оперативы, иначе вашему ПК пизда");
            }
        }
        if (Math.round((float) ozu / 512) * 512 > UserPC._ozu) {
            this._ozu = Math.round(UserPC._ozu / 1536) * 512;
            throw new Exception("Попытка выставить ОЗУ больше, чем имеешь на ПК может вызвать к синему экрану\nP.s Я проверяла");
        }
        this._ozu = Math.round((float) ozu / 512) * 512;
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) throws Exception {
        if (x > UserPC._width) {
            this._x = 800;
            throw new Exception("Попытка выставить х больше, чем разрешение вашего экрана");
        } else this._x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) throws Exception {
        if (y > UserPC._height) {
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
