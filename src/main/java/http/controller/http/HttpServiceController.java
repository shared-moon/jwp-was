package http.controller.http;

import http.controller.Controller;
import http.controller.UserCreateController;
import http.io.HttpRequest;
import http.io.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class HttpServiceController {
    private static final Map<String, Controller> ENDPOINTS = new HashMap<>();

    static {
        ENDPOINTS.put("/user/create", new UserCreateController());
    }

    public static HttpResponse execute(HttpRequest httpRequest) {
        String path = httpRequest.getPath();
        Controller controller = ENDPOINTS.get(path);

        if (controller == null) {
            return HttpResponse.notFound(path + " 경로를 찾을 수 없습니다.");
        }
        return controller.execute(httpRequest);
    }
}
