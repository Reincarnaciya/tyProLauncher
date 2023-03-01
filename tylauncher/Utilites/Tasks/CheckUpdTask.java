package tylauncher.Utilites.Tasks;

import javafx.application.Platform;
import tylauncher.Controllers.ButtonPageController;
import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;
import tylauncher.Managers.ManagerWeb;
import tylauncher.Managers.ManagerWindow;
import tylauncher.Utilites.Constants.ButtonPageButtons;
import tylauncher.Utilites.Constants.Images;
import tylauncher.Utilites.Constants.Titles;
import tylauncher.Utilites.Constants.URLS;
import tylauncher.Utilites.Window;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CheckUpdTask implements Runnable{
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public CheckUpdTask(){
        executorService.scheduleAtFixedRate(this, 10, 10, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        System.err.println("RUN AUTO-CHECK-UPDATE");
        ManagerWeb updateTaskWeb = new ManagerWeb("updTask");
        //"https://typro.space/vendor/launcher/CheckingVersion.php"
        updateTaskWeb.setUrl(URLS.CHECK_LAUCHER_VERS);
        updateTaskWeb.putAllParams(Arrays.asList("hash", "version"), Arrays.asList("rehtrjtkykyjhtjhjotrjhoitrjoihjoith", Main.launcher_version));
        try {
            updateTaskWeb.request();
        } catch (Exception ignore) {} // TODO: 27.02.2023 Catch obrabotat' 

        String line = updateTaskWeb.getFullAnswer();
        if (line.equalsIgnoreCase("1")) {
            Platform.runLater(()-> ButtonPageController.currentButtonPageController.getButton(ButtonPageButtons.SETTINGS).setImage(Images.SETTINGS_BUTTON_INFO));
            ManagerFlags.updateAvailable = true;
        }
    }
}
