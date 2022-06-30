package http.io;

import com.google.common.base.Charsets;
import http.util.JsonUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.HttpStatus;
public class HttpResponse {
    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
    private static final String FIRST_LINE_FORMAT = "%s/%s %s %s";
    private static final String CONTENT_TYPE_LINE_FORMAT = "Content-Type: %s";
    private static final String CONTENT_LENGTH_LINE_FORMAT = "Content-Length: %s";
    private static final String NEXT_LINE = "\r\n";
    private final HttpResponseHeader header;
    private final byte[] body;
    public HttpResponse(HttpResponseHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }
    public HttpResponse(HttpResponseHeader header) {
        this(header, null);
    }

    public static HttpResponse ok(byte[] body) {
        return createResponse(HttpStatus.OK, body);
    }
    public static HttpResponse ok(String body) {
        return ok(body.getBytes(DEFAULT_CHARSET));
    }

    public static HttpResponse ok(Object obj) {
        return ok(JsonUtils.write(obj));
    }

    public static HttpResponse notFound() {
        return notFound("");
    }

    public static HttpResponse notFound(String message) {
        return createResponse(HttpStatus.NOT_FOUND, message.getBytes(DEFAULT_CHARSET));
    }

    public static HttpResponse error() {
        return error("");
    }

    public static HttpResponse error(String message) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, message.getBytes(DEFAULT_CHARSET));
    }

    private static HttpResponse createResponse(HttpStatus httpStatus, byte[] body) {
        HttpResponseHeader responseHeader = HttpResponseHeader.of(httpStatus);
        return new HttpResponse(responseHeader, body);
    }

    public HttpStatus getStatus() {
        return header.getStatus();
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
        writeResponseHead(dos);
        writeContentTypeLine(dos);
        writeContentLengthLine(dos);
    }


    private void writeResponseHead(DataOutputStream dos) throws IOException {
        String responseHead = String.format(FIRST_LINE_FORMAT, header.getProtocol(), header.getVersion(), header.getStatus().value(), header.getStatus().getReasonPhrase());
        dos.writeBytes(responseHead);
        dos.writeBytes(NEXT_LINE);
    }

    private void writeContentTypeLine(DataOutputStream dos) throws IOException {
        String contentTypeLine = String.format(CONTENT_TYPE_LINE_FORMAT, header.getContentType());
        dos.writeBytes(contentTypeLine);
        dos.writeBytes(NEXT_LINE);
    }

    private void writeContentLengthLine(DataOutputStream dos) throws IOException {
        String contentLengthLine = String.format(CONTENT_LENGTH_LINE_FORMAT, (body==null)?0:body.length);
        dos.writeBytes(contentLengthLine);
        dos.writeBytes(NEXT_LINE);
    }

    private void writeBodyIfNotNull(DataOutputStream dos, byte[] body) throws IOException {
        if (body == null) {
            return;
        }
        dos.write(body);
    }
}
