package controller;

import webserver.Request;
import webserver.Response;

public class StaticFileController extends FileController {

    private static final String STATIC_DIR = "./static";

    @Override
    public Response service(Request req) {
        return serveFile(STATIC_DIR + req.getPath());
    }
}
