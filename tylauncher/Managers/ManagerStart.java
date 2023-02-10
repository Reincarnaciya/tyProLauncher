package tylauncher.Managers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import tylauncher.Controllers.PlayController;
import tylauncher.Main;
import tylauncher.Utilites.Settings;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import static tylauncher.Main.user;
public class ManagerStart {
    public static PlayController playController;

    private String UUID;
    private String accessToken;
    private String autoConnect;
    private String size;
    private String fullScrean;
    private final String version;

    private final String pathToVersion;

    public ManagerStart(boolean autoConnect, boolean fullScrean, String version) {
        if(autoConnect) this.autoConnect = "--server ip29.ip-146-59-75.eu --port 25574";
        if(!fullScrean) this.size = String.format(" --width %s --height %s", Settings.getX(), Settings.getY());
        else this.fullScrean = "--fullscreen";
        pathToVersion =  Main.getClientDir().toString() + File.separator + version;
        this.version = version;
    }

    public void Start() throws Exception {
        createUserHashes();
        new Thread(() -> {
            try {
                System.err.printf("================STARTUP[%s]SETTINGS================\n%s%n", this.version, Settings.show());
                Runtime runtime = Runtime.getRuntime();
                Process p1;

                String start = getStartString();
                System.err.printf("================STARTUP[%s]STRING================\n%s%n", this.version, start);
                p1 = runtime.exec(start);

                InputStream is = p1.getInputStream();
                
                ManagerFlags.gameIsStart = true;
                ManagerWindow.currentController.setInfoText("Игра запущена");
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
    private void createUserHashes() throws Exception{
        ManagerWeb userHashes = new ManagerWeb("userHashes");

        userHashes.setUrl("https://typro.space/vendor/launcher/login_get_hash_launcher.php");
        userHashes.putAllParams(Arrays.asList("login", "password"), Arrays.asList(user.GetLogin(), user.GetPassword()));
        userHashes.request();

        JsonObject requestObject = (JsonObject) JsonParser.parseString(userHashes.getFullAnswer());
        if(requestObject.get("status") == null || requestObject.get("status").toString().equalsIgnoreCase("false"))
            throw new Exception("Не удалось получить сессию. Обратитесь к администрации");

        requestObject = (JsonObject) requestObject.get("hashes");

        this.UUID = String.valueOf(requestObject.get("uuid"));
        this.accessToken = String.valueOf(requestObject.get("accessToken"));
    }

    private String getStartString() throws Exception {
        String startSt = null;
        switch (ManagerDirs.getPlatform()){
            case windows:
                startSt = Main.getRuntimeDir() + ""+File.separator+"jre8"+File.separator+"bin"+File.separator+"javaw.exe";
                break;
            case linux:
                startSt = Main.getRuntimeDir() + ""+File.separator+"jre8"+File.separator+"bin"+File.separator+"java";
                break;
            case macos:
            case unknown:
            default:
        }
        if(startSt == null) throw new Exception("Не удалоось инициализировать строку запуска");
        return  startSt
                + " -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -Xss1M "
                + "-Djava.library.path=" + pathToVersion + ""+File.separator+"versions"+File.separator+"Forge1.16.5"+File.separator+"natives -Dminecraft.launcher.brand=minecraft-launcher -Dminecraft.launcher.version=2.3.173 -cp "
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"forge"+File.separator+"1.16.5-36.2.39"+File.separator+"forge-1.16.5-36.2.39.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"ow2"+File.separator+"asm"+File.separator+"asm"+File.separator+"9.1"+File.separator+"asm-9.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"ow2"+File.separator+"asm"+File.separator+"asm-commons"+File.separator+"9.1"+File.separator+"asm-commons-9.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"ow2"+File.separator+"asm"+File.separator+"asm-tree"+File.separator+"9.1"+File.separator+"asm-tree-9.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"ow2"+File.separator+"asm"+File.separator+"asm-util"+File.separator+"9.1"+File.separator+"asm-util-9.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"ow2"+File.separator+"asm"+File.separator+"asm-analysis"+File.separator+"9.1"+File.separator+"asm-analysis-9.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"cpw"+File.separator+"mods"+File.separator+"modlauncher"+File.separator+"8.1.3"+File.separator+"modlauncher-8.1.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"cpw"+File.separator+"mods"+File.separator+"grossjava9hacks"+File.separator+"1.3.3"+File.separator+"grossjava9hacks-1.3.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"accesstransformers"+File.separator+"3.0.1"+File.separator+"accesstransformers-3.0.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"antlr"+File.separator+"antlr4-runtime"+File.separator+"4.9.1"+File.separator+"antlr4-runtime-4.9.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"eventbus"+File.separator+"4.0.0"+File.separator+"eventbus-4.0.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"forgespi"+File.separator+"3.2.0"+File.separator+"forgespi-3.2.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"coremods"+File.separator+"4.0.6"+File.separator+"coremods-4.0.6.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"unsafe"+File.separator+"0.2.0"+File.separator+"unsafe-0.2.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"electronwill"+File.separator+"night-config"+File.separator+"core"+File.separator+"3.6.3"+File.separator+"core-3.6.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"electronwill"+File.separator+"night-config"+File.separator+"toml"+File.separator+"3.6.3"+File.separator+"toml-3.6.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"jline"+File.separator+"jline"+File.separator+"3.12.1"+File.separator+"jline-3.12.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"maven"+File.separator+"maven-artifact"+File.separator+"3.6.3"+File.separator+"maven-artifact-3.6.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"jodah"+File.separator+"typetools"+File.separator+"0.8.3"+File.separator+"typetools-0.8.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"logging"+File.separator+"log4j"+File.separator+"log4j-api"+File.separator+"2.15.0"+File.separator+"log4j-api-2.15.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"logging"+File.separator+"log4j"+File.separator+"log4j-core"+File.separator+"2.15.0"+File.separator+"log4j-core-2.15.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"logging"+File.separator+"log4j"+File.separator+"log4j-slf4j18-impl"+File.separator+"2.15.0"+File.separator+"log4j-slf4j18-impl-2.15.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecrell"+File.separator+"terminalconsoleappender"+File.separator+"1.2.0"+File.separator+"terminalconsoleappender-1.2.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"sf"+File.separator+"jopt-simple"+File.separator+"jopt-simple"+File.separator+"5.0.4"+File.separator+"jopt-simple-5.0.4.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"spongepowered"+File.separator+"mixin"+File.separator+"0.8.4"+File.separator+"mixin-0.8.4.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"minecraftforge"+File.separator+"nashorn-core-compat"+File.separator+"15.1.1.1"+File.separator+"nashorn-core-compat-15.1.1.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"oshi-project"+File.separator+"oshi-core"+File.separator+"1.1"+File.separator+"oshi-core-1.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"java"+File.separator+"dev"+File.separator+"jna"+File.separator+"jna"+File.separator+"4.4.0"+File.separator+"jna-4.4.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"java"+File.separator+"dev"+File.separator+"jna"+File.separator+"platform"+File.separator+"3.4.0"+File.separator+"platform-3.4.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"ibm"+File.separator+"icu"+File.separator+"icu4j"+File.separator+"66.1"+File.separator+"icu4j-66.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"mojang"+File.separator+"javabridge"+File.separator+"1.0.22"+File.separator+"javabridge-1.0.22.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"sf"+File.separator+"jopt-simple"+File.separator+"jopt-simple"+File.separator+"5.0.3"+File.separator+"jopt-simple-5.0.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"io"+File.separator+"netty"+File.separator+"netty-all"+File.separator+"4.1.25.Final"+File.separator+"netty-all-4.1.25.Final.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"google"+File.separator+"guava"+File.separator+"guava"+File.separator+"21.0"+File.separator+"guava-21.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"commons"+File.separator+"commons-lang3"+File.separator+"3.5"+File.separator+"commons-lang3-3.5.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"commons-io"+File.separator+"commons-io"+File.separator+"2.5"+File.separator+"commons-io-2.5.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"commons-codec"+File.separator+"commons-codec"+File.separator+"1.10"+File.separator+"commons-codec-1.10.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"java"+File.separator+"jinput"+File.separator+"jinput"+File.separator+"2.0.5"+File.separator+"jinput-2.0.5.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"net"+File.separator+"java"+File.separator+"jutils"+File.separator+"jutils"+File.separator+"1.0.0"+File.separator+"jutils-1.0.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"mojang"+File.separator+"brigadier"+File.separator+"1.0.17"+File.separator+"brigadier-1.0.17.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"mojang"+File.separator+"datafixerupper"+File.separator+"4.0.26"+File.separator+"datafixerupper-4.0.26.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"google"+File.separator+"code"+File.separator+"gson"+File.separator+"gson"+File.separator+"2.8.0"+File.separator+"gson-2.8.0.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"mojang"+File.separator+"authlib"+File.separator+"2.1.28"+File.separator+"authlib-2.1.28.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"commons"+File.separator+"commons-compress"+File.separator+"1.8.1"+File.separator+"commons-compress-1.8.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"httpcomponents"+File.separator+"httpclient"+File.separator+"4.3.3"+File.separator+"httpclient-4.3.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"commons-logging"+File.separator+"commons-logging"+File.separator+"1.1.3"+File.separator+"commons-logging-1.1.3.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"httpcomponents"+File.separator+"httpcore"+File.separator+"4.3.2"+File.separator+"httpcore-4.3.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"it"+File.separator+"unimi"+File.separator+"dsi"+File.separator+"fastutil"+File.separator+"8.2.1"+File.separator+"fastutil-8.2.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"logging"+File.separator+"log4j"+File.separator+"log4j-api"+File.separator+"2.8.1"+File.separator+"log4j-api-2.8.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"apache"+File.separator+"logging"+File.separator+"log4j"+File.separator+"log4j-core"+File.separator+"2.8.1"+File.separator+"log4j-core-2.8.1.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl"+File.separator+"3.2.2"+File.separator+"lwjgl-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl-jemalloc"+File.separator+"3.2.2"+File.separator+"lwjgl-jemalloc-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl-openal"+File.separator+"3.2.2"+File.separator+"lwjgl-openal-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl-opengl"+File.separator+"3.2.2"+File.separator+"lwjgl-opengl-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl-glfw"+File.separator+"3.2.2"+File.separator+"lwjgl-glfw-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl-stb"+File.separator+"3.2.2"+File.separator+"lwjgl-stb-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"org"+File.separator+"lwjgl"+File.separator+"lwjgl-tinyfd"+File.separator+"3.2.2"+File.separator+"lwjgl-tinyfd-3.2.2.jar;"
                + pathToVersion + ""+File.separator+"libraries"+File.separator+"com"+File.separator+"mojang"+File.separator+"text2speech"+File.separator+"1.11.3"+File.separator+"text2speech-1.11.3.jar;"
                + pathToVersion + ""+File.separator+"versions"+File.separator+"Forge1.16.5"+File.separator+"Forge1.16.5.jar"
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
                + " -Dlog4j.configurationFile=" + pathToVersion + ""+File.separator+"assets"+File.separator+"log_configs"+File.separator+"client-1.12.xml"
                + " cpw.mods.modlauncher.Launcher "
                + "--username " + user.GetLogin()
                + " --version Forge 1.16.5 "
                + "--gameDir " + pathToVersion + ""
                + " --assetsDir " + pathToVersion + ""+File.separator+"assets "
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
}
