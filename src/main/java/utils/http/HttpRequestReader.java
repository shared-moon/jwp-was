package utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import webserver.RequestHandler;

public class HttpRequestReader {
    public static final String NEXT_LINE = "\r\n";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final InputStream inputStream;

    public HttpRequestReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest read() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String firstLine = br.readLine();
            HttpRequest httpRequest = HttpRequestParser.parse(firstLine);

            String fullRequest = readFullRequest(br, firstLine);
            logger.info("read HTTP request" + NEXT_LINE + fullRequest);

            return httpRequest;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
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
