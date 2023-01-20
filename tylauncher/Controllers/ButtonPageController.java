package tylauncher.Controllers;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Sound;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;

public class ButtonPageController {
    private int _buttonCount = 0;
    private static int _pressedNum = 1;
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
                    if (ManagerFlags.updateAvailable || ManagerFlags.lowDiskSpace) button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/settings.png"))));
                    else button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/settings.png"))));
                    break;
                case 6:
                    button.setImage(new Image(String.valueOf(Main.class.getResource("assets/picked/play.png"))));
                    break;
            }

        } else { // КНОПКА НЕ НАЖАТА
            switch (_buttonCount) {
                case 1:
                    button.setOnMouseClicked(event -> {
                        Sound.playSound(Sound.CLICK);
                        _pressedNum = 1;
                        if(user.wasAuth){
                            ManagerWindow.OpenNew("Account.fxml", ManagerWindow.currentController.getA1());
                            accountController.UpdateData();
                        }
                        new Thread(()->{
                            try {
                                if (user.auth()) {
                                    Platform.runLater(()->{
                                        ManagerWindow.OpenNew("Account.fxml", ManagerWindow.currentController.getA1());
                                        accountController.UpdateData();
                                    });
                                }else {
                                    Platform.runLater(()-> ManagerWindow.OpenNew("AccountAuth.fxml", ManagerWindow.currentController.getA1()));
                                }
                            } catch (Exception e) {
                                System.err.println("Except when click \"Account\": " + e.getMessage());
                                Platform.runLater(()-> ManagerWindow.OpenNew("AccountAuth.fxml", ManagerWindow.currentController.getA1()));
                            }
                        }).start();
                    });
                    break;
                case 2:
                    button.setOnMouseClicked(event -> {
                        Sound.playSound(Sound.CLICK);
                        _pressedNum = 2;
                        ManagerWindow.OpenNew("News.fxml", ManagerWindow.currentController.getA1());
                    });
                    break;
                case 3:
                    button.setOnMouseClicked(event -> {
                        Sound.playSound(Sound.CLICK);
                        _pressedNum = 3;
                        ManagerWindow.OpenNew("Forum.fxml", ManagerWindow.currentController.getA1());
                    });
                    break;
                case 4:
                    button.setOnMouseClicked(event -> {
                        Sound.playSound(Sound.CLICK);
                        _pressedNum = 4;
                        ManagerWindow.OpenNew("Message.fxml", ManagerWindow.currentController.getA1());

                    });
                    break;
                case 5:
                    button.setOnMouseClicked(event -> {
                        Sound.playSound(Sound.CLICK);
                        _pressedNum = 5;
                        ManagerWindow.OpenNew("Settings.fxml", ManagerWindow.currentController.getA1());

                    });
                    if (ManagerFlags.updateAvailable || ManagerFlags.lowDiskSpace) {
                        button.setImage(new Image(String.valueOf(Main.class.getResource("assets/notpick/settingsUpdate.png"))));
                    }
                    break;
                case 6:
                    button.setOnMouseClicked(event -> {
                        Sound.playSound(Sound.CLICK);
                        _pressedNum = 6;
                        ManagerWindow.OpenNew("Play.fxml", ManagerWindow.currentController.getA1());
                    });
                    break;
            }
        }
    }



}
