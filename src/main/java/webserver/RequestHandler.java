package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestDispatcher dispatcher;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try {
            dispatcher = new RequestDispatcher();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest req = RequestParser.parse(in);
            HttpResponse res = new HttpResponse();
            dispatcher.handle(req, res);
            ResponseWriter rw = new ResponseWriter(dos);
            rw.write(req, res);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
    }
}
