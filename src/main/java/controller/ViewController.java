package controller;

import webserver.Request;
import webserver.Response;

public class ViewController extends FileController {

    private static final String TEMPLATES_DIR = "./templates";

    @Override
    public Response service(Request req) {
        return serveFile(TEMPLATES_DIR + req.getPath());
    }
}
