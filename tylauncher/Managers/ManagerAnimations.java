package tylauncher.Managers;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ManagerAnimations {
    //Спизженный код, который работает. НЕ ТРОГАТЬ
    //UPD я разобрался как оно работает
    public static FadeTransition CreateFader(Node node) {

        FadeTransition fade = new FadeTransition(Duration.seconds(5), node);

        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }
    private static FadeTransition waitAnim(Node node){

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(1);

        return fadeTransition;
    }

    public static void StartFadeAnim(Node whatToFade) {
        FadeTransition fader = ManagerAnimations.CreateFader(whatToFade);
        SequentialTransition Fade = new SequentialTransition(whatToFade, fader);

        FadeTransition f = waitAnim(whatToFade);
        f.setOnFinished(event ->  Fade.play());
        f.play();
    }
}
