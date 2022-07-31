package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String ROOT_PATH_TEMPLATES = "./templates";
    private static final String ROOT_PATH_STATICS = "./static";

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL url = loadFromTemplatesOrStatics(filePath);
        if (url == null) {
            throw new FileNotFoundException(filePath + " 파일을 찾을 수 없습니다.");
        }
        return Files.readAllBytes(Paths.get(url.toURI()));
    }

    private static URL loadFromTemplatesOrStatics(String path) {
        URL url = load(ROOT_PATH_TEMPLATES + path);
        if (url == null) {
            url = load(ROOT_PATH_STATICS + path);
        }
        return url;
    }

    private static URL load(String path) {
        return FileIoUtils.class.getClassLoader().getResource(path);
    }
}
