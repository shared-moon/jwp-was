package http.io;

import http.enums.HttpExt;
import http.enums.HttpMethod;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private final HttpMethod method;
    private final String path;
    private final String protocol;
    private final String version;

    private HttpHeader header;
    private final Map<String,String> params;
    private final Map<String,String> body;

    private HttpExt ext;

    public HttpRequest(HttpMethod method,
                       String path,
                       String protocol,
                       String version,
                       HttpHeader requestHeader,
                       Map<String, String> params,
                       Map<String, String> body) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.header = requestHeader;
        this.params = params;
        this.body = body;
        this.ext = HttpExt.select(path);
    }

    public boolean isResourcePath() {
        return ext.isResourceExt();
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public HttpExt getExt() {
        return ext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return method == that.method && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version) && Objects.equals(params, that.params) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version, params, body);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                ", params=" + params +
                ", body=" + body +
                '}';
    }
}
