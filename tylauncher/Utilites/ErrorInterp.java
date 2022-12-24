package tylauncher.Utilites;

import tylauncher.Utilites.Managers.ManagerWindow;

public class ErrorInterp {
    //переписать всю эту поеботу из многооконности в другую поеботу, с которой ебанины будет меньше
    public static void setMessageError(String error) {
        ManagerWindow.currentController.setInfoText(error);
    }
}
