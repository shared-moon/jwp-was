package http.controller;

import db.DataBase;
import http.io.HttpCookies;
import http.io.HttpHeader;
import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseFactory;
import http.util.TemplateUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import model.User;

public class UserListController implements Controller {
    private final static String COOKIE_KEY_LOGINED = "logined";
    private static final String URI_FAILED = "/user/login.html";
    @Override
    public HttpResponse execute(HttpRequest request) {
        HttpCookies cookies = request.getHeader().getCookies();

        if (Objects.isNull(cookies)) {
            return notLoginedResponse();
        }

        boolean logined = Boolean.parseBoolean(cookies.getValue(COOKIE_KEY_LOGINED));

        if (!logined) {
            return notLoginedResponse();
        }

        return createUserListResponse();
    }

    private HttpResponse notLoginedResponse() {
        HttpHeader header = new HttpHeader();
        header.setLocation(URI_FAILED);

        return HttpResponseFactory.redirect()
                .header(header)
                .build();
    }

    private HttpResponse createUserListResponse() {
        try {
            Collection<User> users = DataBase.findAll();

            String userListTemplate = TemplateUtils.load("user/list", users);
            return HttpResponseFactory.ok(userListTemplate).build();
        } catch (IOException e) {
            return HttpResponseFactory.error(e.getMessage()).build();
        }
    }
}
