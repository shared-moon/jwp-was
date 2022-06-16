package utils.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestReaderTest {

    @DisplayName("read - HttpRequest를 읽어들여 HttpRequest 반환")
    @Test
    void read() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8))));

        HttpRequest httpRequest = HttpRequestReader.read(bufferedReader);

        bufferedReader.close();

        assertThat(httpRequest).isEqualTo(new HttpRequest(HttpMethod.GET, "/index.html", "HTTP", "1.1", Map.of()));
    }
}