package tylauncher.Utilites;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tylauncher.Controllers.UpdaterController;
import tylauncher.Main;

public class ButtonPage {
    private ImageView _buttonFx;
    private int _buttonNum;
    private static int _pressedNum;
    protected static int _amountButtons = 0;

    public static void setPressedNum(int pressedNum){
        _pressedNum = pressedNum;
    }


    public ButtonPage(ImageView buttonFx){
        _buttonNum = ++_amountButtons;
        if(_pressedNum == _buttonNum){
            switch (_buttonNum){
                case 1:
                    buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/account.png"))));
                    break;
                case 2:
                    buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/news.png"))));
                    break;
                case 3:
                    buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/forum.png"))));
                    break;
                case 4:
                    buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/message.png"))));
                    break;
                case 5:
                    if (UpdaterController.updateAvailable){
                        buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/settings.png"))));
                        break;
                    }
                    buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/settings.png"))));
                    break;
                case 6:
                    buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/play.png"))));
                    break;
            }

        }else {
            switch (_buttonNum){
                case 5:
                    if (UpdaterController.updateAvailable){
                        buttonFx.setImage(new Image(String.valueOf(Main.class.getResource("assets/notpick/settingsUpdate.png"))));
                    }
                    break;
            }
        }
    }
    public ImageView getButtonFX(){
        return _buttonFx;
    }
    public static void reset(){
        _amountButtons = 0;
    }


}

