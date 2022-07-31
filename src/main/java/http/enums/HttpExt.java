package http.enums;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public enum HttpExt {
    HTML("html"),
    JS("js") {
        @Override
        public String getContentType(String path) {
            return "application/javascript;charset=utf-8";
        }
    },
    IMAGE("png") {
        @Override
        public String getContentType(String path) {
            String ext = HttpExt.getExt(path);
            return "image/" + ext;
        }
    },
    FONT("eot", "ttf", "woff", "woff2", "svg"),
    STYLE("css") {
        @Override
        public String getContentType(String path) {
            return "text/css";
        }
    },
    ICON("ico"),
    NO_RESOURCE()
    ;

    private Set<String> exts;

    HttpExt(String ... exts) {
        this.exts = Arrays.stream(exts).collect(toSet());
    }

    public static HttpExt select(String path) {
        String ext = getExt(path);

        return Arrays.stream(HttpExt.values())
                .filter(e -> e.exts.contains(ext))
                .findFirst()
                .orElse(NO_RESOURCE);
    }

    private static String getExt(String path) {
        int extIdx = path.lastIndexOf(".");

        return path.substring(extIdx + 1);
    }

    public boolean isResourceExt() {
        return this != NO_RESOURCE;
    }

    public String getContentType(String path) {
        return "text/html;charset=utf-8";
    }
}
