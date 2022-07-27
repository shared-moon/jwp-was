package http.io;

import com.google.common.base.Charsets;
import http.util.JsonUtils;
import java.nio.charset.Charset;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class HttpResponseFactory {
    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    private HttpResponseFactory(){}

    public static HttpResponseBuilder ok() {
        return builder(OK);
    }

    public static HttpResponseBuilder ok(Object body) {
        return builder(OK).body(body);
    }

    public static HttpResponseBuilder redirect() {
        return builder(FOUND);
    }

    public static HttpResponseBuilder redirect(Object body) {
        return builder(FOUND).body(body);
    }
    public static HttpResponseBuilder notFound() {
        return builder(NOT_FOUND);
    }
    public static HttpResponseBuilder notFound(Object body) {
        return builder(NOT_FOUND).body(body);
    }

    public static HttpResponseBuilder error() {
        return builder(INTERNAL_SERVER_ERROR);
    }

    public static HttpResponseBuilder error(Object body) {
        return builder(INTERNAL_SERVER_ERROR).body(body);
    }

    public static HttpResponseBuilder builder(HttpStatus status) {
        return new HttpResponseBuilder(status);
    }

    public static class HttpResponseBuilder {
        private final HttpStatus status;
        private HttpHeader header = new HttpHeader();
        private Object body;

        public HttpResponseBuilder(HttpStatus status) {
            this.status = status;
        }

        public HttpResponseBuilder header(HttpHeader header) {
            this.header = header;
            return this;
        }

        public HttpResponseBuilder body(Object body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            byte[] convertedBody;
            if (body instanceof byte[]) {
                convertedBody = (byte[])body;
            } else if (body instanceof String) {
                convertedBody = String.valueOf(body).getBytes(DEFAULT_CHARSET);
            } else {
                convertedBody = JsonUtils.write(body).getBytes(DEFAULT_CHARSET);
            }

            return new HttpResponse(status, header, convertedBody);
        }
    }
}
