package http.service;

import db.DataBase;
import java.util.stream.Stream;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserServiceTest {
    private UserService userService = new UserService();

    @BeforeEach
    void setUp() {
        DataBase.init();
    }

    @DisplayName("createUser - 유저를 등록한다")
    @Test
    void createUser() {
        // given
        User user = new User("magyeon", "1234", "문선흠", "email@naver.com");

        // when
        userService.createUser(user);

        // then
        User findUser = DataBase.findUserById(user.getUserId());
        assertThat(findUser).isEqualTo(user);
    }

    @DisplayName("createUser - 동일한 userId가 등록되어 있는 경우 IllegalArgumentException이 발생한다")
    @Test
    void createUserAlreadyRegister() {
        // given
        User user = new User("magyeon", "1234", "문선흠", "email@naver.com");
        userService.createUser(user);

        // when
        assertThatIllegalArgumentException().isThrownBy(() -> userService.createUser(user));
    }

    @MethodSource
    @ParameterizedTest(name = "createUser - user 내부의 필드가 비어있거나 null인 경우 IllegalArgumentException이 발생한다")
    void createUserInvalidRequest(User user) {
        // expect
        assertThatIllegalArgumentException().isThrownBy(() -> userService.createUser(user));
    }

    private static Stream<Arguments> createUserInvalidRequest() {
        return Stream.of(
                Arguments.of(new User(null, "password", "name", "email")),
                Arguments.of(new User("id", null, "name", "email")),
                Arguments.of(new User("id", "password", null, "email")),
                Arguments.of(new User("id", "password", "name", null)),
                Arguments.of(new User("", "password", "name", "email")),
                Arguments.of(new User("id", "", "name", "email")),
                Arguments.of(new User("id", "password", "", "email")),
                Arguments.of(new User("id", "password", "name", ""))
        );
    }
}