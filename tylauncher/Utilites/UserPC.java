package tylauncher.Utilites;

import java.awt.GraphicsEnvironment;
import java.lang.management.ManagementFactory;

public class UserPC {
    public static final float _ozu = (float) (
            (((com.sun.management.OperatingSystemMXBean)
                    ManagementFactory.getOperatingSystemMXBean())
                    .getTotalPhysicalMemorySize()) / 1048576); // может лучше всеже оставить float чтобы лишьний раз не конвертировать?
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
    public static final String _javaBit = System.getProperty("sun.arch.data.model");

    public static void Show() {
        System.err.println("----------------USER PC----------------");
        System.err.println("RAM: " + _ozu);
        System.err.println("OS: " + _os);
        System.err.println("Resolution: " + _width + "x" + _height);
        System.err.println("Java Version: " + _javaVersion);
        System.err.println("Bit Java: " + _javaBit);
        System.err.println("----------------USER PC----------------");
    }
}
