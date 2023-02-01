package tylauncher.Utilites;

import tylauncher.Main;
import tylauncher.Managers.ManagerWeb;

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
    private static final String[] skipFiles = {"saves","servers.dat", "shaderpacks", "META-INF", "fonts", };
    private static final String[] checkFolders = {"runtime", "versions"};
    private final MessageDigest messageDigest;
    private final List<String> ignoredFiles;
    private final List<Path> ignoredDirectories;

    List<Path> allowedFiles;

    public HashCodeCheck(List<String> ignoredFiles, List<String> ignoredDirectories, List<String> whatToCheck, String nameDir) throws NoSuchAlgorithmException {
        this.ignoredFiles = ignoredFiles;
        this.ignoredDirectories = new ArrayList<>();
        this.allowedFiles = new ArrayList<>();
        for(String str : whatToCheck)
            this.allowedFiles.add(Paths.get(Main.getClientDir().getAbsolutePath() + File.separator + nameDir + File.separator + str));

        for (String s : ignoredDirectories)
            this.ignoredDirectories.add(Paths.get(Main.getClientDir().getAbsolutePath() + File.separator + nameDir + File.separator + s));

        messageDigest = MessageDigest.getInstance("SHA-256");
    }




    public String calculateHashes(String directoryPath) throws IOException {
        System.err.println("directoryPath = " + directoryPath);
        System.err.println(this);
        if (!new File(directoryPath).exists()) return "";
        List<Path> files = Files.walk(new File(directoryPath).toPath())
                .filter(path -> Files.isRegularFile(path)
                        && ignoredDirectories.stream().noneMatch(path::startsWith)
                        && allowedFiles.stream().anyMatch(path::startsWith))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        for (Path file1 : files) {
            if (ignoredFiles.contains(file1.getFileName().toString())) {
                continue;
            }
            System.err.println("FileToHex = " + file1.getFileName());
            messageDigest.reset();
            byte[] hash = messageDigest.digest(Files.readAllBytes(file1));
            sb.append(toHexString(hash));
        }
        return sb.toString();
    }

    public boolean CalcAndCheckWithServer(String directoryPath) throws Exception {
        String hash = calculateHashes(directoryPath);


        ManagerWeb hashManager = new ManagerWeb("hashRequest");
        hashManager.setUrl("https://typro.space/vendor/server/check_hash_client.php");
        hashManager.putAllParams(Arrays.asList("mod", "hash"), Arrays.asList("TY_SCI", hash));
        hashManager.request();

        System.err.println("-----------------------------HASH-----------------------------");
        System.err.println(hashManager.getFullAnswer());
        System.err.println(hash);
        System.err.println("-----------------------------HASH-----------------------------");

        switch (hashManager.getFullAnswer()) {
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new Exception(String.format("Сервер лёг. Обратитесь к администрации!\n%s", hashManager.getFullAnswer()));
        }
    }

    private static String toHexString(byte[] hash) {
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
