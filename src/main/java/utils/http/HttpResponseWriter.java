package utils.http;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class HttpResponseWriter {
    private final OutputStream outputStream;

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void ok() {

    }
}
