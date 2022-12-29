package tylauncher.Utilites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerFlags;
import tylauncher.Utilites.Managers.ManagerWindow;

import static tylauncher.Controllers.AccountAuthController.accountController;
import static tylauncher.Main.user;

public class ButtonPageController {
    private ImageView _buttonFx;
    private int _buttonCount = 0;
    private static int _pressedNum = 1;
    protected int _amountButtons = 0;
    private ImageView[] buttonsFx = new ImageView[6];
    public void addButton(ImageView button) {
        buttonsFx[_buttonCount++] = button;

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
                        _pressedNum = 1;
                        try {
                            if (user.Auth()) {
                                System.err.println("userAuth");
                                Main.OpenNew("Account.fxml", ManagerWindow.currentController.getA1());
                                accountController.UpdateData();
                            } else {
                                System.err.println("userNotAuth");
                                Main.OpenNew("AccountAuth.fxml", ManagerWindow.currentController.getA1());
                            }
                        } catch (Exception e) {
                            System.err.println("ExceptUserAuth: " + e.getMessage());
                            Main.OpenNew("AccountAuth.fxml", ManagerWindow.currentController.getA1());
                        }
                    });
                    break;
                case 2:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 2;
                        Main.OpenNew("News.fxml", ManagerWindow.currentController.getA1());
                    });
                    break;
                case 3:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 3;
                        Main.OpenNew("Forum.fxml", ManagerWindow.currentController.getA1());
                    });
                    break;
                case 4:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 4;
                        Main.OpenNew("Message.fxml", ManagerWindow.currentController.getA1());

                    });
                    break;
                case 5:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 5;
                        Main.OpenNew("Settings.fxml", ManagerWindow.currentController.getA1());

                    });
                    if (ManagerFlags.updateAvailable || ManagerFlags.lowDiskSpace) {
                        button.setImage(new Image(String.valueOf(Main.class.getResource("assets/notpick/settingsUpdate.png"))));
                    }
                    break;
                case 6:
                    button.setOnMouseClicked(event -> {
                        _pressedNum = 6;
                        Main.OpenNew("Play.fxml", ManagerWindow.currentController.getA1());
                    });
                    break;
            }
        }
    }

}
