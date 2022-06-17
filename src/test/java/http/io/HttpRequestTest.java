package http.io;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    @MethodSource
    @ParameterizedTest(name = "isResourcePath - 요청에 확장자가 있는 경우 resource에 해당하는 확장자면 파일을 리턴한다")
    void isResourcePath(String path, boolean expected) {
        // given
        HttpRequest httpRequest = HttpRequest.GET(path);

        // expect
        assertThat(httpRequest.isResourcePath()).isEqualTo(expected);
    }

    private static Stream<Arguments> isResourcePath() {
        return Stream.of(
                Arguments.of("/index.html", true),
                Arguments.of("/index", false)
        );
    }

}