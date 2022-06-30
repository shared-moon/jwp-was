package http.controller.http;

import http.io.HttpRequest;
import http.io.HttpResponse;

public class HttpController {
    public static HttpResponse apply(HttpRequest httpRequest) {
        if(httpRequest.isResourcePath()) {
            return HttpResourceController.apply(httpRequest);
        }
        return HttpServiceController.execute(httpRequest);
    }
}
