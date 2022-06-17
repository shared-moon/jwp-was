package http.io;

import http.util.HttpRequestParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class HttpRequestReader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);
    public static final String NEXT_LINE = "\r\n";

    private final InputStream inputStream;

    public HttpRequestReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest read() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String firstLine = br.readLine();
            HttpRequest httpRequest = HttpRequestParser.parse(firstLine);

            String fullRequest = readFullRequest(br, firstLine);
            logger.info("read HTTP request" + NEXT_LINE + fullRequest);

            return httpRequest;
        }
    }

    private String readFullRequest(BufferedReader br, String firstLine) throws IOException {
        StringBuilder sb = new StringBuilder(firstLine).append(NEXT_LINE);

        while(true) {
            String line = br.readLine();
            if (ObjectUtils.isEmpty(line)) {
                break;
            }
            sb.append(line).append(NEXT_LINE);
        }
        return sb.toString();
    }
}
