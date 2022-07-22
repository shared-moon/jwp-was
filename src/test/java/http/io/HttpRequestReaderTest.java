package http.io;

import http.enums.HttpMethod;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestReaderTest {

    @DisplayName("read - HttpRequest를 읽어들여 HttpRequest 반환")
    @Test
    void read() throws IOException {
        // given
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        HttpRequestReader httpRequestReader = new HttpRequestReader(new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)));

        // when
        HttpRequest httpRequest = httpRequestReader.read();

        // then
        assertThat(httpRequest).isEqualTo(new HttpRequest(HttpMethod.GET, "/index.html", "HTTP", "1.1", Map.of(), Map.of()));
    }
}