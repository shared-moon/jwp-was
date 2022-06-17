package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String ROOT_PATH = "./templates";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL url = FileIoUtils.class.getClassLoader().getResource(ROOT_PATH + filePath);
        if (url == null) {
            throw new FileNotFoundException(filePath + " 파일을 찾을 수 없습니다.");
        }
        return Files.readAllBytes(Paths.get(url.toURI()));
    }
}
