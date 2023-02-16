package tylauncher.Utilites;

import tylauncher.Controllers.BaseController;

import java.util.Locale;

public class Logger {

    public static final char upperLineSuffix = '-';

    public static final char bottomLineSuffix = '=';


    private final String className;

    public Logger(Class<?> cl) {
        this.className = cl.getName().toUpperCase(Locale.ROOT);
    }

    //===============================================INFO===============================================

    public void logInfo(String whatToLog) {
        String suffix = getSuffix(upperLineSuffix, whatToLog);
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        System.out.println(whatToLog);
        suffix = getSuffix(bottomLineSuffix, whatToLog);
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
    }

    public void logInfo(Exception whatToLog) {
        String suffix = getSuffix(upperLineSuffix, whatToLog.getMessage());
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        System.out.println(whatToLog.getMessage());
        System.out.println("\n" + suffix + "\n");
        whatToLog.printStackTrace();
        suffix = getSuffix(bottomLineSuffix, whatToLog.getMessage());
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
    }

    public void logInfo(String whatToLog, BaseController baseController) {
        String suffix = getSuffix(upperLineSuffix, whatToLog);
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        System.out.println(whatToLog);
        suffix = getSuffix(bottomLineSuffix, whatToLog);
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        baseController.setInfoText(whatToLog);
    }

    public void logInfo(Exception whatToLog, BaseController baseController) {
        String suffix = getSuffix(upperLineSuffix, whatToLog.getMessage());
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        System.out.println(whatToLog.getMessage());
        System.out.println("\n" + suffix + "\n");
        whatToLog.printStackTrace();
        suffix = getSuffix(bottomLineSuffix, whatToLog.getMessage());
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        baseController.setInfoText(whatToLog.getMessage());
    }

    public void logInfo(String... whatToLog) {
        String suffix = getSuffix(upperLineSuffix, whatToLog);
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
        for (String s : whatToLog) System.out.println(s);
        suffix = getSuffix(bottomLineSuffix, whatToLog);
        System.out.printf("%n%sINFO[%s]%s%n", suffix, this.className, suffix);
    }
    //===============================================ERRORS===============================================

    public void logError(String whatToLog) {
        String suffix = getSuffix(upperLineSuffix, whatToLog);
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        System.err.println(whatToLog);
        suffix = getSuffix(bottomLineSuffix, whatToLog);
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
    }

    public void logError(Exception whatToLog) {
        String suffix = getSuffix(upperLineSuffix, whatToLog.getMessage());
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        System.err.println(whatToLog.getMessage());
        System.out.println("\n" + suffix + "\n");
        whatToLog.printStackTrace();
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
    }

    public void logError(String whatToLog, BaseController baseController) {
        String suffix = getSuffix(upperLineSuffix, whatToLog);
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        System.err.println(whatToLog);
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        baseController.setInfoText(whatToLog);
    }

    public void logError(Exception whatToLog, BaseController baseController) {
        String suffix = getSuffix(upperLineSuffix, whatToLog.getMessage());
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        System.err.println(whatToLog.getMessage());
        System.out.println("\n" + suffix + "\n");
        whatToLog.printStackTrace();
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        baseController.setInfoText(whatToLog.getMessage());
    }

    public void logError(String... whatToLog) {
        String suffix = getSuffix(upperLineSuffix, whatToLog);
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
        for (String s : whatToLog) System.err.println(s);
        System.err.printf("%n%sERROR[%s]%s%n", suffix, this.className, suffix);
    }


    public String getSuffix(char c, String... s) {
        int biggest = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String s1 : s) {
            for (String t : s1.split("\n")) {
                if (t.length() > biggest) biggest = t.length();
            }
        }
        for (int i = 0; i < (biggest / 2); i++) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }
}
