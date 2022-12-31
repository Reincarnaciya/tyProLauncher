package tylauncher.Utilites;

import tylauncher.Main;
import tylauncher.Utilites.Managers.ManagerWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashCodeCheck {
    private static String hash = "";

    public static void Hash(String checkingFile) throws IOException, NoSuchAlgorithmException {
        hash = "";
        File dirClient = new File(checkingFile);
        if (!dirClient.exists()) return;
        File[] files;
        files = dirClient.listFiles();
        if (files != null) {
            for (File file : files) {
                switch (file.getName()) {
                    case "runtime":
                    case "versions":
                        ListFilesForFolder(file);
                        break;
                }
            }
        }
    }

    private static void ListFilesForFolder(final File folder) throws IOException, NoSuchAlgorithmException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                ListFilesForFolder(fileEntry);
            } else {
                hash += Checksum(fileEntry.getAbsolutePath());
            }
        }
    }

    public static String Checksum(String fileName) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(fileName);
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();
        //convert the byte to hex format
        StringBuilder sb = new StringBuilder();
        for (byte mdbyte : mdbytes) {
            sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        }
        fis.close();
        return sb.toString();
    }

    public static String getHash(String checkingFile) throws IOException, NoSuchAlgorithmException {
        Hash(checkingFile);
        return hash;
    }

    public static boolean CheckHashWithServer() throws Exception {
        //getHash(Main.getClientDir() + File.separator + "TySci_1.16.5"
        String hash = getHash(Main.getClientDir() + File.separator + "TySci_1.16.5");

        ManagerWeb hashManagerWeb = new ManagerWeb("hashCodeCheck");
        hashManagerWeb.setUrl("https://typro.space/vendor/server/check_hash_client.php");
        hashManagerWeb.putAllParams(Arrays.asList("mod", "hash"), Arrays.asList("TY_SCI", hash));
        hashManagerWeb.setConnectTimeout(2000);
        hashManagerWeb.request();

        System.err.println(hashManagerWeb.getAnswer());

        switch (hashManagerWeb.getAnswer()){
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new Exception("Сервер лёг. Обратитесь к администрации!\n"  + hashManagerWeb.getAnswer());
        }
    }
}
