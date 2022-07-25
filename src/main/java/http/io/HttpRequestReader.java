package http.io;

import http.util.HttpRequestParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import utils.IOUtils;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;

public class HttpRequestReader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);
    public static final String NEXT_LINE = "\r\n";

    private static final String DELIMITER_REQUEST_HEADER = ":";
    private static final int IDX_REQUEST_HEADER_KEY = 0;
    private static final int IDX_REQUEST_HEADER_VALUE = 1;

    private final InputStream inputStream;

    public HttpRequestReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest read() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = br.readLine();
        HttpHeader requestHeader = readHeader(br);
        String bodyLine = readBody(br, requestHeader);
        printFullRequest(requestLine, requestHeader, bodyLine);

        return HttpRequestParser.parse(requestLine, bodyLine);
    }

    private HttpHeader readHeader(BufferedReader br) throws IOException {
        HttpHeader requestHeader = new HttpHeader();
        while(true) {
            String line = br.readLine();
            if (ObjectUtils.isEmpty(line)) {
                break;
            }
            String[] header = line.split(DELIMITER_REQUEST_HEADER);
            requestHeader.put(header[IDX_REQUEST_HEADER_KEY].trim(), header[IDX_REQUEST_HEADER_VALUE].trim());
        }
        return requestHeader;
    }

    private String readBody(BufferedReader br, HttpHeader requestHeader) throws IOException {
        if (!requestHeader.containsKey(CONTENT_LENGTH)) {
            return null;
        }
        int contentLength = Integer.parseInt(requestHeader.get(CONTENT_LENGTH));
        return IOUtils.readData(br, contentLength);
    }

    private void printFullRequest(String requestLine, HttpHeader requestHeader, String bodyLine) {
        String fullRequest = requestLine + NEXT_LINE + requestHeader + NEXT_LINE;

        if (Objects.nonNull(bodyLine)) {
            fullRequest +=  NEXT_LINE + bodyLine;
        }

        logger.info("read HTTP request" + NEXT_LINE + fullRequest);
    }
}
