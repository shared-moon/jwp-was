package http.io;

import http.enums.HttpMethod;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HttpRequest {
    private static final String DEFAULT_PROTOCOL = "HTTP";
    private static final String DEFAULT_VERSION = "1.1";

    private static final Set<String> RESOURCE_EXT = Set.of("html");

    private HttpMethod method;
    private String path;
    private String protocol;
    private String version;

    private Map<String,String> params;

    public HttpRequest(HttpMethod method,
                       String path,
                       String protocol,
                       String version,
                       Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.params = params;
    }

    public static HttpRequest GET(String path) {
        return new HttpRequest(HttpMethod.GET, path, DEFAULT_PROTOCOL, DEFAULT_VERSION, Map.of());
    }

    public boolean isResourcePath() {
        int extIdx = path.lastIndexOf(".");
        String ext = path.substring(extIdx + 1);

        return RESOURCE_EXT.contains(ext);
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version) && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version, params);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                ", params=" + params +
                '}';
    }
}
