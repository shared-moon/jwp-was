package http.util;

import http.enums.HttpMethod;
import http.io.HttpHeader;
import http.io.HttpRequest;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class HttpRequestParserTest {

    @ParameterizedTest(name = "parse - {0}")
    @MethodSource
    void parse(String requestLine, HttpRequest expected) {
        // when
        HttpRequest httpRequest = HttpRequestParser.parse(requestLine, new HttpHeader(), "");

        // then
        assertThat(httpRequest).isEqualTo(expected);
    }

    private static Stream<Arguments> parse() {
        return Stream.of(
                Arguments.of("GET /users HTTP/1.1", new HttpRequest(HttpMethod.GET, "/users", "HTTP", "1.1", new HttpHeader(), Map.of(), Map.of())),
                Arguments.of("GET /users?userId=moon HTTP/1.1", new HttpRequest(HttpMethod.GET, "/users", "HTTP", "1.1", new HttpHeader(), Map.of("userId", "moon"), Map.of())),
                Arguments.of("POST /users HTTP/1.1", new HttpRequest(HttpMethod.POST, "/users", "HTTP", "1.1", new HttpHeader(), Map.of(), Map.of()))
        );
    }

    @ParameterizedTest(name = "파싱 실패 - {0}")
    @MethodSource
    void parseWithInvalidRequestLine(String requestLine) {
        assertThatIllegalArgumentException().isThrownBy(() -> HttpRequestParser.parse(requestLine, new HttpHeader(), ""));
    }

    private static Stream<String> parseWithInvalidRequestLine() {
        return Stream.of(
                "GET/users HTTP/1.1",
                "GET /usersHTTP/1.1",
                "GET /users HTTP1.1"
        );
    }
}
