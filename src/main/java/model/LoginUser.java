package model;

import java.util.Objects;

public class LoginUser {
    private String userId;
    private String password;

    public LoginUser(){}

    public LoginUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUser loginUser = (LoginUser) o;
        return Objects.equals(userId, loginUser.userId) && Objects.equals(password, loginUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password);
    }
}
