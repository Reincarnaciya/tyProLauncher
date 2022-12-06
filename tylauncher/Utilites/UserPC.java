package tylauncher.Utilites;

import java.awt.*;
import java.lang.management.ManagementFactory;

public class UserPC {
    private static final int _ozu = (int) (
            (((com.sun.management.OperatingSystemMXBean)
                    ManagementFactory.getOperatingSystemMXBean())
                    .getTotalPhysicalMemorySize()) / 1048576); // может лучше всеже оставить float чтобы лишьний раз не конвертировать?
    private static final int _width = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDisplayMode()
            .getWidth();
    private static final int _height = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDisplayMode()
            .getHeight();
    private static final String _os = System.getProperty("os.name").toLowerCase();

    public static void Show() {
        System.err.println("----------------USER PC----------------");
        System.err.println("RAM: " + _ozu);
        System.err.println("OS: " + _os);
        System.err.println("Resolution: ");
        System.err.println("width: " + _width);
        System.err.println("height: " + _height);
        System.err.println("----------------USER PC----------------");
    }

    public static int getOzu() {
        return _ozu;
    }
    public static int getWidth() {
        return _width;
    }
    public static int getHeight() { return _height; }
    public static String getOS() {
        return _os;
    }
}
