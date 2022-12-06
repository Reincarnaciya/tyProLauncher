package tylauncher.Utilites.Managers;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ManagerAnimations {
    //Спизженный код, который работает. НЕ ТРОГАТЬ
    //UPD я разобрался как оно работает
    public static FadeTransition CreateFader(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(4), node);
        fade.setFromValue(1);
        fade.setToValue(0);
        return fade;
    }
    public static void StartFadeAnim(Node whatToFade) {
        FadeTransition fader = ManagerAnimations.CreateFader(whatToFade);

        SequentialTransition Fade = new SequentialTransition(
                whatToFade,
                fader
        );

        Fade.play();
    }
}
