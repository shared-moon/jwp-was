package http.controller;

import http.io.HttpRequest;
import http.io.HttpResponse;

public interface Controller {
    HttpResponse execute(HttpRequest request);
}
