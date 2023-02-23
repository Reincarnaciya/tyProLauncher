package tylauncher.Utilites;

import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;

import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;

public class UserPC {
    public static final int _width = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDisplayMode()
            .getWidth();
    public static final int _height = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDisplayMode()
            .getHeight();
    public static final String _os = System.getProperty("os.name").toLowerCase();
    public static final String _javaVersion = System.getProperty("java.version");
    public static final int _usableDiskSpace = (int) (new File(File.separator).getUsableSpace() / 1048576);
    public static final String _javaBit = System.getProperty("sun.arch.data.model");
    public static final String _javaFXVersion = com.sun.javafx.runtime.VersionInfo.getVersion();
    private static final Logger logger = new Logger(UserPC.class);
    private static final float _ozu = (float) (
            (((com.sun.management.OperatingSystemMXBean)
                    ManagementFactory.getOperatingSystemMXBean())
                    .getTotalPhysicalMemorySize()) / 1048576);
    public static String _pathToLauncher = null;

    static {
        try {
            _pathToLauncher = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            _pathToLauncher = new StringBuilder(_pathToLauncher).deleteCharAt(0).toString();
        } catch (URISyntaxException e) {
            logger.logError(e);
        }
        if (UserPC._usableDiskSpace < 1500) {
            logger.logInfo("Обнаружено малое кол-во места на диске");
            ManagerFlags.lowDiskSpace = true;
        }
    }

    public static String getPCinfo() {
        return String.format("UserPc{RAM=%s;%nAvailable Disk Space=%s;%nOS=%s;%nResolution=%sx%s;%nJava Versions=%s;%nJavaFX Versions=%s;%nBit Java=%s;%nPath To Launcher=%s}",
                _ozu, _usableDiskSpace, _os, _width, _height, _javaVersion, _javaFXVersion, _javaBit, _pathToLauncher);
    }

    public static float getOzu() {
        if (_javaBit.equalsIgnoreCase("32")) return 1536;
        return _ozu;
    }

}
