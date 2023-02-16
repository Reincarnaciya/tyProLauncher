package tylauncher.Utilites;

import java.io.PrintStream;

public class DualStream extends PrintStream {
    private static final Logger logger = new Logger(DualStream.class);
    final PrintStream out;

    public DualStream(PrintStream out1, PrintStream out2) {
        super(out1);
        this.out = out2;
    }

    public void write(byte[] buf, int off, int len) {
        try {
            super.write(buf, off, len);
            out.write(buf, off, len);
        } catch (Exception e) {
            logger.logError(e);
        }
    }

    public void flush() {
        super.flush();
        out.flush();
    }
}
