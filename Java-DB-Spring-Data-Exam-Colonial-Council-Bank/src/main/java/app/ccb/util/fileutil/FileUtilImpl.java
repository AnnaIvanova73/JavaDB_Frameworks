package app.ccb.util.fileutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
        return String.join("", Files.readAllLines(Path.of(filePath)));

    }
}
