package webserver;

import http.controller.http.HttpController;
import http.io.HttpRequest;
import http.io.HttpRequestReader;
import http.io.HttpResponse;
import http.io.HttpResponseWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try {
            HttpRequestReader requestReader = new HttpRequestReader(connection.getInputStream());
            HttpRequest httpRequest = requestReader.read();

            HttpResponse httpResponse = HttpController.apply(httpRequest);

            HttpResponseWriter responseWriter = new HttpResponseWriter(connection.getOutputStream());
            responseWriter.write(httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try { connection.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
