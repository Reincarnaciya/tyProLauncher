package tylauncher.Utilites;

import tylauncher.Main;
import tylauncher.Managers.ManagerWeb;
import tylauncher.Utilites.Constants.URLS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HashCodeCheck {
    private static final Logger logger = new Logger(HashCodeCheck.class);
    private final MessageDigest messageDigest;
    private final List<String> ignoredFiles;
    private final List<Path> ignoredDirectories;

    List<Path> allowedFiles;

    public HashCodeCheck(List<String> ignoredFiles, List<String> ignoredDirectories, List<String> whatToCheck, String nameDir) throws NoSuchAlgorithmException {
        this.ignoredFiles = ignoredFiles;
        this.ignoredDirectories = new ArrayList<>();
        this.allowedFiles = new ArrayList<>();
        for (String str : whatToCheck)
            this.allowedFiles.add(Paths.get(Main.getClientDir().getAbsolutePath() + File.separator + nameDir + File.separator + str));

        if (ignoredDirectories != null) {
            for (String s : ignoredDirectories)
                this.ignoredDirectories.add(Paths.get(Main.getClientDir().getAbsolutePath() + File.separator + nameDir + File.separator + s));
        }


        messageDigest = MessageDigest.getInstance("SHA-256");
    }

    public HashCodeCheck(List<String> whatToCheck, String namePath) throws NoSuchAlgorithmException {
        this.allowedFiles = new ArrayList<>();
        this.ignoredFiles = null;
        this.ignoredDirectories = new ArrayList<>();


        for (String str : whatToCheck)
            this.allowedFiles.add(Paths.get(Main.getLauncherDir().getAbsolutePath() + File.separator + namePath + File.separator + str));


        messageDigest = MessageDigest.getInstance("SHA-256");
    }


    public String calculateHashes(String directoryPath) throws IOException {
        logger.logInfo(this.toString());
        if (!new File(directoryPath).exists()) return "";
        List<Path> files = Files.walk(new File(directoryPath).toPath())
                .filter(path -> Files.isRegularFile(path)
                        && ignoredDirectories.stream().noneMatch(path::startsWith)
                        && allowedFiles.stream().anyMatch(path::startsWith))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        for (Path file1 : files) {
            if (ignoredFiles != null) {
                if (ignoredFiles.contains(file1.getFileName().toString())) {
                    continue;
                }
            }
            messageDigest.reset();
            byte[] hash = messageDigest.digest(Files.readAllBytes(file1));
            sb.append(toHexString(hash));
        }
        return sb.toString();
    }

    public boolean CalcAndCheckWithServer(String directoryPath) throws Exception {
        String hash = calculateHashes(directoryPath);

        ManagerWeb hashManager = new ManagerWeb("hashRequest");
        hashManager.setUrl(URLS.CLIENT_HASH);

        hashManager.putAllParams(Arrays.asList("mod", "hash"), Arrays.asList("TY_SCI", hash));
        hashManager.request();

        switch (hashManager.getFullAnswer()) {
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new Exception(String.format("Сервер лёг. Обратитесь к администрации!\n%s", hashManager.getFullAnswer()));
        }
    }

    private String toHexString(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Override
    public String toString() {
        return "HashCodeCheck{" +
                "messageDigest=" + messageDigest +
                ", ignoredFiles=" + ignoredFiles +
                ", ignoredDirectories=" + ignoredDirectories +
                ", allowedFiles=" + allowedFiles +
                '}';
    }
}
