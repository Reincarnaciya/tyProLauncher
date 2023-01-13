package tylauncher.Utilites;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import tylauncher.Main;

import java.net.URL;

public class Sound {
    public static final String CLICK = "click.mp3";
    public static final String SUCCESS_CLICK = "successful_hit.mp3";
    public static final String UNSUCCESSFUL_OPERATION = "pop.mp3";


    public static void playSound(String sound){
        new Thread(()->{
            URL file = Main.class.getResource(String.format("assets/sounds/%s", sound));
            Media media = new Media(file.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }).start();
    }

}
