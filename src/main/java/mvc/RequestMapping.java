package mvc;

import http.HttpRequest;
import mvc.controller.Controller;

public interface RequestMapping {
    Controller getController(HttpRequest request);
}
