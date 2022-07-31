package http.controller.http;

import http.controller.Controller;
import http.controller.LoginController;
import http.controller.UserCreateController;
import http.controller.UserListController;
import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseFactory;
import java.util.HashMap;
import java.util.Map;

public class HttpServiceController {
    private static final Map<String, Controller> ENDPOINTS = new HashMap<>();

    static {
        ENDPOINTS.put("/user/create", new UserCreateController());
        ENDPOINTS.put("/user/login", new LoginController());
        ENDPOINTS.put("/user/list", new UserListController());
    }

    public static HttpResponse execute(HttpRequest httpRequest) {
        String path = httpRequest.getPath();
        Controller controller = ENDPOINTS.get(path);

        if (controller == null) {
            return HttpResponseFactory.notFound(path + " 경로를 찾을 수 없습니다.").build();
        }
        return controller.execute(httpRequest);
    }
}
