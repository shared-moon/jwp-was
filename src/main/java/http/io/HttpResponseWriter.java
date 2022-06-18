package http.io;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseWriter {
    private final OutputStream outputStream;

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(HttpResponse httpResponse) throws IOException {
        byte[] response = httpResponse.getBytes();
        outputStream.write(response);
        outputStream.flush();
    }
}
