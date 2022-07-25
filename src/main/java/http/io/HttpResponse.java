package http.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class HttpResponse {
    private static final String FIRST_LINE_FORMAT = "%s/%s %s %s";
    private static final String NEXT_LINE = "\r\n";

    private final String protocol = "HTTP";
    private final String version = "1.1";
    private final HttpStatus status;
    private final HttpHeader header;
    private final byte[] body;

    public HttpResponse(HttpStatus status, HttpHeader header, byte[] body) {
        this.status = status;
        this.header = header;
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] getBytes() {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos)) {
            writeHeader(dos);

            dos.writeBytes(NEXT_LINE);

            writeBodyIfNotNull(dos, body);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void writeHeader(DataOutputStream dos) throws IOException {
        writeResponseFirstLine(dos);
        writeResponseHeaders(dos);
        writeContentLengthLine(dos);
    }

    private void writeResponseFirstLine(DataOutputStream dos) throws IOException {
        String responseHead = String.format(FIRST_LINE_FORMAT, protocol, version, status.value(), status.getReasonPhrase());
        dos.writeBytes(responseHead);
        dos.writeBytes(NEXT_LINE);
    }

    private void writeContentLengthLine(DataOutputStream dos) throws IOException {
        if (Objects.isNull(body)) {
            return;
        }
        writeResponseHeader(dos, HttpHeaders.CONTENT_LENGTH, body.length);
    }

    private void writeResponseHeaders(DataOutputStream dos) throws IOException {
        for(String key : header.keySet()) {
            writeResponseHeader(dos, key, header.get(key));
        }
    }

    private void writeResponseHeader(DataOutputStream dos, String key, Object value) throws IOException {
        String headerLine = key + ": " + value;
        dos.writeBytes(headerLine);
        dos.writeBytes(NEXT_LINE);
    }

    private void writeBodyIfNotNull(DataOutputStream dos, byte[] body) throws IOException {
        if (body == null) {
            return;
        }
        dos.write(body);
    }
}
