package http.service;

import db.DataBase;
import model.User;
import org.springframework.util.ObjectUtils;

public class UserService {

    public void createUser(User user) {
        validateUser(user);

        if (DataBase.existsUserById(user.getUserId())) {
            throw new IllegalArgumentException("이미 등록 된 id입니다 : " + user.getUserId());
        }

        DataBase.addUser(user);
    }

    private void validateUser(User user) {
        validateEmpty(user.getUserId(), "userId는 빈값일 수 없습니다.");
        validateEmpty(user.getPassword(), "password는 빈값일 수 없습니다.");
        validateEmpty(user.getEmail(), "email은 빈값일 수 없습니다.");
        validateEmpty(user.getName(), "name은 빈값일 수 없습니다.");
    }

    private void validateEmpty(Object obj, String message) {
        if (ObjectUtils.isEmpty(obj)) {
            throw new IllegalArgumentException(message);
        }
    }
}
