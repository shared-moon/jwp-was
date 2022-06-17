package http.io;

import http.enums.HttpStatus;

public class HttpResponseHeader {
    private final String protocol = "HTTP";
    private final String version = "1.1";
    private final String contentType = "text/html;charset=utf-8";
    private final HttpStatus status;

    public HttpResponseHeader(HttpStatus status) {
        this.status = status;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getContentType() {
        return contentType;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
