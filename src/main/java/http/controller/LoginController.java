package http.controller;

import db.DataBase;
import http.io.HttpCookie;
import http.io.HttpCookies;
import http.io.HttpHeader;
import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseFactory;
import http.util.JsonUtils;
import model.LoginUser;
import model.User;

public class LoginController implements Controller {
    private static final String URI_SUCCESS = "/index.html";
    private static final String URI_FAILED = "/user/login_failed.html";
    private static final String COOKIE_KEY_LOGINED = "logined";
    @Override
    public HttpResponse execute(HttpRequest request) {
        LoginUser loginUser = JsonUtils.read(request.getBody(), LoginUser.class);

        if (!DataBase.existsUserById(loginUser.getUserId())) {
            return failedResponse();
        }

        User user = DataBase.findUserById(loginUser.getUserId());

        if (user.notEqualsPassword(loginUser.getPassword())) {
            return failedResponse();
        }

        return successResponse();
    }

    private HttpResponse failedResponse() {
        HttpHeader header = new HttpHeader();
        header.setLocation(URI_FAILED);

        HttpCookies cookies = new HttpCookies();
        cookies.add(createLoginedCookie(false));
        header.setCookies(cookies);

        return HttpResponseFactory.redirect("아이디 혹은 비밀번호가 잘못되었습니다.")
                .header(header)
                .build();
    }

    private HttpResponse successResponse() {
        HttpHeader header = new HttpHeader();
        header.setLocation(URI_SUCCESS);

        HttpCookies cookies = new HttpCookies();
        cookies.add(createLoginedCookie(true));
        header.setCookies(cookies);

        return HttpResponseFactory.redirect()
                .header(header)
                .build();
    }

    private HttpCookie createLoginedCookie(boolean logined) {
        HttpCookie cookie = HttpCookie.of(COOKIE_KEY_LOGINED, logined);
        cookie.addOption("Path=/");

        return cookie;
    }
}
