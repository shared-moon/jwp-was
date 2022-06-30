package http.controller.http;

import http.io.HttpRequest;
import http.io.HttpResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class HttpResourceController {
    public static HttpResponse apply(HttpRequest httpRequest) {
        try {
            byte[] resource = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
            return HttpResponse.ok(resource);
        } catch (FileNotFoundException e) {
            return HttpResponse.notFound();
        } catch (IOException | URISyntaxException e) {
            return HttpResponse.error();
        }
    }
}
