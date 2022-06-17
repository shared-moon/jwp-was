package http.controller;

import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseHeader;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

public class HttpController {
    public static HttpResponse apply(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if(httpRequest.isResourcePath()) {
            byte[] resource = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
            return new HttpResponse(HttpResponseHeader.of(HttpStatus.OK), resource);
        }
        return new HttpResponse(HttpResponseHeader.of(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
