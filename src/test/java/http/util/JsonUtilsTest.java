package http.util;

import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilsTest {

    @DisplayName("read - Map을 원하는 클래스 타입으로 변환한다")
    @Test
    void read() {
        // given
        Map<String,String> map = Map.of("userId", "userId", "password", "password", "name", "name", "email", "email", "number", "1");

        // when
        User expected = JsonUtils.read(map, User.class);

        // then
        assertThat(expected).isEqualTo(new User("userId", "password", "name", "email"));
    }

    @DisplayName("write - Object를 json string으로 변환한다")
    @Test
    void write() {
        // given
        Map<String, String> map = Map.of("userId", "userId", "password", "password");

        // when
        String expected = JsonUtils.write(map);

        // then
        assertThat(expected).isEqualTo("{\"userId\":\"userId\",\"password\":\"password\"}");
    }
}