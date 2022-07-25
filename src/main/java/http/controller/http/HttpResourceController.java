package http.controller.http;

import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class HttpResourceController {
    public static HttpResponse apply(HttpRequest httpRequest) {
        try {
            byte[] resource = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
            return HttpResponseFactory.ok(resource).build();
        } catch (FileNotFoundException e) {
            return HttpResponseFactory.notFound().build();
        } catch (IOException | URISyntaxException e) {
            return HttpResponseFactory.error().build();
        }
    }
}
