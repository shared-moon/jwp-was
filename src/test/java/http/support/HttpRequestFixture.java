package http.support;

import http.enums.HttpMethod;
import http.io.HttpRequest;
import java.util.Map;

public class HttpRequestFixture {
    private static final String DEFAULT_PROTOCOL = "HTTP";
    private static final String DEFAULT_VERSION = "1.1";

    public static HttpRequest GET(String path) {
        return new HttpRequest(HttpMethod.GET, path, DEFAULT_PROTOCOL, DEFAULT_VERSION, Map.of(), Map.of());
    }
}
