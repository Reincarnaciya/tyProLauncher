package tylauncher.Utilites;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tylauncher.Main;

import java.util.Arrays;

public class BooleanPageController {
    private static ButtonPage[] buttonsFx = new ButtonPage[6];
    private static boolean upd = false;
    public static void addButton(ImageView button){
        buttonsFx[ButtonPage._amountButtons] = new ButtonPage(button);
    }

    public static void UPDATE(){
        buttonsFx[4].getButtonFX().setImage((new Image(String.valueOf(Main.class.getResource("assets/picked/play.png")))));

        upd = true;
    }
}
