package tylauncher.Utilites.Managers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import tylauncher.Controllers.PlayController;
import tylauncher.Main;
import tylauncher.Utilites.Settings;
import tylauncher.Utilites.UserPC;

import java.io.File;
import java.io.InputStream;

import static tylauncher.Main.user;
public class ManagerStart {
    public static PlayController playController;

    private String UUID;
    private String accessToken;
    private String autoConnect;
    private String size;
    private String fullScrean;
    private final String version;

    public ManagerStart(boolean autoConnect, boolean fullScrean, String version) {
        if(autoConnect) this.autoConnect = "--server ip29.ip-146-59-75.eu --port 25574";
        if(!fullScrean) this.size = String.format(" --width %s --height %s", Settings.getX(), Settings.getY());
        else this.fullScrean = "--fullscreen";
        this.version = version;
    }

    public void Start() throws Exception {
        getUserHashes();
        new Thread(() -> {
            try {
                Runtime runtime = Runtime.getRuntime();
                Process p1;
                if(!UserPC._os.contains("win")){
                    throw new Exception("Работает только на винде. сорри");
                }
                p1 = runtime.exec(getStartString());

                InputStream is = p1.getInputStream();

                ManagerFlags.gameIsStart = true;
                int i;
                while ((i = is.read()) != -1) {
                    System.out.print((char) i);
                }
                p1.waitFor();
                ManagerFlags.gameIsStart = false;
                Platform.runLater(() -> playController.PlayButtonEnabled(true));

            } catch (Exception ex) {
                ManagerWindow.currentController.setInfoText(ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
    }
    private void getUserHashes() throws Exception{
        ManagerWeb userHashes = new ManagerWeb("userHashes");
        JsonObject requestObject = (JsonObject) JsonParser.parseString(userHashes.getFullAnswer());
        if(requestObject.get("status") == null || requestObject.get("status").toString().equalsIgnoreCase("false"))
            throw new Exception("Не удалось получить сессию. Обратитесь к администрации");

        requestObject = (JsonObject) requestObject.get("hashes");

        this.UUID = String.valueOf(requestObject.get("uuid"));
        this.accessToken = String.valueOf(requestObject.get("accessToken"));


    }
    private String getStartString(){
        String pathToVersion = Main.getClientDir().toString() + File.separator + this.version;
        if (UserPC._os.contains("win")){
            return pathToVersion + "\\runtime\\jre-legacy\\windows\\jre-legacy\\bin\\javaw.exe"
                    + " -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -Xss1M "
                    + "-Djava.library.path=" + pathToVersion + "\\versions\\Forge1.16.5\\natives -Dminecraft.launcher.brand=minecraft-launcher -Dminecraft.launcher.version=2.3.173 -cp "
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\forge\\1.16.5-36.2.39\\forge-1.16.5-36.2.39.jar;"
                    + pathToVersion + "\\libraries\\org\\ow2\\asm\\asm\\9.1\\asm-9.1.jar;"
                    + pathToVersion + "\\libraries\\org\\ow2\\asm\\asm-commons\\9.1\\asm-commons-9.1.jar;"
                    + pathToVersion + "\\libraries\\org\\ow2\\asm\\asm-tree\\9.1\\asm-tree-9.1.jar;"
                    + pathToVersion + "\\libraries\\org\\ow2\\asm\\asm-util\\9.1\\asm-util-9.1.jar;"
                    + pathToVersion + "\\libraries\\org\\ow2\\asm\\asm-analysis\\9.1\\asm-analysis-9.1.jar;"
                    + pathToVersion + "\\libraries\\cpw\\mods\\modlauncher\\8.1.3\\modlauncher-8.1.3.jar;"
                    + pathToVersion + "\\libraries\\cpw\\mods\\grossjava9hacks\\1.3.3\\grossjava9hacks-1.3.3.jar;"
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\accesstransformers\\3.0.1\\accesstransformers-3.0.1.jar;"
                    + pathToVersion + "\\libraries\\org\\antlr\\antlr4-runtime\\4.9.1\\antlr4-runtime-4.9.1.jar;"
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\eventbus\\4.0.0\\eventbus-4.0.0.jar;"
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\forgespi\\3.2.0\\forgespi-3.2.0.jar;"
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\coremods\\4.0.6\\coremods-4.0.6.jar;"
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\unsafe\\0.2.0\\unsafe-0.2.0.jar;"
                    + pathToVersion + "\\libraries\\com\\electronwill\\night-config\\core\\3.6.3\\core-3.6.3.jar;"
                    + pathToVersion + "\\libraries\\com\\electronwill\\night-config\\toml\\3.6.3\\toml-3.6.3.jar;"
                    + pathToVersion + "\\libraries\\org\\jline\\jline\\3.12.1\\jline-3.12.1.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\maven\\maven-artifact\\3.6.3\\maven-artifact-3.6.3.jar;"
                    + pathToVersion + "\\libraries\\net\\jodah\\typetools\\0.8.3\\typetools-0.8.3.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.15.0\\log4j-api-2.15.0.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.15.0\\log4j-core-2.15.0.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\logging\\log4j\\log4j-slf4j18-impl\\2.15.0\\log4j-slf4j18-impl-2.15.0.jar;"
                    + pathToVersion + "\\libraries\\net\\minecrell\\terminalconsoleappender\\1.2.0\\terminalconsoleappender-1.2.0.jar;"
                    + pathToVersion + "\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.4\\jopt-simple-5.0.4.jar;"
                    + pathToVersion + "\\libraries\\org\\spongepowered\\mixin\\0.8.4\\mixin-0.8.4.jar;"
                    + pathToVersion + "\\libraries\\net\\minecraftforge\\nashorn-core-compat\\15.1.1.1\\nashorn-core-compat-15.1.1.1.jar;"
                    + pathToVersion + "\\libraries\\org\\tlauncher\\patchy\\1.3.9\\patchy-1.3.9.jar;"
                    + pathToVersion + "\\libraries\\oshi-project\\oshi-core\\1.1\\oshi-core-1.1.jar;"
                    + pathToVersion + "\\libraries\\net\\java\\dev\\jna\\jna\\4.4.0\\jna-4.4.0.jar;"
                    + pathToVersion + "\\libraries\\net\\java\\dev\\jna\\platform\\3.4.0\\platform-3.4.0.jar;"
                    + pathToVersion + "\\libraries\\com\\ibm\\icu\\icu4j\\66.1\\icu4j-66.1.jar;"
                    + pathToVersion + "\\libraries\\com\\mojang\\javabridge\\1.0.22\\javabridge-1.0.22.jar;"
                    + pathToVersion + "\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.3\\jopt-simple-5.0.3.jar;"
                    + pathToVersion + "\\libraries\\io\\netty\\netty-all\\4.1.25.Final\\netty-all-4.1.25.Final.jar;"
                    + pathToVersion + "\\libraries\\com\\google\\guava\\guava\\21.0\\guava-21.0.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\commons\\commons-lang3\\3.5\\commons-lang3-3.5.jar;"
                    + pathToVersion + "\\libraries\\commons-io\\commons-io\\2.5\\commons-io-2.5.jar;"
                    + pathToVersion + "\\libraries\\commons-codec\\commons-codec\\1.10\\commons-codec-1.10.jar;"
                    + pathToVersion + "\\libraries\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar;"
                    + pathToVersion + "\\libraries\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar;"
                    + pathToVersion + "\\libraries\\com\\mojang\\brigadier\\1.0.17\\brigadier-1.0.17.jar;"
                    + pathToVersion + "\\libraries\\com\\mojang\\datafixerupper\\4.0.26\\datafixerupper-4.0.26.jar;"
                    + pathToVersion + "\\libraries\\com\\google\\code\\gson\\gson\\2.8.0\\gson-2.8.0.jar;"
                    + pathToVersion + "\\libraries\\com\\mojang\\authlib\\2.1.28\\authlib-2.1.28.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\commons\\commons-compress\\1.8.1\\commons-compress-1.8.1.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\httpcomponents\\httpclient\\4.3.3\\httpclient-4.3.3.jar;"
                    + pathToVersion + "\\libraries\\commons-logging\\commons-logging\\1.1.3\\commons-logging-1.1.3.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\httpcomponents\\httpcore\\4.3.2\\httpcore-4.3.2.jar;"
                    + pathToVersion + "\\libraries\\it\\unimi\\dsi\\fastutil\\8.2.1\\fastutil-8.2.1.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.8.1\\log4j-api-2.8.1.jar;"
                    + pathToVersion + "\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.8.1\\log4j-core-2.8.1.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl\\3.2.2\\lwjgl-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.2.2\\lwjgl-jemalloc-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl-openal\\3.2.2\\lwjgl-openal-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.2.2\\lwjgl-opengl-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.2.2\\lwjgl-glfw-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl-stb\\3.2.2\\lwjgl-stb-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.2.2\\lwjgl-tinyfd-3.2.2.jar;"
                    + pathToVersion + "\\libraries\\com\\mojang\\text2speech\\1.11.3\\text2speech-1.11.3.jar;"
                    + pathToVersion + "\\versions\\Forge1.16.5\\Forge1.16.5.jar"
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
                    + "-Dminecraft.applet.TargetDirectory=" + pathToVersion + ""
                    + " -Dlog4j.configurationFile=" + pathToVersion + "\\assets\\log_configs\\client-1.12.xml"
                    + " cpw.mods.modlauncher.Launcher "
                    + "--username " + user.GetLogin()
                    + " --version Forge 1.16.5 "
                    + "--gameDir " + pathToVersion + ""
                    + " --assetsDir " + pathToVersion + "\\assets "
                    + "--assetIndex 1.16 "
                    + this.autoConnect
                    + " --uuid "+ this.UUID+" "
                    + "--accessToken " + this.accessToken + " "
                    + "--userType mojang "
                    + "--versionType modified"
                    + this.size
                    + " --launchTarget fmlclient "
                    + "--fml.forgeVersion 36.2.39 "
                    + "--fml.mcVersion 1.16.5 "
                    + "--fml.forgeGroup net.minecraftforge "
                    + "--fml.mcpVersion 20210115.111550 "
                    + this.fullScrean;
        }
        return null;
    }
}
