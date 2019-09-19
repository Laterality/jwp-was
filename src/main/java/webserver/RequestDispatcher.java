package webserver;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    private static final Map<String, Controller> controllers;
    private static final StaticFileController staticFileController;
    private static final ViewController viewController;

    static {
        controllers = new HashMap<>();
        staticFileController = new StaticFileController();
        viewController = new ViewController();
        SignUpController signUpController = new SignUpController();
        LoginController loginController = new LoginController();
        UserListController userListController = new UserListController();

        controllers.put(signUpController.getPath(), signUpController);
        controllers.put(loginController.getPath(), loginController);
        controllers.put(userListController.getPath(), userListController);
    }

    public static Response handle(Request request) {
        try {
            Response response = staticFileController.service(request);
            if (response != null) {
                return response;
            }

            response = viewController.service(request);
            if (response != null) {
                return response;
            }

            Controller toServe = controllers.get(request.getPath());
            if (toServe != null) {
                return toServe.service(request);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        return Response.ResponseBuilder.createBuilder()
                .withStatus(Status.NOT_FOUND)
                .build();
    }
}
