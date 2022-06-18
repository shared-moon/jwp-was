package http.controller;

import http.io.HttpRequest;
import http.io.HttpResponse;
import http.io.HttpResponseHeader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

public class HttpController {
    public static HttpResponse apply(HttpRequest httpRequest) {
        if(httpRequest.isResourcePath()) {
            return createResourceResponse(httpRequest);
        }
        return new HttpResponse(HttpResponseHeader.of(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private static HttpResponse createResourceResponse(HttpRequest httpRequest) {
        try {
            byte[] resource = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
            return new HttpResponse(HttpResponseHeader.of(HttpStatus.OK), resource);
        } catch (FileNotFoundException e) {
            return new HttpResponse(HttpResponseHeader.of(HttpStatus.NOT_FOUND));
        } catch (IOException | URISyntaxException e) {
            return new HttpResponse(HttpResponseHeader.of(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
