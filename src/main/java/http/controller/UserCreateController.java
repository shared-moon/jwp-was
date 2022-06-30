package http.controller;

import http.io.HttpRequest;
import http.io.HttpResponse;
import http.service.UserService;
import http.util.JsonUtils;
import model.User;

public class UserCreateController implements Controller{
    private final UserService userService = new UserService();

    @Override
    public HttpResponse execute(HttpRequest request) {
        User user = JsonUtils.read(request.getParams(), User.class);
        userService.createUser(user);

        return HttpResponse.ok(user);
    }
}
