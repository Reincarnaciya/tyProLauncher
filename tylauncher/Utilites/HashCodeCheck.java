package tylauncher.Utilites;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashCodeCheck {

    private final Path nameDir;
    @Nullable
    private final String[] ignoredFiles;
    @Nullable
    private final Path[] ignoredDirectories;

    public HashCodeCheck(Path nameDir, String[] ignoredFiles, Path[] ignoredDirectories) {
        this.nameDir = nameDir;
        this.ignoredFiles = ignoredFiles;
        this.ignoredDirectories = ignoredDirectories;
    }

    public HashCodeCheck(Path nameDir) {
        this(nameDir, null, null);
    }

    public String calculateHashCode() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        calculateHashCodeHelper(nameDir.toFile(), md);
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void calculateHashCodeHelper(File file, MessageDigest md) throws  IOException {
        if (ignoredFiles != null && Arrays.asList(ignoredFiles).contains(file.getName())) {
            return;
        }
        if (ignoredDirectories != null && isIgnoredDirectory(file.toPath())) {
            return;
        }
        if (file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = fis.read(buffer)) != -1) {
                    md.update(buffer, 0, read);
                }
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    calculateHashCodeHelper(child, md);
                }
            }
        }
    }

    private boolean isIgnoredDirectory(Path path) {
        for (Path ignoredDirectory : ignoredDirectories) {
            if (path.startsWith(ignoredDirectory)) {
                return true;
            }
        }
        return false;
    }
}