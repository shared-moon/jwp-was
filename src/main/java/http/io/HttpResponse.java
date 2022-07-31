package http.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

public class HttpResponse {
    private static final String FIRST_LINE_FORMAT = "%s/%s %s %s";
    private static final String HEADER_LINE_FORMAT = "%s: %s";
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
        writeCookies(dos);
    }

    private void writeCookies(DataOutputStream dos) throws IOException {
        HttpCookies cookies = header.getCookies();

        if(ObjectUtils.isEmpty(cookies)) {
            return;
        }

        for (String key : cookies.keySet()) {
            dos.writeBytes(String.format(HEADER_LINE_FORMAT, SET_COOKIE, cookies.getCookie(key)));
            dos.writeBytes(NEXT_LINE);
        }
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
        writeResponseHeader(dos, CONTENT_LENGTH, body.length);
    }

    private void writeResponseHeaders(DataOutputStream dos) throws IOException {
        for(String key : header.keySet()) {
            writeResponseHeader(dos, key, header.get(key));
        }
    }

    private void writeResponseHeader(DataOutputStream dos, String key, Object value) throws IOException {
        dos.writeBytes(String.format(HEADER_LINE_FORMAT, key, value));
        dos.writeBytes(NEXT_LINE);
    }

    private void writeBodyIfNotNull(DataOutputStream dos, byte[] body) throws IOException {
        if (body == null) {
            return;
        }
        dos.write(body);
    }
}
