package http.io;

import http.util.HttpRequestParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import utils.IOUtils;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;

public class HttpRequestReader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);
    public static final String NEXT_LINE = "\r\n";

    private final InputStream inputStream;

    public HttpRequestReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest read() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = br.readLine();
        HttpRequestHeader requestHeader = readHeader(br);
        String bodyLine = readBody(br, requestHeader);
        printFullRequest(requestLine, requestHeader, bodyLine);

        return HttpRequestParser.parse(requestLine, bodyLine);
    }

    private HttpRequestHeader readHeader(BufferedReader br) throws IOException {
        HttpRequestHeader requestHeader = new HttpRequestHeader();
        while(true) {
            String line = br.readLine();
            if (ObjectUtils.isEmpty(line)) {
                break;
            }
            requestHeader.add(line);
        }
        return requestHeader;
    }

    private String readBody(BufferedReader br, HttpRequestHeader requestHeader) throws IOException {
        if (!requestHeader.containsKey(CONTENT_LENGTH)) {
            return null;
        }
        int contentLength = Integer.parseInt(requestHeader.get(CONTENT_LENGTH));
        return IOUtils.readData(br, contentLength);
    }

    private void printFullRequest(String requestLine, HttpRequestHeader requestHeader, String bodyLine) {
        String fullRequest = requestLine + NEXT_LINE + requestHeader + NEXT_LINE + NEXT_LINE + bodyLine;
        logger.info("read HTTP request" + NEXT_LINE + fullRequest);
    }
}
