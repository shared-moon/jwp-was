package webserver;

import http.HttpRequest;
import mvc.RequestMapping;
import mvc.controller.Controller;

public class ConfigurationRequestMapping implements RequestMapping {
    @Override
    public Controller getController(HttpRequest request) {
        // TODO xml 또는 properties 설정을 읽어 Controller 매핑
        return null;
    }
}
