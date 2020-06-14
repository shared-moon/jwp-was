package webserver;

import controller.*;
import http.HttpRequest;
import mvc.RequestMapping;
import mvc.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ManualRequestMapping implements RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualRequestMapping.class);

    private static Map<String, Controller> controllers = new HashMap<>();
    private static Controller forwardController = new ForwardController();
    private static Controller staticController = new StaticController();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
    }

    public Controller getController(HttpRequest request) {
        String requestUrl = getDefaultPath(request.getPath());
        logger.debug("Request Mapping Url : {}", requestUrl);

        Controller controller = controllers.get(requestUrl);
        if (controller != null) {
            return controller;
        }

        if (requestUrl.endsWith(".html")) {
            return forwardController;
        }

        return staticController;
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }
}
