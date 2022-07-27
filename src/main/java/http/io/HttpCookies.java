package http.io;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookies {
    private static final String COOKIE_FORMAT = "%s=%s";
    private static final String DELIMITER = "; ";
    private final Map<String, String> cookies = new HashMap<>();

    public HttpCookies() {
        cookies.put("Path", "/");
    }

    public void put(String key, Object value) {
        cookies.put(key, String.valueOf(value));
    }

    public String cookieLine() {
        return cookies.entrySet().stream()
                .map(entry -> String.format(COOKIE_FORMAT, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(DELIMITER));
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }
}
