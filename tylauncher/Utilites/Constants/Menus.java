package tylauncher.Utilites.Constants;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tylauncher.Main;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.MenuManager.Menu;
import tylauncher.Utilites.Utils;
import tylauncher.Utilites.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Menus {
    public static Menu TySciSettings = new Menu();

    static {
        TySciSettings.addMenuItem("В РАЗРАБОТКЕ");
        /*
        TySciSettings.addMenuItem("Переустановить клиент");
        TySciSettings.addMenuItem("Добавить свой мод");
        TySciSettings.confirm();


        TySciSettings.getMenuItem(0).setOnAction(event ->{
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

                try {

                    delThread.join(1000);
                    System.err.println("Delete complete");
                    ManagerWindow.playController.unsetText();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        });

         */
    }

/*
    static Runnable deleteClient(String client){
        return () -> {
            ManagerWindow.playController.setInfoText("Удаление..");
            ManagerWindow.playController.UdpateProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
            if (!new File(Main.getClientDir().getAbsolutePath() + File.separator + client).exists()) return;
            Utils.DeleteFile(new File(Main.getClientDir().getAbsolutePath() + File.separator + client).toPath().toFile());
        };
    }
 */


}
