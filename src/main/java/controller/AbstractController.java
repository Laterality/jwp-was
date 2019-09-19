package controller;

import webserver.HttpMethod;
import webserver.Request;
import webserver.Response;

public abstract class AbstractController implements Controller {

    public abstract Response doGet(Request req);

    public abstract Response doPost(Request req);

    protected UnsupportedOperationException createUnsupportedException() {
        return new UnsupportedOperationException("Controller for this request is not implemented");
    }

    @Override
    public Response service(Request req) {
        if (req.matchMethod(HttpMethod.GET)) {
            return doGet(req);
        }
        if (req.matchMethod(HttpMethod.POST)) {
            return doPost(req);
        }

        return null;
    }
}