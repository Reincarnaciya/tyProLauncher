package tylauncher.Utilites.Managers;

import javafx.application.Platform;
import tylauncher.Controllers.PlayController;
import tylauncher.Main;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.UserPC;

import java.io.File;
import java.io.InputStream;

import static tylauncher.Main.user;

public class ManagerStart {
    private static final String suffix = "[ManagerStart] ";
    public static boolean gameIsStart = false;
    public static PlayController playController;

    public static void StartMinecraft(String version) {
        System.err.println(Settings.show());


        // if (user.getSession().isEmpty() || user.getSession().equals("")) {
        //    throw new Exception("Проблема с сессией, обратитесь в тех. поддержку");
        //}
        Platform.runLater(()-> ManagerWindow.currentController.setInfoText("Игра запущена"));
        if (Settings.getHide()) Platform.runLater(()-> Main.mainStage.setIconified(true));

        new Thread(() -> {
            try {
                Runtime runtime = Runtime.getRuntime();
                try {

                    String fsc = "";
                    String x;
                    String y;
                    System.err.println(Settings.show());
                    if (Settings.getFsc()) {
                        fsc = "--fullscreen";
                        x = "";
                        y = "";
                    }else {
                        x = " --width " + Settings.getX();
                        y = " --height " + Settings.getY();
                    }

                    Process p1 = null;
                    System.out.println(suffix + "sess - " + user.getSession());
                    if(UserPC._os.contains("linux") || UserPC._os.contains("unix")){
                        System.err.println("linux(");
                    }else if(UserPC._os.contains("win")){
                        p1 = runtime.exec(
                                Main.getClientDir().toString() + File.separator + version + "\\runtime\\jre-legacy\\windows\\jre-legacy\\bin\\javaw.exe"
                                + " -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -Xss1M "
                                + "-Djava.library.path=" + Main.getClientDir().toString() + File.separator + version + "\\versions\\Forge1.16.5\\natives -Dminecraft.launcher.brand=minecraft-launcher -Dminecraft.launcher.version=2.3.173 -cp "
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\forge\\1.16.5-36.2.39\\forge-1.16.5-36.2.39.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\ow2\\asm\\asm\\9.1\\asm-9.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\ow2\\asm\\asm-commons\\9.1\\asm-commons-9.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\ow2\\asm\\asm-tree\\9.1\\asm-tree-9.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\ow2\\asm\\asm-util\\9.1\\asm-util-9.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\ow2\\asm\\asm-analysis\\9.1\\asm-analysis-9.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\cpw\\mods\\modlauncher\\8.1.3\\modlauncher-8.1.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\cpw\\mods\\grossjava9hacks\\1.3.3\\grossjava9hacks-1.3.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\accesstransformers\\3.0.1\\accesstransformers-3.0.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\antlr\\antlr4-runtime\\4.9.1\\antlr4-runtime-4.9.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\eventbus\\4.0.0\\eventbus-4.0.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\forgespi\\3.2.0\\forgespi-3.2.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\coremods\\4.0.6\\coremods-4.0.6.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\unsafe\\0.2.0\\unsafe-0.2.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\electronwill\\night-config\\core\\3.6.3\\core-3.6.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\electronwill\\night-config\\toml\\3.6.3\\toml-3.6.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\jline\\jline\\3.12.1\\jline-3.12.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\maven\\maven-artifact\\3.6.3\\maven-artifact-3.6.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\jodah\\typetools\\0.8.3\\typetools-0.8.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.15.0\\log4j-api-2.15.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.15.0\\log4j-core-2.15.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-slf4j18-impl\\2.15.0\\log4j-slf4j18-impl-2.15.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecrell\\terminalconsoleappender\\1.2.0\\terminalconsoleappender-1.2.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.4\\jopt-simple-5.0.4.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\spongepowered\\mixin\\0.8.4\\mixin-0.8.4.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\minecraftforge\\nashorn-core-compat\\15.1.1.1\\nashorn-core-compat-15.1.1.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\tlauncher\\patchy\\1.3.9\\patchy-1.3.9.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\oshi-project\\oshi-core\\1.1\\oshi-core-1.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\java\\dev\\jna\\jna\\4.4.0\\jna-4.4.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\java\\dev\\jna\\platform\\3.4.0\\platform-3.4.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\ibm\\icu\\icu4j\\66.1\\icu4j-66.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\mojang\\javabridge\\1.0.22\\javabridge-1.0.22.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.3\\jopt-simple-5.0.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\io\\netty\\netty-all\\4.1.25.Final\\netty-all-4.1.25.Final.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\google\\guava\\guava\\21.0\\guava-21.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\commons\\commons-lang3\\3.5\\commons-lang3-3.5.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\commons-io\\commons-io\\2.5\\commons-io-2.5.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\commons-codec\\commons-codec\\1.10\\commons-codec-1.10.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\mojang\\brigadier\\1.0.17\\brigadier-1.0.17.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\mojang\\datafixerupper\\4.0.26\\datafixerupper-4.0.26.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\google\\code\\gson\\gson\\2.8.0\\gson-2.8.0.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\tlauncher\\authlib\\2.0.28.12\\authlib-2.0.28.12.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\commons\\commons-compress\\1.8.1\\commons-compress-1.8.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\httpcomponents\\httpclient\\4.3.3\\httpclient-4.3.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\commons-logging\\commons-logging\\1.1.3\\commons-logging-1.1.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\httpcomponents\\httpcore\\4.3.2\\httpcore-4.3.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\it\\unimi\\dsi\\fastutil\\8.2.1\\fastutil-8.2.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.8.1\\log4j-api-2.8.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.8.1\\log4j-core-2.8.1.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl\\3.2.2\\lwjgl-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.2.2\\lwjgl-jemalloc-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl-openal\\3.2.2\\lwjgl-openal-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.2.2\\lwjgl-opengl-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.2.2\\lwjgl-glfw-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl-stb\\3.2.2\\lwjgl-stb-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.2.2\\lwjgl-tinyfd-3.2.2.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\libraries\\com\\mojang\\text2speech\\1.11.3\\text2speech-1.11.3.jar;"
                                + Main.getClientDir().toString() + File.separator + version + "\\versions\\Forge1.16.5\\Forge1.16.5.jar"
                                + " -XX:+IgnoreUnrecognizedVMOptions"
                                + " --add-exports=java.base/sun.security.util=ALL-UNNAMED"
                                + " --add-exports=jdk.naming.dns/com.sun.jndi.dns=java.naming"
                                + " --add-opens=java.base/java.util.jar=ALL-UNNAMED"
                                + " -Xmx" + Settings.getOzu() + "M "
                                + "-XX:+UnlockExperimentalVMOptions "
                                + "-XX:+UseG1GC "
                                + "-XX:G1NewSizePercent=20 "
                                + "-XX:G1ReservePercent=20 "
                                + "-XX:MaxGCPauseMillis=50 "
                                + "-XX:G1HeapRegionSize=32M "
                                + "-Dfml.ignoreInvalidMinecraftCertificates=true "
                                + "-Dfml.ignorePatchDiscrepancies=true "
                                + "-Djava.net.preferIPv4Stack=true "
                                + "-Dminecraft.applet.TargetDirectory=" + Main.getClientDir().toString() + File.separator + version + ""
                                + " -Dlog4j.configurationFile=" + Main.getClientDir().toString() + File.separator + version + "\\assets\\log_configs\\client-1.12.xml"
                                + " cpw.mods.modlauncher.Launcher "
                                + "--username " + user.GetLogin()
                                + " --version Forge 1.16.5 "
                                + "--gameDir " + Main.getClientDir().toString() + File.separator + version + ""
                                + " --assetsDir " + Main.getClientDir().toString() + File.separator + version + "\\assets "
                                + "--assetIndex 1.16 "
                                + "--uuid 313a7d63a4326905cbace067a7c84a71 "
                                + "--accessToken 123g12h3g12hg31h2g3h12gh321g312g "
                                + "--userType mojang "
                                + "--versionType modified"
                                + x
                                + y
                                + " --launchTarget fmlclient "
                                + "--fml.forgeVersion 36.2.39 "
                                + "--fml.mcVersion 1.16.5 "
                                + "--fml.forgeGroup net.minecraftforge "
                                + "--fml.mcpVersion 20210115.111550 "
                                + fsc);
                    }
                    // System.out.println(suffix + Main.getClientDir().toString()+ File.separator +version + "\\runtime\\jre-legacy\\windows\\jre-legacy\\bin\\java.exe -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -Xss1M -Djava.library.path="+Main.getClientDir().toString()+ File.separator +version + "\\versions\\Forge1.16.5\\natives -Dminecraft.launcher.brand=minecraft-launcher -Dminecraft.launcher.version=2.3.173 -cp "+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\forge\\1.16.5-36.2.35\\forge-1.16.5-36.2.35.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\ow2\\asm\\asm\\9.1\\asm-9.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\ow2\\asm\\asm-commons\\9.1\\asm-commons-9.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\ow2\\asm\\asm-tree\\9.1\\asm-tree-9.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\ow2\\asm\\asm-util\\9.1\\asm-util-9.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\ow2\\asm\\asm-analysis\\9.1\\asm-analysis-9.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\cpw\\mods\\modlauncher\\8.1.3\\modlauncher-8.1.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\cpw\\mods\\grossjava9hacks\\1.3.3\\grossjava9hacks-1.3.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\accesstransformers\\3.0.1\\accesstransformers-3.0.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\antlr\\antlr4-runtime\\4.9.1\\antlr4-runtime-4.9.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\eventbus\\4.0.0\\eventbus-4.0.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\forgespi\\3.2.0\\forgespi-3.2.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\coremods\\4.0.6\\coremods-4.0.6.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\unsafe\\0.2.0\\unsafe-0.2.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\electronwill\\night-config\\core\\3.6.3\\core-3.6.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\electronwill\\night-config\\toml\\3.6.3\\toml-3.6.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\jline\\jline\\3.12.1\\jline-3.12.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\maven\\maven-artifact\\3.6.3\\maven-artifact-3.6.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\jodah\\typetools\\0.8.3\\typetools-0.8.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.15.0\\log4j-api-2.15.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.15.0\\log4j-core-2.15.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-slf4j18-impl\\2.15.0\\log4j-slf4j18-impl-2.15.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecrell\\terminalconsoleappender\\1.2.0\\terminalconsoleappender-1.2.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.4\\jopt-simple-5.0.4.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\spongepowered\\mixin\\0.8.4\\mixin-0.8.4.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\minecraftforge\\nashorn-core-compat\\15.1.1.1\\nashorn-core-compat-15.1.1.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\tlauncher\\patchy\\1.3.9\\patchy-1.3.9.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\oshi-project\\oshi-core\\1.1\\oshi-core-1.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\java\\dev\\jna\\jna\\4.4.0\\jna-4.4.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\java\\dev\\jna\\platform\\3.4.0\\platform-3.4.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\ibm\\icu\\icu4j\\66.1\\icu4j-66.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\mojang\\javabridge\\1.0.22\\javabridge-1.0.22.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.3\\jopt-simple-5.0.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\io\\netty\\netty-all\\4.1.25.Final\\netty-all-4.1.25.Final.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\google\\guava\\guava\\21.0\\guava-21.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\commons\\commons-lang3\\3.5\\commons-lang3-3.5.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\commons-io\\commons-io\\2.5\\commons-io-2.5.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\commons-codec\\commons-codec\\1.10\\commons-codec-1.10.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\mojang\\brigadier\\1.0.17\\brigadier-1.0.17.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\mojang\\datafixerupper\\4.0.26\\datafixerupper-4.0.26.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\google\\code\\gson\\gson\\2.8.0\\gson-2.8.0.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\tlauncher\\authlib\\2.0.28.12\\authlib-2.0.28.12.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\commons\\commons-compress\\1.8.1\\commons-compress-1.8.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\httpcomponents\\httpclient\\4.3.3\\httpclient-4.3.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\commons-logging\\commons-logging\\1.1.3\\commons-logging-1.1.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\httpcomponents\\httpcore\\4.3.2\\httpcore-4.3.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\it\\unimi\\dsi\\fastutil\\8.2.1\\fastutil-8.2.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.8.1\\log4j-api-2.8.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.8.1\\log4j-core-2.8.1.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl\\3.2.2\\lwjgl-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.2.2\\lwjgl-jemalloc-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl-openal\\3.2.2\\lwjgl-openal-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.2.2\\lwjgl-opengl-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.2.2\\lwjgl-glfw-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl-stb\\3.2.2\\lwjgl-stb-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.2.2\\lwjgl-tinyfd-3.2.2.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\libraries\\com\\mojang\\text2speech\\1.11.3\\text2speech-1.11.3.jar;"+Main.getClientDir().toString()+ File.separator +version + "\\versions\\Forge1.16.5\\Forge1.16.5.jar -XX:+IgnoreUnrecognizedVMOptions --add-exports=java.base/sun.security.util=ALL-UNNAMED --add-exports=jdk.naming.dns/com.sun.jndi.dns=java.naming --add-opens=java.base/java.util.jar=ALL-UNNAMED -Xmx"+SettingsController.settings.getOzu() +"M -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Djava.net.preferIPv4Stack=true -Dminecraft.applet.TargetDirectory="+Main.getClientDir().toString()+ File.separator +version + " -Dlog4j.configurationFile="+Main.getClientDir().toString()+ File.separator +version + "\\assets\\log_configs\\client-1.12.xml cpw.mods.modlauncher.Launcher --username "+user.GetLogin()+" --version Forge 1.16.5 --gameDir "+Main.getClientDir().toString()+ File.separator +version + " --assetsDir "+Main.getClientDir().toString()+ File.separator +version + "\\assets --assetIndex 1.16 --session "+user.getSession()+" --accessToken null --userType mojang --versionType modified" +
                    //         x + y + " --launchTarget fmlclient --fml.forgeVersion 36.2.35 --fml.mcVersion 1.16.5 --fml.forgeGroup net.minecraftforge --fml.mcpVersion 20210115.111550" + fsc
                    // );
                    //System.err.println(Main.getClientDir().toString() + File.separator + version +"\\runtime\\jre-legacy\\windows\\jre-legacy\\bin\\javaw.exe -Dos.name="+UserPC.getOS()+" -Dos.version=10.0 -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -Xss1M -Djava.library.path="+Main.getClientDir().toString() + File.separator + version +"\\versions\\Forge1.16.5\\natives -Dminecraft.launcher.brand=minecraft-launcher -Dminecraft.launcher.version=2.3.173 -cp "+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\forge\\1.16.5-36.2.39\\forge-1.16.5-36.2.39.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\ow2\\asm\\asm\\9.1\\asm-9.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\ow2\\asm\\asm-commons\\9.1\\asm-commons-9.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\ow2\\asm\\asm-tree\\9.1\\asm-tree-9.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\ow2\\asm\\asm-util\\9.1\\asm-util-9.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\ow2\\asm\\asm-analysis\\9.1\\asm-analysis-9.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\cpw\\mods\\modlauncher\\8.1.3\\modlauncher-8.1.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\cpw\\mods\\grossjava9hacks\\1.3.3\\grossjava9hacks-1.3.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\accesstransformers\\3.0.1\\accesstransformers-3.0.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\antlr\\antlr4-runtime\\4.9.1\\antlr4-runtime-4.9.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\eventbus\\4.0.0\\eventbus-4.0.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\forgespi\\3.2.0\\forgespi-3.2.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\coremods\\4.0.6\\coremods-4.0.6.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\unsafe\\0.2.0\\unsafe-0.2.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\electronwill\\night-config\\core\\3.6.3\\core-3.6.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\electronwill\\night-config\\toml\\3.6.3\\toml-3.6.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\jline\\jline\\3.12.1\\jline-3.12.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\maven\\maven-artifact\\3.6.3\\maven-artifact-3.6.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\jodah\\typetools\\0.8.3\\typetools-0.8.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.15.0\\log4j-api-2.15.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.15.0\\log4j-core-2.15.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\logging\\log4j\\log4j-slf4j18-impl\\2.15.0\\log4j-slf4j18-impl-2.15.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecrell\\terminalconsoleappender\\1.2.0\\terminalconsoleappender-1.2.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.4\\jopt-simple-5.0.4.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\spongepowered\\mixin\\0.8.4\\mixin-0.8.4.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\minecraftforge\\nashorn-core-compat\\15.1.1.1\\nashorn-core-compat-15.1.1.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\tlauncher\\patchy\\1.3.9\\patchy-1.3.9.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\oshi-project\\oshi-core\\1.1\\oshi-core-1.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\java\\dev\\jna\\jna\\4.4.0\\jna-4.4.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\java\\dev\\jna\\platform\\3.4.0\\platform-3.4.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\ibm\\icu\\icu4j\\66.1\\icu4j-66.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\mojang\\javabridge\\1.0.22\\javabridge-1.0.22.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.3\\jopt-simple-5.0.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\io\\netty\\netty-all\\4.1.25.Final\\netty-all-4.1.25.Final.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\google\\guava\\guava\\21.0\\guava-21.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\commons\\commons-lang3\\3.5\\commons-lang3-3.5.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\commons-io\\commons-io\\2.5\\commons-io-2.5.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\commons-codec\\commons-codec\\1.10\\commons-codec-1.10.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\mojang\\brigadier\\1.0.17\\brigadier-1.0.17.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\mojang\\datafixerupper\\4.0.26\\datafixerupper-4.0.26.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\google\\code\\gson\\gson\\2.8.0\\gson-2.8.0.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\tlauncher\\authlib\\2.0.28.12\\authlib-2.0.28.12.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\commons\\commons-compress\\1.8.1\\commons-compress-1.8.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\httpcomponents\\httpclient\\4.3.3\\httpclient-4.3.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\commons-logging\\commons-logging\\1.1.3\\commons-logging-1.1.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\httpcomponents\\httpcore\\4.3.2\\httpcore-4.3.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\it\\unimi\\dsi\\fastutil\\8.2.1\\fastutil-8.2.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.8.1\\log4j-api-2.8.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.8.1\\log4j-core-2.8.1.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl\\3.2.2\\lwjgl-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.2.2\\lwjgl-jemalloc-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl-openal\\3.2.2\\lwjgl-openal-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.2.2\\lwjgl-opengl-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.2.2\\lwjgl-glfw-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl-stb\\3.2.2\\lwjgl-stb-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.2.2\\lwjgl-tinyfd-3.2.2.jar;"+Main.getClientDir().toString() + File.separator + version +"\\libraries\\com\\mojang\\text2speech\\1.11.3\\text2speech-1.11.3.jar;"+Main.getClientDir().toString() + File.separator + version +"\\versions\\Forge1.16.5\\Forge1.16.5.jar -XX:+IgnoreUnrecognizedVMOptions --add-exports=java.base/sun.security.util=ALL-UNNAMED --add-exports=jdk.naming.dns/com.sun.jndi.dns=java.naming --add-opens=java.base/java.util.jar=ALL-UNNAMED -Xmx4096M -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Djava.net.preferIPv4Stack=true -Dminecraft.applet.TargetDirectory="+Main.getClientDir().toString() + File.separator + version +" -Dlog4j.configurationFile="+Main.getClientDir().toString() + File.separator + version +"\\assets\\log_configs\\client-1.12.xml cpw.mods.modlauncher.Launcher --username "+ user.GetLogin() +" --version Forge1.16.5 --gameDir "+Main.getClientDir().toString() + File.separator + version +" --assetsDir "+Main.getClientDir().toString() + File.separator + version +"\\assets --assetIndex 1.16 --uuid "+user.getSession()+" --accessToken null --userType mojang --versionType modified" +
                    //                x + y + " --launchTarget fmlclient --fml.forgeVersion 36.2.39 --fml.mcVersion 1.16.5 --fml.forgeGroup net.minecraftforge --fml.mcpVersion 20210115.111550");
                    System.out.println(suffix + p1);
                    assert p1 != null;
                    InputStream is = p1.getInputStream();

                    gameIsStart = true;
                    int i;
                    while ((i = is.read()) != -1) {
                        System.out.print((char) i);
                    }
                    p1.waitFor();
                    Platform.runLater(() -> {
                        gameIsStart = false;
                        playController.PlayButtonEnabled(true);
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
