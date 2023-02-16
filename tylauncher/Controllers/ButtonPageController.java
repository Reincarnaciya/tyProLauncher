package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Logger;

import static tylauncher.Main.user;

public class ButtonPageController {
    private static final Logger logger = new Logger(ButtonPageController.class);
    private static int _pressedNum = 1;
    private int _buttonCount = 0;

    //private final ImageView[] buttonsFx = new ImageView[6];
    public void addButton(ImageView button) {
        //buttonsFx[_buttonCount++] = button;
        _buttonCount++;

        //buttonsFx[ButtonPage._amountButtons] = new ButtonPage(button);
        if (_pressedNum == _buttonCount) {
            switch (_buttonCount) {
                case 1:
                    button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/account.png"))));
                    break;
                case 2:
                    button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/news.png"))));
                    break;
                case 3:
                    button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/forum.png"))));
                    break;
                case 4:
                    button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/message.png"))));
                    break;
                case 5:
                    if (ManagerFlags.updateAvailable || ManagerFlags.lowDiskSpace)
                        button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/settings.png"))));
                    else
                        button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/settings.png"))));
                    break;
                case 6:
                    button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/play.png"))));
                    break;
            }

        } else { // КНОПКА НЕ НАЖАТА
            switch (_buttonCount) {
                case 1:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 1;
                        if (user.wasAuth) {
                            ManagerWindow.ACCOUNT.open();
                            user.wasAuth = false;
                        }
                        new Thread(() -> {
                            try {
                                if (user.auth()) {
                                    Platform.runLater(() -> ManagerWindow.ACCOUNT.open());
                                } else {
                                    user.wasAuth = false;
                                    ManagerWindow.ACCOUNT_AUTH.open();
                                }
                            } catch (Exception e) {
                                user.wasAuth = false;
                                logger.logError(e);
                                ManagerWindow.ACCOUNT_AUTH.open();
                            }
                        }).start();
                    });
                    break;
                case 2:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 2;
                        ManagerWindow.NEWS.open();
                    });
                    break;
                case 3:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 3;
                        ManagerWindow.FORUM.open();
                    });
                    break;
                case 4:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 4;
                        ManagerWindow.MESSAGE.open();

                    });
                    break;
                case 5:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 5;
                        ManagerWindow.SETTINGS.open();

                    });
                    if (ManagerFlags.updateAvailable || ManagerFlags.lowDiskSpace) {
                        button.setImage(new Image(String.valueOf(Main.class.getResource("assets/notpick/settingsUpdate.png"))));
                    }
                    break;
                case 6:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 6;
                        ManagerWindow.PLAY.open();
                    });
                    break;
            }
        }
    }


}
