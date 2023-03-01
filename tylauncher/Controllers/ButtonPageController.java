package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.Images;
import tylauncher.Utilites.Logger;

import static tylauncher.Main.user;

public class ButtonPageController {

    private static final Logger logger = new Logger(ButtonPageController.class);
    private static int _pressedNum = 1;
    private int _buttonCount = 0;
    private final ImageView[] currentButtons = new ImageView[6];

    public static ButtonPageController currentButtonPageController;
    //private final ImageView[] buttonsFx = new ImageView[6];
    public void addButton(ImageView button) {
        currentButtonPageController = this;

        currentButtons[_buttonCount] = button;
        _buttonCount++;

        //buttonsFx[ButtonPage._amountButtons] = new ButtonPage(button);
        if (_pressedNum == _buttonCount) {
            switch (_buttonCount) {
                case 1:
                    button.setImage(Images.ACCOUNT_BUTTON_PICKED);
                    break;
                case 2:
                    button.setImage(Images.NEWS_BUTTON_PICKED);
                    break;
                case 3:
                    button.setImage(Images.FORUM_BUTTON_PICKED);
                    break;
                case 4:
                    button.setImage(Images.MESSAGE_BUTTON_PICKED);
                    break;
                case 5:
                    if (ManagerFlags.updateAvailable || ManagerFlags.lowDiskSpace)
                        button.setImage(Images.SETTINGS_BUTTON_INFO);
                    else
                        button.setImage(Images.SETTINGS_BUTTON_PICKED);
                    break;
                case 6:
                    if (!user.wasAuth) button.setImage(Images.PLAY_BUTTON_BLOCKED);
                    else button.setImage(Images.PLAY_BUTTON_PICKED);
                    break;
            }

        } else { // КНОПКА НЕ НАЖАТА
            switch (_buttonCount) {
                case 1:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 1;
                        if (user.wasAuth) {
                            ManagerWindow.ACCOUNT.open();
                        }else {
                            ManagerWindow.ACCOUNT_AUTH.open();
                        }
                        new Thread(() -> {
                            try {
                                if (user.auth()) {
                                    ManagerWindow.ACCOUNT.open();
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
                        button.setImage(Images.SETTINGS_BUTTON_INFO);
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
    public ImageView getButton(int button){
        return currentButtons[button];
    }
}
