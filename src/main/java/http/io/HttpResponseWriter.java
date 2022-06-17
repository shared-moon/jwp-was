package http.io;

import http.enums.HttpStatus;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseWriter {
    private final OutputStream outputStream;

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(HttpStatus status) throws IOException {
        write(status, null);
    }

    public void write(HttpStatus status, byte[] body) throws IOException {
        HttpResponseHeader responseHeader = new HttpResponseHeader(status);
        HttpResponse httpResponse = new HttpResponse(responseHeader, body);

        byte[] response = httpResponse.getBytes();
        outputStream.write(response);
        outputStream.flush();
    }
}
