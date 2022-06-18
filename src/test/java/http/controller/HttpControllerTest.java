package http.controller;

import http.io.HttpRequest;
import http.io.HttpResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;

class HttpControllerTest {

    @DisplayName("apply - path가 자원에 관련된 확장자로 끝나는 경우 templates 경로의 파일을 전달한다")
    @Test
    void apply() throws IOException, URISyntaxException {
        // given
        HttpRequest httpRequest = HttpRequest.GET("/index.html");

        // when
        HttpResponse httpResponse = HttpController.apply(httpRequest);

        // then
        byte[] expected = FileIoUtils.loadFileFromClasspath("/index.html");
        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(expected);
    }


    @DisplayName("apply - path가 자원에 관련된 확장자로 끝나지만 templates 경로에 파일이 존재하지 않는 경우 404 응답이 반환된다")
    @Test
    void applyNotFound() {
        // given
        HttpRequest httpRequest = HttpRequest.GET("/noResource.html");

        // when
        HttpResponse httpResponse = HttpController.apply(httpRequest);

        // then
        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}