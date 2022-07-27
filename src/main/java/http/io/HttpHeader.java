package http.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.util.ObjectUtils;

import static com.google.common.net.HttpHeaders.LOCATION;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class HttpHeader {
    private static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    public static final String NEXT_LINE = "\r\n";
    private static final String DELIMITER = ":";
    private final Map<String, String> headers = new HashMap<>();
    private HttpCookies cookies;

    public HttpHeader() {
        headers.put(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
    }

    public void setLocation(String location) {
        headers.put(LOCATION, location);
    }

    public void setCookies(HttpCookies cookies) {
        this.cookies = cookies;
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public boolean containsKey(String key) {
        return headers.containsKey(key);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public HttpCookies getCookies() {
        return cookies;
    }

    public Set<String> keySet() {
        return headers.keySet();
    }

    public boolean notHasCookies() {
        return ObjectUtils.isEmpty(cookies);
    }
    @Override
    public String toString() {
        return headers.entrySet().stream()
                .map(entry -> entry.getKey() + DELIMITER + " " + entry.getValue())
                .collect(joining(NEXT_LINE));
    }
}
