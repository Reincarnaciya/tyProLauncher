package tylauncher.Utilites.MenuManager;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import tylauncher.Main;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.Dirs;
import tylauncher.Utilites.Constants.Images;
import tylauncher.Utilites.Utils;

import java.io.File;
import java.util.Optional;


public class Menus {
    public static Menu TySciSettings = new Menu();

    static {
        TySciSettings.addMenuItem("Удалить клиент");
        TySciSettings.addMenuItem("Переустановить клиент");

        TySciSettings.addMenuItem("В РАЗРАБОТКЕ");
        TySciSettings.confirm();


        TySciSettings.getMenuItem(0).setOnAction(Menus::delete);
        TySciSettings.getMenuItem(1).setOnAction(Menus::redown);
    }

    private static void redown(ActionEvent event) {
        delete(event);
        ManagerWindow.playController.setInfoText("Начинаю переустановку");
        System.err.println("НАЧИНАЮ ПЕРЕУСТАНОВКУ");
        new Thread(()->{
            Thread download = new Thread(ManagerWindow.playController.downloadTySciClient());
            download.start();
            try {
                download.join();
            }catch (InterruptedException ignore){}
            ManagerWindow.playController.unsetText();
        }).start();
    }


    static Runnable deleteClient(String client){
        return () -> {
            ManagerWindow.playController.setInfoText("Удаление..");
            ManagerWindow.playController.UdpateProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
            if (!new File(Main.getClientDir().getAbsolutePath() + File.separator + client).exists()) return;
            Utils.DeleteFile(new File(Main.getClientDir().getAbsolutePath() + File.separator + client).toPath().toFile());
        };
    }


    private static void delete(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("УВЕРЕН!?!?!?!");

        // Создание кнопок
        ButtonType buttonTypeOk = new ButtonType("YEEEES", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);


        dialog.getDialogPane().getStylesheets().add(Main.class.getResource("StyleSheets/font.css").toExternalForm());
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(Images.ICON_DEFAULT);

        // Добавление кнопок в диалоговое окно
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);
        // Отображение диалогового окна и ожидание выбора кнопки
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeOk) {
            Runnable runnable = deleteClient(Dirs.TYSCI);
            Thread delThread = new Thread(runnable);
            delThread.start();

            new Thread(() -> {
                try {
                    delThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.err.println("Delete complete");
                ManagerWindow.playController.unsetText();
            }).start();
        }
    }
}
