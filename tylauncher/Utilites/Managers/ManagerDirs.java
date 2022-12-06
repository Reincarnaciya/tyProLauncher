package tylauncher.Utilites.Managers;

import tylauncher.Utilites.UserPC;

import java.io.File;

public class ManagerDirs {
    private static final String suffix = "[ManagerLauncherDir] ";

    private final File _workDir;

    public ManagerDirs(String nameDir) {
        String userHome = System.getProperty("user.home", ".");
        File workTempDir;
        switch (getPlatform().ordinal()) {
            case 0:
                String appdata = System.getenv("APPDATA");
                if (appdata != null) workTempDir = new File(appdata, "." + nameDir + "/");
                else workTempDir = new File(userHome, "." + nameDir + "/");
                break;
            case 1:
                workTempDir = new File(userHome, "." + nameDir + "/");
                break;
            case 2:
                workTempDir = new File(userHome, "Library/Application Support/" + nameDir);
                break;
            default:
                workTempDir = new File(userHome, nameDir + "/");
        }
        if ((!workTempDir.exists()) && (!workTempDir.mkdir()))
            throw new RuntimeException("Рабочая директория не определена(" + workTempDir + ")");

        _workDir = workTempDir;
    }
    public static OS getPlatform() {
        if (UserPC.getOS().contains("win")) return OS.windows;
        else if (UserPC.getOS().contains("linux") || UserPC.getOS().contains("unix")) return OS.linux;
        else if (UserPC.getOS().contains("macos")) return OS.macos;
        else return OS.unknow;
    }
    public File getWorkDir() {
        return _workDir;
    }

    public enum OS {
        windows,
        linux,
        macos,
        unknow
    }
}