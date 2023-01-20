package tylauncher.Utilites;

import tylauncher.Main;
import tylauncher.Managers.ManagerFlags;

import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;

public class UserPC {
    private static final float _ozu = (float) (
            (((com.sun.management.OperatingSystemMXBean)
                    ManagementFactory.getOperatingSystemMXBean())
                    .getTotalPhysicalMemorySize()) / 1048576);
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
    public static final int _usableDiskSpace = (int) (new File(File.separator).getUsableSpace()/1048576);
    public static final String _javaBit = System.getProperty("sun.arch.data.model");
    public static String _pathToLauncher = null;

    static {
        try {
            _pathToLauncher = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            _pathToLauncher = new StringBuilder(_pathToLauncher).deleteCharAt(0).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static {
        if(UserPC._usableDiskSpace < 1500) ManagerFlags.lowDiskSpace = true;
    }

    public static final String _javaFXVersion = com.sun.javafx.runtime.VersionInfo.getVersion();

    public static void Show() {
        System.err.println("----------------USER PC----------------");
        System.err.println("RAM: " + _ozu);
        System.err.println("Available Disk Space: " + _usableDiskSpace+ "Mb");
        System.err.println("OS: " + _os);
        System.err.println("Resolution: " + _width + "x" + _height);
        System.err.println("Java Version: " + _javaVersion);
        System.err.println("JavaFX Version: " + _javaFXVersion);
        System.err.println("Bit Java: " + _javaBit);
        System.err.println("PathToLauncher: " + _pathToLauncher);
        System.err.println("----------------USER PC----------------");
    }

    public static float getOzu(){
        if(_javaBit.equalsIgnoreCase("32") && _ozu > 4096.0) return 4096;
        return _ozu;
    }

}
