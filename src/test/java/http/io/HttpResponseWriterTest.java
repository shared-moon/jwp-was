package http.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseWriterTest {

    @DisplayName("write - 200 응답 만들어 반환")
    @Test
    void write() throws IOException {
        // given
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HttpResponseWriter httpResponseWriter = new HttpResponseWriter(bos);

        // when
        httpResponseWriter.write(HttpStatus.OK);

        // then
        String response = bos.toString(StandardCharsets.UTF_8);
        assertThat(response).startsWith("HTTP/1.1 200 OK");
    }
}