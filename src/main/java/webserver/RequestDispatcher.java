package webserver;

import controller.LoginController;
import controller.SignUpController;
import controller.UserListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private static final String TEMPLATES_DIR = "templates";
    private static final String STATIC_DIR = "static";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다: ";
    private static final String EXTENSION_DELIMITER = "\\.";

    private final Map<String, Controller> controllers;
    private final StaticFileResolver staticFileResolver;
    private final StaticFileResolver htmlFileResolver;

    public RequestDispatcher() throws URISyntaxException {
        controllers = new HashMap<>();
        SignUpController signUpController = new SignUpController();
        LoginController loginController = new LoginController();
        UserListController userListController = new UserListController();

        controllers.put(signUpController.getPath(), signUpController);
        controllers.put(loginController.getPath(), loginController);
        controllers.put(userListController.getPath(), userListController);

        staticFileResolver = new StaticFileResolver(STATIC_DIR);
        htmlFileResolver = new StaticFileResolver(TEMPLATES_DIR);
    }

    public void handle(HttpRequest request, HttpResponse response) {
        try {
            tryResolveFile(request.getPath(), response);

            Controller toServe = controllers.get(request.getPath());
            if (toServe != null) {
                toServe.service(request, response);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        if (response.getStatus() == null) {
            response.setStatus(HttpStatus.NOT_FOUND);
        }
    }

    private void tryResolveFile(String path, HttpResponse res) throws IOException {
        byte[] body = new byte[0];

        body = resolveStaticFile(path, body, staticFileResolver);
        body = resolveStaticFile(path, body, htmlFileResolver);

        if (body.length > 0) {
            res.setStatus(HttpStatus.OK);
            res.setContentType(extractExtension(path));
            res.setBody(body);
            res.addHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        }
    }

    private byte[] resolveStaticFile(String path, byte[] body, StaticFileResolver staticFileResolver) throws IOException {
        if (staticFileResolver.isAvailable(path)) {
            body = Files.readAllBytes(staticFileResolver.getFilePath(path));
        }
        return body;
    }

    private static MediaType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return MediaType.fromExtension(tokens[tokens.length - 1])
            .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION + tokens[tokens.length - 1]));
    }
}
