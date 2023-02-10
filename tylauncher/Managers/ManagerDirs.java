package tylauncher.Managers;

import tylauncher.Utilites.UserPC;

import java.io.File;

public class ManagerDirs {
    private File _workDir;

    @Override
    public String toString() {
        return "ManagerDirs{" +
                "_workDir=" + _workDir +
                '}';
    }

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
        if (UserPC._os.contains("win")) return OS.windows;
        else if (UserPC._os.contains("linux") || UserPC._os.contains("unix")) return OS.linux;
        else if (UserPC._os.contains("macos")) return OS.macos;
        else return OS.unknown;
    }
    public File getWorkDir() {
        return _workDir;
    }
    public void setWorkDir(File f){
        this._workDir = f;
    }

    public enum OS {
        windows, linux, macos, unknown
    }
}