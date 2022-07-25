package http.controller;

import http.io.HttpHeader;
import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseFactory;
import http.service.UserService;
import http.util.JsonUtils;
import model.User;

import static org.springframework.http.HttpStatus.FOUND;

public class UserCreateController implements Controller{
    private final UserService userService = new UserService();

    @Override
    public HttpResponse execute(HttpRequest request) {
        User user = JsonUtils.read(request.getBody(), User.class);
        userService.createUser(user);

        HttpHeader httpHeader = new HttpHeader();
        httpHeader.setLocation("/index.html");

        return HttpResponseFactory.builder(FOUND)
                .header(httpHeader)
                .body(user)
                .build();

    }
}
