package http.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final String FIRST_LINE_FORMAT = "%s/%s %s";
    private static final String CONTENT_TYPE_LINE_FORMAT = "Content-Type: %s";
    private static final String CONTENT_LENGTH_LINE_FORMAT = "Content-Length: %s";
    private static final String NEXT_LINE = "\r\n";

    private final HttpResponseHeader header;
    private final byte[] body;

    public HttpResponse(HttpResponseHeader header,
                        byte[] body) {
        this.header = header;
        this.body = body;
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
        String responseHead = String.format(FIRST_LINE_FORMAT, header.getProtocol(), header.getVersion(), header.getStatus().description());
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
