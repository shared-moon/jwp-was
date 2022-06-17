package http;

import http.io.HttpResponseWriter;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseWriterTest {

    @DisplayName("ok - 200 응답 만들어 반환")
    @Test
    void ok() {
        // given
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HttpResponseWriter httpResponseWriter = new HttpResponseWriter(bos);

        // when
        httpResponseWriter.ok();

        // then
        String response = bos.toString(StandardCharsets.UTF_8);
        assertThat(response).startsWith("HTTP/1.1 200 OK");
    }
}